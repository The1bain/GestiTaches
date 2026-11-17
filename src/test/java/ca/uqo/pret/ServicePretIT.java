
package ca.uqo.pret;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

public class ServicePretIT {

    @Test
    @DisplayName("Emprunt et retour complet avec pénalité")
    void empruntEtRetourAvecRetard() {

        // Arrange
        InMemoryPretRepository prets =
                new InMemoryPretRepository();

        InMemoryMaterielRepository materiels =
                new InMemoryMaterielRepository();

        Materiel materiel =
                new Materiel("M1", "Ordinateur");

        materiels.sauvegarder(materiel);

        Clock horlogeEmprunt = Clock.fixed(
                Instant.parse("2026-11-01T10:00:00Z"),
                ZoneOffset.UTC
        );

        ServicePret serviceEmprunt =
                new ServicePret(prets, materiels, horlogeEmprunt);

        // Act : emprunter le 1er novembre
        Pret pret = serviceEmprunt.emprunter("M1", "E1", 7);

        // Assert : le matériel est emprunté
        assertEquals(Statut.EMPRUNTE, materiel.getStatut());
        assertEquals(1, prets.pretsActifsDe("E1").size());

        // Arrange : retour le 20 novembre
        Clock horlogeRetour = Clock.fixed(
                Instant.parse("2026-11-20T10:00:00Z"),
                ZoneOffset.UTC
        );

        ServicePret serviceRetour =
                new ServicePret(prets, materiels, horlogeRetour);

        // Act : retourner le matériel
        BigDecimal penalite =
                serviceRetour.retourner(pret.id());

        // Assert : 12 jours de retard = 24 $
        assertEquals(
                new BigDecimal("24.00"),
                penalite
        );

        assertEquals(
                Statut.DISPONIBLE,
                materiel.getStatut()
        );

        assertTrue(prets.pretsActifsDe("E1").isEmpty());

        assertFalse(
                prets.trouverParId(pret.id())
                        .orElseThrow()
                        .estActif()
        );
    }
}
