package jmb.view;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import static jmb.ConstantsShared.*;
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
            this.pawnArrayWHT[i].setFill(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
            this.pawnArrayWHT[i].setStroke(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
            this.pawnArrayWHT[i].setDisable(true);
            this.pawnArrayBLK[i].setFill(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
            this.pawnArrayBLK[i].setStroke(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
            this.pawnArrayBLK[i].setDisable(true);
        }
        whiteExitRegion.setFill(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
        blackExitRegion.setFill(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(LOW_CONTRAST);
        blackExitRegion.setStroke(Color.BLACK);
        blackExitRegion.setEffect(LOW_CONTRAST);
        outerRect.setStroke(Color.BLACK);
        colorAccordingToSettings();
    }

    private void colorAccordingToSettings() {
        switch (logic.getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD:
                outerRect.setFill(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
                boardRect.setFill(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                boardRect.setStroke(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                separator.setFill(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
                separator.setStroke(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
                for (int i = 0; i < 12; i++) {
                    if ((i % 2) == 0) {
                        this.polArrayTop[i].setFill(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                        this.polArrayTop[i].setStroke(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                        this.polArrayBot[i].setFill(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                        this.polArrayBot[i].setStroke(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                    } else {
                        this.polArrayTop[i].setFill(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                        this.polArrayTop[i].setStroke(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                        this.polArrayBot[i].setFill(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                        this.polArrayBot[i].setStroke(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                    }
                }
                break;
            case LEFT_PRESET:
                outerRect.setFill(Color.web(logic.getSetting(LEFT, Preset.BOARD_FRAME.ordinal())));
                boardRect.setFill(Color.web(logic.getSetting(LEFT, Preset.BOARD_INNER.ordinal())));
                boardRect.setStroke(Color.web(logic.getSetting(LEFT, Preset.BOARD_INNER.ordinal())));
                separator.setFill(Color.web(logic.getSetting(LEFT, Preset.BOARD_FRAME.ordinal())));
                separator.setStroke(Color.web(logic.getSetting(LEFT, Preset.BOARD_FRAME.ordinal())));
                for (int i = 0; i < 12; i++) {
                    if ((i % 2) == 0) {
                        this.polArrayTop[i].setFill(Color.web(logic.getSetting(LEFT, Preset.EVEN_POINTS.ordinal())));
                        this.polArrayTop[i].setStroke(Color.web(logic.getSetting(LEFT, Preset.EVEN_POINTS.ordinal())));
                        this.polArrayBot[i].setFill(Color.web(logic.getSetting(LEFT, Preset.ODD_POINTS.ordinal())));
                        this.polArrayBot[i].setStroke(Color.web(logic.getSetting(LEFT, Preset.ODD_POINTS.ordinal())));
                    } else {
                        this.polArrayTop[i].setFill(Color.web(logic.getSetting(LEFT, Preset.ODD_POINTS.ordinal())));
                        this.polArrayTop[i].setStroke(Color.web(logic.getSetting(LEFT, Preset.ODD_POINTS.ordinal())));
                        this.polArrayBot[i].setFill(Color.web(logic.getSetting(LEFT, Preset.EVEN_POINTS.ordinal())));
                        this.polArrayBot[i].setStroke(Color.web(logic.getSetting(LEFT, Preset.EVEN_POINTS.ordinal())));
                    }
                }
                break;
            case RIGHT_PRESET:
                outerRect.setFill(Color.web(logic.getSetting(RIGHT, Preset.BOARD_FRAME.ordinal())));
                boardRect.setFill(Color.web(logic.getSetting(RIGHT, Preset.BOARD_INNER.ordinal())));
                boardRect.setStroke(Color.web(logic.getSetting(RIGHT, Preset.BOARD_INNER.ordinal())));
                separator.setFill(Color.web(logic.getSetting(RIGHT, Preset.BOARD_FRAME.ordinal())));
                separator.setStroke(Color.web(logic.getSetting(RIGHT, Preset.BOARD_FRAME.ordinal())));
                for (int i = 0; i < 12; i++) {
                    if ((i % 2) == 0) {
                        this.polArrayTop[i].setFill(Color.web(logic.getSetting(RIGHT, Preset.EVEN_POINTS.ordinal())));
                        this.polArrayTop[i].setStroke(Color.web(logic.getSetting(RIGHT, Preset.EVEN_POINTS.ordinal())));
                        this.polArrayBot[i].setFill(Color.web(logic.getSetting(RIGHT, Preset.ODD_POINTS.ordinal())));
                        this.polArrayBot[i].setStroke(Color.web(logic.getSetting(RIGHT, Preset.ODD_POINTS.ordinal())));
                    } else {
                        this.polArrayTop[i].setFill(Color.web(logic.getSetting(RIGHT, Preset.ODD_POINTS.ordinal())));
                        this.polArrayTop[i].setStroke(Color.web(logic.getSetting(RIGHT, Preset.ODD_POINTS.ordinal())));
                        this.polArrayBot[i].setFill(Color.web(logic.getSetting(RIGHT, Preset.EVEN_POINTS.ordinal())));
                        this.polArrayBot[i].setStroke(Color.web(logic.getSetting(RIGHT, Preset.EVEN_POINTS.ordinal())));
                    }
                }
                break;
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
        return (!logic.getSetting("Video", "fullScreen", boolean.class) && !logic.getSetting("Video", "lockResolution", boolean.class));
    }

    public void colorPoint (int index, Color color) {
        colorPoint(index, color, color);
    }
    //La variabile index dei due colorPoint deve essere compresa tra 1 e 24
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
