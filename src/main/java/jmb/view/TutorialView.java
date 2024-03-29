package jmb.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jmb.view.utilities.TimelineBuilder;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.*;

public class TutorialView extends DynamicGameBoard {

    private final static double HORIZONTAL_RESIZE_FACTOR = 0.6;
    private final static double VERTICAL_RESIZE_FACTOR = 0.79;
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
    AnchorPane textBox1, textBox2, window;
    @FXML
    Label textBoxLabel1, textBoxLabel2;
    @FXML
    Rectangle textBoxRectangle1, textBoxRectangle2;
    @FXML
    Button mainMenuButton, newGameButton, windowMenuButton;

    private int pointAnimationIndex = 2;
    private int pointAnimationIndexIncrement = 1;
    private Timeline pointAnimation = new Timeline(new KeyFrame(Duration.seconds(0.06),
                                            e -> pointAnimationCycle()));
    private Timeline getPawnOut = new Timeline(
            new KeyFrame(Duration.seconds(0.8),
                    e -> {
                        getLogic().tutorialStageAction();
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
        super.initialize();
        this.boardAnchor = window;
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

        textBox1.setOnMouseClicked(e ->getLogic().nextTutorialStage());
        textBox2.setOnMouseClicked(e ->getLogic().nextTutorialStage());
        window.setFocusTraversable(true);
        window.setOnKeyPressed(this::handleKeyboard);
        windowMenuButton.setText(getLogic().getString("MainMenu"));
        tutorialOverLabel.setText(getLogic().getString("tutorialOver"));
        mainMenuButton.setText(getLogic().getString("backToMenu"));
        newGameButton.setText(getLogic().getString("newGame"));
        tutorialOverPane.setText(getLogic().getString("congratulations"));

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


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
        getLogic().nextTutorialStage();
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
        textBoxToOpen.setLayoutX(window.getWidth()*textBoxXFactor);
        textBoxToOpen.setLayoutY(window.getHeight()*textBoxYFactor);
        this.textBoxXFactor = textBoxXFactor;
        this.textBoxYFactor = textBoxYFactor;
        Timeline timeline = TimelineBuilder.createTutorialBoxTimeline(textBoxToOpen, textBoxToClose);
        timeline.setOnFinished( e -> {
            if (tutorialOver)
                tutorialOverPane.setVisible(true);
        });
        timeline.play();
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
        window.requestFocus();
    }
    void handleKeyboard(KeyEvent event) {
        String keyPressed = event.getCode().toString();
        if (getLogic().getWhichTurn()) {
            boolean pawnMoved = keyPressed.equals(
                    getLogic().getSetting("Controls", "select", String.class))
                    && selected;
            super.handleKeyboard(event);
            if (keyPressed.equals(getLogic().getSetting("Controls", "select", String.class)) && pawnMoved)
                getLogic().tutorialStageAction();
        }
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        getView().changeRoot(MAIN_MENU);
        if (!getLogic().getSetting("Audio", "muteMusic", boolean.class))
            getView().playMusic(Music.MENU);
    }

    @FXML
    void startNewGame(ActionEvent event) {
        getLogic().initializeGameLogic();
        getLogic().initializeLeaderboardLogic();
        getView().changeRoot(GAME_SETUP);
        if (!getLogic().getSetting("Audio", "muteMusic", boolean.class))
            getView().playMusic(Music.MENU);
    }

    protected void highlightPointsToOpenExit(int stage) {
        if (stage%2==1) {
            for (int i = COL_WHITE; i<=6; i++)
                colorPoint(i, Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class)), Color.web(getLogic().getSetting("Customization", "blackPawnStroke", String.class)));
            for (int i = COL_BLACK; i > 18; i--)
                colorPoint(i, Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class)), Color.web(getLogic().getSetting("Customization", "whitePawnStroke", String.class)));
        } else {
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
        if (pointAnimationIndexIncrement == 1)
            color = Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class));
        else if (pointAnimationIndexIncrement == -1)
            color = Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class));
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
                    getLogic().tutorialStageAction();
                    if (getLogic().isRollDouble()) {
                        openDoubleDice();
                    }
                    DiceView.setDiceValues(diceArray);
                });
            }
            if (diceTray.getWidth() == 0) {
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
        getLogic().tutorialStageAction();
    }

    protected void waitForRecall(double seconds) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds),
                                            e -> getLogic().tutorialStageAction()));
        timeline.setCycleCount(1);
        timeline.play();
    }
    public double getTextBoxYFactor() {
        return textBoxYFactor;
    }
    public double getTextBoxXFactor() {
        return textBoxXFactor;
    }

}
