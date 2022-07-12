package jmb.model;

import java.util.ArrayDeque;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.model.ConstantsLogic.DESELECTED;
import static jmb.model.Logic.view;

/** La classe BoardLogic gestisce il modello logico del tabellone, memorizzando il tipo e la posizione delle pedine e
 *  imponendo il rispetto delle regole del gioco
 */


public class GameLogic extends DynamicBoardLogic {

    /*TODO
        IDEA - Creare gerarchia ereditaria simile a quella di BoardView
        VIEW                        LOGIC
        BoardView (ex GameBoard)    niente - prende da salvataggio
        DynamicBoardView            DynamicBoardLogic (da creare, prende parte dei metodi da questo)
        GameView (ex BoardView)     GameLogic (ex BoardLogic)

     */

    //  VARIABILI D'ISTANZA
    private boolean whiteTurn;              //variabile booleana per indicare il giocatore di turno. Se true è il turno del bianco
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
    private boolean gameStart = false;
    private boolean gameEndState = false;

    //  ----------------------------

    //  COSTRUTTORE

    public GameLogic(){

        //  Creiamo un oggetto di tipo DiceLogic, che gestirà il tiro dei dadi durante la partita
        dice = new DiceLogic();
        setWhoCalled(GAME_CALLED);

    }

    //  -------------------------------


    //  METODI

    protected void setUpGame() {
        System.out.println("Sono in setupgame");
        setGameStart(false);
        setGameEndState(false);
        //  Impostiamo a false i seguenti booleani: all'inizio della partita nessuno dei giocatori
        //  può portare fuori le proprie pedine
        setBlackExit(false);
        setWhiteExit(false);

        squares = new int[16][26];

        //  Inizializziamo la matrice squares, assegnando le pedine dei due giocatori nelle posizioni iniziali
        //  e lasciando null negli spazi vuoti
        for (int i=0; i<=14;i++){
            squares[i][COL_WHITE]= WHITE;
            squares[i][COL_BLACK]= BLACK;
        }

        //Determiniamo quale giocatore inizierà la partita richiamando il metodo initialToss
        //  e tiriamo i dadi per il primo giocatore
        whiteTurn = dice.initialToss();
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void changeTurn() {
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
                        if (puntaFinR > 1 && squares[puntaFinR - 1][puntaFinC] == BLACK) {
                            //controlla che la posizione di arrivo non sia preclusa al bianco
                            possible = false;
                        }
                    } else if (puntaFinR > 1 && squares[puntaFinR - 1][puntaFinC] == WHITE) {
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
            if (checkIfFarthestPawn(puntaIniz))
                possible = dice.checkExitDiceGreaterThan(delta);
        }

        return possible;
    }

