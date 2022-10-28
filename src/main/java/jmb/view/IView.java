package jmb.view;

import jmb.ConstantsShared.*;

public interface IView {

    void setCurrentScene (GenericGUI scene);
    void initializeMusic();
    void openWhiteExit();
    void openBlackExit();
    void rollDice();
    void setDiceContrast();
    void setPawnsForTurn();
    void backBTNSetDisable (boolean disable);
    void closeBlackExit();
    void closeWhiteExit();
    void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, TournamentStatus status);
    void setNextTutorialString(String text, boolean changeTextBox);
    void setTutorialOver();
    void setPawnsVisible(boolean set);
    void tutorialTextBoxAnimation(double tbx, double tby);
    void tutorialPointAnimation(boolean set);
    void tutorialExitZoneAnimation(boolean set);
    void tutorialDiceAnimation(boolean set);
    void tutorialDiceAnimation(boolean set, int cycles);
    void tutorialDiceAnimation(boolean set, boolean infinite);
    void callRedraw ();
    void allowTextBoxMouseInput (boolean allow);
    void waitForRecall(double seconds);
    void restoreBoardColors();
    void playMusic(Music music);
    void playSFX(SFX sound);
    void pauseMusic();
    void stopMusic();
    void highlightPointsToOpenExit(int stage);
    void openDoubleDice();
    void closeDoubleDice();
    void setVolume(int whichVolume, double value);
    void colorPoint(int index, String colorFill, String colorStroke);
}