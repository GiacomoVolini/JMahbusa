package jmb.view;


import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.Duration;
import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

/*TODO
    Potremmo far vedere quali sono i comandi per tastiera quando l'utente pigia un tasto (o sotto o vicino ai pulsanti)
 */


public class BoardView extends DynamicGameBoard {


    private static final double HORIZONTAL_RESIZE_FACTOR = 0.53;
    private static final double VERTICAL_RESIZE_FACTOR = 0.75;


    @FXML
    AnchorPane window;
    @FXML
    AnchorPane saveAnchorPane;
    @FXML
    Label errorLabel;
    @FXML
    Rectangle diceTray;

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
    Button senzaSalvare;

    @FXML
    Button annulla;

    @FXML
    ImageView diceD;

    @FXML
    ImageView diceU;

    @FXML
    ImageView doubleDiceD;

    @FXML
    ImageView doubleDiceU;

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


    /*muscia
    String uriString = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\partita.mp3").toURI().toString();
    MediaPlayer playerp = new MediaPlayer( new Media(uriString));
*/


    //  Booleano che indica se l'animazione di diceTray è stata completata
    boolean dtAnimDone = false;

    protected ImageView[] diceArray;        //  L'array segue la numerazione di dice in DiceLogic, con le posizioni
    //      0 e 1 occupate dai dadi standard e 2 e 3 occupate dai dadi doppi

    //Nodes della schermata di vittoria
    Rectangle victoryPanel;
    Circle victoryPawn;
    ImageView victoryCrown;

    ImageView tourmanentRibbon;
    Button victoryExit;
    Label victoryLabel;

    Label tournamentWhitePoints;

    Label tournamentBlackPoints;
    ImageView tournamentCup;
    Label tournamentPointsToWin;

    protected Timeline turnTimer;

    @FXML
    protected void nextTurn (ActionEvent event) {
        if(turn_duration != 0) {
            turnTimer.stop();

        }
        logic.nextTurn();                   // La parte logica esegue il cambio di turno
        BoardViewRedraw.redrawPawns(this);      // Si chiama il ridisegno delle pedine
                                            //   per disabilitare quelle non di turno
        if (logic.getWhichTurn()){
            plWHTOutRect.setFill(green);
            plBLKOutRect.setFill(red);
        }else {
            plBLKOutRect.setFill(green);
            plWHTOutRect.setFill(red);
        }
        //rimettere i colori
        s="";
        L="";
        i=0;
        for (int i=0; i<polArrayTop.length;i++){
            if((i%2)==0){
                this.polArrayTop[i].setFill(Color.web(logic.getEvenPointsColor()));
                this.polArrayTop[i].setStroke(Color.web(logic.getEvenPointsColor()));
            }else{
                this.polArrayTop[i].setFill(Color.web(logic.getOddPointsColor()));
                this.polArrayTop[i].setStroke(Color.web(logic.getOddPointsColor()));
            }
        }
        for (int i=0; i<polArrayTop.length;i++){
            if((i%2)==0){
                this.polArrayBot[i].setFill(Color.web(logic.getOddPointsColor()));
                this.polArrayBot[i].setStroke(Color.web(logic.getOddPointsColor()));
            }else{
                this.polArrayBot[i].setFill(Color.web(logic.getEvenPointsColor()));
                this.polArrayBot[i].setStroke(Color.web(logic.getEvenPointsColor()));
            }
        }

    }

    @FXML
    void saveGame(ActionEvent event) {
        if (!logic.allDiceUsed()) {
            errorLabel.setText("Completa le tue mosse prima di salvare");
            errorLabel.setVisible(true);
        } else if (saveTextField.getText().equals("")) {
            errorLabel.setText("Inserisci un nome per il salvataggio");
            errorLabel.setVisible(true);
        } else if (!logic.isSaveNamePresent(saveTextField.getText())) {
                WritableImage saveImage = this.saveBoardImage();
                logic.saveGame(saveTextField.getText(), saveImage);
                View.sceneMusica.playerp.stop();
                vaialMainMenu();
            } else {
                errorLabel.setText("Nome Salvataggio già presente");
                errorLabel.setVisible(true);
            }
    }

    @FXML
    void closeSaveDialogue(ActionEvent event) {
        saveTextField.setText("");
        errorLabel.setVisible(false);
        saveDialogue.setVisible(false);
    }

