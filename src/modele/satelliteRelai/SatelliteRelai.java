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

import modele.communication.Message;
import utilitaires.FileSimplementChainee;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();
	
	private Random rand = new Random();
	
	FileSimplementChainee destRover = new FileSimplementChainee();
	FileSimplementChainee destCentreControle = new FileSimplementChainee();
	
	
	
	/**
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) {
		
		lock.lock();
		
		try {

			/*
			 * (5.1) Insérer votre code ici 
			 */
			
	        double nbAleatoire = rand.nextDouble(); // tire un nombre aleatoire entre 0 et 1 
			
	        //si le nombre est plus grand que la probabilite de perdre le message
			if(nbAleatoire > PROBABILITE_PERTE_MESSAGE) {
				destCentreControle.ajouterElement(msg);	//on ajoute le message a la file des messages recu du centre de controle
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

			/*
			 * (5.2) Insérer votre code ici 
			 */
			
			double nbAleatoire = rand.nextDouble(); // tire un nombre aleatoire entre 0 et 1 
			
	        //si le nombre est plus grand que la probabilite de perdre le message
			if(nbAleatoire > PROBABILITE_PERTE_MESSAGE) {
				destRover.ajouterElement(msg);	//on ajoute le message a la file des messages recu du rover
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			/*
			 * (5.3) Insérer votre code ici 
			 */
			
			
			//destRover.afficheFile(destRover);
			//destCentreControle.afficheFile(destCentreControle);
			
			if(destRover.getPremier() != null) {
				System.out.println(destRover.getPremier() + "---------" + destRover.getNoeudSuivant());
			}
			
			
			System.out.println("Cycle +1");
			
			destRover.enleverElement();
			destCentreControle.enleverElement();
			

			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
