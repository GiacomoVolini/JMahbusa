package jmb.view.utilities;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jmb.view.App;
import jmb.view.DynamicGameBoard;
import jmb.view.DynamicGameBoardRedraw;
import jmb.view.GameViewRedraw;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.getLogic;

public class TimelineBuilder {

    public static Timeline buildCustomAnimation (Polygon customPoint1, Polygon customPoint2, Polygon customPoint3,
                                                 ColorPicker selectedPointColorPicker, ColorPicker evenPointColorPicker,
                                                 ColorPicker oddPointColorPicker) {
        Timeline out = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    customPoint3.setFill(selectedPointColorPicker.getValue());
                    customPoint1.setFill(evenPointColorPicker.getValue());
                }), new KeyFrame(Duration.seconds(0.5), e -> {
                    customPoint2.setFill(selectedPointColorPicker.getValue());
                    customPoint3.setFill(evenPointColorPicker.getValue());
                }), new KeyFrame(Duration.seconds(1), e -> {
                    customPoint1.setFill(selectedPointColorPicker.getValue());
                    customPoint2.setFill(oddPointColorPicker.getValue());
                }), new KeyFrame(Duration.seconds(1.5))
        );
        return out;
    }

    public static Timeline buildPresetsAnimation (Polygon leftPresetPoint1, Polygon leftPresetPoint2,
                                                  Polygon leftPresetPoint3, Polygon rightPresetPoint1,
                                                  Polygon rightPresetPoint2, Polygon rightPresetPoint3) {
        Timeline out = new Timeline(
                new KeyFrame(Duration.ZERO, e-> {
                    leftPresetPoint1.setFill(Color.web(getLogic().getSetting(LEFT, SELECTED_POINT)));
                    rightPresetPoint3.setFill(Color.web(getLogic().getSetting(RIGHT, SELECTED_POINT)));
                    leftPresetPoint3.setFill(Color.web(getLogic().getSetting(LEFT, EVEN_POINTS)));
                    rightPresetPoint1.setFill(Color.web(getLogic().getSetting(RIGHT, EVEN_POINTS)));
                }), new KeyFrame(Duration.seconds(0.5), e-> {
                    leftPresetPoint2.setFill(Color.web(getLogic().getSetting(LEFT, SELECTED_POINT)));
                    rightPresetPoint2.setFill(Color.web(getLogic().getSetting(RIGHT, SELECTED_POINT)));
                    leftPresetPoint1.setFill(Color.web(getLogic().getSetting(LEFT, EVEN_POINTS)));
                    rightPresetPoint3.setFill(Color.web(getLogic().getSetting(RIGHT, EVEN_POINTS)));
                }), new KeyFrame(Duration.seconds(1), e-> {
                    leftPresetPoint3.setFill(Color.web(getLogic().getSetting(LEFT, SELECTED_POINT)));
                    rightPresetPoint1.setFill(Color.web(getLogic().getSetting(RIGHT, SELECTED_POINT)));
                    leftPresetPoint2.setFill(Color.web(getLogic().getSetting(LEFT, ODD_POINTS)));
                    rightPresetPoint2.setFill(Color.web(getLogic().getSetting(RIGHT, ODD_POINTS)));
                }), new KeyFrame(Duration.seconds(1.5))
        );
        return out;
    }

    public static Timeline createVictoryPanelAnimations(Rectangle victoryPanel, Circle victoryPawn, Button victoryExit,
                                                        ImageView victoryCrown, Label victoryLabel) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(victoryPanel.opacityProperty(), 0),
                        new KeyValue(victoryPawn.opacityProperty(), 0), new KeyValue(victoryExit.opacityProperty(), 0),
                        new KeyValue(victoryCrown.opacityProperty(), 0), new KeyValue(victoryLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(victoryPanel.opacityProperty(), 1),
                        new KeyValue(victoryPawn.opacityProperty(), 1), new KeyValue(victoryExit.opacityProperty(), 1),
                        new KeyValue(victoryCrown.opacityProperty(), 1), new KeyValue(victoryLabel.opacityProperty(), 1)
                )
        );
        timeline.setCycleCount(1);
        return timeline;
    }

    public static Timeline createVictoryPanelAnimations(Rectangle victoryPanel, Circle victoryPawn, Button victoryExit,
                                                        ImageView victoryCrown, Label victoryLabel,
                                                        ImageView tournamentRibbon) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(victoryPanel.opacityProperty(), 0),
                        new KeyValue(victoryPawn.opacityProperty(), 0),
                        new KeyValue(victoryExit.opacityProperty(), 0),
                        new KeyValue(victoryCrown.opacityProperty(), 0),
                        new KeyValue(victoryLabel.opacityProperty(), 0),
                        new KeyValue(tournamentRibbon.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(victoryPanel.opacityProperty(), 1),
                        new KeyValue(victoryPawn.opacityProperty(), 1),
                        new KeyValue(victoryExit.opacityProperty(), 1),
                        new KeyValue(victoryCrown.opacityProperty(), 1),
                        new KeyValue(victoryLabel.opacityProperty(), 1),
                        new KeyValue(tournamentRibbon.opacityProperty(), 1)
                )
        );
        timeline.setCycleCount(1);
        return timeline;
    }

    public static Timeline createDoubleDiceAnimation(ImageView[] diceArray, boolean open) {
        double initialOffset, finalOffset;
        double diceHeight = diceArray[UPPER_DICE].getFitHeight();
        if (open) {
            initialOffset = 0;
            finalOffset = diceHeight;
        } else {
            initialOffset = diceHeight;
            finalOffset = 0;
        }
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO,
                        e -> {
                            if (open) {
                                diceArray[UPPER_DOUBLE_DICE].setVisible(true);
                                diceArray[LOWER_DOUBLE_DICE].setVisible(true);
                            }
                        },
                        new KeyValue(diceArray[UPPER_DOUBLE_DICE].layoutYProperty(), diceArray[UPPER_DICE].getLayoutY() + initialOffset),
                        new KeyValue(diceArray[LOWER_DOUBLE_DICE].layoutYProperty(), diceArray[LOWER_DICE].getLayoutY() - initialOffset)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        e -> {
                            if (!open) {
                                diceArray[UPPER_DOUBLE_DICE].setVisible(false);
                                diceArray[LOWER_DOUBLE_DICE].setVisible(false);
                            }
                        },
                        new KeyValue(diceArray[UPPER_DOUBLE_DICE].layoutYProperty(), diceArray[UPPER_DICE].getLayoutY() + finalOffset),
                        new KeyValue(diceArray[LOWER_DOUBLE_DICE].layoutYProperty(), diceArray[LOWER_DICE].getLayoutY() - finalOffset)
                )
        );
        timeline.setCycleCount(1);
        return timeline;
    }

    public static Timeline createExitZoneAnimation(Rectangle exitRegion, Rectangle outerRect, boolean open) {
        double initialWidthAndOffset, finalWidthAndOffset;
        if (open) {
            initialWidthAndOffset = 0;
            finalWidthAndOffset = GameViewRedraw.getMaxExitWidth();
        } else {
            initialWidthAndOffset = GameViewRedraw.getMaxExitWidth();
            finalWidthAndOffset = 0;
        }
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(exitRegion.widthProperty(), initialWidthAndOffset),
                        new KeyValue(exitRegion.layoutXProperty(), outerRect.getLayoutX() - initialWidthAndOffset)),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(exitRegion.widthProperty() , finalWidthAndOffset ),
                        new KeyValue(exitRegion.layoutXProperty(), (outerRect.getLayoutX() - finalWidthAndOffset))
                )
        );
        timeline.setCycleCount(1);
        return timeline;
    }

    public static Timeline createDiceTrayAnimation (DynamicGameBoard board, Rectangle diceTray,
                                             ImageView[] diceArray) {
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5),
                    e-> {
                        diceArray[UPPER_DICE].setVisible(true);
                        diceArray[LOWER_DICE].setVisible(true);
                        DynamicGameBoardRedraw.resizeDice(board);
                        getLogic().firstTurn();
                    },
                    new KeyValue(diceTray.widthProperty() , GameViewRedraw.getMaxDTWidth() )
                )
        );
        timeline.setCycleCount(1);
        return timeline;
    }

    public static Timeline createTurnTimerTimeline (Rectangle timerIn) {
        Timeline out = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerIn.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(getLogic().getTurnDuration()), e -> {
                    getLogic().completeMoves();
                }, new KeyValue(timerIn.scaleYProperty(), 0)));
        return out;
    }

    public static Timeline createTutorialBoxTimeline (AnchorPane textBoxToOpen, AnchorPane textBoxToClose) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        e -> textBoxToClose.setMouseTransparent(true),
                        new KeyValue(textBoxToOpen.scaleXProperty(), 0),
                        new KeyValue(textBoxToOpen.scaleYProperty(), 0),
                        new KeyValue(textBoxToClose.scaleXProperty(), 1),
                        new KeyValue(textBoxToClose.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.5),
                        e -> textBoxToOpen.setMouseTransparent(false),
                        new KeyValue(textBoxToOpen.scaleXProperty(), 1),
                        new KeyValue(textBoxToOpen.scaleYProperty(), 1),
                        new KeyValue(textBoxToClose.scaleXProperty(), 0),
                        new KeyValue(textBoxToClose.scaleYProperty(), 0))
        );
        timeline.setCycleCount(1);
        return timeline;
    }

}
