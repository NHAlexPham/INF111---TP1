package modele.communication;
/**
 * Classe de base qui définit un Nack.
 * 
 * Le nack doit etre en priorité sur les autres messages dans la liste des messages.
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */

public class Nack extends Message{

	public Nack(int compte) {
		super(compte);
		// TODO Auto-generated constructor stub
	}
}
