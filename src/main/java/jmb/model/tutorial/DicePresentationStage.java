package jmb.model.tutorial;

import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class DicePresentationStage extends ComparableTutorialStage{
    private String stageString = "All'inizio di ogni turno vengono tirati due dadi. I risultati dei dadi determinano come si potranno muovere le pedine.";

    public DicePresentationStage() {
        super();
        setStageIndex(3);
    }

    public void start() {
        logic.setWhiteTurn(whoCalled, false);
        logic.setUpNewBoard(whoCalled);
        view.tutorialExitZoneAnimation(false);
        view.tutorialDiceAnimation(true, true);
        view.setNextTutorialString(stageString, true);
        view.tutorialTextBoxAnimation(0.1,0.5);
        view.restoreBoardColors(whoCalled);
    }
    public void action() {

    }

}
