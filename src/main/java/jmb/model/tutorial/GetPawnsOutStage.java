package jmb.model.tutorial;

import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class GetPawnsOutStage extends ComparableTutorialStage {

    private String stageStringInitial = "getPawnsOut";
    public GetPawnsOutStage() {
        super();
        setStageIndex(8);
    }

    public void start() {
        view.tutorialDiceAnimation(true);
        logic.forceDice(whoCalled, 5);
        view.callRedraw(whoCalled);
        view.setNextTutorialString(logic.getString(stageStringInitial), true);
        view.tutorialTextBoxAnimation(0.6,0.35 );
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: logic.getUsedArray(whoCalled))
            if (!used)
                allUsed = false;
        if (allUsed)
            logic.nextTutorialStage();
    }
}
