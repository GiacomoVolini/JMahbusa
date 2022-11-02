package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.*;

public class FirstOpponentTurnStage extends ComparableTutorialStage{
    private String stageString = "beforeFirstOpponentTurn";
    private String stageStringAfterAction = "afterFirstOpponentTurn";

    public FirstOpponentTurnStage() {
        super();
        setStageIndex(5);
    }
    public void start() {
        getView().restoreBoardColors();
        getLogic().setWhiteTurn(false);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.DICE_ROLL);
        getView().tutorialDiceAnimation(true, 100);
        getLogic().forceDice(6);
        getView().callRedraw();
        getView().setNextTutorialString(getLogic().getString(stageString), true);
        getView().tutorialTextBoxAnimation(0.1,0.4 );
    }

    public void action() {
        if (internalIndex == 0) {
            getView().setNextTutorialString(getLogic().getString(stageStringAfterAction),true);
            view.tutorialTextBoxAnimation(0.15,0.2 );
        }
        if (internalIndex < 5)
            view.waitForRecall(2.0);
        else {
            view.allowTextBoxMouseInput(true);
            getLogic().nextTutorialStage();
        }
        firstOpponentMoves();
    }

    private void firstOpponentMoves() {
        switch (internalIndex) {
            default:
                break;
            case 1: case 3:
                getLogic().movePawn(COL_BLACK, 18);
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
