package jmb.view;

import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

public class GameBoardRedraw {

    /*TODO
        - Individuare i metodi di BoardView
     */
    /*
        TODO
            - Semplificare il metodo per ridimensionare outerRect (togliere controllo sofisticato,
                tanto in mezzo ci si metterà la coppa del torneo)
            - Spostare dentro GameBoard (utilizzeremo gli stessi metodi di resize del tabellone sia
                in BoardView che in LoadGameView)
            - Valutare quanto sia possibile spostare anche il redraw delle pedine
            - Alternativa: creare metodo placePawns in LoadGameView (più semplice ma si potrebbe finire
                per duplicare codice)
     */
    protected static double maxExitWidth;

    protected static double hResizeFactor;
    protected static void setHResizeFactor(double value) {
        hResizeFactor = value;
    }
    protected static double vResizeFactor;
    protected static void setVResizeFactor(double value) {
        vResizeFactor = value;
    }


    // Metodo getter per maxExitWidth
    public static double getMaxExitWidth() { return maxExitWidth; }

    protected static void calcTrayWidths (GameBoard board) {
        maxExitWidth = board.pawnArrayWHT[0].getRadius() * 6 ;
    }


    protected static double getBoardSize (GameBoard board) {
//TODO MODIFICARE E SEMPLIFICARE
        double usableWidth = board.boardAnchor.getWidth()* hResizeFactor;
        double usableHeight = board.boardAnchor.getHeight()* vResizeFactor;
        return min(usableHeight, usableWidth);
    }

    public static void resizeOuterRect(GameBoard board) {
        System.out.println("Sono dentro resizeOuterRect");
        System.out.println(board.boardAnchor);
        System.out.println("WIDTH " + board.boardAnchor.getWidth());
        System.out.println("HEIGHT " + board.boardAnchor.getHeight());
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        double size = getBoardSize(board);
        System.out.println(getBoardSize(board));
        board.outerRect.setLayoutX((board.boardAnchor.getWidth()/2)-(size*0.6));
        board.outerRect.setLayoutY((board.boardAnchor.getHeight()/2)-(size/2));
        board.outerRect.setWidth(size);
        board.outerRect.setHeight(size);

    }

    public static void resizeBoardRect(GameBoard board) {
        //  Ridimensiona il rettangolo interno in base alla dimensione di quello esterno
        board.boardRect.setWidth((board.outerRect.getWidth()*0.9));
        board.boardRect.setLayoutX(board.outerRect.getLayoutX()+(board.outerRect.getWidth()/2)-(board.boardRect.getWidth()/2));
        board.boardRect.setHeight((board.outerRect.getHeight()*0.9));
        board.boardRect.setLayoutY(board.outerRect.getLayoutY()+(board.outerRect.getHeight()/2)-(board.boardRect.getHeight()/2));
    }

    public static void resizeSeparator(GameBoard board) {
        //  Ridimensiona il separatore tra le due metà dell'area di gioco
        //  in funzione della sua effettiva dimensione
        board.separator.setWidth(board.boardRect.getWidth()/13);
        board.separator.setLayoutX(board.boardRect.getLayoutX() + ((6*(board.boardRect.getWidth()/13))));
        board.separator.setHeight(board.boardRect.getHeight()+2);
        board.separator.setLayoutY(board.boardRect.getLayoutY()-1);
    }
    public static void resizeLeftPoints(GameBoard board) {
        //  Ridimensiona le punte a sinistra del tabellone e relative regioni
        for (int i=0; i<6; i++) {

            board.regArrayTop[i].setLayoutX(board.boardRect.getLayoutX()+(i*(board.boardRect.getWidth()/13)));
            board.regArrayTop[i].setPrefWidth((board.boardRect.getWidth())/13);
            board.regArrayTop[i].setLayoutY(board.boardRect.getLayoutY());
            board.regArrayTop[i].setPrefHeight((board.boardRect.getHeight())*0.46);

            board.polArrayTop[i].setLayoutX(board.boardRect.getLayoutX()+(i*(board.boardRect.getWidth()/13)));
            board.polArrayTop[i].setLayoutY(board.boardRect.getLayoutY());
            board.polArrayTop[i].getPoints().setAll(0d, 0d,
                    (board.boardRect.getWidth()/13), 0d,
                    (board.boardRect.getWidth()/26), board.boardRect.getY() + board.regArrayTop[i].getPrefHeight() );

            board.regArrayBot[i].setLayoutX(board.boardRect.getLayoutX()+(i*(board.boardRect.getWidth()/13)));
            board.regArrayBot[i].setPrefWidth((board.boardRect.getWidth())/13);
            board.regArrayBot[i].setLayoutY(board.boardRect.getLayoutY() + board.boardRect.getHeight()*(1-0.46));
            board.regArrayBot[i].setPrefHeight((board.boardRect.getHeight())*0.46);


            board.polArrayBot[i].setLayoutX(board.boardRect.getLayoutX()+(i*(board.boardRect.getWidth()/13)));
            board.polArrayBot[i].setLayoutY(board.boardRect.getLayoutY() + board.boardRect.getHeight());
            board.polArrayBot[i].getPoints().setAll(0d, 0d,
                    (board.boardRect.getWidth()/13), 0d,
                    (board.boardRect.getWidth()/26), board.boardRect.getY()-board.regArrayTop[i].getPrefHeight() );

        }
    }

