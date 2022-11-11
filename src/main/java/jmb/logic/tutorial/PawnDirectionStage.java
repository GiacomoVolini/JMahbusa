package jmb.logic.tutorial;

import static jmb.ConstantsShared.SFX;
import static jmb.logic.Logic.*;

public class PawnDirectionStage extends ComparableTutorialStage{
    private String stageString = "pawnDirection";
    public PawnDirectionStage() {
        super();
        setStageIndex(1);
    }

    public void start() {
        getLogic().setWhiteTurn(false);
        getLogic().setUpNewBoard();
        getView().setPawnsVisible(true);
        getView().callRedraw();
        getView().setNextTutorialString(getLogic().getString(stageString), true);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.PAWN_DROP);
        getView().waitForRecall(0.5);
        getView().tutorialTextBoxAnimation(0.4,0.4);
    }

    public void action() {
        getView().tutorialPointAnimation(true);
    }
}
