package modele.rover;

import modele.communication.Message;
import modele.satelliteRelai.SatelliteRelai;

public class Rover extends modele.communication.TransporteurMessage{

	private SatelliteRelai satelliteRelai;
	
	public Rover(SatelliteRelai satelliteRelai){
		
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
