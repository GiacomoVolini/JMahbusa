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
    public boolean placePawnOnPoint(int prevPoint, int prevRow, int whichPoint) {

        int whichRow = board.searchFirstFreeRow(whichPoint);
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
        //  FIXME
        //      DA CREARE
        //      - Il metodo vuole salvare delle informazioni relative alla posizione iniziale di un movimento
        //      - Queste informazioni verranno poi utilizzate dai metodi relativi al movimento per sapere
        //              quale pedina andare a pescare e quindi muovere
        //      - L'informazione sulla colonna è passata al metodo direttamente, sarà il view a determinarla
        //              in base alla posizione della pedina nel tabellone di gioco (quale Regione la contiene)
        //      - L'informazione sulla riga è determinata automaticamente dal logic, cercando la prima posizione
        //              occupata "dall'alto" (scandendo da 16 a scendere)
        board.setMoveBuffer(whichPoint, board.searchTopOccupiedRow(whichPoint));
    }
}
