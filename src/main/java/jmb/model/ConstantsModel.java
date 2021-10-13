package jmb.model;

public final class ConstantsModel {

    public static final int LVL_ONE = 1;
    public static final int COL_WHITE = 1;
    public static final int COL_BLACK = 24;
    public static final int COL_WHITE_EXIT = 25;
    public static final int COL_BLACK_EXIT = 0;

    public static final String IMG_DEF_WHITE = "img-def-white";
    public static final String IMG_DEF_BLACK = "img-def-black";


    private ConstantsModel() {
        throw new AssertionError() ;
    }
}
