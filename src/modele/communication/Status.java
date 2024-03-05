package modele.communication;
/**
 * Classe de base qui définit un message de status.
 * 
 * Ce type de message contient les informations que le rover veut update au centre de controle.
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

import utilitaires.Vect2D;

public class Status extends Message{
	
	private Vect2D vect2d;

	public Status(int compte) {
		super(compte);
		// TODO Auto-generated constructor stub
	}

	/*
	 * methode qui retourne une position incluant dans le message
	 */
	public Vect2D getVecteur() {
		return vect2d;
	}
	
	/*
	 * methode qui set les informations de la positions dans le message
	 * @param vect2d, position
	 */
	public void setVecteur(Vect2D vect2d) {
		
		this.vect2d = vect2d;
	}
}
