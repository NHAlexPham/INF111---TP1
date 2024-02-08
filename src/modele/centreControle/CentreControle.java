package modele.centreControle;

import modele.communication.Message;
import modele.satelliteRelai.SatelliteRelai;

public class CentreControle extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;
	
	public CentreControle(SatelliteRelai satelliteRelai){
		
		this.satelliteRelai = satelliteRelai;
	}
	
	@Override
	protected void envoyerMessage(Message msg) {

		satelliteRelai.envoyerMessageVersRover(msg);
		
	}

	@Override
	protected void gestionnaireMessage(Message msg) {

		System.out.println(this.getClass().getName() + msg);
		
	}

}
