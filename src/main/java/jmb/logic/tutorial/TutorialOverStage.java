package jmb.logic.tutorial;

import static jmb.logic.Logic.*;

public class TutorialOverStage extends ComparableTutorialStage{
    public TutorialOverStage() {
        super();
        setStageIndex(Integer.MAX_VALUE);
    }
    public void start() {
        getView().setTutorialOver();
        getLogic().flagTutorialPlayed();
        getView().tutorialTextBoxAnimation(0.1,0.1 );
    }
    public void action() {
    }
}
