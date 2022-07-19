package jmb.model.tutorial;

import static jmb.ConstantsShared.COL_BLACK;
import static jmb.ConstantsShared.EMPTY;
import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class ExitZoneExplanationStage extends ComparableTutorialStage {

    private int[][] startingMatrix = new int[][]
            {   {0,0,2,0,0,2,0,2,2,0,2,2,2,0,0,0,2,1,2,1,2,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,2,0,0,2,2,0,0,0,2,0,0,0,1,1,1,0,1,0},
                {0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            };

    private String stageStringInitial = "Ora passeremo ad una fase più avanzata del gioco.";
    private String stageStringInter = "Sei in una posizione molto favorevole, ma non puoi portare fuori ancora nessuna pedina: per farlo, infatti, un giocatore deve avere tutte le proprie pedine nella propria \"casa\", ovvero le ultime sei punte del proprio percorso.";
    private String stageStringFinal = "Fai la tua mossa in modo da aprire la tua zona d'uscita.";
    private String wrongMoveString = "No, hai mosso la pedina sbagliata. Tutte le tue pedine devono essere nelle ultime sei punte.\n Riprova!";
    private String rightMoveString = "Ottimo! Ora puoi cominciare a portare fuori le tue pedine. Vediamo come...";
    public ExitZoneExplanationStage() {
        super();
        setStageIndex(7);
    }

    public void start() {
        view.setNextTutorialString(stageStringInitial, true);
        view.tutorialTextBoxAnimation(0.2,0.2 );
        view.allowTextBoxMouseInput(false);
        view.waitForRecall(whoCalled, 2.0);
    }

    public void action() {
        switch (internalIndex) {
            case 0:
                setBoardUp();
                logic.setWhiteTurn(whoCalled, false);
                view.closeDoubleDice(whoCalled);
                view.setNextTutorialString(stageStringInter, true);
                view.tutorialTextBoxAnimation(0.55,0.4 );
                view.waitForRecall(whoCalled, 1.5);
                break;
            case 1: case 2: case 3: case 4: case 5: case 6:
                view.highlightPointsToOpenExit(internalIndex);
                view.tutorialDiceAnimation(true, 8);
                break;
            case 7:
                setBoardUp();
                view.restoreBoardColors(whoCalled);
                view.callRedraw(whoCalled);
                view.setNextTutorialString(stageStringFinal, true);
                view.tutorialTextBoxAnimation(0.52,0.2 );
                break;
            case 8:
                if (logic.getBoardMatrix(whoCalled)[2][20]==EMPTY) {
                    internalIndex-=2;
                    view.setNextTutorialString(wrongMoveString, true);
                } else {
                    view.setNextTutorialString(rightMoveString, true);
                }
                view.tutorialTextBoxAnimation(0.2,0.2 );
                view.waitForRecall(whoCalled, 3);
                break;
            case 9:
                view.restoreBoardColors(whoCalled);
                logic.nextTutorialStage();
                break;
        }
        internalIndex++;
    }

    private void setBoardUp() {
        logic.setUpSavedBoard(whoCalled, startingMatrix);
        logic.setWhiteTurn(whoCalled, true);
        logic.forceDice(whoCalled, 3, 4);
        logic.movePawn(whoCalled, COL_BLACK -4, COL_BLACK);
        view.callRedraw(whoCalled);
    }
}
