package jmb.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class ConstantsView {

    public static final String MAIN_MENU = "MainMenu";
    public static final String LOG_IN = "Login";
    public static final String SETTINGS = "MenuImpostazioni";
    public static final String LOAD_GAME = "LoadGameView";
    public static final String LEADERBOARDS = "Leaderboard";
    public static final String PLAY_GAME = "GameBoard";
    public static final String TUTORIAL = "TutorialView";

    public static final double MAX_BTN_WIDTH = 111;                     //  La larghezza massima del buttone "finish turn"
    public static final double MAX_BTN_HEIGHT = 80;                     //  L'altezza massima dei buttoni piccoli
    public static final double BUTTON_ANCHOR = 10;                      //  Il valore dell'anchor per i tre pulsanti

    protected static final int UPPER_DICE = 0;
    protected static final int LOWER_DICE = 1;
    protected static final int UPPER_DOUBLE_DICE = 2;
    protected static final int LOWER_DOUBLE_DICE = 3;


    public static final ColorAdjust LOW_CONTRAST = new ColorAdjust(0,0,0,-0.5);
    public static final double SELECTED_PAWN_SCALE = 1.2;
    public static final double NORMAL_PAWN_SCALE = 1;
    public static final double NORMAL_PAWN_STROKE_WIDTH= 2;
    public static final double SELECTED_PAWN_STROKE_WIDTH = 4;
    public static final double MOVABLE_PAWN_STROKE_WIDTH = 3.5;


    private ConstantsView() {
        throw new AssertionError() ;
    }

}
