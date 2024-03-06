package modele.communication;
/**
 * Classe qui implémente le protocol de communication entre le Rover
 * et le Centre d'opération.
 * 
 * Il se base sur une interprétation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyés sont mémorisé. À l'aide du compte unique
 * le transporteur de message peut identifier les Messages manquant
 * dans la séquence et demander le renvoi d'un Message à l'aide du Nack.
 * 
 * Pour contourner la situation ou le Nack lui-même est perdu, le Nack
 * est renvoyé periodiquement, tant que le Message manquant n'a pas été reçu.
 * 
 * C'est également cette classe qui gère les comptes unique.
 * 
 * Les messages reçu sont mis en file pour être traité.
 * 
 * La gestion des messages reçu s'effectue comme une tâche s'exécutant indépendamment (Thread)
 * 
 * Quelques détails:
 *  - Le traitement du Nack a priorité sur tout autre message.
 *  - Un message NoOp est envoyé périodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de séquence.
 * 
 * Services offerts:
 *  - TransporteurMessage
 *  - receptionMessageDeSatellite
 *  - run
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();

	// Listes
	PriorityQueue<Message> listeRecu = new PriorityQueue<Message>();
	
	/**
	 * Constructeur, initialise le compteur de messages unique
	 */
	public TransporteurMessage() {
		compteurMsg = new CompteurMessage();		
	}
	
	/**
	 * Méthode gérant les messages reçu du satellite. La gestion se limite
	 * à l'implémentation du Nack, les messages spécialisé sont envoyés
	 * aux classes dérivés
	 * @param msg, message reçu
	 */
	public void receptionMessageDeSatellite(Message msg) {
		lock.lock();
		
		try {
			/*
			 * (6.3.3) Insérer votre code ici 
			 */

			//System.out.println("==============MESSAGE==================");
			//System.out.println(msg.toString() + " #"+msg.getCompte());

			System.out.println("RECEPTION MESSAGE DE SATELLITE");
			try {
				listeRecu.add(msg);
				System.out.println("ELEMENT #"+msg.getCompte()+ ", " + msg.toString() +" AJOUTER A LA LISTE RECU");
			} catch (Exception e){
				System.out.println("EXCEPTION RECEPTION MESSAGE SATTELITE---> " + e);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	/**
	 * Tâche effectuant la gestion des messages reçu
	 */
	public void run() {
		
		int compteCourant = 0;

		//CompteurMessage cpt = new CompteurMessage();
		boolean run = true;
		boolean nackEnvoyer = false;
		Message messageAGerer = null;
		
		while(run) {
			
			lock.lock();

			//System.out.println("TRANSPORTEUR --> RUN");
			//System.out.println("TAILLE RECU : " + listeRecu.size());

			try {

				/*
				 * (6.3.4) Insérer votre code ici 
				 */
				//System.out.println("GESTION");
				while(!listeRecu.isEmpty() && !nackEnvoyer){
					System.out.println("OBTIENT LE MESSAGE");
					Message message = listeRecu.poll();
					System.out.println("LE MESSAGE: " + message);
					if(message instanceof Nack){
						System.out.println("UN NACK A ETE TROUVER");
						//System.out.println("COMPTE DU NACK: " + message.getCompte());
						//System.out.println("TAILLE ENVOIE : " + getList().size());
						Message envoi = null;
						Iterator<Message> iter = getList().iterator();
						while (iter.hasNext()) {
							Message msg = iter.next();

							if (msg.getCompte() == message.getCompte()) {
								//System.out.println("TROUVER: " + msg.getCompte());
								envoi = msg;
							}
							if (msg.getCompte() < compteCourant || msg instanceof Nack){
								iter.remove();
								System.out.println("============= TO DELETE =======================");
								//System.out.println("COmpare "+msg.getCompte() +" < "+ compteCourant);
								//System.out.println("Un NACK ? " + (msg instanceof Nack));
								System.out.println("DELETE - TAILLE ENVOIE : " + getList().size());
							}
						}
						envoyerMessage(envoi);
						listeRecu.remove(envoi);
					} else if (message.getCompte() > compteCourant) {
						System.out.println("COMPTEUR ERREUR");
						//System.out.println("COMPTEUR: " + compteCourant +", COMPTEUR ACTUEL"+compteurMsg.getCompteActuel());
						Nack nack = new Nack(compteCourant);
						archiverMessages(nack);
						envoyerMessage(nack);
						System.out.println("UN NACK A ETE ENVOYER");
						nackEnvoyer = true;
					} else if (message.getCompte() < compteCourant) {
						System.out.println("COMPTE INFERIEUR");
						listeRecu.remove(message);
					} else {
						System.out.println("JE NE SUIS PAS UN NACK");
						gestionnaireMessage(message);
						listeRecu.remove(message);
						compteCourant++;
					}
				}
				System.out.println("ENVOI D'UN NOOP");
				//NoOp noOp = new NoOp(compteurMsg.getCompteActuel());
				//envoyerMessage(noOp);


			}finally{
				lock.unlock();
			}
			
			// pause, cycle de traitement de messages
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * méthode abstraite utilisé pour envoyer un message
	 * @param msg, le message à envoyer
	 */
	abstract protected void envoyerMessage(Message msg);

	/**
	 * méthode abstraite utilisé pour effectuer le traitement d'un message
	 * @param msg, le message à traiter
	 */
	abstract protected void gestionnaireMessage(Message msg);

	abstract protected void archiverMessages(Message msg);

	abstract protected PriorityQueue<Message> getList();


	

}
