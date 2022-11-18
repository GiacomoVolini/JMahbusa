package jmb.view;

import javafx.scene.shape.Circle;

public class PawnView extends Circle {

    public PawnView() {
        super();
    }

    protected void setPawnScale (double scale) {
        this.setScaleY(scale);
        this.setScaleX(scale);
    }

}