package jmb.logic.tutorial;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

public class TutorialOverStage extends ComparableTutorialStage{
    public TutorialOverStage() {
        super();
        setStageIndex(Integer.MAX_VALUE);
    }
    public void start() {
        view.setTutorialOver();
        logic.flagTutorialPlayed();
        view.tutorialTextBoxAnimation(0.1,0.1 );
    }
    public void action() {
    }
}
