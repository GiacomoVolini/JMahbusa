package jmb.view.settings;

import javafx.animation.KeyFrame;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ColorStringFactory {

    public static String buildString(Color color) {
        int redComponent = (int) Math.round(color.getRed() * 255);
        int greenComponent = (int) Math.round(color.getGreen() * 255);
        int blueComponent = (int) Math.round(color.getBlue() * 255);
        String out = "#" + Integer.toHexString(redComponent) +
                Integer.toHexString(greenComponent) + Integer.toHexString(blueComponent);
        return out;
    }

}
