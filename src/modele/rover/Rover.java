package modele.rover;
/**
 * Classe simulant le Rover
 * 
 * Le rover peut envoyer et recevoir des messages. Il peut egalement se deplacer grace aux commandes du Centre de Controle
 * 
 * Services offerts:
 *  - setPositionActuelle
 *  - envoyerMessage
 *  - gestionnaireMessage
 *  - gestionnaireCommande
 *  - deplacerRover
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

import modele.communication.CmdDeplacerRover;
import modele.communication.Commande;
import modele.communication.Message;
import modele.communication.Status;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;
import utilitaires.Vect2D;

public class Rover extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;										//creation d'un nouveau satellite
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();		//liste des messages envoyer au centre de controle
	
	private Vect2D positionActuelle;											//position actuelle du rover
	private static final double VITESSE_MparS = 0.5;							//vitesse de deplacement du rover
	
	/*
	 * Constructeur qui initialise la position du rover
	 * @param satellite
	 */
	public Rover(SatelliteRelai satelliteRelai){
		
		this.satelliteRelai = satelliteRelai;
		
		positionActuelle = new Vect2D(0, 0);	//initialisation de la position actuelle du rover a (0, 0)
	}
	
	/*
	 * Setter qui permet de modifier la position du rover
	 * @param x, y les position en x et y du rover
	 */
	public void setPositionActuelle(double x, double y) {
		positionActuelle.setLongueurX(x);
		positionActuelle.setLongueurY(y);
	}
	
	
	@Override
	/*
	 * methode qui sert a envoyer les messages au satellite pour qu'il les renvoie au Centre de controle
	 * @param msg, message recu
	 */
	protected void envoyerMessage(Message msg) {

		satelliteRelai.envoyerMessageVersCentrOp(msg);	//envoie le message vers le Centre de controle
		stockerMsgEnvoye(msg);	 						//store le message dans les messages envoyes du Rover
	}

	@Override
	/*
	 * methode qui gere le message recu
	 * @param msg, message recu
	 */
	protected void gestionnaireMessage(Message msg) {
		
		System.out.println("Rover MESSAGE RECU No " + msg.getCompte());	//affiche le nom de la classe est le numero du message
		
		gestionnaireCommande((Commande)msg);
	}
	
	
	/*
	 * Methode qui gere les commandes recus en appelant la méthode approprié pour chaque cas
	 * @param cmd, commande envoyé par le centre de controle
	 */
	private void gestionnaireCommande(Commande cmd) {
		
	    switch (cmd.getECom()) {
	        case DEPLACER_ROVER:

	            	deplacerRover((CmdDeplacerRover)cmd);   //a verifier, jai fournis la commande au complet pas la destination uniquement***
	        	
	        		System.out.println("Le rover se deplace!");
	            break;
	            
	        case PRENDRE_PHOTOS:

	        		System.out.println("Le rover prend une photo!");
	        	break;
	        default:

	        		System.out.println("La commande est nulle!");	            
	            break;
	    }
	}

	
	/*
	 * Methode qui permet de deplacer le rover 
	 * @param cmd, commande que le centre de controle a envoyé
	 */
	private void deplacerRover(CmdDeplacerRover cmd) {
		
		
		//Calcul le deplcaement a effectuer (destination - position actuelle)
		Vect2D deplacement = positionActuelle.calculerDiff(cmd.getDestination());
		
		//Calcul de la distance du deplacement
		double distance = deplacement.getLongueur();
		
		//Calcul du temps requis (distance/vitesse)
		double temps =  (distance / VITESSE_MparS);
		
		//Calcul de l'angle de deplacement
		double angle = deplacement.getAngle();
		
		
		Status statusPosition = new Status(0);				//creation dun nouveau status
		statusPosition.setVecteur(positionActuelle);		//set la position actuelle dans le status
		
		
		//envoyer un message de status indiquant la position initiale du Rover
		satelliteRelai.envoyerMessageVersCentrOp(statusPosition);	
		stockerMsgEnvoye(statusPosition);	 
		
		//pour le nombre de secondes entiere que dure le deplacement
		
		for(int i = 0; i <= temps; i++) {
		
			//ajouter (equation) a la position 
			positionActuelle.ajouter(Math.cos(angle) * VITESSE_MparS, Math.sin(angle)* VITESSE_MparS);
		
			Status statusPositionMouv = new Status(i + 1);	
			statusPositionMouv.setVecteur(positionActuelle);		//set la position actuelle dans le status
		
			//envoyer un message de status qui indique la position du Rover
			satelliteRelai.envoyerMessageVersCentrOp(statusPositionMouv);	
			stockerMsgEnvoye(statusPositionMouv);	
		
		}
		
		//Calcul de la fraction de secondes requise pour terminer le deplacement
		double fractionTemps = temps % 1.0;
		
		//ajouter le reste de la fraction de temps pour le dernier deplacement
		positionActuelle.ajouter(Math.cos(angle)*VITESSE_MparS*fractionTemps, Math.sin(angle)*VITESSE_MparS*fractionTemps);
		
		statusPosition.setVecteur(positionActuelle);		//set la position actuelle dans le status
		
		//envoyer un message status indiquant la position du Rover
		satelliteRelai.envoyerMessageVersCentrOp(statusPosition);	
		stockerMsgEnvoye(statusPosition);	
	}

}
