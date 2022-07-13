package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

public class TutorialView extends DynamicGameBoard{

    private final static double HORIZONTAL_RESIZE_FACTOR = 0.6;
    private final static double VERTICAL_RESIZE_FACTOR = 0.8;
    private final static double X_LAYOUT_FACTOR = 0.5;
    private final static double Y_LAYOUT_FACTOR = 0.5;
    private boolean textBox1ToOpen = true;
    private boolean tutorialOver = false;
    @FXML
    Button completeTutorialButton;
    @FXML
    private Label tutorialOverLabel;
    @FXML
    private TitledPane tutorialOverPane;
    @FXML
    AnchorPane textBox1;
    @FXML
    AnchorPane textBox2;
    @FXML
    Label textBoxLabel1;
    @FXML
    Label textBoxLabel2;
    @FXML
    Rectangle textBoxRectangle1;
    @FXML
    Rectangle textBoxRectangle2;
    @FXML
    AnchorPane windowPane;
    @FXML
    Button mainMenuButton;

    /*TODO
        Idea generale di TutorialView
            - Tutorial con delle TextBox che compaiono e scompaiono di volta in volta e del testo che spiega cosa fare
            - Magari interattivo per quanto possibile
            -
            - Idea
                - Si parte con solo tabellone, si da benvenuto
                - Si piazzano le pedine, si spiega obiettivo del gioco
                - Si evidenziano le punte, spiegando cosa sono
                - Si aprono le zone di uscita, spiegando cosa sono
                - Chiudi zone di uscita, apri cassetto dei dadi
                - Fa partire animazione dei dadi, in loop infinito, spiega dadi
                - Ferma animazione in risultato, permetti a giocatore di muovere
                - Dopo la mossa del giocatore, fai tiro per altro giocatore. Risultati fissi in base alla mossa del giocatore
                    - Se si riesce mostrare tiro doppio
                - Prepara situazione per far bloccare la pedina al giocatore, informalo
                - Fagli vedere che può impilare quante pedine vuole se non è già bloccata dall'altro
                - Cambia situazione pedine, manca solo una per aprire la zona di uscita
                    - Fare in modo che si possa muovere solo quella pedina
                - Mostrare apertura zona uscita, spiegare
                - Spiegare metodo per vittoria singola
                - Spiegare metodi per vittoria doppia
                - Far sparire pedine gradualmente
                - Chiudere cassetti
                - Aprire menu: si vuole cominciare una partita o tornare al menu?
     */

    public void initialize() {
        this.boardAnchor = windowPane;

        addChildrenToAnchor();
        TutorialViewRedraw.setHResizeFactor(HORIZONTAL_RESIZE_FACTOR);
        TutorialViewRedraw.setVResizeFactor(VERTICAL_RESIZE_FACTOR);
        TutorialViewRedraw.setXLayoutFactor(X_LAYOUT_FACTOR);
        TutorialViewRedraw.setYLayoutFactor(Y_LAYOUT_FACTOR);

        for (int i = 0; i<15; i++) {
            pawnArrayWHT[i].setVisible(false);
            pawnArrayBLK[i].setVisible(false);
        }
        textBox1.setViewOrder(-10);
        textBox2.setVisible(false);
        textBox2.setViewOrder(-10);
        tutorialOverPane.setViewOrder(-10);

        initialAnimation();

        //TODO TEST
        windowPane.setOnMouseClicked(e ->textBoxAnimation());



        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        windowPane.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        windowPane.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


    }
    private void initialAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.0),
                        e -> textBoxAnimation()),
                new KeyFrame(Duration.seconds(1.6),
                        e ->textBox2.setVisible(true))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void textBoxAnimation() {
        AnchorPane textBoxToOpen;
        AnchorPane textBoxToClose;
        logic.nextTutorialStage();
        // String tutorialText = logic.getNextTutorialString(); TODO
        if (textBox1ToOpen) {
            textBoxToOpen = textBox1;
            textBoxToClose = textBox2;
        } else {
            textBoxToOpen = textBox2;
            textBoxToClose = textBox1;
        }
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(textBoxToOpen.scaleXProperty(), 0),
                        new KeyValue(textBoxToOpen.scaleYProperty(), 0),
                        new KeyValue(textBoxToClose.scaleXProperty(), 1),
                        new KeyValue(textBoxToClose.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(textBoxToOpen.scaleXProperty(), 1),
                        new KeyValue(textBoxToOpen.scaleYProperty(), 1),
                        new KeyValue(textBoxToClose.scaleXProperty(), 0),
                        new KeyValue(textBoxToClose.scaleYProperty(), 0))
                );
        timeline.setCycleCount(1);
        timeline.play();
        timeline.setOnFinished( e -> {
            textBox1ToOpen = !textBox1ToOpen;
            if (tutorialOver)
                tutorialOverPane.setVisible(true);
        });
    }
    protected void setNextTutorialStage(String text) {
        if (textBox1ToOpen)
            textBoxLabel1.setText(text);
        else textBoxLabel2.setText(text);
    }
    protected void setTutorialOver() {
        if (textBox1ToOpen)
            textBox1.setVisible(false);
        else textBox2.setVisible(false);
        tutorialOver = true;
    }

    private void changeDimensions() {
        TutorialViewRedraw.resizeAll(this);

    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        App.changeRoot(MAIN_MENU);
    }
}
