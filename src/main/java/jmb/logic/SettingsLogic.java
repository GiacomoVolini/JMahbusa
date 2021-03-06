package jmb.logic;

import org.ini4j.Ini;
import org.ini4j.Profile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;

public class SettingsLogic {

    private boolean fullScreen = false;
    private boolean lockResolution = false;
    private int resolutionWidth = 640;
    private final static int MIN_RESOLUTION_WIDTH = 640;
    private int resolutionHeight = 480;
    private final static int MIN_RESOLUTION_HEIGHT = 480;
    private int musicVolume = 100;
    private int soundFXVolume = 100;
    private boolean muteMusic = false;
    private boolean muteSFX = false;
    private int boardPreset = 0;
    private String whitePawnStroke = "#000000";
    private String whitePawnFill = "#ffffff";
    private String blackPawnStroke = "#ffffff";
    private String blackPawnFill = "#000000";
    private String boardFrameColor = "#432d05";
    private static final String BOARD_FRAME_LEFT_PRESET = "#000000";
    private static final String BOARD_FRAME_RIGHT_PRESET = "#090957";
    private String boardInnerColor = "#e1c699";
    private static final String BOARD_INNER_LEFT_PRESET = "#ad1111";
    private static final String BOARD_INNER_RIGHT_PRESET = "#53960f";
    private String evenPointsColor = "#b27e31";
    private static final String EVEN_POINTS_LEFT_PRESET = "#ffde3a";
    private static final String EVEN_POINTS_RIGHT_PRESET = "#c21123";
    private String oddPointsColor ="#2abc95";
    private static final String ODD_POINTS_LEFT_PRESET = "#c0c0c0";
    private static final String ODD_POINTS_RIGHT_PRESET = "#daa505";
    private String selectedPointColor = "#fffb00";
    private static final String SELECTED_POINT_LEFT_PRESET = "#6495ed";
    private static final String SELECTED_POINT_RIGHT_PRESET = "#fffb00";
    private String backgroundColor = "#008000";

    private String moveRight = "D";
    private String moveLeft = "A";
    private String moveUp = "W";
    private String moveDown = "S";
    private String select = "SPACE";
    private String confirm = "ENTER";
    private String revertMove = "BACK_SPACE";
    private String finishTurn = "F";
    private String openMenu = "ESCAPE";
    private boolean bypassDice = false;
    private Path defaultsPath;
    private Path currentPath;
    private String language = "IT";


