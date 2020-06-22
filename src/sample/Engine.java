package sample;

public class Engine {

    //*****ATTRIBUTS*****//
    private int[][] grille;
    private int[][] grilleReponse;

    //*****CONSTRUCTEURS*****//
    public Engine() {
        this.grille = new int[9][9];
        this.grilleReponse = new int[9][9];
        this.generation();
    }

    public Engine(int[][] grille){
        this.grille = new int[9][9];
        for (int i=0;i<9;i++) {
            System.arraycopy(grille[i], 0, this.grille[i], 0, 9);
        }
    }

    //*****METHODES*****//

    /**
     * Renvoie la grille complète avec les réponses
     * @return grille résolue
     */
    public int[][] getGrilleReponse(){
        return this.grilleReponse;
    }

    /**
     * Recuperer une case à partir de ses coordonnées
     * @param r ligne
     * @param c conne
     * @return le numéro de la case
     */
    public int getCase(int r, int c){
        return grille[r][c];
    }

    /**
     * Recuperer une case résolue
     * @param r ligne
     * @param c conne
     * @return le numéro de la case résolue
     */
    public int getCaseReponse(int r, int c){
        return grilleReponse[r][c];
    }

    /**
     * Attribuer un numéro à une case
     * @param num numéro
     * @param r ligne
     * @param c connne
     */
    public void setCase(int num, int r, int c){
        this.grille[r][c] = num;
    }

    /**
     * Test si toute les cases de la grille sont remplies
     * @return booleen réponse
     */
    public boolean isRemplie(){
        boolean res = true;
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if(grille[i][j]==0){
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * Teste si la grille est correctement remplie
     * @return booleen
     */
    public boolean isCorrect() {
        boolean res = true;
        for (int i=0;i<9;i++) {
            for (int j=0;j<9;j++) {
                if(grille[i][j]!=grilleReponse[i][j]){
                    res =false;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * Génération des numéros de la grille
     */
    public void generation() {
        for (int i = 0; i <9; i+=3) {generationSousGrille(i,i);}
        this.generationAutreSousGrille(0,3);
        for(int i = 0; i < 9; i++){
            System.arraycopy(grille[i], 0, this.grilleReponse[i], 0, 9);
        }
        boolean res = true;
        int i = 50;
        while(res) {
            int c = (int) Math.floor((Math.random()*9 +1))-1;
            int r = (int) Math.floor((Math.random()*9 +1))-1;
            if (grille[r][c]!=0) {
                int value = grille[r][c];
                grille[r][c] = 0;
                if (isSolutionUnique()) {
                    i--;
                    if (i<=0) { res = false; }
                } else {
                    grille[r][c] = value;
                    res = false;
                }
            }
        }
    }

    /**
     * Génération des sous grilles
     * @param r
     * @param c
     */
    private void generationSousGrille(int r, int c) {
        int num = (int) Math.floor((Math.random()*9 +1));
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                while (isSousGrille(num, r, c)){
                    num = (int) Math.floor((Math.random()*9 +1));
                }
                grille[r+i][c+j] = num;
            }
        }
    }

    /**
     *
     * @param l
     * @param c
     * @return
     */
    private boolean generationAutreSousGrille(int l, int c) {
        if ((c>=9)&&(l<8)){
            l=l+1;
            c=0;
        }

        if((c>=9)&&(l>=9)) {
            return true;
        }

        if (l < 3) {
            if (c <3) c=3;
        } else if (l < 6) {
            if (c >=3 && c <6) c =6;
        } else {
            if (c >= 6) {
                l+= 1; c=0;
                if (l >= 9) return true;
            }
        }

        for (int num=1; num <=9; num++) {
            if (canPlace(num, l, c)){
                grille[l][c] = num;
                if (generationAutreSousGrille(l, c+1))
                    return true;
                grille[l][c] = 0;
            }
        }

        return false;
    }

    /**
     * Test si un nombre peut etre placé
     */
    public boolean canPlace(int num, int r, int c) {
        boolean res = true;
        if(isLigne(num, r) || isColonne(num, c) || isSousGrille(num, r-r%3, c-c%3)){res = false;}
        return res;
    }

    /**
     * On teste si la solution est unique
     * @return booleen reponse
     */
    public boolean isSolutionUnique() {
        Solver solver = new Solver(new Engine(grille));
        solver.solve(true);
        Engine firstSolution = solver.getMoteurJeu();
        solver = new Solver(new Engine(grille));
        solver.solve(false);
        Engine secondSolution = solver.getMoteurJeu();
        return isEqual(firstSolution, secondSolution);
    }

    /**
     * On test si la valeur est présente dans la ligne
     * @param num numéro
     * @param r lign
     * @return booleen reponse
     */
    private boolean isLigne(int num, int r) {
        boolean res = false;
        int i = 0;
        while((i<9)&&(!res)){
            if (grille[r][i]==num) {res = true;}
            i++;
        }
        return res;
    }

    /**
     * On test si la valeur est présente dans la colonne
     * @param num numéro
     * @param c colonne
     * @return booleen reponse
     */
    private boolean isColonne(int num, int c) {
        boolean res = false;
        int i = 0;
        while ((i<9)&&(!res)){
            if (grille[i][c] == num){res = true;}
            i++;
        }
        return res;
    }

    /**
     * On test si la valeur est présente dans la sous grille
     * @param num numéro
     * @param r ligne
     * @param c colonne
     * @return booleen reponse
     */
    private boolean isSousGrille(int num, int r, int c) {
        boolean res = false;
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (grille[r+i][c+j]==num) {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }


    /**
     * Teste si deux moteurs de jeu sont identidques
     * @param mj1 premier moteur de jeu
     * @param mj2 second moteur de jeu
     * @return
     */
    public static boolean isEqual(Engine mj1, Engine mj2) {
        for (int i=0;i<9;i++) {
            for (int j=0;j<9;j++) {
                if (mj1.getCase(i,j)!=mj2.getCase(i,j)){
                    return false;
                }
            }
        }
        return true;
    }
}