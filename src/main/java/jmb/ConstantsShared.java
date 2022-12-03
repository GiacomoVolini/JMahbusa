package jmb;

public class ConstantsShared {

    public static final int UNDEFINED = -1;
    public static final int COL_WHITE = 1;
    public static final int LAST_COL_TOP = 12;
    public static final int FIRST_COL_BOT = LAST_COL_TOP + 1;
    public static final int COL_BLACK = 24;
    public static final int COL_WHITE_EXIT = 25;
    public static final int COL_BLACK_EXIT = 0;
    public static final int PAWNS_PER_PLAYER = 15;

    //  Costanti per i metodi di scansione tabellone
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int SELECTED_WHITE = 3;
    public static final int SELECTED_BLACK = 4;
    public static final int WRONG_WHITE = 5;

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

    public enum TournamentStatus {
        NO_TOURNAMENT,
        TOURNAMENT_CONTINUES,
        TOURNAMENT_WON
    }


    // Costanti per il valore di boardPreset delle impostazioni
    // Si utilizzano queste tre costanti e non un Enum per l'associazione tra questi valori e la chiave boardPreset
    //  del file di impostazioni
    public static final int CUSTOM_BOARD = 0;
    public static final int LEFT_PRESET = 1;
    public static final int RIGHT_PRESET = 2;

    public static final boolean LEFT = true;
    public static final boolean RIGHT = false;

    // Costanti per le posizioni degli array preset
        public static final int BOARD_FRAME = 0;
        public static final int BOARD_INNER = 1;
        public static final int EVEN_POINTS = 2;
        public static final int ODD_POINTS = 3;
        public static final int SELECTED_POINT = 4;


    public enum SFX {
        PAWN_DROP,
        ERROR,
        SINGLE_WIN,
        DOUBLE_WIN,
        DICE_ROLL
    }
    
    public enum Music {
        MENU,
        GAME,
        TUTORIAL
    }

    private ConstantsShared() {
        throw new AssertionError();
    }
}
