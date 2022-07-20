package jmb.model;

import javafx.scene.image.WritableImage;

import java.util.List;

public interface ILogic {

    //void placePawnOnTopPoints (int prevRegion, int prevPoint, int whichPoint);

    //void placePawnOnBotPoints (int whichPoint);
    String getAppDirectory();

    void placePawnOnPoint (int whichPoint, int whoCalled);

    boolean getWhichTurn(int whoCalled);

    void nextTurn ();

    void initializeBoardLogic ();

    void initializeLeaderboardLogic();
    void initializeTutorialLogic();
    void initializeStringsReader();

    int getBoardPlaceState (int whichPoint, int whichRow, int whoCalled);
    //  Metodo che restituisce tre valori a seconda dello stato della casella corrispondente di BoardLogic
    //      Restituisce EMPTY (0) se la casella non contiene alcuna pedina
    //                  WHITE (1) se la casella contiene una pedina del bianco
    //                  BLACK (2) se la casella contiene una pedina del nero

    boolean isLastOnPoint (int whichPoint, int whichRow, int whoCalled);
    //  Metodo che restituisce true se non ci sono altre pedine al di sopra di essa in quella punta

    void createMoveBuffer (int whichPoint, int whoCalled);

    List<Player> getPlayerList ();

    boolean isRollDouble(int whoCalled);

    int[] getDiceValues(int whoCalled);

    void firstTurn(int whoCalled);

    List<String> getPlayerNameList();

    int compareNameLists(String newName1, String newName2);

    void addNewPlayersToList (String newName1, String newName2);

    boolean isDiceUsed (int i, int whoCalled);

    void writeLdbList();

    void addStatsToLeaderboard();

    void setUpNewBoard (int whoCalled);

    void revertMove();
    void setWhiteExit(int whoCalled, boolean value);
    void setBlackExit(int whoCalled, boolean value);
    boolean getBlackExit(int whoCalled);

    boolean getWhiteExit(int whoCalled);

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

    List<String> getSaveList();

    SaveGameReader readSaveGame (String saveName);

    String[] getLoadViewData(String saveName);

    int searchTopOccupiedRow(int whoCalled, int col);

    boolean isPawnMovable(int col, int row,boolean highlight, int whoCalled);

    void deleteSaveFile(String fileName);

    boolean isSaveNamePresent(String saveName);

    int getTournamentPointsToWin();

    void setUpSavedGame(String saveName);

    int[][] getBoardMatrix(int whoCalled);
  
    void selectPawn(int whichPoint, int whichRow, int whoCalled);
    boolean movePawn(int whoCalled, int from, int to);
    boolean movePawn(int whoCalled, int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC);

    int[][] getSaveMatrix(String saveName);


    boolean getGameStart(int whoCalled);
    boolean getGameEndState();
    void setGameStart(boolean value);
    void setGameEndState(boolean value);
    boolean allDiceUsed(int whoCalled);
    void completeMoves();

    void deselectPawn(int col, int row, int whoCalled);
    void resetDefaultSettings();
    void applySettingsChanges();
    void revertSettingsChanges();
    void initializeSettingsLogic();
    void nextTutorialStage();
    void tutorialStageAction();
    void checkTutorialStageAdvancement();
    void forceMovePawn(int whoCalled, int from, int to);
    void forceDice(int whoCalled, int value1, int value2);
    void forceDice(int whoCalled, int value);
    void setWhiteTurn(int whoCalled, boolean value);
    boolean[] getUsedArray(int whoCalled);
    void setUpSavedBoard(int whoCalled, int[][]matrix);
    void moveOpensWhiteExit();
    void moveOpensBlackExit();
    String getString(String key);
    //------------------------------
    //GETTER E SETTER DELLE IMPOSTAZIONI
    //------------------------------
    String getLanguage();
    void setLanguage(String value);
    void setFullScreen(boolean value);
    boolean getFullScreen();
    void setLockResolution (boolean value);
    boolean getLockResolution ();
    void setResolutionHeight (int value);
    int getResolutionHeight ();
    void setResolutionWidth (int value);
    int getResolutionWidth ();
    void setMusicVolume (int value);
    int getMusicVolume ();
    void setSFXVolume (int value);
    int getSFXVolume ();
    void setMuteMusic (boolean value);
    boolean getMuteMusic ();
    void setMuteSFX (boolean value);
    boolean getMuteSFX ();
    void setBoardPreset (int value);
    int getBoardPreset ();
    void setWhitePawnStroke (String value);
    String getWhitePawnStroke ();
    void setWhitePawnFill (String value);
    String getWhitePawnFill ();
    void setBlackPawnStroke (String value);
    String getBlackPawnStroke ();
    void setBlackPawnFill (String value);
    String getBlackPawnFill ();
    void setBoardFrameColor (String value);
    String getBoardFrameColor ();
    String getBoardFrameColor (boolean forceCustom);
    void setBoardInnerColor (String value);
    String getBoardInnerColor ();
    String getBoardInnerColor (boolean forceCustom);
    void setEvenPointsColor (String value);
    String getEvenPointsColor ();
    String getEvenPointsColor (boolean forceCustom);
    void setOddPointsColor (String value);
    String getOddPointsColor ();
    String getOddPointsColor (boolean forceCustom);
    void setSelectedPointColor(String value);
    String getSelectedPointColor();
    String getSelectedPointColor (boolean forceCustom);
    String getSelectedPointPreset(boolean left);
    String getEvenPointsLeftPreset();
    String getOddPointsLeftPreset();
    String getEvenPointsRightPreset();
    String getOddPointsRightPreset();
    void setMoveRight (String value);
    String getMoveRight ();
    void setMoveLeft (String value);
    String getMoveLeft ();
    void setMoveUp (String value);
    String getMoveUp ();
    void setMoveDown (String value);
    String getMoveDown ();
    void setSelect (String value);
    String getSelect ();
    void setConfirm (String value);
    String getConfirm ();
    void setRevertMove (String value);
    String getRevertMove ();
    void setFinishTurn (String value);
    String getFinishTurn ();
    void setOpenMenu (String value);
    String getOpenMenu ();
    boolean getBypassDice ();

    //_________________________

}
