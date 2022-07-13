package jmb.model;

import javafx.scene.image.WritableImage;
import jmb.view.IView;

import java.io.IOException;
import java.util.List;

import static jmb.ConstantsShared.*;

public class Logic implements ILogic{

    public static IView view;
    public static GameLogic game;
    public static LeaderboardLogic ldb;
    public static DiceLogic gameDice;
    public static DiceLogic tutorialDice;
    public static SettingsLogic settings;
    public static TutorialLogic tutorial;
    public static String appDirectory;

    @Override
    public void initializeBoardLogic() {
        game = new GameLogic();
        gameDice = game.getDiceLogic();
        appDirectory = System.getProperty("user.dir");
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
    public void initializeTutorialLogic() {
        tutorial = new TutorialLogic();
        tutorialDice = tutorial.getDiceLogic();
    }

    public static String getAppDirectory() {
        return appDirectory;
    }

    @Override
    public boolean getWhichTurn() {
        return game.isWhiteTurn();
    }

    @Override
    public void placePawnOnPoint(int whichPoint) {
        if (game.movePawn(game.getMoveBufferColumn(), game.getMoveBufferRow(),
                game.searchFirstFreeRow(whichPoint), whichPoint)) {
            game.victoryCheck();
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
        game.changeTurn();

    }

    @Override
    public int getBoardPlaceState (int whichPoint, int whichRow) {
        return game.getBoardPlaceState(whichPoint, whichRow);
        }

    @Override
    public void createMoveBuffer (int whichPoint) {
        game.setMoveBuffer(whichPoint, game.searchTopOccupiedRow(whichPoint));
    }

    @Override
    public List<Player> getPlayerList() {
        return ldb.getList();
    }

    @Override
    public boolean isRollDouble() {
        return gameDice.getDoubleNum();
    }

    @Override
    public int[] getDiceValues() {
        return gameDice.getDiceValues();
    }

    @Override
    public void firstTurn() {
        game.runTurn();
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
        return gameDice.getUsed(i);
    }

    @Override
    public void writeLdbList() {
        ldb.ldbWriter(ldb.path);
    }

    @Override
    public void setUpNewGame () {
        game.setUp();
    }

    @Override
    public void revertMove() {
        game.revertMove();
    }

    @Override
    public boolean getWhiteExit() {
        return game.getWhiteExit();
    }

    @Override
    public boolean getBlackExit() {
        return game.getBlackExit();
    }

    @Override
    public void setPlayersForGame(String whitePlayer, String blackPlayer) {
        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setBlacksWonPoints(0);
        game.setWhitesWonPoints(0);

    }

    @Override
    public void setPlayersForGame(String whitePlayer, String blackPlayer, int tournamentPoints) {
        setPlayersForGame(whitePlayer, blackPlayer);
        game.setTournamentPoints(tournamentPoints);
    }

    @Override
    public String getWhitePlayer() {
        return game.getWhitePlayer();
    }

    @Override
    public String getBlackPlayer() {
        return game.getBlackPlayer();
    }

    @Override
    public void addStatsToLeaderboard() {
        ldb.addStatsToList(game.getWhitePlayer(), game.getBlackPlayer(), game.getWhitesWonPoints());
        ldb.addStatsToList(game.getBlackPlayer(), game.getWhitePlayer(), game.getBlacksWonPoints());
    }

    @Override
    public boolean isTournamentOngoing() {
        return (game.getTournamentPoints()!=0);
    }

    @Override
    public int getWhiteTournamentPoints() {
        return game.getWhitesWonPoints();
    }

    @Override
    public int getBlackTournamentPoints() {
        return game.getBlacksWonPoints();
    }

    @Override
    public int getTournamentPointsToWin() {
        return game.getTournamentPoints();
    }
    @Override
    public void setTurnDuration (double value) {
        game.setTurnDuration(value);
    }

    @Override
    public double getTurnDuration () {
        return game.getTurnDuration();
    }

    @Override
    public void setTimeRemaining (double value) {
        game.setTimeRemaining(value);
    }

    @Override
    public void saveGame(String saveName, WritableImage saveImage) {
        SaveGameWriter.writeSaveFile(game, saveName, saveImage);
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
    public int searchTopOccupiedRow(int col){return game.searchTopOccupiedRow(col);}

    @Override
    public boolean isPawnMovable(int col, int row) {
        return game.isPawnMovable(col, row);
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
        game.setUpSavedGame(SaveGameReader.readSaveGame(saveName));
    }

    @Override
    public int[][] getBoardMatrix() {
        return game.squares;
    }

    @Override
    public void thePawnColor(int whichPoint, int whichRow){
        game.thePawnColor( whichPoint, whichRow);}

    @Override
    public boolean movePawn(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {
        return game.movePawn(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
    }
    
    @Override
    public int[][] getSaveMatrix(String saveName) {return this.readSaveGame(saveName).getSquareMatrix();}

    @Override
    public boolean getGameStart() {
        return game.getGameStart();
    }
    @Override
    public boolean getGameEndState() {
        return game.getGameEndState();
    }
    @Override
    public void setGameStart(boolean value) {
        game.setGameStart(value);
    }
    @Override
    public void setGameEndState(boolean value) {
        game.setGameEndState(value);
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

    @Override
    public void deselectPawn(int col, int row) {
        game.deselectPawn(col, row);
    }
    @Override
    public void printMatrix() {
        for (int col = COL_BLACK_EXIT; col<=COL_WHITE_EXIT; col++) {
            for (int row = 0; row <=15; row++)
                System.out.print(getBoardMatrix()[row][col]);
            System.out.println();
        }
    }
    @Override
    public void resetDefaultSettings() {
        settings.resetDefaultSettings();
    }
    @Override
    public void applySettingsChanges() {
        settings.applySettingsChanges();
    }
    @Override
    public void revertSettingsChanges(){
        settings.revertSettingsChanges();
    }
    @Override
    public void initializeSettingsLogic() {
        settings = new SettingsLogic();
    }

    //------------------------------
    //GETTER E SETTER DELLE IMPOSTAZIONI
    //------------------------------

    @Override
    public void setFullScreen(boolean value){
        settings.setFullScreen(value);
    }
    @Override
    public boolean getFullScreen() {
        return settings.getFullScreen();
    }
    @Override
    public void setLockResolution (boolean value){
        settings.setLockResolution(value);
    }
    @Override
    public boolean getLockResolution (){
        return settings.getLockResolution();
    }
    @Override
    public void setResolutionHeight (int value){
        settings.setResolutionHeight(value);
    }
    @Override
    public int getResolutionHeight (){
        return settings.getResolutionHeight();
    }
    @Override
    public void setResolutionWidth (int value){
        settings.setResolutionWidth(value);
    }
    @Override
    public int getResolutionWidth (){
        return settings.getResolutionWidth();
    }
    @Override
    public void setMusicVolume (int value){
        settings.setMusicVolume(value);
        System.out.println(value + "LOGIC");
    }
    @Override
    public int getMusicVolume (){
        return settings.getMusicVolume();
    }
    @Override
    public void setSFXVolume (int value){
        settings.setSoundFXVolume(value);
    }
    @Override
    public int getSFXVolume (){
        return settings.getSoundFXVolume();
    }
    @Override
    public void setMuteMusic (boolean value){
        settings.setMuteMusic(value);
    }
    @Override
    public boolean getMuteMusic (){
        return settings.getMuteMusic();
    }
    @Override
    public void setMuteSFX (boolean value){
        settings.setMuteSFX(value);
    }
    @Override
    public boolean getMuteSFX (){
        return settings.getMuteSFX();
    }
    @Override
    public void setBoardPreset (int value){
        settings.setBoardPreset(value);
    }
    @Override
    public int getBoardPreset (){
        return settings.getBoardPreset();
    }
    @Override
    public void setWhitePawnStroke (String value){
        settings.setWhitePawnStroke(value);
    }
    @Override
    public String getWhitePawnStroke (){
        return settings.getWhitePawnStroke();
    }
    @Override
    public void setWhitePawnFill (String value){
        settings.setWhitePawnFill(value);
    }
    @Override
    public String getWhitePawnFill (){
        return settings.getWhitePawnFill();
    }
    @Override
    public void setBlackPawnStroke (String value){
        settings.setBlackPawnStroke(value);
    }
    @Override
    public String getBlackPawnStroke (){
        return settings.getBlackPawnStroke();
    }
    @Override
    public void setBlackPawnFill (String value){
        settings.setBlackPawnFill(value);
    }
    @Override
    public String getBlackPawnFill (){
        return settings.getBlackPawnFill();
    }
    @Override
    public void setBoardFrameColor (String value){
        settings.setBoardFrameColor(value);
    }
    @Override
    public String getBoardFrameColor (){
        return settings.getBoardFrameColor(false);
    }
    @Override
    public String getBoardFrameColor (boolean forceCustom) { return settings.getBoardFrameColor(forceCustom);}
    @Override
    public void setBoardInnerColor (String value)  {
        settings.setBoardInnerColor(value);
    }
    @Override
    public String getBoardInnerColor (){
        return settings.getBoardInnerColor(false);
    }
    @Override
    public String getBoardInnerColor (boolean forceCustom){
        return settings.getBoardInnerColor(forceCustom);
    }
    @Override
    public void setEvenPointsColor (String value){
        settings.setEvenPointsColor(value);
    }
    @Override
    public String getEvenPointsColor (){
        return settings.getEvenPointsColor(false);
    }
    @Override
    public String getEvenPointsColor (boolean forceCustom){
        return settings.getEvenPointsColor(forceCustom);
    }
    @Override
    public void setOddPointsColor (String value){
        settings.setOddPointsColor(value);
    }
    @Override
    public String getOddPointsColor (){
        return settings.getOddPointsColor(false);
    }
    @Override
    public String getOddPointsColor (boolean forceCustom){
        return settings.getOddPointsColor(forceCustom);
    }
    @Override
    public void setMoveRight (String value){
        settings.setMoveRight(value);
    }
    @Override
    public String getMoveRight (){
        return settings.getMoveRight();
    }
    @Override
    public void setMoveLeft (String value){
        settings.setMoveLeft(value);
    }
    @Override
    public String getMoveLeft (){
        return settings.getMoveLeft();
    }
    @Override
    public void setMoveUp (String value){
        settings.setMoveUp(value);
    }
    @Override
    public String getMoveUp (){
        return settings.getMoveUp();
    }
    @Override
    public void setMoveDown (String value){
        settings.setMoveDown(value);
    }
    @Override
    public String getMoveDown (){
        return settings.getMoveDown();
    }
    @Override
    public void setSelect (String value){
        settings.setSelect(value);
    }
    @Override
    public String getSelect (){
        return settings.getSelect();
    }
    @Override
    public void setConfirm (String value){
        settings.setConfirm(value);
    }
    @Override
    public String getConfirm (){
        return settings.getConfirm();
    }
    @Override
    public void setRevertMove (String value) {
        settings.setRevertMove(value);
    }
    @Override
    public String getRevertMove (){
        return settings.getRevertMove();
    }
    @Override
    public void setFinishTurn (String value){
        settings.setFinishTurn(value);
    }
    @Override
    public String getFinishTurn (){
        return settings.getFinishTurn();
    }
    @Override
    public void setOpenMenu (String value){
        settings.setOpenMenu(value);
    }
    @Override
    public String getOpenMenu (){
        return settings.getOpenMenu();
    }
    @Override
    public boolean getBypassDice (){
        return settings.getBypassDice();
    }
    @Override
    public String getNextTutorialString() {
        return tutorial.getNextTutorialString();
    }
    @Override
    public void nextTutorialStage() {
        tutorial.nextTutorialStage();
    }
}