    public static void resizeRightPoints(GameBoard board) {
        //  Ridimensiona le punte a destra del tabellone e relative regioni
        for (int i=6; i<12; i++) {

            board.regArrayTop[i].setLayoutX(board.boardRect.getLayoutX()+((i+1)*(board.boardRect.getWidth()/13)));
            board.regArrayTop[i].setPrefWidth((board.boardRect.getWidth())/13);
            board.regArrayTop[i].setLayoutY(board.boardRect.getLayoutY());
            board.regArrayTop[i].setPrefHeight((board.boardRect.getHeight())*0.46);

            board.polArrayTop[i].setLayoutX(board.boardRect.getLayoutX()+((i+1)*(board.boardRect.getWidth()/13)));
            board.polArrayTop[i].setLayoutY(board.boardRect.getLayoutY());
            board.polArrayTop[i].getPoints().setAll(0d, 0d,
                    (board.boardRect.getWidth()/13), 0d,
                    (board.boardRect.getWidth()/26), board.boardRect.getY()+board.regArrayTop[i].getPrefHeight() );

            board.regArrayBot[i].setLayoutX(board.boardRect.getLayoutX()+((i+1)*(board.boardRect.getWidth()/13)));
            board.regArrayBot[i].setPrefWidth((board.boardRect.getWidth())/13);
            board.regArrayBot[i].setLayoutY(board.boardRect.getLayoutY() + board.boardRect.getHeight()*(1-0.46));
            board.regArrayBot[i].setPrefHeight((board.boardRect.getHeight())*0.46);


            board.polArrayBot[i].setLayoutX(board.boardRect.getLayoutX()+((i+1)*(board.boardRect.getWidth()/13)));
            board.polArrayBot[i].setLayoutY(board.boardRect.getLayoutY() + board.boardRect.getHeight());
            board.polArrayBot[i].getPoints().setAll(0d, 0d,
                    (board.boardRect.getWidth()/13), 0d,
                    (board.boardRect.getWidth()/26), board.boardRect.getY()-board.regArrayTop[i].getPrefHeight() );

        }
    }

