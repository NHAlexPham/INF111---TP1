package programme;

import java.io.IOException;

import Testing.TestMessage;
import modele.centreControle.CentreControle;
import modele.communication.CompteurMessage;
import modele.communication.Message;
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
		//testVect2D();
		//testFileSimplementChainee();
		//testNooPNack();
		//testSatelite();
		start();
	}

	/**
	 *
	 */
	private static void testNooPNack() {
		int max = 5;

		SatelliteRelai satellite = new SatelliteRelai();
		Vect2D vect2D = new Vect2D(50,50);
		Rover rover = new Rover(satellite,vect2D);
		CentreControle centreControle = new CentreControle(satellite);

		satellite.lierCentrOp(centreControle);
		satellite.lierRover(rover);

		CompteurMessage cpt = new CompteurMessage();

		for (int i = 0; i < max; i++) {
			Message message = new TestMessage(cpt.getCompteActuel(),"Message #"+i);
			centreControle.testMessage(message);
		}

		rover.start();
		centreControle.start();
		satellite.start();
	}

	/**
	 *
	 */
	public static void start() {
		SatelliteRelai satellite = new SatelliteRelai();
		Vect2D vect2D = new Vect2D(50,50);
		Rover rover = new Rover(satellite,vect2D);
		CentreControle centreControle = new CentreControle(satellite);

		satellite.lierCentrOp(centreControle);
		satellite.lierRover(rover);

		rover.start();
		centreControle.start();
		satellite.start();

		centreControle.sequenceTest();
	}

	/**
	 *
	 */
	public static void testSatelite(){
		SatelliteRelai satellite = new SatelliteRelai();

		CompteurMessage cpt = new CompteurMessage();

		TestMessage ct1 = new TestMessage(cpt.getCompteActuel(),"Houston");
		TestMessage ct2 = new TestMessage(cpt.getCompteActuel(),"Nous Avons");
		TestMessage ct3 = new TestMessage(cpt.getCompteActuel(),"Un probleme");

		TestMessage r1 = new TestMessage(cpt.getCompteActuel(),"Allo la Terre");
		TestMessage r2 = new TestMessage(cpt.getCompteActuel(),"Je veux une pizza");
		TestMessage r3 = new TestMessage(cpt.getCompteActuel(),"Coucou");

		satellite.envoyerMessageVersCentrOp(r1);
		satellite.envoyerMessageVersCentrOp(r2);
		satellite.envoyerMessageVersCentrOp(r3);

		satellite.envoyerMessageVersRover(ct1);
		satellite.envoyerMessageVersRover(ct2);
		satellite.envoyerMessageVersRover(ct3);

		satellite.start();
	}

	/**
	 *
	 */
	public static void testFileSimplementChainee() {
		//
		FileSimplementChainee file = new FileSimplementChainee();

		// Object
		Integer d1 = new Integer(1);
		Integer d2 = new Integer(2);
		Integer d3 = new Integer(3);
		Integer d4 = new Integer(4);
		Integer d5 = new Integer(5);

		file.ajouterElement(d1);
		file.ajouterElement(d2);
		file.ajouterElement(d3);

		System.out.println("Taille: " + file.getTaille());

		file.afficher();

		System.out.println("Taille: " + file.getTaille());

		System.out.println("Premier: " + file.getPremier());
		System.out.println("Dernier: " + file.getDernier());

		Object o = file.enleverElement();
		System.out.println("Defile: " + o.toString());


		System.out.println("Taille: " + file.getTaille());

		file.afficher();

		file.ajouterElement(d4);
		file.ajouterElement(d5);

		System.out.println("Taille: " + file.getTaille());

		file.afficher();

		o = file.enleverElement();
		System.out.println("Defile: " + o.toString());

		file.afficher();

		System.out.println("Taille: " + file.getTaille());
		file.enleverElement();
		file.enleverElement();
		file.enleverElement();

		System.out.println("Taille: " + file.getTaille());
		file.enleverElement();
		System.out.println("Taille: " + file.getTaille());
		file.enleverElement();
		System.out.println("Taille: " + file.getTaille());
	}

	/*
	 * methode de test pour la classe de Vect2D
	 */
	public static void testVect2D() {
		// Ct vide
		Vect2D vect2D1 = new Vect2D();
		// Ct double, double
		Vect2D vect2D2 = new Vect2D(25.5,25.3);
		// Ct copie
		Vect2D vect2D3 = new Vect2D(vect2D2);

		// Methode a tester:
		// calculerDiff
		// diviser
		// ajouter
		// getAngle

		System.out.println("L'angle du vecteur est : "+ vect2D2.getAngle());

		vect2D1.ajouter(50.35,25);

		Vect2D vect = vect2D1.calculerDiff(vect2D2);

		System.out.println("Vect resultant: " + vect);

		vect2D2.diviser(3.25);

		System.out.println("Vect resultant: " + vect2D2);

	}

}
