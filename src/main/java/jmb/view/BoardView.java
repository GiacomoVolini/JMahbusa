package jmb.view;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import static jmb.App.getStage;
import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

public class BoardView {


    @FXML
    AnchorPane window;

    @FXML
    Rectangle outerRect;

    @FXML
    Rectangle boardRect;

    @FXML
    Rectangle separator;

    @FXML
    private Polygon point_24;

    @FXML
    private Polygon point_23;

    @FXML
    private Polygon point_22;

    @FXML
    private Polygon point_21;

    @FXML
    private Polygon point_20;

    @FXML
    private Polygon point_19;

    @FXML
    private Polygon point_18;

    @FXML
    private Polygon point_17;

    @FXML
    private Polygon point_16;

    @FXML
    private Polygon point_15;

    @FXML
    private Polygon point_14;

    @FXML
    private Polygon point_13;

    @FXML
    private Polygon point_1;

    @FXML
    private Polygon point_2;

    @FXML
    private Polygon point_3;

    @FXML
    private Polygon point_4;

    @FXML
    private Polygon point_5;

    @FXML
    private Polygon point_6;

    @FXML
    private Polygon point_7;

    @FXML
    private Polygon point_8;

    @FXML
    private Polygon point_9;

    @FXML
    private Polygon point_10;

    @FXML
    private Polygon point_11;

    @FXML
    private Polygon point_12;

    @FXML
    private Region point_1_R;

    @FXML
    private Region point_2_R;

    @FXML
    private Region point_3_R;

    @FXML
    private Region point_4_R;

    @FXML
    private Region point_5_R;

    @FXML
    private Region point_6_R;

    @FXML
    private Region point_7_R;

    @FXML
    private Region point_8_R;

    @FXML
    private Region point_9_R;

    @FXML
    private Region point_10_R;

    @FXML
    private Region point_11_R;

    @FXML
    private Region point_12_R;

    @FXML
    private Region point_24_R;

    @FXML
    private Region point_23_R;

    @FXML
    private Region point_22_R;

    @FXML
    private Region point_21_R;

    @FXML
    private Region point_20_R;

    @FXML
    private Region point_19_R;

    @FXML
    private Region point_18_R;

    @FXML
    private Region point_17_R;

    @FXML
    private Region point_16_R;

    @FXML
    private Region point_15_R;

    @FXML
    private Region point_14_R;

    @FXML
    private Region point_13_R;

    @FXML
    protected Rectangle whiteExitRegion;

    @FXML
    protected Rectangle blackExitRegion;

    @FXML
    Rectangle diceTray;

    @FXML
    protected Button backBTN;

    @FXML
    Button finishBTN;

    @FXML
    Rectangle timerOut;

    @FXML
    Rectangle timerIn;

    @FXML
    Button menuBTN;

    @FXML
    private PawnView pawnBLK01;

    @FXML
    private PawnView pawnBLK02;

    @FXML
    private PawnView pawnBLK03;

    @FXML
    private PawnView pawnBLK04;

    @FXML
    private PawnView pawnBLK05;

    @FXML
    private PawnView pawnBLK06;

    @FXML
    private PawnView pawnBLK07;

    @FXML
    private PawnView pawnBLK08;

    @FXML
    private PawnView pawnBLK09;

    @FXML
    private PawnView pawnBLK10;

    @FXML
    private PawnView pawnBLK11;

    @FXML
    private PawnView pawnBLK12;

    @FXML
    private PawnView pawnBLK13;

    @FXML
    private PawnView pawnBLK14;

    @FXML
    private PawnView pawnBLK15;

    @FXML
    private PawnView pawnWHT01;

    @FXML
    private PawnView pawnWHT02;

    @FXML
    private PawnView pawnWHT03;

    @FXML
    private PawnView pawnWHT04;

    @FXML
    private PawnView pawnWHT05;

    @FXML
    private PawnView pawnWHT06;

    @FXML
    private PawnView pawnWHT07;

    @FXML
    private PawnView pawnWHT08;

    @FXML
    private PawnView pawnWHT09;

    @FXML
    private PawnView pawnWHT10;

    @FXML
    private PawnView pawnWHT11;

    @FXML
    private PawnView pawnWHT12;

    @FXML
    private PawnView pawnWHT13;

    @FXML
    private PawnView pawnWHT14;

    @FXML
    private PawnView pawnWHT15;

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

