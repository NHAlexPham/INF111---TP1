package utilitaires;

import java.util.ArrayList;

import modele.communication.Message;

public class FileSimplementChainee {
	

	private Noeud premier; 	// premier element entree dans la file
	private Noeud dernier;	// dernier element entree dans la file
	private int nbElements;	// nombre d'elements dans la file
	
	
	/*
	 * @param file
	 */
	public FileSimplementChainee() {
	}
	
	/*
	 * retourne le dernier element de la file
	 */
	public Noeud getDernier() {
		return dernier;
	}
	/*
	 * retourne le premier element de la file 
	 */
	public Noeud getPremier() {
		return premier;
	}
	
	/*
	 * retourne le nombre d'element dans la file
	 */
	public int getNbElements() {
		return nbElements;
	}
	
	public Noeud getNoeudSuivant() {
		return premier.suivant;
	}
	
	
	public void afficheFile(FileSimplementChainee file) {
		
		FileSimplementChainee fileTest = new FileSimplementChainee();
		fileTest = file;
		
		while(fileTest.premier != null) { 
			System.out.print(fileTest.premier + "-------");
			fileTest.enleverElement();	
		}
		System.out.println();
	}
	
	/*
	 * @param element
	 * methode qui permet d'ajouter un element a la fin de la file
	 */
	public void ajouterElement(Message msg) {
		
		Noeud nouveau = new Noeud();  	//creation dun nouveau noeud
		nouveau.msg = msg;				//ajout du message dans le nouveau noeud
		
		
		//Si la file est vide, le premier noeud est le nouveau noeud
		if(estVide()) {
			this.premier = nouveau;
			this.dernier = nouveau;
		}
		//Sinon on reference ce nouveau noeud au precedent et le nouveau noeud devient le dernier 
		else {
			
			this.dernier.suivant = nouveau;
			this.dernier = nouveau;
		}
		
		this.nbElements++;	//incremente le nombre de noeud dans la file
		
		
		//affiche les informations importantes durant les test *****A EFFACER PLUTARD******
		/*
		System.out.println("---- premier            " + premier);
		System.out.println("---- suivant du premier " + premier.suivant);
		System.out.println("---- dernier            " + dernier);
		System.out.println();
		System.out.println("La file est vide?  " + estVide());
		System.out.println("Nombre d'element dans la file:   " + nbElements);
		System.out.println("______________________________________________________");
		*/
		
	}
	
	/*
	 * @param element
	 * methode qui permet d'enlever un element a la file
	 */
	public void enleverElement() {
		
		//s'il y a 1 noeud ou plus
		if(nbElements > 0) {
			
			//verifier quil y a un noeud apres pour que le noeud suivant deviens le premier noeud
			if(premier.suivant != null) {
				premier = premier.suivant;
			}
			//sinon cest le dernier noeud, donc on le supprime
			else {
				premier = null;
				dernier = null;
			}
			nbElements --; //on diminue le nombre d'element a chaque fois que l'on supprime
		}
		
		//s'il n'y a aucun noeud, on ne supprime rien *****ON PEUT ENLEVER CETTE PARTIE PUISQUE LE CODE NE FAIT RIEN SIL NE RENTRE PAS DANS LA BOUCLE AU DESSUS*****
		else if(nbElements == 0) {
			dernier = null;
		}
		
		
		
		//affiche les informations importantes durant les test *****A EFFACER PLUTARD******
		/*
		System.out.println("---- premier            " + premier);
		
		if(premier != null) {
			System.out.println("---- suivant du premier " + premier.suivant);
		}else {
			System.out.println("---- suivant du premier " + null);
		}
		System.out.println("---- dernier            " + dernier);
		System.out.println();
		System.out.println("La file est vide?  " + estVide());
		System.out.println("Nombre d'element dans la file:   " + nbElements);
		System.out.println("______________________________________________________");
		*/
	}
	
	/*
	 * methode qui returne si la file est vide ou pas
	 */
	public boolean estVide() {
		
		boolean vide = true;
		
		if(nbElements > 0) {
			vide = false;
		}
		else {
			vide = true;
		}
		
		return vide;
	}
	
	
	
	//Classe Interne noeud ***
	//-------------------------------------------------------------------------------------
	
	private class Noeud {
		public Message msg;
		public Noeud suivant;
		
		/*
		 * methode toString pour permetre l'affichage d'un noeud lors du test
		 */
		@Override
		public String toString() {
			return "Message " + msg.getCompte();
		}
		
		}
	//-------------------------------------------------------------------------------------
	
}
