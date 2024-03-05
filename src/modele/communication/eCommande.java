package modele.communication;
/**
 * Classe enum qui énumere tout les type de message de commande possible
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

public enum eCommande {

	NULLE, 				//commande est nulle
	DEPLACER_ROVER, 	//commande sert a deplacer le rover
	PRENDRE_PHOTOS		//commande sert a dire au rover de prendre des photos
	
}
