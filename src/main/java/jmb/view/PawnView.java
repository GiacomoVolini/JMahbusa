package jmb.view;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;

/*
La classe PawnView estende la classe Circle aggiungendogli tre metodi utili per determinare
le coordinate del centro all'interno della finestra.
Ciò si rende necessario poiché i metodi getCenterX e getCenterY di Circle restituiscono
sempre 0.0
 */

public class PawnView extends Circle {

    public PawnView() {
        super();
    }


    protected double getPawnCenterX () {
        return this.getLayoutX() + (this.getRadius()/2);
    }

    protected double getPawnCenterY () {
        return this.getLayoutY() + (this.getRadius()/2);
    }

    protected Point2D getPawnCenter () {
        return new Point2D(getPawnCenterX(), getPawnCenterY());
    }

    protected void setPawnScale (double scale) {
        this.setScaleY(scale);
        this.setScaleX(scale);
    }

}