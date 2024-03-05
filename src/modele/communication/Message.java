package modele.communication;
/**
 * Classe de base qui définit un message.
 * 
 * Elle contient les informations permettant d'identifier la position 
 * du message dans la séquence et le temps de son envoi
 * 
 * Services offerts:
 *  - Message
 *  - getTempsEnvoi
 *  - setTempsEnvoi
 *  - getCompte
 * 
 * @author Frederic Simard, ETS
 * @version Ete, 2021
 */

import java.util.PriorityQueue;

public abstract class Message implements Comparable<Message>{

	private int compte;
	private long tempsEnvoi;

    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     * @param compte, identifiant unique et incrémental
     */
	public Message(int compte) {
		this.compte = compte; 
		tempsEnvoi = System.currentTimeMillis(); // prend en note le temps de l'envoi
	}
	
	/**
	 * Accesseur informateur, pour obtenir le temps de l'envoi
	 * @return long, temps de l'envoi
	 */
	public long getTempsEnvoi() {
		return tempsEnvoi;
	}

	/**
	 * Accesseur mutateur, pour mettre à jours le temps de l'envoi
	 * @param long, temps de la répétition de l'envoi
	 */
	public void setTempsEnvoi(long tempsEnvoi) {
		this.tempsEnvoi = tempsEnvoi;
	}

	/**
	 * Accesseur informateur, pour obtenir le compte unique
	 * @return int, compte unique
	 */
	public int getCompte() {
		return compte;
	}

	
	@Override
	public int compareTo(Message msg) {
		
		int compteMsg = msg.getCompte();
		int val = 0;

		
		if(msg instanceof Nack) {
			val = 1;
		}
		
		else if(this instanceof Nack) {
			val = -1;
			
		}
		else {
			
			if(compteMsg < compte) {
				val = 1;
			}
			else if(compteMsg > compte) {
				val = -1;
			}
			else {
				val = 0;
			}
			
		}
				
			
		
		return val;
	} 
	
	
	//main juste pour tester la priority queue  (Ce main ce run independament du programme, il va run juste la classe Message)*****
	/*
	public static void main(String[] args) {
		
		PriorityQueue<Message> pq = new PriorityQueue<>();
		
		TestMessage msg1 = new TestMessage(1);
		TestMessage msg2 = new TestMessage(2);
		TestMessage msg5 = new TestMessage(5);
		TestMessage msg10 = new TestMessage(10);
		
		Nack n1 = new Nack(11);
		Nack n2 = new Nack(12);
		
		pq.add(msg5);
		pq.add(msg10);
		pq.add(n2);
		pq.add(msg1);
		pq.add(msg2);
		pq.add(n1);
		
		while(!pq.isEmpty()) {
			Message msg = pq.poll();
			System.out.println(msg.getCompte());
		}
	}
	*/
	
}
