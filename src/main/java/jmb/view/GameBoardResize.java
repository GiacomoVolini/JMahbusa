package jmb.view;

import static jmb.view.ConstantsView.*;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class GameBoardResize {

    //Valore di larghezza massima delle regioni di uscita e della zona dei dadi
    private static double maxExitWidth;
    private static double maxDTWidth;

    // Metodo getter per maxExitWidth
    public static double getMaxExitWidth() { return maxExitWidth; }

    // Metodo getter per maxDTWidth
    public static double getMaxDTWidth() { return maxDTWidth; }


    public static void calcTrayWidth(Rectangle outerRect) {
        maxExitWidth = outerRect.getWidth() * EXTRA_REGION_FACTOR;
        maxDTWidth = outerRect.getWidth() * EXTRA_REGION_FACTOR;
    }


    public static double getBoardSize (AnchorPane window) {
        double usableWidth = window.getWidth()*HORIZONTAL_RESIZE_FACTOR;
        double usableHeight = window.getHeight()*VERTICAL_RESIZE_FACTOR;
        return Math.min(usableHeight, usableWidth);
    }

    public static double getMaxBtnWidth(AnchorPane window, Rectangle diceTray) {
        double maxBtnWidth = window.getWidth() - (diceTray.getLayoutX()+maxDTWidth+(BUTTON_ANCHOR*2));
        return Math.min(MAX_BTN_WIDTH, maxBtnWidth);

    }

    public static void resizeOuterRect(AnchorPane window, Rectangle outerRect) {
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        outerRect.setWidth(getBoardSize(window));
        outerRect.setLayoutX((window.getWidth()/2)-(outerRect.getWidth()/2));
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
                                  LogicPoints[] regArrayTop, LogicPoints[] regArrayBot,
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
                                   LogicPoints[] regArrayTop, LogicPoints[] regArrayBot,
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
        whiteExitRegion.setHeight(outerRect.getHeight()/2);
        whiteExitRegion.setLayoutY(outerRect.getLayoutY());
        blackExitRegion.setHeight(outerRect.getHeight()/2);
        blackExitRegion.setLayoutY(outerRect.getLayoutY() + (outerRect.getHeight()/2));

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
    public static void resizePawns(PawnV[] pawnArrayWHT, PawnV[] pawnArrayBLK, LogicPoints[] regArrayBot , LogicPoints[] regArrayTop,
                                   Rectangle whiteExitRegion, Rectangle blackExitRegion) {

        for (PawnV pawn : pawnArrayWHT) {
            pawn.setRadius(regArrayBot[0].getPrefWidth() / 2);
            switch (pawn.getPlace()) {
                case TOP_POINTS:
                    mvPawnOnTop(pawn, regArrayTop);
                    break;
                case BOT_POINTS:
                    mvPawnOnBot(pawn, regArrayBot);
                    break;
                case WHITE_EXIT_REGION:
                    mvPawnOnWHTExit(pawn, whiteExitRegion);
                    break;
            }
        }

        for (PawnV pawn : pawnArrayBLK) {
            pawn.setRadius(regArrayBot[0].getPrefWidth() / 2);
            switch (pawn.getPlace()) {
                case TOP_POINTS:
                    mvPawnOnTop(pawn, regArrayTop);
                    break;
                case BOT_POINTS:
                    mvPawnOnBot(pawn, regArrayBot);
                    break;
                case BLACK_EXIT_REGION:
                    mvPawnOnBLKExit(pawn, whiteExitRegion);
                    break;
            }
        }

    }

    //  Metodo per riposizionamento dinamico delle pedine nelle Punte superiori
    private static void mvPawnOnTop( PawnV pawn, LogicPoints[] regArrayTop) {
        int pointNmb = pawn.getWhichPoint();
        pawn.setLayoutX(regArrayTop[pointNmb].getLayoutX() + pawn.getRadius());
        if (pawn.getHowManyBelow() < 5) {
            pawn.setLayoutY(regArrayTop[pointNmb].getLayoutY() + pawn.getRadius() * (1 + pawn.getHowManyBelow() * 2));}
        else {
            pawn.setLayoutY(regArrayTop[pointNmb].getLayoutY() + pawn.getRadius() * 9);
        }
    }

    //  Metodo per riposizionamento dinamico delle pedine nelle Punte inferiori
    private static void mvPawnOnBot(PawnV pawn, LogicPoints[] regArrayBot) {
        int pointNmb = pawn.getWhichPoint();
        pawn.setLayoutX(regArrayBot[pointNmb].getLayoutX() + pawn.getRadius());

        if (pawn.getHowManyBelow() < 5) {
            pawn.setLayoutY(regArrayBot[pointNmb].getLayoutY() + regArrayBot[pointNmb].getPrefHeight() - pawn.getRadius() * (1 + pawn.getHowManyBelow() * 2));}
        else {
            pawn.setLayoutY(regArrayBot[pointNmb].getLayoutY() + regArrayBot[pointNmb].getPrefHeight() - pawn.getRadius() * 9);
        }
    }


    //  Metodo per riposizionamento dinamico delle pedine nella Regione di Uscita del Bianco
    private static void mvPawnOnWHTExit(PawnV pawn, Rectangle whiteExitRegion) {
        pawn.setLayoutX(whiteExitRegion.getLayoutX() + pawn.getRadius());
        pawn.setLayoutY(whiteExitRegion.getLayoutY() + pawn.getRadius());
    }

    //  Metodo per riposizionamento dinamico delle pedine nella Regione di Uscita del Nero
    private static void mvPawnOnBLKExit(PawnV pawn, Rectangle blackExitRegion) {
        pawn.setLayoutX(blackExitRegion.getLayoutX() + pawn.getRadius());
        pawn.setLayoutY(blackExitRegion.getLayoutY() + blackExitRegion.getHeight() - pawn.getRadius());
    }

    protected static void resizeAll(AnchorPane window, Rectangle outerRect, Rectangle boardRect,
                                    Rectangle separator, Rectangle timerOut, Rectangle timerIn,
                                    Polygon[] polArrayTop, Polygon[] polArrayBot, LogicPoints[] regArrayTop,
                                    LogicPoints[] regArrayBot, boolean bExit, boolean wExit,
                                    Rectangle whiteExitRegion, Rectangle blackExitRegion,
                                    boolean dtAnimDone, Rectangle diceTray, Button backBTN, Button finishBTN,
                                    Button menuBTN, PawnV[] pawnArrayWHT, PawnV[] pawnArrayBLK) {

        resizeOuterRect(window, outerRect);
        resizeBoardRect(outerRect, boardRect);
        resizeSeparator(separator, boardRect);
        resizeTimer(timerOut, timerIn, separator);
        resizeLeftPoints(polArrayTop, polArrayBot, regArrayTop, regArrayBot, boardRect);
        resizeRightPoints(polArrayTop, polArrayBot, regArrayTop, regArrayBot, boardRect);
        calcTrayWidth(outerRect);
        resizeExitRegions(bExit, wExit, whiteExitRegion, blackExitRegion, outerRect);
        resizeDiceTray(dtAnimDone, diceTray, outerRect);
        resizeButtons(backBTN, finishBTN, menuBTN, window, diceTray);
        resizePawns(pawnArrayWHT, pawnArrayBLK, regArrayBot, regArrayTop, whiteExitRegion, blackExitRegion);
    }


}
