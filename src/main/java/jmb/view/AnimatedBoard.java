package jmb.view;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public interface AnimatedBoard {


    ImageView[] getDiceArray();
    void openBlackExit();
    void openWhiteExit();
    void closeBlackExit();
    void closeWhiteExit();
    void openDiceTray();
    void setPawnsVisible(boolean set);
    void restoreBoardColors();
    void restoreColorToPoint(int i);
    void openDoubleDice();
    void closeDoubleDice();
    void colorPoint(int index, Color colorFill, Color colorStroke);
}
