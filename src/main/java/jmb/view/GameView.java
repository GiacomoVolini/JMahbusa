package jmb.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import jmb.view.game.*;
import jmb.view.utilities.TimelineBuilder;

import java.util.Objects;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.*;

public class GameView extends DynamicGameBoard {

    private static final double HORIZONTAL_RESIZE_FACTOR = 0.53;
    private static final double VERTICAL_RESIZE_FACTOR = 0.75;
    private boolean wasBackBTNDisabled;
    @FXML AnchorPane window;
    @FXML AnchorPane keyboardInfo;
    @FXML Label plBLKText, plWHTText;
    @FXML protected Button backBTN;
    @FXML Button finishBTN, menuBTN, startBTN;
    @FXML Rectangle timerOut, timerIn, plBLKInRect, plBLKOutRect, plWHTInRect, plWHTOutRect;
    @FXML TitledPane startDialogue;
    @FXML Circle plWHTPawn, plBLKPawn;
    @FXML Text backText, finishTurnText, menuText, rightText, leftText, upText, downText, selectText,
            readyText;
    @FXML PausePane pausePaneController;
    @FXML SaveGamePane saveGamePaneController;

    //Nodes della schermata di vittoria
    Rectangle victoryPanel;
    Circle victoryPawn;
    ImageView victoryCrown, tournamentRibbon, tournamentCup;
    Button victoryExit;
    Label victoryLabel, tournamentWhitePoints, tournamentBlackPoints, tournamentPointsToWin;
    protected Timeline turnTimer;

    @FXML
    protected void nextTurn (ActionEvent event) {
        if(getLogic().getTurnDuration() != 0)
            turnTimer.stop();
        getLogic().completeMoves();
        getLogic().nextTurn();
        GameViewRedraw.redrawPawns(this);
        setTurnColors(true);
        menuBTN.setDisable(false);
        restoreBoardColors();
        selected = false;
        restoreColorToPoint(selectedIndex);
        selectedIndex = UNDEFINED;
    }

    private void setTurnColors(boolean isGameOn) {
        if (isGameOn) {
            if (getLogic().getWhichTurn()) {
                plWHTOutRect.setFill(Color.web("#e1a751"));
                plBLKOutRect.setFill(Color.web("#43300a"));
            } else {
                plWHTOutRect.setFill(Color.web("#43300a"));
                plBLKOutRect.setFill(Color.web("#e1a751"));
            }
        } else {
            plWHTOutRect.setFill(Color.web("#43300a"));
            plBLKOutRect.setFill(Color.web("#43300a"));
        }
    }

    @FXML
    void openExitOption() {
        turnTimer.pause();
        pausePaneController.openExitOption();
    }

    @FXML
    protected void revertMove() {
        getLogic().revertMove();
        GameViewRedraw.redrawPawns(this);
    }

