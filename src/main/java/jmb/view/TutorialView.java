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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public class TutorialView extends DynamicGameBoard implements GenericGUI{

    private final static double HORIZONTAL_RESIZE_FACTOR = 0.6;
    private final static double VERTICAL_RESIZE_FACTOR = 0.8;
    private final static double X_LAYOUT_FACTOR = 0.5;
    private final static double Y_LAYOUT_FACTOR = 0.5;
    protected boolean textBox1ToOpen = true;
    private boolean tutorialOver = false;
    protected double textBoxXFactor = 0.1;
    protected double textBoxYFactor = 0.2;
    @FXML
    Label tutorialOverLabel;
    @FXML
    TitledPane tutorialOverPane;
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
    @FXML
    Button newGameButton;
    @FXML
    Button windowMenuButton;

    private int pointAnimationIndex = 2;
    private int pointAnimationIndexIncrement = 1;
    private Timeline pointAnimation = new Timeline(new KeyFrame(Duration.seconds(0.06),
                                            e -> pointAnimationCycle()));
    private Timeline getPawnOut = new Timeline(
            new KeyFrame(Duration.seconds(0.8),
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
            new KeyFrame(Duration.seconds(2.0),
                    e -> {
                        getPawnOut.setCycleCount(30);
                        getPawnOut.play();
                    })
    );

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
            pawnArrayWHT[i].setOnMouseReleased(this::releasePawn);
        }
        textBox1.setViewOrder(-10);
        textBox2.setVisible(false);
        textBox2.setViewOrder(-10);
        tutorialOverPane.setViewOrder(-10);

        initialAnimation();

        textBox1.setOnMouseClicked(e ->logic.nextTutorialStage());
        textBox2.setOnMouseClicked(e ->logic.nextTutorialStage());
        windowPane.setFocusTraversable(true);
        windowPane.setOnKeyPressed(this::handleKeyboard);
        windowMenuButton.setText(logic.getString("MainMenu"));
        tutorialOverLabel.setText(logic.getString("tutorialOver"));
        mainMenuButton.setText(logic.getString("backToMenu"));
        newGameButton.setText(logic.getString("newGame"));
        tutorialOverPane.setText(logic.getString("congratulations"));


        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        windowPane.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        windowPane.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


    }
    private void initialAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.0),
                        e -> textBoxClick()),
                new KeyFrame(Duration.seconds(1.6),
                        e ->textBox2.setVisible(true))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void textBoxClick() {
        //textBoxAnimation();
        logic.nextTutorialStage();
    }
    protected void textBoxAnimation(double textBoxXFactor, double textBoxYFactor) {
        AnchorPane textBoxToOpen;
        AnchorPane textBoxToClose;

        if (textBox1ToOpen) {
            textBoxToOpen = textBox1;
            textBoxToClose = textBox2;
        } else {
            textBoxToOpen = textBox2;
            textBoxToClose = textBox1;
        }
        textBox1ToOpen = !textBox1ToOpen;
        textBoxToOpen.setLayoutX(windowPane.getWidth()*textBoxXFactor);
        textBoxToOpen.setLayoutY(windowPane.getHeight()*textBoxYFactor);
        setTextBoxXFactor(textBoxXFactor);
        setTextBoxYFactor(textBoxYFactor);
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
            if (tutorialOver)
                tutorialOverPane.setVisible(true);
        });
    }
    protected void setNextTutorialString(String text, boolean changeTextBox) {
        if ((textBox1ToOpen && changeTextBox) || (!textBox1ToOpen && !changeTextBox))
            textBoxLabel1.setText(text);
        else textBoxLabel2.setText(text);
    }
    protected void setTutorialOver() {
        if (textBox1ToOpen)
            textBox1.setVisible(false);
        else textBox2.setVisible(false);
        tutorialOver = true;
    }

    public void changeDimensions() {
        TutorialViewRedraw.resizeAll(this);
        windowPane.requestFocus();
    }
    void handleKeyboard(KeyEvent event) {
        String keyPressed = event.getCode().toString();
        if (logic.getWhichTurn()) {
            boolean pawnMoved = false;
            if (keyPressed.equals(logic.getSetting("Controls", "select", String.class)) && selected)
                pawnMoved = true;
            super.handleKeyboard(event);
            if (keyPressed.equals(logic.getSetting("Controls", "select", String.class)) && pawnMoved)
                logic.tutorialStageAction();
        }
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        App.changeRoot(MAIN_MENU);
        view.playMusic(Music.MENU);
    }

    @FXML
    void startNewGame(ActionEvent event) {
        logic.initializeGameLogic();
        logic.initializeLeaderboardLogic();
        App.changeRoot(LOG_IN);
        view.playMusic(Music.MENU);
    }
    protected void highlightPointsToOpenExit(int stage) {
        if (stage%2==1) {
            //COLORA DEL COLORE DEI GIOCATORI
            for (int i = COL_WHITE; i<=6; i++)
                colorPoint(i, Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)), Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
            for (int i = COL_BLACK; i > 18; i--)
                colorPoint(i, Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)), Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
        } else
        {
            for (int i = COL_WHITE; i<=6; i++)
                restoreColorToPoint(i);
            for (int i = COL_BLACK; i > 18; i--)
                restoreColorToPoint(i);
        }
    }

    protected void pointAnimation(boolean start) {
        if (start) {
            TutorialViewRedraw.resizeAll(this);
            pointAnimation.setCycleCount(Animation.INDEFINITE);
            pointAnimation.play();
        } else {
            pointAnimation.stop();
            int index =pointAnimationIndex-pointAnimationIndexIncrement;
            restoreColorToPoint(index);
        }
    }
    private void pointAnimationCycle() {
        int restoreIndex = pointAnimationIndex - pointAnimationIndexIncrement;
        Color color = Color.RED;
        if (pointAnimationIndexIncrement == 1) {
            color = Color.web(logic.getSetting("Customization", "whitePawnFill", String.class));
        } else if (pointAnimationIndexIncrement == -1) {
            color = Color.web(logic.getSetting("Customization", "blackPawnFill", String.class));
        }
        colorPoint(pointAnimationIndex, color);
        restoreColorToPoint(restoreIndex);
        if (pointAnimationIndex == COL_WHITE)
            pointAnimationIndexIncrement = 1;
        else if (pointAnimationIndex == COL_BLACK)
            pointAnimationIndexIncrement = -1;
        pointAnimationIndex+=pointAnimationIndexIncrement;
    }
    protected void exitZoneAnimation(boolean start) {
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

    protected void diceAnimation(boolean start, boolean infinite, int cycles) {
        if (start) {
            if (infinite)
                diceRollAnimation.setCycleCount(Animation.INDEFINITE);
            else {
                diceRollAnimation.setCycleCount(cycles);
                diceRollAnimation.setOnFinished(e -> {
                    logic.tutorialStageAction();
                    if (logic.isRollDouble()) {
                        openDoubleDice();
                    }
                    DiceView.setDiceValues(diceArray);
                });
            }
            if (!diceTrayOpen) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                        e -> openDiceTray()),
                        new KeyFrame(Duration.seconds(0.55),
                                e -> diceRollAnimation.play())
                );
                timeline.setCycleCount(1);
                timeline.play();
            } else diceRollAnimation.play();
        }
        else {
            diceRollAnimation.stop();
            DiceView.setDiceValues(diceArray);
        }
    }
    protected void allowTextBoxMouseInput (boolean allow) {
        if (allow) {
            textBox1.setOnMouseClicked(e -> textBoxClick());
            textBox2.setOnMouseClicked(e -> textBoxClick());
        } else {
            textBox1.setOnMouseClicked(null);
            textBox2.setOnMouseClicked(null);
        }
    }
    protected void releasePawn(MouseEvent event) {
        super.releasePawn(event);
        logic.tutorialStageAction();
    }

    protected void waitForRecall(double seconds) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds),
                                            e -> logic.tutorialStageAction()));
        timeline.setCycleCount(1);
        timeline.play();
    }
    public double getTextBoxYFactor() {
        return textBoxYFactor;
    }
    public void setTextBoxYFactor(double textBoxYFactor) {
        this.textBoxYFactor = textBoxYFactor;
    }
    public double getTextBoxXFactor() {
        return textBoxXFactor;
    }
    public void setTextBoxXFactor(double textBoxXFactor) {
        this.textBoxXFactor = textBoxXFactor;
    }

}
