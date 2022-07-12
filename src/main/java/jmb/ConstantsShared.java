package jmb;

public class ConstantsShared {

    public static final int UNDEFINED = -1;

    public static final int COL_WHITE = 1;
    public static final int LAST_COL_TOP = 12;
    public static final int FIRST_COL_BOT = LAST_COL_TOP + 1;
    public static final int COL_BLACK = 24;
    public static final int COL_WHITE_EXIT = 25;
    public static final int LAST_BOARD_COLUMN = COL_WHITE_EXIT;
    public static final int COL_BLACK_EXIT = 0;
    public static final int FIRST_BOARD_COLUMN = COL_BLACK_EXIT;

    public static final int PAWNS_PER_PLAYER = 15;

    //  Costanti per i metodi di scansione tabellone
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int SELECTED_WHITE = 3;
    public static final int SELECTED_BLACK = 4;

    //  Costanti per i metodi di confronto nomi giocatori
    public static final int DECIDING = 0;
    public static final int SAME_NAME_ERROR = 1;
    public static final int EMPTY_NAMES_ERROR = 2;
    public static final int NAME1_ALREADY_PRESENT = 3;
    public static final int NAME2_ALREADY_PRESENT = 4;
    public static final int SUCCESS = 5;

    public static final boolean WHITE_WINS = true;
    public static final boolean BLACK_WINS = false;

    public static final boolean SINGLE_WIN = false;
    public static final boolean DOUBLE_WIN = true;

    public static final int NO_TOURNAMENT = 0;
    public static final int TOURNAMENT_CONTINUES = 1;
    public static final int TOURNAMENT_WON = 2;

    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;
    public static final int WHITE_NAME = 0;
    public static final int BLACK_NAME = 1;
    public static final int TIME = 2;
    public static final int TOURNAMENT_POINTS = 3;
    public static final int WHITE_WON_POINTS = 4;
    public static final int BLACK_WON_POINTS = 5;

    //Costanti per il valore di boardPreset delle impostazioni
    public static final int CUSTOM_BOARD = 0;
    public static final int LEFT_PRESET = 1;
    public static final int RIGHT_PRESET = 2;

    //Costanti per la variabile whoCalled per alcuni metodi del View
    public static final int GAME_CALLED = 0;
    public static final int TUTORIAL_CALLED = 1;

}
