package jmb.view;

import javafx.scene.paint.Color;

public class ConstantsView {

    public static final double HORIZONTAL_RESIZE_FACTOR = 0.53;         //  La larghezza massima che il tabellone può occupare rispetto alla finestra
    public static final double VERTICAL_RESIZE_FACTOR = 0.9;            //  L'altezza massima che il tabellone può occupare rispetto alla finestra
    public static final double TURN_DURATION = 2;                       //  La durata in minuti del turno di gioco
    public static final double MAX_BTN_WIDTH = 111;                     //  La larghezza massima del buttone "finish turn"
    public static final double MAX_BTN_HEIGHT = 80;                     //  L'altezza massima dei buttoni piccoli
    public static final double EXTRA_REGION_FACTOR = 0.2;               //  La larghezza delle regioni di uscita e dei dadi rispetto al tabellone
    public static final double BUTTON_ANCHOR = 10;                      //  Il valore dell'anchor per i tre pulsanti

    //  Costanti per la variabile place di PawnV
    public static final int UNDEFINED = -1;
    public static final int TOP_POINTS = 1;
    public static final int BOT_POINTS = 2;
    public static final int WHITE_EXIT_REGION = 3;
    public static final int BLACK_EXIT_REGION = 4;
    //---------------------------------------------

    // variabili per le impostazioni (opzioni)
    public static Color pedIn1 = Color.web("#ffffff");
    public static Color pedIn2 = Color.web("#000000");
    public static Color pedOut1 = Color.web("#ffffff");
    public static Color pedOut2 = Color.web("#000000");
    public static Color table = Color.web("#e1c699");
    public static Color frame = Color.web("#432d05");
    public static Color point2 = Color.web("#2abc95");
    public static Color point = Color.web("#b27e31");

    //variabili per il radio botton
    public static int rd;
    public static final int middle = 1;
    public static final int right = 2;
    public static final int left = 3;

    private ConstantsView() {
        throw new AssertionError() ;
    }

}
