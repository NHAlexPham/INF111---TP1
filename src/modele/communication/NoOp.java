package modele.communication;

public class NoOp extends Message{
    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     *
     * @param compte
     */
    public NoOp(int compte) {
        super(compte);
    }

    @Override
    public String toString() {
        return "JE SUIS UN NO-OP";
    }
}