    /*muscia
    String uriString = new File("C:\\Users\\Ameen\\IdeaProjects\\JMahbusa\\src\\main\\resources\\jmb\\view\\partita.mp3").toURI().toString();
    MediaPlayer playerp = new MediaPlayer( new Media(uriString));
*/


    //  Booleano che indica se l'animazione di diceTray è stata completata
    boolean dtAnimDone = false;

    //  Si creano degli array di Polygon, Region, PawnView ed ImageView per gestire in maniera più agevole il
    //  ridimensionamento dinamico delle componenti

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected Region[] regArrayTop;
    protected Region[] regArrayBot;

    protected PawnView[] pawnArrayWHT;
    protected PawnView[] pawnArrayBLK;

    protected ImageView[] diceArray;        //  L'array segue la numerazione di dice in DiceLogic, con le posizioni
    //      0 e 1 occupate dai dadi standard e 2 e 3 occupate dai dadi doppi

    //Nodes della schermata di vittoria
    Rectangle victoryPanel;
    Circle victoryPawn;
    ImageView victoryCrown;
    Button victoryExit;
    Label victoryLabel;

    protected Timeline turnTimer;

    boolean gameStart = false;

    boolean gameEndState = false;


    @FXML
    protected void nextTurn (ActionEvent event) {
        if(turn_duration != 0) {
            turnTimer.stop();
            turnTimer.play();
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

    }

    @FXML
    protected void openBlackExit() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), 0),
                                new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1),  e-> {
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(blackExitRegion.widthProperty() , BoardViewRedraw.getMaxExitWidth() ),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    protected void openWhiteExit() {

        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), 0),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1), e-> {
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , BoardViewRedraw.getMaxExitWidth() ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    void openExitoption(ActionEvent event) {
        pauseMenu.setVisible(true);
    }

    @FXML
    void closeExitoption(ActionEvent event) {
        pauseMenu.setVisible(false);
    }

    @FXML
    void vaialMainMenu(){
        try {
            senzaSalvare.getScene().getWindow();
            jmb.App.MainMenu();
            getStage().setFullScreen(cb);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
        View.sceneMusica.playerp.stop();
        
        if (!mu) {
            View.sceneMusica.player.play();
        }else{
            View.sceneMusica.player.pause();
        }
    }

    @FXML
    void salvaEMainMenu() {
        getStage().setFullScreen(cb);
        View.sceneMusica.playerp.stop();
        }

    //  Metodo che salva la posizione della pedina prima che essa venga mossa

    @FXML
    private void savePosition (MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        logic.createMoveBuffer(this.searchPawnPlace(node));
    }

    //  Metodo per il trascinamento della pedina
    @FXML
    private void drag(MouseEvent event) {

        PawnView n = (PawnView) event.getSource();

            n.setLayoutX(n.getLayoutX() + event.getX());
            n.setLayoutY(n.getLayoutY() + event.getY());
    }


    @FXML
    private void releasePawn(MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = this.searchPawnPlace(node);
        if (col != UNDEFINED) {
            logic.placePawnOnPoint(col);
        }
        BoardViewRedraw.redrawPawns(this);

    }

    @FXML
    protected void revertMove() {
        logic.revertMove();
        BoardViewRedraw.redrawPawns(this);
    }

    protected void openDoubleDice() {
        if (!diceArray[2].isVisible()) {
            jmb.App.getStage().setResizable(false);
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
                            e -> jmb.App.getStage().setResizable(true),
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
            jmb.App.getStage().setResizable(false);
            Timeline timeline = new Timeline (
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(diceArray[2].layoutYProperty(), diceArray[0].getLayoutY() + diceArray[0].getFitHeight()),
                            new KeyValue(diceArray[3].layoutYProperty(), diceArray[1].getLayoutY() - diceArray[0].getFitHeight())
                    ),
                    new KeyFrame(Duration.seconds(0.5),
                            e -> {
                                jmb.App.getStage().setResizable(true);
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
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.dtAnimDone = true;
                    jmb.App.getStage().setResizable(true);
                    BoardViewRedraw.resizeDice(this);
                    rollDice();
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
        });
        timeline.play();
    }


    private void changeDimensions() {

        BoardViewRedraw.resizeAll(this);

    }

    private int searchPawnPlace(PawnView node) {
        //Il metodo cerca a quale zona del tabellone appartiene la pedina

        //Per ridurre il numero di iterazioni del ciclo for si determina in quale quarto del tabellone sia la pedina
        Region[] array;
        boolean top, found, left;
        int begin, end;
        int out = UNDEFINED;
        if (node.getPawnCenterY() > (boardRect.getLayoutY() + boardRect.getHeight()/2)) {
            array = regArrayBot;
            top = false;
        } else {
            array = regArrayTop;
            top = true;
        }

        if (node.getPawnCenterX() > separator.getLayoutX()) {
            begin = 6;
            end = 11;
            left = false;
        } else {
            begin = 0;
            end = 5;
            left = true;
        }


        if (left && top) {
            if (blackExitRegion.contains(blackExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = 0;
            }
        } else if (left && !top) {
            if (whiteExitRegion.contains(whiteExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = 25;
            }
        }

        //  Ciclo for per controllare se la pedina si trova su una delle sei punte possibili
        if (out == UNDEFINED) {
            found = false;
            for (int i = begin; i <= end && !found; i++) {
                if (array[i].contains(array[i].sceneToLocal(node.getPawnCenter()))) {
                    found = true;
                    if (top) {
                        out = i + 1;
                    } else {
                        out = 24 - i;
                    }
                }
            }
        }
        return out;
    }

    //Metodo per inizio partita
    @FXML
    protected void startGame(ActionEvent event) {
        startDialogue.setVisible(false);
        gameStart = true;
        changeDimensions();
        diceTrayAnim();
        logic.firstTurn();
        if(turn_duration != 0) {
            runTimer();
        }
        window.getChildren().remove(startDialogue);
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
        pawn.setStrokeWidth(2);

        return pawn;

    }

    private Button createVictoryButton() {
        Button victoryExit = new Button("Torna al menu");
        window.getChildren().add(victoryExit);
        victoryExit.setOnAction(event -> vaialMainMenu());
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

    private Label createVictoryLabel(String winner, boolean doubleWin) {
        Label victoryLabel = new Label();
        window.getChildren().add(victoryLabel);
        String victoryString = "Congratulazioni ";
        victoryString = victoryString.concat(winner.stripTrailing());
        if (doubleWin)
            victoryString = victoryString.concat("!\nHai ottenuto una vittoria doppia!");
        else
            victoryString = victoryString.concat("!\nHai vinto la partita!");
        victoryLabel.setText(victoryString);
        //victoryLabel.getStyleClass().add("victory-label");
        victoryLabel.setViewOrder(-15);
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 16));



        return victoryLabel;

    }
/*TODO VECCHIO
    protected void gameWon (boolean whiteWon, boolean doubleWin) {
        gameEndDisable();                       //  Disabilita i Nodes sottostanti (pulsanti e pedine)
        String winner, loser;
        if (whiteWon) {
            winner = n1;
            loser = n2;
        } else {
            winner = n2;
            loser = n1;
        }
        victoryPanel = createVictoryPanel();    //  Crea il Rettangolo del pannello vittoria
        victoryPawn = createVictoryPawn(whiteWon);     //  Crea il Cerchio per la pedina del pannello vittoria, usando whiteWon per assegnare i colori
        victoryLabel = createVictoryLabel(winner, doubleWin);    //  Crea la Label del pannello vittoria con il nome del vincitore
        victoryExit = createVictoryButton();    //  Crea il pulsante per il ritorno al menu principale
        victoryCrown = createCrownImage(doubleWin);             //  Crea l'ImageView per la corona del vincitore
        gameEndState = true;
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
        logic.addStatsToPlayers(winner, loser, doubleWin);
        logic.writeLdbList();
    }
 */
    protected void gameWon(String winner, String loser, boolean whiteWon, boolean doubleWin) {
        gameEndDisable();
        victoryPanel = createVictoryPanel();    //  Crea il Rettangolo del pannello vittoria
        victoryPawn = createVictoryPawn(whiteWon);     //  Crea il Cerchio per la pedina del pannello vittoria, usando whiteWon per assegnare i colori
        victoryLabel = createVictoryLabel(winner, doubleWin);    //  Crea la Label del pannello vittoria con il nome del vincitore
        victoryExit = createVictoryButton();    //  Crea il pulsante per il ritorno al menu principale
        victoryCrown = createCrownImage(doubleWin);             //  Crea l'ImageView per la corona del vincitore
        gameEndState = true;
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
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), BoardViewRedraw.getMaxExitWidth()),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1),  e-> {
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(blackExitRegion.widthProperty() ,  0 ),
                        new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void closeWhiteExit() {

        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), BoardViewRedraw.getMaxExitWidth()),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1), e-> {
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , 0 ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    //--------------------------------------------
    //METODO INITIALIZE
    //--------------------------------------------

    public void initialize() {

        //musica
        if(mu) {
            View.sceneMusica.playerp.pause();
        } else {
            View.sceneMusica.playerp.play();
        }

        window.getStylesheets().add("/jmb/view/style.css");

        //informazione del giocatore
        //nomi
        plWHTText.setText(logic.getWhitePlayer().stripTrailing());
        plBLKText.setText(logic.getBlackPlayer().stripTrailing());
        //colori
        plWHTPawn.setFill(pedIn1);
        plWHTPawn.setStroke(pedOut1);
        plBLKPawn.setFill(pedIn2);
        plBLKPawn.setStroke(pedOut2);
        //turni
        if (logic.getWhichTurn()){
            plWHTOutRect.setFill(green);
            plBLKOutRect.setFill(red);
        }else {
            plBLKOutRect.setFill(green);
            plWHTOutRect.setFill(red);
        }

        //  INIZIALIZZAZIONE ARRAY
        this.polArrayTop = new Polygon[]    {   this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
                this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12               };
        this.regArrayTop = new Region[]     {   this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
                this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R   };
        this.polArrayBot = new Polygon[]    {   this.point_13, this.point_14, this.point_15, this.point_16, this.point_17, this.point_18,
                this.point_19, this.point_20, this.point_21, this.point_22, this.point_23, this.point_24            };
        this.regArrayBot = new Region[]     {   this.point_13_R, this.point_14_R, this.point_15_R, this.point_16_R, this.point_17_R, this.point_18_R,
                this.point_19_R, this.point_20_R, this.point_21_R, this.point_22_R, this.point_23_R, this.point_24_R};
        this.pawnArrayWHT = new PawnView[]     {   this.pawnWHT01, this.pawnWHT02, this.pawnWHT03, this.pawnWHT04, this.pawnWHT05, this.pawnWHT06,
                this.pawnWHT07, this.pawnWHT08, this.pawnWHT09, this.pawnWHT10, this.pawnWHT11, this.pawnWHT12, this.pawnWHT13, this.pawnWHT14, this.pawnWHT15};
        this.pawnArrayBLK = new PawnView[]     {   this.pawnBLK01, this.pawnBLK02, this.pawnBLK03, this.pawnBLK04, this.pawnBLK05, this.pawnBLK06,
                this.pawnBLK07, this.pawnBLK08, this.pawnBLK09, this.pawnBLK10, this.pawnBLK11, this.pawnBLK12, this.pawnBLK13, this.pawnBLK14, this.pawnBLK15};
        this.diceArray = new ImageView[]        { this.diceU, this.diceD, this.doubleDiceU, this.doubleDiceD};

        //Forziamo il rendering delle finestre di pausa e di inizio partita al di sopra delle altre componenti
        //  del tabellone
        startDialogue.setViewOrder(-2);
        pauseMenu.setViewOrder(-2);

        //colori tavolo
        outerRect.setFill(frame);
        outerRect.setStroke(frame);
        separator.setFill(frame);
        separator.setStroke(frame);
        boardRect.setFill(table);
        boardRect.setStroke(table);

        //colori pedine
        for (int i= 0; i<pawnArrayWHT.length; i++){
            this.pawnArrayWHT[i].setFill(pedIn1);
            this.pawnArrayWHT[i].setStroke(pedOut1);
            this.pawnArrayBLK[i].setFill(pedIn2);
            this.pawnArrayBLK[i].setStroke(pedOut2);
        }

        //colori punte
        for (int i=0; i<polArrayTop.length;i++){
            if((i%2)==0){
                this.polArrayTop[i].setFill(point);
                this.polArrayTop[i].setStroke(point);
            }else{
                this.polArrayTop[i].setFill(point2);
                this.polArrayTop[i].setStroke(point2);
            }
        }
        for (int i=0; i<polArrayTop.length;i++){
            if((i%2)==0){
                this.polArrayBot[i].setFill(point2);
                this.polArrayBot[i].setStroke(point2);
            }else{
                this.polArrayBot[i].setFill(point);
                this.polArrayBot[i].setStroke(point);
            }
        }
        if(turn_duration != 0) {
            //Inizializzo il timer del turno
            turnTimer = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                    new KeyFrame(Duration.seconds(turn_duration), e -> {
                        nextTurn(null);
                    }, new KeyValue(timerIn.scaleYProperty(), 0))
            );
        }


        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }
}

