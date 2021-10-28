package jmb.controller;

public class ConstantsController {

    public static final double HORIZONTAL_RESIZE_FACTOR = 0.6;      //  La larghezza massima che il tabellone può occupare rispetto alla finestra
    public static final double VERTICAL_RESIZE_FACTOR = 0.9;        //  L'altezza massima che il tabellone può occupare rispetto alla finestra
    public static final double TURN_DURATION = 2;                   //  La durata in minuti del turno di gioco

    private ConstantsController() {
        throw new AssertionError() ;
    }

}
