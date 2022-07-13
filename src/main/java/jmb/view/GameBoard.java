package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    protected int whoCalled;

    public GameBoard() {
        outerRect = new Rectangle();
        outerRect.setFill(Color.web(logic.getBoardFrameColor()));
        outerRect.setStroke(Color.BLACK);
        boardRect = new Rectangle();
        boardRect.setFill(Color.web(logic.getBoardInnerColor()));
        boardRect.setStroke(Color.web(logic.getBoardInnerColor()));
        separator = new Rectangle();
        separator.setFill(Color.web(logic.getBoardFrameColor()));
        separator.setStroke(Color.web(logic.getBoardFrameColor()));
        whiteExitRegion = new Rectangle();
        whiteExitRegion.setFill(Color.web(logic.getWhitePawnFill()));
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(LOW_CONTRAST);
        blackExitRegion = new Rectangle();
        blackExitRegion.setFill(Color.web(logic.getBlackPawnFill()));
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
            this.pawnArrayWHT[i].setFill(Color.web(logic.getWhitePawnFill()));
            this.pawnArrayWHT[i].setStroke(Color.web(logic.getWhitePawnStroke()));
            this.pawnArrayWHT[i].setDisable(true);
            this.pawnArrayBLK[i].setFill(Color.web(logic.getBlackPawnFill()));
            this.pawnArrayBLK[i].setStroke(Color.web(logic.getBlackPawnStroke()));
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
        outerRect.setFill(Color.web(logic.getBoardFrameColor()));
        boardRect.setFill(Color.web(logic.getBoardInnerColor()));
        boardRect.setStroke(Color.web(logic.getBoardInnerColor()));
        separator.setFill(Color.web(logic.getBoardFrameColor()));
        separator.setStroke(Color.web(logic.getBoardFrameColor()));
        whiteExitRegion.setFill(Color.web(logic.getWhitePawnFill()));
        whiteExitRegion.setStroke(Color.BLACK);
        whiteExitRegion.setEffect(LOW_CONTRAST);
        blackExitRegion.setFill(Color.web(logic.getBlackPawnFill()));
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
            this.pawnArrayWHT[i].setFill(Color.web(logic.getWhitePawnFill()));
            this.pawnArrayWHT[i].setStroke(Color.web(logic.getWhitePawnStroke()));
            this.pawnArrayWHT[i].setDisable(true);
            this.pawnArrayBLK[i].setFill(Color.web(logic.getBlackPawnFill()));
            this.pawnArrayBLK[i].setStroke(Color.web(logic.getBlackPawnStroke()));
            this.pawnArrayBLK[i].setDisable(true);
        }
    }

    protected boolean blockResizeCondition() {
        return (!logic.getFullScreen() && !logic.getLockResolution());
    }

    protected void openBlackExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        blackExitRegion.setVisible(true);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), 0),
                        new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1),  e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                }, new KeyValue(blackExitRegion.widthProperty() , GameViewRedraw.getMaxExitWidth() ),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    protected void openWhiteExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        whiteExitRegion.setVisible(true);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), 0),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1), e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , GameViewRedraw.getMaxExitWidth() ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void closeBlackExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), GameViewRedraw.getMaxExitWidth()),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1),  e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                    blackExitRegion.setVisible(false);
                }, new KeyValue(blackExitRegion.widthProperty() ,  0 ),
                        new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void closeWhiteExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), GameViewRedraw.getMaxExitWidth()),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1), e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                    whiteExitRegion.setVisible(false);
                }, new KeyValue(whiteExitRegion.widthProperty() , 0 ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    protected void setWhoCalled(int value) {
        whoCalled = value;
    }
}
