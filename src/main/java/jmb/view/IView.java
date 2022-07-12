package jmb.view;

import java.io.IOException;

public interface IView {

    void initializeMusic();

    void openWhiteExit(int whoCalled);

    void openBlackExit(int whoCalled);

    void rollDice();

    void setDiceContrast();

    void setPawnsForTurn();

    void backBTNSetDisable (boolean disable);

    void closeBlackExit();

    void closeWhiteExit();

    void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus);

    void playmusica();

}