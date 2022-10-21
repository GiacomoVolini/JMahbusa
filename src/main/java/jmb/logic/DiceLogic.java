package jmb.logic;

import java.util.Arrays;
import java.util.Random;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

/**
 * La classe DiceLogic modella e gestisce la logica dei dadi
 */

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

        /* Assegna un valore da 1 a 6 ai dadi 0 e 1, controlla se i risultati sono uguali e assegna
          di conseguenza lo stato di tiro doppio e i valori corretti ai dadi 2 e 3
         */

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
    
    public void resetToBeUsed() {
        Arrays.fill(toBeUsed, false);
    }

    // checkExitDiceSimple controlla che ci sia un dado che permetta di muovere la pedina esattamente nella zona di uscita
    public boolean checkExitDiceSimple(int delta) {
        boolean possible = false;
        for (int i = 0; i < 4 && !possible; i++) {
            if (values[i] == delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;
            }
        }
        if (logic.getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        return possible;
    }

    
    public boolean checkExitDiceGreaterThan(int delta) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++)  {
            if (values[i] >= delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;
            }
        }
        if (logic.getSetting("DEBUG", "bypassDice", boolean.class))
            possible = true;
        return possible;
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
        view.setDiceContrast();
    }

    
    public void forceDice(int value) {
        this.doubleNum = true;
        for (int i = 0; i<4; i++) {
            this.values[i] = value;
            this.used[i] = false;
            this.toBeUsed[i] = false;
        }
        view.setDiceContrast();
    }

    //METODI GETTER E SETTER
    
    public int getDiceValue(int i) {
        return this.values[i];
    }
    
    public boolean getUsed(int i) {
        return this.used[i];
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

    //--------------------------------




}
