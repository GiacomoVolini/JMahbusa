package jmb.logic;

import javafx.scene.paint.Color;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.logic.ConstantsLogic.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class DynamicBoardLogic implements GenericBoard{

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
    protected boolean moveOpensWhiteExit = false;
    protected boolean moveOpensBlackExit = false;
    protected int[] moveBuffer = {UNDEFINED, UNDEFINED};    //array di interi che memorizza la posizione di partenza nella matrice squares di una pedina
    //mentre si sta per effettuare una mossa
    //nella posizione 0 si memorizza la colonna, nella posizione 1 la riga
    protected BoardDice dice;                 //oggetto di tipo DiceLogic per la gestione del tiro dei dadi
    public void setWhiteExit (boolean value) {
        this.whiteExit = value;
    }
    public void setBlackExit (boolean value) {
        this.blackExit = value;
    }
    public boolean getWhiteExit() {
        return whiteExit;
    }
    public boolean getBlackExit() {
        return blackExit;
    }
    public int[][] getSquares() {
            return squares;
    }
    public BoardDice getDice() {
        return dice;
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
                Logic.view.openBlackExit();
                if (whoCalled == GAME_CALLED)
                    moveOpensBlackExit = true;
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
                view.openWhiteExit();
                if (whoCalled == GAME_CALLED)
                    moveOpensWhiteExit = true;
            }
        }
        return open;
    }

    public void setUp() {
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
            view.playSFX(PAWN_SFX);
            //  Se la mossa è effettuabile sposta la pedina nella nuova posizione
            squares[puntaFinR][puntaFinC]= squares[puntaInizR][puntaInizC];
            squares[puntaInizR][puntaInizC]= EMPTY;
            dice.setDiceToUsed();
            view.setDiceContrast();
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
    public boolean isPointUnlocked(int point) {
        boolean out;
        if (searchFirstFreeRow(point)<2)
            out = true;
        else {
            out = (squares[searchTopOccupiedRow(point)][point]==WHITE)==isWhiteTurn();
        }
        return out;
    }
    protected boolean checkDiceSimple (int delta) {
        boolean out = false;
        for (int i = 0; i<4 && !out; i++) {
            if (!dice.getUsedArray()[i] && dice.getDiceValues()[i] == delta) {
                out = true;
                dice.setToBeUsed(i);
            }
        }
        if (logic.getSetting("DEBUG","bypassDice",boolean.class))
            out = true;
        return out;
    }
    protected boolean checkDiceSum (int pointFrom, int delta) {
        boolean legal = false;
        int sign;
        if (isWhiteTurn())
            sign = 1;
        else sign = -1;
        if (dice.getDoubleNum()) {
            int neededDice = 0;
            legal = delta % dice.getDiceValue(0) == 0;
            if (legal) {
                legal = false;
                for (int i = 0; i < 4 && !legal; i++) {
                    if (!dice.getUsed(i))
                        neededDice++;
                    if (dice.getDiceValue(0) * neededDice == delta)
                        legal = true;
                }
                if (legal) {
                    for (int i = 1; i <= neededDice; i++)
                        if (!isPointUnlocked(pointFrom + (dice.getDiceValue(0)*i*sign))) {
                            legal = false;
                        }
                }
                if (legal)
                    dice.setDoublesToBeUsed(neededDice);
            }
        } else {
            if (!dice.getUsed(0) && !dice.getUsed(1) && delta == dice.getDiceValue(0) + dice.getDiceValue(1)) {
                legal = isPointUnlocked(pointFrom + dice.getDiceValue(0)*sign) ||
                        isPointUnlocked(pointFrom + dice.getDiceValue(1)*sign);
                if (legal) {
                    dice.setToBeUsed(0);
                    dice.setToBeUsed(1);
                }
            }
        }
        return legal;
    }
    public boolean possibleMove (int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++) {
            if (!dice.getUsed(i)) {
                possible = true;
            }
        }
        possible = possible && puntaInizC!=puntaFinC && rightWay(puntaInizC, puntaInizR, puntaFinC) &&
                isPointUnlocked(puntaFinC) && puntaFinC <=COL_WHITE_EXIT && puntaFinC>=COL_BLACK_EXIT;
        int delta = abs(puntaFinC - puntaInizC);
        if (possible) {
            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT) {
                if(checkDiceSimple(delta)) {
                    possible = true;
                } else {
                   possible = checkDiceSum(puntaInizC, delta);
                }
            }
            else {
                if (puntaFinC<= COL_BLACK_EXIT && getBlackExit() || puntaFinC >= COL_WHITE_EXIT && getWhiteExit()) {
                    possible = delta <= 6 && checkExitMove(delta, puntaInizC);
                } else possible= false;
            }
        }
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
        boolean right = (puntaInizR!= UNDEFINED && (squares[puntaInizR][puntaInizC] == WHITE || squares[puntaInizR][puntaInizC] == SELECTED_WHITE)
                && (puntaFinC > puntaInizC)) ||
                ((squares[puntaInizR][puntaInizC] == BLACK || squares[puntaInizR][puntaInizC] == SELECTED_BLACK) && (puntaFinC < puntaInizC));
        return right;
    }
    public boolean isPawnMovable (int col, int row, boolean highlight) {
        boolean movable = false;
        boolean whiteTurn = isWhiteTurn();
        int sign;
        int endCol;
        if (whiteTurn)
            sign = 1;
        else sign = -1;
        for (int i = 0; i<4 && (!movable|| highlight); i++) {
            endCol = max (0, min(col + dice.getDiceValue(i) * sign, 25));
            if (possibleMove(col, row, searchFirstFreeRow(endCol), endCol)) {
                movable = true;
                if (highlight) {
                    if (whiteTurn)
                        view.colorPoint(endCol, logic.getSetting("Customization","whitePawnFill",String.class), logic.getSetting("Customization","whitePawnStroke",String.class));
                    else view.colorPoint(endCol, logic.getSetting("Customization","blackPawnFill",String.class), logic.getSetting("Customization","blackPawnStroke",String.class));
                }
            }
        }
        endCol = max(0, min(25, col + (dice.getDiceValue(0) + dice.getDiceValue(1))*sign));
        if ((!movable||highlight) && possibleMove(col, row, searchFirstFreeRow(endCol), endCol)) {
            movable = true;
            if (highlight)
                if (whiteTurn)
                    view.colorPoint(endCol, logic.getSetting("Customization","whitePawnFill",String.class), logic.getSetting("Customization","whitePawnStroke",String.class));
                else view.colorPoint(endCol, logic.getSetting("Customization","blackPawnFill",String.class), logic.getSetting("Customization","blackPawnStroke",String.class));
        }
        for (int i =3; i<=4 && (!movable||highlight); i++) {
            endCol = max (0, min (25, col + (i *dice.getDiceValue(0)*sign)));
            if ((!movable||highlight) && dice.getDoubleNum() && possibleMove(col, row, searchFirstFreeRow(endCol), endCol)) {
                movable = true;
                if (highlight)
                    if (whiteTurn)
                        view.colorPoint(endCol, logic.getSetting("Customization","whitePawnFill",String.class), logic.getSetting("Customization","whitePawnStroke",String.class));
                    else view.colorPoint(endCol, logic.getSetting("Customization","blackPawnFill",String.class), logic.getSetting("Customization","blackPawnStroke",String.class));
            }
        }
        return movable;
    }
    public void selectPawn(int col, int row){
        squares[row][col] += SELECTED;
    }
    public void deselectPawn(int col, int row) {
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
    public int getBoardPlaceState (int whichPoint, int whichRow) {
        return squares[whichRow][whichPoint];
    }
    public void forceMovePawn(int from, int to) {
        view.playSFX(PAWN_SFX);
        int toRow = searchFirstFreeRow(to);
        int fromRow = searchTopOccupiedRow(from);
        squares[toRow][to] = squares[fromRow][from];
        squares[fromRow][from] = EMPTY;
    }
    public void setWhiteTurn(boolean value) {
        whiteTurn = value;
    }

    public void setUpSavedBoard(int[][] squareMatrix) {
        if (squares == null) {
            squares = new int[16][26];
        }
        for (int row =0; row<16; row++) {
            for (int col = 0; col < 26; col++) {
                squares[row][col] = squareMatrix[row][col];
            }
        }
    }
    public boolean getGameStart() {
        return true;
    }

    public void runTurn() {}
}
