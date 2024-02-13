package modele.centreControle;

import java.util.ArrayList;

import modele.communication.Message;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;

public class CentreControle extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();
	private ArrayList<Message> msgRecu = new ArrayList<>();
	
	public CentreControle(SatelliteRelai satelliteRelai){
		
		this.satelliteRelai = satelliteRelai;
	}
	
	
	public FileSimplementChainee getMsgEnvoye() {
		return msgEnvoye;
	}
	
	
	@Override
	protected void envoyerMessage(Message msg) {

		satelliteRelai.envoyerMessageVersRover(msg);	//envoie le message au Rover
		msgEnvoye.ajouterElement(msg);					//store le message dans la liste des messages envoyes du centre de controle
		
	}

	@Override
	protected void gestionnaireMessage(Message msg) {
		
		System.out.println(this.getClass().getName() + msg.getCompte()); //affiche le nom de la classe est le numero du message
		
		
	}

}
