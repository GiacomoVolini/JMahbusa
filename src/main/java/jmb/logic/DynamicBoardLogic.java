package jmb.logic;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.logic.ConstantsLogic.*;
import static jmb.logic.Logic.*;

public abstract class DynamicBoardLogic {

    protected boolean whiteTurn = true;
    protected int[][] squares;
    protected boolean whiteExit;
    protected boolean blackExit;
    protected boolean moveOpensWhiteExit = false;
    protected boolean moveOpensBlackExit = false;
    protected int[] moveBuffer = {UNDEFINED, UNDEFINED};
    protected DiceLogic dice;
    public void checkPlayerExit (boolean exitCondition, int valueToSearch) {
        if (!exitCondition) {
            boolean found = false;
            for (int col = 7; col<=24 && !found; col++)
                for (int row = 0; row<=1 && !found; row++)
                    if (squares[row][col] == valueToSearch)
                        found = true;
            if (!found)
                if (valueToSearch == BLACK) {
                setBlackExit(true);
                Logic.getView().openBlackExit();
                moveOpensBlackExit = true;
                } else {
                    setWhiteExit(true);
                    getView().openWhiteExit();
                    moveOpensWhiteExit = true;
                }
        }
    }
    public void checkBlackExit() {
        checkPlayerExit(this.blackExit, BLACK);
    }

