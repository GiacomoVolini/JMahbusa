package jmb.view;

public interface IView {

    void initializeMusic();

    void openWhiteExit(int whoCalled);

    void openBlackExit(int whoCalled);

    void rollDice();

    void setDiceContrast(int whoCalled);

    void setPawnsForTurn();

    void backBTNSetDisable (boolean disable);

    void closeBlackExit();

    void closeWhiteExit();

    void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus);

    void playPawnSFX();
    void setNextTutorialString(String text, boolean changeTextBox);
    void setTutorialOver();
    void setPawnsVisible(boolean set, int whoCalled);
    void tutorialTextBoxAnimation(double tbx, double tby);
    void tutorialPointAnimation(boolean set);
    void tutorialExitZoneAnimation(boolean set);
    void tutorialDiceAnimation(boolean set);
    void tutorialDiceAnimation(boolean set, int cycles);
    void tutorialDiceAnimation(boolean set, boolean infinite);
    void callRedraw (int whoCalled);
    void allowTextBoxMouseInput (boolean allow);
    void waitForRecall(int whoCalled, double seconds);
    void restoreBoardColors(int whoCalled);
    void playMusic(int which);
    void playSFX(int which);
    void pauseMusic();
    void stopMusic();
    void highlightPointsToOpenExit(int stage);
    void openDoubleDice(int whoCalled);
    void closeDoubleDice(int whoCalled);
    void setMusicVolume(double value);
    void setSFXVolume(double value);
    void colorPoint(int whoCalled, int index, String colorFill, String colorStroke);

}