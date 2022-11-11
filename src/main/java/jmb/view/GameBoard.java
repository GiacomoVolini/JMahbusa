package jmb.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.LOW_CONTRAST;
import static jmb.view.View.*;

public abstract class GameBoard {

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
        boardRect = new Rectangle();
        separator = new Rectangle();
        whiteExitRegion = new Rectangle();
        blackExitRegion = new Rectangle();

        for (int i = 0; i<12; i++) {
            polArrayTop[i] = new Polygon();
            polArrayBot[i] = new Polygon();
            regArrayTop[i] = new Region();
            regArrayBot[i] = new Region();
        }
        for (int i = 0; i<15; i++) {
            pawnArrayWHT[i] = new PawnView();
            pawnArrayBLK[i] = new PawnView();
            this.pawnArrayWHT[i].setFill(Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class)));
            this.pawnArrayWHT[i].setStroke(Color.web(getLogic().getSetting("Customization", "whitePawnStroke", String.class)));
            this.pawnArrayWHT[i].setDisable(true);
            this.pawnArrayBLK[i].setFill(Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class)));
            this.pawnArrayBLK[i].setStroke(Color.web(getLogic().getSetting("Customization", "blackPawnStroke", String.class)));
            this.pawnArrayBLK[i].setDisable(true);
        }
        whiteExitRegion.setFill(Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class)));
        blackExitRegion.setFill(Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class)));
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(LOW_CONTRAST);
        blackExitRegion.setStroke(Color.BLACK);
        blackExitRegion.setEffect(LOW_CONTRAST);
        outerRect.setStroke(Color.BLACK);
        colorAccordingToSettings();
    }

    private void colorAccordingToSettings() {
        switch (getLogic().getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD:
                colorBoardCustom();
                break;
            case LEFT_PRESET:
                colorBoardLeftPreset();
                break;
            case RIGHT_PRESET:
                colorBoardRightPreset();
                break;
        }

    }

    private void colorBoardCustom() {
        outerRect.setFill(Color.web(getLogic().getSetting("Customization", "boardFrameColor", String.class)));
        boardRect.setFill(Color.web(getLogic().getSetting("Customization", "boardInnerColor", String.class)));
        boardRect.setStroke(Color.web(getLogic().getSetting("Customization", "boardInnerColor", String.class)));
        separator.setFill(Color.web(getLogic().getSetting("Customization", "boardFrameColor", String.class)));
        separator.setStroke(Color.web(getLogic().getSetting("Customization", "boardFrameColor", String.class)));
        for (int i = 0; i < 12; i++) {
            if ((i % 2) == 0) {
                this.polArrayTop[i].setFill(Color.web(getLogic().getSetting("Customization", "evenPointsColor", String.class)));
                this.polArrayTop[i].setStroke(Color.web(getLogic().getSetting("Customization", "evenPointsColor", String.class)));
                this.polArrayBot[i].setFill(Color.web(getLogic().getSetting("Customization", "oddPointsColor", String.class)));
                this.polArrayBot[i].setStroke(Color.web(getLogic().getSetting("Customization", "oddPointsColor", String.class)));
            } else {
                this.polArrayTop[i].setFill(Color.web(getLogic().getSetting("Customization", "oddPointsColor", String.class)));
                this.polArrayTop[i].setStroke(Color.web(getLogic().getSetting("Customization", "oddPointsColor", String.class)));
                this.polArrayBot[i].setFill(Color.web(getLogic().getSetting("Customization", "evenPointsColor", String.class)));
                this.polArrayBot[i].setStroke(Color.web(getLogic().getSetting("Customization", "evenPointsColor", String.class)));
            }
        }
    }

    private void colorBoardLeftPreset() {
        outerRect.setFill(Color.web(getLogic().getSetting(LEFT, BOARD_FRAME)));
        boardRect.setFill(Color.web(getLogic().getSetting(LEFT, BOARD_INNER)));
        boardRect.setStroke(Color.web(getLogic().getSetting(LEFT, BOARD_INNER)));
        separator.setFill(Color.web(getLogic().getSetting(LEFT, BOARD_FRAME)));
        separator.setStroke(Color.web(getLogic().getSetting(LEFT, BOARD_FRAME)));
        for (int i = 0; i < 12; i++) {
            if ((i % 2) == 0) {
                this.polArrayTop[i].setFill(Color.web(getLogic().getSetting(LEFT, EVEN_POINTS)));
                this.polArrayTop[i].setStroke(Color.web(getLogic().getSetting(LEFT, EVEN_POINTS)));
                this.polArrayBot[i].setFill(Color.web(getLogic().getSetting(LEFT, ODD_POINTS)));
                this.polArrayBot[i].setStroke(Color.web(getLogic().getSetting(LEFT, ODD_POINTS)));
            } else {
                this.polArrayTop[i].setFill(Color.web(getLogic().getSetting(LEFT, ODD_POINTS)));
                this.polArrayTop[i].setStroke(Color.web(getLogic().getSetting(LEFT, ODD_POINTS)));
                this.polArrayBot[i].setFill(Color.web(getLogic().getSetting(LEFT, EVEN_POINTS)));
                this.polArrayBot[i].setStroke(Color.web(getLogic().getSetting(LEFT, EVEN_POINTS)));
            }
        }
    }

    private void colorBoardRightPreset() {
        outerRect.setFill(Color.web(getLogic().getSetting(RIGHT, BOARD_FRAME)));
        boardRect.setFill(Color.web(getLogic().getSetting(RIGHT, BOARD_INNER)));
        boardRect.setStroke(Color.web(getLogic().getSetting(RIGHT, BOARD_INNER)));
        separator.setFill(Color.web(getLogic().getSetting(RIGHT, BOARD_FRAME)));
        separator.setStroke(Color.web(getLogic().getSetting(RIGHT, BOARD_FRAME)));
        for (int i = 0; i < 12; i++) {
            if ((i % 2) == 0) {
                this.polArrayTop[i].setFill(Color.web(getLogic().getSetting(RIGHT, EVEN_POINTS)));
                this.polArrayTop[i].setStroke(Color.web(getLogic().getSetting(RIGHT, EVEN_POINTS)));
                this.polArrayBot[i].setFill(Color.web(getLogic().getSetting(RIGHT, ODD_POINTS)));
                this.polArrayBot[i].setStroke(Color.web(getLogic().getSetting(RIGHT, ODD_POINTS)));
            } else {
                this.polArrayTop[i].setFill(Color.web(getLogic().getSetting(RIGHT, ODD_POINTS)));
                this.polArrayTop[i].setStroke(Color.web(getLogic().getSetting(RIGHT, ODD_POINTS)));
                this.polArrayBot[i].setFill(Color.web(getLogic().getSetting(RIGHT, EVEN_POINTS)));
                this.polArrayBot[i].setStroke(Color.web(getLogic().getSetting(RIGHT, EVEN_POINTS)));
            }
        }
    }

    protected void addChildrenToAnchor() {
        boardAnchor.getChildren().addAll(outerRect, boardRect, separator, whiteExitRegion, blackExitRegion);
        for (int i = 0; i<12; i++)
            boardAnchor.getChildren().addAll(polArrayTop[i], polArrayBot[i], regArrayTop[i], regArrayBot[i]);
        for (int i = 0; i<15; i++)
            boardAnchor.getChildren().addAll(pawnArrayWHT[i], pawnArrayBLK[i]);

    }

    protected boolean blockResizeCondition() {
        return (!getLogic().getSetting("Video", "fullScreen", boolean.class) &&
                !getLogic().getSetting("Video", "lockResolution", boolean.class));
    }

    //La variabile index dei due colorPoint deve essere compresa tra 1 e 24
    public void colorPoint (int index, Color color) {
        colorPoint(index, color, color);
    }
    public void colorPoint(int index, Color colorFill, Color colorStroke) {
        switch (index) {
            default:
                if (index<13) {
                    polArrayTop[index-1].setFill(colorFill);
                    polArrayTop[index-1].setStroke(colorStroke);
                } else {
                    polArrayBot[24 - index].setFill(colorFill);
                    polArrayBot[24 - index].setStroke(colorStroke);
                }
                break;
            case COL_WHITE_EXIT:
                whiteExitRegion.setFill(colorFill);
                break;
            case COL_BLACK_EXIT:
                blackExitRegion.setFill(colorFill);
        }

    }

}
