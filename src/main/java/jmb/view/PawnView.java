package jmb.view;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import static jmb.view.ConstantsView.*;

public class PawnView extends Circle {

    public PawnView() {
        super();
        place = UNDEFINED;
        whichPoint = UNDEFINED;
        isWhite = true;
        whichRow = 0;
    }

    //FIXME

    private int place;                  //  Variabile che indica in che tipo di regione si trova la pedina
    private int whichPoint;             //  Variabile che indica in quale punta si trova la pedina, specificando
                                        //      la posizione che essa occupa nell'array corrispondente
    private boolean isWhite;            //  Variabile che indica a quale giocatore appartiene la pedina

    private int whichRow;           //  Variabile che indica la "riga" in cui la pedina è posizionata

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

    public boolean getIsWhite() {return this.isWhite; }

    public void setIsWhite(boolean isWhite) {this.isWhite = isWhite; }

    private double getPawnCenterX () {
        return this.getLayoutX() + (this.getRadius()/2);
    }

    private double getPawnCenterY () {
        return this.getLayoutY() + (this.getRadius()/2);
    }

    protected Point2D getPawnCenter () {
        return new Point2D(getPawnCenterX(), getPawnCenterY());
    }

    public int getWhichRow() {
        return this.whichRow;
    }

    public void setWhichRow(int newNmb) {
        this.whichRow = newNmb;
    }

}
