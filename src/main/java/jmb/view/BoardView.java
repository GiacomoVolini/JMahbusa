package jmb.view;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static jmb.ConstantsShared.UNDEFINED;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

public class BoardView {


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
    private Rectangle whiteExitRegion;

    @FXML
    private Rectangle blackExitRegion;

    @FXML
    private Rectangle diceD;

    @FXML
    private Rectangle diceTray;

    @FXML
    private Rectangle diceU;

    @FXML
    private Rectangle doubleDiceD;

    @FXML
    private Rectangle doubleDiceU;

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



    //TEST
    protected boolean bExit = false;
    protected boolean wExit = false;

    //END TEST

    //  Booleano che indica se l'animazione di diceTray è stata completata
    private boolean dtAnimDone = false;

    //  Si creano degli array di Polygon, Region e PawnView per gestire in maniera più agevole il
    //  ridimensionamento dinamico delle componenti

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected Region[] regArrayTop;
    protected Region[] regArrayBot;

    protected PawnView[] pawnArrayWHT;
    protected PawnView[] pawnArrayBLK;


    @FXML
    protected void nextTurn (ActionEvent event) {
        //TODO
    }

    @FXML
    protected void openBlackExit() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), 0),
                                new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1),  e-> {
                    this.bExit = true; //TEST
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
                    this.wExit = true; //TEST
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , BoardViewRedraw.getMaxExitWidth() ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - BoardViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
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
        BoardViewRedraw.redrawPawns(pawnArrayWHT, pawnArrayBLK, regArrayBot,
                regArrayTop, whiteExitRegion, blackExitRegion);

    }




    private void diceTrayAnim() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.dtAnimDone = true;
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(diceTray.widthProperty() , BoardViewRedraw.getMaxDTWidth() )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }


    private void changeDimensions() {

        BoardViewRedraw.resizeAll(window, outerRect, boardRect, separator, timerOut, timerIn, polArrayTop,
                                    polArrayBot, regArrayTop, regArrayBot, bExit, wExit, whiteExitRegion,
                                    blackExitRegion, dtAnimDone, diceTray, backBTN, finishBTN, menuBTN,
                                    pawnArrayWHT, pawnArrayBLK, diceU, diceD, doubleDiceU, doubleDiceD);
        if(!dtAnimDone) {
            diceTrayAnim();
            //openBlackExit();
            //openWhiteExit();
        }

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



    //--------------------------------------------
    //METODO INITIALIZE
    //--------------------------------------------

    public void initialize() {

        //  INIZIALIZZAZIONE ARRAY
        this.polArrayTop = new Polygon[]    {   this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
                this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12               };
        this.regArrayTop = new Region[]     {   this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
                this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R   };
        this.polArrayBot = new Polygon[]    {   this.point_13, this.point_14, this.point_15, this.point_16, this.point_17, this.point_18,
                this.point_19, this.point_20, this.point_21, this.point_22, this.point_23, this.point_24            };
        this.regArrayBot = new Region[]     {   this.point_13_R, this.point_14_R, this.point_15_R, this.point_16_R, this.point_17_R, this.point_18_R,
                this.point_19_R, this.point_20_R, this.point_21_R, this.point_22_R, this.point_23_R, this.point_24_R};
        //this.regArrayBot = new Region[]     {   this.point_24_R, this.point_23_R, this.point_22_R, this.point_21_R, this.point_20_R, this.point_19_R,
          //      this.point_18_R, this.point_17_R, this.point_16_R, this.point_15_R, this.point_14_R, this.point_13_R};
        this.pawnArrayWHT = new PawnView[]     {   this.pawnWHT01, this.pawnWHT02, this.pawnWHT03, this.pawnWHT04, this.pawnWHT05, this.pawnWHT06,
                this.pawnWHT07, this.pawnWHT08, this.pawnWHT09, this.pawnWHT10, this.pawnWHT11, this.pawnWHT12, this.pawnWHT13, this.pawnWHT14, this.pawnWHT15};
        this.pawnArrayBLK = new PawnView[]     {   this.pawnBLK01, this.pawnBLK02, this.pawnBLK03, this.pawnBLK04, this.pawnBLK05, this.pawnBLK06,
                this.pawnBLK07, this.pawnBLK08, this.pawnBLK09, this.pawnBLK10, this.pawnBLK11, this.pawnBLK12, this.pawnBLK13, this.pawnBLK14, this.pawnBLK15};


        Timeline turnTimer = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                new KeyFrame(Duration.minutes(TURN_DURATION), e-> {
                    // TODO Verificare che turno venga effettivamente cambiato
                    logic.nextTurn();
                }, new KeyValue(timerIn.scaleYProperty(), 0))
        );

        turnTimer.setCycleCount(Animation.INDEFINITE);
        turnTimer.play();



        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

}

