package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

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
    private String initialString = "victoryStageInitial";
    private String singleWinString = "victoryStageBeforeSingle";
    private String afterSingleWinString = "victoryStageAfterSingle";
    private String doubleWinString = "victoryStageDouble";

    public VictoryConditionsStage(){
        super();
        setStageIndex(9);
    }

    public void start() {
        view.setNextTutorialString(logic.getString(initialString), true);
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
                view.setNextTutorialString(logic.getString(singleWinString), true);
                view.tutorialTextBoxAnimation(0.5,0.45 );
                break;
            case 2:
                view.playSFX(SINGLE_WIN_SFX);
                view.setNextTutorialString(logic.getString(afterSingleWinString), true);
                view.tutorialTextBoxAnimation(0.2,0.4 );
                view.waitForRecall(whoCalled, 3.0);
                break;
            case 3:
                setBoardUp(doubleWinMatrix, 4, 10);
                view.restoreBoardColors(whoCalled);
                view.setNextTutorialString(logic.getString(doubleWinString), true);
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
