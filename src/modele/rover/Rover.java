package modele.rover;

import java.util.ArrayList;

import modele.communication.Message;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;

public class Rover extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();
	//private ArrayList<Message> msgRecu = new ArrayList<>();   *********************sert a rien pour la partie 2
	
	public Rover(SatelliteRelai satelliteRelai){
		
		this.satelliteRelai = satelliteRelai;
	}
	
	
	public FileSimplementChainee getMsgEnvoye() {
		return msgEnvoye;
	}
	
	
	@Override
	protected void envoyerMessage(Message msg) {

		satelliteRelai.envoyerMessageVersCentrOp(msg);	//envoie le message vers le Centre de controle
		msgEnvoye.ajouterElement(msg);					//store le message dans les messages envoyes du Rover
		
	}

	@Override
	protected void gestionnaireMessage(Message msg) {
		
		System.out.println("Rover: " + msg.getCompte());	//affiche le nom de la classe est le numero du message
		
	}

}
