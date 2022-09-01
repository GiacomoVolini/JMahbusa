package jmb.logic;

public interface GenericBoard {

    BoardDice getDice();
    void setWhiteTurn(boolean value);
    boolean isWhiteTurn();
    boolean movePawn(int from, int to);
    int getBoardPlaceState(int whichPoint, int whichRow);
    void setMoveBuffer(int col, int row);
    void setUp();
    void setWhiteExit(boolean value);
    void setBlackExit(boolean value);
    boolean getWhiteExit();
    boolean getBlackExit();
    int searchTopOccupiedRow(int col);
    boolean isPawnMovable(int col, int row, boolean highlight);
    int[][] getSquares();
    void selectPawn(int whichPoint, int whichRow);
    void deselectPawn(int whichPoint, int whichRow);
    boolean getGameStart();
    void forceMovePawn(int from, int to);
    void setUpSavedBoard(int[][] matrix);
    int getMoveBufferColumn();
    int getMoveBufferRow();
    void runTurn();
}
