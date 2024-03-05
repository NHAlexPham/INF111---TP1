package modele.communication;
/**
 * Classe de base qui définit une Commande de deplacement du Rover.
 * 
 * Ce type de message contient les informations utiles dans le rover utilisera pour se deplacer.
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

import utilitaires.Vect2D;

public class CmdDeplacerRover extends Commande{
	
	private eCommande eCom;				//type de commande pour le rover
	private Vect2D	destination;		//Destination pour le rover

	public CmdDeplacerRover(int compte, Vect2D destination) {
		super(compte);

		this.destination = destination;
		this.eCom = eCommande.DEPLACER_ROVER;
	}
	
	/*
	 * retourne le type de commande du message
	 */
	public eCommande getECom() {
		return eCom;
	}
	
	/*
	 * retourne la destination inclus dans ce message
	 */
	public Vect2D getDestination() {
		return destination;
	}

}
