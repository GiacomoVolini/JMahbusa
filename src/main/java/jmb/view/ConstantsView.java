package jmb.view;

public class ConstantsView {

    public static final double HORIZONTAL_RESIZE_FACTOR = 0.53;         //  La larghezza massima che il tabellone può occupare rispetto alla finestra
    public static final double VERTICAL_RESIZE_FACTOR = 0.9;            //  L'altezza massima che il tabellone può occupare rispetto alla finestra
    public static final double TURN_DURATION = 0.25;                       //  La durata in minuti del turno di gioco
    public static final double MAX_BTN_WIDTH = 111;                     //  La larghezza massima del buttone "finish turn"
    public static final double MAX_BTN_HEIGHT = 80;                     //  L'altezza massima dei buttoni piccoli
    public static final double EXTRA_REGION_FACTOR = 0.2;               //  La larghezza delle regioni di uscita e dei dadi rispetto al tabellone
    public static final double BUTTON_ANCHOR = 10;                      //  Il valore dell'anchor per i tre pulsanti

    //  Costanti per la variabile place di PawnView
    public static final int TOP_POINTS = 1;
    public static final int BOT_POINTS = 2;
    public static final int WHITE_EXIT_REGION = 3;
    public static final int BLACK_EXIT_REGION = 4;
    //---------------------------------------------

    private ConstantsView() {
        throw new AssertionError() ;
    }

}
