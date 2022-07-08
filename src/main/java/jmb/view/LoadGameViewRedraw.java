package jmb.view;

import static java.lang.Math.min;
import static jmb.ConstantsShared.PAWNS_PER_PLAYER;

public class LoadGameViewRedraw extends GameBoardRedraw{

    protected static void redrawAll(LoadGameView loadGameView) {
        resizeOuterRect(loadGameView);
        GameBoardRedraw.resizeInnerBoard(loadGameView);
        redrawExitRegions(loadGameView);
        if (loadGameView.saveMatrix!= null) {
            redrawPawns(loadGameView);
        }
    }

    protected static void setPawnsVisibility(LoadGameView loadGameView, boolean choice) {
        for (int i = 0; i <PAWNS_PER_PLAYER; i++) {
            loadGameView.pawnArrayBLK[i].setVisible(choice);
            loadGameView.pawnArrayWHT[i].setVisible(choice);
        }
    }

    protected static void resizeOuterRect(LoadGameView loadGameView) {
        double size = getBoardSize(loadGameView);
        loadGameView.outerRect.setLayoutX(getBoardLayoutX(loadGameView));
        loadGameView.outerRect.setLayoutY(getBoardLayoutY(loadGameView));
        loadGameView.outerRect.setWidth(size);
        loadGameView.outerRect.setHeight(size);
    }

    protected static double getBoardSize (LoadGameView loadGameView) {
        double usableWidth = loadGameView.window.getWidth()*(1- loadGameView.SEPARATOR_RATIO)* hResizeFactor;
        double usableHeight = loadGameView.window.getHeight()* vResizeFactor;
        return min(usableHeight, usableWidth);
    }

    protected static double getBoardLayoutX (LoadGameView loadGameView) {
        return (loadGameView.window.getWidth()*(1- loadGameView.SEPARATOR_RATIO)/2)-(getBoardSize(loadGameView)/2);
    }

    protected static double getBoardLayoutY (LoadGameView loadGameView) {
        return (loadGameView.window.getHeight()/2)-(getBoardSize(loadGameView)/2);
    }

    protected static void redrawExitRegions (LoadGameView loadGameView) {
        loadGameView.whiteExitRegion.setWidth(loadGameView.pawnArrayWHT[0].getRadius()*6);
        loadGameView.blackExitRegion.setWidth(loadGameView.pawnArrayBLK[0].getRadius()*6);
        loadGameView.whiteExitRegion.setLayoutX(loadGameView.outerRect.getLayoutX() - loadGameView.whiteExitRegion.getWidth());
        loadGameView.blackExitRegion.setLayoutX(loadGameView.outerRect.getLayoutX() - loadGameView.blackExitRegion.getWidth());
    }

    protected static void redrawPawns (LoadGameView loadGameView) {
        GameBoardRedraw.redrawPawns(loadGameView, loadGameView.saveMatrix);
    }


}
