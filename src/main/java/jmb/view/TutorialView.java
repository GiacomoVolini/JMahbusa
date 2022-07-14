package jmb.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static jmb.ConstantsShared.*;
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
                - Si parte con solo tabellone, si da benvenuto - FATTO
                - Si piazzano le pedine, si spiega obiettivo del gioco - FATTO
                - Si evidenziano le punte, spiegando cosa sono - FATTO
                - Si aprono le zone di uscita, spiegando cosa sono - FATTO
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
    private int pointAnimationIndex = 1;
    private int pointAnimationIndexIncrement = 1;
    private Timeline pointAnimation = new Timeline(new KeyFrame(Duration.seconds(0.06),
                                            e -> pointAnimationCycle()));
    private Timeline getPawnOut = new Timeline(
            new KeyFrame(Duration.seconds(0.7),
                    e -> {
                        logic.tutorialStageAction();
                        TutorialViewRedraw.redrawPawns(this);
                    })
    );
    private Timeline openZones = new Timeline(
            new KeyFrame(Duration.ZERO,
                    e -> {
                        openWhiteExit();
                        openBlackExit();
                    }),
            new KeyFrame(Duration.seconds(1.0),
                    e -> {
                        getPawnOut.setCycleCount(30);
                        getPawnOut.play();
                    })
    );

    public void initialize() {
        this.boardAnchor = windowPane;
        setWhoCalled(TUTORIAL_CALLED);

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

        textBox1.setOnMouseClicked(e ->textBoxAnimation());
        textBox2.setOnMouseClicked(e ->textBoxAnimation());



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
                        e -> textBoxToClose.setMouseTransparent(true),
                        new KeyValue(textBoxToOpen.scaleXProperty(), 0),
                        new KeyValue(textBoxToOpen.scaleYProperty(), 0),
                        new KeyValue(textBoxToClose.scaleXProperty(), 1),
                        new KeyValue(textBoxToClose.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.5),
                        e -> textBoxToOpen.setMouseTransparent(false),
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

    protected void tutorialPointAnimation(boolean start) {
        if (start) {
            TutorialViewRedraw.resizeAll(this);
            pointAnimation.setCycleCount(Animation.INDEFINITE);
            pointAnimation.play();
        } else {
            pointAnimation.stop();
            int index =pointAnimationIndex-pointAnimationIndexIncrement;
            if (index%2==0)
                colorPoint(index, Color.web(logic.getEvenPointsColor()));
            else colorPoint(index, Color.web(logic.getOddPointsColor()));
        }
    }
    private void pointAnimationCycle() {
        int restoreIndex = pointAnimationIndex - pointAnimationIndexIncrement;
        Color color = Color.RED;
        Color color2;
        if (pointAnimationIndexIncrement == 1) {
            color = Color.web(logic.getWhitePawnFill());
        } else if (pointAnimationIndexIncrement == -1) {
            color = Color.web(logic.getBlackPawnFill());
        }
        colorPoint(pointAnimationIndex, color);
        if (restoreIndex%2==0)
            color2 = Color.web(logic.getEvenPointsColor());
        else color2 = Color.web(logic.getOddPointsColor());
        colorPoint(restoreIndex, color2);
        if (pointAnimationIndex == 0)
            pointAnimationIndexIncrement = 1;
        else if (pointAnimationIndex ==23)
            pointAnimationIndexIncrement = -1;
        pointAnimationIndex+=pointAnimationIndexIncrement;
    }

    private void colorPoint(int index, Color color) {
        if (index<12) {
            polArrayTop[index].setFill(color);
            polArrayTop[index].setStroke(color);
        } else {
            polArrayBot[23 - index].setFill(color);
            polArrayBot[23 - index].setStroke(color);
        }
    }

    protected void tutorialExitZoneAnimation(boolean start) {
        if (start) {
            openZones.setCycleCount(1);
            openZones.play();
        }
        else {
            getPawnOut.stop();
            openZones.stop();
            closeBlackExit();
            closeWhiteExit();
            TutorialViewRedraw.redrawPawns(this);
        }
    }

    protected void tutorialDiceAnimation (boolean start) {
        if (start) {
            diceRollAnimation.setCycleCount(Animation.INDEFINITE);
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                                                    e -> openDiceTray()),
                                            new KeyFrame(Duration.seconds(1.2),
                                                    e -> diceRollAnimation.play())
                                                );
            timeline.setCycleCount(1);
            timeline.play();
        }
        else {
            diceRollAnimation.stop();
        }
    }
    protected void allowTextBoxMouseInput (boolean allow) {
        if (allow) {
            textBox1.setOnMouseClicked(e -> textBoxAnimation());
            textBox2.setOnMouseClicked(e -> textBoxAnimation());
        } else {
            textBox1.setOnMouseClicked(null);
            textBox2.setOnMouseClicked(null);
        }
    }
}
