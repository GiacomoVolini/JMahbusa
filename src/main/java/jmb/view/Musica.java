package jmb.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

  //  String pawnSFXLocation = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\Pawns.mp3").toURI().toString();
  //  MediaPlayer pawnSFX = new MediaPlayer( new Media(pawnSFXLocation));
    protected void setMusicVolume (double value) {
        this.menuMusic.setVolume(value);
        this.gameMusic.setVolume(value);
    }
    protected void setSFXVolume (double value) {
        this.pawnSFX.setVolume(value);
    }
}
