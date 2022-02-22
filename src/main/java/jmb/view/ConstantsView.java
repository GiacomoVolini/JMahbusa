package jmb.view;

public class ConstantsView {

    public static final double HORIZONTAL_RESIZE_FACTOR = 0.53;         //  La larghezza massima che il tabellone può occupare rispetto alla finestra
    public static final double VERTICAL_RESIZE_FACTOR = 0.9;            //  L'altezza massima che il tabellone può occupare rispetto alla finestra
    public static final double TURN_DURATION = 2;                       //  La durata in minuti del turno di gioco
    public static final double MAX_BTN_WIDTH = 111;                     //  La larghezza massima del buttone "finish turn"
    public static final double MAX_BTN_HEIGHT = 80;                     //  L'altezza massima dei buttoni piccoli
    public static final double BUTTON_ANCHOR = 10;                      //  Il valore dell'anchor per i tre pulsanti


    private ConstantsView() {
        throw new AssertionError() ;
    }

}
