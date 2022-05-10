package jmb.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static jmb.view.View.logic;

import java.util.Random;

public class DiceView {

    private static Random rnd = new Random();
    private static Image[] diceImgs = {new Image("/jmb/view/diceImg/Dado1.png"), new Image("/jmb/view/diceImg/Dado2.png"),
                        new Image("/jmb/view/diceImg/Dado3.png"), new Image("/jmb/view/diceImg/Dado4.png"),
                        new Image("/jmb/view/diceImg/Dado5.png"), new Image("/jmb/view/diceImg/Dado6.png")};
    private static Image[] invDiceImgs = {new Image("/jmb/view/diceImg/Dado1INV.png"), new Image("/jmb/view/diceImg/Dado2INV.png"),
                           new Image("/jmb/view/diceImg/Dado3INV.png"), new Image("/jmb/view/diceImg/Dado4INV.png"),
                           new Image("/jmb/view/diceImg/Dado5INV.png"), new Image("/jmb/view/diceImg/Dado6INV.png")};
    private static ColorAdjust normalContrast = new ColorAdjust(0, 0, 0, 0);
    private static ColorAdjust lowContrast = new ColorAdjust(0,0,0,-0.5);

    protected static void rndRolls(ImageView[] diceArr) {
        rndRoll(diceArr[0]);
        rndRoll(diceArr[1]);
    }

    protected static void rndRoll(ImageView dice) {
        int i = rnd.nextInt(6);
        dice.setImage(diceImgs[i]);
    }

    public static void setDiceValues (ImageView[] diceArr) {
        int[] values = logic.getDiceValues();
        diceArr[0].setImage(diceImgs[values[0]-1]);
        diceArr[1].setImage(diceImgs[values[1]-1]);
        if (logic.isRollDouble()) {
            diceArr[2].setImage(invDiceImgs[values[2]-1]);
            diceArr[3].setImage(invDiceImgs[values[3]-1]);
        }
    }

    public static void setDiceContrast (ImageView[] diceArr) {
        for (int i = 0; i<4; i++) {
            if (logic.isDiceUsed(i))
                diceArr[i].setEffect(lowContrast);
            else
                diceArr[i].setEffect(normalContrast);

        }
    }



}
