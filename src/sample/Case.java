package sample;

public class Case {
    //*****ATTRIBUTS*****//
    private int x;
    private int y;

    //*****CONSTRUCTEUR*****//
    public Case(){
        this.x = 0;
        this.y = 0;
    }

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //*****GETTER/SETTER*****//
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
