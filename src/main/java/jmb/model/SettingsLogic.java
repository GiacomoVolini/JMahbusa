package jmb.model;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.cert.Extension;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SettingsLogic {

    private boolean fullScreen = false;
    private boolean lockResolution = false;
    private int resolutionHeight = 640;
    private int resolutionWidth = 480;
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
    private String revertMove = "BACKSPACE";
    private String finishTurn = "F";
    private String openMenu = "ESCAPE";
    private boolean bypassDice = false;

    public SettingsLogic() {
        try{
            Ini defaults = new Ini();
            Ini current = new Ini();
            Path defaultsPath = Path.of("/settings/Defaults.ini");
            Path currentPath = Path.of("/settings/Current.ini");
            if (Files.exists(defaultsPath)) {
                if (Files.exists(currentPath)) {
                    current.load(currentPath.toFile());
                    loadSettingsFrom(current);
                } else{
                    defaults.load(defaultsPath.toFile());
                    loadSettingsFrom(defaults);
                    Files.createFile(currentPath);
                    defaults.store(currentPath.toFile());
                }
            } else {
                Files.createFile(defaultsPath);
                Files.createFile(currentPath);
                defaults.load(defaultsPath.toFile());
                saveSettingsTo(defaults);
                defaults.store(currentPath.toFile());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSettingsFrom(Ini ini) {
        Profile.Section video = ini.get("Video");
        this.fullScreen = video.get("fullScreen", boolean.class);
        this.lockResolution = video.get("lockResolution", boolean.class);
        this.resolutionHeight = video.get ("resolutionHeight", int.class);
        this.resolutionWidth = video.get("resolutionWidth", int.class);
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

    private void saveSettingsTo (Ini ini) {
        ini.put ("Video", "fullScreen", fullScreen);
        ini.put ("Video", "lockResolution", lockResolution);
        ini.put ("Video", "resolutionHeight", resolutionHeight);
        ini.put ("Video", "resolutionWidth", resolutionWidth);
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
    }


}
