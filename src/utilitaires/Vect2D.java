package utilitaires;

public class Vect2D {
    // Attributs
    private double longueurX;
    private double longueurY;

    // Constructeurs

    /**
     *
     */
    public Vect2D() {
        this.longueurX = 0;
        this.longueurY = 0;
    }

    /**
     *
     * @param longueurX
     * @param longueurY
     */
    public Vect2D(double longueurX, double longueurY) {
        this.longueurX = longueurX;
        this.longueurY = longueurY;
    }

    /**
     *
     * @param vector
     */
    public Vect2D(Vect2D vector) {
        this.longueurX = vector.longueurX;
        this.longueurY = vector.longueurY;
    }

    /**
     *
     * @return
     */
    public double getLongueurX() {
        return longueurX;
    }

    /**
     *
     * @return
     */
    public double getLongueurY() {
        return longueurY;
    }

    /**
     *
     * @return
     */
    public double getLongueur() {
        double longueur = 0;
        longueur = Math.sqrt((Math.pow(this.longueurX, 2)) +
                Math.pow(this.longueurY, 2));
        return longueur;
    }

    /**
     *
     * @return
     */
    public double getAngle() {
        double angle = 0;
        angle = Math.atan2(this.longueurY, this.longueurX);
        return angle;
    }

    /**
     *
     * @param posFin
     * @return
     */
    public Vect2D calculerDiff(Vect2D posFin) {
        Vect2D vect2 = new Vect2D();
        vect2.longueurX = posFin.longueurX - this.longueurX;
        vect2.longueurY = posFin.longueurY - this.longueurY;
        return vect2;
    }

    /**
     *
     * @param a
     */
    public void diviser(double a) {
        if (a != 0) {
            this.longueurX = this.longueurX / a;
            this.longueurY = this.longueurY / a;
        } else {
            System.out.println("Le scalaire est 0, division impossible !\n");
        }
    }

    /**
     *
     * @param x
     * @param y
     */
    public void ajouter(double x, double y) {
        this.longueurX = this.longueurX + x;
        this.longueurY = this.longueurY + y;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vect2D vect2D = (Vect2D) object;
        return Double.compare(longueurX, vect2D.longueurX) == 0 && Double.compare(longueurY, vect2D.longueurY) == 0;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Vecteur : " +
                "\nlongueur X= " + longueurX +
                "\nlongueur Y= " + longueurY;
    }
}
