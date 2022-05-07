package jmb.view;

import java.io.IOException;

public interface IView {

    void openWhiteExit();

    void openBlackExit();

    void rollDice();

    void openDoubleDice();

    void closeDoubleDice();

    void setDiceContrast();

    void setPawnsForTurn();

    void backBTNSetDisable (boolean disable);

    void closeBlackExit();

    void closeWhiteExit();

    void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus);

}