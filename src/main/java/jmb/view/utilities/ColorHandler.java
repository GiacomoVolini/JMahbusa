package jmb.view.utilities;

import javafx.scene.paint.Color;

import static jmb.ConstantsShared.*;
import static jmb.view.View.*;

public class ColorHandler {

    public static String buildColorString(Color color) {
        String out = "#" + componentSubstringFactory(color.getRed()) +
                componentSubstringFactory(color.getGreen()) + componentSubstringFactory(color.getBlue());
        return out;
    }

    private static String componentSubstringFactory(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        //Se il valore esadecimale è a una cifra gli aggiunge uno zero in testa e restituisce le due cifre come stringa
        //      altrimenti restituisce il valore
        return in.length() == 1 ? "0" + in : in;
    }

    public static Color getPointColor (String customColorString, int presetArrayPosition) {
        Color out;
        switch (getLogic().getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD: default:
                out = Color.web(getLogic().getSetting("Customization", customColorString, String.class));
                break;
            case LEFT_PRESET:
                out = Color.web(getLogic().getSetting(LEFT, presetArrayPosition));
                break;
            case RIGHT_PRESET:
                out = Color.web(getLogic().getSetting(RIGHT, presetArrayPosition));
                break;
        }
        return out;
    }
}
