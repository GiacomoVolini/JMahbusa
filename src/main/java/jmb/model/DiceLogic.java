package jmb.model;

import java.util.Random;

import static jmb.model.Logic.settings;
import static jmb.model.Logic.view;

/**
 * La classe DiceLogic modella e gestisce la logica dei dadi
 */

public class DiceLogic{

    private int[] dice = new int[] {0, 0, 0, 0};            //Array per il valore dei dadi, le posizioni 2 e 3 dell'array sono utilizzate nel caso di tiro doppio
    private boolean[] used = new boolean [4];   //Array che determina se il dado in posizione i è stato o meno usato per una mossa
    private boolean doubleNum;                  //Variabile booleana per indicare lo stato di "tiro doppio"
    private boolean[] toBeUsed = new boolean [4];//Array che memorizza quali dadi verranno utilizzati da un movimento
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

    public int getDiceValue (int i) {
        return this.dice[i];
    }

    public boolean getUsed (int i) {
        return this.used[i];
    }

    public int[] getDiceValues () {
        return this.dice;
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
    //--------------------------------

    public boolean whoStarts() {

        //  Metodo che determina quale dei due giocatori effettuerà il primo turno

        do {
            this.dice[0] = rnd.nextInt(6) + 1;
            this.dice[1] = rnd.nextInt(6) + 1;
        } while (this.dice[0] == this.dice[1]);
        boolean whiteBegins = this.dice[0] > this.dice[1];

        return whiteBegins;

    }

    protected boolean checkDice(int delta) {
        //  Il metodo controlla se sia possibile effettuare muovere la pedina di un certo numero di punte
        //      dato il risultato dei dadi.
        //  Per prima cosa controlla se esiste un dado con il valore esatto di delta.
        //  Nel caso contrario controlla se delta sia pari alla somma dei due dadi.
        //  Se ciò non fosse controlla se il tiro è doppio e se il movimento è possibile con i quattro dadi
        //      risultanti.

        int maxDeltaPossible;
        int availableDice = 0;
        boolean possible = false;

        //  Si scorre l'array used per controllare quanti dadi non sono stati ancora usati, e si incrementa il
        //      contatore dei dadi disponibili di conseguenza
        for (int i = 0; i<used.length; i++) {
            if (!used[i])
                availableDice++;
        }

        //  Se c'è almeno un dado disponibile si procede con i controlli, altrimenti la mossa non è possibile
        if (availableDice!=0) {
            maxDeltaPossible = availableDice * 6;
            if (delta <= maxDeltaPossible) {
                possible = checkMove(delta, availableDice);
            }
        }

        if (settings.getBypassDice())
            possible = true;

        return possible;

    }

    private boolean checkMove(int delta, int availableDice) {

        // Prima di tutto si controlla se esiste un dado non usato dal valore uguale a delta
        boolean possible = false;
        for (int i = 0; i < dice.length && !possible ; i++) {
            if (dice[i] == delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;
            }
        }

        // Nel caso in cui non sia possibile effettuare la mossa con un solo dado e ci siano almeno due dadi
        //  disponibili si procede con dei controlli sulle somme di dadi
        if (!possible && availableDice > 1) {
            // Nel caso di un tiro doppio si controlla se il delta è ottenibile tramite somme dei dadi disponibili
            if (doubleNum) {
                for (int i = 2; i <= availableDice && !possible; i++) {
                    if ((dice[0]*i) == delta && !possible) {
                        setDoublesToBeUsed(i);     // Sceglie i primi "i" dadi disponibili da usare per il movimento
                        possible = true;
                    }
                }
            } else {
                // Nel caso non si tratti di un tiro doppio si controlla che entrambi i dadi siano disponibili
                //  e che la somma dei due sia pari a delta
                if (!used[0] && !used[1] && dice[0]+dice[1] == delta) {
                    possible = true;
                    toBeUsed[0] = toBeUsed[1] = true;
                }
            }
        }

        return possible;
    }

    protected void setToBeUsed(int i) {
        this.toBeUsed[i] = true;
    }

    protected void setDoublesToBeUsed (int howManyDice) {
        int set = 0;
        for (int i = 0; i<4 && set< howManyDice; i++) {
            if (set < howManyDice && !used[i]) {
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
        for (int i = 0; i < toBeUsed.length; i++)
            toBeUsed[i] = false;
    }

    // checkExitDiceSimple controlla che ci sia un dado che permetta di muovere la pedina esattamente nella zona di uscita
    protected boolean checkExitDiceSimple (int delta) {
        boolean possible = false;
        for (int i = 0; i < 4 && !possible; i++) {
            if (dice[i] == delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;
            }
        }
        if (settings.getBypassDice())
            possible = true;
        return possible;
    }

    protected boolean checkExitDiceGreaterThan (int delta) {
        boolean possible = false;
        for (int i = 0; i<4 && !possible; i++)  {
            if (dice[i] >= delta && !used[i]) {
                possible = true;
                toBeUsed[i] = true;
            }
        }
        if (settings.getBypassDice())
            possible = true;
        return possible;
    }

    protected void revertUsed (int i) {
        this.used[i]= false;
    }

    protected void forceDice (int whoCalled, int dice0, int dice1) {
        this.dice[0]=dice0;
        this.dice[1]=dice1;
        this.used[0]=this.used[1]=false;
        this.used[2]=this.used[3]=true;
        this.doubleNum=false;
        for (boolean tbu : this.toBeUsed)
            tbu=false;
        view.setDiceContrast(whoCalled);
    }

    protected void forceDice (int whoCalled, int value) {
        this.doubleNum = true;
        for (int i = 0; i<4; i++) {
            this.dice[i] = value;
            this.used[i] = false;
            this.toBeUsed[i] = false;
        }
        view.setDiceContrast(whoCalled);
    }


}
