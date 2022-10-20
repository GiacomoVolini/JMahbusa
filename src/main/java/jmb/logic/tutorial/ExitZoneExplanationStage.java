package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

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
        view.setNextTutorialString(logic.getString(stageStringInitial), true);
        view.tutorialTextBoxAnimation(0.2,0.2 );
        view.allowTextBoxMouseInput(false);
        view.waitForRecall(2.0);
    }

    public void action() {
        switch (internalIndex) {
            case 0:
                setUpTheStage();
                break;
            case 1:
                view.playSFX(SFX.DICE_ROLL);
            case 2: case 3: case 4: case 5: case 6:
                view.highlightPointsToOpenExit(internalIndex);
                view.tutorialDiceAnimation(true, 8);
                break;
            case 7:
                setUpForPlayerMove();
                break;
            case 8:
                checkMoveAndRespond();
                break;
            case 9:
                view.restoreBoardColors();
                logic.nextTutorialStage();
                break;
        }
        internalIndex++;
    }

    private void setUpTheStage() {
        setBoardUp();
        logic.setWhiteTurn(false);
        view.closeDoubleDice();
        view.setNextTutorialString(logic.getString(stageStringInter), true);
        view.tutorialTextBoxAnimation(0.55,0.4 );
        view.waitForRecall(1.5);
    }

    private void setUpForPlayerMove() {
        setBoardUp();
        view.restoreBoardColors();
        view.callRedraw();
        view.setNextTutorialString(logic.getString(stageStringFinal), true);
        view.tutorialTextBoxAnimation(0.52,0.2 );
    }

    private void setBoardUp() {
        logic.setUpSavedBoard(startingMatrix);
        logic.setWhiteTurn(true);
        logic.forceDice(3, 4);
        logic.movePawn(COL_BLACK -4, COL_BLACK);
        view.callRedraw();
    }

    private void checkMoveAndRespond() {
        if (logic.getBoardMatrix()[2][20]==EMPTY) {
            internalIndex-=2;
            view.setNextTutorialString(logic.getString(wrongMoveString), true);
        } else {
            view.setNextTutorialString(logic.getString(rightMoveString), true);
        }
        view.tutorialTextBoxAnimation(0.2,0.2 );
        view.waitForRecall(3);
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
