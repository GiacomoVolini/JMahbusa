package jmb.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.AccessibleObject;

import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

public class GameBoard {

    protected AnchorPane boardAnchor;
    protected Rectangle outerRect;
    protected Rectangle boardRect;
    protected Rectangle separator;
    protected Polygon[] polArrayTop = new Polygon[12];
    protected Polygon[] polArrayBot = new Polygon[12];
    protected Region[] regArrayTop = new Region[12];
    protected Region[] regArrayBot = new Region[12];
    protected PawnView[] pawnArrayWHT = new PawnView[15];
    protected PawnView[] pawnArrayBLK = new PawnView[15];
    protected Rectangle whiteExitRegion;
    protected Rectangle blackExitRegion;

    public GameBoard() {
        outerRect = new Rectangle();
        outerRect.setFill(frame);
        outerRect.setStroke(Color.BLACK);
        boardRect = new Rectangle();
        boardRect.setFill(table);
        boardRect.setStroke(table);
        separator = new Rectangle();
        separator.setFill(frame);
        separator.setStroke(frame);
        whiteExitRegion = new Rectangle();
        whiteExitRegion.setFill(pedIn1);
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(LOW_CONTRAST);
        blackExitRegion = new Rectangle();
        blackExitRegion.setFill(pedIn2);
        blackExitRegion.setStroke(Color.BLACK);
        blackExitRegion.setEffect(LOW_CONTRAST);
        for (int i = 0; i<12; i++) {
            polArrayTop[i] = new Polygon();
            polArrayBot[i] = new Polygon();
            regArrayTop[i] = new Region();
            regArrayBot[i] = new Region();
            if((i%2)==0){
                this.polArrayTop[i].setFill(Color.web(logic.getEvenPointsColor()));
                this.polArrayTop[i].setStroke(Color.web(logic.getEvenPointsColor()));
                this.polArrayBot[i].setFill(Color.web(logic.getOddPointsColor()));
                this.polArrayBot[i].setStroke(Color.web(logic.getOddPointsColor()));
            }else{
                this.polArrayTop[i].setFill(Color.web(logic.getOddPointsColor()));
                this.polArrayTop[i].setStroke(Color.web(logic.getOddPointsColor()));
                this.polArrayBot[i].setFill(Color.web(logic.getEvenPointsColor()));
                this.polArrayBot[i].setStroke(Color.web(logic.getEvenPointsColor()));
            }
        }
        for (int i = 0; i<15; i++) {
            pawnArrayWHT[i] = new PawnView();
            pawnArrayBLK[i] = new PawnView();
            this.pawnArrayWHT[i].setFill(pedIn1);
            this.pawnArrayWHT[i].setStroke(pedOut1);
            this.pawnArrayWHT[i].setDisable(true);
            this.pawnArrayBLK[i].setFill(pedIn2);
            this.pawnArrayBLK[i].setStroke(pedOut2);
            this.pawnArrayBLK[i].setDisable(true);
        }
    }

    protected void addChildrenToAnchor() {
        boardAnchor.getChildren().addAll(outerRect, boardRect, separator, whiteExitRegion, blackExitRegion);
        for (int i = 0; i<12; i++)
            boardAnchor.getChildren().addAll(polArrayTop[i], polArrayBot[i], regArrayTop[i], regArrayBot[i]);
        for (int i = 0; i<15; i++)
            boardAnchor.getChildren().addAll(pawnArrayWHT[i], pawnArrayBLK[i]);

    }

    protected void setBoardColors() {
        outerRect.setFill(frame);
        boardRect.setFill(table);
        boardRect.setStroke(table);
        separator.setFill(frame);
        separator.setStroke(frame);
        whiteExitRegion.setFill(pedIn1);
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(LOW_CONTRAST);
        blackExitRegion.setFill(pedIn2);
        blackExitRegion.setStroke(Color.BLACK);
        blackExitRegion.setEffect(LOW_CONTRAST);
        for (int i = 0; i<12; i++) {
            if((i%2)==0){
                this.polArrayTop[i].setFill(Color.web(logic.getEvenPointsColor()));
                this.polArrayTop[i].setStroke(Color.web(logic.getEvenPointsColor()));
                this.polArrayBot[i].setFill(Color.web(logic.getOddPointsColor()));
                this.polArrayBot[i].setStroke(Color.web(logic.getOddPointsColor()));
            }else{
                this.polArrayTop[i].setFill(Color.web(logic.getOddPointsColor()));
                this.polArrayTop[i].setStroke(Color.web(logic.getOddPointsColor()));
                this.polArrayBot[i].setFill(Color.web(logic.getEvenPointsColor()));
                this.polArrayBot[i].setStroke(Color.web(logic.getEvenPointsColor()));
            }
        }
        for (int i = 0; i<15; i++) {
            this.pawnArrayWHT[i].setFill(pedIn1);
            this.pawnArrayWHT[i].setStroke(pedOut1);
            this.pawnArrayWHT[i].setDisable(true);
            this.pawnArrayBLK[i].setFill(pedIn2);
            this.pawnArrayBLK[i].setStroke(pedOut2);
            this.pawnArrayBLK[i].setDisable(true);
        }
    }
}
