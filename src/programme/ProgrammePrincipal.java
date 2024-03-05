package programme;
/**
 * Programme principale qui sert la simulation du TP
 * 
 * 
 * Services offerts:
 *  - testVect2D
 *  - testFileSimplementChainee

 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

import modele.centreControle.CentreControle;
import modele.communication.Message;
import modele.communication.Nack;
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
	
		SatelliteRelai satellite = new SatelliteRelai();				//creation d'un nouveau Satellite
		
		CentreControle centreControle = new CentreControle(satellite);	//instanciation dun centre de controle
		Rover rover = new Rover(satellite);								//instanciation dun rover
		
		satellite.lierCentreOp(centreControle);							//lier le centre de controle au satellite	
		satellite.lierRover(rover);										//lier le rover au satellite
			
		
		rover.setPositionActuelle(50, 50);								//initialiser la position du rover
		
		rover.start();
		centreControle.start();
		satellite.start();
		
		centreControle.sequenceTest();									//lancer la sequence de test pour deplacer le rover
		
		
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
		
		if(vect1.equals(vect1)) {
			System.out.println("cest vrai!");
		}else {
			System.out.println("cest faux!");
		}
		
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
		
		Message mess;
		
		Nack msg1 = new Nack(1);
		Nack msg2 = new Nack(2);
		Nack msg3 = new Nack(3);
		Nack msg4 = new Nack(4);
		Nack msg5 = new Nack(5);
		
		System.out.println("La vide est vide?  " + file.estVide());
		System.out.println("Nombre d'element dans la file:   " + file.getNbElements());
		System.out.println("______________________________________________________");
		
		System.out.println("On ajoute Message 1");
		file.ajouterElement(msg1);

		System.out.println("On ajoute Message 2");
		file.ajouterElement(msg2);
		
		System.out.println("On ajoute Message 3");
		file.ajouterElement(msg3);
		
		System.out.println("On ajoute Message 4");
		file.ajouterElement(msg4);
		
		System.out.println("On ajoute Message 5");
		file.ajouterElement(msg5);
		
		System.out.println("On enleve le premier element");
		file.enleverElement();
		
		System.out.println("On enleve le premier element");
		file.enleverElement();
		
		System.out.println("On enleve le premier element");
		file.enleverElement();
		
		System.out.println("On enleve le premier element");
		file.enleverElement();
		
		System.out.println("On enleve le premier element");
		file.enleverElement();
		
		System.out.println("On ajoute Message 2");
		file.ajouterElement(msg2);
		
		System.out.println("On ajoute Message 5");
		file.ajouterElement(msg5);
		
	}
	
	
	
	
}
