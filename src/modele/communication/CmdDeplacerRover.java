package modele.communication;

import utilitaires.Vect2D;

public class CmdDeplacerRover extends Commande {

    private Vect2D vect2D;
    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     *
     * @param compte
     * @param eCommande
     */
    public CmdDeplacerRover(int compte, modele.communication.eCommande eCommande, Vect2D vect2D) {
        super(compte, eCommande);
        this.vect2D = vect2D;
    }

    public Vect2D getVect2D() {
        return vect2D;
    }
}
