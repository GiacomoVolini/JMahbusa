package jmb.view;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Point2D;

import java.io.IOException;

import static jmb.view.ConstantsView.*;

public class GameBoard {


    @FXML
    private AnchorPane window;

    @FXML
    private Rectangle outerRect;

    @FXML
    private Rectangle boardRect;

    @FXML
    private Rectangle separator;

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
    private LogicPoints point_1_R;

    @FXML
    private LogicPoints point_2_R;

    @FXML
    private LogicPoints point_3_R;

    @FXML
    private LogicPoints point_4_R;

    @FXML
    private LogicPoints point_5_R;

    @FXML
    private LogicPoints point_6_R;

    @FXML
    private LogicPoints point_7_R;

    @FXML
    private LogicPoints point_8_R;

    @FXML
    private LogicPoints point_9_R;

    @FXML
    private LogicPoints point_10_R;

    @FXML
    private LogicPoints point_11_R;

    @FXML
    private LogicPoints point_12_R;

    @FXML
    private LogicPoints point_24_R;

    @FXML
    private LogicPoints point_23_R;

    @FXML
    private LogicPoints point_22_R;

    @FXML
    private LogicPoints point_21_R;

    @FXML
    private LogicPoints point_20_R;

    @FXML
    private LogicPoints point_19_R;

    @FXML
    private LogicPoints point_18_R;

    @FXML
    private LogicPoints point_17_R;

    @FXML
    private LogicPoints point_16_R;

    @FXML
    private LogicPoints point_15_R;

    @FXML
    private LogicPoints point_14_R;

    @FXML
    private LogicPoints point_13_R;

    @FXML
    private Rectangle whiteExitRegion;

    @FXML
    private Rectangle blackExitRegion;

    @FXML
    private Rectangle diceTray;

    @FXML
    private Button backBTN;

    @FXML
    private Button finishBTN;

    @FXML
    private Rectangle timerOut;

    @FXML
    private Rectangle timerIn;

    @FXML
    private Button menuBTN;

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
    private TitledPane Iniziamo;

    @FXML
    private Button iniziaTempo;

    @FXML
    private TitledPane Pause;

    @FXML
    private Button senzaSalvare;

    @FXML
    private Button annulla;



    //TEST
    protected boolean bExit = false;
    protected boolean wExit = false;

    //END TEST

    //  Booleano che indica se l'animazione di diceTray è stata completata
    private boolean dtAnimDone = false;

    //  Si creano degli array di Polygon, LogicPoints e PawnV per gestire in maniera più agevole il
    //  ridimensionamento dinamico delle componenti

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected LogicPoints[] regArrayTop;
    protected LogicPoints[] regArrayBot;

    protected PawnView[] pawnArrayWHT;
    protected PawnView[] pawnArrayBLK;


