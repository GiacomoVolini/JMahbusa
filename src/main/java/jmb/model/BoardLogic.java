package jmb.model;

import static java.lang.Math.abs;
import static jmb.ConstantsShared.*;
import static jmb.model.Logic.view;

/** La classe BoardLogic gestisce il modello logico del tabellone, memorizzando il tipo e la posizione delle pedine e
 *  imponendo il rispetto delle regole del gioco
 */


public class BoardLogic {

    //  VARIABILI D'ISTANZA

    PawnLogic[][] squares = new PawnLogic[16][26];    //una matrice di PawnLogic, per gestire posizione e spostamento delle pedine
                                            //  le colonne 0 e 25 rappresentano le due zone di uscita per le pedine,
                                            //  mentre le restanti 24 colonne rappresentano le punte

    private boolean whiteExit;              //variabile booleana per indicare se il bianco può portare fuori le sue pedine
    private boolean blackExit;              //variabile booleana per indicare se il nero può portare fuori le sue pedine
    private boolean whiteTurn;              //variabile booleana per indicare il giocatore di turno. Se true è il turno del bianco
    private DiceLogic dice;                 //oggetto di tipo DiceLogic per la gestione del tiro dei dadi
    private int[] moveBuffer = {UNDEFINED, UNDEFINED};    //array di interi che memorizza la posizione di partenza nella matrice squares di una pedina
                                                //mentre si sta per effettuare una mossa
                                                //nella posizione 0 si memorizza la colonna, nella posizione 1 la riga

    //  ----------------------------

    //  COSTRUTTORE

    public BoardLogic(){

        //  Impostiamo a false i seguenti booleani: all'inizio della partita nessuno dei giocatori
        //  può portare fuori le proprie pedine
        this.blackExit = false;
        this.whiteExit = false;

        //  Creiamo un oggetto di tipo DiceLogic, che gestirà il tiro dei dadi durante la partita
        dice = new DiceLogic();

        //  Inizializziamo la matrice squares, assegnando le pedine dei due giocatori nelle posizioni iniziali
        //  e lasciando null negli spazi vuoti
        for (int i=0; i<=14;i++){
            squares[i][COL_WHITE]= new PawnLogic(false, true, true, COL_WHITE, i);
            squares[i][COL_BLACK]= new PawnLogic(true, false, false, COL_BLACK, i);
        }

        //Determiniamo quale giocatore inizierà la partita richiamando il metodo initialToss
        //  e tiriamo i dadi per il primo giocatore
        whiteTurn = dice.initialToss();

    }

    //  -------------------------------


    //  METODI

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void changeTurn() {
        this.whiteTurn= !this.whiteTurn;
        runTurn();
        view.setPawnsForTurn();
    }

    protected void runTurn() {
        dice.tossDice();
        view.rollDice();
    }





    /*  Il metodo possibleMove riceve informazioni sulla mossa (posizioni iniziale e finale della pedina mossa),
        poi verifica le condizioni per cui la mossa non potrebbe essere effettuata.
        Il metodo da per scontato che la pedina mossa sia del colore giusto.
     */

    public boolean possibleMove(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){

        boolean possible;

        //  Si effettuano dei controlli sui dadi
        possible = dice.checkDice( abs(puntaFinC - puntaInizC));



        //  Si controlla che la mossa sia effettuata nel verso giusto
        if (rightWay(puntaInizC, puntaInizR, puntaFinC) && possible) {

            //  Se true, controlla se la mossa è volta a portare fuori la pedina
            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT) {

                //  Se la mossa non fa uscire dal gioco una pedina controlla che la posizione di arrivo
                //  non sia bloccata per il giocatore di turno
                if (whiteTurn) {
                    if (puntaFinR> 0 && squares[puntaFinR-1][puntaFinC].getLocksWhite()) {
                        //controlla che la posizione di arrivo non sia preclusa al bianco
                        possible = false;
                    }
                } else if (puntaFinR> 0 && squares[puntaFinR-1][puntaFinC].getLocksBlack()) {
                    //controlla che la posizione di arrivo non sia preclusa al nero
                    possible = false;
                }
            }   //  Se la mossa fa uscire dal gioco la pedina controlla che al giocatore ciò sia permesso
            else if (((!this.blackExit) && !whiteTurn) || ((!this.whiteExit) && whiteTurn)) {
                possible = false;
            }
        } else possible = false;

