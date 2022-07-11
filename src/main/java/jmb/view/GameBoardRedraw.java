package jmb.view;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static jmb.ConstantsShared.*;

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
    protected static double xLayoutFactor = 0.6;
    protected static void setXLayoutFactor (double value) {xLayoutFactor = value;}
    protected static double yLayoutFactor = 0.5;
    protected static void setYLayoutFactor (double value) {yLayoutFactor = value;}


    // Metodo getter per maxExitWidth
    public static double getMaxExitWidth() { return maxExitWidth; }

    protected static void calcTrayWidths (GameBoard board) {
        maxExitWidth = board.pawnArrayWHT[0].getRadius() * 6 ;
    }


    protected static double getBoardSize (GameBoard board) {
        double usableWidth = board.boardAnchor.getWidth()* hResizeFactor;
        double usableHeight = board.boardAnchor.getHeight()* vResizeFactor;
        return min(usableHeight, usableWidth);
    }

    protected static double getBoardLayoutX (GameBoard board) {
        //return (board.boardAnchor.getWidth()/2)-(getBoardSize(board)*0.6); TODO VECCHIO
        return (board.boardAnchor.getWidth()/2) - (getBoardSize(board) * xLayoutFactor);
    }

    protected static double getBoardLayoutY (GameBoard board) {
        //return (board.boardAnchor.getHeight()/2)-(getBoardSize(board)/2); TODO VECCHIO
        return (board.boardAnchor.getHeight()/2) - (getBoardSize(board) * yLayoutFactor);
    }

    public static void resizeOuterRect(GameBoard board) {
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        double size = getBoardSize(board);
        board.outerRect.setLayoutX(getBoardLayoutX(board));
        board.outerRect.setLayoutY(getBoardLayoutY(board));
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
    }

    protected static void redrawPawns (GameBoard board, int[][] matrix) {
        int whitesPlaced = 0;
        int blacksPlaced = 0;
        for (int col = COL_BLACK_EXIT; col <= COL_WHITE_EXIT && (whitesPlaced < PAWNS_PER_PLAYER || blacksPlaced < PAWNS_PER_PLAYER); col++) {
            for (int row = 0; row < 16 && (whitesPlaced < PAWNS_PER_PLAYER || blacksPlaced < PAWNS_PER_PLAYER) && matrix[row][col] != EMPTY; row++) {
                if (matrix[row][col] == WHITE) {
                    placePawn(board, board.pawnArrayWHT, whitesPlaced, col, row);
                    whitesPlaced++;
                }
                if (matrix[row][col] == BLACK) {
                    placePawn(board, board.pawnArrayBLK, blacksPlaced, col, row);
                    blacksPlaced++;
                }
            }
        }
    }

    protected static void placePawn (GameBoard board, PawnView[] pawnArray, int pawnIndex, int col, int row) {
       if (col<=LAST_COL_TOP && col >=COL_WHITE) {
           pawnArray[pawnIndex].setLayoutX(board.regArrayTop[col - 1].getLayoutX() + pawnArray[pawnIndex].getRadius());
           pawnArray[pawnIndex].setLayoutY(board.regArrayTop[col - 1].getLayoutY() + (min(5, row) * 2 + 1) * pawnArray[pawnIndex].getRadius());
       } else if (col<=COL_BLACK && col >= FIRST_COL_BOT) {
           pawnArray[pawnIndex].setLayoutX(board.regArrayBot[COL_BLACK - col].getLayoutX() + pawnArray[pawnIndex].getRadius());
           pawnArray[pawnIndex].setLayoutY(board.regArrayBot[COL_BLACK - col].getLayoutY() + board.regArrayBot[COL_BLACK - col].getPrefHeight() -
                   (min(5, row) * 2 + 1) * pawnArray[pawnIndex].getRadius());
       } else if (col == COL_BLACK_EXIT) {
           pawnArray[pawnIndex].setLayoutX(board.blackExitRegion.getLayoutX() + ((row % 3) * 2 + 1) * pawnArray[pawnIndex].getRadius());
           pawnArray[pawnIndex].setLayoutY(board.blackExitRegion.getLayoutY() + ((row/3) * 2  + 1) * pawnArray[pawnIndex].getRadius());
       } else {
           System.out.println(pawnIndex);
           pawnArray[pawnIndex].setLayoutX(board.whiteExitRegion.getLayoutX() + ((row % 3) * 2 + 1) * pawnArray[pawnIndex].getRadius());
           pawnArray[pawnIndex].setLayoutY(board.whiteExitRegion.getLayoutY() + board.whiteExitRegion.getHeight() - ((row / 3) * 2 + 1) * pawnArray[pawnIndex].getRadius());
       }
       pawnArray[pawnIndex].setDisable(true);
    }


    protected static void resizePawns (GameBoard board) {
        for (int i =0; i<board.pawnArrayWHT.length; i++){
            board.pawnArrayBLK[i].setRadius(board.regArrayTop[0].getPrefWidth() / 2);
            board.pawnArrayWHT[i].setRadius(board.regArrayTop[0].getPrefWidth() / 2);
        }
    }

    public static void resizeInnerBoard(GameBoard board) {
        resizeBoardRect(board);
        resizeSeparator(board);
        resizeLeftPoints(board);
        resizeRightPoints(board);
        resizePawns(board);
        calcTrayWidths(board);
        resizeExitRegions(board);
    }


}
