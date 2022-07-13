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

    protected void setUp() {
        System.out.println("Sono in setUp");
        setGameStart(false);
        setGameEndState(false);
        super.setUp();
        whiteTurn = dice.whoStarts();
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

    public boolean movePawn(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC) {
        boolean possible = super.movePawn(puntaInizC, puntaInizR, puntaFinR, puntaFinC);
        if (possible) {
            turnMoves.push(new MoveRecord(puntaInizC, puntaFinC, dice.getToBeUsedArray()));
            view.backBTNSetDisable(false);
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
        view.setDiceContrast(whoCalled);
        if (turnMoves.isEmpty())
            view.backBTNSetDisable(true);
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