    private boolean checkIfFarthestPawn( int puntaIniz) {
        boolean farthest = true;

        if (isWhiteTurn()) {
            for (int i = puntaIniz - 1; i>18 && farthest; i--) {
                if (squares[0][i]== WHITE || squares[1][i]==WHITE) {
                    farthest = false;
                }
            }
        } else {
            for (int i = puntaIniz + 1; i <=6 && farthest; i++) {
                if (squares[0][i]==BLACK || squares[1][i]==BLACK) {
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

        dice.resetToBeUsed();
        //  Si richiama il metodo possibleMove per controllare che la mossa sia effettuabile
        boolean possible = possibleMove(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
        if(possible){
            view.playmusica();
            //  Se la mossa è effettuabile sposta la pedina nella nuova posizione
            squares[puntaFinR][puntaFinC]= squares[puntaInizR][puntaInizC];
            squares[puntaInizR][puntaInizC]= EMPTY;
            dice.setUsed();
            turnMoves.push(new MoveRecord(puntaInizC, puntaFinC, dice.getToBeUsedArray()));
            view.setDiceContrast();
            view.backBTNSetDisable(false);

            //  Controlla se il giocatore di turno può far uscire le proprie
            //  pedine e aggiorna di conseguenza la variabile relativa
            if (whiteTurn) {
                this.checkWhiteExit();
            } else {
                this.checkBlackExit();
            }
        } else {
            dice.resetToBeUsed();
        }
        return possible;
    }

    public int searchFirstFreeRow(int whichPoint) {
        // Data una colonna della matrice cerca la prima riga libera e la restituisce
        int whichRow = UNDEFINED;
        if (squares[15][whichPoint]==EMPTY) {
            boolean found = false;
            for (int i=14; i>=0 && !found; i--) {
                if (squares[i][whichPoint] !=EMPTY) {
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
        if (squares[15][whichPoint]!= EMPTY) {
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
        boolean right = (squares[puntaInizR][puntaInizC] == WHITE && (puntaFinC > puntaInizC)) ||
                (squares[puntaInizR][puntaInizC] == BLACK && (puntaFinC < puntaInizC));
        return right;

    }

    //TODO Spostare metodi sotto in DynamicBoardLogic, renderli booleani.
    //  Creare metodi void sotto che chiamino i boolean e se ottengono true chiamino l'apertura e vadano a mettere lo stato opensXExit
    //      nella mosssa

    public boolean checkWhiteExit() {
        if (super.checkWhiteExit())
            turnMoves.peek().moveOpensWhiteExit();
        return true;
    }

    @Override
    public boolean checkBlackExit() {
        if (super.checkBlackExit())
            turnMoves.peek().moveOpensBlackExit();
        return true;
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
        return (squares[0][COL_WHITE]==WHITE && squares[1][COL_WHITE]==BLACK ||
                (squares[14][COL_BLACK_EXIT]!=EMPTY && squares[0][COL_WHITE_EXIT]==EMPTY));
    }

    private boolean whiteDoubleWinCondition() {
        return (squares[0][COL_BLACK]==BLACK && squares[1][COL_BLACK]==WHITE) ||
                (squares[14][COL_WHITE_EXIT]!=EMPTY && squares[0][COL_BLACK_EXIT]==EMPTY);

    }

    private boolean blackSingleWinCondition() {
        return (squares[14][COL_BLACK_EXIT]!=EMPTY && squares[0][COL_WHITE_EXIT]!=EMPTY);
    }

    private boolean whiteSingleWinCondition() {
        return (squares[14][COL_WHITE_EXIT]!=EMPTY && squares[0][COL_BLACK_EXIT]!=EMPTY);
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
        squares[fromRow][from] = EMPTY;
    }

    public void setUpSavedGame(SaveGameReader save) {
        setGameStart(false);
        setGameEndState(false);
        this.blackPlayer = save.blackPlayer;
        this.whitePlayer = save.whitePlayer;
        this.blackExit = save.blackExit;
        this.whiteExit = save.whiteExit;
        this.whiteTurn = save.isWhiteTurn;
        this.turnDuration = save.turnDuration;
        this.tournamentPoints = toIntExact(save.tournamentPoints);
        this.blacksWonPoints = toIntExact(save.blacksWonPoints);
        this.whitesWonPoints = toIntExact(save.whitesWonPoints);
        setUpSavedBoard(save.squareMatrix);
    }

    private void setUpSavedBoard(int[][] squareMatrix) {
        if (squares == null) {
            squares = new int[16][26];
        }
        for (int row =0; row<16; row++) {
            for (int col = 0; col < 26; col++) {
                squares[row][col] = squareMatrix[row][col];
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

    protected void thePawnColor(int whichPoint, int whichRow){
        if(squares[whichRow][whichPoint] == WHITE) {
            squares[whichRow][whichPoint] = SELECTED_WHITE;
        }else if(squares[whichRow][whichPoint]==BLACK){
            squares[whichRow][whichPoint] = SELECTED_BLACK;
        }
    }

    protected void deselectPawn(int col, int row) {
        squares[row][col] += DESELECTED;
    }

    protected int getBoardPlaceState (int whichPoint, int whichRow) {
        return squares[whichRow][whichPoint];
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
    protected boolean getGameStart() {
        return this.gameStart;
    }
    protected void setGameStart(boolean value) {
        this.gameStart = value;
    }
    protected boolean getGameEndState() {
        return this.gameEndState;
    }
    protected void setGameEndState(boolean value) {
        this.gameEndState = value;
    }
}
