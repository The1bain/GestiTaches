
package ca.uqo.pret;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ServicePret {

    static final int MAX_PRETS_ACTIFS = 3;
    static final int DUREE_MAX_JOURS = 14;

    static final BigDecimal PENALITE_JOUR =
            new BigDecimal("2.00");

    static final BigDecimal PENALITE_MAX =
            new BigDecimal("30.00");

    private final PretRepository prets;
    private final MaterielRepository materiels;
    private final Clock clock;

    public ServicePret(PretRepository prets,
                       MaterielRepository materiels,
                       Clock clock) {
        this.prets = prets;
        this.materiels = materiels;
        this.clock = clock;
    }

    public Pret emprunter(String materielId,
                         String emprunteurId,
                         int dureeJours) {

        if (dureeJours < 1 ||
                dureeJours > DUREE_MAX_JOURS) {
            throw new IllegalArgumentException(
                    "Durée invalide");
        }

        Materiel m = materiels.trouverParId(materielId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Matériel introuvable"));

        if (m.getStatut() != Statut.DISPONIBLE) {
            throw new IllegalStateException(
                    "Matériel non disponible");
        }

        if (prets.pretsActifsDe(emprunteurId).size()
                >= MAX_PRETS_ACTIFS) {
            throw new IllegalStateException(
                    "Limite de prêts atteinte");
        }

        LocalDate aujourdhui = LocalDate.now(clock);

        Pret p = new Pret(
                UUID.randomUUID().toString(),
                materielId,
                emprunteurId,
                aujourdhui,
                aujourdhui.plusDays(dureeJours),
                null
        );

        m.setStatut(Statut.EMPRUNTE);
        materiels.sauvegarder(m);
        prets.sauvegarder(p);

        return p;
    }

    public BigDecimal retourner(String pretId) {

        Pret p = prets.trouverParId(pretId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Prêt introuvable"));

        if (!p.estActif()) {
            throw new IllegalStateException(
                    "Prêt déjà retourné");
        }

        LocalDate aujourdhui = LocalDate.now(clock);

        Materiel m = materiels
                .trouverParId(p.materielId())
                .orElseThrow();

        m.setStatut(Statut.DISPONIBLE);
        materiels.sauvegarder(m);

        prets.sauvegarder(new Pret(
                p.id(),
                p.materielId(),
                p.emprunteurId(),
                p.debut(),
                p.echeance(),
                aujourdhui
        ));

        return calculerPenalite(
                p.echeance(), aujourdhui);
    }

    BigDecimal calculerPenalite(
            LocalDate echeance,
            LocalDate retour) {

        long retard = ChronoUnit.DAYS.between(
                echeance, retour);

        if (retard <= 0) {
            return BigDecimal.ZERO.setScale(2);
        }

        return PENALITE_JOUR
                .multiply(BigDecimal.valueOf(retard))
                .min(PENALITE_MAX);
    }
}
