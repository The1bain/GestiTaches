
package ca.uqo.pret;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPretRepository
        implements PretRepository {

    private final Map<String, Pret> prets = new HashMap<>();

    @Override
    public void sauvegarder(Pret pret) {
        prets.put(pret.id(), pret);
    }

    @Override
    public Optional<Pret> trouverParId(String id) {
        return Optional.ofNullable(prets.get(id));
    }

    @Override
    public List<Pret> pretsActifsDe(String emprunteurId) {
        return prets.values().stream()
                .filter(Pret::estActif)
                .filter(p -> p.emprunteurId().equals(emprunteurId))
                .toList();
    }
}
