package jmb.model;

public class PawnLogic {

    /** La classe PawnLogic modella la logica di una singola Pedina,
     *  includendo informazioni sul suo colore e sul suo status di bloccata
     *  per i due giocatori
     */

    /*TODO FORSE PAWNLOGIC E' INUTILE
        - TRASFORMARE SQUARES DENTRO BOARDLOGIC IN UNA MATRICE DI INTERI PARAGONABILE A QUELLA DEL SALVATAGGIO
        - CAMBIARE I CONTROLLI SU LOCKSWHITE E LOCKSBLACK IN CONTROLLI RELATIVI AI NUMERI DELlA MATRICE
        - SEMPLIFICARE I CONTROLLI SU GAMEBOARDREDRAW
     */
/*
    //VARIABILI D'ISTANZA
    private boolean isWhite;        //Se true la pedina appartiene al bianco
    private boolean locksWhite;     //Se true il bianco non può piazzare pedine sopra di questa
    private boolean locksBlack;     //Se true il nero non può piazzare pedine sopra di questa
    //----------------------------------------------------

    public PawnLogic(boolean locksWhite, boolean locksBlack, boolean isWhite) {

        this.locksWhite = locksWhite;
        this.locksBlack = locksBlack;
        this.isWhite = isWhite;
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

    //-------------------------------------
    //-------------------------------------

    protected static PawnLogic newWhitePawn() {
        return new PawnLogic(false, true, true);
    }

    protected static PawnLogic newBlackPawn() {
        return new PawnLogic(true, false, false);
    }

    */

}