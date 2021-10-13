package jmb.model;
import static jmb.ConstantsClass.*;

/** La classe Board gestisce il modello logico del tabellone, memorizzando il tipo e la posizione delle pedine e
 *  imponendo il rispetto delle regole del gioco
 */


public class Board {

    //  VARIABILI D'ISTANZA

    Pawn[][] squares = new Pawn[16][25];    //una matrice di Pawn, per gestire posizione e spostamento delle pedine
    private boolean whiteExit;              //variabile booleana per indicare se il bianco può portare fuori le sue pedine
    private boolean blackExit;              //variabile booleana per indicare se il nero può portare fuori le sue pedine
    private boolean whiteTurn;              //variabile booleana per indicare il giocatore di turno. Se true è il turno del bianco
    private Dice dice;                      //oggetto di tipo Dice per la gestione del tiro dei dadi

    //  ----------------------------

    //  COSTRUTTORE

    public Board(){

        //  Impostiamo a false i seguenti booleani: all'inizio della partita nessuno dei giocatori
        //  può portare fuori le proprie pedine
        this.blackExit = false;
        this.whiteExit = false;

        //  Creiamo un oggetto di tipo Dice, che gestirà il tiro dei dadi durante la partita
        dice = new Dice();

        //  Inizializziamo la matrice squares, assegnando le pedine dei due giocatori nelle posizioni iniziali
        //  e lasciando null negli spazi vuoti
        for (int i=0; i<=14;i++){
            squares[i][COL_WHITE]= new Pawn(false, true, true);
            squares[i][COL_BLACK]= new Pawn(true, false, false);
        }

        //Determiniamo quale giocatore inizierà la partita richiamando il metodo initialToss
        whiteTurn = dice.initialToss();

    }

    //  -------------------------------


    //  METODI


    /*  Il metodo possibleMove riceve informazioni sulla mossa (posizioni iniziale e finale della pedina mossa),
        poi verifica le condizioni per cui la mossa non potrebbe essere effettuata.
        Il metodo da per scontato che la pedina mossa sia del colore giusto.
     */

    public boolean possibleMove(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){

        boolean possible = true;

        //  Si controlla che la mossa sia effettuata nel verso giusto
        if (rightWay(puntaInizC, puntaInizR, puntaFinC)) {

            //  Se true, controlla se la mossa è volta a portare fuori la pedina
            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT) {

                //  Se la mossa non fa uscire dal gioco una pedina controlla che la posizione di arrivo
                //  non sia bloccata per il giocatore di turno
                if (whiteTurn) {
                    if (squares[puntaFinR][puntaFinC].getisBlockedW()) {    //controlla che la posizione di arrivo non sia preclusa al bianco
                        possible = false;
                    }
                } else if (squares[puntaFinR][puntaFinC].getisBlockedB()) { //controlla che la posizione di arrivo non sia preclusa al nero
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

    public void movePawn(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){



        //  Si richiama il metodo possibleMove per controllare che la mossa sia effettuabile
        if(possibleMove(puntaInizC, puntaInizR, puntaFinR, puntaFinC)){

            //  Se la mossa è effettuabile sposta la pedina nella nuova posizione
            squares[puntaFinR][puntaFinC]= squares[puntaInizR][puntaInizC];
            squares[puntaInizR][puntaInizC]= null;

            //  Si effettuano dei controlli per impostare lo stato di bloccato alla pedina
            if (puntaFinR==0) {
                squares[puntaFinR][puntaFinC].setisBlockedB(false);
                squares[puntaFinR][puntaFinC].setisBlockedW(false);
            } else if (squares[puntaFinR][puntaFinC].getisWhite()) {
                squares[puntaFinR][puntaFinC].setisBlockedB(true);
                squares[puntaFinR][puntaFinC].setisBlockedW(false);
            } else {
                squares[puntaFinR][puntaFinC].setisBlockedB(false);
                squares[puntaFinR][puntaFinC].setisBlockedW(true);
            }

            //  Controlla se il giocatore di turno può far uscire le proprie
            //  pedine e aggiorna di conseguenza la variabile relativa
            if (whiteTurn) {
                this.isWhiteExit();
            } else {
                this.isBlackExit();
            }

        }
    }

    /* Il metodo rightWay riceve delle informazioni su una mossa e controlla che questa sia effettuata nel verso giusto
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
                boolean trovato = false;

                for (int i=1; i<=18 && !trovato; i++){
                    for (int j=0; j<=1 && !trovato; j++){
                        if (squares[j][i] != null && !squares[j][i].getisWhite()){
                            trovato = true;
                        }
                    }
                }
                if (!trovato){
                    blackExit = true;
                }
            }
    }


    public void isWhiteExit() {

        // i=colonne  j=righe


        if (!this.whiteExit){
            boolean trovato = false;

            for (int i=7; i<=24 && !trovato; i++){
                for (int j=0; j<=1 && !trovato; j++){
                    if (squares[j][i] != null && squares[j][i].getisWhite()){
                        trovato = true;
                    }
                }
            }
            if (!trovato){
                whiteExit = true;
            }
        }
    }
}