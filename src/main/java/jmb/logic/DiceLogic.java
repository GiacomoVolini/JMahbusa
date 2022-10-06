package jmb.logic;

import java.util.Random;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

/**
 * La classe DiceLogic modella e gestisce la logica dei dadi
 */

public class DiceLogic implements BoardDice {

    private int[] dice = new int[] {0, 0, 0, 0};            //Array per il valore dei dadi, le posizioni 2 e 3 dell'array sono utilizzate nel caso di tiro doppio
    private boolean[] used = new boolean [4];   //Array che determina se il dado in posizione i è stato o meno usato per una mossaprivate boolean[] toBeUsed = new boolean [4];//Array che memorizza quali dadi verranno utilizzati da un movimento
    private boolean[] toBeUsed = new boolean [4];//Array che memorizza quali dadi verranno utilizzati da un movimento
    private boolean doubleNum;                  //Variabile booleana per indicare lo stato di "tiro doppio"
    Random rnd = new Random();



    public DiceLogic() {
        //  COSTRUTTORE -- inizializza i valori di array e variabili di istanza
        for (int i =0; i<4; i++) {
            this.dice[i]=0;
            this.used[i]=true;
        }
        this.doubleNum = false;
    }

    //---------------------------------------------

    @Override
    public void tossDice() {

        /* Assegna un valore da 1 a 6 ai dadi 0 e 1, controlla se i risultati sono uguali e assegna
          di conseguenza lo stato di tiro doppio e i valori corretti ai dadi 2 e 3
         */

        this.dice[0] = rnd.nextInt(6) + 1;
        this.dice[1] = rnd.nextInt(6) + 1;
        for (int i =0; i<4; i++) {
            this.used[i] = false;
            this.toBeUsed[i] = false;
        }
        if (this.dice[0] == this.dice[1]) {
            this.doubleNum = true;
            this.dice[3]=this.dice[2]=this.dice[1];
        } else {
            this.doubleNum = false;
            this.dice[3]=this.dice[2]=0;
            this.used[3]=this.used[2]=true;
        }

    }

    //METODI GETTER E SETTER
    @Override
    public int getDiceValue(int i) {
        return this.dice[i];
    }
    @Override
    public boolean getUsed(int i) {
        return this.used[i];
    }
    @Override
    public int[] getDiceValues() {
        return this.dice;
    }
    @Override
    public boolean getDoubleNum() {
        return this.doubleNum;
    }
    @Override
    public boolean[] getToBeUsedArray() {
        return this.toBeUsed;
    }
    @Override
    public boolean[] getUsedArray() {
        return this.used;
    }
    @Override
    public void setToBeUsed(int i) {
        this.toBeUsed[i] = true;
    }

    //--------------------------------

    @Override
    public boolean whoStarts() {
        //  Metodo che determina quale dei due giocatori effettuerà il primo turno
        do {
            this.dice[0] = rnd.nextInt(6) + 1;
            this.dice[1] = rnd.nextInt(6) + 1;
        } while (this.dice[0] == this.dice[1]);
        boolean whiteBegins = this.dice[0] > this.dice[1];
        return whiteBegins;
    }

    @Override
    public int countAvailableDice() {
        int availableDice = 0;
        for (int i = 0; i<used.length; i++) {
            if (!used[i])
                availableDice++;
        }
        return availableDice;
    }
    @Override
    public void setDoublesToBeUsed(int howManyDice) {
        int set = 0;
        for (int i = 0; i<4 && set< howManyDice; i++) {
            if (set < howManyDice && !used[i]) {
                toBeUsed[i]=true;
                set++;
            }
        }
    }
    @Override
    public void setDiceToUsed() {
        // Il metodo, richiamato in seguito ad una mossa correttamente effettuata, imposta come usati i dadi
        //      indicati nell'array toBeUsed
        // Il metodo agisce "alla cieca" poichè si assume che i controlli necessari siano già stati
        //      effettuati in precedenza

        for (int i = 0; i < used.length; i++) {
            if (toBeUsed[i]) {
                used[i]=true;
            }
        }
    }
    @Override
    public void resetToBeUsed() {
        for (int i = 0; i < toBeUsed.length; i++)
            toBeUsed[i] = false;
    }
    // checkExitDiceSimple controlla che ci sia un dado che permetta di muovere la pedina esattamente nella zona di uscita
    @Override
    public boolean checkExitDiceSimple(int delta) {
        boolean possible = false;
        for (int i = 0; i < 4 && !possible; i++) {
            if (dice[i] == delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;

            }
        }
        if (logic.getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        return possible;
    }

    @Override
    public boolean checkExitDiceGreaterThan(int delta) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++)  {
            if (dice[i] >= delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;
            }
        }
        if (logic.getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        return possible;
    }

    @Override
    public void revertUsed(int i) {
        this.used[i]= false;
    }

    @Override
    public void forceDice(int dice0, int dice1) {
        this.dice[0]=dice0;
        this.dice[1]=dice1;
        this.used[0]=this.used[1]=false;
        this.used[2]=this.used[3]=true;
        this.doubleNum=false;
        for (boolean tbu : this.toBeUsed)
            tbu=false;
        view.setDiceContrast();
    }

    @Override
    public void forceDice(int value) {
        this.doubleNum = true;
        for (int i = 0; i<4; i++) {
            this.dice[i] = value;
            this.used[i] = false;
            this.toBeUsed[i] = false;
        }
        view.setDiceContrast();
    }


}
