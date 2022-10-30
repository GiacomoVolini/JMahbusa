package jmb.view.settings;

import javafx.animation.KeyFrame;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ColorStringFactory {

    public static String buildString(Color color) {
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
}
