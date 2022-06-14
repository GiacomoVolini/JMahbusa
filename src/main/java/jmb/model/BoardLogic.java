package jmb.model;

import java.util.ArrayDeque;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.model.Logic.view;

/** La classe BoardLogic gestisce il modello logico del tabellone, memorizzando il tipo e la posizione delle pedine e
 *  imponendo il rispetto delle regole del gioco
 */


public class BoardLogic {

    //  VARIABILI D'ISTANZA

    //TODO Aggiungere delle variabili per la durata del turno e per il tempo rimanente nel turno

    PawnLogic[][] squares;
    private boolean whiteExit;              //variabile booleana per indicare se il bianco può portare fuori le sue pedine
    private boolean blackExit;              //variabile booleana per indicare se il nero può portare fuori le sue pedine
    private boolean whiteTurn;              //variabile booleana per indicare il giocatore di turno. Se true è il turno del bianco
    private DiceLogic dice;                 //oggetto di tipo DiceLogic per la gestione del tiro dei dadi
    private int[] moveBuffer = {UNDEFINED, UNDEFINED};    //array di interi che memorizza la posizione di partenza nella matrice squares di una pedina
                                                //mentre si sta per effettuare una mossa
                                                //nella posizione 0 si memorizza la colonna, nella posizione 1 la riga
    private ArrayDeque<MoveRecord> turnMoves = new ArrayDeque<>(4);   //Deque utilizzata come Stack per la memorizzazione delle mosse effettuate
                                                //  in un turno

    private String whitePlayer;
    private String blackPlayer;

    private int tournamentPoints = 0;

    private int blacksWonPoints;

    private int whitesWonPoints;

    private double turnDuration = 120;

    private double timeRemaining;

    //  ----------------------------

    //  COSTRUTTORE

    public BoardLogic(){

        //  Creiamo un oggetto di tipo DiceLogic, che gestirà il tiro dei dadi durante la partita
        dice = new DiceLogic();

    }

    //  -------------------------------


    //  METODI

    protected void setUpGame() {
        //TODO Modificare una volta richiamato da LogIn

        //  Impostiamo a false i seguenti booleani: all'inizio della partita nessuno dei giocatori
        //  può portare fuori le proprie pedine
        setBlackExit(false);
        setWhiteExit(false);

        squares = new PawnLogic[16][26];

        //  Inizializziamo la matrice squares, assegnando le pedine dei due giocatori nelle posizioni iniziali
        //  e lasciando null negli spazi vuoti
        for (int i=0; i<=14;i++){
            squares[i][COL_WHITE]= PawnLogic.newWhitePawn();
            squares[i][COL_BLACK]= PawnLogic.newBlackPawn();
        }
        squares[0][COL_WHITE].setLocksBlack(false);
        squares[0][COL_BLACK].setLocksWhite(false);

        //Determiniamo quale giocatore inizierà la partita richiamando il metodo initialToss
        //  e tiriamo i dadi per il primo giocatore
        whiteTurn = dice.initialToss();

        //TODO TEST
        //SaveGameReader.readSaveGame("BUONGIORNO");
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void changeTurn() {
    /*TODO TEST
        Iterator it = turnMoves.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }
    //TODO END TEST*/

        this.whiteTurn= !this.whiteTurn;
        runTurn();
        view.setPawnsForTurn();
        turnMoves.clear();
        view.backBTNSetDisable(true);
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

        possible = (puntaInizC!=puntaFinC);

        //  Si controlla che la mossa sia effettuata nel verso giusto
        if (rightWay(puntaInizC, puntaInizR, puntaFinC) && possible) {

            //  Se true, controlla se la mossa è volta a portare fuori la pedina
            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT) {

                //  Se la mossa non fa uscire dal gioco una pedina controlla che i dadi la permettano, poi che
                //  la posizione di arrivo non sia bloccata per il giocatore di turno
                if (dice.checkDice( abs(puntaFinC - puntaInizC))) {
                    if (whiteTurn) {
                        if (puntaFinR > 0 && squares[puntaFinR - 1][puntaFinC].getLocksWhite()) {
                            //controlla che la posizione di arrivo non sia preclusa al bianco
                            possible = false;
                        }
                    } else if (puntaFinR > 0 && squares[puntaFinR - 1][puntaFinC].getLocksBlack()) {
                        //controlla che la posizione di arrivo non sia preclusa al nero
                        possible = false;
                    }
                } else possible = false;
            }   //  Se la mossa fa uscire dal gioco la pedina controlla che al giocatore ciò sia permesso
            else {
                int delta = abs(puntaFinC - puntaInizC);
                possible = delta <= 6 && checkExitMove(delta, puntaInizC);
            }
        } else possible = false;

        return possible;

    }

