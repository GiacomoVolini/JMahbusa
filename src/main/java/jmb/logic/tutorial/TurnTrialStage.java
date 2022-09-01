package jmb.logic.tutorial;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class TurnTrialStage extends ComparableTutorialStage{
    private String stageString = "turnTrialStage";
    public TurnTrialStage() {
        super();
        setStageIndex(4);
    }
    public void start() {
        logic.setWhiteTurn(true);
        view.allowTextBoxMouseInput(false);
        logic.forceDice(6, 5);
        view.tutorialDiceAnimation(false);
        view.callRedraw();
        view.setNextTutorialString(logic.getString(stageString), true);
        view.tutorialTextBoxAnimation(0.4,0.55);
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: logic.getUsedArray())
            if (!used)
                allUsed = false;
        if (allUsed) {
            logic.nextTutorialStage();
            view.restoreBoardColors();
        }
    }
}
