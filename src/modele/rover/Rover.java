package modele.rover;

import modele.communication.*;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Vect2D;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Rover extends TransporteurMessage {

    private SatelliteRelai satelliteRelai;
    private final PriorityQueue<Message> listEnvoyer = new PriorityQueue<Message>();
    private Vect2D positionActuel;
    private final double VITESSE_MparS = 0.5;

    public Rover(SatelliteRelai satelliteRelai, Vect2D positionActuelle) {
        this.satelliteRelai = satelliteRelai;
        this.positionActuel = positionActuelle;
    }

    @Override
    protected void envoyerMessage(Message msg) {
        System.out.println("ENVOYER MESSAGE ROVER ---> CENTRE-CONTROLE");
        this.satelliteRelai.envoyerMessageVersCentrOp(msg);
        archiverMessages(msg);
    }

    @Override
    protected void gestionnaireMessage(Message msg) {
        System.out.println("GESTIONNAIRE MESSAGE - ROVER");
        System.out.println("Class: " + this.getClass().getName() );
        if (msg instanceof Commande){
            System.out.println("JE SUIS UNE COMMANDE");
            if(msg instanceof CmdDeplacerRover){
                System.out.println("JE SUIS UNE COMMANDE DEPLACER");
                System.out.println("JE VAIS DEPLACER LE ROVER");
                gestionnaireCommande((Commande) msg,((CmdDeplacerRover) msg).getVect2D());
            }

        }

    }

    private void gestionnaireCommande(Commande commande, Vect2D vect2D){
        switch (commande.geteCommande()){
            case NULLE:
                System.out.println("NULLE - COMMANDE");
                break;
            case DEPLACER_ROVER:
                System.out.println("DEPLACER_ROVER - COMMANDE");
                deplacerRover(vect2D);
                break;
            case PRENDRE_PHOTOS:
                System.out.println("PRENDRE_PHOTO - COMMANDE");
                break;
        }
    }

    @Override
    protected void archiverMessages(Message msg) {
        System.out.println("MESSAGE ARCHIVER -->"+this.getClass().getName());
        try{
            this.listEnvoyer.add(msg);
        }catch (Exception e){
            System.out.println("EXCEPTION ARCHIVER ---> " + e);
        }

    }

    @Override
    protected PriorityQueue<Message> getList() {
        return listEnvoyer;
    }

    private void deplacerRover(Vect2D destination){
        System.out.println("=== DEPLACEMENT DU ROVER ====");
        Vect2D position = positionActuel;
        Status status = new Status(compteurMsg.getCompteActuel(),positionActuel);
        Vect2D vect2D1 = positionActuel.calculerDiff(destination);
        double distance = vect2D1.getLongueur();
        double temps = distance/VITESSE_MparS;
        double angle = vect2D1.getAngle();


        System.out.println("POSITION INITIALE: "+ position);
        System.out.println("ENVOI D'UN STATUS ---> CENTRE");
        System.out.println("STATUS ---> " + status);
        envoyerMessage(status);


        positionActuel.ajouter(Math.cos(angle)*VITESSE_MparS,Math.sin(angle)*VITESSE_MparS);
        System.out.println("ENVOI D'UN STATUS ---> CENTRE");
        System.out.println("STATUS ---> " + status);
        envoyerMessage(status);

        double fraction = temps%1.0;
        positionActuel.ajouter(Math.cos(angle)*VITESSE_MparS*fraction,Math.sin(angle)*VITESSE_MparS*fraction);

        position = positionActuel;
        System.out.println("POSITION FINALE: "+ position);
        System.out.println("ENVOI D'UN STATUS ---> CENTRE");
        System.out.println("STATUS ---> " + status);
        envoyerMessage(status);
    }
}
