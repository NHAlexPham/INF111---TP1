package modele.satelliteRelai;

/**
 * Classe simulant le satellite relai
 * 
 * Le satellite ne se contente que de transferer les messages du Rover vers le centre de contrôle
 * et vice-versa.
 * 
 * Le satellite exécute des cycles à intervale de TEMPS_CYCLE_MS. Période à
 * laquelle tous les messages en attente sont transmis. Ceci est implémenté à
 * l'aide d'une tâche (Thread).
 * 
 * Le relai satellite simule également les interférence dans l'envoi des messages.
 * 
 * Services offerts:
 *  - lierCentrOp
 *  - lierRover
 *  - envoyerMessageVersCentrOp
 *  - envoyerMessageVersRover
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import modele.centreControle.CentreControle;
import modele.communication.Message;
import modele.rover.Rover;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	private ArrayList<Double> destRover = new ArrayList<>();
	private ArrayList<Double> destCentreControle = new ArrayList<>();
	
	private CentreControle centreControle;
	private Rover rover;
	
	ReentrantLock lock = new ReentrantLock();
	
	private Random rand = new Random();
	
	

	public ArrayList<Double> getDestRover(){
		return destRover;
	}
	
	public ArrayList<Double> getDestCentreControle(){
		return destCentreControle;
	}
	
	/**
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) {
		
		lock.lock();
		
		try {
			
			double min = 0;
			double max = 200;
			
			double nbAleatoire = min + (max - min) * rand.nextDouble();
			
			if(nbAleatoire > PROBABILITE_PERTE_MESSAGE) {
				destCentreControle.add(nbAleatoire);
			}
			
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Méthode permettant d'envoyer un message vers le rover
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersRover(Message msg) {
		lock.lock();
		
		try {
			
			double min = 0;
			double max = 200;
			
			double nbAleatoire = min + (max - min) * rand.nextDouble();
			
			if(nbAleatoire > PROBABILITE_PERTE_MESSAGE) {
				destRover.add(nbAleatoire);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			if(destRover.size() > 0) {
				destRover.remove(0);
			}
			if(destCentreControle.size() > 0) {
				destCentreControle.remove(0);
			}

			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void lierCentrOp(CentreControle centreControle) {
		
		this.centreControle = centreControle;
	}

	public void lierRover(Rover rover) {
		
		this.rover = rover;
	}
}
