package jmb.model;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static jmb.ConstantsShared.*;

public class SettingsLogic {

    /*TODO
        Forse potrebbe esserci un problema sui metodi loadFrom e storeTo
        I metodi potrebbero aggiornare gli oggetti Ini e non gestire correttamente la lettura e scrittura da file
     */

    private boolean fullScreen = false;
    private boolean lockResolution = false;
    private int resolutionWidth = 640;
    private int resolutionHeight = 480;
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
    private String boardInnerColor = "#e1c699";
    private String evenPointsColor = "#b27e31";
    private String oddPointsColor ="#2abc95";
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
    private Ini defaults;
    private Ini current;

/* TODO VECCHIO
    public SettingsLogic() {
        try{
            //defaults = new Ini();
            //current = new Ini();
            defaultsPath = Path.of("./settings/Defaults.ini");
            currentPath = Path.of("./settings/Current.ini");
            if (Files.exists(defaultsPath)) {
                System.out.println("Defaults esiste");
                if (Files.exists(currentPath)) {
                    System.out.println("Current esiste");
                    //current.load(currentPath.toFile());
                    //loadSettingsFrom(current);
                    loadSettingsFrom(currentPath);
                } else{
                    System.out.println("Current non esiste");
                    //defaults.load(defaultsPath.toFile());
                    //loadSettingsFrom(defaults);
                    loadSettingsFrom(defaultsPath);
                    Files.createFile(currentPath);
                    //defaults.store(currentPath.toFile());
                    saveSettingsTo(currentPath);
                }
            } else {
                System.out.println("Defaults non esiste");
                Files.createFile(defaultsPath);
                Files.createFile(currentPath);
                //defaults.load(defaultsPath.toFile());
                //saveSettingsTo(defaults);
                //defaults.store(currentPath.toFile());
                saveSettingsTo(defaultsPath);
                saveSettingsTo(currentPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 */

    public SettingsLogic() {
        try {
            String settingsDir = Logic.getAppDirectory() + "/settings";
            if (!Files.exists(Path.of(settingsDir)))
                Files.createDirectory(Path.of(settingsDir));
            defaultsPath = Path.of (settingsDir.concat("/Defaults.ini"));
            currentPath = Path.of (settingsDir.concat("/Current.ini"));
            if (Files.exists(defaultsPath)) {
                System.out.println("Defaults esiste");
                if (Files.exists(currentPath)) {
                    System.out.println("Current esiste");
                    loadSettingsFrom(currentPath);
                } else{
                    System.out.println("Current non esiste");
                    loadSettingsFrom(defaultsPath);
                    Files.createFile(currentPath);
                    saveSettingsTo(currentPath);
                }
            } else {
                System.out.println("Defaults non esiste");
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
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    protected void applySettingsChanges() {
        try {
            saveSettingsTo(currentPath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    protected void revertSettingsChanges() {
        try {
            loadSettingsFrom(currentPath);
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
        this.resolutionHeight = value;
    }
    protected int getResolutionHeight() {
        return this.resolutionHeight;
    }
    protected void setResolutionWidth(int value) {
        this.resolutionWidth = value;
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
    protected String getBoardFrameColor() {
        return this.boardFrameColor;
    }
    protected void setBoardInnerColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.boardInnerColor = value;
        }
    }
    protected String getBoardInnerColor() {
        return this.boardInnerColor;
    }
    protected void setEvenPointsColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.evenPointsColor = value;
        }
    }
    protected String getEvenPointsColor() {
        return this.evenPointsColor;
    }
    protected void setOddPointsColor(String value) {
        if (value.charAt(0)=='#' && value.length()==7) {
            this.oddPointsColor = value;
        }
    }
    protected String getOddPointsColor() {
        return this.oddPointsColor;
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
    protected void setConfirm(String value) {
        this.confirm = value;
    }
    protected String getConfirm() {
        return this.confirm;
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
}
