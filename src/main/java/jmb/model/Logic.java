package jmb.model;

import jmb.view.IView;

import static jmb.view.View.logic;
import static jmb.model.ConstantsLogic.*;

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
    public boolean placePawnOnPoint(int prevPoint, int prevRow, int whichPoint) {

        int whichRow = board.searchRow(whichPoint);
        boolean moved;
        if (whichRow == UNDEFINED) {
            moved = false;
        } else {
            moved = board.movePawn(prevPoint, prevRow, whichRow, whichPoint);
        }
        return moved;

    }

    @Override
    public void placePawnOnBLKExit() {
        //TODO
    }

    @Override
    public void placePawnOnWHTExit() {
        //TODO
    }

    @Override
    public void nextTurn() {
        //TODO
    }

    @Override
    public boolean checkIfMovable(int whichPoint, int whichRow) {
        // TODO non controllare se pedine in zone uscita, bypassare a false
        // Il metodo controlla che sia il turno del giocatore a cui appartiene la pedina che si intende muovere
        //      e che non ci siano pedine al di sopra di essa.
        return (board.squares[whichRow][whichPoint].getisWhite() == logic.getWhichTurn()) &&
                    ((whichRow==15) || ((whichRow < 15) && board.squares[whichRow+1][whichPoint]==null));
    }
}