    private WritableImage saveBoardImage() {
        SnapshotParameters param = new SnapshotParameters();
        double minX = outerRect.getLayoutX() - max(whiteExitRegion.getWidth(), blackExitRegion.getWidth());
        double minY = outerRect.getLayoutY();
        double width = max(whiteExitRegion.getWidth(), blackExitRegion.getWidth()) + outerRect.getWidth();
        double height = outerRect.getHeight();
        saveDialogue.setVisible(false);
        pauseMenu.setVisible(false);
        timerIn.setVisible(false);
        timerOut.setVisible(false);
        param.setViewport(new Rectangle2D(minX, minY, width, height));
        return window.snapshot(param, null);
    }




    @FXML
    void openExitoption() {
        pauseMenu.setVisible(true);
        if (turn_duration!=0)
            turnTimer.pause();
        BoardViewRedraw.resizePauseMenu(this);
        senzaSalvare.requestFocus();
    }

    @FXML
    void closeExitoption(ActionEvent event) {
        pauseMenu.setVisible(false);
        if (turn_duration!=0)
            turnTimer.play();
        window.requestFocus();
    }

    @FXML
    void vaialMainMenu(){
            App.mainMenu();
        View.sceneMusica.playerp.stop();
        
        if (!mu) {
            View.sceneMusica.player.play();
        }else{
            View.sceneMusica.player.pause();
        }
    }

    @FXML 
    void exitAndSave(ActionEvent event) {
        saveDialogue.setVisible(true);
        saveDialogue.setViewOrder(-50);
    }

    @FXML
    protected void revertMove() {
        logic.revertMove();
        BoardViewRedraw.redrawPawns(this);
    }

