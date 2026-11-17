
package ca.uqo.pret;

import java.time.LocalDate;

public record Pret(
        String id,
        String materielId,
        String emprunteurId,
        LocalDate debut,
        LocalDate echeance,
        LocalDate retour
) {
    public boolean estActif() {
        return retour == null;
    }
}
