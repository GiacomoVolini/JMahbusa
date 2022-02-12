package jmb.model;

import jmb.view.IView;

import static jmb.view.View.logic;
import static jmb.model.ConstantsLogic.*;
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
    public boolean isLastOnPoint (int whichPoint, int whichRow) {
        boolean last = true;
        if (whichRow != 15 && this.getBoardPlaceState(whichPoint, whichRow + 1)!=EMPTY) {
            last = true;
        }
        return last;
    }

    @Override
    public void nextTurn() {
        board.changeTurn();
    }

    @Override
    public boolean checkIfMovable(int whichPoint, int whichRow) {
        //  Il metodo controlla in base alla posizione della pedina
        //      e al giocatore di turno se essa può essere spostata o meno
        boolean movable = ((whichPoint != COL_BLACK_EXIT) || (whichPoint != COL_WHITE_EXIT)) &&
                (board.squares[whichRow][whichPoint].getisWhite() == logic.getWhichTurn()) &&
                ((whichRow == 15) || ((whichRow < 15) && (board.squares[whichRow + 1][whichPoint] == null)));

        return movable;
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
        //  FIXME
        //      DA CREARE
        //      - Il metodo vuole salvare delle informazioni relative alla posizione iniziale di un movimento
        //      - Queste informazioni verranno poi utilizzate dai metodi relativi al movimento per sapere
        //              quale pedina andare a pescare e dove rimetterla in caso di movimento non possibile
        //      - L'informazione sulla colonna è passata al metodo direttamente, sarà il view a determinarla
        //              in base alla posizione della pedina nel tabellone di gioco (quale Regione la contiene)
        //      - L'informazione sulla riga è determinata automaticamente dal logic, cercando la prima posizione
        //              non occupata "dall'alto" (scandendo da 16 a scendere)
    }
}
