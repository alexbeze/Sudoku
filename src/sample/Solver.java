package sample;

public class Solver {
    //*****ATTRIBUTS*****/
    private Engine moteurJeu;

    ///*****CONSTRUCTEUER*****//
    public Solver(Engine moteurJeu) {
        this.moteurJeu = moteurJeu;
    }

    //*****METHODES*****//
    public Engine getMoteurJeu() {
        return this.moteurJeu;
    }

    /**
     * Algorithme de résolution du sudoku
     * @return
     */
    public boolean solve(boolean ordre) {
        //init
        int row=-1;
        int col=-1;
        boolean isEmpty = true;

        //recuperation des coordonnees de la prochaine case
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(moteurJeu.getCase(i,j)==0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty){break;}
        }
        if (isEmpty) {return true; }
        if (ordre) {
            for (int num=1;num<=9;num++) {
                if (moteurJeu.canPlace(num, row, col)){
                    moteurJeu.setCase(num, row, col);
                    if (solve(ordre)) {return true;}
                    else {moteurJeu.setCase(0, row, col); }
                }
            }
        } else {
            for (int num=9;num>=1;num--) {
                if (moteurJeu.canPlace(num, row, col)){
                    moteurJeu.setCase(num, row, col);
                    if (solve(ordre)) {return true; }
                    else { moteurJeu.setCase(0, row, col); }
                }
            }
        }
        return false;
    }
}
