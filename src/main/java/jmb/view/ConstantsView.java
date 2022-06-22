package jmb.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class ConstantsView {

    public static final double MAX_BTN_WIDTH = 111;                     //  La larghezza massima del buttone "finish turn"
    public static final double MAX_BTN_HEIGHT = 80;                     //  L'altezza massima dei buttoni piccoli
    public static final double BUTTON_ANCHOR = 10;                      //  Il valore dell'anchor per i tre pulsanti


    // variabili per le impostazioni (opzioni)
    public static Color pedIn1 = Color.web("#ffffff");
    public static Color pedIn2 = Color.web("#000000");
    public static Color pedOut1 = Color.web("#000000");
    public static Color pedOut2 = Color.web("#ffffff");
    public static Color table = Color.web("#e1c699");
    public static Color frame = Color.web("#432d05");
    public static Color point2 = Color.web("#2abc95");
    public static Color point = Color.web("#b27e31");

    // Colori per boardView
    public static Color red = Color.web("#ff0000");
    public static Color green = Color.web("#008000");

    //variabili per il radio botton
    public static int rd;
    public static final int middle = 1;
    public static final int right = 2;
    public static final int left = 3;

    //variabii per ilradio botton
    public static double turn_duration = 120;

    //variabili di check box
    public static boolean cb;
    public static final boolean nofullscreen = false;
    public static final boolean fullscreen = true;
    public static boolean sr;
    public static final boolean fermo = true;
    public static final boolean nonfermo = false;
    public static boolean mu;
    public static final boolean muta = true;
    public static final boolean nonmuta = false;
    public static boolean mue;
    public static final boolean mutae = true;
    public static final boolean nonmutae = false;

    //salva comandi
    public static String moveTO_right = "D";
    public static String moveTO_left = "A";
    public static String moveTO_up = "W";
    public static String moveTO_down = "S";
    public static String select = "SPACE";
    public static String confirm = "ENTER";
    public static String delete_move = "BACKSPACE";
    public static String finish_turn = "F";
    public static String main_menu = "ESCAPE";

    public static double MIN_PAWN_SIZE = 11.741538461538463;

    public static final ColorAdjust LOW_CONTRAST = new ColorAdjust(0,0,0,-0.5);


    private ConstantsView() {
        throw new AssertionError() ;
    }

}
