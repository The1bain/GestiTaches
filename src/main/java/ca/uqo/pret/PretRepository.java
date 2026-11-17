
package ca.uqo.pret;

import java.util.List;
import java.util.Optional;

public interface PretRepository {

    void sauvegarder(Pret pret);

    Optional<Pret> trouverParId(String id);

    List<Pret> pretsActifsDe(String emprunteurId);
}
