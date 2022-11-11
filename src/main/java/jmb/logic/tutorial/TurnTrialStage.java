package jmb.logic.tutorial;

import jmb.logic.Logic;

import static jmb.logic.Logic.*;

public class TurnTrialStage extends ComparableTutorialStage{
    private String stageString = "turnTrialStage";
    public TurnTrialStage() {
        super();
        setStageIndex(4);
    }
    public void start() {
        getLogic().setWhiteTurn(true);
        Logic.getView().allowTextBoxMouseInput(false);
        getLogic().forceDice(6, 5);
        getView().tutorialDiceAnimation(false);
        getView().callRedraw();
        getView().setNextTutorialString(getLogic().getString(stageString), true);
        getView().tutorialTextBoxAnimation(0.4,0.55);
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: getLogic().getUsedArray())
            if (!used)
                allUsed = false;
        if (allUsed) {
            getLogic().nextTutorialStage();
            getView().restoreBoardColors();
        }
    }
}
