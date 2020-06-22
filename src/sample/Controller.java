package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.prefs.Preferences;


public class Controller {
    //*****ATTRIBUTS*****//
    @FXML
    private GridPane grille;
    @FXML
    private Button newPartie;
    private boolean partieEnCours = false;
    @FXML
    private Engine moteurJeu;
    @FXML
    private Node tempNode = null;
    @FXML
    private GridPane fond;

    //*****METHODES*****//

    /**
     * Démarre une nouvelle partie, ou intéromp celle en cours
     */
    public void newPartie(){
        if(this.partieEnCours){
            this.partieEnCours = false;
            this.newPartie.setText("Démarrer une nouvelle partie");
            this.dispSolution();
        } else {
            this.partieEnCours = true;
            this.newPartie.setText("Quitter");
            this.moteurJeu = new Engine();
            this.dispGrille();
        }
    }

    /**
     * Affiche une par une les cases de la grille à partir du moteur de jeu
     */
    public void dispGrille(){
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                Label l = getCase(new Case(i,j));
                if (moteurJeu.getCase(i,j) == 0){
                    l.setText("");
                    l.setDisable(false);
                } else{
                    l.setText("" + moteurJeu.getCase(i,j));
                    l.setDisable(true);
                }
            }
        }
    }

    /**
     * recupère le label à partir de sa case
     * @param c Case
     * @return label réponse
     */
    public Label getCase(Case c){
        for (Node box : this.grille.getChildren()) {
            for (Node label : ((GridPane)box).getChildren()) {
                Case c2 = getPosition(label);
                if (Case.isEqual(c, c2)){
                    return (Label) label;
                }
            }
        }
        return null;
    }

    /**
     * Retourne la position d'une case
     * @param c case
     * @return
     */
    public Case getPosition(Node c){
        int boxColonne = (GridPane.getColumnIndex(c) == null) ?  0 : (GridPane.getColumnIndex(c));
        int boxLigne = (GridPane.getRowIndex(c) == null) ? 0 : (GridPane.getRowIndex(c));
        int gridColonne = (GridPane.getColumnIndex(c.getParent()) == null) ?  0 : (GridPane.getColumnIndex(c.getParent()));
        int gridLigne = (GridPane.getRowIndex(c.getParent()) == null) ? 0 : (GridPane.getRowIndex(c.getParent()));
        int y = boxColonne + 3*gridColonne;
        int x = boxLigne + 3*gridLigne;
        return new Case(x,y);
    }

    /**
     * S'éxecute lorsque que le sudoku à été remplie corrrectement
     */
    public void partieGagnee(){
        this.newPartie();
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Bravo");
        dialog.setHeaderText("Partie terminée, vous avez gagné");
        dialog.show();
    }

    /**
     * S'éxecute lorsqu'il y a une errreur
     */
    public void partieErreur(){
        this.newPartie();
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Vous avez perdu");
        dialog.setHeaderText("Vous avez bien rempli la grille mais certains chiffres sont incorrects");
        dialog.show();
    }

    /**
     * Affiche la solution du sudoku
     */
    public void dispSolution() {
        for (int i=0;i<9;i++) {
            for (int j=0;j<9;j++) {
                tempNode = this.getCase(new Case(i,j));
                System.out.println(tempNode);
                if (this.moteurJeu.getGrilleReponse()[i][j]!=0) {
                    ((Label) tempNode).setText(""+this.moteurJeu.getGrilleReponse()[i][j]);
                    tempNode.setDisable(true);
                }
            }
        }
    }

    //*****METHODES RELATIVES A I/O*****//
    @FXML
    private void onMouseClicked(MouseEvent event)
    {
        Node source = (Node)event.getSource();
        tempNode = source;
    }

    private void numberPressed(int n){
        if(tempNode!=null){((Label)tempNode).setText(""+n);}
        Case c = getPosition(tempNode);
        this.moteurJeu.setCase(n, c.getX(), c.getY());
        if (moteurJeu.isRemplie()) {
            if (moteurJeu.isCorrect()) { this.partieGagnee();
            } else {this.partieErreur();}
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case DIGIT1: case NUMPAD1:
                this.numberPressed(1);
                break;
            case DIGIT2: case NUMPAD2:
                this.numberPressed(2);
                break;
            case DIGIT3: case NUMPAD3:
                this.numberPressed(3);
                break;
            case DIGIT4: case NUMPAD4:
                this.numberPressed(4);
                break;
            case DIGIT5: case NUMPAD5:
                this.numberPressed(5);
                break;
            case DIGIT6: case NUMPAD6:
                this.numberPressed(6);
                break;
            case DIGIT7: case NUMPAD7:
                this.numberPressed(7);
                break;
            case DIGIT8: case NUMPAD8:
                this.numberPressed(8);
                break;
            case DIGIT9: case NUMPAD9:
                this.numberPressed(9);
                break;
        }
    }
}
