package jmb.model;

import javafx.collections.ObservableList;
import jmb.view.IView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;

public class Logic implements ILogic{

    public static IView view;

    public static BoardLogic board;

    public static LeaderboardLogic ldb;

    public static DiceLogic dice;

    @Override
    public void initializeBoardLogic() {
        board = new BoardLogic();
        dice = board.getDiceLogic();
    }

    @Override
    public void initializeLeaderboardLogic() {
        try {
            ldb = new LeaderboardLogic();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

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

    @Override
    public List<Player> getPlayerList() {
        return ldb.getList();
    }

    @Override
    public boolean isRollDouble() {
        return dice.getDoubleNum();
    }

    @Override
    public int[] getDiceValues() {
        return dice.getDiceValues();
    }

    @Override
    public void firstTurn() {
        board.runTurn();
    }

    @Override
    public List<String> getPlayerNameList() {
        return ldb.getNameList();
    }

    //TODO forse spostare corpo da un'altra parte e richiamare quella funzione da qui
    @Override
    public int compareNameLists(String newName1, String newName2) {
        List<String> nameList = this.getPlayerNameList();
        int output = DECIDING;
        if (newName1 == null || newName2 == null) {
            output = EMPTY_NAMES_ERROR;
        } else if (newName1.equals(newName2)) {
            output = SAME_NAME_ERROR;
        }
        // I nomi della lista hanno in coda il carattere di escape "\u2001"
        // Questo consente di distinguere tra un nome preso dalla lista e lo stesso nome inserito manualmente,
        //      e permette di effettuare un controllo su eventuali duplicati
        if (output == DECIDING) {
            Iterator<String> it = nameList.iterator();
            while (it.hasNext() && output == DECIDING) {
                String temp = it.next();
                if (newName1.equals(temp.substring(0, temp.length()-1))) {
                    output = NAME1_ALREADY_PRESENT;
                } else if (newName2.equals(temp.substring(0, temp.length()-1))) {
                    output = NAME2_ALREADY_PRESENT;
                }
            }
        }
        if (output == DECIDING) {
            output = SUCCESS;
        }
        return output;
    }

    @Override
    public void addNewPlayersToList (String newName1, String newName2) {
        //  Se i due nomi non contengono il carattere di escape "\u2001" in coda essi sono nuovi.
        //  Si crea quindi un nuovo oggetto Player contenente quel nome e lo si aggiunge alla PlayerList
        if (!newName1.contains("\u2001"))
            ldb.addNewPlayer(newName1);
        if (!newName2.contains("\u2001"))
            ldb.addNewPlayer(newName2);
    }
}
