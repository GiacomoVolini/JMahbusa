package jmb.model;

import javafx.collections.ObservableList;
import javafx.scene.image.WritableImage;
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
        return board.getBoardPlaceState(whichPoint, whichRow);
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

    @Override
    public int compareNameLists(String newName1, String newName2) {
        return ldb.compareNameLists(newName1, newName2);
    }

    @Override
    public void addNewPlayersToList (String newName1, String newName2) {
        //  Se i due nomi non contengono il carattere di escape "\u2001" in coda essi sono nuovi.
        //  Si crea quindi un nuovo oggetto Player contenente quel nome e lo si aggiunge alla PlayerList
        if (!newName1.contains("\u2001")) {
            ldb.addNewPlayer(newName1);
        }
        if (!newName2.contains("\u2001")) {
            ldb.addNewPlayer(newName2);
        }
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
    public int getTournamentPointsToWin() {
        return board.getTournamentPoints();
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
    public void saveGame(String saveName, WritableImage saveImage) {
        SaveGameWriter.writeSaveFile(board, saveName, saveImage);
    }

    @Override
    public List<String> getSaveList() {
        return SaveGameReader.getSaveList();
    }

    @Override
    public SaveGameReader readSaveGame(String saveName) {
        return SaveGameReader.readSaveGame(saveName);
    }

    @Override
    public String[] getLoadViewData(String saveName) {
        return this.readSaveGame(saveName).getLoadViewData();
    }

    @Override
    public int searchTopOccupiedRow(int col){return board.searchTopOccupiedRow(col);}

    @Override
    public boolean isPawnMovable(int col, int row) {
        return board.isPawnMovable(col, row);
    }

    @Override
    public void deleteSaveFile(String fileName) {
        SaveGameWriter.deleteSaveFile(fileName);
    }

    @Override
    public boolean isSaveNamePresent(String saveName) {
        return SaveGameReader.isSaveNamePresent(saveName);
    }

    @Override
    public void setUpSavedGame(String saveName) {
        board.setUpSavedGame(SaveGameReader.readSaveGame(saveName));
    }

    @Override
    public int[][] getBoardMatrix() {
        return board.squares;
    }

    @Override
    public void thePawnColor(int whichPoint, int whichRow){board.thePawnColor( whichPoint, whichRow);}

    @Override
    public boolean movePawn(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {
        return logic.movePawn(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
    }
    @Override
    public int[][] getSaveMatrix(String saveName) {return this.readSaveGame(saveName).getSquareMatrix();}

    @Override
    public boolean getGameStart() {
        return board.getGameStart();
    }
    @Override
    public boolean getGameEndState() {
        return board.getGameEndState();
    }
    @Override
    public void setGameStart(boolean value) {
        board.setGameStart(value);
    }
    @Override
    public void setGameEndState(boolean value) {
        board.setGameEndState(value);
    }
    @Override
    public boolean allDiceUsed() {
        boolean used = true;
        for (int i = 0; i<4 && used; i++)
            if (!isDiceUsed(i))
                used = false;
        return used;
    }

    @Override
    public void completeMoves() {
        /*TODO
            - Il metodo deve andare in BoardLogic e controllare se ci sono dei dadi con used == false
            - Se ci sono, il gioco deve tentare con la forza bruta di effettuare delle mosse
         */
    }
}
