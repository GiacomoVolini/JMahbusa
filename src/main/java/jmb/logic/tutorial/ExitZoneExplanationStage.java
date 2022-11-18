package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.*;

public class ExitZoneExplanationStage extends ComparableTutorialStage {

    private String stageStringInitial = "exitZoneStageInitial";
    private String stageStringInter = "exitZoneStageInter";
    private String stageStringFinal = "exitZoneStageFinal";
    private String wrongMoveString = "wrongMove";
    private String rightMoveString = "rightMove";
    public ExitZoneExplanationStage() {
        super();
        setStageIndex(7);
    }

    public void start() {
        getView().setNextTutorialString(getLogic().getString(stageStringInitial), true);
        getView().tutorialTextBoxAnimation(0.2,0.2 );
        getView().allowTextBoxMouseInput(false);
        getView().waitForRecall(2.0);
    }

    public void action() {
        switch (internalIndex) {
            case 0:
                setUpTheStage();
                break;
            case 1:
                if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
                    getView().playSFX(SFX.DICE_ROLL);
            case 2: case 3: case 4: case 5: case 6:
                getView().highlightPointsToOpenExit(internalIndex);
                getView().tutorialDiceAnimation(true, 8);
                break;
            case 7:
                setUpForPlayerMove();
                break;
            case 8:
                checkMoveAndRespond();
                break;
            case 9:
                getView().restoreBoardColors();
                getLogic().nextTutorialStage();
                break;
        }
        internalIndex++;
    }

    private void setUpTheStage() {
        setBoardUp();
        getLogic().setWhiteTurn(false);
        getView().closeDoubleDice();
        getView().setNextTutorialString(getLogic().getString(stageStringInter), true);
        getView().tutorialTextBoxAnimation(0.55,0.4 );
        getView().waitForRecall(1.5);
    }

    private void setUpForPlayerMove() {
        setBoardUp();
        getView().restoreBoardColors();
        getView().callRedraw();
        getView().setNextTutorialString(getLogic().getString(stageStringFinal), true);
        getView().tutorialTextBoxAnimation(0.52,0.2 );
    }

    private void setBoardUp() {
        getLogic().setUpSavedBoard(startingMatrix);
        getLogic().setWhiteTurn(true);
        getLogic().forceDice(3, 4);
        getLogic().movePawn(COL_BLACK -4, COL_BLACK);
        getView().callRedraw();
    }

    private void checkMoveAndRespond() {
        if (getLogic().getBoardMatrix()[2][20]==EMPTY) {
            internalIndex-=2;
            getView().setNextTutorialString(getLogic().getString(wrongMoveString), true);
        } else {
            getView().setNextTutorialString(getLogic().getString(rightMoveString), true);
        }
        getView().tutorialTextBoxAnimation(0.2,0.2 );
        getView().waitForRecall(3);
    }


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
}
