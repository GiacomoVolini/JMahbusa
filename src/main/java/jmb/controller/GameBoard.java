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
    private Rectangle boardRect;

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

    protected Polygon[] polArrayTop;
    protected Polygon[] polArrayBot;

    protected Region[] regArrayTop;
    protected Region[] regArrayBot;

    //public GameBoard() {
      //  super();
        //this.polArrayTop = new Polygon[] {this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
          //      this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12};
        /*this.polArrayBot = new Polygon[] {point_24, point_23, point_22, point_21, point_20, point_19,
                point_18, point_17, point_16, point_15, point_14, point_13};*/
        //this.regArrayTop = new Region[] {this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
          //      this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R};
        /*this.regArrayBot = new Region[] {point_24_R, point_23_R, point_22_R, point_21_R, point_20_R, point_19_R,
                point_18_R, point_17_R, point_16_R, point_15_R, point_14_R, point_13_R};*/
    //}

    public void initialize() {

        this.polArrayTop = new Polygon[] {this.point_1, this.point_2, this.point_3, this.point_4, this.point_5, this.point_6,
                      this.point_7, this.point_8, this.point_9, this.point_10, this.point_11, this.point_12};
        this.regArrayTop = new Region[] {this.point_1_R, this.point_2_R, this.point_3_R, this.point_4_R, this.point_5_R, this.point_6_R,
                     this.point_7_R, this.point_8_R, this.point_9_R, this.point_10_R, this.point_11_R, this.point_12_R};
        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            boardRect.setLayoutX((window.getWidth())/3);
            boardRect.setWidth((window.getWidth())/3);
            for (int i=0; i<12; i++) {
                /*DEBUG System.out.println (polArrayTop[i]);
                System.out.println (regArrayTop[i]);*/
                regArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth())));
                regArrayTop[i].setPrefWidth((boardRect.getWidth())/12);

                polArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/12)));
                polArrayTop[i].getPoints().setAll(0d, 0d,
                        (boardRect.getWidth()/12), 0d,
                        (i*(boardRect.getWidth()/12)+(boardRect.getWidth()/24)), boardRect.getY()+regArrayTop[i].getPrefHeight() );
                /*point_1.setLayoutX(boardRect.getLayoutX());
                point_1.getPoints().setAll(0d, 0d,
                        boardRect.getWidth(), 0d,
                        boardRect.getWidth()/48, boardRect.getY()+point_1_R.getPrefHeight() );
                */
            }

        });

        window.heightProperty().addListener((obs, oldVal, newVal) -> {
            boardRect.setLayoutY((window.getHeight())/3);
            boardRect.setHeight((window.getHeight())/3);
        });

    }

}

