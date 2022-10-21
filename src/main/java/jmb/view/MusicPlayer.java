package jmb.view;

import javafx.scene.media.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

public class MusicPlayer {
    String gameMusicLocation = getClass().getResource("/jmb/view/music/game.mp3").toString();
    MediaPlayer gameMusic = new MediaPlayer (new Media (gameMusicLocation));

    String menuMusicLocation = getClass().getResource("/jmb/view/music/menus.mp3").toString();
    MediaPlayer menuMusic = new MediaPlayer( new Media(menuMusicLocation));

    String pawnSFXLocation = getClass().getResource("/jmb/view/sfx/pawnDrop.mp3").toString();
    AudioClip pawnSFX = new AudioClip(pawnSFXLocation);
    String tutorialMusicLocation = getClass().getResource("/jmb/view/music/tutorial.mp3").toString();
    MediaPlayer tutorialMusic = new MediaPlayer(new Media(tutorialMusicLocation));

    String errorSFXLocation = getClass().getResource("/jmb/view/sfx/error.mp3").toString();
    AudioClip errorSFX = new AudioClip(errorSFXLocation);
    String winSFXLocation = getClass().getResource("/jmb/view/sfx/victory.mp3").toString();
    AudioClip winSFX = new AudioClip(winSFXLocation);
    String applauseSFXLocation = getClass().getResource("/jmb/view/sfx/applause.mp3").toString();
    AudioClip applauseSFX = new AudioClip(applauseSFXLocation);
    String diceRollSFXLocation = getClass().getResource("/jmb/view/sfx/diceRoll.mp3").toString();
    AudioClip diceRollSFX = new AudioClip(diceRollSFXLocation);

    protected void setMusicVolume (double value) {
        this.menuMusic.setVolume(value);
        this.gameMusic.setVolume(value);
        this.tutorialMusic.setVolume(value);
    }
    protected void setSFXVolume (double value) {
        this.pawnSFX.setVolume(value);
        this.errorSFX.setVolume(value);
    }
    protected void playMusic(Music music) {
        if (!logic.getSetting("Audio","muteMusic",boolean.class)){
            switch (music) {
                case MENU:
                    this.menuMusic.setCycleCount(Integer.MAX_VALUE);
                    this.menuMusic.play();
                    this.tutorialMusic.stop();
                    this.gameMusic.stop();
                    break;
                case TUTORIAL:
                    this.tutorialMusic.setCycleCount(Integer.MAX_VALUE);
                    this.tutorialMusic.play();
                    this.menuMusic.stop();
                    this.gameMusic.stop();
                    break;
                case GAME:
                    this.gameMusic.setCycleCount(Integer.MAX_VALUE);
                    this.gameMusic.play();
                    this.menuMusic.stop();
                    this.tutorialMusic.stop();
                    break;
            }
        }
    }

    protected void playSFX(SFX sound) {
        if (!logic.getSetting("Audio","muteSFX",boolean.class)) {
            switch (sound) {
                case ERROR:
                    this.errorSFX.play();
                    break;
                case PAWN_DROP:
                    this.pawnSFX.play();
                    break;
                case DOUBLE_WIN:
                    this.applauseSFX.play();
                case SINGLE_WIN:
                    this.winSFX.play();
                    break;
                case DICE_ROLL:
                    this.diceRollSFX.play();
                    break;
            }
        }
    }
    protected void pauseMusic() {
        this.gameMusic.pause();
        this.menuMusic.pause();
        this.tutorialMusic.pause();
    }
    protected void stopMusic() {
        this.gameMusic.stop();
        this.menuMusic.stop();
        this.tutorialMusic.stop();
    }
}
