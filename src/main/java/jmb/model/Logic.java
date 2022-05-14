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
        if (board.movePawn(board.getMoveBufferColumn(), board.getMoveBufferRow(),
                board.searchFirstFreeRow(whichPoint), whichPoint)) {
            board.victoryCheck();
        }
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
                if (newName1.equals(temp.stripTrailing())) {
                    output = NAME1_ALREADY_PRESENT;
                } else if (newName2.equals(temp.stripTrailing())) {
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
        System.out.println("Provo ad aggiungere nomi");
        if (!newName1.contains("\u2001")) {
            ldb.addNewPlayer(newName1);
            System.out.println("Aggiungo nome 1");
        }
        if (!newName2.contains("\u2001")) {
            ldb.addNewPlayer(newName2);
            System.out.println("Aggiungo nome 2");
        }
        System.out.println("Ho fatto di aggiungere");
    }

    @Override
    public boolean isDiceUsed (int i) {
        return dice.getUsed(i);
    }

    @Override
    public void writeLdbList() {
        ldb.ldbWriter(ldb.path);
    }

    @Override
    public void setUpNewGame () {
        board.setUpGame();
    }

    @Override
    public void revertMove() {
        board.revertMove();
    }

    @Override
    public boolean getWhiteExit() {
        return board.getWhiteExit();
    }

    @Override
    public boolean getBlackExit() {
        return board.getBlackExit();
    }

    @Override
    public void setPlayersForGame(String whitePlayer, String blackPlayer) {
        board.setWhitePlayer(whitePlayer);
        board.setBlackPlayer(blackPlayer);
        board.setBlacksWonPoints(0);
        board.setWhitesWonPoints(0);

    }

    @Override
    public void setPlayersForGame(String whitePlayer, String blackPlayer, int tournamentPoints) {
        setPlayersForGame(whitePlayer, blackPlayer);
        board.setTournamentPoints(tournamentPoints);
    }

    @Override
    public String getWhitePlayer() {
        return board.getWhitePlayer();
    }

    @Override
    public String getBlackPlayer() {
        return board.getBlackPlayer();
    }

    @Override
    public void addStatsToLeaderboard() {
        ldb.addStatsToList(board.getWhitePlayer(), board.getBlackPlayer(), board.getWhitesWonPoints());
        ldb.addStatsToList(board.getBlackPlayer(), board.getWhitePlayer(), board.getBlacksWonPoints());
    }

    @Override
    public boolean isTournamentOngoing() {
        System.out.println(board.getTournamentPoints());
        return (board.getTournamentPoints()!=0);
    }

    @Override
    public int getWhiteTournamentPoints() {
        return board.getWhitesWonPoints();
    }

    @Override
    public int getBlackTournamentPoints() {
        return board.getBlacksWonPoints();
    }

    @Override
    public void saveData (double turnDuration, double percentRemaining) {
        //TODO
    }

    @Override
    public void setTurnDuration (double value) {
        board.setTurnDuration(value);
    }

    @Override
    public double getTurnDuration () {
        return board.getTurnDuration();
    }

    @Override
    public void setTimeRemaining (double value) {
        board.setTimeRemaining(value);
    }

    @Override
    public void saveGame(String saveName) {
        SaveGameWriter.writeSaveFile(board, saveName);
    }

}
