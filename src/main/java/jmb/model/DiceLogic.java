package jmb.model;

import java.util.Random;

/**
 * La classe DiceLogic modella e gestisce la logica dei dadi
 */

public class DiceLogic {

    private int[] dice = new int[4];            //Array per il valore dei dadi, le posizioni 2 e 3 dell'array sono utilizzate nel caso di tiro doppio
    private boolean[] used = new boolean [4];   //Array che determina se il dado in posizione i è stato o meno usato per una mossa
    private int sum;                            //Variabile intera per la somma dei dadi
    private boolean doubleNum;                  //Variabile booleana per indicare lo stato di "tiro doppio"
    Random rnd = new Random();



    public DiceLogic() {

        //  COSTRUTTORE -- inizializza i valori di array e variabili di istanza

        for (int i =0; i<4; i++) {
            this.dice[i]=0;
            this.used[i]=true;
        }

        this.sum = 0;
        this.doubleNum = false;

    }

    //---------------------------------------------

    public void tossDice() {

        /* Assegna un valore da 1 a 6 ai dadi 0 e 1, controlla se i risultati sono uguali e assegna
          di conseguenza lo stato di tiro doppio e i valori corretti ai dadi 2 e 3
         */


        this.dice[0] = rnd.nextInt(6) + 1;
        this.dice[1] = rnd.nextInt(6) + 1;
        for (int i =0; i<4; i++) {
            this.used[i] = false;
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

    public int getDiceValue (int i) {
        return this.dice[i];
    }

    public boolean getUsed (int i) {
        return this.used[i];
    }

    public int getSum() {
        return this.sum;
    }

    public void setUsed(int i, boolean used) {
        this.used[i] = used;
    }

    //--------------------------------

    public boolean initialToss() {

        //  Metodo che determina quale dei due giocatori effettuerà il primo turno

        do {
            this.dice[0] = rnd.nextInt(6) + 1;
            this.dice[1] = rnd.nextInt(6) + 1;
        } while (this.dice[0] == this.dice[1]);
        boolean whiteBegins = this.dice[0] > this.dice[1];


        return whiteBegins;

    }


}
