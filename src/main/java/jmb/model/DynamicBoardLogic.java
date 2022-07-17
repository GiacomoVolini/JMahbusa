package jmb.model;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.ConstantsShared.COL_BLACK;
import static jmb.model.ConstantsLogic.DESELECTED;
import static jmb.model.ConstantsLogic.SELECTED;
import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class DynamicBoardLogic {

    protected int whoCalled;
    protected void setWhoCalled(int whoCalled) {
        this.whoCalled = whoCalled;
    }
    protected boolean whiteTurn = true;              //variabile booleana per indicare il giocatore di turno. Se true è il turno del bianco
    public boolean isWhiteTurn() {
        return whiteTurn;
    }
    protected int[][] squares;
    protected boolean whiteExit;              //variabile booleana per indicare se il bianco può portare fuori le sue pedine
    protected boolean blackExit;              //variabile booleana per indicare se il nero può portare fuori le sue pedine
    protected int[] moveBuffer = {UNDEFINED, UNDEFINED};    //array di interi che memorizza la posizione di partenza nella matrice squares di una pedina
    //mentre si sta per effettuare una mossa
    //nella posizione 0 si memorizza la colonna, nella posizione 1 la riga
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
    protected int[][] getSquares() {
            return squares;
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
                if (whoCalled == GAME_CALLED)
                    logic.moveOpensBlackExit();
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
                if (whoCalled == GAME_CALLED)
                    logic.moveOpensWhiteExit();
            }
        }
        return open;
    }

    protected void setUp() {
        setBlackExit(false);
        setWhiteExit(false);

        squares = new int[16][26];
        for (int i=0; i<=14;i++){
            squares[i][COL_WHITE]= WHITE;
            squares[i][COL_BLACK]= BLACK;
        }
    }

    public boolean movePawn(int from, int to) {
        return movePawn(from, searchTopOccupiedRow(from), searchFirstFreeRow(to), to);
    }

    public boolean movePawn(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {

        dice.resetToBeUsed();
        //  Si richiama il metodo possibleMove per controllare che la mossa sia effettuabile
        boolean possible = possibleMove(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
        if(possible){
            view.playPawnSFX();
            //  Se la mossa è effettuabile sposta la pedina nella nuova posizione
            squares[puntaFinR][puntaFinC]= squares[puntaInizR][puntaInizC];
            squares[puntaInizR][puntaInizC]= EMPTY;
            dice.setUsed();
            view.setDiceContrast(whoCalled);
            //  Controlla se il giocatore di turno può far uscire le proprie
            //  pedine e aggiorna di conseguenza la variabile relativa
            if (whiteTurn) {
                this.checkWhiteExit();
            } else {
                this.checkBlackExit();
            }
        } else {
            dice.resetToBeUsed();
        }
        return possible;
    }
    public boolean possibleMove(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){

        boolean possible;

        possible = (puntaInizC!=puntaFinC);

        //  Si controlla che la mossa sia effettuata nel verso giusto
        if (rightWay(puntaInizC, puntaInizR, puntaFinC) && possible) {

            //  Se true, controlla se la mossa è volta a portare fuori la pedina
            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT) {

                //  Se la mossa non fa uscire dal gioco una pedina controlla che i dadi la permettano, poi che
                //  la posizione di arrivo non sia bloccata per il giocatore di turno
                if (dice.checkDice( abs(puntaFinC - puntaInizC))) {
                    if (whiteTurn) {
                        if (puntaFinR > 1 && squares[puntaFinR - 1][puntaFinC] == BLACK) {
                            //controlla che la posizione di arrivo non sia preclusa al bianco
                            possible = false;
                        }
                    } else if (puntaFinR > 1 && squares[puntaFinR - 1][puntaFinC] == WHITE) {
                        //controlla che la posizione di arrivo non sia preclusa al nero
                        possible = false;
                    }
                } else possible = false;
            }   //  Se la mossa fa uscire dal gioco la pedina controlla che al giocatore ciò sia permesso
            else {
                int delta = abs(puntaFinC - puntaInizC);
                possible = delta <= 6 && checkExitMove(delta, puntaInizC);
            }
        } else possible = false;

        return possible;

    }

    private boolean checkExitMove(int delta, int puntaIniz) {

        boolean possible = dice.checkExitDiceSimple(delta);
        if (!possible) {
            if (checkIfFarthestPawn(puntaIniz))
                possible = dice.checkExitDiceGreaterThan(delta);
        }

        return possible;
    }

    private boolean checkIfFarthestPawn( int puntaIniz) {
        boolean farthest = true;

        if (isWhiteTurn()) {
            for (int i = puntaIniz - 1; i>18 && farthest; i--) {
                if (squares[0][i]== WHITE || squares[1][i]==WHITE) {
                    farthest = false;
                }
            }
        } else {
            for (int i = puntaIniz + 1; i <=6 && farthest; i++) {
                if (squares[0][i]==BLACK || squares[1][i]==BLACK) {
                    farthest = false;
                }
            }
        }
        return farthest;
    }

    public int searchFirstFreeRow(int whichPoint) {
        // Data una colonna della matrice cerca la prima riga libera e la restituisce
        int whichRow = UNDEFINED;
        if (squares[15][whichPoint]==EMPTY) {
            boolean found = false;
            for (int i=14; i>=0 && !found; i--) {
                if (squares[i][whichPoint] !=EMPTY) {
                    found = true;
                    whichRow = i + 1;
                }
            }
            if (!found) {
                whichRow = 0;
            }
        }
        return whichRow;
    }

    public int searchTopOccupiedRow(int whichPoint) {
        int whichRow;
        if (squares[15][whichPoint] != EMPTY) {
            whichRow = 15;
        } else {
            whichRow = searchFirstFreeRow(whichPoint) - 1;
        }
        return whichRow;
    }
    public boolean rightWay(int puntaInizC, int puntaInizR, int puntaFinC) {
        System.out.println(puntaInizC + "\n" + puntaInizR + "\n" + puntaFinC);
        boolean right = (squares[puntaInizR][puntaInizC] == WHITE && (puntaFinC > puntaInizC)) ||
                (squares[puntaInizR][puntaInizC] == BLACK && (puntaFinC < puntaInizC));
        return right;
    }
    protected boolean isPawnMovable (int col, int row) {
        boolean movable = false;
        int sign;
        int endCol;
        if (isWhiteTurn())
            sign = 1;
        else sign = -1;
        for (int i = 0; i<4 && !movable; i++) {
            endCol = max (0, min(col + dice.getDiceValue(i) * sign, 25));
            if (possibleMove(col, row, searchFirstFreeRow(endCol), endCol))
                movable = true;
        }
        endCol = max(0, min(25, col + (dice.getDiceValue(0) + dice.getDiceValue(1))*sign));
        if (!movable && possibleMove(col, row, searchFirstFreeRow(endCol), endCol))
            movable = true;
        for (int i =3; i<=4 && !movable; i++) {
            endCol = max (0, min (25, col + (i *dice.getDiceValue(0)*sign)));
            if (!movable && dice.getDoubleNum() && possibleMove(col, row, searchFirstFreeRow(endCol), endCol))
                movable = true;
        }
        return movable;
    }
    protected void selectPawn(int col, int row){
        squares[row][col] += SELECTED;
    }
    protected void deselectPawn(int col, int row) {
        squares[row][col] += DESELECTED;
    }
    public void setMoveBuffer (int col, int row) {
        this.moveBuffer[0] = col;
        this.moveBuffer[1] = row;
    }
    public int getMoveBufferColumn () {
        return this.moveBuffer[0];
    }

    public int getMoveBufferRow () {
        return this.moveBuffer[1];
    }
    protected int getBoardPlaceState (int whichPoint, int whichRow) {
        return squares[whichRow][whichPoint];
    }
    protected void forceMovePawn(int from, int to) {
        view.playPawnSFX();
        int toRow = searchFirstFreeRow(to);
        int fromRow = searchTopOccupiedRow(from);
        squares[toRow][to] = squares[fromRow][from];
        squares[fromRow][from] = EMPTY;
    }
    protected void setWhiteTurn(boolean value) {
        whiteTurn = value;
    }

    protected void setUpSavedBoard(int[][] squareMatrix) {
        if (squares == null) {
            squares = new int[16][26];
        }
        for (int row =0; row<16; row++) {
            for (int col = 0; col < 26; col++) {
                squares[row][col] = squareMatrix[row][col];
            }
        }
    }


    public DiceLogic getDiceLogic () { return this.dice; }
}
