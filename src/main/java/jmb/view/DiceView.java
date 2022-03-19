package jmb.view;

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


    //TODO Modificare funzione per "tirare" solo i dadi normali
    protected static void rndRolls(ImageView[] diceArr) {
        rndRoll(diceArr[0]);
        rndRoll(diceArr[1]);
    }

    protected static void rndDoubleRolls (ImageView[] diceArr) {
        rndDoubleRoll(diceArr[2]);
        rndDoubleRoll(diceArr[3]);
    }

    protected static void rndRoll(ImageView dice) {
        int i = rnd.nextInt(6);
        dice.setImage(diceImgs[i]);
    }

    protected static void rndDoubleRoll (ImageView dice) {
        int i = rnd.nextInt(6);
        dice.setImage(invDiceImgs[i]);
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


}
