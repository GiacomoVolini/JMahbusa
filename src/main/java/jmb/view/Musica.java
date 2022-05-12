package jmb.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Musica {

    //String musicaPartita = new File("/jmb/view/partita.mp3").toURI().toString();
    //MediaPlayer playerp = new MediaPlayer( new Media(musicaPartita));
    String musicaPartita = getClass().getResource("/jmb/view/partita.mp3").toString();
    MediaPlayer playerp = new MediaPlayer (new Media (musicaPartita));

    String musicaMenu = getClass().getResource("/jmb/view/musicaMenu.mp3").toString();
    MediaPlayer player = new MediaPlayer( new Media(musicaMenu));

    String musicaPawn = getClass().getResource("/jmb/view/Pawns.mp3").toString();
    AudioClip Pawn = new AudioClip(musicaPawn);

  //  String musicaPawn = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\Pawns.mp3").toURI().toString();
  //  MediaPlayer Pawn = new MediaPlayer( new Media(musicaPawn));
}
