package jmb.view;

import javafx.scene.effect.ColorAdjust;

public class ConstantsView {

    public static final String MAIN_MENU = "MainMenu";
    public static final String LOG_IN = "Login";
    public static final String SETTINGS = "SettingsView";
    public static final String LOAD_GAME = "LoadGameView";
    public static final String LEADERBOARDS = "Leaderboard";
    public static final String PLAY_GAME = "GameBoard";
    public static final String TUTORIAL = "TutorialView";

    public static final double MAX_BTN_WIDTH = 111;                     //  La larghezza massima del buttone "finish turn"
    public static final double MAX_BTN_HEIGHT = 80;                     //  L'altezza massima dei buttoni piccoli
    public static final double BUTTON_ANCHOR = 8;                      //  Il valore dell'anchor per i tre pulsanti

    public static final int UPPER_DICE = 0;
    public static final int LOWER_DICE = 1;
    public static final int UPPER_DOUBLE_DICE = 2;
    public static final int LOWER_DOUBLE_DICE = 3;


    public static final ColorAdjust LOW_CONTRAST = new ColorAdjust(0,0,0,-0.5);
    public static final double SELECTED_PAWN_SCALE = 1.2;
    public static final double NORMAL_PAWN_SCALE = 1;
    public static final double NORMAL_PAWN_STROKE_WIDTH= 2;
    public static final double SELECTED_PAWN_STROKE_WIDTH = 4;
    public static final double MOVABLE_PAWN_STROKE_WIDTH = 3.5;

    public final static int MUSIC_VOLUME = 0;
    public final static int SFX_VOLUME = 1;


    private ConstantsView() {
        throw new AssertionError() ;
    }

}
