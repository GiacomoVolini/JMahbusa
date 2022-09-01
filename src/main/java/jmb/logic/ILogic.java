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
    //------------------------------
    //GETTER E SETTER DELLE IMPOSTAZIONI
    //------------------------------
    //TODO
    //  Sostituire tutte questi metodi con un getSetting e un setSetting, che poi va a modificare l'impostazione scelta, in maniera
    //  simile a getString sopra
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
    void setRevertMove (String value);
    String getRevertMove ();
    void setFinishTurn (String value);
    String getFinishTurn ();
    void setOpenMenu (String value);
    String getOpenMenu ();
    boolean getBypassDice ();
    boolean isParsable(String input);
    void setBackgroundColor(String value);
    String getBackgroundColor();
    String[] getSupportedLanguages();


    //_________________________

}
