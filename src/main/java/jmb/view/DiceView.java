package jmb.view;

import javafx.scene.image.*;

import java.net.URISyntaxException;
import java.util.Random;

import static jmb.view.View.*;
import static jmb.view.ConstantsView.*;

public class DiceView {

    private static Random rnd = new Random();
    private static Image[] diceImgs;

    static {
        try {
            diceImgs = new Image[]{new Image(DiceView.class.getResource("diceImg/Dado1.png").toURI().toString()), new Image(DiceView.class.getResource("diceImg/Dado2.png").toURI().toString()),
                    new Image(DiceView.class.getResource("diceImg/Dado3.png").toURI().toString()), new Image(DiceView.class.getResource("diceImg/Dado4.png").toURI().toString()),
                    new Image(DiceView.class.getResource("diceImg/Dado5.png").toURI().toString()), new Image(DiceView.class.getResource("diceImg/Dado6.png").toURI().toString())};
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private static Image[] invDiceImgs;

    static {
        try {
            invDiceImgs = new Image[]{new Image(DiceView.class.getResource("diceImg/Dado1INV.png").toURI().toString()), new Image(DiceView.class.getResource("diceImg/Dado2INV.png").toURI().toString()),
                    new Image(DiceView.class.getResource("diceImg/Dado3INV.png").toURI().toString()), new Image(DiceView.class.getResource("diceImg/Dado4INV.png").toURI().toString()),
                    new Image(DiceView.class.getResource("diceImg/Dado5INV.png").toURI().toString()), new Image(DiceView.class.getResource("diceImg/Dado6INV.png").toURI().toString())};
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    protected static void rndRolls(ImageView[] diceArr) {
        rndRoll(diceArr[0]);
        rndRoll(diceArr[1]);
    }

    protected static void rndRoll(ImageView dice) {
        int i = rnd.nextInt(6);
        dice.setImage(diceImgs[i]);
    }

    public static void setDiceValues (ImageView[] diceArr) {
        int[] values = getLogic().getDiceValues();
        if(values[0]!=0) {
            diceArr[0].setImage(diceImgs[values[0] - 1]);
            diceArr[1].setImage(diceImgs[values[1] - 1]);
            if (getLogic().isRollDouble()) {
                diceArr[2].setImage(invDiceImgs[values[2] - 1]);
                diceArr[3].setImage(invDiceImgs[values[3] - 1]);
            }
        }
    }

    public static void setDiceContrast (ImageView[] diceArr) {
        for (int i = 0; i<4; i++) {
            if (getLogic().isDiceUsed(i))
                diceArr[i].setEffect(LOW_CONTRAST);
            else
                diceArr[i].setEffect(NORMAL_CONTRAST);
        }
    }

}
