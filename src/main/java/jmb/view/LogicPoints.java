package jmb.view;

import javafx.scene.layout.Region;

public class LogicPoints extends Region {

    private int howManyPawns;

    public LogicPoints() {
        super();
        this.howManyPawns = 0;
    }

    public int getHowManyPawns() {
        return this.howManyPawns;
    }

    public void incPawn() {
        this.howManyPawns++;
    }

    public void decPawn() {
        this.howManyPawns--;
    }

    public void setHowManyPawns (int newNmb) {
        this.howManyPawns = newNmb;
    }

}
