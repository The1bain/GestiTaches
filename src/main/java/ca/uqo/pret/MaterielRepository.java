
package ca.uqo.pret;

import java.util.Optional;

public interface MaterielRepository {

    Optional<Materiel> trouverParId(String id);

    void sauvegarder(Materiel materiel);
}
