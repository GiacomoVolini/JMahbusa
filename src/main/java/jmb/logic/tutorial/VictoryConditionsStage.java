package jmb.logic.tutorial;

import static jmb.ConstantsShared.SFX;
import static jmb.logic.Logic.*;

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
        getView().setNextTutorialString(getLogic().getString(initialString), true);
        getView().tutorialTextBoxAnimation(0.5, 0.4);
        getView().waitForRecall(3.0);
    }
    public void action() {
        switch (internalIndex) {
            case 0:
                if (!getLogic().getWhiteExit()) {
                    getView().openWhiteExit();
                    getLogic().setWhiteExit(true);
                }
                if (!getLogic().getBlackExit()) {
                    getView().openBlackExit();
                    getLogic().setBlackExit(true);
                }
                getView().closeDoubleDice();
                if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
                    getView().playSFX(SFX.DICE_ROLL);
                getView().tutorialDiceAnimation(true,20);
                break;
            case 1:
                setBoardUp(startingMatrix, 19, 24);
                getView().restoreBoardColors();
                getView().setNextTutorialString(getLogic().getString(singleWinString), true);
                getView().tutorialTextBoxAnimation(0.5,0.45 );
                break;
            case 2:
                if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
                    getView().playSFX(SFX.SINGLE_WIN);
                getView().setNextTutorialString(getLogic().getString(afterSingleWinString), true);
                getView().tutorialTextBoxAnimation(0.2,0.4 );
                getView().waitForRecall(12.0);
                break;
            case 3:
                setBoardUp(doubleWinMatrix, 4, 10);
                getView().restoreBoardColors();
                getView().setNextTutorialString(getLogic().getString(doubleWinString), true);
                getView().tutorialTextBoxAnimation(0.55,0.57 );
                break;
            case 4:
                if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
                    getView().playSFX(SFX.DOUBLE_WIN);
                getLogic().nextTutorialStage();
        }
        internalIndex++;
    }
    private void setBoardUp(int[][] matrix, int from, int to) {
        getLogic().forceDice(5, 6);
        getLogic().setWhiteTurn(true);
        getLogic().setUpSavedBoard(matrix);
        getLogic().movePawn(from, to);
        getView().callRedraw();
    }
}
