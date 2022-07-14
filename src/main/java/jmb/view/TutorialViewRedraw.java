package jmb.view;

import static jmb.ConstantsShared.*;
public class TutorialViewRedraw extends DynamicGameBoardRedraw {

    protected static void resizeAll (TutorialView view) {
        setWhoCalled(TUTORIAL_CALLED);
        DynamicGameBoardRedraw.resizeAll(view);
        //TODO
    }
}
