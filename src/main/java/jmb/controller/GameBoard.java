package jmb.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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

    // Si creano degli array di Polygon e Region per gestire in maniera più agevole il
    //   ridimensionamento dinamico delle componenti

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected Region[] regArrayTop;
    protected Region[] regArrayBot;

    public void initialize() {

        this.polArrayTop = new Polygon[] {this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
                      this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12};
        this.regArrayTop = new Region[] {this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
                     this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R};


        //LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> {

            //Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
            outerRect.setLayoutX(window.getWidth()/3);
            outerRect.setWidth(window.getWidth()/3);
          
           // Ridimensione il rettangolo interno in base alle diminsione di quello esterno

            boardRect.setLayoutX(outerRect.getLayoutX()+(outerRect.getWidth()/10));
            boardRect.setWidth((outerRect.getWidth()*0.8));

            //Ridimensiona il separatore tra le due metà dell'area di gioco
            //in funzione della sua effettiva dimensione
            separator.setWidth(boardRect.getWidth()/13);
            separator.setLayoutX(boardRect.getLayoutX() + ((boardRect.getWidth() - separator.getWidth())/2));

           /*for (int i=0; i<12; i++) {

                regArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth())));
                regArrayTop[i].setPrefWidth((boardRect.getWidth())/12);

                polArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/12)));
                polArrayTop[i].getPoints().setAll(0d, 0d,
                        (boardRect.getWidth()/12), 0d,
                        (boardRect.getWidth()/24), boardRect.getY()+regArrayTop[i].getPrefHeight() );

                regArrayBot[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth())));
                regArrayBot[i].setPrefWidth((boardRect.getWidth())/12);

                polArrayBot[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/12)));
                polArrayBot[i].getPoints().setAll(0d, 0d,
                        (boardRect.getWidth()/12), 0d,
                        (boardRect.getWidth()/24), boardRect.getY()+regArrayTop[i].getPrefHeight() );
            }

             */

        });

        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> {

            //Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
            outerRect.setLayoutY((window.getHeight())/3);
            outerRect.setHeight((window.getHeight())/3);


            // Ridimensione il rettangolo interno in base alle diminsione di quello esterno

            boardRect.setLayoutY(outerRect.getLayoutY()+(outerRect.getHeight()/10));
            boardRect.setHeight((outerRect.getHeight()*0.8));

            //Ridimensiona il separatore tra le due metà dell'area di gioco
            //in funzione della sua effettiva dimensione
            separator.setHeight(boardRect.getHeight());
            separator.setLayoutY(boardRect.getLayoutY());


            /*for (int i=0; i<12; i++) {

                regArrayTop[i].setLayoutY(boardRect.getLayoutY());
                regArrayTop[i].setPrefHeight((boardRect.getHeight())*0.46);

                polArrayTop[i].setLayoutY(boardRect.getLayoutY());
                polArrayTop[i].getPoints().setAll(0d, 0d,
                        (boardRect.getWidth()/12), 0d,
                        (boardRect.getWidth()/24), boardRect.getY()+regArrayTop[i].getPrefHeight() );

                regArrayBot[i].setLayoutY(boardRect.getLayoutY());
                regArrayBot[i].setPrefHeight((boardRect.getHeight())*0.46);

                polArrayBot[i].setLayoutY(boardRect.getLayoutY());
                polArrayBot[i].getPoints().setAll(0d, 0d,
                        (boardRect.getWidth()/12), 0d,
                        (boardRect.getWidth()/24), boardRect.getY()+regArrayTop[i].getPrefHeight() );

            }

             */
        });

    }

}

