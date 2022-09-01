package jmb.view;

import javafx.scene.layout.AnchorPane;

import static jmb.ConstantsShared.*;
public class TutorialViewRedraw extends DynamicGameBoardRedraw {

    protected static void resizeAll (TutorialView view) {
        DynamicGameBoardRedraw.resizeAll(view);
        resizeTextBox(view);
        resizeTutorialOver(view);
    }

    protected static void resizeTextBox (TutorialView view) {
        AnchorPane textBoxToResize;
        if (view.textBox1ToOpen)
            textBoxToResize = view.textBox2;
        else textBoxToResize = view.textBox1;
        textBoxToResize.setLayoutX(view.windowPane.getWidth()*view.getTextBoxXFactor());
        textBoxToResize.setLayoutY(view.windowPane.getHeight()*view.getTextBoxYFactor());
    }
    protected static void resizeTutorialOver (TutorialView view) {
        view.tutorialOverPane.setLayoutX((view.windowPane.getWidth()-view.tutorialOverPane.getWidth())/2);
        view.tutorialOverPane.setLayoutY((view.windowPane.getHeight()-view.tutorialOverPane.getHeight())/2);
    }
}
