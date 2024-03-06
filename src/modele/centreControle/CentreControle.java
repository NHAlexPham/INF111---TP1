package modele.centreControle;

import modele.communication.*;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Vect2D;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class CentreControle extends TransporteurMessage {

    private SatelliteRelai satelliteRelai;
    private final PriorityQueue<Message> listEnvoyer = new PriorityQueue<>();

    public CentreControle(SatelliteRelai satelliteRelai) {
        this.satelliteRelai = satelliteRelai;
    }

    @Override
    protected void envoyerMessage(Message msg) {
        System.out.println("ENVOYER MESSAGE CENTRE ---> ROVER");
        this.satelliteRelai.envoyerMessageVersRover(msg);
        archiverMessages(msg);
    }

    @Override
    protected void gestionnaireMessage(Message msg) {
        System.out.println("GESTIONNAIRE MESSAGE - CENTRE");
        System.out.println("Class: " + this.getClass().getName() );
        if (msg instanceof Status){
            gestionnaireCommande(msg);
        }
    }

    @Override
    protected void archiverMessages(Message msg) {
        System.out.println("MESSAGE ARCHIVER -->"+this.getClass().getName());
        this.listEnvoyer.add(msg);
    }

    @Override
    protected PriorityQueue<Message> getList() {
        return listEnvoyer;
    }

    public void testMessage(Message message){
        envoyerMessage(message);
    }

    public void sequenceTest(){
        System.out.println("=== SEQUENCE TEST ===");
        Commande msgCmd = null;
        msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),eCommande.DEPLACER_ROVER,new Vect2D(25,75));
        envoyerMessage(msgCmd);
        msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),eCommande.DEPLACER_ROVER,new Vect2D(99,15));
        envoyerMessage(msgCmd);
        msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),eCommande.DEPLACER_ROVER,new Vect2D(10,10));
        envoyerMessage(msgCmd);
    }

    private void gestionnaireCommande(Message message){
        if(message instanceof Status){
            System.out.println("===========================================");
            System.out.println("STATUS --> RECU");
            System.out.println("POSITION DU ROVER: X: " + ((Status) message).getVect2D().getLongueurX() +
                    ", Y: " + ((Status) message).getVect2D().getLongueurY());
            System.out.println("===========================================");
        }
    }
}
