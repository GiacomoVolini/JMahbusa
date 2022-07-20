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
        logic.setWhiteTurn(whoCalled, true);
        view.allowTextBoxMouseInput(false);
        view.tutorialDiceAnimation(false);
        logic.forceDice(whoCalled, 5, 6);
        view.callRedraw(whoCalled);
        view.setNextTutorialString(logic.getString(stageString), true);
        view.tutorialTextBoxAnimation(0.4,0.55);
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: logic.getUsedArray(whoCalled))
            if (!used)
                allUsed = false;
        if (allUsed) {
            logic.nextTutorialStage();
            view.restoreBoardColors(whoCalled);
        }
    }
}
