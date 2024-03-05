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

import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

import utilitaires.FileSimplementChainee;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	
	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();
	
	//file simplement chainee des messages envoyees
	protected FileSimplementChainee msgEnvoye = new FileSimplementChainee();
	
	//Liste des message recu que le satellite a transferer
	PriorityQueue<Message> msgRecu = new PriorityQueue<Message>();
	
	
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
			msgRecu.add(msg); //ajoute les messages dans la liste des recus (en priorité les nacks au debut de la liste)
					
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
				
				boolean nackEnvoye = false; //verifie si un nack a ete envoye
				
				//Boucle tant qu'il y a des messages dans la liste 
				while(msgRecu.size() > compteCourant && !nackEnvoye) {
					
					//on obtient le prochain message
					Message message = msgRecu.poll();  			//  on prend le premier element de la liste et on le delete 
					
					
					//si le message est un nack
					if(message instanceof Nack) {
						
						//obtient le compte du message manquant (cest le compte du message Nack)
						int compteMsgManquant = message.getCompte();
						boolean trouver = false;//si on trouve le message manquant ou pas
						
						//tant qu'on a pas trouver le message manquant
						while(!trouver) {
							
							//*********** probleme dans le compte de message jsp ou est l'erreur 
							//********************************************************************************************************************
							
							//si le compte du message est inferieur au message manquant ou que cest un nack
							if(((Message) msgEnvoye.getPremier()).getCompte() < compteMsgManquant || msgEnvoye.getPremier() instanceof Nack) {
								
								System.out.println("message suprime: " + msgEnvoye.getPremier());
								msgEnvoye.enleverElement(); //on enleve le message de la liste des messages envoyes

							}
							
							//Sinon, si on trouve le message manquant
							else if(((Message) msgEnvoye.getPremier()).getCompte() == compteMsgManquant) {
								
								System.out.println("MESSAGE A RENVOYER TROUVE:  " + msgEnvoye.getPremier());
								Message msg = (Message) msgEnvoye.getPremier();    	//peek le message (recuperer le message sans le supprimer)
								envoyerMessage(msg);					 			//envoie le message
								
								
								trouver = true;										//change la valeur de trouver a vrai pour sortir de la boucle
							}
						}
						
						msgRecu.poll();	//enlever le message de la liste des messages recus 	
					
						
						//test pour afficher les messages recus
						System.out.println("*********JE SUIS UN NACK***********");					
						System.out.println();
						System.out.println("Cherche dans la liste des messages envoyee pour renvoyer le message!!");					
						System.out.println();	
						System.out.println("^^^^^^^^^^^^^ " + this);
						System.out.println("----------------------------------------------");
						
							//********************************************************************************************************************
						
					}
					
					//Sinon, si il y a un message manquant
					else if(message.getCompte() > compteCourant) {
						
						//test pour afficher les messages recus
						System.out.println("Compte du message: " + message.getCompte());
						System.out.println("Compte courant   : " + compteCourant);
						System.out.println("je suis superieur au compte courant!, envoie un NACK!!");
						System.out.println("^^^^^^^^^^^^^ " + this);
						System.out.println("----------------------------------------------");
						
						Nack nack = new Nack(compteCourant);	//creer un nack
						envoyerMessage(nack);					//envoyer le nack 
						nackEnvoye = true;						//mettre la variable a true et sortir de la boucle
						
					}
					//Sinon, si le compte du message est inferieur au compte courant
					else if(message.getCompte() < compteCourant) {
						
						//test pour afficher les messages recus
						System.out.println("Compte du message: " + message.getCompte());
						System.out.println("Compte courant   : " + compteCourant);
						System.out.println("je suis inferieur au compte courant, message efface!!");
						System.out.println("^^^^^^^^^^^^^ " + this);
						System.out.println("----------------------------------------------");
						
						msgRecu.poll();	//enlever le message de la liste des recu 		
					}
					//Sinon
					else {
						
						//test pour afficher les messages recus
						System.out.println("Compte du message: " + message.getCompte());
						System.out.println("Compte courant   : " + compteCourant);
						System.out.println("je suis egale au compte courant, message sauvegarde!!");
						System.out.println("^^^^^^^^^^^^^ " + this);
						System.out.println("----------------------------------------------");	
						
						gestionnaireMessage(message);	//faire suivre le message au gestionnaire
														//manque a defiler le message? si cest necessaire****************
						compteCourant++;				//incremente le compte courant
						
					}
					
					
					compteurMsg.getCompteActuel();							// obtient un nouveau compte unique
					//NoOp noOp = new NoOp(compteurMsg.getCompteActuel());	//envoie un NoOp     ????? A VERIFIER**********
					//envoyerMessage(noOp);	
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
	
	/*
	 * Methode qui stocke les messages dans la liste des messages envoyés
	 * @param message, message recu
	 */
	public void stockerMsgEnvoye(Message message) {
		this.msgEnvoye.ajouterElement(message);
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
