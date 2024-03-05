package modele.communication;
/**
 * Classe de base qui définit un NoOp.
 * 
 * Le NoOp doit etre envoyer a la fin pour s'assurer que le dernier message n'est pas perdu
 * 
 * @author Dyaa Abou Arida, ETS
 * @version Hiver, 2024
 */
public class NoOp extends Message{

	public NoOp(int compte) {
		super(compte);
		// TODO Auto-generated constructor stub
	}
}