    protected void rollDice() {
        DiceView.setDiceContrast(diceArray);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.DICE_ROLL);
        finishBTN.setDisable(true);
        if (!getLogic().isRollDouble())
            closeDoubleDice();
        diceRollAnimation.setCycleCount(10);
        diceRollAnimation.setOnFinished(e -> {
            DiceView.setDiceValues(diceArray);
            if ( !pausePaneController.isVisible() && !getLogic().getGameEndState()) {
                finishBTN.setDisable(false);
                if (getLogic().getTurnDuration() != 0)
                    turnTimer.play();
            }
            if (getLogic().isRollDouble()) {
                openDoubleDice();
            }
            GameViewRedraw.redrawPawns(this);
        });
        diceRollAnimation.play();
    }

    public void closeKeyboardInfo (MouseEvent event){
        if(keyboardInfo.getWidth()> 107){
            GameViewRedraw.informationVisibility(this, false);
            keyboardInfo.setPrefWidth(107);
            keyboardInfo.setPrefHeight(38);
            AnchorPane.setLeftAnchor(rightText, 5.0);
            rightText.setText(getLogic().getString("keyboardInfo"));
        }else{
            keyboardInfo.setPrefWidth(684);
            keyboardInfo.setPrefHeight(58);
            GameViewRedraw.informationVisibility(this, true);
            AnchorPane.setLeftAnchor(rightText, 28.0);
            rightText.setText(getLogic().getString("Right")+"\n" + getLogic().getSetting("Controls", "moveRight", String.class));
        }
        GameViewRedraw.resizeButtons(this);
    }

    public void changeDimensions() {
        GameViewRedraw.resizeAll(this);
        pausePaneController.changeDimensions();
        saveGamePaneController.changeDimensions();
        if (startDialogue.isVisible()) {
            startBTN.requestFocus();
        } else {
            window.requestFocus();
        }
    }

    @FXML
    protected void startGame(ActionEvent event) {
        startDialogue.setVisible(false);
        disableMenuAndFinishButtons(false);
        getLogic().setGameStart(true);
        changeDimensions();
        if (diceTray.getWidth()==0)
            openDiceTray();
        else getLogic().firstTurn();
        if(getLogic().getTurnDuration() != 0)
            runTimer();
        setTurnColors(true);
        DiceView.setDiceContrast(diceArray);
        GameViewRedraw.redrawPawns(this);
    }

    private void runTimer(){
        turnTimer.setCycleCount(Animation.INDEFINITE);
        turnTimer.play();
    }

    private void gameEndDisable() {
        turnTimer.stop();
        backBTN.setDisable(true);
        disableMenuAndFinishButtons(true);
        for (PawnView pawn : pawnArrayBLK)
            pawn.setDisable(true);
        for (PawnView pawn : pawnArrayWHT)
            pawn.setDisable(true);
    }

    private void continueTournament() {
        closeWhiteExit();
        closeBlackExit();
        getLogic().setGameEndState(false);
        removeVictoryScreen();
        startDialogue.setVisible(true);
        menuBTN.setDisable(false);
        tournamentWhitePoints.setText(String.valueOf(getLogic().getWhiteTournamentPoints()));
        tournamentBlackPoints.setText(String.valueOf(getLogic().getBlackTournamentPoints()));
        getLogic().setUpNewBoard();
        this.changeDimensions();
    }

    private void gameOver() {
        getLogic().addNewPlayersToList(getLogic().getWhitePlayer(), getLogic().getBlackPlayer());
        getLogic().addStatsToLeaderboard();
        getView().changeRoot(MAIN_MENU);
    }

    private void createVictoryScreen(boolean whiteWon, boolean doubleWin, String winner, TournamentStatus status) {
        victoryPanel = VictoryComponentFactory.createVictoryPanel(window);
        victoryPawn = VictoryComponentFactory.createVictoryPawn(whiteWon, window);
        victoryLabel = VictoryComponentFactory.createVictoryLabel(winner, doubleWin, status, window);
        victoryExit = VictoryComponentFactory.createVictoryButton(status, window);
        if (status == TournamentStatus.TOURNAMENT_CONTINUES)
            victoryExit.setOnAction(event -> continueTournament());
        else
            victoryExit.setOnAction(event -> gameOver());
        victoryCrown = VictoryComponentFactory.createCrownImage(doubleWin, window);
        if (status.equals(TournamentStatus.TOURNAMENT_WON))
            tournamentRibbon = VictoryComponentFactory.createTournamentRibbon(window);
    }

    private void removeVictoryScreen() {
        window.getChildren().removeAll(victoryCrown, victoryExit, victoryLabel,
                victoryPanel, victoryPawn, tournamentRibbon);
    }
    protected void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, TournamentStatus status) {
        String winner;
        gameEndDisable();
        if (getLogic().getTurnDuration()!=0)
            turnTimer.stop();
        setTurnColors(false);
        if (whiteWon)
            winner = whitePlayer;
        else winner = blackPlayer;
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            if (doubleWin)
                getView().playSFX(SFX.DOUBLE_WIN);
            else getView().playSFX(SFX.SINGLE_WIN);
        createVictoryScreen(whiteWon, doubleWin, winner, status);
        getLogic().setGameEndState(true);
        GameViewRedraw.resizeVictoryPanel(this);
        animateVictoryScreen(status);
    }

    private void animateVictoryScreen(TournamentStatus status) {
        Timeline timeline;
        if (status.equals(TournamentStatus.TOURNAMENT_WON))
            timeline = TimelineBuilder.createVictoryPanelAnimations(victoryPanel, victoryPawn,
                    victoryExit, victoryCrown, victoryLabel);
        else timeline = TimelineBuilder.createVictoryPanelAnimations(victoryPanel, victoryPawn,
                victoryExit, victoryCrown, victoryLabel, tournamentRibbon);
        timeline.play();
    }

    void handleKeyboard(KeyEvent event){
        String keyPressed = event.getCode().toString();
        super.handleKeyboard(event);
        if(keyPressed.equals(getLogic().getSetting("Controls", "openMenu", String.class))) {
            if (!menuBTN.isDisabled())
                openExitOption();
        } else if(keyPressed.equals(getLogic().getSetting("Controls", "finishTurn", String.class))) {
            if (!finishBTN.isDisabled())
                nextTurn(null);
        } else if (keyPressed.equals(getLogic().getSetting("Controls", "revertMove", String.class))) {
            if (!backBTN.isDisabled())
                revertMove();
        }else if (keyPressed.equals(getLogic().getSetting("Controls", "select", String.class))) {
            if (selected) {
                wasBackBTNDisabled = backBTN.isDisabled();
                backBTN.setDisable(true);
                disableMenuAndFinishButtons(true);
            } else {
                if (backBTN.isDisabled())
                    backBTN.setDisable(wasBackBTNDisabled);
                disableMenuAndFinishButtons(false);
            }
        }
    }

    public void disableMenuAndFinishButtons(boolean set) {
        finishBTN.setDisable(set);
        menuBTN.setDisable(set);
    }

    public void initialize() {
        super.initialize();
        saveGamePaneController.initialize(this);
        pausePaneController.initialize(this, saveGamePaneController);
        GameViewInitializer.setStrings(backText, finishTurnText, menuText, upText, downText, rightText, leftText,
                selectText, backBTN, menuBTN, finishBTN, readyText, startDialogue, startBTN, plWHTText, plBLKText);
        tournamentCup = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("TournamentCup.png")).toString()));
        tournamentWhitePoints = new Label(String.valueOf(getLogic().getWhiteTournamentPoints()));
        tournamentBlackPoints = new Label(String.valueOf(getLogic().getBlackTournamentPoints()));
        tournamentPointsToWin = new Label(String.valueOf(getLogic().getTournamentPointsToWin()));
        GameViewInitializer.setTournamentComponents(tournamentWhitePoints, tournamentBlackPoints, tournamentPointsToWin);
        window.getChildren().addAll(tournamentWhitePoints, tournamentBlackPoints, tournamentPointsToWin, tournamentCup);
        GameViewInitializer.setColors(plWHTPawn, plBLKPawn, tournamentWhitePoints, tournamentBlackPoints);

        if (!getLogic().getSetting("Audio", "muteMusic", boolean.class))
            getView().playMusic(Music.GAME);

        this.boardAnchor = window;
        addChildrenToAnchor();
        window.setFocusTraversable(true);
        window.setOnKeyPressed(this::handleKeyboard);
        window.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("style.css")).toString());

        GameViewRedraw.setHResizeFactor(HORIZONTAL_RESIZE_FACTOR);
        GameViewRedraw.setVResizeFactor(VERTICAL_RESIZE_FACTOR);
        GameViewRedraw.setXLayoutFactor(0.65);

        setTurnColors(false);
        if (getLogic().getTurnDuration() == 0) {
            timerIn.setVisible(false);
            timerOut.setVisible(false);
        }

        if (getLogic().getTurnDuration() != 0) {
            turnTimer = TimelineBuilder.createTurnTimerTimeline(timerIn);
            turnTimer.setOnFinished(e -> nextTurn(null));
        }
        GameViewInitializer.setTournamentComponentsVisibility(getLogic().isTournamentOngoing(), tournamentWhitePoints,
                tournamentBlackPoints, tournamentPointsToWin, tournamentCup);
        GameViewInitializer.setViewOrders(timerIn, timerOut, startDialogue, tournamentPointsToWin);

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        //  LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());
    }

    public Timeline getTurnTimer() {
        return turnTimer;
    }

    public AnchorPane getWindowPane() {
        return window;
    }
}