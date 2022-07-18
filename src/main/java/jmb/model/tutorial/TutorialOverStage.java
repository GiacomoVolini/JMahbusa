package jmb.model.tutorial;

import static jmb.model.Logic.view;

public class TutorialOverStage extends ComparableTutorialStage{
    public TutorialOverStage() {
        super();
        setStageIndex(Integer.MAX_VALUE);
    }
    public void start() {
        view.setTutorialOver();
        view.tutorialTextBoxAnimation(0.1,0.1 );
    }
    public void action() {
        System.out.println("Qualcosa non va");
    }
}
