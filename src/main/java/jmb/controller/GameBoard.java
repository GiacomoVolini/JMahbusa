package jmb.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import static jmb.controller.ConstantsController.*;

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
    private ProgressBar timerBar;

    @FXML
    private Button backBTN;

    @FXML
    private Button menuBTN;

    @FXML
    private Text timerLBL;

    //  Si creano degli array di Polygon e Region per gestire in maniera più agevole il
    //  ridimensionamento dinamico delle componenti

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected Region[] regArrayTop;
    protected Region[] regArrayBot;

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
        window.widthProperty().addListener((obs, oldVal, newVal) -> {

            //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
            outerRect.setWidth(getBoardSize());
            outerRect.setLayoutX((window.getWidth()/2)-(outerRect.getWidth()/2));
            outerRect.setHeight(getBoardSize());
            outerRect.setLayoutY((window.getHeight()/2)-(outerRect.getHeight()/2));

            //  Ridimensiona il rettangolo interno in base alla dimensione di quello esterno

            boardRect.setLayoutX(outerRect.getLayoutX()+(outerRect.getWidth()/10));
            boardRect.setWidth((outerRect.getWidth()*0.8));
            boardRect.setLayoutY(outerRect.getLayoutY()+(outerRect.getHeight()/10));
            boardRect.setHeight((outerRect.getHeight()*0.8));

            //  Ridimensiona il separatore tra le due metà dell'area di gioco
            //  in funzione della sua effettiva dimensione
            separator.setWidth(boardRect.getWidth()/13);
            separator.setLayoutX(boardRect.getLayoutX() + ((6*(boardRect.getWidth()/13))));
            separator.setHeight(boardRect.getHeight()+2);
            separator.setLayoutY(boardRect.getLayoutY()-1);

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



        });

        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> {

            //Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
            outerRect.setWidth(getBoardSize());
            outerRect.setLayoutX((window.getWidth()/2)-(outerRect.getWidth()/2));
            outerRect.setHeight(getBoardSize());
            outerRect.setLayoutY((window.getHeight()/2)-(outerRect.getHeight()/2));


            //Ridimensiona il rettangolo interno in base alla dimensione di quello esterno

            boardRect.setLayoutX(outerRect.getLayoutX()+(outerRect.getWidth()/10));
            boardRect.setWidth((outerRect.getWidth()*0.8));
            boardRect.setLayoutY(outerRect.getLayoutY()+(outerRect.getHeight()/10));
            boardRect.setHeight((outerRect.getHeight()*0.8));

            //Ridimensiona il separatore tra le due metà dell'area di gioco
            //in funzione della sua effettiva dimensione
            separator.setWidth(boardRect.getWidth()/13);
            separator.setLayoutX(boardRect.getLayoutX() + ((6*(boardRect.getWidth()/13))));
            separator.setHeight(boardRect.getHeight()+2);
            separator.setLayoutY(boardRect.getLayoutY()-1);


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




        });

    }

    private double getBoardSize () {
        double usableWidth = window.getWidth()*HORIZONTAL_RESIZE_FACTOR;
        double usableHeight = window.getHeight()*VERTICAL_RESIZE_FACTOR;
        if (usableHeight<usableWidth)
            return usableHeight;
        else
            return usableWidth;
    }
}

