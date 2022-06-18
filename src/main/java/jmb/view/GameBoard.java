package jmb.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.AccessibleObject;

import static jmb.view.ConstantsView.*;

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
        ColorAdjust lowContrast = new ColorAdjust(0,0,0,-0.5);
        whiteExitRegion = new Rectangle();
        whiteExitRegion.setFill(pedIn1);
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(lowContrast);
        blackExitRegion = new Rectangle();
        blackExitRegion.setFill(pedIn2);
        blackExitRegion.setStroke(Color.BLACK);
        blackExitRegion.setEffect(lowContrast);
        for (int i = 0; i<12; i++) {
            polArrayTop[i] = new Polygon();
            polArrayBot[i] = new Polygon();
            regArrayTop[i] = new Region();
            regArrayBot[i] = new Region();
            if((i%2)==0){
                this.polArrayTop[i].setFill(point);
                this.polArrayTop[i].setStroke(point);
                this.polArrayBot[i].setFill(point2);
                this.polArrayBot[i].setStroke(point2);
            }else{
                this.polArrayTop[i].setFill(point2);
                this.polArrayTop[i].setStroke(point2);
                this.polArrayBot[i].setFill(point);
                this.polArrayBot[i].setStroke(point);
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
}
