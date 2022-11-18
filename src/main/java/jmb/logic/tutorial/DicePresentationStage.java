package jmb.logic.tutorial;

import static jmb.ConstantsShared.SFX;
import static jmb.logic.Logic.getLogic;
import static jmb.logic.Logic.getView;

public class DicePresentationStage extends ComparableTutorialStage{
    private String stageString = "dicePresentationString";

    public DicePresentationStage() {
        super();
        setStageIndex(3);
    }

    public void start() {
        getLogic().setWhiteTurn(false);
        getLogic().setUpNewBoard();
        getView().tutorialExitZoneAnimation(false);
        getView().tutorialDiceAnimation(true, true);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.DICE_ROLL);
        getView().setNextTutorialString(getLogic().getString(stageString), true);
        getView().tutorialTextBoxAnimation(0.1,0.5);
        getView().restoreBoardColors();
    }
    public void action() {

    }

}
