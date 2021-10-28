package jmb.controller;

public class ConstantsController {

    public static final double HORIZONTAL_RESIZE_FACTOR = 0.6;      //  La larghezza massima che il tabellone può occupare rispetto alla finestra
    public static final double VERTICAL_RESIZE_FACTOR = 0.9;        //  L'altezza massima che il tabellone può occupare rispetto alla finestra
    public static final double TURN_DURATION = 2;                   //  La durata in minuti del turno di gioco
    public static final double FIN_BTN_WIDTH = 111;                 //  La larghezza massima del buttone "finish turn"
    public static final double SMAL_BTN_WIDTH = 71;                 //  La larghezza massima dei buttoni piccoli
    public static final double FIN_BTN_HIGHT = 80;                  //  L'altezza massima del buttone "finish turn"
    public static final double SMAL_BTN_HIGHT = 38;                 //  L'altezza massima dei buttoni piccoli

    private ConstantsController() {
        throw new AssertionError() ;
    }

}
