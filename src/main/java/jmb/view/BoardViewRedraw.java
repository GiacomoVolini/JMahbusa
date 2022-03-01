package jmb.view;

import static java.lang.Math.min;
import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class BoardViewRedraw {

    //Valore di larghezza massima delle regioni di uscita e della zona dei dadi
    private static double maxExitWidth;
    private static double maxDTWidth;

    // Metodo getter per maxExitWidth
    public static double getMaxExitWidth() { return maxExitWidth; }

    // Metodo getter per maxDTWidth
    public static double getMaxDTWidth() { return maxDTWidth; }


    //public static void calcTrayWidth(Rectangle outerRect) {               VECCHIO
        //maxExitWidth = outerRect.getWidth() * EXTRA_REGION_FACTOR;        VECCHIO
        //maxDTWidth = outerRect.getWidth() * EXTRA_REGION_FACTOR;          VECCHIO
    private static void calcTrayWidth (PawnView pawn) {
        maxExitWidth = pawn.getRadius() * 6 ;
    }

    private static void calcDTWidth (PawnView pawn) {
        maxDTWidth = pawn.getRadius() * 6;
    }


    public static double getBoardSize (AnchorPane window) {
        double usableWidth = window.getWidth()*HORIZONTAL_RESIZE_FACTOR;
        double usableHeight = window.getHeight()*VERTICAL_RESIZE_FACTOR;
        return min(usableHeight, usableWidth);
    }

    public static double getMaxBtnWidth(AnchorPane window, Rectangle diceTray) {
        double maxBtnWidth = window.getWidth() - (diceTray.getLayoutX()+maxDTWidth+(BUTTON_ANCHOR*2));
        return min(MAX_BTN_WIDTH, maxBtnWidth);

    }

    public static void resizeOuterRect(AnchorPane window, Rectangle outerRect) {
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        outerRect.setWidth(getBoardSize(window));
        outerRect.setLayoutX((window.getWidth()/2)-(outerRect.getWidth()*0.6));
        outerRect.setHeight(getBoardSize(window));
        outerRect.setLayoutY((window.getHeight()/2)-(outerRect.getHeight()/2));
    }

    public static void resizeBoardRect(Rectangle outerRect, Rectangle boardRect) {
        //  Ridimensiona il rettangolo interno in base alla dimensione di quello esterno
        boardRect.setWidth((outerRect.getWidth()*0.9));
        boardRect.setLayoutX(outerRect.getLayoutX()+(outerRect.getWidth()/2)-(boardRect.getWidth()/2));
        boardRect.setHeight((outerRect.getHeight()*0.9));
        boardRect.setLayoutY(outerRect.getLayoutY()+(outerRect.getHeight()/2)-(boardRect.getHeight()/2));
    }

    public static void resizeSeparator(Rectangle separator, Rectangle boardRect) {
        //  Ridimensiona il separatore tra le due metà dell'area di gioco
        //  in funzione della sua effettiva dimensione
        separator.setWidth(boardRect.getWidth()/13);
        separator.setLayoutX(boardRect.getLayoutX() + ((6*(boardRect.getWidth()/13))));
        separator.setHeight(boardRect.getHeight()+2);
        separator.setLayoutY(boardRect.getLayoutY()-1);
    }

    public static void resizeTimer(Rectangle timerOut, Rectangle timerIn, Rectangle separator) {
        //  Ridimensiona le barre per il timer
        timerOut.setWidth(separator.getWidth()/2);
        timerOut.setLayoutX(separator.getLayoutX() + (separator.getWidth()/2) -(timerOut.getWidth()/2));
        timerOut.setHeight(separator.getHeight()-4);
        timerOut.setLayoutY(separator.getLayoutY() + (separator.getHeight()/2) -(timerOut.getHeight()/2));

        timerIn.setWidth(timerOut.getWidth()-4);
        timerIn.setLayoutX(timerOut.getLayoutX()+2);
        timerIn.setHeight(timerOut.getHeight()-4);
        timerIn.setLayoutY(timerOut.getLayoutY()+2);
    }

    public static void resizeLeftPoints(Polygon[] polArrayTop, Polygon[] polArrayBot,
                                        Region[] regArrayTop, Region[] regArrayBot,
                                        Rectangle boardRect) {
        //  Ridimensiona le punte a sinistra del tabellone e relative regioni
        for (int i=0; i<6; i++) {

            regArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            regArrayTop[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayTop[i].setLayoutY(boardRect.getLayoutY());
            regArrayTop[i].setPrefHeight((boardRect.getHeight())*0.46);

            polArrayTop[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            polArrayTop[i].setLayoutY(boardRect.getLayoutY());
            polArrayTop[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()+regArrayTop[i].getPrefHeight() );

            regArrayBot[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            regArrayBot[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight()*(1-0.46));
            regArrayBot[i].setPrefHeight((boardRect.getHeight())*0.46);


            polArrayBot[i].setLayoutX(boardRect.getLayoutX()+(i*(boardRect.getWidth()/13)));
            polArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight());
            polArrayBot[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()-regArrayTop[i].getPrefHeight() );

        }
    }

    public static void resizeRightPoints(Polygon[] polArrayTop, Polygon[] polArrayBot,
                                   Region[] regArrayTop, Region[] regArrayBot,
                                   Rectangle boardRect) {
        //  Ridimensiona le punte a destra del tabellone e relative regioni
        for (int i=6; i<12; i++) {

            regArrayTop[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            regArrayTop[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayTop[i].setLayoutY(boardRect.getLayoutY());
            regArrayTop[i].setPrefHeight((boardRect.getHeight())*0.46);

            polArrayTop[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            polArrayTop[i].setLayoutY(boardRect.getLayoutY());
            polArrayTop[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()+regArrayTop[i].getPrefHeight() );

            regArrayBot[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            regArrayBot[i].setPrefWidth((boardRect.getWidth())/13);
            regArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight()*(1-0.46));
            regArrayBot[i].setPrefHeight((boardRect.getHeight())*0.46);


            polArrayBot[i].setLayoutX(boardRect.getLayoutX()+((i+1)*(boardRect.getWidth()/13)));
            polArrayBot[i].setLayoutY(boardRect.getLayoutY() + boardRect.getHeight());
            polArrayBot[i].getPoints().setAll(0d, 0d,
                    (boardRect.getWidth()/13), 0d,
                    (boardRect.getWidth()/26), boardRect.getY()-regArrayTop[i].getPrefHeight() );

        }
    }

    public static void resizeButtons(Button backBTN, Button finishBTN, Button menuBTN, AnchorPane window, Rectangle diceTray) {

        //  Ridimensiona i Buttoni rispetto alla finestra principale
        //  Larghezza
        backBTN.setMaxWidth(getMaxBtnWidth(window, diceTray));
        finishBTN.setMaxWidth(getMaxBtnWidth(window, diceTray));
        menuBTN.setMaxWidth(getMaxBtnWidth(window, diceTray));
        backBTN.setPrefWidth(window.getWidth()*0.15);
        finishBTN.setPrefWidth(backBTN.getPrefWidth());
        menuBTN.setPrefWidth(backBTN.getPrefWidth());
        // Altezza
        backBTN.setMaxHeight(MAX_BTN_HEIGHT);
        finishBTN.setMaxHeight(MAX_BTN_HEIGHT);
        menuBTN.setMaxHeight(MAX_BTN_HEIGHT);
        backBTN.setPrefHeight(window.getHeight()*0.2);
        finishBTN.setPrefHeight(backBTN.getPrefHeight());
        menuBTN.setPrefHeight(backBTN.getPrefHeight());
        backBTN.setLayoutY(window.getHeight()*.25 - backBTN.getPrefHeight()/2);
        finishBTN.setLayoutY((window.getHeight() - finishBTN.getPrefHeight())/2);
        menuBTN.setLayoutY(window.getHeight()*.75 - menuBTN.getPrefHeight()/2);

    }

    public static void resizeExitRegions(boolean bExit, boolean wExit, Rectangle whiteExitRegion,
                                   Rectangle blackExitRegion, Rectangle outerRect) {
        blackExitRegion.setHeight(outerRect.getHeight()/2);
        blackExitRegion.setLayoutY(outerRect.getLayoutY());
        whiteExitRegion.setHeight(outerRect.getHeight()/2);
        whiteExitRegion.setLayoutY(outerRect.getLayoutY() + (outerRect.getHeight()/2));

        if (bExit) {
            blackExitRegion.setWidth(maxExitWidth);
            blackExitRegion.setLayoutX(outerRect.getLayoutX() - maxExitWidth);
        }
        if (wExit) {
            whiteExitRegion.setWidth(maxExitWidth);
            whiteExitRegion.setLayoutX(outerRect.getLayoutX() - maxExitWidth);
        }
    }

    public static void resizeDiceTray(boolean dtAnimDone, Rectangle diceTray, Rectangle outerRect) {
        diceTray.setLayoutX(outerRect.getLayoutX() + outerRect.getWidth());
        diceTray.setLayoutY(outerRect.getLayoutY());
        if (dtAnimDone) {
            diceTray.setWidth(maxDTWidth);
        }
        diceTray.setHeight(outerRect.getHeight());

    }

    //  Metodo per ridimensionamento e riposizionamento Pedine
    public static void redrawPawns(PawnView[] pawnArrayWHT, PawnView[] pawnArrayBLK, Region[] regArrayBot , Region[] regArrayTop,
                                   Rectangle whiteExitRegion, Rectangle blackExitRegion) {

        int whitesPlaced = 0;
        int blacksPlaced = 0;

        blacksPlaced = redrawExitRegionPawns (blacksPlaced, pawnArrayBLK, blackExitRegion, COL_BLACK_EXIT, BLACK);
        whitesPlaced = redrawExitRegionPawns (whitesPlaced, pawnArrayWHT, whiteExitRegion, COL_WHITE_EXIT, WHITE);
        blacksPlaced = redrawAllPointsPawns (blacksPlaced, pawnArrayBLK, regArrayBot, regArrayTop, BLACK);
        whitesPlaced = redrawAllPointsPawns (whitesPlaced, pawnArrayWHT, regArrayBot, regArrayTop, WHITE);





    }

    public static int redrawAllPointsPawns (int pawnsPlaced, PawnView[] pawnArray, Region[] regArrayBot, Region[] regArrayTop, int color) {
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

    public static int redrawPointPawns (int pawnsPlaced, PawnView[] pawnArray, Region[] regArray, int col, boolean top, int color) {

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
                if (logic.isLastOnPoint(col, rows)) {
                    pawnArray[pawnsPlaced].setViewOrder(-1.0);
                    pawnArray[pawnsPlaced].setDisable(false);
                } else {
                    pawnArray[pawnsPlaced].setViewOrder(0.0);
                    pawnArray[pawnsPlaced].setDisable(true);
                }
                pawnsPlaced++;
            }

        }
        return pawnsPlaced;
    }

    public static int redrawExitRegionPawns (int pawnsPlaced, PawnView[] pawnArray, Rectangle exitRegion, int exitID, int color) {
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


    private static void resizePawns (PawnView[] pawnArrayWHT, PawnView[] pawnArrayBLK, Region[] regArraySample) {
        for (int i =0; i<pawnArrayWHT.length; i++){
            pawnArrayBLK[i].setRadius(regArraySample[0].getPrefWidth() / 2);
            pawnArrayWHT[i].setRadius(regArraySample[0].getPrefWidth() / 2);
        }
    }

    // Metodo per rimposizionamento dinamico della pagina Pausa
    private static void resizePaginaPauseIniziamo(AnchorPane window, TitledPane Pause, TitledPane Iniziamo){
        Pause.setLayoutX(window.getWidth()/2-Pause.getWidth()/2);
        Pause.setLayoutY(window.getHeight()/2-Pause.getHeight()/2);
        Iniziamo.setLayoutX(window.getWidth()/2-Iniziamo.getPrefWidth()/2);
        Iniziamo.setLayoutY(window.getHeight()/2-Iniziamo.getPrefHeight()/2);

    }

    //TODO Metodo che gestisca il riposizionamento dei dadi (ImageView)

    protected static void resizeAll(AnchorPane window, Rectangle outerRect, Rectangle boardRect,
                                    Rectangle separator, Rectangle timerOut, Rectangle timerIn,
                                    Polygon[] polArrayTop, Polygon[] polArrayBot, Region[] regArrayTop,
                                    Region[] regArrayBot, boolean bExit, boolean wExit,
                                    Rectangle whiteExitRegion, Rectangle blackExitRegion,
                                    boolean dtAnimDone, Rectangle diceTray, Button backBTN, Button finishBTN,
                                    Button menuBTN, PawnView[] pawnArrayWHT, PawnView[] pawnArrayBLK,
                                    TitledPane Pause, TitledPane Iniziamo) {

        resizeOuterRect(window, outerRect);
        resizeBoardRect(outerRect, boardRect);
        resizeSeparator(separator, boardRect);
        resizeTimer(timerOut, timerIn, separator);
        resizeLeftPoints(polArrayTop, polArrayBot, regArrayTop, regArrayBot, boardRect);
        resizeRightPoints(polArrayTop, polArrayBot, regArrayTop, regArrayBot, boardRect);
        resizePawns(pawnArrayWHT, pawnArrayBLK, regArrayBot);
        calcTrayWidth(pawnArrayBLK[0]);
        calcDTWidth(pawnArrayBLK[0]);
        resizeDiceTray(dtAnimDone, diceTray, outerRect);
        resizeButtons(backBTN, finishBTN, menuBTN, window, diceTray);
        resizeExitRegions(bExit, wExit, whiteExitRegion, blackExitRegion, outerRect);
        redrawPawns(pawnArrayWHT, pawnArrayBLK, regArrayBot, regArrayTop, whiteExitRegion, blackExitRegion);
        resizePaginaPauseIniziamo(window, Pause, Iniziamo);
        //TODO metodo resize Dadi
    }

}
