package jmb.controller;

public class ConstantsController {

    public static final double BOARD_RESIZE_FACTOR = 0.9;
    public static final double BOARD_POSITION_FACTOR = (1 - BOARD_RESIZE_FACTOR)/2;
    private ConstantsController() {
        throw new AssertionError() ;
    }

}
