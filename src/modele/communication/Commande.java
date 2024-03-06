package modele.communication;

public class Commande extends Message{

    eCommande eCommande;

    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     *
     * @param compte
     */
    public Commande(int compte, eCommande eCommande) {
        super(compte);
        this.eCommande = eCommande;
    }

    public modele.communication.eCommande geteCommande() {
        return eCommande;
    }
}
