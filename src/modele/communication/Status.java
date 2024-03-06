package modele.communication;

import utilitaires.Vect2D;

public class Status extends Message{

    Vect2D vect2D;

    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     *
     * @param compte
     */
    public Status(int compte, Vect2D vect2D) {
        super(compte);
        this.vect2D = vect2D;
    }

    public Vect2D getVect2D() {
        return vect2D;
    }

    @Override
    public String toString() {
        return "Status{" +
                "vect2D=" + vect2D +
                '}';
    }
}
