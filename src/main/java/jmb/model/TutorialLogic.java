package jmb.model;

import static jmb.ConstantsShared.*;
import static jmb.model.Logic.view;

public class TutorialLogic extends DynamicBoardLogic {


    private static final String TUTORIAL_STRING_0 = "Stringa 0";
    private static final String TUTORIAL_STRING_1 = "Stringa 1";
    private static final String TUTORIAL_STRING_2 = "Stringa 2";
    private static final String TUTORIAL_STRING_3 = "Stringa 3";
    private static final String TUTORIAL_STRING_4 = "Stringa 4";
    private static final String TUTORIAL_STRING_5 = "Stringa 5";
    private static final String TUTORIAL_STRING_6 = "Stringa 6";
    private static final String TUTORIAL_STRING_7 = "Stringa 7";
    private int tutorialStage = 0;

    private String[] tutorialStrings = new String[] {TUTORIAL_STRING_0, TUTORIAL_STRING_1, TUTORIAL_STRING_2, TUTORIAL_STRING_3,
                                                        TUTORIAL_STRING_4, TUTORIAL_STRING_5, TUTORIAL_STRING_6, TUTORIAL_STRING_7};
    public TutorialLogic() {
        setWhoCalled(TUTORIAL_CALLED);
    }
    protected String getNextTutorialString() {
        String out;
        if (tutorialStage < tutorialStrings.length) {
            out = tutorialStrings[tutorialStage];
            tutorialStage++;
        } else out = ARRAY_ENDED;
        return out;
    }
    protected void nextTutorialStage() {
        if (tutorialStage < tutorialStrings.length) {
            view.setNextTutorialString(tutorialStrings[tutorialStage]);
            this.setTutorialStage(tutorialStage);
            tutorialStage++;
        } else {
            view.setTutorialOver();
        }
    }
    private void setTutorialStage(int stage) {
        //TODO
    }
}
