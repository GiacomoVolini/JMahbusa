package jmb.logic;

import jmb.view.IView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static jmb.ConstantsShared.EMPTY;

public class Logic implements ILogic {

    public static IView view;
    public static ILogic logic;
    public DynamicBoardLogic board;
    public LeaderboardLogic ldb;
    public SettingsLogic settings;
    public String appDirectory;
    public StringsReader strings;

    private void createApplicationFolders() {
        try {

            Files.createDirectories(Path.of(getAppDirectory() + "/leaderboard"));
            Files.createDirectories(Path.of(getAppDirectory() + "/saves"));
            Files.createDirectories(Path.of(getAppDirectory() + "/settings"));
            Files.createDirectories(Path.of(getAppDirectory() + "/languages/strings"));
            Files.createDirectories(Path.of(getAppDirectory() + "/languages/flags"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void placeLanguageFiles() {
        System.out.println("PRIMA DEL RTY");
        try {
            System.out.println("SON QUI, DENTRO TRY");
            Path supportedPath = Path.of(getAppDirectory(), "languages","supportedLanguages.ini");
            if (!Files.exists(supportedPath)) {
                Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("supportedLanguages.ini")), supportedPath);
                for (String lang : StringsReader.getSupportedLanguages()) {
                    System.out.println(lang);
                    Path langPath = Path.of(getAppDirectory() , "languages","strings","STRINGS_" + lang + ".ini");
                    Path flagPath = Path.of(getAppDirectory() , "languages","flags","flag_" + lang + ".png");
                    if (!Files.exists(langPath)) {
                        System.out.println("DOVREI COPIARE STRING");
                        Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("STRINGS_"+lang+".ini")), langPath);
                    } else {
                        System.out.println("IL FILE ESISTE?");
                    }
                    if (!Files.exists(flagPath)) {
                        System.out.println("DOVREI COPIARE FLAG");
                        Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("flags/flag_"+lang+".png")), flagPath);
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("OH NO");
            ioe.printStackTrace();
        }
    }

    @Override
    public void initializeBoardLogic() {
        board = new GameLogic();
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
        board = new TutorialLogic();
    }
    @Override
    public void initializeProgramLogic() {
        appDirectory = System.getProperty("user.dir");
        System.out.println(appDirectory);
        createApplicationFolders();
        placeLanguageFiles();
        settings = new SettingsLogic();
        initializeStringsReader();
    }

    @Override
    public void initializeStringsReader() {
        strings = new StringsReader(settings.getSetting("General", "language", String.class));
    }

    public String getAppDirectory() {
        return appDirectory;
    }

    @Override
    public boolean getWhichTurn() {
        return board.isWhiteTurn();
    }

    @Override
    public void placePawnOnPoint(int whichPoint) {
        board.movePawn(board.getMoveBufferColumn(), whichPoint);
    }

    @Override
    public boolean isLastOnPoint(int whichPoint, int whichRow) {
        boolean last = true;
        if (whichRow != 15 && this.getBoardPlaceState(whichPoint, whichRow + 1) != EMPTY) {
            last = false;
        }
        return last;
    }

    @Override
    public void nextTurn() {
        ((GameLogic)board).changeTurn();
    }

    @Override
    public int getBoardPlaceState(int whichPoint, int whichRow) {
        return board.getBoardPlaceState(whichPoint, whichRow);
    }

    @Override
    public void createMoveBuffer(int whichPoint) {
        board.setMoveBuffer(whichPoint, board.searchTopOccupiedRow(whichPoint));
    }

    @Override
    public List<Player> getPlayerList() {
        return ldb.getList();
    }

    @Override
    public boolean isRollDouble() {
        return board.getDice().getDoubleNum();
    }

    @Override
    public int[] getDiceValues() {
        return board.getDice().getDiceValues();
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
    public boolean isDiceUsed (int i) {

        return board.getDice().getUsed(i);
    }

    @Override
    public void setUpNewBoard() {
        board.setUp();
    }

    @Override
    public void revertMove() {
        ((GameLogic)board).revertMove();
    }

    @Override
    public void setWhiteExit(boolean value) {
        board.setWhiteExit(value);
    }

    @Override
    public void setBlackExit(boolean value) {
        board.setBlackExit(value);
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
        GameLogic game = (GameLogic)board;
        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setBlacksWonPoints(0);
        game.setWhitesWonPoints(0);
    }

    @Override
    public void setPlayersForGame(String whitePlayer, String blackPlayer, int tournamentPoints) {
        setPlayersForGame(whitePlayer, blackPlayer);
        ((GameLogic)board).setTournamentPoints(tournamentPoints);
    }

    @Override
    public String getWhitePlayer() {
        return ((GameLogic)board).getWhitePlayer();
    }

    @Override
    public String getBlackPlayer() {
        return ((GameLogic)board).getBlackPlayer();
    }

    @Override
    public void addStatsToLeaderboard() {
        GameLogic game = (GameLogic)board;
        ldb.addStatsToList(game.getWhitePlayer(), game.getBlackPlayer(), game.getWhitesWonPoints());
        ldb.addStatsToList(game.getBlackPlayer(), game.getWhitePlayer(), game.getBlacksWonPoints());
    }

    @Override
    public boolean isTournamentOngoing() {
        return (((GameLogic)board).getTournamentPoints() != 0);
    }

    @Override
    public int getWhiteTournamentPoints() {
        return ((GameLogic)board).getWhitesWonPoints();
    }

    @Override
    public int getBlackTournamentPoints() {
        return ((GameLogic)board).getBlacksWonPoints();
    }

    @Override
    public int getTournamentPointsToWin() {
        return ((GameLogic)board).getTournamentPoints();
    }

    @Override
    public void setTurnDuration(double value) {
        ((GameLogic)board).setTurnDuration(value);
    }

    @Override
    public double getTurnDuration() {
        return ((GameLogic)board).getTurnDuration();
    }

    @Override
    public void saveGame(String saveName) {
        SaveGameWriter.writeSaveFile(((GameLogic)board), saveName);
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
    public int searchTopOccupiedRow(int col) {
        return board.searchTopOccupiedRow(col);
    }

    @Override
    public boolean isPawnMovable(int col, int row, boolean highlight) {
        return board.isPawnMovable(col, row, highlight);
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
        ((GameLogic)board).setUpSavedGame(SaveGameReader.readSaveGame(saveName));
    }

    @Override
    public int[][] getBoardMatrix() {
        int[][] out = null;
        out = board.getSquares();
        return out;
    }

    @Override
    public void setCanRevert (boolean value) {
        ((GameLogic)board).setCanRevert(value);
    }
    @Override
    public void selectPawn(int whichPoint, int whichRow) {
        board.selectPawn(whichPoint, whichRow);
    }

    @Override
    public void deselectPawn(int col, int row) {
        board.deselectPawn(col, row);
    }

    @Override
    public boolean movePawn(int from, int to) {
        return board.movePawn(from, to);
    }

    @Override
    public int[][] getSaveMatrix(String saveName) {
        return this.readSaveGame(saveName).getSquareMatrix();
    }

    @Override
    public boolean getGameStart() {
        return board.getGameStart();
    }

    @Override
    public boolean getGameEndState() {
        return ((GameLogic)board).getGameEndState();
    }

    @Override
    public void setGameStart(boolean value) {
        ((GameLogic)board).setGameStart(value);
    }

    @Override
    public void setGameEndState(boolean value) {
        ((GameLogic)board).setGameEndState(value);
    }

    @Override
    public boolean allDiceUsed() {
        boolean used = true;
        for (int i = 0; i < 4 && used; i++)
            if (!isDiceUsed(i))
                used = false;
        return used;
    }

    @Override
    public void completeMoves() {
        ((GameLogic)board).completeMoves();
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
        settings.getDefaultSettings();
        settings.restoreCurrent();
    }

    @Override
    public void applySettingsChanges() {
        settings.applySettingsChanges();
    }

    @Override
    public void nextTutorialStage() {
        ((TutorialLogic)board).nextTutorialStage();
    }
    @Override
    public void tutorialStageAction() {
        ((TutorialLogic)board).tutorialStageAction();
    }
    @Override
    public void forceMovePawn(int from, int to) {
        board.forceMovePawn(from, to);
    }
    @Override
    public void forceDice (int value1, int value2) {
        board.getDice().forceDice(value1, value2);
    }
    @Override
    public void forceDice (int value) {
        board.getDice().forceDice(value);
    }
    @Override
    public void setWhiteTurn(boolean value) {
        board.setWhiteTurn(value);
    }
    @Override
    public boolean[] getUsedArray() {
        return board.getDice().getUsedArray();
    }
    @Override
    public void setUpSavedBoard(int[][] matrix) {
        board.setUpSavedBoard(matrix);
    }
    @Override
    public String getString(String key) {
        return strings.get(key);
    }
    @Override
    public String[] getSupportedLanguages() {
        return StringsReader.getSupportedLanguages();
    }
    @Override
    public boolean isLanguageRightToLeft(String language) {
        return StringsReader.isLanguageRightToLeft(language);
    }
    @Override
    public boolean shouldPlayTutorial() {
        return SettingsLogic.shouldPlayTutorial();
    }
    @Override
    public void flagTutorialPlayed() {
        SettingsLogic.flagTutorialPlayed();
    }
    @Override
    public void setSetting(String section, String setting, Object value) {
        settings.setSetting(section, setting, value);
    }
    @Override
    public <T> T getSetting (String section, String setting, Class<T> whichClass) {
        return settings.getSetting(section, setting, whichClass);
    }
    @Override
    public String getSetting (boolean leftPreset, int presetEnum) {
        return settings.getSetting(leftPreset, presetEnum);
    }

}