    private boolean checkExitMove(int delta, int puntaIniz) {

        boolean possible = dice.checkExitDiceSimple(delta);
        if (!possible) {
            if (checkIfFarthestPawn(puntaIniz)) {
                possible = dice.checkExitDiceGreaterThan(delta);
            } else {
                //TODO EVIDENZIARE PEDINE DA MUOVERE PRIMA
            }
        }

        return possible;
    }

    private boolean checkIfFarthestPawn( int puntaIniz) {

        boolean farthest = true;
        if (isWhiteTurn()) {

            for (int i = puntaIniz - 1; i>18 && farthest; i--) {
                if ((squares[0][i]!= null && squares[0][i].getisWhite()) || (squares[1][i]!= null && squares[1][i].getisWhite())) {
                    farthest = false;
                }
            }
        } else {
            for (int i = puntaIniz + 1; i <=6 && farthest; i++) {
                if ((squares[0][i]!= null && !squares[0][i].getisWhite()) || (squares[1][i]!= null && !squares[1][i].getisWhite())) {
                    farthest = false;
                }
            }
        }
        return farthest;
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
            view.playmusica();
            //  Se la mossa è effettuabile sposta la pedina nella nuova posizione
            squares[puntaFinR][puntaFinC]= squares[puntaInizR][puntaInizC];
            squares[puntaInizR][puntaInizC]= null;
            dice.setUsed();
            turnMoves.push(new MoveRecord(puntaInizC, puntaFinC, dice.getToBeUsedArray()));
            dice.resetToBeUsed();
            view.setDiceContrast();
            view.backBTNSetDisable(false);


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
                    setBlackExit(true);
                    Logic.view.openBlackExit();
                    turnMoves.peek().moveOpensBlackExit();
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
                setWhiteExit(true);
                Logic.view.openWhiteExit();
                turnMoves.peek().moveOpensWhiteExit();
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

        if (blackDoubleWinCondition())
            gameWon(BLACK_WINS, DOUBLE_WIN);
        else if (whiteDoubleWinCondition())
            gameWon(WHITE_WINS, DOUBLE_WIN);
        else if (blackSingleWinCondition())
            gameWon(BLACK_WINS, SINGLE_WIN);
        else if (whiteSingleWinCondition())
            gameWon(WHITE_WINS, SINGLE_WIN);

    }

    private boolean blackDoubleWinCondition() {
        return (squares[0][COL_WHITE]!=null && squares[0][COL_WHITE].getisWhite() &&
                squares[1][COL_WHITE]!=null && !squares[1][COL_WHITE].getisWhite()) ||
                (squares[14][COL_BLACK_EXIT]!=null && squares[0][COL_WHITE_EXIT]==null);
    }

    private boolean whiteDoubleWinCondition() {
        return (squares[0][COL_BLACK]!=null && !squares[0][COL_BLACK].getisWhite() &&
                squares[1][COL_BLACK]!=null && squares[1][COL_BLACK].getisWhite()) ||
                (squares[14][COL_WHITE_EXIT]!=null && squares[0][COL_BLACK_EXIT]==null);

    }

    private boolean blackSingleWinCondition() {
        return (squares[14][COL_BLACK_EXIT]!=null && squares[0][COL_WHITE_EXIT]!=null);
    }

    private boolean whiteSingleWinCondition() {
        return (squares[14][COL_WHITE_EXIT]!=null && squares[0][COL_BLACK_EXIT]!=null);
    }

