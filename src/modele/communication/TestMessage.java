package modele.communication;
/**
 * Classe qui sert a faire des messages tests
 * 
 * 
 * Permet de creer un objet message que l'on peut afficher etc.
 * 
 * Services offerts:
 *  - toString
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */
public class TestMessage extends Message{

	private int compte;
	
	
	public TestMessage(int compte) {
		super(compte);
		// TODO Auto-generated constructor stub
		
		
		this.compte = compte;
	}

	
	public String toString() {
		
		
		return " " + compte; 
	}
	
	
}
