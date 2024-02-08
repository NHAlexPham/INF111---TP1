package utilitaires;
/**
 * Classe de base qui définit un vecteur 2D.
 * 
 *
 * Services offerts:
 *  - Message
 *  - getTempsEnvoi
 *  - setTempsEnvoi
 *  - getCompte
 * 
 * @author Dyaa , ETS
 * @version hiver, 2024
 */
public class Vect2D {

	private double longueurX; 
	private double longueurY;

	/*
	 * constructeur du'un vecteur sans parametres
	 */
	public Vect2D() {
		
	}
	
	/*
	 * @param longueurX,
	 * @param longueurY
	 * constructeur d'un vecteur avec une longueur X et Y
	 */
	public Vect2D(double longueurX, double longueurY) {
		
		this.longueurX = longueurX;
		this.longueurY = longueurY;
		
	}
	
	/*
	 * @param vect2D
	 * constructeur d'un vecteur qui copie un autre vecteur 
	 */
	public Vect2D(Vect2D vect2D) {
		
		longueurX = vect2D.getLongueurX();
		longueurY = vect2D.getLongueurY();
	}
	
	/*
	 * retourne la longueur X du vecteur
	 */
	public double getLongueurX() {
		return longueurX;
	}
	
	/*
	 * retourne la longueur Y du vecteur
	 */
	public double getLongueurY() {
		return longueurY;
	}
	
	/*
	 * @param longueurX
	 * set la longueur x du vecteur
	 */
	public void setLongueurX(double longueurX) {
		this.longueurX = longueurX;
	}
	
	/*
	 * @param longueurY
	 * set la longueur y du vecteur
	 */
	public void setLongueurY(double longueurY) {
		this.longueurY = longueurY;
	}
	
	/*
	 * methode qui retourne la longueur totale du vecteur
	 */
	public double getLongueur() {
		double longueur = 0;
		
		longueur = Math.sqrt(Math.pow(longueurX, 2) + Math.pow(longueurY, 2));
		
		return longueur;
	}
	
	/*
	 * methode qui retourne l'angle du vecteur
	 */
	public double getAngle() {
		double angle = 0;
		
		angle = Math.atan2(longueurY,longueurX);
		
		return angle;
	}
	
	/*
	 * @param posFin, vecteur dont on veut calculer la difference avec
	 * methode qui retourne un vecteur dont on a fait la difference avec un autre vecteur
	 */
	public Vect2D calculerDiff(Vect2D posFin) {
		
		double longueurX = 0;
		double longueurY = 0;
		
		longueurX = posFin.getLongueurX() - this.longueurX;
		longueurY = posFin.getLongueurY() - this.longueurY;
		
		Vect2D vectDiff = new Vect2D(longueurX, longueurY);
		
		return vectDiff;
	}
	
	/*
	 * @param a, nombre scalaire 
	 * methode qui divise la longueur en x et y du vecteur
	 */
	public void diviser(double a) {
		
		longueurX = longueurX / a;
		longueurY = longueurY / a;
		
	}
	
	/*
	 * @param x, valeur a ajouter a la longueur x
	 * @param y, valeur a ajouter a la longueur y
	 * methode qui permet d'ajouter un nombre a la longueur x et y
	 */
	public void ajouter(double x, double y) {
		
		longueurX = longueurX + x;
		longueurY = longueurY + y;
	}
	
	/*
	 * methode to string qui permet de retourner l'objet en string
	 */
	@Override
	public String toString() {
		
		return "Vecteur-------------- \n" + "Longueur X : " + longueurX + "\n" + "Longueur Y : " + longueurY;
	}
	
	/*
	 * @param obj, l'objet qui sert a la comparaison
	 * methode qui retourne si les 2 vecteurs sont egaux ou non
	 */
	@Override
	public boolean equals(Object obj){
		
		boolean egaux = false;
		Vect2D vect2d = new Vect2D();
		obj = vect2d;
		
		if(vect2d.getLongueurX() == longueurX && vect2d.getLongueurY() == longueurY) {
			egaux = true;
		}
		
		return egaux;
	}
	
}
