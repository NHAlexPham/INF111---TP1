package modele.communication;
/**
 * Classe de base qui définit une Commande.
 * 
 * Permet au centre de controle d'envoyer un message avec une commande spécifique.
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

public class Commande extends Message{
	
	private eCommande eCom;	

	public Commande(int compte) {
		super(compte);
		
	}
	
	/*
	 * methode qui retourne le type de commande du message
	 */
	public eCommande getECom() {
		return eCom;
	}

}