    public static void resizeExitRegions(GameBoard board) {
        board.blackExitRegion.setHeight(board.outerRect.getHeight()/2);
        board.blackExitRegion.setLayoutY(board.outerRect.getLayoutY());
        board.whiteExitRegion.setHeight(board.outerRect.getHeight()/2);
        board.whiteExitRegion.setLayoutY(board.outerRect.getLayoutY() + (board.outerRect.getHeight()/2));

        if (blackExitCondition()) {
            board.blackExitRegion.setWidth(maxExitWidth);
            board.blackExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
        if (whiteExitCondition()) {
            board.whiteExitRegion.setWidth(maxExitWidth);
            board.whiteExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
    }

    protected static boolean blackExitCondition() {
        return true;
    }
    protected static boolean whiteExitCondition() {
        return true;
    }

    //TODO FORSE RISCRIVERE LE ROBE DELLE PEDINE
    public static void redrawPawns(GameBoard board) {

        int whitesPlaced = 0;
        int blacksPlaced = 0;

        blacksPlaced = redrawExitRegionPawns (blacksPlaced, board.pawnArrayBLK, board.blackExitRegion, COL_BLACK_EXIT, BLACK);
        whitesPlaced = redrawExitRegionPawns (whitesPlaced, board.pawnArrayWHT, board.whiteExitRegion, COL_WHITE_EXIT, WHITE);
        blacksPlaced = redrawAllPointsPawns (blacksPlaced, board.pawnArrayBLK, board.regArrayBot, board.regArrayTop, BLACK);
        whitesPlaced = redrawAllPointsPawns (whitesPlaced, board.pawnArrayWHT, board.regArrayBot, board.regArrayTop, WHITE);
    }

    protected static int redrawAllPointsPawns (int pawnsPlaced, PawnView[] pawnArray, Region[] regArrayBot, Region[] regArrayTop, int color) {
        for (int cols = COL_WHITE; cols <= LAST_COL_TOP && pawnsPlaced < PAWN_NUMBER_PER_PLAYER; cols++) {
            pawnsPlaced = redrawPointPawns (pawnsPlaced, pawnArray, regArrayTop, cols, true, color);
            //Il ciclo for chiama un redraw delle pedine di ogni singola punta in alto
        }
        for (int cols = FIRST_COL_BOT; cols <= COL_BLACK && pawnsPlaced < PAWN_NUMBER_PER_PLAYER; cols++) {
            pawnsPlaced = redrawPointPawns (pawnsPlaced, pawnArray, regArrayBot, cols, false, color);
            //Il ciclo for chiama un redraw delle pedine di ogni singola punta in basso
        }
        return pawnsPlaced;
    }

    private static int redrawPointPawns (int pawnsPlaced, PawnView[] pawnArray, Region[] regArray, int col, boolean top, int color) {

        boolean white;
        if (color==WHITE) {
            white = true;
        } else {
            white = false;
        }
        for (int rows = 0; logic.getBoardPlaceState(col, rows) != EMPTY && pawnsPlaced < PAWN_NUMBER_PER_PLAYER && rows <= 16; rows++) {
            if (logic.getBoardPlaceState(col, rows) == color) {
                if (top) {
                    pawnArray[pawnsPlaced].setLayoutX(regArray[col - 1].getLayoutX() + pawnArray[pawnsPlaced].getRadius());
                    pawnArray[pawnsPlaced].setLayoutY(regArray[col - 1].getLayoutY() + (min(5, rows) * 2 + 1) * pawnArray[pawnsPlaced].getRadius());
                } else {
                    pawnArray[pawnsPlaced].setLayoutX(regArray[COL_BLACK - col].getLayoutX() + pawnArray[pawnsPlaced].getRadius());
                    pawnArray[pawnsPlaced].setLayoutY(regArray[COL_BLACK - col].getLayoutY() + regArray[COL_BLACK - col].getPrefHeight() -
                            (min(5, rows) * 2 + 1) * pawnArray[pawnsPlaced].getRadius());
                }
                //  L'if controlla se la pedina appena posizionata è l'ultima della punta o meno
                //      In caso positivo la pedina viene visualizzata in cima a tutte le altre
                //      In caso negativo si riporta la priorità di rendering di default
                if (logic.isLastOnPoint(col, rows) && white == logic.getWhichTurn()) {
                    pawnArray[pawnsPlaced].setViewOrder(-1.0);
                    pawnArray[pawnsPlaced].setDisable(false);
                    if (logic.isPawnMovable(col, rows))
                        pawnArray[pawnsPlaced].setStrokeWidth(3.5);
                    else pawnArray[pawnsPlaced].setStrokeWidth(2);
                } else {
                    pawnArray[pawnsPlaced].setViewOrder(0.0);
                    pawnArray[pawnsPlaced].setDisable(true);
                    pawnArray[pawnsPlaced].setStrokeWidth(2);
                }
                pawnsPlaced++;
            }

        }
        return pawnsPlaced;
    }

    private static int redrawExitRegionPawns (int pawnsPlaced, PawnView[] pawnArray, Rectangle exitRegion, int exitID, int color) {
        for ( int row = 0; logic.getBoardPlaceState(exitID, row)!=EMPTY && pawnsPlaced< PAWN_NUMBER_PER_PLAYER && row<= 16; row++) {
            if (logic.getBoardPlaceState(exitID, row) == color) {
                pawnArray[pawnsPlaced].setLayoutX(exitRegion.getLayoutX() + ((pawnsPlaced % 3) * 2 + 1) * pawnArray[pawnsPlaced].getRadius());
                if (color == WHITE) {
                    pawnArray[pawnsPlaced].setLayoutY(exitRegion.getLayoutY() + exitRegion.getHeight() - ( (pawnsPlaced/3) * 2  + 1) * pawnArray[pawnsPlaced].getRadius());
                } else {
                    pawnArray[pawnsPlaced].setLayoutY(exitRegion.getLayoutY() + ( (pawnsPlaced/3) * 2  + 1) * pawnArray[pawnsPlaced].getRadius());
                }
                pawnArray[pawnsPlaced].setDisable(true);
                pawnsPlaced++;
            }
        }
        return pawnsPlaced;
    }


    protected static void resizePawns (BoardView board) {
        for (int i =0; i<board.pawnArrayWHT.length; i++){
            board.pawnArrayBLK[i].setRadius(board.regArrayTop[0].getPrefWidth() / 2);
            board.pawnArrayWHT[i].setRadius(board.regArrayTop[0].getPrefWidth() / 2);
        }
    }


}