    protected void openDoubleDice() {
        if (!diceArray[2].isVisible()) {
            App.getStage().setResizable(false);
            Timeline timeline = new Timeline (
                    new KeyFrame(Duration.ZERO,
                            e -> {
                                diceArray[2].setVisible(true);
                                diceArray[3].setVisible(true);
                            },
                            new KeyValue(diceArray[2].layoutYProperty(), diceArray[0].getLayoutY()),
                            new KeyValue(diceArray[3].layoutYProperty(), diceArray[1].getLayoutY())
                            ),
                    new KeyFrame(Duration.seconds(0.5),
                            e -> App.getStage().setResizable(true),
                            new KeyValue(diceArray[2].layoutYProperty(), diceArray[0].getLayoutY() + diceArray[0].getFitHeight()),
                            new KeyValue(diceArray[3].layoutYProperty(), diceArray[1].getLayoutY() - diceArray[0].getFitHeight())
                    )
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    protected void closeDoubleDice() {
        if (diceArray[2].isVisible()) {
            App.getStage().setResizable(false);
            Timeline timeline = new Timeline (
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(diceArray[2].layoutYProperty(), diceArray[0].getLayoutY() + diceArray[0].getFitHeight()),
                            new KeyValue(diceArray[3].layoutYProperty(), diceArray[1].getLayoutY() - diceArray[0].getFitHeight())
                    ),
                    new KeyFrame(Duration.seconds(0.5),
                            e -> {
                                App.getStage().setResizable(true);
                                diceArray[2].setVisible(false);
                                diceArray[3].setVisible(false);
                            },
                            new KeyValue(diceArray[2].layoutYProperty(), diceArray[0].getLayoutY()),
                            new KeyValue(diceArray[3].layoutYProperty(), diceArray[1].getLayoutY())
                    )
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    private void diceTrayAnim() {
        App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.dtAnimDone = true;
                    App.getStage().setResizable(true);
                    BoardViewRedraw.resizeDice(this);
                    logic.firstTurn();
                }, new KeyValue(diceTray.widthProperty() , BoardViewRedraw.getMaxDTWidth() )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void rollDice() {
        DiceView.setDiceContrast(diceArray);
        finishBTN.setDisable(true);
        if(!logic.isRollDouble())
            closeDoubleDice();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> DiceView.rndRolls(diceArray))
        );
        timeline.setCycleCount(10);
        timeline.setOnFinished( e -> {
            DiceView.setDiceValues(diceArray);
            finishBTN.setDisable(false);
            if (logic.isRollDouble()) {
                openDoubleDice();
            }
            if (logic.getTurnDuration()!=0)
                turnTimer.play();
        });
        timeline.play();
    }


    private void changeDimensions() {
        BoardViewRedraw.resizeAll(this);
        if (pauseMenu.isVisible()) {
            senzaSalvare.requestFocus();
        } else if (startDialogue.isVisible()) {
            startBTN.requestFocus();
        } else {
            window.requestFocus();
        }
        System.out.println(window.getScene().getFocusOwner() + "FHJHJFHSJ");
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
            diceTrayAnim();
        if(turn_duration != 0) {
            runTimer();
        }
        if (logic.getWhichTurn()){
            plWHTOutRect.setFill(green);
            plBLKOutRect.setFill(red);
        }else {
            plBLKOutRect.setFill(green);
            plWHTOutRect.setFill(red);
        }
        BoardViewRedraw.redrawPawns(this);

        //window.getChildren().remove(startDialogue);
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
        window.getChildren().remove(tourmanentRibbon);
        startDialogue.setVisible(true);
        menuBTN.setDisable(false);
        tournamentWhitePoints.setText(String.valueOf(logic.getWhiteTournamentPoints()));
        tournamentBlackPoints.setText(String.valueOf(logic.getBlackTournamentPoints()));
        logic.setUpNewGame();

        this.changeDimensions();
    }

    private void gameOver() {
            logic.addNewPlayersToList(logic.getWhitePlayer(), logic.getBlackPlayer());
            logic.addStatsToLeaderboard();
            App.mainMenu();
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
            pawn.setFill(pedIn1);
            pawn.setStroke(pedOut1);
        } else {
            pawn.setFill(pedIn2);
            pawn.setStroke(pedOut2);
        }
        pawn.setStrokeWidth(NORMAL_PAWN_STROKE_WIDTH);

        return pawn;

    }

    private Button createVictoryButton(int tournamentStatus) {
        String label;
        boolean tournamentContinues = tournamentStatus == TOURNAMENT_CONTINUES;
        if (tournamentContinues)
            label = "Continua torneo";
        else label = "Torna al menu";
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
        ImageView crown;
        if (doubleWin) {
            crown = new ImageView(new Image("/jmb/view/victory/crownDouble.png"));
        } else {
            crown = new ImageView(new Image("jmb/view/victory/crown.png"));
        }
        crown.setPreserveRatio(true);
        crown.setViewOrder(-14);
        window.getChildren().add(crown);

        return crown;
    }

    private Label createVictoryLabel(String winner, boolean doubleWin, int tournamentStatus) {
        Label victoryLabel = new Label();
        window.getChildren().add(victoryLabel);
        String victoryString = "Congratulazioni ";
        victoryString = victoryString.concat(winner.stripTrailing());
        if (tournamentStatus == TOURNAMENT_WON)
            victoryString = victoryString.concat("!\nHai vinto il torneo!");
        else if (doubleWin)
                victoryString = victoryString.concat("!\nHai ottenuto una vittoria doppia!");
            else
                victoryString = victoryString.concat("!\nHai vinto la partita!");
        victoryLabel.setText(victoryString);
        //victoryLabel.getStyleClass().add("victory-label");
        victoryLabel.setViewOrder(-15);
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 16));



        return victoryLabel;

    }

    private ImageView createTournamentRibbon() {
        ImageView tournamentRibbon = new ImageView(new Image("/jmb/view/victory/tournamentRibbon.png"));
        tournamentRibbon.setPreserveRatio(true);
        tournamentRibbon.setViewOrder(-15);
        window.getChildren().add(tournamentRibbon);
        return tournamentRibbon;
    }

