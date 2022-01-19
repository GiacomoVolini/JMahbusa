package jmb.view;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Point2D;

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
    private PawnV examplePawn;

    @FXML
    private Button menuBTN;

    //Valore di larghezza massima delle regioni di uscita e della zona dei dadi
    private double maxExitWidth;
    private double maxDTWidth;


    //TEST
    protected boolean bExit = false;
    protected boolean wExit = false;

    protected int pawnRegion;
    //TEST

    //  Booleano che indica se l'animazione di diceTray è stata completata
    private boolean dtAnimDone = false;

    //  Si creano degli array di Polygon e Region per gestire in maniera più agevole il
    //  ridimensionamento dinamico delle componenti

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected Region[] regArrayTop;
    protected Region[] regArrayBot;


    @FXML
    protected void runTimer(ActionEvent event) {
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
                }, new KeyValue(blackExitRegion.widthProperty() , this.maxExitWidth ),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - this.maxExitWidth))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    private void testWhiteExit() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), 0),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.wExit = true; //TEST
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , this.maxExitWidth ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - this.maxExitWidth))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private Point2D prevPosition;

    @FXML
    private void savePosition (MouseEvent event) {
        Node n = (Node)event.getSource();
        this.prevPosition = new Point2D(n.getLayoutX(), n.getLayoutY());
    }

    @FXML
    private void drag(MouseEvent event) {
        Node n = (Node)event.getSource();
        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }

    @FXML
    private void releaseTest(MouseEvent event) {
        Node node = (Node)event.getSource();
        boolean done = false;
        for (int i=0; i<regArrayTop.length && !done; i++) {
            if (regArrayTop[i].contains(regArrayTop[i].sceneToLocal(getPawnCenter((PawnV)node)))) {
                done = true;
                node.setLayoutX(regArrayTop[i].getLayoutX() + ((PawnV) node).getRadius());
                node.setLayoutY(regArrayTop[i].getLayoutY() + ((PawnV) node).getRadius());
                ((PawnV)node).setPlace(TOP_POINTS);
                ((PawnV)node).setWhichPoint(i);
            }
        }
        for (int i=0; i<regArrayBot.length && !done; i++) {
            if (regArrayBot[i].contains(regArrayBot[i].sceneToLocal(getPawnCenter((PawnV) node)))) {
                done = true;
                node.setLayoutX(regArrayBot[i].getLayoutX() + ((PawnV) node).getRadius());
                node.setLayoutY(regArrayBot[i].getLayoutY() + regArrayBot[i].getHeight() - ((PawnV) node).getRadius());
                ((PawnV)node).setPlace(BOT_POINTS);
                ((PawnV)node).setWhichPoint(i);
            }
        }

        if (whiteExitRegion.contains(whiteExitRegion.sceneToLocal(getPawnCenter((PawnV) node))) && !done) {
            done = true;
            node.setLayoutX(whiteExitRegion.getLayoutX() + ((PawnV) node).getRadius());
            node.setLayoutY(whiteExitRegion.getLayoutY() + ((PawnV) node).getRadius());
            ((PawnV)node).setPlace(WHITE_EXIT_REGION);
        }

        if (blackExitRegion.contains(blackExitRegion.sceneToLocal(getPawnCenter((PawnV) node))) && !done) {
            done = true;
            node.setLayoutX(blackExitRegion.getLayoutX() + ((PawnV) node).getRadius());
            node.setLayoutY(blackExitRegion.getLayoutY() + blackExitRegion.getHeight() - ((PawnV) node).getRadius());
            ((PawnV)node).setPlace(BLACK_EXIT_REGION);
        }

        if (!done) {
            node.setLayoutX(prevPosition.getX());
            node.setLayoutY(prevPosition.getY());
        }
    }

    //  Metodi per trovare il centro di una pedina
    private double getPawnCenterX (PawnV pawn) {
        return pawn.getLayoutX() + (pawn.getRadius()/2);
    }

    private double getPawnCenterY (PawnV pawn) {
        return pawn.getLayoutY() + (pawn.getRadius()/2);
    }

    private Point2D getPawnCenter (PawnV pawn) {
        return new Point2D(getPawnCenterX(pawn), getPawnCenterY(pawn));
    }

    private void diceTrayAnim() {
        jmb.App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.dtAnimDone = true;
                    jmb.App.getStage().setResizable(true);
                }, new KeyValue(diceTray.widthProperty() , this.maxDTWidth )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }


    private void calcTrayWidth() {
        this.maxExitWidth = outerRect.getWidth()* EXTRA_REGION_FACTOR;
        this.maxDTWidth = outerRect.getWidth()*EXTRA_REGION_FACTOR;
    }

    private double getBoardSize () {
        double usableWidth = window.getWidth()*HORIZONTAL_RESIZE_FACTOR;
        double usableHeight = window.getHeight()*VERTICAL_RESIZE_FACTOR;
        return Math.min(usableHeight, usableWidth);
    }

    private double getMaxBtnWidth() {
        double maxBtnWidth = window.getWidth() - (diceTray.getLayoutX()+maxDTWidth+(BUTTON_ANCHOR*2));
        return Math.min(MAX_BTN_WIDTH, maxBtnWidth);

    }

    private void resizeOuterRect() {
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        outerRect.setWidth(getBoardSize());
        outerRect.setLayoutX((window.getWidth()/2)-(outerRect.getWidth()/2));
        outerRect.setHeight(getBoardSize());
        outerRect.setLayoutY((window.getHeight()/2)-(outerRect.getHeight()/2));
    }

    private void resizeBoardRect() {
        //  Ridimensiona il rettangolo interno in base alla dimensione di quello esterno
        boardRect.setWidth((outerRect.getWidth()*0.9));
        boardRect.setLayoutX(outerRect.getLayoutX()+(outerRect.getWidth()/2)-(boardRect.getWidth()/2));
        boardRect.setHeight((outerRect.getHeight()*0.9));
        boardRect.setLayoutY(outerRect.getLayoutY()+(outerRect.getHeight()/2)-(boardRect.getHeight()/2));
    }

    private void resizeSeparator() {
        //  Ridimensiona il separatore tra le due metà dell'area di gioco
        //  in funzione della sua effettiva dimensione
        separator.setWidth(boardRect.getWidth()/13);
        separator.setLayoutX(boardRect.getLayoutX() + ((6*(boardRect.getWidth()/13))));
        separator.setHeight(boardRect.getHeight()+2);
        separator.setLayoutY(boardRect.getLayoutY()-1);
    }

    private void resizeTimer() {
        //  Ridimensiona le barre per il timer
        timerOut.setWidth(separator.getWidth()/2);
        timerOut.setLayoutX(separator.getLayoutX() + (separator.getWidth()/2) -(timerOut.getWidth()/2));
        timerOut.setHeight(separator.getHeight()-4);
        timerOut.setLayoutY(separator.getLayoutY() + (separator.getHeight()/2) -(timerOut.getHeight()/2));

        timerIn.setWidth(timerOut.getWidth()-4);
        timerIn.setLayoutX(timerOut.getLayoutX()+2);
        timerIn.setHeight(timerOut.getHeight()-4);
        timerIn.setLayoutY(timerOut.getLayoutY()+2);
    }

    private void resizeLeftPoints() {
        //  Ridimensiona le punte a sinistra del tabellone e relative regioni
        for (int i=0; i<6; i++) {

            regArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            regArrayTop[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayTop[i].setLayoutY(boardRect.getLayoutY());
            regArrayTop[i].setPrefHeight((boardRect.getHeight())*0.46);

            polArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            polArrayTop[i].setLayoutY(boardRect.getLayoutY());
            polArrayTop[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()+regArrayTop[i].getPrefHeight() );

            regArrayBot[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            regArrayBot[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight()*(1-0.46));
            regArrayBot[i].setPrefHeight((boardRect.getHeight())*0.46);


            polArrayBot[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            polArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight());
            polArrayBot[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()-regArrayTop[i].getPrefHeight() );

        }
    }

    private void resizeRightPoints() {
        //  Ridimensiona le punte a destra del tabellone e relative regioni
        for (int i=6; i<12; i++) {

            regArrayTop[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            regArrayTop[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayTop[i].setLayoutY(boardRect.getLayoutY());
            regArrayTop[i].setPrefHeight((boardRect.getHeight())*0.46);

            polArrayTop[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            polArrayTop[i].setLayoutY(boardRect.getLayoutY());
            polArrayTop[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()+regArrayTop[i].getPrefHeight() );

            regArrayBot[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            regArrayBot[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight()*(1-0.46));
            regArrayBot[i].setPrefHeight((boardRect.getHeight())*0.46);


            polArrayBot[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            polArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight());
            polArrayBot[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()-regArrayTop[i].getPrefHeight() );

        }
    }

    private void resizeButtons() {

        //  Ridimensiona i Buttoni rispetto alla finestra principale
        //  Larghezza
        backBTN.setMaxWidth(getMaxBtnWidth());
        finishBTN.setMaxWidth(getMaxBtnWidth());
        menuBTN.setMaxWidth(getMaxBtnWidth());
        backBTN.setPrefWidth(window.getWidth()*0.15);
        finishBTN.setPrefWidth(backBTN.getPrefWidth());
        menuBTN.setPrefWidth(backBTN.getPrefWidth());
        // Altezza
        backBTN.setMaxHeight(MAX_BTN_HEIGHT);
        finishBTN.setMaxHeight(MAX_BTN_HEIGHT);
        menuBTN.setMaxHeight(MAX_BTN_HEIGHT);
        backBTN.setPrefHeight(window.getHeight()*0.2);
        finishBTN.setPrefHeight(backBTN.getPrefHeight());
        menuBTN.setPrefHeight(backBTN.getPrefHeight());
        backBTN.setLayoutY(window.getHeight()*.25 - backBTN.getPrefHeight()/2);
        finishBTN.setLayoutY((window.getHeight() - finishBTN.getPrefHeight())/2);
        menuBTN.setLayoutY(window.getHeight()*.75 - menuBTN.getPrefHeight()/2);

    }

    private void resizeExitRegions() {
        whiteExitRegion.setHeight(outerRect.getHeight()/2);
        whiteExitRegion.setLayoutY(outerRect.getLayoutY());
        blackExitRegion.setHeight(outerRect.getHeight()/2);
        blackExitRegion.setLayoutY(outerRect.getLayoutY() + (outerRect.getHeight()/2));

        if (bExit) {
            blackExitRegion.setWidth(this.maxExitWidth);
            blackExitRegion.setLayoutX(outerRect.getLayoutX() - this.maxExitWidth);
        }
        if (wExit) {
            whiteExitRegion.setWidth(this.maxExitWidth);
            whiteExitRegion.setLayoutX(outerRect.getLayoutX() - this.maxExitWidth);
        }
    }

    private void resizeDiceTray() {
        diceTray.setLayoutX(outerRect.getLayoutX() + outerRect.getWidth());
        diceTray.setLayoutY(outerRect.getLayoutY());
        if (dtAnimDone) {
            diceTray.setWidth(maxDTWidth);
        }
        diceTray.setHeight(outerRect.getHeight());

    }

    private void resizePawns() {
        examplePawn.setRadius(regArrayBot[0].getPrefWidth()/2);
        switch (examplePawn.getPlace()) {
            //TODO gestire casi riposizionamento pedine al variare delle dimensioni della finestra
            case TOP_POINTS :
                //mvPawnOnTop();
                break;
            case BOT_POINTS :
                //mvPawnOnBot();
                break;
        }
    }


    private void changeDimensions() {

        resizeOuterRect();
        resizeBoardRect();
        resizeSeparator();
        resizeTimer();
        resizeLeftPoints();
        resizeRightPoints();
        calcTrayWidth();
        resizeExitRegions();
        resizePawns();
        resizeDiceTray();
        if(!dtAnimDone) {
            diceTrayAnim();
            testBlackExit();
            testWhiteExit();
        }
        resizeButtons();

    }

    public void initialize() {

        this.polArrayTop = new Polygon[]    {   this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
                this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12               };
        this.regArrayTop = new Region[]     {   this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
                this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R   };
        this.polArrayBot = new Polygon[]    {   this.point_13, this.point_14, this.point_15, this.point_16, this.point_17, this.point_18,
                this.point_19, this.point_20, this.point_21, this.point_22, this.point_23, this.point_24            };
        this.regArrayBot = new Region[]     {   this.point_13_R, this.point_14_R, this.point_15_R, this.point_16_R, this.point_17_R, this.point_18_R,
                this.point_19_R, this.point_20_R, this.point_21_R, this.point_22_R, this.point_23_R, this.point_24_R};

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

}

