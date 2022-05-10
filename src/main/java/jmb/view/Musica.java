package jmb.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Musica {

    String musicaPartita = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\partita.mp3").toURI().toString();
    MediaPlayer playerp = new MediaPlayer( new Media(musicaPartita));

    String musicaMenu = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\musicaMenu.mp3").toURI().toString();
    MediaPlayer player = new MediaPlayer( new Media(musicaMenu));

    String musicaPawn = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\Pawns.mp3").toURI().toString();
    AudioClip Pawn = new AudioClip(musicaPawn);

  //  String musicaPawn = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\Pawns.mp3").toURI().toString();
  //  MediaPlayer Pawn = new MediaPlayer( new Media(musicaPawn));
}
