package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

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
                setBoardUp();
                logic.setWhiteTurn(false);
                view.closeDoubleDice();
                view.setNextTutorialString(logic.getString(stageStringInter), true);
                view.tutorialTextBoxAnimation(0.55,0.4 );
                view.waitForRecall(1.5);
                break;
            case 1: case 2: case 3: case 4: case 5: case 6:
                view.highlightPointsToOpenExit(internalIndex);
                view.tutorialDiceAnimation(true, 8);
                break;
            case 7:
                setBoardUp();
                view.restoreBoardColors();
                view.callRedraw();
                view.setNextTutorialString(logic.getString(stageStringFinal), true);
                view.tutorialTextBoxAnimation(0.52,0.2 );
                break;
            case 8:
                if (logic.getBoardMatrix()[2][20]==EMPTY) {
                    internalIndex-=2;
                    view.setNextTutorialString(logic.getString(wrongMoveString), true);
                } else {
                    view.setNextTutorialString(logic.getString(rightMoveString), true);
                }
                view.tutorialTextBoxAnimation(0.2,0.2 );
                view.waitForRecall(3);
                break;
            case 9:
                view.restoreBoardColors();
                logic.nextTutorialStage();
                break;
        }
        internalIndex++;
    }

    private void setBoardUp() {
        logic.setUpSavedBoard(startingMatrix);
        logic.setWhiteTurn(true);
        logic.forceDice(4);
        logic.movePawn(COL_BLACK -4, COL_BLACK);
        view.callRedraw();
    }
}
