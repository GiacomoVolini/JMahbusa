package jmb.logic.tutorial;

import static jmb.ConstantsShared.SFX;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class PawnDirectionStage extends ComparableTutorialStage{
    private String stageString = "pawnDirection";
    public PawnDirectionStage() {
        super();
        setStageIndex(1);
    }

    public void start() {
        logic.setWhiteTurn(false);
        logic.setUpNewBoard();
        view.setPawnsVisible(true);
        view.callRedraw();
        view.setNextTutorialString(logic.getString(stageString), true);
        view.playSFX(SFX.PAWN_DROP);
        view.waitForRecall(0.5);
        view.tutorialTextBoxAnimation(0.4,0.4);
    }

    public void action() {
        view.tutorialPointAnimation(true);
    }
}
