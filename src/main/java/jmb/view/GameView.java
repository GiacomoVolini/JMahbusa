package jmb.view;


import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.net.URISyntaxException;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public class GameView extends DynamicGameBoard implements GenericGUI{


    private static final double HORIZONTAL_RESIZE_FACTOR = 0.53;
    private static final double VERTICAL_RESIZE_FACTOR = 0.75;

    private boolean wasBackBTNDisabled;


    @FXML
    AnchorPane window;
    @FXML
    AnchorPane saveAnchorPane;
    @FXML
    Label errorLabel;

    @FXML
    protected Button backBTN;
    @FXML
    Button exitAndSave;

    @FXML
    Button finishBTN;

    @FXML
    Rectangle timerOut;

    @FXML
    Rectangle timerIn;

    @FXML
    Button menuBTN;
    @FXML
    TitledPane startDialogue;

    @FXML
    Button startBTN;

    @FXML
    TitledPane pauseMenu;

    @FXML
    Button exitWithoutSave;

    @FXML
    Button cancelButton;

    @FXML
    Rectangle plBLKInRect;

    @FXML
    Rectangle plBLKOutRect;

    @FXML
    Label plBLKText;

    @FXML
    Rectangle plWHTInRect;

    @FXML
    Rectangle plWHTOutRect;

    @FXML
    Label plWHTText;

    @FXML
    Circle plWHTPawn;

    @FXML
    Circle plBLKPawn;

    @FXML
    Button saveButton;

    @FXML
    Button closeSaveButton;

    @FXML
    TitledPane saveDialogue;

    @FXML
    Text saveLabel;

    @FXML
    TextField saveTextField;

    @FXML
    Text backText;

    @FXML
    Text finishTurnText;

    @FXML
    Text menuText;

    @FXML
    Text rightText;

    @FXML
    Text leftText;

    @FXML
    Text upText;

    @FXML
    Text downText;

    @FXML
    Text selectText;
    @FXML
    Text readyText;
    @FXML
    Text pauseText;


    //Nodes della schermata di vittoria
    Rectangle victoryPanel;
    Circle victoryPawn;
    ImageView victoryCrown;
    ImageView tournamentRibbon;
    Button victoryExit;
    Label victoryLabel;
    Label tournamentWhitePoints;
    Label tournamentBlackPoints;
    ImageView tournamentCup;
    Label tournamentPointsToWin;

    protected Timeline turnTimer;

    @FXML
    protected void nextTurn (ActionEvent event) {
        if(logic.getTurnDuration() != 0) {
            turnTimer.stop();
        }
        logic.completeMoves();
        logic.nextTurn();                   // La parte logica esegue il cambio di turno
        GameViewRedraw.redrawPawns(this);      // Si chiama il ridisegno delle pedine
                                            //   per disabilitare quelle non di turno
        if (logic.getWhichTurn()){
            plWHTOutRect.setFill(Color.GREEN);
            plBLKOutRect.setFill(Color.RED);
        }else {
            plBLKOutRect.setFill(Color.GREEN);
            plWHTOutRect.setFill(Color.RED);
        }
        if (selectedIndex!=UNDEFINED) {
            restoreColorToPoint(selectedIndex);
            selectedIndex = UNDEFINED;
        }
    }

    @FXML
    void saveGame(ActionEvent event) {
        if (!logic.allDiceUsed()) {
            errorLabel.setText(logic.getString("saveErrorCompleteMoves"));
            errorLabel.setVisible(true);
        } else if (saveTextField.getText().equals("")) {
            errorLabel.setText(logic.getString("saveErrorNoName"));
            errorLabel.setVisible(true);
        } else if (!logic.isSaveNamePresent(saveTextField.getText())) {
                logic.saveGame(saveTextField.getText());
                view.stopMusic();
                goToMainMenu();
            } else {
                errorLabel.setText(saveTextField.getText() + " " + logic.getString("errorAlreadyPresent"));
                errorLabel.setVisible(true);
            }
    }

    @FXML
    void closeSaveDialogue(ActionEvent event) {
        saveTextField.setText("");
        errorLabel.setVisible(false);
        saveDialogue.setVisible(false);
    }

    @FXML
    void openExitoption() {
        pauseMenu.setVisible(true);
        if (logic.getTurnDuration()!=0)
            turnTimer.pause();
        GameViewRedraw.resizePauseMenu(this);
        exitWithoutSave.requestFocus();
    }

    @FXML
    void closeExitoption(ActionEvent event) {
        pauseMenu.setVisible(false);
        if (logic.getTurnDuration()!=0)
            turnTimer.play();
        window.requestFocus();
    }

    @FXML
    void goToMainMenu(){
        App.changeRoot(MAIN_MENU);
        view.stopMusic();
        
        if (!logic.getMuteMusic())
            view.playMusic(MENU_MUSIC);
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
        finishBTN.setDisable(true);
        if (!logic.isRollDouble())
            closeDoubleDice();
        diceRollAnimation.setCycleCount(10);
        diceRollAnimation.setOnFinished(e -> {
            DiceView.setDiceValues(diceArray);
            finishBTN.setDisable(false);
            if (logic.isRollDouble()) {
                openDoubleDice();
            }
            if (logic.getTurnDuration() != 0)
                turnTimer.play();
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


    //Metodo per inizio partita
    @FXML
    protected void startGame(ActionEvent event) {
        startDialogue.setVisible(false);
        finishBTN.setDisable(false);
        menuBTN.setDisable(false);

        logic.setGameStart(true);
        changeDimensions();
        if (diceTray.getWidth()==0)
            openDiceTray();
        if(logic.getTurnDuration() != 0) {
            runTimer();
        }
        if (logic.getWhichTurn()){
            plWHTOutRect.setFill(Color.GREEN);
            plBLKOutRect.setFill(Color.RED);
        }else {
            plBLKOutRect.setFill(Color.GREEN);
            plWHTOutRect.setFill(Color.RED);
        }
        GameViewRedraw.redrawPawns(this);
    }

    private void runTimer(){
        turnTimer.setCycleCount(Animation.INDEFINITE);
        turnTimer.play();
    }

    private void gameEndDisable() {
        backBTN.setDisable(true);
        finishBTN.setDisable(true);
        menuBTN.setDisable(true);
        for (PawnView pawn : pawnArrayBLK)
            pawn.setDisable(true);
        for (PawnView pawn : pawnArrayWHT)
            pawn.setDisable(true);
    }

    private void continueTournament() {
        removeVictoryPanel();
        window.getChildren().remove(victoryCrown);
        window.getChildren().remove(victoryExit);
        window.getChildren().remove(victoryLabel);
        window.getChildren().remove(victoryPanel);
        window.getChildren().remove(victoryPawn);
        window.getChildren().remove(tournamentRibbon);
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

    private Rectangle createVictoryPanel() {

        Rectangle victoryPanel = new Rectangle();
        window.getChildren().add(victoryPanel);
        victoryPanel.setFill(Color.WHITESMOKE);
        victoryPanel.setStroke(Color.LIGHTGRAY);
        victoryPanel.setArcHeight(10);
        victoryPanel.setArcWidth(10);
        victoryPanel.setViewOrder(-10);

        return victoryPanel;
    }

    private Circle createVictoryPawn(boolean whiteWon) {
        Circle pawn = new Circle();
        window.getChildren().add(pawn);
        pawn.setViewOrder(-15);
        if (whiteWon) {
            pawn.setFill(Color.web(logic.getWhitePawnFill()));
            pawn.setStroke(Color.web(logic.getWhitePawnStroke()));
        } else {
            pawn.setFill(Color.web(logic.getBlackPawnFill()));
            pawn.setStroke(Color.web(logic.getBlackPawnStroke()));
        }
        pawn.setStrokeWidth(NORMAL_PAWN_STROKE_WIDTH);

        return pawn;

    }

    private Button createVictoryButton(int tournamentStatus) {
        String label;
        boolean tournamentContinues = tournamentStatus == TOURNAMENT_CONTINUES;
        if (tournamentContinues)
            label = logic.getString("continueTournament");
        else label = logic.getString("backToMenu");
        Button victoryExit = new Button(label);
        window.getChildren().add(victoryExit);
        if (tournamentContinues)
            victoryExit.setOnAction(event -> continueTournament());
        else
            victoryExit.setOnAction(event -> gameOver());
        victoryExit.setViewOrder(-16);
        return victoryExit;
    }


    private ImageView createCrownImage( boolean doubleWin) {
        try {
            ImageView crown;
            if (doubleWin) {
                crown = new ImageView(new Image(this.getClass().getResource("victory/crownDouble.png").toURI().toString()));
            } else {
                crown = new ImageView(new Image(this.getClass().getResource("victory/crown.png").toURI().toString()));
            }
            crown.setPreserveRatio(true);
            crown.setViewOrder(-14);
            window.getChildren().add(crown);

            return crown;
        } catch (URISyntaxException use) {
            use.printStackTrace();
            return null;
        }
    }

    private Label createVictoryLabel(String winner, boolean doubleWin, int tournamentStatus) {
        Label victoryLabel = new Label();
        window.getChildren().add(victoryLabel);
        victoryLabel.setWrapText(true);
        String victoryString = logic.getString("congratulations") + " ";
        victoryString = victoryString.concat(winner.stripTrailing());
        if (tournamentStatus == TOURNAMENT_WON)
            victoryString = victoryString.concat(logic.getString("tournamentWon"));
        else if (doubleWin)
                victoryString = victoryString.concat(logic.getString("doubleVictory"));
            else
                victoryString = victoryString.concat(logic.getString("singleVictory"));
        victoryLabel.setText(victoryString);
        victoryLabel.setViewOrder(-15);
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 16));



        return victoryLabel;

    }

    private ImageView createTournamentRibbon() {
        try {
            ImageView tournamentRibbon = new ImageView(new Image(this.getClass().getResource("victory/tournamentRibbon.png").toURI().toString()));
            tournamentRibbon.setPreserveRatio(true);
            tournamentRibbon.setViewOrder(-15);
            window.getChildren().add(tournamentRibbon);
            return tournamentRibbon;
        } catch (URISyntaxException use) {
            use.printStackTrace();
            return null;
        }
    }

    private void removeVictoryPanel() {
        logic.setGameEndState(false);
    }
    protected void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus) {
        gameEndDisable();
        if (logic.getTurnDuration()!=0)
            turnTimer.stop();
        plWHTOutRect.setFill(Color.GRAY);
        plBLKOutRect.setFill(Color.GRAY);
        String winner;
        if (whiteWon)
            winner = whitePlayer;
        else winner = blackPlayer;
        if (doubleWin)
            view.playSFX(DOUBLE_WIN_SFX);
        else view.playSFX(SINGLE_WIN_SFX);
        victoryPanel = createVictoryPanel();    //  Crea il Rettangolo del pannello vittoria
        victoryPawn = createVictoryPawn(whiteWon);     //  Crea il Cerchio per la pedina del pannello vittoria, usando whiteWon per assegnare i colori
        victoryLabel = createVictoryLabel(winner, doubleWin, tournamentStatus);    //  Crea la Label del pannello vittoria con il nome del vincitore
        victoryExit = createVictoryButton(tournamentStatus);    //  Crea il pulsante per il ritorno al menu principale
        victoryCrown = createCrownImage(doubleWin);             //  Crea l'ImageView per la corona del vincitore
        if (tournamentStatus == TOURNAMENT_WON)
            tournamentRibbon = createTournamentRibbon();
        logic.setGameEndState(true);
        GameViewRedraw.resizeVictoryPanel(this);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(victoryPanel.opacityProperty(), 0),
                        new KeyValue(victoryPawn.opacityProperty(), 0), new KeyValue(victoryExit.opacityProperty(), 0),
                        new KeyValue(victoryCrown.opacityProperty(), 0), new KeyValue(victoryLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(victoryPanel.opacityProperty(), 1),
                        new KeyValue(victoryPawn.opacityProperty(), 1), new KeyValue(victoryExit.opacityProperty(), 1),
                        new KeyValue(victoryCrown.opacityProperty(), 1), new KeyValue(victoryLabel.opacityProperty(), 1)
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    void handleKeyboard(KeyEvent event){
        String keyPressed = event.getCode().toString();
        super.handleKeyboard(event);
        if(keyPressed.equals(logic.getOpenMenu())){
            if (!menuBTN.isDisabled())
                openExitoption();
        }
        else if(keyPressed.equals(logic.getFinishTurn())){
            if (!finishBTN.isDisabled())
                nextTurn(null);
        }
        else if (keyPressed.equals(logic.getRevertMove())) {
            if(!backBTN.isDisabled())
                revertMove();
        }
        else if (keyPressed.equals(logic.getSelect()))
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



    //--------------------------------------------
    //METODO INITIALIZE
    //--------------------------------------------

    public void initialize() {
            double xditextfield = saveTextField.getLayoutX() + 40;
            double xdilabel = saveLabel.getLayoutX() + 50;

        try {

            backText.setText(logic.getRevertMove());
            finishTurnText.setText(logic.getFinishTurn());
            menuText.setText(logic.getOpenMenu());
            upText.setText(logic.getString("Up")+"\n" + logic.getMoveUp());
            downText.setText(logic.getString("Down")+"\n" + logic.getMoveDown());
            rightText.setText(logic.getString("Right")+"\n" + logic.getMoveRight());
            leftText.setText(logic.getString("Left")+"\n" +logic.getMoveLeft());
            selectText.setText(logic.getString("Select")+"\n" + logic.getSelect());
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

            if (logic.getLanguage().equals("AR")) {
                saveLabel.setLayoutX(xditextfield);
                saveTextField.setLayoutX(xdilabel);
            }

            setWhoCalled(GAME_CALLED);
            view.stopMusic();
            if (!logic.getMuteMusic())
                view.playMusic(GAME_MUSIC);

            this.boardAnchor = window;
            addChildrenToAnchor();
            window.setFocusTraversable(true);
            window.setOnKeyPressed(this::handleKeyboard);

            GameViewRedraw.setHResizeFactor(HORIZONTAL_RESIZE_FACTOR);
            GameViewRedraw.setVResizeFactor(VERTICAL_RESIZE_FACTOR);
            timerIn.setViewOrder(-2);
            timerOut.setViewOrder(-1.999);

            //musica
            if (logic.getMuteMusic()) {
                view.pauseMusic();
            } else {
                view.playMusic(GAME_MUSIC);
            }

            window.getStylesheets().add(this.getClass().getResource("style.css").toURI().toString());

            //informazione del giocatore
            //nomi
            plWHTText.setText(logic.getWhitePlayer().stripTrailing());
            plBLKText.setText(logic.getBlackPlayer().stripTrailing());
            //colori
            plWHTPawn.setFill(Color.web(logic.getWhitePawnFill()));
            plWHTPawn.setStroke(Color.web(logic.getWhitePawnStroke()));
            plBLKPawn.setFill(Color.web(logic.getBlackPawnFill()));
            plBLKPawn.setStroke(Color.web(logic.getBlackPawnStroke()));
            diceTray.setFill(Color.web(logic.getBoardInnerColor()));
            //turni
            plBLKOutRect.setFill(Color.GRAY);
            plWHTOutRect.setFill(Color.GRAY);

            if (logic.getTurnDuration() == 0) {
                timerIn.setVisible(false);
                timerOut.setVisible(false);
            }

            //Forziamo il rendering delle finestre di pausa e di inizio partita al di sopra delle altre componenti
            //  del tabellone
            startDialogue.setViewOrder(-4);
            pauseMenu.setViewOrder(-4);


            if (logic.getTurnDuration() != 0) {
                //Inizializzo il timer del turno
                turnTimer = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                        new KeyFrame(Duration.seconds(logic.getTurnDuration()), e -> {
                            logic.completeMoves();
                            nextTurn(null);
                        }, new KeyValue(timerIn.scaleYProperty(), 0))
                );
            }

            tournamentCup = new ImageView(new Image(this.getClass().getResource("TournamentCup.png").toURI().toString()));
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
            tournamentWhitePoints.setTextFill(Color.web(logic.getWhitePawnStroke()));
            tournamentBlackPoints.setTextFill(Color.web(logic.getBlackPawnStroke()));

            if (logic.isTournamentOngoing()) {
                tournamentWhitePoints.setVisible(true);
                tournamentBlackPoints.setVisible(true);
                tournamentPointsToWin.setVisible(true);
                tournamentPointsToWin.setViewOrder(-10);
                tournamentCup.setVisible(true);
            } else {
                tournamentWhitePoints.setVisible(false);
                tournamentBlackPoints.setVisible(false);
                tournamentPointsToWin.setVisible(false);
                tournamentCup.setVisible(false);
            }

            //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
            window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

            //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
            window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

