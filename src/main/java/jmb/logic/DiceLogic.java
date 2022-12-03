package jmb.logic;

import java.util.Arrays;
import java.util.Random;

import static jmb.logic.Logic.*;


public class DiceLogic {

    private int[] values = new int[] {0, 0, 0, 0};            //Array per il valore dei dadi, le posizioni 2 e 3 dell'array sono utilizzate nel caso di tiro doppio
    private boolean[] used = new boolean [4];   //Array che determina se il dado in posizione i è stato o meno usato per una mossaprivate boolean[] toBeUsed = new boolean [4];//Array che memorizza quali dadi verranno utilizzati da un movimento
    private boolean[] toBeUsed = new boolean [4];//Array che memorizza quali dadi verranno utilizzati da un movimento
    private boolean doubleNum;                  //Variabile booleana per indicare lo stato di "tiro doppio"
    Random rnd = new Random();

    public DiceLogic() {
        //  COSTRUTTORE -- inizializza i valori di array e variabili di istanza
        for (int i =0; i<4; i++) {
            this.values[i]=0;
            this.used[i]=true;
        }
        this.doubleNum = false;
    }

    
    public void tossDice() {

        this.values[0] = rnd.nextInt(6) + 1;
        this.values[1] = rnd.nextInt(6) + 1;
        Arrays.fill(this.used, false);
        Arrays.fill(this.toBeUsed, false);

        if (this.values[0] == this.values[1]) {
            this.doubleNum = true;
            this.values[3]=this.values[2]=this.values[1];
        } else {
            this.doubleNum = false;
            this.values[3]=this.values[2]=0;
            this.used[3]=this.used[2]=true;
        }

    }
    
    public boolean doesWhiteStart() {
        return rnd.nextBoolean();
    }

    
    public int countAvailableDice() {
        int availableDice = 0;
        for (boolean isUsed : used) {
            if (!isUsed)
                availableDice++;
        }
        return availableDice;
    }
    
    public void setDoublesToBeUsed(int howManyDice) {
        int set = 0;
        for (int i = 0; i<4 && set< howManyDice; i++) {
            if (!used[i]) {
                toBeUsed[i]=true;
                set++;
            }
        }
    }
    
    public void setDiceToUsed() {
        // Il metodo agisce "alla cieca" poichè si assume che i controlli necessari siano già stati
        //      effettuati in precedenza
        for (int i = 0; i < used.length; i++) {
            if (toBeUsed[i]) {
                used[i]=true;
            }
        }
    }
    
    public void resetToBeUsed() {
        Arrays.fill(toBeUsed, false);
    }

    public void revertUsed(int i) {
        this.used[i]= false;
    }

    public void forceDice(int dice0, int dice1) {
        this.values[0]=dice0;
        this.values[1]=dice1;
        this.used[0]=this.used[1]=false;
        this.used[2]=this.used[3]=true;
        this.doubleNum=false;
        for (boolean tbu : this.toBeUsed)
            tbu=false;
        getView().setDiceContrast();
    }

    
    public void forceDice(int value) {
        this.doubleNum = true;
        for (int i = 0; i<4; i++) {
            this.values[i] = value;
            this.used[i] = false;
            this.toBeUsed[i] = false;
        }
        getView().setDiceContrast();
    }
    
    public int getDiceValue(int i) {
        return this.values[i];
    }
    
    public boolean getUsed(int i) {
        return this.used[i];
    }

    public boolean areAllDiceUsed() {
        boolean allUsed = true;
        for (int i = 0; i < 4 && allUsed; i++)
            if (!used[i])
                allUsed = false;
        return allUsed;
    }
    
    public int[] getDiceValues() {
        return this.values;
    }
    
    public boolean getDoubleNum() {
        return this.doubleNum;
    }
    
    public boolean[] getToBeUsedArray() {
        return this.toBeUsed;
    }
    
    public boolean[] getUsedArray() {
        return this.used;
    }
    
    public void setToBeUsed(int i) {
        this.toBeUsed[i] = true;
    }

    protected boolean checkDiceSimple (int delta) {
        boolean out = false;
        if (getLogic().getSetting("DEBUG","bypassDice",boolean.class))
            out = true;
        for (int i = 0; i<4 && !out; i++)
            if (!getUsedArray()[i] && getDiceValues()[i] == delta) {
                out = true;
                setToBeUsed(i);
            }
        return out;
    }

    protected boolean checkDiceSum (int pointFrom, int delta, DynamicBoardLogic dynamicBoardLogic) {
        boolean legal = false;
        int sign;
        if (dynamicBoardLogic.isWhiteTurn())
            sign = 1;
        else sign = -1;
        if (getDoubleNum()) {
            int neededDice = 0;
            legal = delta % getDiceValue(0) == 0;
            if (legal) {
                legal = false;
                for (int i = 0; i < 4 && !legal; i++) {
                    if (!getUsed(i))
                        neededDice++;
                    if (getDiceValue(0) * neededDice == delta)
                        legal = true;
                }
                if (legal)
                    for (int i = 1; i <= neededDice; i++)
                        if (!dynamicBoardLogic.isPointUnlocked(pointFrom + (getDiceValue(0)*i*sign)))
                            legal = false;
                if (legal)
                    setDoublesToBeUsed(neededDice);
            }
        } else
            if (!getUsed(0) && !getUsed(1) && delta == getDiceValue(0) + getDiceValue(1)) {
                legal = dynamicBoardLogic.isPointUnlocked(pointFrom + getDiceValue(0)*sign) ||
                        dynamicBoardLogic.isPointUnlocked(pointFrom + getDiceValue(1)*sign);
                if (legal) {
                    setToBeUsed(0);
                    setToBeUsed(1);
                }
            }
        return legal;
    }

    public boolean checkExitDiceGreaterThan(int delta) {
        boolean possible = false;
        if (getLogic().getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        for (int i = 0; i<4 && !possible; i++)
            if (getDiceValue(i) >= delta && !getUsed(i)) {
                possible = true;
                getToBeUsedArray()[i] = true;
            }
        return possible;
    }
}