    private void removeVictoryPanel() {
        logic.setGameEndState(false);
    }
    protected void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus) {
        gameEndDisable();
        if (turn_duration!=0)
            turnTimer.stop();
        plWHTOutRect.setFill(Color.GRAY);
        plBLKOutRect.setFill(Color.GRAY);
        String winner;
        if (whiteWon)
            winner = whitePlayer;
        else winner = blackPlayer;
        victoryPanel = createVictoryPanel();    //  Crea il Rettangolo del pannello vittoria
        victoryPawn = createVictoryPawn(whiteWon);     //  Crea il Cerchio per la pedina del pannello vittoria, usando whiteWon per assegnare i colori
        victoryLabel = createVictoryLabel(winner, doubleWin, tournamentStatus);    //  Crea la Label del pannello vittoria con il nome del vincitore
        victoryExit = createVictoryButton(tournamentStatus);    //  Crea il pulsante per il ritorno al menu principale
        victoryCrown = createCrownImage(doubleWin);             //  Crea l'ImageView per la corona del vincitore
        if (tournamentStatus == TOURNAMENT_WON)
            tourmanentRibbon = createTournamentRibbon();
        logic.setGameEndState(true);
        BoardViewRedraw.resizeVictoryPanel(this);
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

    protected void closeBlackExit() {
        App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), BoardViewRedraw.getMaxExitWidth()),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1),  e-> {
                    App.getStage().setResizable(true);
                }, new KeyValue(blackExitRegion.widthProperty() ,  0 ),
                        new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void closeWhiteExit() {

        App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), BoardViewRedraw.getMaxExitWidth()),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1), e-> {
                    App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , 0 ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    int i=0;
    String s="";
    String L="";
    String selez="";
    boolean selected = false;
    int col;

    //seleziona l'arrey
    private int trovaColonna(){
        int col = 1;
        for (int i = 0; i < 12; i++) {
            if (polArrayTop[i].getFill().equals(Paint.valueOf("#fffb00"))) {
                col = i + 1;
            } else if (polArrayBot[i].getFill().equals(Paint.valueOf("#fffb00"))) {
                col = (24-i);
            }
        }
        return col;
    }


    @FXML
    void comandaLAtastiera(KeyEvent event) {
        if(event.getCode().toString().equals(logic.getOpenMenu())){
            openExitoption();
        }
        if(event.getCode().toString().equals(logic.getFinishTurn())){
            nextTurn(null);
        }
            //spostamenti per il bianco
         //****************************************************** bianco destra
        if(event.getCode().toString().equals(logic.getMoveRight()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp())) && !s.equals(logic.getMoveDown()) && i>=0 && i <= 11){
            if(L.equals(logic.getMoveLeft()) && i<10){
                L=logic.getMoveRight();
                i+=2;
            } else if(L.equals(logic.getMoveLeft()) && i==10){
                L=logic.getMoveLeft();
                i=0;
            }
            polArrayTop[i].setFill(Paint.valueOf("#fffb00"));
            for(int j=0; j<12;j++){
                if(polArrayTop[j].getFill().equals(Paint.valueOf("#fffb00")) && j!=i){
                    if ((j)%2 == 0){
                        polArrayTop[j].setFill(point);
                    }else {
                        polArrayTop[j].setFill(Color.web(logic.getOddPointsColor()));
                    }
                }
            }
            i++;
            L = event.getCode().toString();
        } else if(i==12 && event.getCode().toString().equals(logic.getMoveDown()) && !s.equals(logic.getMoveDown()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp()))) {
            i=1;
            polArrayTop[0].setFill(Paint.valueOf("#fffb00"));
            polArrayTop[11].setFill(Color.web(logic.getOddPointsColor()));
        } else if( i==-1 && event.getCode().toString().equals(logic.getMoveRight()) && !s.equals(logic.getMoveDown()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp()))){
            i=2;
            polArrayTop[1].setFill(Paint.valueOf("#fffb00"));
            polArrayTop[0].setFill(point);
            L=logic.getMoveRight();
        }

        // BIANCO sinistra
        if(event.getCode().toString().equals(logic.getMoveLeft()) && !s.equals(logic.getMoveDown()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp())) && i>=0 && i <= 11){
            if(L.equals(logic.getMoveRight()) && i>1){
                L=logic.getMoveLeft();
                i-=2;
            } else if(L.equals(logic.getMoveRight()) && i==1){
                L=logic.getMoveLeft();
                i=11;
            }
            polArrayTop[i].setFill(Paint.valueOf("#fffb00"));
            for(int j=0; j<12;j++){
                if(polArrayTop[j].getFill().equals(Paint.valueOf("#fffb00")) && j!=i){
                    if ((j)%2 == 0){
                        polArrayTop[j].setFill(point);
                    }else {
                        polArrayTop[j].setFill(Color.web(logic.getOddPointsColor()));
                    }
                }
            }
            i--;
            L = event.getCode().toString();
        } else if( i<=-1 && event.getCode().toString().equals(logic.getMoveLeft()) && !s.equals(logic.getMoveDown()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp()))) {
            i=10;
            polArrayTop[11].setFill(Paint.valueOf("#fffb00"));
            polArrayTop[0].setFill(point);
        } else if( i==12 && event.getCode().toString().equals(logic.getMoveLeft()) && !s.equals(logic.getMoveDown()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp()))){
            i=9;
            polArrayTop[10].setFill(Paint.valueOf("#fffb00"));
            polArrayTop[11].setFill(Color.web(logic.getOddPointsColor()));
            L=logic.getMoveLeft();
        }

        // da sopra a sotto
        if(event.getCode().toString().equals(logic.getMoveDown()) && (logic.getWhichTurn() || s.equals(logic.getMoveUp()))){
            s =logic.getMoveDown();
            if(L.equals(logic.getMoveRight())){
                polArrayBot[i-1].setFill(Paint.valueOf("#fffb00"));
            }else if(L.equals(logic.getMoveLeft())){
                polArrayBot[i+1].setFill(Paint.valueOf("#fffb00"));
            }
            for (int i=0; i<polArrayTop.length;i++){
                if((i%2)==0){
                    this.polArrayTop[i].setFill(point);
                }else{
                    this.polArrayTop[i].setFill(Color.web(logic.getOddPointsColor()));
                }
            }
        }

        // da sotto a sopra
        if(event.getCode().toString().equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown()))){
            s =logic.getMoveUp();
            if(L.equals(logic.getMoveRight())){
                polArrayTop[i-1].setFill(Paint.valueOf("#fffb00"));
            }else if(L.equals(logic.getMoveLeft())){
                polArrayTop[i+1].setFill(Paint.valueOf("#fffb00"));
            }
            for (int i=0; i<polArrayTop.length;i++){
                if((i%2)==0){
                    this.polArrayBot[i].setFill(Color.web(logic.getOddPointsColor()));
                }else{
                    this.polArrayBot[i].setFill(point);
                }
            }
        }

            //spostamento per il nero
        //******************************************************* nero destra
        if(event.getCode().toString().equals(logic.getMoveRight()) && !s.equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown())) && i>=0 && i <= 11){
            if(L.equals(logic.getMoveLeft()) && i<10){
                L=logic.getMoveRight();
                i+=2;
            } else if(L.equals(logic.getMoveLeft()) && i==10){
                L=logic.getMoveLeft();
                i=0;
            }
            polArrayBot[i].setFill(Paint.valueOf("#fffb00"));
            for(int j=0; j<12;j++){
                if(polArrayBot[j].getFill().equals(Paint.valueOf("#fffb00")) && j!=i){
                    if ((j)%2 == 0){
                        polArrayBot[j].setFill(Color.web(logic.getOddPointsColor()));
                    }else {
                        polArrayBot[j].setFill(point);
                    }
                }
            }
            i++;
            L = event.getCode().toString();
        } else if(i==12 && event.getCode().toString().equals(logic.getMoveRight()) && !s.equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown()))) {
            i=1;
            polArrayBot[0].setFill(Paint.valueOf("#fffb00"));
            polArrayBot[11].setFill(point);
        } else if( i==-1 && event.getCode().toString().equals(logic.getMoveRight()) && !s.equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown()))){
            i=2;
            polArrayBot[1].setFill(Paint.valueOf("#fffb00"));
            polArrayBot[0].setFill(Color.web(logic.getOddPointsColor()));
            L=logic.getMoveRight();
        }

        // nero sinistra
        if(event.getCode().toString().equals(logic.getMoveLeft()) && !s.equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown())) && i>=0 && i <= 11){
            if(L.equals(logic.getMoveRight()) && i>1){
                L=logic.getMoveLeft();
                i-=2;
            } else if(L.equals(logic.getMoveRight()) && i==1){
                L=logic.getMoveLeft();
                i=11;
            }
            polArrayBot[i].setFill(Paint.valueOf("#fffb00"));
            for(int j=0; j<12;j++){
                if(polArrayBot[j].getFill().equals(Paint.valueOf("#fffb00")) && j!=i){
                    if ((j)%2 == 0){
                        polArrayBot[j].setFill(Color.web(logic.getOddPointsColor()));
                    }else {
                        polArrayBot[j].setFill(point);
                    }
                }
            }
            i--;
            L = event.getCode().toString();
        } else if( i<=-1 && event.getCode().toString().equals(logic.getMoveLeft()) && !s.equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown()))) {
           i=10;
           polArrayBot[11].setFill(Paint.valueOf("#fffb00"));
           polArrayBot[0].setFill(Color.web(logic.getOddPointsColor()));
        } else if( i==12 && event.getCode().toString().equals(logic.getMoveLeft()) && !s.equals(logic.getMoveUp()) && (!logic.getWhichTurn() || s.equals(logic.getMoveDown()))){
            i=9;
            polArrayBot[10].setFill(Paint.valueOf("#fffb00"));
            polArrayBot[11].setFill(point);
            L=logic.getMoveLeft();
        }

        //SELEZIONARE LA PEDINA

        if(event.getCode().toString().equals(logic.getSelect()) && !selected){
            col = trovaColonna();
            System.out.println(logic.searchTopOccupiedRow(col));
            System.out.println(col);
            if(logic.searchTopOccupiedRow(col)!=-1 && logic.getBoardPlaceState(col, logic.searchTopOccupiedRow(col))==WHITE && logic.getWhichTurn()){
                logic.thePawnColor(col, logic.searchTopOccupiedRow(col));
                selected = true;
            }else if(logic.searchTopOccupiedRow(col)!=-1 && logic.getBoardPlaceState(col, logic.searchTopOccupiedRow(col))==BLACK && !logic.getWhichTurn()){
                logic.thePawnColor(col, logic.searchTopOccupiedRow(col));
                selected = true;
            }
            logic.createMoveBuffer(col);
            logic.printMatrix();
            BoardViewRedraw.redrawPawns(this);


        }

        // confermare e muovere la pedina
        if(event.getCode().toString().equals(logic.getConfirm()) && selected){
            int col2 = trovaColonna();
            logic.deselectPawn(col, logic.searchTopOccupiedRow(col));
            logic.placePawnOnPoint(col2);
            logic.printMatrix();
            BoardViewRedraw.redrawPawns(this);
            selected = false;
        }
        System.out.println(event.getCode().toString() + i + col);
        System.out.println(event.getCode().toString() + logic.getSelect());
        System.out.println(event.getCode().toString().equals(logic.getSelect()));
        System.out.println("selected = " + selected);

    }


    //--------------------------------------------
    //METODO INITIALIZE
    //--------------------------------------------

    public void initialize() {

        try {

            this.boardAnchor = window;
            addChildrenToAnchor();
            window.setFocusTraversable(true);
            //backBTN.setFocusTraversable(false);
            //finishBTN.setFocusTraversable(false);
            //menuBTN.setFocusTraversable(false);
            //startBTN.setFocusTraversable(false);
            //startDialogue.setFocusTraversable(false);

            BoardViewRedraw.setHResizeFactor(HORIZONTAL_RESIZE_FACTOR);
            BoardViewRedraw.setVResizeFactor(VERTICAL_RESIZE_FACTOR);
            timerIn.setViewOrder(-3);
            timerOut.setViewOrder(-2);

            //musica
            if (mu) {
                View.sceneMusica.playerp.pause();
            } else {
                View.sceneMusica.playerp.play();
            }

            window.getStylesheets().add(this.getClass().getResource("style.css").toURI().toString());

            //informazione del giocatore
            //nomi
            plWHTText.setText(logic.getWhitePlayer().stripTrailing());
            plBLKText.setText(logic.getBlackPlayer().stripTrailing());
            //colori
            plWHTPawn.setFill(pedIn1);
            plWHTPawn.setStroke(pedOut1);
            plBLKPawn.setFill(pedIn2);
            plBLKPawn.setStroke(pedOut2);
            diceTray.setFill(table);
            //turni
            plBLKOutRect.setFill(Color.GRAY);
            plWHTOutRect.setFill(Color.GRAY);

            if (turn_duration == 0) {
                timerIn.setVisible(false);
                timerOut.setVisible(false);
            }

            //  INIZIALIZZAZIONE ARRAY
            this.diceArray = new ImageView[]{this.diceU, this.diceD, this.doubleDiceU, this.doubleDiceD};

            //Forziamo il rendering delle finestre di pausa e di inizio partita al di sopra delle altre componenti
            //  del tabellone
            startDialogue.setViewOrder(-4);
            pauseMenu.setViewOrder(-4);


            if (turn_duration != 0) {
                //Inizializzo il timer del turno
                turnTimer = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                        new KeyFrame(Duration.seconds(turn_duration), e -> {
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
            tournamentWhitePoints.setTextFill(pedOut1);
            tournamentBlackPoints.setTextFill(pedOut2);
            tournamentCup.setBlendMode(BlendMode.DARKEN);

            if (logic.isTournamentOngoing()) {
                tournamentWhitePoints.setVisible(true);
                tournamentBlackPoints.setVisible(true);
                tournamentPointsToWin.setVisible(true);
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

