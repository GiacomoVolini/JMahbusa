package jmb.model.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class VictoryConditionsStage extends ComparableTutorialStage {

    private int[][] startingMatrix = new int[][]
            {       {2,0,2,0,0,2,0,2,2,0,2,2,2,0,0,0,2,0,0,1,0,0,0,0,0,1},
                    {2,0,0,0,0,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,1},
                    {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            };

    private int[][] doubleWinMatrix = new int[][]
            {       {2,0,2,0,1,2,0,2,2,0,2,2,2,0,1,0,2,0,0,1,0,0,0,0,2,1},
                    {2,0,2,0,0,1,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1},
                    {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            };
    private String initialString = "Ora vedremo i diversi modi per vincere una partita di JMahbusa.";
    private String singleWinString = "Il modo più semplice per vincere è portar fuori tutte le proprie pedine prima dell'avversario.\nProva ora!";
    private String afterSingleWinString = "Proprio così! In questo caso hai ottenuto una vittoria singola. Nel caso riesca a portare fuori tutte le tue pedine senza farne portare fuori nemmeno una all'avversario otterresti una vittoria doppia!";
    private String doubleWinString = "Un altro modo per ottenere una vittoria doppia è quello di bloccare una pedina all'avversario nella sua punta di partenza. Prova!";

    public VictoryConditionsStage(){
        super();
        setStageIndex(9);
    }

    public void start() {
        view.setNextTutorialString(initialString, true);
        view.tutorialTextBoxAnimation(0.5, 0.4);
        view.waitForRecall(whoCalled, 3.0);
    }
    public void action() {
        switch (internalIndex) {
            case 0:
                if (!logic.getWhiteExit(whoCalled)) {
                    view.openWhiteExit(whoCalled);
                    logic.setWhiteExit(whoCalled, true);
                }
                if (!logic.getBlackExit(whoCalled)) {
                    view.openBlackExit(whoCalled);
                    logic.setBlackExit(whoCalled, true);
                }
                view.closeDoubleDice(whoCalled);
                view.tutorialDiceAnimation(true,20);
                break;
            case 1:
                setBoardUp(startingMatrix, 19, 24);
                view.restoreBoardColors(whoCalled);
                view.setNextTutorialString(singleWinString, true);
                view.tutorialTextBoxAnimation(0.5,0.45 );
                break;
            case 2:
                view.playSFX(SINGLE_WIN_SFX);
                view.setNextTutorialString(afterSingleWinString, true);
                view.tutorialTextBoxAnimation(0.2,0.4 );
                view.waitForRecall(whoCalled, 3.0);
                break;
            case 3:
                setBoardUp(doubleWinMatrix, 4, 10);
                view.restoreBoardColors(whoCalled);
                view.setNextTutorialString(doubleWinString, true);
                view.tutorialTextBoxAnimation(0.55,0.57 );
                break;
            case 4:
                view.playSFX(DOUBLE_WIN_SFX);
                logic.nextTutorialStage();
        }
        internalIndex++;
    }
    private void setBoardUp(int[][] matrix, int from, int to) {
        logic.forceDice(whoCalled, 5, 6);
        logic.setWhiteTurn(whoCalled, true);
        logic.setUpSavedBoard(whoCalled, matrix);
        logic.movePawn(whoCalled, from, to);
        view.callRedraw(whoCalled);
    }
}
