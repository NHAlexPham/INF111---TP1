package modele.communication;

public class testMessage extends Message{

	private int compte;
	
	
	public testMessage(int compte) {
		super(compte);
		// TODO Auto-generated constructor stub
		
		
		this.compte = compte;
	}

	
	public String toString() {
		
		
		return " " + compte; 
	}
	
	
}
