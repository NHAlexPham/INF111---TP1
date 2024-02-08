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

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import utilitaires.FileSimplementChainee;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();
	
	private ArrayList<Message> msgEnvoye = new ArrayList<>(); 	//---------------------------------File de message a envoyer
	private ArrayList<Message> msgRecu = new ArrayList<>();		//---------------------------------liste de message recu
	
	private int posNack = 0; 									//---------------------------------position des messages nack
		
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
			
			
			if(msg instanceof Nack) {
				msgRecu.add(posNack, msg);
				posNack++;
				
			}else {
				msgRecu.add(msg.getCompte() + posNack, msg);
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
		
		while(true) {
			
			lock.lock();
			
			try {

				/*
				 * (6.3.4) Insérer votre code ici 
				 */
			
				boolean nackEnvoye = false;
				Message msgRepete = msgEnvoye.get(compteCourant);
				int compteMessage = 0;
				
				
				while(msgEnvoye.size() > 0 && !nackEnvoye ) {
					
					Message prochainMsg = msgEnvoye.get(compteCourant);
				 
					if(prochainMsg instanceof Nack) {
						
						int compte = prochainMsg.getCompte();
						boolean msgTrouve = false;
						
						ListIterator<Message> iterateur = msgEnvoye.listIterator();
						while (iterateur.hasNext()) {
						
							if( !(iterateur.next() instanceof Nack) && iterateur.next().getCompte() < compte) { 
								msgEnvoye.remove(iterateur.nextIndex());
							}
							else if(iterateur.next() instanceof Nack) {
								msgEnvoye.remove(iterateur.nextIndex());
							}
							else {
								msgRepete = iterateur.next();
								msgTrouve = true;
							}
						}
						
						
						msgRecu.add(compte, msgRepete);
						msgRecu.remove(compteCourant);
					}
					
					else if(prochainMsg.getCompte() > compteCourant) {
						
						Nack nack = new Nack(prochainMsg.getCompte());
						envoyerMessage(nack);
						nackEnvoye = true;
						
					}
					else if(prochainMsg.getCompte() < compteCourant) {
						msgEnvoye.remove(prochainMsg.getCompte());
					}
					else {
						gestionnaireMessage(prochainMsg);
						compteCourant++;
					}
					
					compteMessage ++;
					NoOp noOp = new NoOp(compteMessage);
					envoyerMessage(noOp);
					
				}
				
				
				
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

	

}
