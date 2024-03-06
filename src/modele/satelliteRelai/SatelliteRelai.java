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
import utilitaires.FileSimplementChainee;

import javax.swing.*;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();
	
	private Random rand = new Random();

	private Rover rover;
	private CentreControle centreControle;

	FileSimplementChainee fileVersRover = new FileSimplementChainee();
	FileSimplementChainee fileVersCentre = new FileSimplementChainee();
	
	
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
			double randomDouble = rand.nextDouble();
			if (randomDouble > PROBABILITE_PERTE_MESSAGE){
				fileVersCentre.ajouterElement(msg);
				System.out.println("MESSAGE AJOUTER A ... CENTRE FILE");
				System.out.println("TAILLE FIlE_CENTRE: " + fileVersCentre.getTaille());
			}else{
				System.out.println("===OUPS!===");
				System.out.println("MESSAGE OUPS CENTRE: " + msg + " #"+msg.getCompte());
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
			double randomDouble = rand.nextDouble();
			if (randomDouble > PROBABILITE_PERTE_MESSAGE){
				fileVersRover.ajouterElement(msg);
				System.out.println("MESSAGE AJOUTER A ... ROVER FILE ");
				System.out.println("TAILLE FIlE_ROVER: " + fileVersRover.getTaille());
			}else{
				System.out.println("===OUPS!===");
				System.out.println("MESSAGE OUPS ROVER: " + msg + " #"+msg.getCompte());
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {

		System.out.println("SATELLITE --> RUN");

		int cycle = 1;
		boolean run = true;
		
		while(run) {
			
			/*
			 * (5.3) Insérer votre code ici 
			 */
			System.out.println("Cycles #" + cycle);

			if (!fileVersCentre.estVide()){
				System.out.println("Satelite ---> Centre....");
				System.out.println("TAILLE FILE_CENTRE: " + fileVersCentre.getTaille());
				Message message = (Message) fileVersCentre.enleverElement();
				System.out.println("PREMIER: " + message);
				this.centreControle.receptionMessageDeSatellite( message);
			}else {
				System.out.println("Satelite ---> Centre ---> FILE VIDE");
			}

			if (!fileVersRover.estVide()){
				System.out.println("Satelite ---> Rover....");
				System.out.println("TAILLE FILE_ROVER: " + fileVersRover.getTaille());
				Message message = (Message) fileVersRover.enleverElement();
				System.out.println("PREMIER: " + message);
				this.rover.receptionMessageDeSatellite( message);
			}else{
				System.out.println("Satelite ---> Rover ---> FILE VIDE");
			}

			if(fileVersRover.estVide() && fileVersCentre.estVide()){
				//run = false;
			}
			cycle++;
			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void lierCentrOp(CentreControle centreControle){
		this.centreControle = centreControle;
	}

	public void lierRover(Rover rover){
		this.rover = rover;
	}
	

}
