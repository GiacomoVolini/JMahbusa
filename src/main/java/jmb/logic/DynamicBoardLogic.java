package jmb.logic;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.*;

public abstract class DynamicBoardLogic {

    protected boolean whiteTurn = true;
    protected int[][] squares;
    protected boolean whiteExit, blackExit;
    protected boolean moveOpensWhiteExit = false;
    protected boolean moveOpensBlackExit = false;
    protected int[] moveBuffer = {UNDEFINED, UNDEFINED};
    private static final int SELECTED = 2;
    private static final int DESELECTED = -2;
    protected DiceLogic dice;

    public void checkPlayerExit (boolean exitCondition, int valueToSearch, int startingPoint, int finalPoint) {
        if (!exitCondition) {
            boolean found = false;
            for (int col = startingPoint; col<=finalPoint && !found; col++)
                for (int row = 0; row<=1 && !found; row++)
                    if (squares[row][col] == valueToSearch)
                        found = true;
            if (!found)
                if (valueToSearch == BLACK) {
                    setBlackExit(true);
                    getView().openBlackExit();
                    moveOpensBlackExit = true;
                } else {
                    setWhiteExit(true);
                    getView().openWhiteExit();
                    moveOpensWhiteExit = true;
                }
        }
    }
    public void checkBlackExit() {
        checkPlayerExit(this.blackExit, BLACK, 7, 24);
    }

    public void checkWhiteExit() {
        checkPlayerExit(this.whiteExit, WHITE, 1, 18);
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
        boolean possible = possibleMove(startingColumn, startingRow, destinationColumn);
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
        boolean out = searchFirstFreeRow(point) < 2;
        if (!out)
            out = (squares[searchTopOccupiedRow(point)][point]==WHITE)==isWhiteTurn();
        return out;
    }

    public boolean possibleMove (int startingPoint, int startingRow, int finalPoint) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++)
            if (!dice.getUsed(i))
                possible = true;
        possible = possible && startingPoint != finalPoint && rightPlayer(startingRow, startingPoint) &&
                rightWay(startingPoint, finalPoint) && isPointUnlocked(finalPoint) && finalPoint <=COL_WHITE_EXIT &&
                finalPoint >=COL_BLACK_EXIT;
        int delta = abs(finalPoint - startingPoint);
        if (possible)
            if (COL_BLACK_EXIT < finalPoint && finalPoint < COL_WHITE_EXIT)
                possible = dice.checkDiceSimple(delta) || dice.checkDiceSum(startingPoint, delta, this);
            else if (finalPoint == COL_BLACK_EXIT && getBlackExit() || finalPoint == COL_WHITE_EXIT && getWhiteExit())
                possible = delta <= 6 && checkExitMove(delta, startingPoint);
        else possible= false;
        return possible;
    }
    private boolean rightPlayer(int startingRow, int startingPoint) {
        int square = squares[startingRow][startingPoint];
        return isWhiteTurn() && (square == WHITE || square == SELECTED_WHITE) ||
                !isWhiteTurn() && (square == BLACK || square == SELECTED_BLACK);
    }

    private boolean checkExitMove(int delta, int startingPoint) {
        boolean possible = dice.checkDiceSimple(delta) || dice.checkDiceSum(startingPoint, delta, this)
                || (checkIfFarthestPawn(startingPoint) && dice.checkExitDiceGreaterThan(delta));
        return possible;
    }
    private boolean checkIfFarthestPawn( int startingPoint) {
        boolean farthest = true;
        if (isWhiteTurn()) {
            for (int i = startingPoint - 1; i > 18 && farthest; i--)
                if (squares[0][i] == WHITE || squares[1][i] == WHITE)
                    farthest = false;
        } else for (int i = startingPoint + 1; i <=6 && farthest; i++)
                if (squares[0][i]==BLACK || squares[1][i]==BLACK)
                    farthest = false;
        return farthest;
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
    public boolean rightWay(int startingPoint, int finalPoint) {
        boolean right = (isWhiteTurn() && (finalPoint > startingPoint)) || (!isWhiteTurn() && (finalPoint < startingPoint));
        return right;
    }
    public boolean isPawnMovable (int col, int row, boolean highlight) {
        boolean movable = false;
        int sign, endCol;
        if (whiteTurn)
            sign = 1;
        else sign = -1;
        for (int i = 0; i<4 && (!movable|| highlight); i++) {
            endCol = max (0, min(col + dice.getDiceValue(i) * sign, 25));
            if (possibleMove(col, row, endCol)) {
                movable = true;
                if (highlight) {
                    if (whiteTurn)
                        getView().colorPoint(endCol, getLogic().getSetting("Customization","whitePawnFill",String.class),
                                getLogic().getSetting("Customization","whitePawnStroke",String.class));
                    else getView().colorPoint(endCol, getLogic().getSetting("Customization","blackPawnFill",String.class),
                            getLogic().getSetting("Customization","blackPawnStroke",String.class));
                }
            }
        }
        endCol = max(0, min(25, col + (dice.getDiceValue(0) + dice.getDiceValue(1))*sign));
        if ((!movable||highlight) && possibleMove(col, row, endCol)) {
            movable = true;
            if (highlight)
                if (whiteTurn)
                    getView().colorPoint(endCol, getLogic().getSetting("Customization","whitePawnFill",String.class),
                            getLogic().getSetting("Customization","whitePawnStroke",String.class));
                else getView().colorPoint(endCol, getLogic().getSetting("Customization","blackPawnFill",String.class),
                        getLogic().getSetting("Customization","blackPawnStroke",String.class));
        }
        for (int i =3; i<=4 && (!movable||highlight); i++) {
            endCol = max (0, min (25, col + (i *dice.getDiceValue(0)*sign)));
            if ((!movable||highlight) && dice.getDoubleNum() && possibleMove(col, row, endCol)) {
                movable = true;
                if (highlight)
                    if (whiteTurn)
                        getView().colorPoint(endCol, getLogic().getSetting("Customization","whitePawnFill",String.class),
                                getLogic().getSetting("Customization","whitePawnStroke",String.class));
                    else getView().colorPoint(endCol, getLogic().getSetting("Customization","blackPawnFill",String.class),
                            getLogic().getSetting("Customization","blackPawnStroke",String.class));
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
