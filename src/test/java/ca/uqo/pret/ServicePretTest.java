
package ca.uqo.pret;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicePretTest {

    private final Clock horloge = Clock.fixed(
            Instant.parse("2026-11-09T10:00:00Z"),
            ZoneOffset.UTC
    );

    @ParameterizedTest
    @CsvSource({
        "-1, 0.00",
        "0, 0.00",
        "1, 2.00",
        "14, 28.00",
        "15, 30.00",
        "16, 30.00"
    })
    @DisplayName("Calcul de la pénalité selon le retard")
    void calculerPenaliteSelonRetard(
            int joursRetard, String penaliteAttendue) {

        // Arrange
        ServicePret service =
                new ServicePret(null, null, horloge);

        LocalDate echeance = LocalDate.of(2026, 11, 9);
        LocalDate retour = echeance.plusDays(joursRetard);

        // Act
        BigDecimal penalite =
                service.calculerPenalite(echeance, retour);

        // Assert
        assertEquals(
                new BigDecimal(penaliteAttendue),
                penalite
        );
    }

    @Test
    @DisplayName("Un emprunt valide enregistre le prêt")
    void emprunterMaterielDisponible() {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        Materiel materiel =
                new Materiel("M1", "Ordinateur");

        when(materiels.trouverParId("M1"))
                .thenReturn(Optional.of(materiel));

        when(prets.pretsActifsDe("E1"))
                .thenReturn(List.of());

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act
        Pret pret = service.emprunter("M1", "E1", 7);

        // Assert
        assertEquals(
                LocalDate.of(2026, 11, 16),
                pret.echeance()
        );

        assertEquals(
                Statut.EMPRUNTE,
                materiel.getStatut()
        );

        verify(materiels).sauvegarder(materiel);
        verify(prets).sauvegarder(pret);
    }

    @ParameterizedTest
    @EnumSource(
            value = Statut.class,
            names = {"EMPRUNTE", "EN_REPARATION"}
    )
    @DisplayName("Un matériel indisponible est refusé")
    void refuserEmpruntMaterielIndisponible(
            Statut statut) {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        Materiel materiel =
                new Materiel("M1", "Ordinateur");

        materiel.setStatut(statut);

        when(materiels.trouverParId("M1"))
                .thenReturn(Optional.of(materiel));

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act et Assert
        assertThrows(
                IllegalStateException.class,
                () -> service.emprunter("M1", "E1", 7)
        );

        verify(materiels, never())
                .sauvegarder(any(Materiel.class));

        verify(prets, never())
                .sauvegarder(any(Pret.class));

        assertEquals(statut, materiel.getStatut());
    }

    @Test
    @DisplayName("Refuser plus de trois prêts actifs")
    void refuserQuatriemePret() {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        Materiel materiel =
                new Materiel("M1", "Ordinateur");

        when(materiels.trouverParId("M1"))
                .thenReturn(Optional.of(materiel));

        LocalDate debut = LocalDate.of(2026, 11, 9);

        Pret p1 = new Pret("P1", "M2", "E1",
                debut, debut.plusDays(7), null);

        Pret p2 = new Pret("P2", "M3", "E1",
                debut, debut.plusDays(7), null);

        Pret p3 = new Pret("P3", "M4", "E1",
                debut, debut.plusDays(7), null);

        when(prets.pretsActifsDe("E1"))
                .thenReturn(List.of(p1, p2, p3));

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act et Assert
        assertThrows(
                IllegalStateException.class,
                () -> service.emprunter("M1", "E1", 7)
        );

        verify(prets, never())
                .sauvegarder(any(Pret.class));

        verify(materiels, never())
                .sauvegarder(any(Materiel.class));

        assertEquals(
                Statut.DISPONIBLE,
                materiel.getStatut()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15})
    @DisplayName("Refuser une durée de prêt invalide")
    void refuserDureeInvalide(int duree) {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act et Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> service.emprunter("M1", "E1", duree)
        );

        verifyNoInteractions(prets, materiels);
    }

    @Test
    @DisplayName("Refuser un matériel introuvable")
    void refuserMaterielIntrouvable() {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        when(materiels.trouverParId("M99"))
                .thenReturn(Optional.empty());

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act et Assert
        assertThrows(
                NoSuchElementException.class,
                () -> service.emprunter("M99", "E1", 7)
        );

        verify(prets, never())
                .sauvegarder(any(Pret.class));

        verify(materiels, never())
                .sauvegarder(any(Materiel.class));
    }

    @Test
    @DisplayName("Refuser un prêt déjà retourné")
    void refuserPretDejaRetourne() {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        Pret pret = new Pret(
                "P1", "M1", "E1",
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 8),
                LocalDate.of(2026, 11, 8)
        );

        when(prets.trouverParId("P1"))
                .thenReturn(Optional.of(pret));

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act et Assert
        assertThrows(
                IllegalStateException.class,
                () -> service.retourner("P1")
        );

        verify(prets, never())
                .sauvegarder(any(Pret.class));

        verifyNoInteractions(materiels);
    }

    @Test
    @DisplayName("Un retour en retard applique une pénalité")
    void retournerMaterielAvecRetard() {

        // Arrange
        PretRepository prets = mock(PretRepository.class);
        MaterielRepository materiels =
                mock(MaterielRepository.class);

        Materiel materiel =
                new Materiel("M1", "Ordinateur");

        materiel.setStatut(Statut.EMPRUNTE);

        Pret pret = new Pret(
                "P1", "M1", "E1",
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 4),
                null
        );

        when(prets.trouverParId("P1"))
                .thenReturn(Optional.of(pret));

        when(materiels.trouverParId("M1"))
                .thenReturn(Optional.of(materiel));

        ServicePret service =
                new ServicePret(prets, materiels, horloge);

        // Act
        BigDecimal penalite = service.retourner("P1");

        // Assert
        assertEquals(
                new BigDecimal("10.00"),
                penalite
        );

        assertEquals(
                Statut.DISPONIBLE,
                materiel.getStatut()
        );

        verify(materiels).sauvegarder(materiel);

        org.mockito.ArgumentCaptor<Pret> captor =
                org.mockito.ArgumentCaptor.forClass(Pret.class);

        verify(prets).sauvegarder(captor.capture());

        Pret pretSauvegarde = captor.getValue();

        assertEquals(
                LocalDate.of(2026, 11, 9),
                pretSauvegarde.retour()
        );

        assertFalse(pretSauvegarde.estActif());
    }

    @Test
    @DisplayName("Demonstration CI - echec volontaire")
    void testEchecVolontaireCI() {

        // Test incorrect volontaire pour le TD 3
        assertEquals(10, 20);
    }
}
