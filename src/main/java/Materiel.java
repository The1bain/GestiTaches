
package ca.uqo.pret;

public class Materiel {

    private final String id;
    private final String nom;
    private Statut statut = Statut.DISPONIBLE;

    public Materiel(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut s) {
        this.statut = s;
    }
}