    private void gameWon(boolean whiteWon, boolean doubleWin) {

        if (whiteWon) {
            if (doubleWin)
                whitesWonPoints += 2;
            else whitesWonPoints += 1;
        } else {
            if (doubleWin)
                blacksWonPoints +=2;
            else blacksWonPoints +=1;
        }

        if (tournamentPoints == 0)
            view.gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, NO_TOURNAMENT);
        else if (tournamentPoints<=whitesWonPoints || tournamentPoints<=blacksWonPoints)
            view.gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, TOURNAMENT_WON);
        else
            view.gameWon(whitePlayer,blackPlayer,whiteWon,doubleWin,TOURNAMENT_CONTINUES);

    }

    protected void revertMove() {
        MoveRecord move = turnMoves.pop();
        int from = move.getPointFin();
        int to = move.getPointInit();
        this.forceMovePawn(from, to);
        for (int i = 0; i<4; i++) {
            if (dice.getUsed(i) && move.getDiceUsed()[i])
                dice.revertUsed(i);
        }
        if (move.getOpensBlackExit()) {
            view.closeBlackExit();
            setBlackExit(false);
        } else if (move.getOpensWhiteExit()) {
            view.closeWhiteExit();
            setWhiteExit(false);
        }
        dice.resetToBeUsed();
        view.setDiceContrast();
        if (turnMoves.isEmpty())
            view.backBTNSetDisable(true);
    }

    private void forceMovePawn(int from, int to) {
        int toRow = searchFirstFreeRow(to);
        int fromRow = searchTopOccupiedRow(from);
        squares[toRow][to] = squares[fromRow][from];
        squares[fromRow][from] = null;

        if (toRow==0) {
            squares[toRow][to].setLocksBlack(false);
            squares[toRow][to].setLocksWhite(false);
        } else if (squares[toRow][to].getisWhite()) {
            squares[toRow][to].setLocksBlack(true);
            squares[toRow][to].setLocksWhite(false);
        } else {
            squares[toRow][to].setLocksBlack(false);
            squares[toRow][to].setLocksWhite(true);
        }
    }

    public void setUpSavedGame(SaveGameReader save) {
        this.blackPlayer = save.blackPlayer;
        this.whitePlayer = save.whitePlayer;
        this.whiteTurn = save.isWhiteTurn;
        this.turnDuration = save.turnDuration;
        this.tournamentPoints = toIntExact(save.tournamentPoints);
        this.blacksWonPoints = toIntExact(save.blacksWonPoints);
        this.whitesWonPoints = toIntExact(save.whitesWonPoints);
        setUpSavedBoard(save.squareMatrix);
        //TODO GESTIRE LOGICA TORNEO
    }

    private void setUpSavedBoard(int[][] squareMatrix) {
        if (squares == null) {
            squares = new PawnLogic[16][26];
        }
        for (int row =0; row<16; row++) {
            for (int col = 0; col < 26; col++) {
                switch(squareMatrix[row][col]) {
                    case 0:
                        squares[row][col] = null;
                        break;
                    case 1:
                        squares[row][col] = PawnLogic.newWhitePawn();
                        break;
                    case 2:
                        squares[row][col] = PawnLogic.newBlackPawn();
                        break;
                }
            }
        }
    }

    protected boolean isPawnMovable (int col, int row) {

        boolean movable = false;
        int sign;
        int endCol;
        if (isWhiteTurn())
            sign = 1;
        else sign = -1;
        for (int i = 0; i<4 && !movable; i++) {
            endCol = max (0, min(col + dice.getDiceValue(i) * sign, 25));
            if (possibleMove(col, row, searchFirstFreeRow(endCol), endCol))
                movable = true;

        }
        endCol = max(0, min(25, col + (dice.getDiceValue(0) + dice.getDiceValue(1))*sign));
        if (!movable && possibleMove(col, row, searchFirstFreeRow(endCol), endCol))
            movable = true;
        for (int i =3; i<=4 && !movable; i++) {
            endCol = max (0, min (25, col + (i *dice.getDiceValue(0)*sign)));
            if (!movable && dice.getDoubleNum() && possibleMove(col, row, searchFirstFreeRow(endCol), endCol))
                movable = true;
        }

        return movable;
    }

    protected int getBoardPlaceState (int whichPoint, int whichRow) {
        int boardPlaceState = UNDEFINED;
        if (squares[whichRow][whichPoint] == null) {
            boardPlaceState = EMPTY;
        } else if (squares[whichRow][whichPoint].getisWhite()) {
            boardPlaceState = WHITE;
        } else {
            boardPlaceState = BLACK;
        }
        return boardPlaceState;

    }


    protected void setWhiteExit (boolean value) {
        this.whiteExit = value;
    }

    protected void setBlackExit (boolean value) {
        this.blackExit = value;
    }

    protected boolean getWhiteExit() {
        return whiteExit;
    }

    protected boolean getBlackExit() {
        return blackExit;
    }

    protected void setWhitePlayer(String playerName) {
        this.whitePlayer = playerName;
    }

    protected void setBlackPlayer(String playerName) {
        this.blackPlayer = playerName;
    }

    protected String getWhitePlayer() {
        return this.whitePlayer;
    }

    protected String getBlackPlayer() {
        return this.blackPlayer;
    }

    protected void setTournamentPoints(int value) {
        tournamentPoints = value;
    }

    protected int getTournamentPoints() {
        return tournamentPoints;
    }

    protected void setBlacksWonPoints(int value) {
        blacksWonPoints = value;
    }

    protected int getBlacksWonPoints() {
        return blacksWonPoints;
    }

    protected void setWhitesWonPoints(int value) {
        whitesWonPoints = value;
    }

    protected int getWhitesWonPoints() {
        return whitesWonPoints;
    }

    protected void setTurnDuration(double value) {
        this.turnDuration = value;
    }

    protected double getTurnDuration() {
        return this.turnDuration;
    }

    protected void setTimeRemaining(double value) {
        this.timeRemaining = value;
    }

    protected double getTimeRemaining() {
        return this.timeRemaining;
    }
}
