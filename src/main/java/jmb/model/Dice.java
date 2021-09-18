package jmb.model;

import java.util.Random;

public class Dice {

    private int[] dice = new int[4];            //Array per il valore dei dadi, le posizioni 2 e 3 dell'array sono utilizzate nel caso di tiro doppio
    private boolean[] used = new boolean [4];   //Array che determina se il dado in posizione i è stato o meno usato per una mossa
    private int sum;
    private boolean doubleNum;
    Random rnd = new Random();

    //COSTRUTTORE -- inizializza i valori di array e variabili di istanza

    public Dice () {

        for (int i =0; i<4; i++) {
            this.dice[i]=0;
            this.used[i]=true;
        }

        this.sum = 0;
        this.doubleNum = false;

    }

    //---------------------------------------------

    public void TossDice() {

        // Assegna un valore da 1 a 6 ai dadi 0 e 1, controlla se i risultati sono uguali e assegna
        // di conseguenza lo stato di tiro doppio e i valori corretti ai dadi 2 e 3

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

    public void setUsed(int i, boolean used) {
        this.used[i] = used;
    }

    //--------------------------------

    public boolean InitialToss() {

        //Determina quale dei due giocatori effettuerà il primo turno

        boolean WhiteBegins;
        do {
            this.dice[0] = rnd.nextInt(6) + 1;
            this.dice[1] = rnd.nextInt(6) + 1;
        } while (this.dice[0] == this.dice[1]);
        WhiteBegins = this.dice[0] > this.dice[1];

        return WhiteBegins;

    }


}
