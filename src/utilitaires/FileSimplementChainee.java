package utilitaires;

import java.util.ArrayList;

public class FileSimplementChainee {
	

	private ArrayList<Double> file = new ArrayList<>(); // File simplement chainee
	
	
	/*
	 * constructeur vide
	 */
	public FileSimplementChainee() {
		
	}
	
	/*
	 * @param file
	 * constructeur qui prend une file en parametre 
	 */
	public FileSimplementChainee(ArrayList<Double> file) {
		
		this.file = file;
	}
	
	/*
	 * methode qui retounre la file
	 */
	public ArrayList<Double> getFile(){
		return file;
	}
	
	/*
	 * @param element
	 * methode qui permet d'ajouter un element a la file
	 */
	public void ajouterElement(double element) {
		
		file.add(element);
	}
	
	/*
	 * @param element
	 * methode qui permet d'enlever un element a la file
	 */
	public void enleverElement(double element) {
		
		file.remove(element);
	}
	
	/*
	 * methode qui returne si la file est vide ou pas
	 */
	public boolean estVide() {
		
		boolean vide = true;
		
		if(file.size() > 1) {
			vide = false;
		}
		
		return vide;
	}
}
