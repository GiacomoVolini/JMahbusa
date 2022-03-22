package jmb.view;

import java.io.IOException;

public interface IView {

    void openWhiteExit();

    void openBlackExit();

    void rollDice();

    void openDoubleDice();

    void closeDoubleDice();

    void setDiceContrast();

}