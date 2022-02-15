package jmb.model;

import jmb.view.IView;

import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;

public class Logic implements ILogic{

    public static IView view;

    public static BoardLogic board;

    @Override
    public void initializeBoardLogic() {
        board = new BoardLogic();
    }

    @Override
    public boolean getWhichTurn() {
        return board.isWhiteTurn();
    }

    @Override
    public void placePawnOnPoint(int whichPoint) {
        board.movePawn(board.getMoveBufferColumn(), board.getMoveBufferRow(),
                board.searchFirstFreeRow(whichPoint), whichPoint);
    }

    @Override
    public boolean isLastOnPoint (int whichPoint, int whichRow) {
        boolean last = true;
        if (whichRow != 15 && this.getBoardPlaceState(whichPoint, whichRow + 1)!=EMPTY) {
            last = false;
        }
        return last;
    }

    @Override
    public void nextTurn() {
        board.changeTurn();
    }

    @Override
    public int getBoardPlaceState (int whichPoint, int whichRow) {
        int boardPlaceState = UNDEFINED;
        if (board.squares[whichRow][whichPoint] == null) {
            boardPlaceState = EMPTY;
        } else if (board.squares[whichRow][whichPoint].getisWhite()) {
            boardPlaceState = WHITE;
        } else {
            boardPlaceState = BLACK;
        }
        return boardPlaceState;
    }

    @Override
    public void createMoveBuffer (int whichPoint) {
        board.setMoveBuffer(whichPoint, board.searchTopOccupiedRow(whichPoint));
    }
}