    public SettingsLogic() {
        try {
            String settingsDir = logic.getAppDirectory() + "/settings";
            Path settingsPath = Path.of(settingsDir);
            if (!Files.exists(settingsPath))
                Files.createDirectory(settingsPath);
            defaultsPath = Path.of (settingsDir.concat("/Defaults.ini"));
            currentPath = Path.of (settingsDir.concat("/Current.ini"));
            if (Files.exists(defaultsPath)) {
                if (Files.exists(currentPath)) {
                    loadSettingsFrom(currentPath);
                } else{
                    loadSettingsFrom(defaultsPath);
                    Files.createFile(currentPath);
                    saveSettingsTo(currentPath);
                }
            } else {
                Files.createFile(defaultsPath);
                Files.createFile(currentPath);
                saveSettingsTo(defaultsPath);
                saveSettingsTo(currentPath);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //private void loadSettingsFrom(Ini ini) {
    private void loadSettingsFrom(Path path) throws IOException {
        Ini ini = new Ini(path.toFile());
        Profile.Section general = ini.get("General");
        this.language = general.get("language");
        Profile.Section video = ini.get("Video");
        this.fullScreen = video.get("fullScreen", boolean.class);
        this.lockResolution = video.get("lockResolution", boolean.class);
        this.resolutionWidth = video.get("resolutionWidth", int.class);
        this.resolutionHeight = video.get ("resolutionHeight", int.class);
        Profile.Section audio = ini.get("Audio");
        this.musicVolume = min(100, max(audio.get ("musicVolume", int.class), 0));
        this.soundFXVolume = min(100, max(audio.get("soundFXVolume", int.class), 0));
        this.muteMusic = audio.get("muteMusic", boolean.class);
        this.muteSFX = audio.get("muteSFX", boolean.class);
        Profile.Section customization = ini.get("Customization");
        this.boardPreset = customization.get("boardPreset", int.class);
        this.whitePawnStroke = customization.get("whitePawnStroke");
        this.whitePawnFill = customization.get("whitePawnFill");
        this.blackPawnStroke = customization.get("blackPawnStroke");
        this.blackPawnFill = customization.get("blackPawnFill");
        this.boardFrameColor = customization.get("boardFrameColor");
        this.boardInnerColor = customization.get("boardInnerColor");
        this.evenPointsColor = customization.get("evenPointsColor");
        this.oddPointsColor = customization.get("oddPointsColor");
        this.selectedPointColor = customization.get("selectedPointColor");
        Profile.Section controls = ini.get("Controls");
        this.moveRight = controls.get("moveRight");
        this.moveLeft = controls.get("moveLeft");
        this.moveUp = controls.get("moveUp");
        this.moveDown = controls.get("moveDown");
        this.select = controls.get("select");
        this.confirm = controls.get("confirm");
        this.revertMove = controls.get("revertMove");
        this.finishTurn = controls.get("finishTurn");
        this.openMenu = controls.get("openMenu");
        Profile.Section debug = ini.get("DEBUG");
        this.bypassDice = debug.get ("bypassDice", boolean.class);
    }

    //private void saveSettingsTo (Ini ini) {
    private void saveSettingsTo(Path path) throws IOException {
        Ini ini = new Ini(path.toFile());
        ini.put ("General", "language", language);
        ini.put ("Video", "fullScreen", fullScreen);
        ini.put ("Video", "lockResolution", lockResolution);
        ini.put ("Video", "resolutionWidth", resolutionWidth);
        ini.put ("Video", "resolutionHeight", resolutionHeight);
        ini.put("Audio", "musicVolume", musicVolume);
        ini.put("Audio", "soundFXVolume", soundFXVolume);
        ini.put("Audio", "muteMusic", muteMusic);
        ini.put("Audio", "muteSFX", muteSFX);
        ini.put("Customization", "boardPreset", boardPreset);
        ini.put("Customization", "whitePawnStroke", whitePawnStroke);
        ini.put("Customization", "whitePawnFill", whitePawnFill);
        ini.put("Customization", "blackPawnStroke", blackPawnStroke);
        ini.put("Customization", "blackPawnFill", blackPawnFill);
        ini.put("Customization", "boardFrameColor", boardFrameColor);
        ini.put("Customization", "boardInnerColor", boardInnerColor);
        ini.put("Customization", "evenPointsColor", evenPointsColor);
        ini.put("Customization", "oddPointsColor", oddPointsColor);
        ini.put("Customization", "selectedPointColor", selectedPointColor);
        ini.put("Controls", "moveRight", moveRight);
        ini.put("Controls", "moveLeft", moveLeft);
        ini.put("Controls", "moveUp", moveUp);
        ini.put("Controls", "moveDown", moveDown);
        ini.put("Controls", "select", select);
        ini.put("Controls", "confirm", confirm);
        ini.put("Controls", "revertMove", revertMove);
        ini.put("Controls", "finishTurn", finishTurn);
        ini.put("Controls", "openMenu", openMenu);
        ini.put("DEBUG", "bypassDice", bypassDice);
        ini.store();
    }

    protected void resetDefaultSettings() {
        try {
            loadSettingsFrom(defaultsPath);
            saveSettingsTo(currentPath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    protected void applySettingsChanges() {
        try {
            saveSettingsTo(currentPath);
            logic.initializeStringsReader();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //------------------------
    // METODI SETTER E GETTER
    //------------------------

    protected void setFullScreen(boolean value) {
        this.fullScreen = value;
    }
    protected boolean getFullScreen () {
        return this.fullScreen;
    }
    protected void setLockResolution(boolean value) {
        this.lockResolution = value;
    }
    protected boolean getLockResolution() {
        return this.lockResolution;
    }
    protected void setResolutionHeight(int value) {
        this.resolutionHeight = max(MIN_RESOLUTION_HEIGHT, value);
    }
    protected int getResolutionHeight() {
        return this.resolutionHeight;
    }
    protected void setResolutionWidth(int value) {
        this.resolutionWidth = max(MIN_RESOLUTION_WIDTH, value);
    }
    protected int getResolutionWidth() {
        return this.resolutionWidth;
    }
    protected void setMusicVolume(int value) {
        this.musicVolume = max(0, min(100, value));
    }
    protected int getMusicVolume() {
        return this.musicVolume;
    }
    protected void setSoundFXVolume(int value){
        this.soundFXVolume = max(0, min(100, value));
    }
    protected int getSoundFXVolume() {
        return  this.soundFXVolume;
    }
    protected void setMuteMusic(boolean value) {
        this.muteMusic = value;
    }
    protected boolean getMuteMusic() {
        return this.muteMusic;
    }
    protected void setMuteSFX(boolean value) {
        this.muteSFX = value;
    }
    protected boolean getMuteSFX() {
        return this.muteSFX;
    }
    protected void setBoardPreset(int value) {
        this.boardPreset = value;
    }
    protected int getBoardPreset() {
        return this.boardPreset;
    }
    protected void setWhitePawnStroke(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.whitePawnStroke = value;
        }
    }
    protected String getWhitePawnStroke() {
        return this.whitePawnStroke;
    }
    protected void setWhitePawnFill(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.whitePawnFill = value;
        }
    }
    protected String getWhitePawnFill() {
        return this.whitePawnFill;
    }
    protected void setBlackPawnStroke(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.blackPawnStroke = value;
        }
    }
    protected String getBlackPawnStroke() {
        return this.blackPawnStroke;
    }
    protected void setBlackPawnFill(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.blackPawnFill = value;
        }
    }
    protected String getBlackPawnFill() {
        return this.blackPawnFill;
    }
    protected void setBoardFrameColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.boardFrameColor = value;
        }
    }
    protected String getBoardFrameColor(boolean forceCustom) {
        String out = this.boardFrameColor;
        if (!forceCustom) {
            switch (boardPreset) {
                case LEFT_PRESET:
                    out = BOARD_FRAME_LEFT_PRESET;
                    break;
                case RIGHT_PRESET:
                    out = BOARD_FRAME_RIGHT_PRESET;
                    break;
            }
        }
                return out;
    }
    protected void setBoardInnerColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.boardInnerColor = value;
        }
    }
    protected String getBoardInnerColor(boolean forceCustom) {
        String out = this.boardInnerColor;
        if (!forceCustom) {
            switch (boardPreset) {
                case LEFT_PRESET:
                    out = BOARD_INNER_LEFT_PRESET;
                    break;
                case RIGHT_PRESET:
                    out = BOARD_INNER_RIGHT_PRESET;
                    break;
            }
        }
        return out;
    }
    protected void setEvenPointsColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.evenPointsColor = value;
        }
    }
    protected String getEvenPointsColor(boolean forceCustom) {
        String out = this.evenPointsColor;
        if (!forceCustom) {
            switch (boardPreset) {
                case LEFT_PRESET:
                    out = EVEN_POINTS_LEFT_PRESET;
                    break;
                case RIGHT_PRESET:
                    out = EVEN_POINTS_RIGHT_PRESET;
                    break;
            }
        }
        return out;
    }
    protected void setOddPointsColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.oddPointsColor = value;
        }
    }
    protected String getOddPointsColor(boolean forceCustom) {
        String out = this.oddPointsColor;
        if (!forceCustom) {
            switch (boardPreset) {
                case LEFT_PRESET:
                    out = ODD_POINTS_LEFT_PRESET;
                    break;
                case RIGHT_PRESET:
                    out = ODD_POINTS_RIGHT_PRESET;
                    break;
            }
        }
        return out;
    }
    protected void setSelectedPointColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.selectedPointColor = value;
        }
    }
    protected String getSelectedPointColor(boolean forceCustom) {
        String out = this.selectedPointColor;
        if (!forceCustom) {
            switch (boardPreset) {
                case LEFT_PRESET:
                    out = SELECTED_POINT_LEFT_PRESET;
                    break;
                case RIGHT_PRESET:
                    out = SELECTED_POINT_RIGHT_PRESET;
                    break;
            }
        }
        return out;
    }
    protected String getSelectedPointPreset(boolean left) {
        String out;
        if (left)
            out = SELECTED_POINT_LEFT_PRESET;
        else out = SELECTED_POINT_RIGHT_PRESET;
        return out;
    }
    protected void setMoveRight(String value) {
        this.moveRight = value;
    }
    protected String getMoveRight() {
        return this.moveRight;
    }
    protected void setMoveLeft(String value) {
        this.moveLeft = value;
    }
    protected String getMoveLeft() {
        return this.moveLeft;
    }
    protected void setMoveUp(String value) {
        this.moveUp = value;
    }
    protected String getMoveUp() {
        return this.moveUp;
    }
    protected void setMoveDown(String value) {
        this.moveDown = value;
    }
    protected String getMoveDown() {
        return this.moveDown;
    }
    protected void setSelect(String value) {
        this.select = value;
    }
    protected String getSelect() {
        return this.select;
    }
    protected void setRevertMove(String value) {
        this.revertMove = value;
    }
    protected String getRevertMove() {
        return this.revertMove;
    }
    protected void setFinishTurn(String value) {
        this.finishTurn = value;
    }
    protected String getFinishTurn() {
        return this.finishTurn;
    }
    protected void setOpenMenu(String value) {
        this.openMenu = value;
    }
    protected String getOpenMenu() {
        return this.openMenu;
    }
    protected boolean getBypassDice() {
        return this.bypassDice;
    }
    protected String getEvenPointsLeftPreset() {
        return EVEN_POINTS_LEFT_PRESET;
    }
    protected String getOddPointsLeftPreset() {
        return ODD_POINTS_LEFT_PRESET;
    }
    protected String getEvenPointsRightPreset(){
        return EVEN_POINTS_RIGHT_PRESET;
    }
    protected String getOddPointsRightPreset() {
        return ODD_POINTS_RIGHT_PRESET;
    }
    protected String getLanguage() {
        return language;
    }
    protected void setLanguage(String value) {
        this.language = value;
    }
    protected void setBackgroundColor(String value) {
        this.backgroundColor = value;
    }
    protected String getBackgroundColor() {
        return this.backgroundColor;
    }
}
