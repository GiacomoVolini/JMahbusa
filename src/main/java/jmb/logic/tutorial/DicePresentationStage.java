package jmb.logic.tutorial;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class DicePresentationStage extends ComparableTutorialStage{
    private String stageString = "dicePresentationString";

    public DicePresentationStage() {
        super();
        setStageIndex(3);
    }

    public void start() {
        logic.setWhiteTurn(false);
        logic.setUpNewBoard();
        view.tutorialExitZoneAnimation(false);
        view.tutorialDiceAnimation(true, true);
        view.setNextTutorialString(logic.getString(stageString), true);
        view.tutorialTextBoxAnimation(0.1,0.5);
        view.restoreBoardColors();
    }
    public void action() {

    }

}
