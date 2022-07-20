package jmb.logic.tutorial;

import static jmb.ConstantsShared.TUTORIAL_CALLED;

public interface TutorialStage {

    int whoCalled = TUTORIAL_CALLED;

    void start();
    void action();

    int compareTo(ComparableTutorialStage obj);
}
