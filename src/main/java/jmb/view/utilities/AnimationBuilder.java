package jmb.view.utilities;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static jmb.ConstantsShared.*;
import static jmb.ConstantsShared.ODD_POINTS;
import static jmb.view.View.logic;

public class AnimationBuilder {

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
                    leftPresetPoint1.setFill(Color.web(logic.getSetting(LEFT, SELECTED_POINT)));
                    rightPresetPoint3.setFill(Color.web(logic.getSetting(RIGHT, SELECTED_POINT)));
                    leftPresetPoint3.setFill(Color.web(logic.getSetting(LEFT, EVEN_POINTS)));
                    rightPresetPoint1.setFill(Color.web(logic.getSetting(RIGHT, EVEN_POINTS)));
                }), new KeyFrame(Duration.seconds(0.5), e-> {
                    leftPresetPoint2.setFill(Color.web(logic.getSetting(LEFT, SELECTED_POINT)));
                    rightPresetPoint2.setFill(Color.web(logic.getSetting(RIGHT, SELECTED_POINT)));
                    leftPresetPoint1.setFill(Color.web(logic.getSetting(LEFT, EVEN_POINTS)));
                    rightPresetPoint3.setFill(Color.web(logic.getSetting(RIGHT, EVEN_POINTS)));
                }), new KeyFrame(Duration.seconds(1), e-> {
                    leftPresetPoint3.setFill(Color.web(logic.getSetting(LEFT, SELECTED_POINT)));
                    rightPresetPoint1.setFill(Color.web(logic.getSetting(RIGHT, SELECTED_POINT)));
                    leftPresetPoint2.setFill(Color.web(logic.getSetting(LEFT, ODD_POINTS)));
                    rightPresetPoint2.setFill(Color.web(logic.getSetting(RIGHT, ODD_POINTS)));
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
}
