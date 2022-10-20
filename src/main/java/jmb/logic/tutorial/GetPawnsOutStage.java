package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class GetPawnsOutStage extends ComparableTutorialStage {

    private String stageStringInitial = "getPawnsOut";
    public GetPawnsOutStage() {
        super();
        setStageIndex(8);
    }

    public void start() {
        view.playSFX(SFX.DICE_ROLL);
        view.tutorialDiceAnimation(true);
        logic.forceDice(5);
        view.callRedraw();
        view.setNextTutorialString(logic.getString(stageStringInitial), true);
        view.tutorialTextBoxAnimation(0.6,0.35 );
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: logic.getUsedArray())
            if (!used)
                allUsed = false;
        if (allUsed)
            logic.nextTutorialStage();
    }
}
