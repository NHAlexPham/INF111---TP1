package programme;

import java.io.IOException;
import java.util.ListIterator;

import modele.centreControle.CentreControle;
import modele.rover.Rover;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.FileSimplementChainee;
import utilitaires.Vect2D;



public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args){
	
		SatelliteRelai satellite = new SatelliteRelai();
		
		
		CentreControle centreControle = new CentreControle(satellite);
		Rover rover = new Rover(satellite);
		
		
		satellite.start();
	}
	
	/*
	 * methode de test pour la classe de Vect2D
	 */
	public static void testVect2D() {
		
		Vect2D vect1 = new Vect2D();
		Vect2D vect2 = new Vect2D(12, 34);
		
		vect1.setLongueurX(10);
		vect1.setLongueurY(21);
		
		Vect2D vect3 = new Vect2D(vect1);
		
		vect1.ajouter(4, 6);
		System.out.println("vect3 en x" + vect3.getLongueurX());
		System.out.println("vect3 en y" + vect3.getLongueurY());
		
		System.out.println(vect2);
		
		System.out.println("longueur vect 2" + vect2.getLongueur());
		System.out.println("angle vect 2" +vect2.getAngle());
		
		vect1.diviser(2);
		System.out.println(vect1);
		
		if(vect1.equals(vect3)) {
			System.out.println("cest vrai!");
		}else {
			System.out.println("cest faux!");
		}
		
	}

	/*
	 * methode de test pour la classe FileSimplementChainee
	 */
	public static void testFileSimplementChainee() {
		
		FileSimplementChainee file = new FileSimplementChainee();
		
		System.out.println("La File est vide?  " + file.estVide());
		
		file.ajouterElement(32);
		file.ajouterElement(12);
		file.ajouterElement(84);
		file.ajouterElement(1);
		file.ajouterElement(46);
		
		for (int i = 0; i < file.getFile().size(); i++) {
			System.out.print(" " + file.getFile().get(i));
		}
		
		file.enleverElement(84);
		
		System.out.println();
		
		for (int i = 0; i < file.getFile().size(); i++) {
			System.out.print(" " + file.getFile().get(i));
		}
		
		System.out.println();
		
		System.out.println("La File est vide?  " + file.estVide());
		
	}
	
	
}
