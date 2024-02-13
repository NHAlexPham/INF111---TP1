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
import java.util.concurrent.locks.ReentrantLock;

import utilitaires.FileSimplementChainee;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();
	
	//file simplement chainee des messages envoyees
	private FileSimplementChainee msgEnvoye = new FileSimplementChainee();
	
	//liste des messages recus
	private ArrayList<Message> msgRecu = new ArrayList<>();
	
	
	
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
				
				msgRecu.add(msg);
			}
			else {
				msgRecu.add(msg.getCompte(),msg);
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
		CompteurMessage compteurMsg = new CompteurMessage();
		
		while(true) {
			
			lock.lock();
			
			try {

				/*
				 * (6.3.4) Insérer votre code ici 
				 */
				
				
				
				boolean nackEnvoye = false; //verifie si un nack a ete envoye
				
				//Boucle tant qu'il y a des messages dans la file *************************Ou est ce que cest dans la liste qu'il faut?
				while(!msgEnvoye.estVide() && !nackEnvoye) {
					
					//on obtient le prochain message
					Message message = msgRecu.get(compteCourant);
					
					
					//si le message est un nack
					if(message instanceof Nack) {
						
						//obtient le compte du message manquant (cest le compte du message Nack)
						int compteMsgManquant = message.getCompte();
						boolean trouver = false;//si on trouve le message manquant ou pas
						
						//tant qu'on a pas trouver le message manquant
						while(!trouver) {
							
							//si le compte du message est inferieur au message manquant ou que cest un nack
							if(msgEnvoye.getPremierMsg().getCompte() < compteMsgManquant || msgEnvoye.getPremierMsg() instanceof Nack) {
								
								msgEnvoye.enleverElement(); //on enleve le message de la liste des messages envoyes
							}
							//Sinon, si on trouve le message manquant
							else if(msgEnvoye.getPremierMsg().getCompte() == compteMsgManquant) {
								
								Message msg = msgEnvoye.getPremierMsg(); //peek le message (recuperer le message sans le supprimer)
								envoyerMessage(msg);					 //envoie le message
								trouver = true;							 //change la valeur de trouver a vrai pour sortir de la boucle
							}
						}
						
						msgRecu.remove(compteCourant);	//enlever le message de la liste des messages recus
						
					}
					
					//Sinon, si il y a un message manquant
					else if(message.getCompte() > compteCourant) {
						
						Nack nack = new Nack(compteCourant);	//creer un nack
						envoyerMessage(nack);					//envoyer le nack 
						nackEnvoye = true;						//mettre la variable a true et sortir de la boucle
						
					}
					//Sinon, si le compte du message est inferieur au compte courant
					else if(message.getCompte() < compteCourant) {
						msgRecu.remove(compteCourant);	//enlever le message de la liste des recu **************A VERIFIER
					}
					//Sinon
					else {
						gestionnaireMessage(message);	//faire suivre le message au gestionnaire
						compteCourant++;				//incremente le compte courant
														//*********************************il manque a defile le message?????
					}
					
					NoOp noOp = new NoOp(compteurMsg.getCompteActuel());	// obtient un nouveau compte unique et envoie un NoOp
							
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
