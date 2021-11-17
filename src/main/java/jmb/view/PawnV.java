package jmb.view;

import javafx.scene.shape.Circle;
import static jmb.view.ConstantsView.*;

public class PawnV extends Circle {

    public PawnV() {
        super();
        place = UNDEFINED;
        whichPoint=UNDEFINED;
    }

    private int place;                  //  Variabile che indica in che tipo di regione si trova la pedina
    private int whichPoint;             //  Variabile che indica in quale punta si trova la pedina, specificando
                                                    //      la posizione che essa occupa nell'array corrispondente

    public int getPlace() {
        return this.place;
    }

    public int getWhichPoint() {
        return this.whichPoint;
    }

    public void setPlace (int place) {
        this.place = place;
    }

    public void setWhichPoint (int whichPoint) {
        this.whichPoint = whichPoint;
    }
}
