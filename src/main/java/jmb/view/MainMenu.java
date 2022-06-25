package jmb.view;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jmb.model.SaveGameReader;
//import javafx.scene.media.AudioClip;

import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

import java.awt.*;
import java.io.IOException;

import static jmb.App.getStage;
import static jmb.view.View.sceneLoadView;


public class MainMenu {

    @FXML
    private AnchorPane Window;

    @FXML
    private ImageView BackGround1;

    @FXML
    private ImageView background2;

    @FXML
    private Rectangle BGText;

    @FXML
    private Text Text;

    @FXML
    private Button NewGame;

    @FXML
    private Button tutorialButton;

    @FXML
    private Button LoadGame;

    @FXML
    private Button LDB;

    @FXML
    private Button Settings;

    @FXML
    private Button Exit;

    /*muscia
    String uriString = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\musicaMenu.mp3").toURI().toString();
    MediaPlayer player = new MediaPlayer( new Media(uriString));
*/


    @FXML
    void closeButtonAction() {
        Exit.getScene().getWindow();
        jmb.App.getStage().close();
    }

    @FXML
    void newGameAction()  throws IOException {
        NewGame.getScene().getWindow();
        jmb.App.login();
        View.sceneMusica.playerp.pause();
        getStage().setFullScreen(cb);
        View.sceneLogIn.changeDimensions();
    }

    @FXML
    void openLeaderBoard()  throws IOException {
        LDB.getScene().getWindow();
        jmb.App.leaderBoard();
        getStage().setFullScreen(cb);
        View.sceneLeaderboard.table.refresh();
        View.sceneLeaderboard.changeDimensions();

    }

    @FXML
    void openMenuImpostazioni()  throws IOException {
        Settings.getScene().getWindow();
        jmb.App.edit();
        getStage().setFullScreen(cb);
        View.sceneImpostazioni.changeDimensions();
    }

    @FXML
    void openLoadGame() throws IOException {
        jmb.App.loadGame();
        sceneLoadView.refreshSaveList();
        sceneLoadView.setBoardColors();
        sceneLoadView.renderNoSelection();
        getStage().setFullScreen(cb);
    }

    public void initialize() {


        Image im1 = new Image("/jmb/view/background1.jpg");
        Image im2 = new Image("/jmb/view/background2.jpg");
        Image im3 = new Image("/jmb/view/background3.jpg");
        Image im4 = new Image("/jmb/view/background4.jpg");


          //  BackGround.setImage(new Image("/jmb/view/background3.jpg"));
            BackGround1.setPreserveRatio(false);
            background2.setPreserveRatio(false);

          // musica
                if(!mu) {
                    View.sceneMusica.player.play();
                }

           Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(background2.opacityProperty(),0)),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(background2.opacityProperty(),0)),
                new KeyFrame(Duration.seconds(6), e-> {BackGround1.setImage(im3);},
                        new KeyValue(background2.opacityProperty(),1)
                        ),
                new KeyFrame(Duration.seconds(11),
                        new KeyValue(background2.opacityProperty(),1)),
                new KeyFrame(Duration.seconds(12),e-> {background2.setImage(im4);},
                        new KeyValue(background2.opacityProperty(),0)
                        ),
                new KeyFrame(Duration.seconds(17),
                        new KeyValue(background2.opacityProperty(),0)),
                new KeyFrame(Duration.seconds(18), e -> {BackGround1.setImage(im1);},
                        new KeyValue(background2.opacityProperty(),1)),
                new KeyFrame(Duration.seconds(23),
                        new KeyValue(background2.opacityProperty(),1)),
                new KeyFrame(Duration.seconds(24), e->{background2.setImage(im2);},
                        new KeyValue(background2.opacityProperty(),0))
                );
           timeline.setCycleCount(Timeline.INDEFINITE);
           timeline.play();


        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        Window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        Window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


    }


    private void changeDimensions() {

            //  Ridimensiona il titolo

                Text.setLayoutX(Window.getWidth()/2 - 170);
                BGText.setLayoutX(Window.getWidth()/2 - 136);


            //  Ridimensiona i Buttoni rispetto alla finestra principale
            // Largezza

            NewGame.setLayoutX(Window.getWidth()/2 );
            NewGame.setPrefWidth(Window.getWidth()*0.25);
            NewGame.setMaxWidth(89);

            tutorialButton.setLayoutX(Window.getWidth()/2 + 13);
            tutorialButton.setPrefWidth(Window.getWidth()*0.25);
            tutorialButton.setMaxWidth(64);

            LoadGame.setLayoutX(Window.getWidth()/2 + 22);
            LoadGame.setPrefWidth(Window.getWidth()*0.25);
            LoadGame.setMaxWidth(49);

            LDB.setLayoutX(Window.getWidth()/2 + 13);
            LDB.setPrefWidth(Window.getWidth()*0.25);
            LDB.setPrefWidth(64);

            Settings.setLayoutX(Window.getWidth()/2 + 5);
            Settings.setPrefWidth(Window.getWidth()*0.25);
            Settings.setPrefWidth(84);

            Exit.setLayoutX(Window.getWidth()/2 + 3);
            Exit.setPrefWidth(Window.getWidth()*0.25);
            Exit.setPrefWidth(88);

            // Altezza
            Text.setLayoutY(Window.getHeight()/3);
            BGText.setLayoutY(Window.getHeight()/3 - 71);

            NewGame.setLayoutY(Window.getHeight()/2.5);
            NewGame.setPrefHeight(Window.getHeight()*0.25);
            NewGame.setMaxHeight(25);

            tutorialButton.setLayoutY(Window.getHeight()/2.5 + Window.getHeight()/16);
            tutorialButton.setPrefHeight(Window.getHeight()*0.25);
            tutorialButton.setMaxHeight(25);

            LoadGame.setLayoutY(Window.getHeight()/2.5 + Window.getHeight()/16 + Window.getHeight()/16);
            LoadGame.setPrefHeight(Window.getHeight()*0.25);
            LoadGame.setMaxHeight(25);

            LDB.setLayoutY(Window.getHeight()/2.5 + Window.getHeight()/16 + Window.getHeight()/16 + Window.getHeight()/16);
            LDB.setPrefHeight(Window.getHeight()*0.25);
            LDB.setPrefHeight(25);

            Settings.setLayoutY(Window.getHeight()/2.5 + Window.getHeight()/16 + Window.getHeight()/16 + Window.getHeight()/16 + Window.getHeight()/16);
            Settings.setPrefHeight(Window.getHeight()*0.25);
            Settings.setPrefHeight(25);

            Exit.setLayoutY(Window.getHeight()/2.5 + Window.getHeight()/16 + Window.getHeight()/16 + Window.getHeight()/16 + Window.getHeight()/16 + Window.getHeight()/16);
            Exit.setPrefHeight(Window.getHeight()*0.25);
            Exit.setPrefHeight(25);


            BackGround1.setFitWidth(Window.getWidth());
            BackGround1.setFitHeight(Window.getHeight());
            background2.setFitWidth(Window.getWidth());
            background2.setFitHeight(Window.getHeight());


    }

    }
