package jmb.model;

public class PawnLogic {

    /** La classe PawnLogic modella la logica di una singola Pedina,
     *  includendo informazioni sul suo colore e sul suo status di bloccata
     *  per i due giocatori
     */

    //VARIABILI D'ISTANZA
    private boolean isWhite;        //Se true la pedina appartiene al bianco
    private boolean locksWhite;     //Se true il bianco non può piazzare pedine sopra di questa
    private boolean locksBlack;     //Se true il nero non può piazzare pedine sopra di questa
    private int whichPoint;         //Variabile che memorizza la colonna che la pedina occupa
                                    //  all'interno della matrice in BoardLogic
    private int whichRow;           //Variabile che memorizza la riga che la pedina occupa
                                    //  all'interno della matrice in BoardLogic
    //----------------------------------------------------

    public PawnLogic(boolean locksWhite, boolean locksBlack, boolean isWhite, int whichPoint, int whichRow) {
        /* Metodo costruttore, che prende le informazioni sullo stato iniziale della pedina dalla classe che lo invoca
           e le assegna alle rispettive variabili d'istanza
         */
        this.locksWhite = locksWhite;
        this.locksBlack = locksBlack;
        this.isWhite = isWhite;
        this.whichPoint = whichPoint;
        this.whichRow = whichRow;

    }
    //----------------------
    //METODI GETTER E SETTER
    //----------------------

    public boolean getisWhite() {
        return isWhite;
    }
    public boolean getLocksWhite() { return locksWhite;  }
    public boolean getLocksBlack() {
        return locksBlack;
    }
    public void setLocksWhite(boolean locksWhite) {
        this.locksWhite = locksWhite;
    }
    public void setLocksBlack(boolean locksBlack) {
        this.locksBlack = locksBlack;
    }
    public int getWhichPoint() { return whichPoint; }
    public int getWhichRow() { return whichRow; }
    public void setWhichPoint(int whichPoint) { this.whichPoint = whichPoint; }
    public void setWhichRow(int whichRow) { this.whichRow = whichRow; }

    //-------------------------------------
    //-------------------------------------

}