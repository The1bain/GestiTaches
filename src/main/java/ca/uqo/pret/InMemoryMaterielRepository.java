
package ca.uqo.pret;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMaterielRepository
        implements MaterielRepository {

    private final Map<String, Materiel> materiels =
            new HashMap<>();

    @Override
    public Optional<Materiel> trouverParId(String id) {
        return Optional.ofNullable(materiels.get(id));
    }

    @Override
    public void sauvegarder(Materiel materiel) {
        materiels.put(materiel.getId(), materiel);
    }
}
