package jmb.logic;

import java.util.List;

public interface ILogic {

    String getAppDirectory();
    void placePawnOnPoint (int whichPoint);
    boolean getWhichTurn();
    void nextTurn();
    void initializeBoardLogic();
    void initializeLeaderboardLogic();
    void initializeTutorialLogic();
    void initializeProgramLogic();
    void initializeStringsReader();
    int getBoardPlaceState (int whichPoint, int whichRow);
    boolean isLastOnPoint (int whichPoint, int whichRow);
    void createMoveBuffer (int whichPoint);
    List<Player> getPlayerList ();
    boolean isRollDouble();
    int[] getDiceValues();
    void firstTurn();
    List<String> getPlayerNameList();
    int compareNameLists(String newName1, String newName2);
    void addNewPlayersToList (String newName1, String newName2);
    boolean isDiceUsed (int i);
    void addStatsToLeaderboard();
    void setUpNewBoard ();
    void revertMove();
    void setWhiteExit(boolean value);
    void setBlackExit(boolean value);
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
    void saveGame(String saveName);
    List<String> getSaveList();
    SaveGameReader readSaveGame (String saveName);
    String[] getLoadViewData(String saveName);
    int searchTopOccupiedRow(int col);
    boolean isPawnMovable(int col, int row,boolean highlight);
    void deleteSaveFile(String fileName);
    boolean isSaveNamePresent(String saveName);
    int getTournamentPointsToWin();
    void setUpSavedGame(String saveName);
    int[][] getBoardMatrix();
    void setCanRevert(boolean value);
    void selectPawn(int whichPoint, int whichRow);
    boolean movePawn(int from, int to);
    int[][] getSaveMatrix(String saveName);
    boolean getGameStart();
    boolean getGameEndState();
    void setGameStart(boolean value);
    void setGameEndState(boolean value);
    boolean allDiceUsed();
    void completeMoves();
    void deselectPawn(int col, int row);
    void resetDefaultSettings();
    void applySettingsChanges();
    void nextTutorialStage();
    void tutorialStageAction();
    void forceMovePawn(int from, int to);
    void forceDice(int value1, int value2);
    void forceDice(int value);
    void setWhiteTurn(boolean value);
    boolean[] getUsedArray();
    void setUpSavedBoard(int[][]matrix);
    String getString(String key);
    boolean shouldPlayTutorial();
    void flagTutorialPlayed();

    void setSetting (String section, String setting, Object value);
    <T> T getSetting (String section, String setting, Class<T> whichClass);
    //TODO
    //  Sostituire tutte questi metodi con un getSetting e un setSetting, che poi va a modificare l'impostazione scelta, in maniera
    //  simile a getString sopra
    boolean isParsable(String input);
    String[] getSupportedLanguages();
}
