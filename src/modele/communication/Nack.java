package modele.communication;

public class Nack extends Message{
    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     *
     * @param compte
     */
    public Nack(int compte) {
        super(compte);
    }

    @Override
    public String toString() {
        return "JE SUIS UN NACK";
    }
}
