package jmb.view;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import static jmb.ConstantsShared.UNDEFINED;
import static jmb.view.View.logic;

public class DynamicGameBoard extends GameBoard{

    public DynamicGameBoard() {
        for (int i = 0; i < 15; i++) {
            pawnArrayWHT[i].setOnMouseDragged(this::drag);
            pawnArrayWHT[i].setOnMousePressed(this::savePosition);
            pawnArrayWHT[i].setOnMouseReleased(this::releasePawn);
            pawnArrayBLK[i].setOnMouseDragged(this::drag);
            pawnArrayBLK[i].setOnMousePressed(this::savePosition);
            pawnArrayBLK[i].setOnMouseReleased(this::releasePawn);
        }
    }

    private void savePosition (MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        logic.createMoveBuffer(this.searchPawnPlace(node));
    }

    //  Metodo per il trascinamento della pedina
    private void drag(MouseEvent event) {

        PawnView n = (PawnView) event.getSource();

        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }
    private void releasePawn(MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = this.searchPawnPlace(node);
        if (col != UNDEFINED) {
            logic.placePawnOnPoint(col);
        }
        BoardViewRedraw.redrawPawns(this);

    }
    private int searchPawnPlace(PawnView node) {
        //Il metodo cerca a quale zona del tabellone appartiene la pedina

        //Per ridurre il numero di iterazioni del ciclo for si determina in quale quarto del tabellone sia la pedina
        Region[] array;
        boolean top, found, left;
        int begin, end;
        int out = UNDEFINED;
        if (node.getPawnCenterY() > (boardRect.getLayoutY() + boardRect.getHeight()/2)) {
            array = regArrayBot;
            top = false;
        } else {
            array = regArrayTop;
            top = true;
        }

        if (node.getPawnCenterX() > separator.getLayoutX()) {
            begin = 6;
            end = 11;
            left = false;
        } else {
            begin = 0;
            end = 5;
            left = true;
        }


        if (left && top) {
            if (blackExitRegion.contains(blackExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = 0;
            }
        } else if (left && !top) {
            if (whiteExitRegion.contains(whiteExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = 25;
            }
        }

        //  Ciclo for per controllare se la pedina si trova su una delle sei punte possibili
        if (out == UNDEFINED) {
            found = false;
            for (int i = begin; i <= end && !found; i++) {
                if (array[i].contains(array[i].sceneToLocal(node.getPawnCenter()))) {
                    found = true;
                    if (top) {
                        out = i + 1;
                    } else {
                        out = 24 - i;
                    }
                }
            }
        }
        return out;
    }
}
