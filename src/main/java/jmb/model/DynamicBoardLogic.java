package jmb.model;

import static jmb.ConstantsShared.BLACK;
import static jmb.ConstantsShared.WHITE;

public class DynamicBoardLogic {

    protected int whoCalled;
    protected void setWhoCalled(int whoCalled) {
        this.whoCalled = whoCalled;
    }

    protected int[][] squares;
    protected boolean whiteExit;              //variabile booleana per indicare se il bianco può portare fuori le sue pedine
    protected boolean blackExit;              //variabile booleana per indicare se il nero può portare fuori le sue pedine
    protected DiceLogic dice;                 //oggetto di tipo DiceLogic per la gestione del tiro dei dadi


    protected void setWhiteExit (boolean value) {
        this.whiteExit = value;
    }
    protected void setBlackExit (boolean value) {
        this.blackExit = value;
    }
    protected boolean getWhiteExit() {
        return whiteExit;
    }
    protected boolean getBlackExit() {
        return blackExit;
    }

    /** I metodi checkBlackExit e checkWhiteExit controllano rispettivamente per il nero e per il bianco che lo stato
     *  del tabellone consenta l'uscita delle pedine e aggiornano le rispettive variabili.
     *  I metodi eseguono suddetti controlli solamente se i booleani di uscita sono ancora "false",
     *  poichè una volta diventati "true" non è possibile che tornino "false".
     *  I controlli consistono nello scandire le prime due righe delle colonne intermedie della matrice squares.
     *  Non appena la scansione trova una pedina del giocatore controllato essa si ferma e non cambia lo stato di is*Exit.
     *  In caso contrario la pone a "true".
     */
    public boolean checkBlackExit() {
        boolean open = false;
        if (!this.blackExit){
            boolean found = false;
            for (int col = 7; col<=24 && !found; col++){
                for (int row = 0; row<=1 && !found; row++){
                    if (squares[row][col] == BLACK){
                        found = true;
                    }
                }
            }
            if (!found){
                open = true;
                setBlackExit(true);
                Logic.view.openBlackExit(whoCalled);
            }
        }
        return open;
    }


    public boolean checkWhiteExit() {
        boolean open = false;
        if (!this.whiteExit){
            boolean found = false;
            for (int col = 1; col<=18 && !found; col++){
                for (int row = 0; row<=1 && !found; row++){
                    if (squares[row][col] == WHITE){
                        found = true;
                    }
                }
            }
            if (!found){
                open = true;
                setWhiteExit(true);
                Logic.view.openWhiteExit(whoCalled);
            }
        }
        return open;
    }
}