    public void checkWhiteExit() {
        checkPlayerExit(this.whiteExit, WHITE);
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
    public void setUpSavedBoard(int[][] squareMatrix) {
        if (squares == null)
            squares = new int[16][26];
        for (int row =0; row<16; row++)
            System.arraycopy(squareMatrix[row], 0, squares[row], 0, 26);
    }

    public boolean movePawn(int from, int to) {
        return movePawn(from, searchTopOccupiedRow(from), searchFirstFreeRow(to), to);
    }

    public boolean movePawn(int startingColumn, int startingRow, int destinationRow, int destinationColumn) {
        dice.resetToBeUsed();
        boolean possible = possibleMove(startingColumn, startingRow, destinationRow, destinationColumn);
        if(possible){
            forceMovePawn(startingColumn, destinationColumn);
            setMoveBuffer(UNDEFINED, UNDEFINED);
            if (squares[destinationRow][destinationColumn]== SELECTED_WHITE || squares[destinationRow][destinationColumn] == SELECTED_BLACK) {
                deselectPawn(destinationColumn, destinationRow);
            }
            dice.setDiceToUsed();
            getView().setDiceContrast();
            if (whiteTurn)
                this.checkWhiteExit();
            else  this.checkBlackExit();
        } else
            dice.resetToBeUsed();
        return possible;
    }
    public void forceMovePawn(int from, int to) {
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.PAWN_DROP);
        int toRow = searchFirstFreeRow(to);
        int fromRow = searchTopOccupiedRow(from);
        squares[toRow][to] = squares[fromRow][from];
        squares[fromRow][from] = EMPTY;
    }
    public boolean isPointUnlocked(int point) {
        boolean out;
        if (searchFirstFreeRow(point)<2)
            out = true;
        else out = (squares[searchTopOccupiedRow(point)][point]==WHITE)==isWhiteTurn();
        return out;
    }
    protected boolean checkDiceSimple (int delta) {
        boolean out = false;
        for (int i = 0; i<4 && !out; i++)
            if (!dice.getUsedArray()[i] && dice.getDiceValues()[i] == delta) {
                out = true;
                dice.setToBeUsed(i);
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
                if (legal)
                    for (int i = 1; i <= neededDice; i++)
                        if (!isPointUnlocked(pointFrom + (dice.getDiceValue(0)*i*sign)))
                            legal = false;
                if (legal)
                    dice.setDoublesToBeUsed(neededDice);
            }
        } else
            if (!dice.getUsed(0) && !dice.getUsed(1) && delta == dice.getDiceValue(0) + dice.getDiceValue(1)) {
                legal = isPointUnlocked(pointFrom + dice.getDiceValue(0)*sign) ||
                        isPointUnlocked(pointFrom + dice.getDiceValue(1)*sign);
                if (legal) {
                    dice.setToBeUsed(0);
                    dice.setToBeUsed(1);
                }
            }
        return legal;
    }
    public boolean possibleMove (int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++)
            if (!dice.getUsed(i))
                possible = true;
        possible = possible && puntaInizC!=puntaFinC && rightWay(puntaInizC, puntaInizR, puntaFinC) &&
                isPointUnlocked(puntaFinC) && puntaFinC <=COL_WHITE_EXIT && puntaFinC>=COL_BLACK_EXIT;
        int delta = abs(puntaFinC - puntaInizC);
        if (possible)
            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT)
                if(checkDiceSimple(delta))
                    possible = true;
                else possible = checkDiceSum(puntaInizC, delta);
            else
                if (puntaFinC<= COL_BLACK_EXIT && getBlackExit() || puntaFinC >= COL_WHITE_EXIT && getWhiteExit())
                    possible = delta <= 6 && checkExitMove(delta, puntaInizC);
                else possible= false;
        return possible;
    }
    private boolean checkExitMove(int delta, int puntaIniz) {
        boolean possible = checkExitDiceSimple(delta);
        if (!possible)
            possible = checkDiceSum(puntaIniz, delta);
        if (!possible && checkIfFarthestPawn(puntaIniz))
                possible = checkExitDiceGreaterThan(delta);
        return possible;
    }
    private boolean checkIfFarthestPawn( int puntaIniz) {
        boolean farthest = true;

        if (isWhiteTurn())
            for (int i = puntaIniz - 1; i > 18 && farthest; i--)
                if (squares[0][i] == WHITE || squares[1][i] == WHITE)
                    farthest = false;
        else for (i = puntaIniz + 1; i <=6 && farthest; i++)
                if (squares[0][i]==BLACK || squares[1][i]==BLACK)
                    farthest = false;
        return farthest;
    }
    // checkExitDiceSimple controlla che ci sia un dado che permetta di muovere la pedina esattamente nella zona di uscita
    public boolean checkExitDiceSimple(int delta) {
        boolean possible = false;
        for (int i = 0; i < 4 && !possible; i++)
            if (dice.getDiceValue(i) == delta && !dice.getUsed(i)) {
                possible = true;
                dice.getToBeUsedArray()[i] = true;
            }
        if (logic.getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        return possible;
    }
    public boolean checkExitDiceGreaterThan(int delta) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++)
            if (dice.getDiceValue(i) >= delta && !dice.getUsed(i)) {
                possible = true;
                dice.getToBeUsedArray()[i] = true;
            }
        if (logic.getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        return possible;
    }
    public int searchFirstFreeRow(int whichPoint) {
        int whichRow = UNDEFINED;
        if (squares[15][whichPoint]==EMPTY) {
            boolean found = false;
            for (int i=14; i>=0 && !found; i--)
                if (squares[i][whichPoint] !=EMPTY) {
                    found = true;
                    whichRow = i + 1;
                }
            if (!found)
                whichRow = 0;
        }
        return whichRow;
    }
    public int searchTopOccupiedRow(int whichPoint) {
        int whichRow;
        if (squares[15][whichPoint] != EMPTY)
            whichRow = 15;
        else whichRow = searchFirstFreeRow(whichPoint) - 1;
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
        int sign, endCol;
        if (whiteTurn)
            sign = 1;
        else sign = -1;
        for (int i = 0; i<4 && (!movable|| highlight); i++) {
            endCol = max (0, min(col + dice.getDiceValue(i) * sign, 25));
            if (possibleMove(col, row, searchFirstFreeRow(endCol), endCol)) {
                movable = true;
                if (highlight) {
                    if (whiteTurn)
                        getView().colorPoint(endCol, logic.getSetting("Customization","whitePawnFill",String.class), logic.getSetting("Customization","whitePawnStroke",String.class));
                    else getView().colorPoint(endCol, logic.getSetting("Customization","blackPawnFill",String.class), logic.getSetting("Customization","blackPawnStroke",String.class));
                }
            }
        }
        endCol = max(0, min(25, col + (dice.getDiceValue(0) + dice.getDiceValue(1))*sign));
        if ((!movable||highlight) && possibleMove(col, row, searchFirstFreeRow(endCol), endCol)) {
            movable = true;
            if (highlight)
                if (whiteTurn)
                    getView().colorPoint(endCol, logic.getSetting("Customization","whitePawnFill",String.class), logic.getSetting("Customization","whitePawnStroke",String.class));
                else getView().colorPoint(endCol, logic.getSetting("Customization","blackPawnFill",String.class), logic.getSetting("Customization","blackPawnStroke",String.class));
        }
        for (int i =3; i<=4 && (!movable||highlight); i++) {
            endCol = max (0, min (25, col + (i *dice.getDiceValue(0)*sign)));
            if ((!movable||highlight) && dice.getDoubleNum() && possibleMove(col, row, searchFirstFreeRow(endCol), endCol)) {
                movable = true;
                if (highlight)
                    if (whiteTurn)
                        getView().colorPoint(endCol, logic.getSetting("Customization","whitePawnFill",String.class), logic.getSetting("Customization","whitePawnStroke",String.class));
                    else getView().colorPoint(endCol, logic.getSetting("Customization","blackPawnFill",String.class), logic.getSetting("Customization","blackPawnStroke",String.class));
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
    public void setWhiteTurn(boolean value) {
        whiteTurn = value;
    }
    public boolean getGameStart() {
        return true;
    }
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
    public boolean isWhiteTurn() {
        return whiteTurn;
    }
    public DiceLogic getDice() {
        return dice;
    }

    public void runTurn() {}
}
