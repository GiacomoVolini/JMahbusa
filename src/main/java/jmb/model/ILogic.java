package jmb.model;

import javafx.collections.ObservableList;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;

public interface ILogic {

    //void placePawnOnTopPoints (int prevRegion, int prevPoint, int whichPoint);

    //void placePawnOnBotPoints (int whichPoint);

    void placePawnOnPoint (int whichPoint);

    boolean getWhichTurn();

    void nextTurn ();

    void initializeBoardLogic ();

    void initializeLeaderboardLogic();

    int getBoardPlaceState (int whichPoint, int whichRow);
    //  Metodo che restituisce tre valori a seconda dello stato della casella corrispondente di BoardLogic
    //      Restituisce EMPTY (0) se la casella non contiene alcuna pedina
    //                  WHITE (1) se la casella contiene una pedina del bianco
    //                  BLACK (2) se la casella contiene una pedina del nero

    boolean isLastOnPoint (int whichPoint, int whichRow);
    //  Metodo che restituisce true se non ci sono altre pedine al di sopra di essa in quella punta

    void createMoveBuffer (int whichPoint);

    List<Player> getPlayerList ();

    boolean isRollDouble();

    int[] getDiceValues();

    void firstTurn();

    List<String> getPlayerNameList();

    int compareNameLists(String newName1, String newName2);

    void addNewPlayersToList (String newName1, String newName2);

    boolean isDiceUsed (int i);

    void writeLdbList();

    void addStatsToLeaderboard();

    void setUpNewGame ();

    void revertMove();

    boolean getBlackExit();

    boolean getWhiteExit();

    void setPlayersForGame(String whitePlayer, String blackPlayer);

    void setPlayersForGame(String whitePlayer, String blackPlayer, int tournamentPoints);

    String getWhitePlayer();

    String getBlackPlayer();

    boolean isTournamentOngoing();

    int getWhiteTournamentPoints();

    int getBlackTournamentPoints();

    void setTurnDuration(double value);

    double getTurnDuration();

    void setTimeRemaining (double value);

    void saveGame(String saveName, WritableImage saveImage);

    BoardLogic getBoard();

    List<String> getSaveList();

    SaveGameReader readSaveGame (String saveName);

    String[] getLoadViewData(String saveName);

    byte[] getImageBytes(String saveName);

    long[] getImageDimensions(String saveName);

    int searchTopOccupiedRow(int col);


}
