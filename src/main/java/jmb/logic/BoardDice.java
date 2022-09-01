package jmb.logic;

public interface BoardDice {
    void tossDice();

    //METODI GETTER E SETTER
    int getDiceValue(int i);

    boolean getUsed(int i);

    int[] getDiceValues();

    boolean getDoubleNum();

    boolean[] getToBeUsedArray();

    boolean[] getUsedArray();

    void setToBeUsed(int i);

    boolean whoStarts();

    int countAvailableDice();

    void setDoublesToBeUsed(int howManyDice);

    void setDiceToUsed();

    void resetToBeUsed();

    // checkExitDiceSimple controlla che ci sia un dado che permetta di muovere la pedina esattamente nella zona di uscita
    boolean checkExitDiceSimple(int delta);

    boolean checkExitDiceGreaterThan(int delta);

    void revertUsed(int i);

    void forceDice(int dice0, int dice1);

    void forceDice(int value);
}
