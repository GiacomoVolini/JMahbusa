package jmb.view;

import javafx.scene.layout.AnchorPane;

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
        textBoxToResize.setLayoutX(view.window.getWidth()*view.getTextBoxXFactor());
        textBoxToResize.setLayoutY(view.window.getHeight()*view.getTextBoxYFactor());
    }
    protected static void resizeTutorialOver (TutorialView view) {
        view.tutorialOverPane.setLayoutX((view.window.getWidth()-view.tutorialOverPane.getWidth())/2);
        view.tutorialOverPane.setLayoutY((view.window.getHeight()-view.tutorialOverPane.getHeight())/2);
    }
}
