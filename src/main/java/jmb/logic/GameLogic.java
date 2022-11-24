package jmb.logic;

import java.util.ArrayDeque;

import static java.lang.Math.*;
import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.*;

public class GameLogic extends DynamicBoardLogic {

    //Deque utilizzata come Stack per la memorizzazione delle mosse effettuate
    //  in un turno
    private ArrayDeque<MoveRecord> turnMoves = new ArrayDeque<>(4);
    private String whitePlayer;
    private String blackPlayer;
    private int tournamentPoints = 1;
    private int blacksWonPoints;
    private int whitesWonPoints;
    private double turnDuration = 120;
    private boolean canRevert = true;
    private boolean gameStart = false;
    private boolean gameEndState = false;

    public GameLogic(){
        //  Creiamo un oggetto di tipo DiceLogic, che gestirà il tiro dei dadi durante la partita
        dice = new DiceLogic();
    }

    public void setUp() {
        setGameStart(false);
        setGameEndState(false);
        super.setUp();
        whiteTurn = dice.doesWhiteStart();
    }

    public void changeTurn() {
        if (getMoveBufferColumn()!=UNDEFINED)
            deselectPawn(getMoveBufferColumn(), getMoveBufferRow());
        this.whiteTurn= !this.whiteTurn;
        runTurn();
        getView().setPawnsForTurn();
        turnMoves.clear();
        getView().backBTNSetDisable(true);
    }


    public void runTurn() {
        dice.tossDice();
        getView().rollDice();
    }

    public boolean movePawn(int from, int to) {
        boolean out = this.movePawn(from, searchTopOccupiedRow(from), searchFirstFreeRow(to), to);
        if (out)
            this.victoryCheck();
        return out;
    }

    public boolean movePawn(int startingColumn, int startingRow, int destinationRow, int destinationColumn) {
        boolean possible = super.movePawn(startingColumn, startingRow, destinationRow, destinationColumn);
        if (possible && canRevert) {
            turnMoves.push(new MoveRecord(startingColumn, destinationColumn, dice.getToBeUsedArray()));
            if (moveOpensWhiteExit)
                turnMoves.peek().moveOpensWhiteExit();
            else if (moveOpensBlackExit)
                turnMoves.peek().moveOpensBlackExit();
            moveOpensWhiteExit=moveOpensBlackExit=false;
            getView().backBTNSetDisable(false);
        }
        return possible;
    }

    protected void completeMoves() {
        int availableDice = dice.countAvailableDice();
        boolean atLeastOneMoveDone = true;
        while (availableDice!=0 && atLeastOneMoveDone) {
            atLeastOneMoveDone = false;
            if (dice.getDoubleNum()) {
                for (int i = availableDice; i>0 && availableDice!=0; i--) {
                    boolean moveDone = tryToMove(dice.getDiceValue(0) * i);
                    if (moveDone) {
                        availableDice -= i;
                        atLeastOneMoveDone = true;
                    }
                }
            } else {
                if (availableDice == 2) {
                    if (tryToMove(dice.getDiceValue(0) + dice.getDiceValue(1))) {
                        availableDice = 0;
                        atLeastOneMoveDone = true;
                    }
                }
                if (availableDice!=0 &&tryToMove(dice.getDiceValue(0))) {
                    availableDice--;
                    atLeastOneMoveDone = true;
                }
                if (availableDice!=0 &&tryToMove(dice.getDiceValue(1))) {
                    availableDice--;
                    atLeastOneMoveDone = true;
                }
            }
        }
        victoryCheck();
    }

    private boolean tryToMove(int delta) {
        int sign, firstCol, lastCol;
        if (isWhiteTurn()) {
            sign = 1;
            firstCol = COL_WHITE;
            lastCol = COL_BLACK;
        } else {
            sign = -1;
            firstCol = COL_BLACK;
            lastCol = COL_WHITE;
        }
        boolean moveDone = false;
        for (int i = firstCol; lastCol+sign!=i && !moveDone; i+=sign) {
            int to = max(0, min(25, i+(delta*sign)));
            if (searchTopOccupiedRow(i) != UNDEFINED)
                moveDone = movePawn(i, to);
        }
        return moveDone;
    }

    public int searchFirstFreeRow(int whichPoint) {
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

    protected void victoryCheck() {
        if(!gameEndState) {
            if (blackDoubleWinCondition())
                gameWon(BLACK_WINS, DOUBLE_WIN);
            else if (whiteDoubleWinCondition())
                gameWon(WHITE_WINS, DOUBLE_WIN);
            else if (blackSingleWinCondition())
                gameWon(BLACK_WINS, SINGLE_WIN);
            else if (whiteSingleWinCondition())
                gameWon(WHITE_WINS, SINGLE_WIN);
        }
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

        if (tournamentPoints == 1)
            getView().gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, TournamentStatus.NO_TOURNAMENT);
        else if (tournamentPoints<=whitesWonPoints || tournamentPoints<=blacksWonPoints)
            getView().gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, TournamentStatus.TOURNAMENT_WON);
        else
            getView().gameWon(whitePlayer,blackPlayer,whiteWon,doubleWin, TournamentStatus.TOURNAMENT_CONTINUES);

    }

    protected void revertMove() {
        if (!turnMoves.isEmpty()) {
            MoveRecord move = turnMoves.pop();
            int from = move.getPointFin();
            int to = move.getPointInit();
            this.forceMovePawn(from, to);
            for (int i = 0; i < 4; i++) {
                if (dice.getUsed(i) && move.getDiceUsed()[i])
                    dice.revertUsed(i);
            }
            if (move.getOpensBlackExit()) {
                getView().closeBlackExit();
                setBlackExit(false);
            } else if (move.getOpensWhiteExit()) {
                getView().closeWhiteExit();
                setWhiteExit(false);
            }
            dice.resetToBeUsed();
            getView().setDiceContrast();
        }
        if (turnMoves.isEmpty())
            getView().backBTNSetDisable(true);
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

    protected void setPlayersForGame (String whitePlayer, String blackPlayer) {
        setWhitePlayer(whitePlayer);
        setBlackPlayer(blackPlayer);
        setBlacksWonPoints(0);
        setWhitesWonPoints(0);
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
    protected void setCanRevert(boolean value) {
        this.canRevert = value;
    }
    protected boolean getCanRevert() {
        return this.canRevert;
    }
    protected double getTurnDuration() {
        return this.turnDuration;
    }
    public boolean getGameStart() {
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
