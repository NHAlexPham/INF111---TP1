package modele.centreControle;

import java.util.ArrayList;

import modele.communication.Message;
import modele.communication.NoOp;
import modele.communication.testMessage;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;

public class CentreControle extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();
	//private ArrayList<Message> msgRecu = new ArrayList<>();   *********************sert a rien pour la partie 2
	
	public CentreControle(SatelliteRelai satelliteRelai){
		this.satelliteRelai = satelliteRelai;
		
		for(int i = 0; i < 100; i++) {
			testMessage msg = new testMessage(i);
			
			msgEnvoye.ajouterElement(msg);
			envoyerMessage(msg);
			
		}
		System.out.println("taille de msgEnvoye dans centre de controle: " + msgEnvoye.getNbElements());
		
		setMsgEnvoye(msgEnvoye);
	}
	
	
	public FileSimplementChainee getMsgEnvoye() {
		return msgEnvoye;
	}
	
	
	@Override
	protected void envoyerMessage(Message msg) {

		satelliteRelai.envoyerMessageVersRover(msg);	    //envoie le message au Rover
		//msgEnvoye.ajouterElement(msg);					//store le message dans la liste des messages envoyes du centre de controle
		
	}

	@Override
	protected void gestionnaireMessage(Message msg) {
		
		System.out.println("Centre de controle: " + msg.getCompte()); //affiche le nom de la classe est le numero du message
		
		
	}

}
