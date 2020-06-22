package sample;

public class Case {
    //*****ATTRIBUTS*****//
    private int x;
    private int y;

    //*****CONSTRUCTEURS*****//
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //*****GETTER/SETTER*****//
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //*****METHODES*****//

    /**
     * Permet de tester si deux cases sont identiques
     * @param c première case à tester
     * @param c2 seconde case à tester
     * @return booleen pour donner la reponse au test
     */
    public static boolean isEqual(Case c, Case c2){
        boolean res = false;
        if ((c.getX()==c2.getX())&&(c.getY()==c2.getY())) res = true;
        return res;
    }
}
