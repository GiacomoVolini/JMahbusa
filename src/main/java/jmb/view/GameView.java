package jmb.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.Duration;
import jmb.view.utilities.AnimationBuilder;

import java.net.URISyntaxException;
import java.util.Objects;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public class GameView extends DynamicGameBoard implements GenericGUI{

    private static final double HORIZONTAL_RESIZE_FACTOR = 0.53;
    private static final double VERTICAL_RESIZE_FACTOR = 0.75;
    private boolean wasBackBTNDisabled;
    @FXML AnchorPane window, saveAnchorPane;
    @FXML Label errorLabel, plBLKText, plWHTText;
    @FXML protected Button backBTN;
    @FXML Button exitAndSave, finishBTN, menuBTN, startBTN, exitWithoutSave, cancelButton,
            saveButton, closeSaveButton;
    @FXML Rectangle timerOut, timerIn, plBLKInRect, plBLKOutRect, plWHTInRect, plWHTOutRect;
    @FXML TitledPane startDialogue, pauseMenu, saveDialogue;
    @FXML Circle plWHTPawn, plBLKPawn;
    @FXML TextField saveTextField;
    @FXML Text saveLabel, backText, finishTurnText, menuText, rightText, leftText, upText, downText, selectText,
            readyText, pauseText;

    //Nodes della schermata di vittoria
    Rectangle victoryPanel;
    Circle victoryPawn;
    ImageView victoryCrown, tournamentRibbon, tournamentCup;
    Button victoryExit;
    Label victoryLabel, tournamentWhitePoints, tournamentBlackPoints, tournamentPointsToWin;
    protected Timeline turnTimer;

    @FXML
    protected void nextTurn (ActionEvent event) {
        if(logic.getTurnDuration() != 0)
            turnTimer.stop();
        logic.completeMoves();
        logic.nextTurn();
        GameViewRedraw.redrawPawns(this);
        setTurnColors(true);
        menuBTN.setDisable(false);
        view.restoreBoardColors();
        selected = false;
        if (selectedIndex!=UNDEFINED) {
            restoreColorToPoint(selectedIndex);
            selectedIndex = UNDEFINED;
        }
    }

    private void setTurnColors(boolean isGameOn) {
        if (isGameOn) {
            if (logic.getWhichTurn()) {
                plWHTOutRect.setFill(Color.GREEN);
                plBLKOutRect.setFill(Color.RED);
            } else {
                plBLKOutRect.setFill(Color.GREEN);
                plWHTOutRect.setFill(Color.RED);
            }
        } else {
            plWHTOutRect.setFill(Color.GRAY);
            plBLKOutRect.setFill(Color.GRAY);
        }
    }

    @FXML
    void saveGame(ActionEvent event) {
        errorLabel.setVisible(true);
        if (!logic.allDiceUsed())
            errorLabel.setText(logic.getString("saveErrorCompleteMoves"));
        else if (saveTextField.getText().equals(""))
            errorLabel.setText(logic.getString("saveErrorNoName"));
        else if (logic.isSaveNamePresent(saveTextField.getText()))
            errorLabel.setText(saveTextField.getText() + " " + logic.getString("errorAlreadyPresent"));
        else {
            logic.saveGame(saveTextField.getText());
            errorLabel.setVisible(false);
            goToMainMenu();
        }
    }

    @FXML
    void closeSaveDialogue(ActionEvent event) {
        saveTextField.setText("");
        errorLabel.setVisible(false);
        saveDialogue.setVisible(false);
    }

    @FXML
    void openExitOption() {
        turnTimer.pause();
        handleExitOption(true, exitWithoutSave);
        GameViewRedraw.resizePauseMenu(this);
    }

    @FXML
    void closeExitOption(ActionEvent event) {
        turnTimer.play();
        handleExitOption(false, window);
    }
    private void handleExitOption (boolean open, Node node) {
        pauseMenu.setVisible(open);
        finishBTN.setDisable(open);
        node.requestFocus();
    }


    @FXML
    void goToMainMenu(){
        App.changeRoot(MAIN_MENU);
    }

    @FXML 
    void exitAndSave(ActionEvent event) {
        saveDialogue.setVisible(true);
        saveDialogue.setViewOrder(-50);
    }

    @FXML
    protected void revertMove() {
        logic.revertMove();
        GameViewRedraw.redrawPawns(this);
    }

    protected void rollDice() {
        DiceView.setDiceContrast(diceArray);
        if(!logic.getSetting("Audio", "muteSFX", boolean.class))
            view.playSFX(SFX.DICE_ROLL);
        finishBTN.setDisable(true);
        if (!logic.isRollDouble())
            closeDoubleDice();
        diceRollAnimation.setCycleCount(10);
        diceRollAnimation.setOnFinished(e -> {
            DiceView.setDiceValues(diceArray);
            if ( !pauseMenu.isVisible() && !logic.getGameEndState()) {
                finishBTN.setDisable(false);
                if (logic.getTurnDuration() != 0)
                    turnTimer.play();
            }
            if (logic.isRollDouble()) {
                openDoubleDice();
            }
            GameViewRedraw.redrawPawns(this);
        });
        diceRollAnimation.play();
    }


    public void changeDimensions() {
        GameViewRedraw.resizeAll(this);
        if (pauseMenu.isVisible()) {
            exitWithoutSave.requestFocus();
        } else if (startDialogue.isVisible()) {
            startBTN.requestFocus();
        } else {
            window.requestFocus();
        }
    }

    @FXML
    protected void startGame(ActionEvent event) {
        startDialogue.setVisible(false);
        finishBTN.setDisable(false);
        menuBTN.setDisable(false);

        logic.setGameStart(true);
        changeDimensions();
        if (diceTray.getWidth()==0) {
            openDiceTray();
        } else logic.firstTurn();
        if(logic.getTurnDuration() != 0) {
            runTimer();
        }
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
        finishBTN.setDisable(true);
        menuBTN.setDisable(true);
        for (PawnView pawn : pawnArrayBLK)
            pawn.setDisable(true);
        for (PawnView pawn : pawnArrayWHT)
            pawn.setDisable(true);
    }

    private void continueTournament() {
        closeWhiteExit();
        closeBlackExit();
        logic.setGameEndState(false);
        removeVictoryScreen();
        startDialogue.setVisible(true);
        menuBTN.setDisable(false);
        tournamentWhitePoints.setText(String.valueOf(logic.getWhiteTournamentPoints()));
        tournamentBlackPoints.setText(String.valueOf(logic.getBlackTournamentPoints()));
        logic.setUpNewBoard();
        this.changeDimensions();
    }

    private void gameOver() {
            logic.addNewPlayersToList(logic.getWhitePlayer(), logic.getBlackPlayer());
            logic.addStatsToLeaderboard();
            App.changeRoot(MAIN_MENU);
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
        window.getChildren().remove(victoryCrown);
        window.getChildren().remove(victoryExit);
        window.getChildren().remove(victoryLabel);
        window.getChildren().remove(victoryPanel);
        window.getChildren().remove(victoryPawn);
        window.getChildren().remove(tournamentRibbon);
    }
    protected void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, TournamentStatus status) {
        gameEndDisable();
        if (logic.getTurnDuration()!=0)
            turnTimer.stop();
        setTurnColors(false);
        String winner;
        if (whiteWon) winner = whitePlayer;
        else winner = blackPlayer;
        if(!logic.getSetting("Audio", "muteSFX", boolean.class)) {
            if (doubleWin)
                view.playSFX(SFX.DOUBLE_WIN);
            else view.playSFX(SFX.SINGLE_WIN);
        }
        createVictoryScreen(whiteWon, doubleWin, winner, status);
        logic.setGameEndState(true);
        GameViewRedraw.resizeVictoryPanel(this);
        animateVictoryScreen(status);
    }

    private void animateVictoryScreen(TournamentStatus status) {
        Timeline timeline;
        if (status.equals(TournamentStatus.TOURNAMENT_WON))
            timeline = AnimationBuilder.createVictoryPanelAnimations(victoryPanel, victoryPawn,
                    victoryExit, victoryCrown, victoryLabel);
        else timeline = AnimationBuilder.createVictoryPanelAnimations(victoryPanel, victoryPawn,
                victoryExit, victoryCrown, victoryLabel, tournamentRibbon);
        timeline.play();
    }

    void handleKeyboard(KeyEvent event){
        String keyPressed = event.getCode().toString();
        super.handleKeyboard(event);
        if(keyPressed.equals(logic.getSetting("Controls", "openMenu", String.class))){
            if (!menuBTN.isDisabled())
                openExitOption();
        }
        else if(keyPressed.equals(logic.getSetting("Controls", "finishTurn", String.class))){
            if (!finishBTN.isDisabled())
                nextTurn(null);
        }
        else if (keyPressed.equals(logic.getSetting("Controls", "revertMove", String.class))) {
            if(!backBTN.isDisabled())
                revertMove();
        }
        else if (keyPressed.equals(logic.getSetting("Controls", "select", String.class)))
            if (selected) {
                finishBTN.setDisable(true);
                wasBackBTNDisabled = backBTN.isDisabled();
                backBTN.setDisable(true);
                menuBTN.setDisable(true);
            } else {
                finishBTN.setDisable(false);
                backBTN.setDisable(wasBackBTNDisabled);
                menuBTN.setDisable(false);
            }
    }

    public void initialize() {

            backText.setText(logic.getSetting("Controls", "revertMove", String.class));
            finishTurnText.setText(logic.getSetting("Controls", "finishTurn", String.class));
            menuText.setText(logic.getSetting("Controls", "openMenu", String.class));
            upText.setText(logic.getString("Up")+"\n" + logic.getSetting("Controls", "moveUp", String.class));
            downText.setText(logic.getString("Down")+"\n" + logic.getSetting("Controls", "moveDown", String.class));
            rightText.setText(logic.getString("Right")+"\n" + logic.getSetting("Controls", "moveRight", String.class));
            leftText.setText(logic.getString("Left")+"\n" +logic.getSetting("Controls", "moveLeft", String.class));
            selectText.setText(logic.getString("Select")+"\n" + logic.getSetting("Controls", "select", String.class));
            backBTN.setText(logic.getString("revertMove").toUpperCase());
            menuBTN.setText(logic.getString("menu").toUpperCase());
            finishBTN.setText(logic.getString("finishTurn").toUpperCase());
            startDialogue.setText(logic.getString("startTitle"));
            readyText.setText(logic.getString("ready"));
            startBTN.setText(logic.getString("yes").toUpperCase());
            pauseMenu.setText(logic.getString("pause"));
            pauseText.setText(logic.getString("pausePrompt"));
            exitWithoutSave.setText(logic.getString("exitNoSave"));
            exitAndSave.setText(logic.getString("exitAndSave"));
            cancelButton.setText(logic.getString("cancel"));
            saveDialogue.setText(logic.getString("saveDialogueTitle"));
            saveLabel.setText(logic.getString("saveName"));
            errorLabel.setText(logic.getString("saveError"));
            saveButton.setText(logic.getString("confirm"));
            closeSaveButton.setText(logic.getString("cancel"));
            saveTextField.setPromptText(logic.getString("savePrompt"));

            if (logic.isLanguageRightToLeft(logic.getSetting("General", "language", String.class))) {
                saveLabel.setLayoutX(saveTextField.getLayoutX() + 40);
                saveTextField.setLayoutX(saveLabel.getLayoutX() + 50);
            }

            // Musica
            if (!logic.getSetting("Audio", "muteMusic", boolean.class))
                view.playMusic(Music.GAME);

            this.boardAnchor = window;
            addChildrenToAnchor();
            window.setFocusTraversable(true);
            window.setOnKeyPressed(this::handleKeyboard);
            window.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("style.css")).toString());

            GameViewRedraw.setHResizeFactor(HORIZONTAL_RESIZE_FACTOR);
            GameViewRedraw.setVResizeFactor(VERTICAL_RESIZE_FACTOR);
            timerIn.setViewOrder(-2);
            timerOut.setViewOrder(-1.999);

            //Nomi dei Giocatori
            plWHTText.setText(logic.getWhitePlayer().stripTrailing());
            plBLKText.setText(logic.getBlackPlayer().stripTrailing());
            //Colori delle pedine
            plWHTPawn.setFill(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
            plWHTPawn.setStroke(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
            plBLKPawn.setFill(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
            plBLKPawn.setStroke(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));

            setTurnColors(false);
            if (logic.getTurnDuration() == 0) {
                timerIn.setVisible(false);
                timerOut.setVisible(false);
            }

            startDialogue.setViewOrder(-4);
            pauseMenu.setViewOrder(-4);

            if (logic.getTurnDuration() != 0) {
                turnTimer = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                        new KeyFrame(Duration.seconds(logic.getTurnDuration()), e -> {
                            logic.completeMoves();
                            nextTurn(null);
                        }, new KeyValue(timerIn.scaleYProperty(), 0))
                );
            }

            tournamentCup = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("TournamentCup.png")).toString()));
            tournamentWhitePoints = new Label(String.valueOf(logic.getWhiteTournamentPoints()));
            tournamentBlackPoints = new Label(String.valueOf(logic.getBlackTournamentPoints()));
            tournamentPointsToWin = new Label(String.valueOf(logic.getTournamentPointsToWin()));
            tournamentWhitePoints.setAlignment(Pos.CENTER);
            tournamentBlackPoints.setAlignment(Pos.CENTER);
            tournamentPointsToWin.setAlignment(Pos.CENTER);
            tournamentPointsToWin.setFont(Font.font("calibri", FontWeight.BOLD, 16));
            tournamentWhitePoints.setFont(Font.font("calibri", FontWeight.BOLD, 16));
            tournamentBlackPoints.setFont(Font.font("calibri", FontWeight.BOLD, 16));
            window.getChildren().addAll(tournamentWhitePoints, tournamentBlackPoints, tournamentPointsToWin, tournamentCup);
            tournamentWhitePoints.setTextFill(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
            tournamentBlackPoints.setTextFill(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));

            setTournamentComponentsVisibility(logic.isTournamentOngoing());
            tournamentPointsToWin.setViewOrder(-10);

            //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
            window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

            //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
            window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());
    }

    private void setTournamentComponentsVisibility (boolean set) {
        tournamentWhitePoints.setVisible(set);
        tournamentBlackPoints.setVisible(set);
        tournamentPointsToWin.setVisible(set);
        tournamentCup.setVisible(set);
    }
}