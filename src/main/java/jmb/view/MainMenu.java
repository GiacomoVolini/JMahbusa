package jmb.view;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import jmb.App;

import java.io.IOException;

public class MainMenu {

    GameBoard C = new GameBoard();

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
    private Button Continue;

    @FXML
    private Button LoadGame;

    @FXML
    private Button LDB;

    @FXML
    private Button Settings;

    @FXML
    private Button Exit;

    @FXML
    void closeButtonAction() {
        Exit.getScene().getWindow();
        jmb.App.getStage().close();
    }

    @FXML
    void openGameBoard()  throws IOException {
        NewGame.getScene().getWindow();
        jmb.App.board();

    }

    @FXML
    void openMenuImpostazioni()  throws IOException {
        Settings.getScene().getWindow();
        jmb.App.edit();

    }

    public void initialize() {


        Image im1 = new Image("/jmb/view/background1.jpg");
        Image im2 = new Image("/jmb/view/background2.jpg");
        Image im3 = new Image("/jmb/view/background3.jpg");
        Image im4 = new Image("/jmb/view/background4.jpg");


          //  BackGround.setImage(new Image("/jmb/view/background3.jpg"));
            BackGround1.setPreserveRatio(false);
            background2.setPreserveRatio(false);


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

            Continue.setLayoutX(Window.getWidth()/2 + 13);
            Continue.setPrefWidth(Window.getWidth()*0.25);
            Continue.setMaxWidth(64);

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

            Continue.setLayoutY(Window.getHeight()/2.5 + Window.getHeight()/16);
            Continue.setPrefHeight(Window.getHeight()*0.25);
            Continue.setMaxHeight(25);

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
