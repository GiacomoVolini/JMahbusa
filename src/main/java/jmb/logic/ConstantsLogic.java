package jmb.logic;

public final class ConstantsLogic {

    public static final int LVL_ONE = 1;


    public static final String IMG_DEF_WHITE = "img-def-white";
    public static final String IMG_DEF_BLACK = "img-def-black";

    public static final boolean WHITE_TURN = true;
    public static final boolean BLACK_TURN = false;

    public static final int SELECTED = 2;
    public static final int DESELECTED = -2;


    private ConstantsLogic() {
        throw new AssertionError() ;
    }
}