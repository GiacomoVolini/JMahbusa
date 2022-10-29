package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class FirstOpponentTurnStage extends ComparableTutorialStage{
    private String stageString = "beforeFirstOpponentTurn";
    private String stageStringAfterAction = "afterFirstOpponentTurn";

    public FirstOpponentTurnStage() {
        super();
        setStageIndex(5);
    }
    public void start() {
        view.restoreBoardColors();
        logic.setWhiteTurn(false);
        if(!logic.getSetting("Audio", "muteSFX", boolean.class))
            view.playSFX(SFX.DICE_ROLL);
        view.tutorialDiceAnimation(true, 100);
        logic.forceDice(6);
        view.callRedraw();
        view.setNextTutorialString(logic.getString(stageString), true);
        view.tutorialTextBoxAnimation(0.1,0.4 );
    }

    public void action() {
        if (internalIndex == 0) {
            view.setNextTutorialString(logic.getString(stageStringAfterAction),true);
            view.tutorialTextBoxAnimation(0.15,0.2 );
        }
        if (internalIndex < 5)
            view.waitForRecall(2.0);
        else {
            view.allowTextBoxMouseInput(true);
            logic.nextTutorialStage();
        }
        firstOpponentMoves();
    }

    private void firstOpponentMoves() {
        switch (internalIndex) {
            default:
                break;
            case 1: case 3:
                logic.movePawn(COL_BLACK, 18);
                break;
            case 2:
                logic.movePawn(18, 12);
                break;
            case 4:
                if (logic.getBoardMatrix()[0][12]==WHITE)
                    logic.movePawn(18, 12);
                else logic.movePawn(12, 6);
                break;
        }
        view.callRedraw();
        internalIndex++;
    }
}
