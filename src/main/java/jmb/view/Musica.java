package jmb.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.time.Duration;

import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

public class Musica {

    //String gameMusicLocation = new File("/jmb/view/partita.mp3").toURI().toString();
    //MediaPlayer gameMusic = new MediaPlayer( new Media(gameMusicLocation));
    String gameMusicLocation = getClass().getResource("/jmb/view/partita.mp3").toString();
    MediaPlayer gameMusic = new MediaPlayer (new Media (gameMusicLocation));

    String menuMusicLocation = getClass().getResource("/jmb/view/musicaMenu.mp3").toString();
    MediaPlayer menuMusic = new MediaPlayer( new Media(menuMusicLocation));

    String pawnSFXLocation = getClass().getResource("/jmb/view/Pawns.mp3").toString();
    AudioClip pawnSFX = new AudioClip(pawnSFXLocation);
    String tutorialMusicLocation = getClass().getResource("/jmb/view/TutorialMusic.mp3").toString();
    MediaPlayer tutorialMusic = new MediaPlayer(new Media(tutorialMusicLocation));

    String errorSFXLocation = getClass().getResource("/jmb/view/ErrorSFX.mp3").toString();
    AudioClip errorSFX = new AudioClip(errorSFXLocation);
    String winSFXLocation = getClass().getResource("/jmb/view/WinSFX.mp3").toString();
    AudioClip winSFX = new AudioClip(winSFXLocation);
    String applauseSFXLocation = getClass().getResource("/jmb/view/ApplauseSFX.mp3").toString();
    AudioClip applauseSFX = new AudioClip(applauseSFXLocation);


  //  String pawnSFXLocation = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\Pawns.mp3").toURI().toString();
  //  MediaPlayer pawnSFX = new MediaPlayer( new Media(pawnSFXLocation));
    protected void setMusicVolume (double value) {
        this.menuMusic.setVolume(value);
        this.gameMusic.setVolume(value);
        this.tutorialMusic.setVolume(value);
    }
    protected void setSFXVolume (double value) {
        this.pawnSFX.setVolume(value);
        this.errorSFX.setVolume(value);
    }
    protected void playMusic(int which) {
        switch (which) {
            case MENU_MUSIC:
                this.menuMusic.setCycleCount(Integer.MAX_VALUE);
                this.menuMusic.play();
                this.tutorialMusic.stop();
                this.gameMusic.stop();
                break;
            case TUTORIAL_MUSIC:
                this.tutorialMusic.setCycleCount(Integer.MAX_VALUE);
                this.tutorialMusic.play();
                this.menuMusic.stop();
                this.gameMusic.stop();
                break;
            case GAME_MUSIC:
                this.gameMusic.setCycleCount(Integer.MAX_VALUE);
                this.gameMusic.play();
                this.menuMusic.stop();
                this.tutorialMusic.stop();
                break;
        }
    }

    protected void playSFX(int which) {
        switch (which) {
            case ERROR_SFX:
                this.errorSFX.play();
                break;
            case PAWN_SFX:
                this.pawnSFX.play();
                break;
            case DOUBLE_WIN_SFX:
                this.applauseSFX.play();
            case SINGLE_WIN_SFX:
                this.winSFX.play();
                break;
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