    //Timer del turno e animazione Timer
    @FXML
    protected void runTimer(ActionEvent event) {
        Iniziamo.setVisible(false);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                new KeyFrame(Duration.minutes(TURN_DURATION), e-> {
                    // TODO gestione cambio turno
                }, new KeyValue(timerIn.scaleYProperty(), 0))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void testBlackExit() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), 0),
                                new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1),  e-> {
                    this.bExit = true; //TEST
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(blackExitRegion.widthProperty() , GameBoardResize.getMaxExitWidth() ),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameBoardResize.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    void openExitoption(ActionEvent event) {
            Pause.setVisible(true);
    }

    @FXML
    void closeExitoption(ActionEvent event) {
            Pause.setVisible(false);
    }

    @FXML
    void vaialMainMenu()  throws IOException {
        senzaSalvare.getScene().getWindow();
        jmb.App.MainMenu();

    }

    //TODO da cancellare
    /*@FXML
    private void testWhiteExit() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), 0),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.wExit = true; //TEST
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , GameBoardResize.getMaxExitWidth() ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameBoardResize.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }anche devi attivare changeDimensions*/

    //  Variabili che memorizzano posizione e regioni di una pedina prima del movimento
    private Point2D prevPosition;
    private int prevRegion;
    private int prevPoint;

    //  Metodo che salva la posizione della pedina prima che essa venga mossa
    @FXML
    private void savePosition (MouseEvent event) {
        Node n = (Node)event.getSource();
        this.prevPosition = new Point2D(n.getLayoutX(), n.getLayoutY());
        this.prevRegion = ((PawnView) n).getPlace();
        this.prevPoint = ((PawnView) n).getWhichPoint();
    }

    //  Metodo per il trascinamento della pedina
    @FXML
    private void drag(MouseEvent event) {
        Node n = (Node)event.getSource();
        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }


    @FXML
    private void releasePawn(MouseEvent event) {
        //Node node = (Node)event.getSource();
        PawnView node = (PawnView)event.getSource();
        boolean done = false;
        for (int i=0; i<regArrayTop.length && !done; i++) {
            if (regArrayTop[i].contains(regArrayTop[i].sceneToLocal(node.getPawnCenter()))) {
                done = true;
                if (this.prevPoint > 0) {
                    if (this.prevRegion == TOP_POINTS) {
                        regArrayTop[this.prevPoint].decPawn();
                    } else {
                        regArrayBot[this.prevPoint].decPawn();
                    }
                }
                node.setLayoutX(regArrayTop[i].getLayoutX() + node.getRadius());
                node.setHowManyBelow(regArrayTop[i].getHowManyPawns());
                if (node.getHowManyBelow() < 5) {
                    node.setLayoutY(regArrayTop[i].getLayoutY() + node.getRadius() * (1 + node.getHowManyBelow() * 2));}
                else {
                    node.setLayoutY(regArrayTop[i].getLayoutY() + node.getRadius() * 9);
                }
                //node.setLayoutY(regArrayTop[i].getLayoutY() + node.getRadius());
                node.setPlace(TOP_POINTS);
                node.setWhichPoint(i);
                regArrayTop[i].incPawn();
            }
        }
        for (int i=0; i<regArrayBot.length && !done; i++) {
            if (regArrayBot[i].contains(regArrayBot[i].sceneToLocal(node.getPawnCenter()))) {
                done = true;
                if (this.prevPoint > 0) {
                    if (this.prevRegion == TOP_POINTS) {
                        regArrayTop[this.prevPoint].decPawn();
                    } else {
                        regArrayBot[this.prevPoint].decPawn();
                    }
                }
                node.setLayoutX(regArrayBot[i].getLayoutX() + node.getRadius());
                node.setHowManyBelow(regArrayBot[i].getHowManyPawns());
                if (node.getHowManyBelow() < 5) {
                    node.setLayoutY(regArrayBot[i].getLayoutY() + regArrayBot[i].getPrefHeight() - node.getRadius() * (1 + node.getHowManyBelow() * 2));
                } else {
                    node.setLayoutY(regArrayBot[i].getLayoutY() + regArrayBot[i].getPrefHeight() - node.getRadius() * 9);
                }
                node.setPlace(BOT_POINTS);
                node.setWhichPoint(i);
                regArrayBot[i].incPawn();
            }
        }

        // FIXME
        //  - Inserire controllo su possibilità uscita del giocatore
        if ((node.getIsWhite()) && whiteExitRegion.contains(whiteExitRegion.sceneToLocal(node.getPawnCenter())) && !done) {
            done = true;
            if (this.prevPoint > 0) {
                if (this.prevRegion == TOP_POINTS) {
                    regArrayTop[this.prevPoint].decPawn();
                } else {
                    regArrayBot[this.prevPoint].decPawn();
                }
            }
            node.setLayoutX(whiteExitRegion.getLayoutX() + node.getRadius());
            node.setLayoutY(whiteExitRegion.getLayoutY() + node.getRadius());
            node.setPlace(WHITE_EXIT_REGION);
        }

        // FIXME
        //  - Inserire controllo su possibilità uscita del giocatore
        if (!(node.getIsWhite()) && blackExitRegion.contains(blackExitRegion.sceneToLocal(node.getPawnCenter())) && !done) {
            done = true;
            if (this.prevPoint > 0) {
                if (this.prevRegion == TOP_POINTS) {
                    regArrayTop[this.prevPoint].decPawn();
                } else {
                    regArrayBot[this.prevPoint].decPawn();
                }
            }
            node.setLayoutX(blackExitRegion.getLayoutX() + node.getRadius());
            node.setLayoutY(blackExitRegion.getLayoutY() + blackExitRegion.getHeight() - node.getRadius());
            node.setPlace(BLACK_EXIT_REGION);
        }

        if (!done) {
            node.setLayoutX(prevPosition.getX());
            node.setLayoutY(prevPosition.getY());
        }

    }


    private void diceTrayAnim() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.dtAnimDone = true;
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(diceTray.widthProperty() , GameBoardResize.getMaxDTWidth() )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }


    private void changeDimensions() {

        GameBoardResize.resizeAll(window, outerRect, boardRect, separator, timerOut, timerIn, polArrayTop,
                                    polArrayBot, regArrayTop, regArrayBot, bExit, wExit, whiteExitRegion,
                                    blackExitRegion, dtAnimDone, diceTray, backBTN, finishBTN, menuBTN,
                                    pawnArrayWHT, pawnArrayBLK, Pause, Iniziamo);
        if(!dtAnimDone) {
            diceTrayAnim();
            testBlackExit();
            //testWhiteExit();
        }

    }

    public void initialize() {

        //  INIZIALIZZAZIONE ARRAY
        this.polArrayTop = new Polygon[]    {   this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
                this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12               };
        this.regArrayTop = new LogicPoints[]     {   this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
                this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R   };
        this.polArrayBot = new Polygon[]    {   this.point_13, this.point_14, this.point_15, this.point_16, this.point_17, this.point_18,
                this.point_19, this.point_20, this.point_21, this.point_22, this.point_23, this.point_24            };
        this.regArrayBot = new LogicPoints[]     {   this.point_13_R, this.point_14_R, this.point_15_R, this.point_16_R, this.point_17_R, this.point_18_R,
                this.point_19_R, this.point_20_R, this.point_21_R, this.point_22_R, this.point_23_R, this.point_24_R};
        this.pawnArrayWHT = new PawnView[]     {   this.pawnWHT01, this.pawnWHT02, this.pawnWHT03, this.pawnWHT04, this.pawnWHT05, this.pawnWHT06,
                this.pawnWHT07, this.pawnWHT08, this.pawnWHT09, this.pawnWHT10, this.pawnWHT11, this.pawnWHT12, this.pawnWHT13, this.pawnWHT14, this.pawnWHT15};
        this.pawnArrayBLK = new PawnView[]     {   this.pawnBLK01, this.pawnBLK02, this.pawnBLK03, this.pawnBLK04, this.pawnBLK05, this.pawnBLK06,
                this.pawnBLK07, this.pawnBLK08, this.pawnBLK09, this.pawnBLK10, this.pawnBLK11, this.pawnBLK12, this.pawnBLK13, this.pawnBLK14, this.pawnBLK15};

        for (int i = 0; i < pawnArrayWHT.length; i++){
            this.pawnArrayWHT[i].setPlace(BOT_POINTS);
            this.pawnArrayWHT[i].setHowManyBelow(i);
            this.pawnArrayWHT[i].setIsWhite(true);
            this.pawnArrayBLK[i].setPlace(TOP_POINTS);
            this.pawnArrayBLK[i].setHowManyBelow(i);
            this.pawnArrayBLK[i].setIsWhite(false);
            this.pawnArrayWHT[i].setWhichPoint(0);
            this.pawnArrayBLK[i].setWhichPoint(0);
        }

        this.regArrayBot[0].setHowManyPawns(15);
        this.regArrayTop[0].setHowManyPawns(15);
        for (int i = 1; i < regArrayTop.length; i++){
            this.regArrayTop[i].setHowManyPawns(0);
            this.regArrayBot[i].setHowManyPawns(0);
        }
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




        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

}

