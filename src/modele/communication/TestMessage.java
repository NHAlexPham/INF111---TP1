package modele.communication;

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
