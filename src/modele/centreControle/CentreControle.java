package modele.centreControle;


import modele.communication.Message;
import modele.communication.TestMessage;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;

public class CentreControle extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();
	
	public CentreControle(SatelliteRelai satelliteRelai){
		this.satelliteRelai = satelliteRelai;
		
		testEnvoieMessage();
		
	}
	
	
	public FileSimplementChainee getMsgEnvoye() {
		return msgEnvoye;
	}
	
	
	@Override
	protected void envoyerMessage(Message msg) {

		satelliteRelai.envoyerMessageVersRover(msg);	    //envoie le message au Rover
		stockerMsgEnvoye(msg);								//store le message dans la liste des messages envoyes du centre de controle
		
	}

	@Override
	protected void gestionnaireMessage(Message msg) {
		
		System.out.println("Centre de controle MESSAGE RECU No " + msg.getCompte()); //affiche le nom de la classe est le numero du message
		
		
	}
	
	public void testEnvoieMessage() {
		

		for(int i = 0; i < 100; i++) {
			TestMessage msg = new TestMessage(i);
			
			msgEnvoye.ajouterElement(msg);
			envoyerMessage(msg);
			
		}
		
	}
	

	public String toString() {
		
		
		return "Centre de controle";
	}

}
