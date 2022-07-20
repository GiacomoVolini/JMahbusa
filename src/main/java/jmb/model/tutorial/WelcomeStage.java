package jmb.model.tutorial;

import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class WelcomeStage extends ComparableTutorialStage{

    public WelcomeStage() {
        super();
        setStageIndex(0);
    }
    private String stageString = "tutorialWelcome";

    public void start() {
        logic.setWhiteTurn(whoCalled, false);
        view.setNextTutorialString(logic.getString(stageString), true);
        view.tutorialTextBoxAnimation(0.1,0.2);

    }
    public void action() {}

}
