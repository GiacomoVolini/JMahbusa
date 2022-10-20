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
        view.waitForRecall(3.0);
    }
    public void action() {
        switch (internalIndex) {
            case 0:
                if (!logic.getWhiteExit()) {
                    view.openWhiteExit();
                    logic.setWhiteExit(true);
                }
                if (!logic.getBlackExit()) {
                    view.openBlackExit();
                    logic.setBlackExit(true);
                }
                view.closeDoubleDice();
                view.playSFX(SFX.DICE_ROLL);
                view.tutorialDiceAnimation(true,20);
                break;
            case 1:
                setBoardUp(startingMatrix, 19, 24);
                view.restoreBoardColors();
                view.setNextTutorialString(logic.getString(singleWinString), true);
                view.tutorialTextBoxAnimation(0.5,0.45 );
                break;
            case 2:
                view.playSFX(SFX.SINGLE_WIN);
                view.setNextTutorialString(logic.getString(afterSingleWinString), true);
                view.tutorialTextBoxAnimation(0.2,0.4 );
                view.waitForRecall(3.0);
                break;
            case 3:
                setBoardUp(doubleWinMatrix, 4, 10);
                view.restoreBoardColors();
                view.setNextTutorialString(logic.getString(doubleWinString), true);
                view.tutorialTextBoxAnimation(0.55,0.57 );
                break;
            case 4:
                view.playSFX(SFX.DOUBLE_WIN);
                logic.nextTutorialStage();
        }
        internalIndex++;
    }
    private void setBoardUp(int[][] matrix, int from, int to) {
        logic.forceDice(5, 6);
        logic.setWhiteTurn(true);
        logic.setUpSavedBoard(matrix);
        logic.movePawn(from, to);
        view.callRedraw();
    }
}
