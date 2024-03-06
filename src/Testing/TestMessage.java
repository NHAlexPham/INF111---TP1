package Testing;

import modele.communication.Message;

public class TestMessage extends Message {

    String message;
    int compte;

    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     *
     * @param compte
     */
    public TestMessage(int compte, String message) {
        super(compte);
        this.message = message;
        this.compte = compte;
    }

    @Override
    public String toString() {
        return "TestMessage{" +
                "message='" + message + '\'' +
                ", compte=" + compte +
                '}';
    }
}