        return possible;

    }

    /*  Il metodo movePawn si occupa dello spostamento delle pedine.
        Esso controlla innanzitutto che la mossa possa essere effettuata,
        in caso positivo sposta la pedina nella posizione desiderata e
        fa controllare se il nuovo stato del tabellone consente al giocatore
        di far uscire dal gioco le proprie pedine.
     */

    public boolean movePawn(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {

        //  Si richiama il metodo possibleMove per controllare che la mossa sia effettuabile
        boolean possible = possibleMove(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
        if(possible){

            //  Se la mossa è effettuabile sposta la pedina nella nuova posizione
            squares[puntaFinR][puntaFinC]= squares[puntaInizR][puntaInizC];
            squares[puntaFinR][puntaFinC].setWhichPoint(puntaFinC);
            squares[puntaFinR][puntaFinC].setWhichRow(puntaFinR);
            squares[puntaInizR][puntaInizC]= null;
            dice.setUsed();
            dice.resetToBeUsed();
            view.setDiceContrast();


            //  Si effettuano dei controlli per impostare lo stato di bloccato alla pedina
            if (puntaFinR==0) {
                squares[puntaFinR][puntaFinC].setLocksBlack(false);
                squares[puntaFinR][puntaFinC].setLocksWhite(false);
            } else if (squares[puntaFinR][puntaFinC].getisWhite()) {
                squares[puntaFinR][puntaFinC].setLocksBlack(true);
                squares[puntaFinR][puntaFinC].setLocksWhite(false);
            } else {
                squares[puntaFinR][puntaFinC].setLocksBlack(false);
                squares[puntaFinR][puntaFinC].setLocksWhite(true);
            }

            //  Controlla se il giocatore di turno può far uscire le proprie
            //  pedine e aggiorna di conseguenza la variabile relativa
            if (whiteTurn) {
                this.isWhiteExit();
            } else {
                this.isBlackExit();
            }
        } else {
            dice.resetToBeUsed();
        }
        return possible;
    }

    public int searchFirstFreeRow(int whichPoint) {
        // Data una colonna della matrice cerca la prima riga libera e la restituisce
        int whichRow = UNDEFINED;
        if (squares[15][whichPoint]==null) {
            boolean found = false;
            for (int i=14; i>=0 && !found; i--) {
                if (squares[i][whichPoint] !=null) {
                    found = true;
                    whichRow = i + 1;
                }
            }
            if (!found) {
                whichRow = 0;
            }
        }

        return whichRow;
    }

    public int searchTopOccupiedRow(int whichPoint) {
        // Data una colonna della matrice cerca l'ultima riga occupata
        // Il metodo restituisce UNDEFINED se la colonna è completamente vuota
        int whichRow;
        if (squares[15][whichPoint]!= null) {
            whichRow = 15;
        } else {
            whichRow = searchFirstFreeRow(whichPoint) - 1;
        }
        return whichRow;
    }



    /* Il metodo rightWay riceve delle informazioni su una mossa e controlla che questa sia effettuata nel verso giusto
            Il bianco può andare "avanti", il nero "indietro", per quanto concerne il numero di punta
     */

    public boolean rightWay(int puntaInizC, int puntaInizR, int puntaFinC) {


        boolean right = (squares[puntaInizR][puntaInizC].getisWhite() && (puntaFinC > puntaInizC)) ||
                (!squares[puntaInizR][puntaInizC].getisWhite() && (puntaFinC < puntaInizC));
        return right;

    }


    /** I metodi isBlackExit ed isWhiteExit controllano rispettivamente per il nero e per il bianco che lo stato
     *  del tabellone consenta l'uscita delle pedine e aggiornano le rispettive variabili.
     *  I metodi eseguono suddetti controlli solamente se i booleani di uscita sono ancora "false",
     *  poichè una volta diventati "true" non è possibile che tornino "false".
     *  I controlli consistono nello scandire le prime due righe delle colonne intermedie della matrice squares.
     *  Non appena la scansione trova una pedina del giocatore controllato essa si ferma e non cambia lo stato di is*Exit.
     *  In caso contrario la pone a "true".
     */

    public void isBlackExit() {

        // i=colonne  j=righe

            if (!this.blackExit){
                boolean found = false;

                for (int i = 7; i<=24 && !found; i++){
                    for (int j = 0; j<=1 && !found; j++){
                        if (squares[j][i] != null && !squares[j][i].getisWhite()){

                            found = true;
                        }
                    }
                }
                if (!found){
                    blackExit = true;
                    Logic.view.openBlackExit();
                }
            }
    }


    public void isWhiteExit() {

        // i=colonne  j=righe


        if (!this.whiteExit){
            boolean found = false;


            for (int i = 1; i<=18 && !found; i++){
                for (int j = 0; j<=1 && !found; j++){

                    if (squares[j][i] != null && squares[j][i].getisWhite()){
                        found = true;
                    }
                }
            }
            if (!found){

                whiteExit = true;
                Logic.view.openWhiteExit();
            }
        }
    }

    // Metodo setter per l'array moveBuffer
    public void setMoveBuffer (int col, int row) {
        this.moveBuffer[0] = col;
        this.moveBuffer[1] = row;
    }

    public int getMoveBufferColumn () {
        return this.moveBuffer[0];
    }

    public int getMoveBufferRow () {
        return this.moveBuffer[1];
    }

    public DiceLogic getDiceLogic () { return this.dice; }

    public void resetMoveBuffer() {
        this.moveBuffer[0] = this.moveBuffer[1] = UNDEFINED;
    }

    protected void victoryCheck() {
        if (squares[14][COL_BLACK_EXIT]!=null) {
            view.blackWins();
        } else if (squares[14][COL_WHITE_EXIT]!=null) {
            view.whiteWins();
        }
    }
}
