package modele.centreControle;
/**
 * Classe simulant le Centre de controle
 * 
 * Le centre de controle peut envoyer et recevoir des messages. Il affiche aussi le status du deplacement du Rover.
 * 
 * Services offerts:
 *  - envoyerMessage
 *  - gestionnaireMessage
 *  - gestionnaireCommande
 *  - afficheStatus
 *  - sequenceTest
 *  - testEnvoieMessage
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

import modele.communication.CmdDeplacerRover;
import modele.communication.Commande;
import modele.communication.Message;
import modele.communication.Status;
import modele.communication.TestMessage;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;
import utilitaires.Vect2D;

public class CentreControle extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;										//creation d'un satellite pour le lier au centre de controle
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();		//liste des messages que le centre de controle a envoyé
	
	/*
	 * Constructeur qui initialise le satellite
	 * @param satellite
	 */
	public CentreControle(SatelliteRelai satelliteRelai){
		this.satelliteRelai = satelliteRelai;
		
		//testEnvoieMessage();
		
	}
	
	@Override
	/*
	 * methode qui sert a envoyer les messages au satellite pour qu'il les renvoie au Rover
	 * @param msg, message recu
	 */
	protected void envoyerMessage(Message msg) {
		
		satelliteRelai.envoyerMessageVersRover(msg);	    //envoie le message au Rover
		stockerMsgEnvoye(msg);								//store le message dans la liste des messages envoyes du centre de controle
	}

	@Override
	/*
	 * methode qui gere le message recu
	 * @param msg, message recu
	 */
	protected void gestionnaireMessage(Message msg) {
		
		System.out.println("Centre de controle MESSAGE RECU No " + msg.getCompte()); //affiche le nom de la classe est le numero du message
		
		gestionnaireCommande(msg);
	}
	
	/*
	 * methode qui gere le message si cest un status d'une commande
	 * @param msg, message recu
	 */
	private void gestionnaireCommande(Message msg) {
		
		//si le message est un status
	    if(msg instanceof Status) {
	    	
	    	afficheStatus((Status)msg); //afficher la position du rover
	    }
	}
	
	/*
	 * Methode qui affiche la position du rover
	 * @param stat, message status du rover
	 */
	private void afficheStatus(Status stat) {
		
		System.out.println("Status Recu");
		System.out.println("position du Rover: x: " + stat.getVecteur().getLongueurX() + " y: " + stat.getVecteur().getLongueurY());
		
	}
	
	/*
	 * Methode qui sert a tester le deplacement du rover en envoyant plusieurs commandes differentes
	 */
	public void sequenceTest() {
		
		Commande msgCmd = null; 
		msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),new Vect2D(25,75)); 
		satelliteRelai.envoyerMessageVersRover(msgCmd); 
		stockerMsgEnvoye(msgCmd);
		 
		msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),new Vect2D(99,15)); 
		satelliteRelai.envoyerMessageVersRover(msgCmd); 
		stockerMsgEnvoye(msgCmd); 
		
		msgCmd = new CmdDeplacerRover(compteurMsg.getCompteActuel(),new Vect2D(10,10)); 
		satelliteRelai.envoyerMessageVersRover(msgCmd); 
		stockerMsgEnvoye(msgCmd); 
		
		
	}
	
	/*
	 * Methode qui sert a tester l'envoie et la reception des messages (incluant les nacks)
	 */
	public void testEnvoieMessage() {

		for(int i = 0; i < 20; i++) {
			TestMessage msg = new TestMessage(i);
			
			msgEnvoye.ajouterElement(msg);
			envoyerMessage(msg);
			
		}
		
	}

}
