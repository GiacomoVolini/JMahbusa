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
    void setNextTutorialString(String text);
    void setTutorialOver();
    void setPawnsVisible(boolean set, int whoCalled);
    void tutorialPointAnimation(boolean set);
    void tutorialExitZoneAnimation(boolean set);

}