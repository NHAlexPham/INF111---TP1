package utilitaires;

import java.util.ArrayList;

public class FileSimplementChainee {

	private Noeud dernier;
	private Noeud premier;
	private int taille;

	/**
	 *
	 */
	public FileSimplementChainee() {
		taille = 0;
	}

	/**
	 *
	 * @param element
	 */
	public void ajouterElement(Object element) {
		Noeud noeud = new Noeud(element, null);
		if (estVide()){
			premier = noeud;
			dernier = noeud;
		}else{
			Noeud temp = premier;
			premier = noeud;
			premier.suivant = temp;
		}
		taille++;
	}

	/**
	 *
	 * @return
	 */
	public Object enleverElement() {
		Object noeud = null;
		if (!estVide()){
			if (taille == 1){
				noeud = premier.donnee;
				premier = null;
				dernier = null;
			}else{
				Noeud temp = dernier;
				Noeud p = premier;
				while (p.suivant != temp){
					p = p.suivant;
				}
				noeud = dernier.donnee;
				dernier = p;
				p.suivant = null;
			}
			taille--;
		}else {
			System.out.println("La File est vide!");
		}
		return noeud;
	}
	
	/*
	 * methode qui returne si la file est vide ou pas
	 */
	public boolean estVide() {
		return taille == 0;
	}

	/**
	 *
	 */
	public void afficher(){
		Noeud p = premier;
		while (p != null){
			System.out.println(p.donnee.toString());
			if (p.suivant != null)
				System.out.println("Suivant : " + p.suivant.donnee.toString());
			p = p.suivant;
		}
	}

	/**
	 *
	 * @return
	 */
	public Object getPremier() {
		return premier.donnee;
	}

	/**
	 *
	 * @return
	 */
	public Object getDernier() {
		return dernier.donnee;
	}

	/**
	 *
	 * @return
	 */
	public int getTaille(){
		return this.taille;
	}

	/**
	 * Class Interne Noeud
	 */
	static class Noeud{
		public Object donnee;
		public Noeud suivant;

		public Noeud(Object donnee, Noeud suivant) {
			this.donnee = donnee;
			this.suivant = suivant;
		}
	}
}


