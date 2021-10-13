package jmb.model;

public class Pawn {

    /** La classe Pawn modella la logica di una singola Pedina,
     *  includendo informazioni sul suo colore e sul suo status di bloccata
     *  per i due giocatori
     */

    //VARIABILI D'ISTANZA
    private boolean isWhite;        //Se true la pedina appartiene al bianco
    private boolean isBlockedW;     //Se true il bianco non può piazzare pedine sopra di questa
    private boolean isBlockedB;     //Se true il nero non può piazzare pedine sopra di questa
    //----------------------------------------------------

    public Pawn(boolean isBlockedW, boolean isBlockedB, boolean isWhite) {
        /* Metodo costruttore, che prende le informazioni sullo stato iniziale della pedina dalla classe che lo invoca
           e le assegna alle rispettive variabili d'istanza
         */
        this.isBlockedW = isBlockedW;
        this.isBlockedB = isBlockedB;
        this.isWhite = isWhite;

    }
    //----------------------
    //METODI GETTER E SETTER
    //----------------------
    public boolean getisWhite() {
        return isWhite;
    }
    public boolean getisBlockedW() {
        return isBlockedW;
    }

    public boolean getisBlockedB() {
        return isBlockedB;
    }

    public void setisBlockedW(boolean isBlockedW) {
        this.isBlockedW = isBlockedW;
    }

    public void setisBlockedB(boolean isBlockedB) {
        this.isBlockedB = isBlockedB;
    }

    //-------------------------------------
    //-------------------------------------

}