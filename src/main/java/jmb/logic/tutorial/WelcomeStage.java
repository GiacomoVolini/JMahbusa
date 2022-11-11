package jmb.logic.tutorial;

import static jmb.logic.Logic.*;

public class WelcomeStage extends ComparableTutorialStage{

    public WelcomeStage() {
        super();
        setStageIndex(0);
    }
    private String stageString = "tutorialWelcome";

    public void start() {
        getLogic().setWhiteTurn(false);
        getView().setNextTutorialString(getLogic().getString(stageString), true);
        getView().tutorialTextBoxAnimation(0.1,0.2);
    }
    public void action() {}

}
