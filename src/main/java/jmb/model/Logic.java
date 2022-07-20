package jmb.model;

import javafx.scene.image.WritableImage;
import jmb.view.IView;

import java.io.IOException;
import java.util.List;

import static jmb.ConstantsShared.*;

public class Logic implements ILogic {

    public static IView view;
    public static ILogic logic;
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

    public String getAppDirectory() {
        return appDirectory;
    }

    @Override
    public boolean getWhichTurn(int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board=game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        return board.isWhiteTurn();
    }

    @Override
    public void placePawnOnPoint(int whichPoint, int whoCalled) {
        boolean possible = false;
        switch (whoCalled) {
            case GAME_CALLED:
                possible = game.movePawn(game.getMoveBufferColumn(), game.getMoveBufferRow(),
                        game.searchFirstFreeRow(whichPoint), whichPoint);
                if (possible)
                    game.victoryCheck();
                break;
            case TUTORIAL_CALLED:
                possible = tutorial.movePawn(tutorial.getMoveBufferColumn(), tutorial.getMoveBufferRow(),
                        tutorial.searchFirstFreeRow(whichPoint), whichPoint);
                break;
        }
    }

    @Override
    public boolean isLastOnPoint(int whichPoint, int whichRow, int whoCalled) {
        boolean last = true;
        if (whichRow != 15 && this.getBoardPlaceState(whichPoint, whichRow + 1, whoCalled) != EMPTY) {
            last = false;
        }
        return last;
    }

    @Override
    public void nextTurn() {
        game.changeTurn();

    }

    @Override
    public int getBoardPlaceState(int whichPoint, int whichRow, int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        return board.getBoardPlaceState(whichPoint, whichRow);
    }

    @Override
    public void createMoveBuffer(int whichPoint, int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        board.setMoveBuffer(whichPoint, board.searchTopOccupiedRow(whichPoint));
    }

    @Override
    public List<Player> getPlayerList() {
        return ldb.getList();
    }

    @Override
    public boolean isRollDouble(int whoCalled) {
        DiceLogic dice = null;
        switch (whoCalled) {
            case GAME_CALLED:
                dice = gameDice;
                break;
            case TUTORIAL_CALLED:
                dice = tutorialDice;
                break;
        }
        return dice.getDoubleNum();
    }

    @Override
    public int[] getDiceValues(int whoCalled) {
        DiceLogic dice = null;
        switch (whoCalled) {
            case GAME_CALLED:
                dice = gameDice;
                break;
            case TUTORIAL_CALLED:
                dice = tutorialDice;
                break;
        }
        return dice.getDiceValues();
    }

