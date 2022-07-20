package jmb.logic.tutorial;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class PawnDirectionStage extends ComparableTutorialStage{
    private String stageString = "pawnDirection";
    public PawnDirectionStage() {
        super();
        setStageIndex(1);
    }

    public void start() {
        logic.setWhiteTurn(whoCalled, false);
        logic.setUpNewBoard(whoCalled);
        view.setPawnsVisible(true, whoCalled);
        view.callRedraw(whoCalled);
        view.setNextTutorialString(logic.getString(stageString), true);
        view.playPawnSFX();
        view.waitForRecall(whoCalled, 0.5);
        view.tutorialTextBoxAnimation(0.4,0.4);
    }

    public void action() {
        view.tutorialPointAnimation(true);
    }
}
