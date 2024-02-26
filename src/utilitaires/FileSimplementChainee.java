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
	public Object getDernier() {
		return dernier.donnee;
	}
	/*
	 * retourne le premier element de la file 
	 */
	public Object getPremier() {
		return premier.donnee;
	}
	
	/*
	 * retourne le nombre d'element dans la file
	 */
	public int getNbElements() {
		return nbElements;
	}
	
	public Object getObjSuivant() {
		return premier.suivant.donnee;
	}
	
	
	public void afficheFile() {
		int nbAff = nbElements;
		
		while(nbAff > 0) { 
			System.out.print(premier.donnee + "--");
			ajouterElement(this.enleverElement());	
			nbAff --;
			
		}
		System.out.println();
	}
	
	/*
	 * @param element
	 * methode qui permet d'ajouter un element a la fin de la file
	 */
	public void ajouterElement(Object object) {
		
		Noeud nouveau = new Noeud();  			//creation dun nouveau noeud
		nouveau.donnee = object;				//ajout du message dans le nouveau noeud
		
		
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
	public Object enleverElement() {
		Noeud noeud = null;
		//s'il y a 1 noeud ou plus
		if(nbElements > 0) {
			
			//verifier quil y a un noeud apres pour que le noeud suivant deviens le premier noeud
			if(premier.suivant != null) {
				noeud = premier;
				premier = premier.suivant;				
			}
			//sinon cest le dernier noeud, donc on le supprime
			else {
				noeud = premier;
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
		
		
		return noeud.donnee;
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
		public Object donnee;
		public Noeud suivant;
		
		/*
		 * methode toString pour permetre l'affichage d'un noeud lors du test
		 */
		@Override
		public String toString() {
			return "Noeud [donnee=" + donnee + ", suivant=" + suivant + "]";
		}
		
		
	}
	//-------------------------------------------------------------------------------------
	
}