    @Override
    public void firstTurn(int whoCalled) {
        if (whoCalled == GAME_CALLED)
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
    public void addNewPlayersToList(String newName1, String newName2) {
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
    public boolean isDiceUsed (int i, int whoCalled) {
        DiceLogic dice = null;
        switch (whoCalled) {
            case GAME_CALLED:
                dice = gameDice;
                break;
            case TUTORIAL_CALLED:
                dice = tutorialDice;
                break;
        }
        return dice.getUsed(i);
    }

    @Override
    public void writeLdbList() {
        ldb.ldbWriter(ldb.path);
    }

    @Override
    public void setUpNewBoard(int whoCalled) {
        switch (whoCalled) {
            case GAME_CALLED:
                game.setUp();
                break;
            case TUTORIAL_CALLED:
                tutorial.setUp();
                break;
        }
    }

    @Override
    public void revertMove() {
        game.revertMove();
    }

    @Override
    public void setWhiteExit(int whoCalled, boolean value) {
        switch (whoCalled) {
            case GAME_CALLED:
                game.setWhiteExit(value);
                break;
            case TUTORIAL_CALLED:
                tutorial.setWhiteExit(value);
                break;
        }
    }

    @Override
    public void setBlackExit(int whoCalled, boolean value) {
        switch (whoCalled) {
            case GAME_CALLED:
                game.setBlackExit(value);
                break;
            case TUTORIAL_CALLED:
                tutorial.setBlackExit(value);
                break;
        }
    }

    @Override
    public boolean getWhiteExit(int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        return board.getWhiteExit();
    }

    @Override
    public boolean getBlackExit(int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        return board.getBlackExit();
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
        return (game.getTournamentPoints() != 0);
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
    public void setTurnDuration(double value) {
        game.setTurnDuration(value);
    }

    @Override
    public double getTurnDuration() {
        return game.getTurnDuration();
    }

    @Override
    public void setTimeRemaining(double value) {
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
    public int searchTopOccupiedRow(int whoCalled, int col) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        return board.searchTopOccupiedRow(col);
    }

    @Override
    public boolean isPawnMovable(int col, int row, int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        return board.isPawnMovable(col, row, false);
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
    public int[][] getBoardMatrix(int whoCalled) {
        int[][] out = null;
        switch (whoCalled) {
            case GAME_CALLED:
                out = game.getSquares();
                break;
            case TUTORIAL_CALLED:
                out = tutorial.getSquares();
                break;
        }
        return out;
    }

    @Override
    public void selectPawn(int whichPoint, int whichRow, int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        board.selectPawn(whichPoint, whichRow);
    }

    @Override
    public void deselectPawn(int col, int row, int whoCalled) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        board.deselectPawn(col, row);
    }

    @Override
    public boolean movePawn(int whoCalled, int from, int to) {
        boolean possible = false;
        switch (whoCalled) {
            case GAME_CALLED:
                possible = game.movePawn(from, to);
                break;
            case TUTORIAL_CALLED:
                possible = tutorial.movePawn(from, to);
                break;
        }
        return possible;
    }

    @Override
    public boolean movePawn(int whoCalled, int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {
        boolean possible = false;
        switch (whoCalled) {
            case GAME_CALLED:
                possible =game.movePawn(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
                break;
            case TUTORIAL_CALLED:
                possible = tutorial.movePawn(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
                break;
        }
        return possible;
    }

    @Override
    public int[][] getSaveMatrix(String saveName) {
        return this.readSaveGame(saveName).getSquareMatrix();
    }

    @Override
    public boolean getGameStart(int whoCalled) {
        boolean out = false;
        switch (whoCalled) {
            case GAME_CALLED:
                out = game.getGameStart();
                break;
            case TUTORIAL_CALLED:
                out = true;
                break;
        }
        return out;
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
    public boolean allDiceUsed(int whoCalled) {
        boolean used = true;
        for (int i = 0; i < 4 && used; i++)
            if (!isDiceUsed(i, whoCalled))
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
    public boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
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
    public void revertSettingsChanges() {
        settings.revertSettingsChanges();
    }

    @Override
    public void initializeSettingsLogic() {
        settings = new SettingsLogic();
    }
    @Override
    public void nextTutorialStage() {
        tutorial.nextTutorialStage();
    }
    @Override
    public void tutorialStageAction() {
        tutorial.tutorialStageAction();
    }
    @Override
    public void checkTutorialStageAdvancement() {
        tutorial.checkTutorialStageAdvancement();
    }
    @Override
    public void forceMovePawn(int whoCalled, int from, int to) {
        switch (whoCalled){
            case GAME_CALLED:
                game.forceMovePawn(from,to);
                break;
            case TUTORIAL_CALLED:
                tutorial.forceMovePawn(from, to);
                break;
        }
    }
    @Override
    public void forceDice (int whoCalled, int value1, int value2) {
        switch(whoCalled) {
            case GAME_CALLED:
                gameDice.forceDice(whoCalled, value1, value2);
                break;
            case TUTORIAL_CALLED:
                tutorialDice.forceDice(whoCalled, value1, value2);
        }
    }
    @Override
    public void forceDice (int whoCalled, int value) {
        switch(whoCalled) {
            case GAME_CALLED:
                gameDice.forceDice(whoCalled, value);
                break;
            case TUTORIAL_CALLED:
                tutorialDice.forceDice(whoCalled, value);
        }
    }
    @Override
    public void setWhiteTurn(int whoCalled, boolean value) {
        switch (whoCalled) {
            case GAME_CALLED:
                game.setWhiteTurn(value);
                break;
            case TUTORIAL_CALLED:
                tutorial.setWhiteTurn(value);
                break;
        }
    }
    @Override
    public boolean[] getUsedArray(int whoCalled) {
        DiceLogic dice = null;
        switch (whoCalled) {
            case GAME_CALLED:
                dice = gameDice;
                break;
            case TUTORIAL_CALLED:
                dice = tutorialDice;
                break;
        }
        return dice.getUsedArray();
    }
    @Override
    public void setUpSavedBoard(int whoCalled, int[][] matrix) {
        DynamicBoardLogic board = null;
        switch (whoCalled) {
            case GAME_CALLED:
                board = game;
                break;
            case TUTORIAL_CALLED:
                board = tutorial;
                break;
        }
        board.setUpSavedBoard(matrix);
    }
    @Override
    public void moveOpensWhiteExit() {
        game.flagMoveOpensWhiteExit();
    }
    @Override
    public void moveOpensBlackExit() {
        game.flagMoveOpensBlackExit();
    }

    //------------------------------
    //GETTER E SETTER DELLE IMPOSTAZIONI
    //------------------------------

    @Override
    public void setFullScreen(boolean value) {
        settings.setFullScreen(value);
    }

    @Override
    public boolean getFullScreen() {
        return settings.getFullScreen();
    }

    @Override
    public void setLockResolution(boolean value) {
        settings.setLockResolution(value);
    }

    @Override
    public boolean getLockResolution() {
        return settings.getLockResolution();
    }

    @Override
    public void setResolutionHeight(int value) {
        settings.setResolutionHeight(value);
    }

    @Override
    public int getResolutionHeight() {
        return settings.getResolutionHeight();
    }

    @Override
    public void setResolutionWidth(int value) {
        settings.setResolutionWidth(value);
    }

    @Override
    public int getResolutionWidth() {
        return settings.getResolutionWidth();
    }

    @Override
    public void setMusicVolume(int value) {
        settings.setMusicVolume(value);
        System.out.println(value + "LOGIC");
    }

    @Override
    public int getMusicVolume() {
        return settings.getMusicVolume();
    }

    @Override
    public void setSFXVolume(int value) {
        settings.setSoundFXVolume(value);
    }

    @Override
    public int getSFXVolume() {
        return settings.getSoundFXVolume();
    }

    @Override
    public void setMuteMusic(boolean value) {
        settings.setMuteMusic(value);
    }

    @Override
    public boolean getMuteMusic() {
        return settings.getMuteMusic();
    }

    @Override
    public void setMuteSFX(boolean value) {
        settings.setMuteSFX(value);
    }

    @Override
    public boolean getMuteSFX() {
        return settings.getMuteSFX();
    }

    @Override
    public void setBoardPreset(int value) {
        settings.setBoardPreset(value);
    }

    @Override
    public int getBoardPreset() {
        return settings.getBoardPreset();
    }

    @Override
    public void setWhitePawnStroke(String value) {
        settings.setWhitePawnStroke(value);
    }

    @Override
    public String getWhitePawnStroke() {
        return settings.getWhitePawnStroke();
    }

    @Override
    public void setWhitePawnFill(String value) {
        settings.setWhitePawnFill(value);
    }

    @Override
    public String getWhitePawnFill() {
        return settings.getWhitePawnFill();
    }

    @Override
    public void setBlackPawnStroke(String value) {
        settings.setBlackPawnStroke(value);
    }

    @Override
    public String getBlackPawnStroke() {
        return settings.getBlackPawnStroke();
    }

    @Override
    public void setBlackPawnFill(String value) {
        settings.setBlackPawnFill(value);
    }

    @Override
    public String getBlackPawnFill() {
        return settings.getBlackPawnFill();
    }

    @Override
    public void setBoardFrameColor(String value) {
        settings.setBoardFrameColor(value);
    }

    @Override
    public String getBoardFrameColor() {
        return settings.getBoardFrameColor(false);
    }

    @Override
    public String getBoardFrameColor(boolean forceCustom) {
        return settings.getBoardFrameColor(forceCustom);
    }

    @Override
    public void setBoardInnerColor(String value) {
        settings.setBoardInnerColor(value);
    }

    @Override
    public String getBoardInnerColor() {
        return settings.getBoardInnerColor(false);
    }

    @Override
    public String getBoardInnerColor(boolean forceCustom) {
        return settings.getBoardInnerColor(forceCustom);
    }

    @Override
    public void setEvenPointsColor(String value) {
        settings.setEvenPointsColor(value);
    }

    @Override
    public String getEvenPointsColor() {
        return settings.getEvenPointsColor(false);
    }

    @Override
    public String getEvenPointsColor(boolean forceCustom) {
        return settings.getEvenPointsColor(forceCustom);
    }

    @Override
    public void setOddPointsColor(String value) {
        settings.setOddPointsColor(value);
    }

    @Override
    public String getOddPointsColor() {
        return settings.getOddPointsColor(false);
    }

    @Override
    public String getOddPointsColor(boolean forceCustom) {
        return settings.getOddPointsColor(forceCustom);
    }
    @Override
    public void setSelectedPointColor(String value) {
        settings.setSelectedPointColor(value);
    }
    @Override
    public String getSelectedPointColor() {
        return settings.getSelectedPointColor(false);
    }
    @Override
    public String getSelectedPointColor(boolean forceCustom) {
        return settings.getSelectedPointColor(forceCustom);
    }
    @Override
    public String getSelectedPointPreset(boolean left) {
        return settings.getSelectedPointPreset(left);
    }
    public String getEvenPointsLeftPreset() {
        return settings.getEvenPointsLeftPreset();
    }
    public String getOddPointsLeftPreset() {
        return settings.getOddPointsLeftPreset();
    }
    public String getEvenPointsRightPreset(){
        return settings.getEvenPointsRightPreset();
    }
    public String getOddPointsRightPreset() {
        return settings.getOddPointsRightPreset();
    }
    @Override
    public void setMoveRight(String value) {
        settings.setMoveRight(value);
    }

    @Override
    public String getMoveRight() {
        return settings.getMoveRight();
    }

    @Override
    public void setMoveLeft(String value) {
        settings.setMoveLeft(value);
    }

    @Override
    public String getMoveLeft() {
        return settings.getMoveLeft();
    }

    @Override
    public void setMoveUp(String value) {
        settings.setMoveUp(value);
    }

    @Override
    public String getMoveUp() {
        return settings.getMoveUp();
    }

    @Override
    public void setMoveDown(String value) {
        settings.setMoveDown(value);
    }

    @Override
    public String getMoveDown() {
        return settings.getMoveDown();
    }

    @Override
    public void setSelect(String value) {
        settings.setSelect(value);
    }

    @Override
    public String getSelect() {
        return settings.getSelect();
    }

    @Override
    public void setConfirm(String value) {
        settings.setConfirm(value);
    }

    @Override
    public String getConfirm() {
        return settings.getConfirm();
    }

    @Override
    public void setRevertMove(String value) {
        settings.setRevertMove(value);
    }

    @Override
    public String getRevertMove() {
        return settings.getRevertMove();
    }

    @Override
    public void setFinishTurn(String value) {
        settings.setFinishTurn(value);
    }

    @Override
    public String getFinishTurn() {
        return settings.getFinishTurn();
    }

    @Override
    public void setOpenMenu(String value) {
        settings.setOpenMenu(value);
    }

    @Override
    public String getOpenMenu() {
        return settings.getOpenMenu();
    }

    @Override
    public boolean getBypassDice() {
        return settings.getBypassDice();
    }
}
