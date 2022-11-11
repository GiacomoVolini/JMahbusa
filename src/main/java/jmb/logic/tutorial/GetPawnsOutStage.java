package jmb.logic.tutorial;

import static jmb.ConstantsShared.SFX;
import static jmb.logic.Logic.*;

public class GetPawnsOutStage extends ComparableTutorialStage {

    private String stageStringInitial = "getPawnsOut";
    public GetPawnsOutStage() {
        super();
        setStageIndex(8);
    }

    public void start() {
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.DICE_ROLL);
        getView().tutorialDiceAnimation(true);
        getLogic().forceDice(5);
        getView().callRedraw();
        getView().setNextTutorialString(getLogic().getString(stageStringInitial), true);
        getView().tutorialTextBoxAnimation(0.6,0.35 );
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: getLogic().getUsedArray())
            if (!used)
                allUsed = false;
        if (allUsed)
            getLogic().nextTutorialStage();
    }
}
