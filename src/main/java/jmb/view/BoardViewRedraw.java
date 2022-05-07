package jmb.view;

import static java.lang.Math.*;
import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    private static void calcTrayWidths (BoardView board) {
        maxExitWidth = maxDTWidth = board.pawnArrayWHT[0].getRadius() * 6 ;
    }


    private static double getMaxBtnWidth(AnchorPane window, Rectangle outerRect) {
        double maxBtnWidth = window.getWidth() - (outerRect.getLayoutX() + outerRect.getWidth() +maxDTWidth+(BUTTON_ANCHOR*2));
        return min(MAX_BTN_WIDTH, maxBtnWidth);

    }



    /*System.out.println(" dist = " + dist);
        System.out.println("calc = " + calc);
        System.out.println("plRectFactor = " + plRectFactor);

                VA IN GETBOARDSIZE         */


    private static double getBoardSize (BoardView board) {

        double usableWidth = board.window.getWidth()*HORIZONTAL_RESIZE_FACTOR;
        double usableHeight = board.window.getHeight()*VERTICAL_RESIZE_FACTOR;


        double proposedSize = min (usableWidth, usableHeight);

        double proposedX = board.window.getWidth()/2 - proposedSize * 0.6;

        double dist = proposedX - (board.plWHTOutRect.getLayoutX() + board.plWHTOutRect.getWidth() - 10) ;
        double calc = board.plWHTOutRect.getHeight() * 1.8 - dist;
        double sizeCorrection = max( 0, min(board.plWHTOutRect.getHeight() * 1.8, calc));



        return min(usableHeight - sizeCorrection, usableWidth);

    }
    public static void resizeOuterRect(BoardView board) {
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        double size = getBoardSize(board);
        board.outerRect.setLayoutX((board.window.getWidth()/2)-(size*0.6));
        board.outerRect.setLayoutY((board.window.getHeight()/2)-(size/2));
        board.outerRect.setWidth(size);
        board.outerRect.setHeight(size);

    }

    public static void resizeBoardRect(BoardView board) {
        //  Ridimensiona il rettangolo interno in base alla dimensione di quello esterno
        board.boardRect.setWidth((board.outerRect.getWidth()*0.9));
        board.boardRect.setLayoutX(board.outerRect.getLayoutX()+(board.outerRect.getWidth()/2)-(board.boardRect.getWidth()/2));
        board.boardRect.setHeight((board.outerRect.getHeight()*0.9));
        board.boardRect.setLayoutY(board.outerRect.getLayoutY()+(board.outerRect.getHeight()/2)-(board.boardRect.getHeight()/2));
    }

    public static void resizeSeparator(BoardView board) {
        //  Ridimensiona il separatore tra le due metà dell'area di gioco
        //  in funzione della sua effettiva dimensione
        board.separator.setWidth(board.boardRect.getWidth()/13);
        board.separator.setLayoutX(board.boardRect.getLayoutX() + ((6*(board.boardRect.getWidth()/13))));
        board.separator.setHeight(board.boardRect.getHeight()+2);
        board.separator.setLayoutY(board.boardRect.getLayoutY()-1);
    }

    public static void resizeTimer(BoardView board) {
        //  Ridimensiona le barre per il timer
        board.timerOut.setWidth(board.separator.getWidth()/2);
        board.timerOut.setLayoutX(board.separator.getLayoutX() + (board.separator.getWidth()/2) -(board.timerOut.getWidth()/2));
        board.timerOut.setHeight(board.separator.getHeight()-4);
        board.timerOut.setLayoutY(board.separator.getLayoutY() + (board.separator.getHeight()/2) -(board.timerOut.getHeight()/2));

        board.timerIn.setWidth(board.timerOut.getWidth()-4);
        board.timerIn.setLayoutX(board.timerOut.getLayoutX()+2);
        board.timerIn.setHeight(board.timerOut.getHeight()-4);
        board.timerIn.setLayoutY(board.timerOut.getLayoutY()+2);
    }

    public static void resizeLeftPoints(BoardView board) {
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

    public static void resizeRightPoints(BoardView board) {
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


    public static void resizeDice (BoardView board) {
        if (!board.diceArray[0].isVisible()) {
            for (ImageView dice : board.diceArray) {
                dice.setVisible(true);
            }
        }
        for (ImageView dice : board.diceArray) {
            dice.setFitHeight(board.diceTray.getWidth()*0.425);
            dice.setFitWidth(board.diceTray.getWidth()*0.425);
        }
        double diceX = board.diceTray.getLayoutX() + board.diceTray.getWidth()/2 - board.diceArray[0].getFitWidth()/2;
        for (ImageView dice : board.diceArray){
            dice.setLayoutX(diceX);
        }
        board.diceArray[0].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight()*0.15);
        board.diceArray[2].setLayoutY(board.diceArray[0].getLayoutY() + board.diceArray[0].getFitHeight());
        board.diceArray[1].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight()*0.85 - board.diceArray[3].getFitHeight());
        board.diceArray[3].setLayoutY(board.diceArray[1].getLayoutY() - board.diceArray[1].getFitHeight());

    }

    public static void resizeButtons(BoardView board) {

        //  Ridimensiona i Buttoni rispetto alla finestra principale
        //  Larghezza
        board.backBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.finishBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.menuBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.backBTN.setPrefWidth(board.window.getWidth()*0.15);
        board.finishBTN.setPrefWidth(board.backBTN.getPrefWidth());
        board.menuBTN.setPrefWidth(board.backBTN.getPrefWidth());
        // Altezza
        board.backBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.finishBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.menuBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.backBTN.setPrefHeight(board.window.getHeight()*0.2);
        board.finishBTN.setPrefHeight(board.backBTN.getPrefHeight());
        board.menuBTN.setPrefHeight(board.backBTN.getPrefHeight());
        board.backBTN.setLayoutY(board.window.getHeight()*.25 - board.backBTN.getPrefHeight()/2);
        board.finishBTN.setLayoutY((board.window.getHeight() - board.finishBTN.getPrefHeight())/2);
        board.menuBTN.setLayoutY(board.window.getHeight()*.75 - board.menuBTN.getPrefHeight()/2);

    }

    public static void resizeExitRegions(BoardView board) {
        board.blackExitRegion.setHeight(board.outerRect.getHeight()/2);
        board.blackExitRegion.setLayoutY(board.outerRect.getLayoutY());
        board.whiteExitRegion.setHeight(board.outerRect.getHeight()/2);
        board.whiteExitRegion.setLayoutY(board.outerRect.getLayoutY() + (board.outerRect.getHeight()/2));

        if (logic.getBlackExit()) {
            board.blackExitRegion.setWidth(maxExitWidth);
            board.blackExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
        if (logic.getWhiteExit()) {
            board.whiteExitRegion.setWidth(maxExitWidth);
            board.whiteExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
    }

    public static void resizeDiceTray(BoardView board) {
        board.diceTray.setLayoutX(board.outerRect.getLayoutX() + board.outerRect.getWidth());
        board.diceTray.setLayoutY(board.outerRect.getLayoutY());
        if (board.dtAnimDone) {
            board.diceTray.setWidth(maxDTWidth);
        }
        board.diceTray.setHeight(board.outerRect.getHeight());

    }

    //  Metodo per ridimensionamento e riposizionamento Pedine
    public static void redrawPawns(BoardView board) {

        int whitesPlaced = 0;
        int blacksPlaced = 0;

        blacksPlaced = redrawExitRegionPawns (blacksPlaced, board.pawnArrayBLK, board.blackExitRegion, COL_BLACK_EXIT, BLACK);
        whitesPlaced = redrawExitRegionPawns (whitesPlaced, board.pawnArrayWHT, board.whiteExitRegion, COL_WHITE_EXIT, WHITE);
        blacksPlaced = redrawAllPointsPawns (blacksPlaced, board.pawnArrayBLK, board.regArrayBot, board.regArrayTop, BLACK);
        whitesPlaced = redrawAllPointsPawns (whitesPlaced, board.pawnArrayWHT, board.regArrayBot, board.regArrayTop, WHITE);
    }

    private static int redrawAllPointsPawns (int pawnsPlaced, PawnView[] pawnArray, Region[] regArrayBot, Region[] regArrayTop, int color) {
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
                } else {
                    pawnArray[pawnsPlaced].setViewOrder(0.0);
                    pawnArray[pawnsPlaced].setDisable(true);
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


    private static void resizePawns (BoardView board) {
        for (int i =0; i<board.pawnArrayWHT.length; i++){
            board.pawnArrayBLK[i].setRadius(board.regArrayTop[0].getPrefWidth() / 2);
            board.pawnArrayWHT[i].setRadius(board.regArrayTop[0].getPrefWidth() / 2);
        }
    }

    // Metodo per rimposizionamento dinamico della pagina Pausa
    protected static void resizePauseMenu(BoardView board){
        board.pauseMenu.setLayoutX(board.window.getWidth()/2-board.pauseMenu.getWidth()/2);
        board.pauseMenu.setLayoutY(board.window.getHeight()/2-board.pauseMenu.getHeight()/2);
    }

    private static void resizeVictoryRect (BoardView board) {
        board.victoryPanel.setWidth(board.window.getWidth() / 2);
        board.victoryPanel.setHeight(board.window.getHeight()/2.5);
        board.victoryPanel.setLayoutX((board.window.getWidth() - board.victoryPanel.getWidth()) / 2);
        board.victoryPanel.setLayoutY((board.window.getHeight() - board.victoryPanel.getHeight()) / 2);
    }

    private static void resizeVictoryPawn (BoardView board) {
        board.victoryPawn.setRadius(board.victoryPanel.getHeight() / 10);
        board.victoryPawn.setLayoutX(board.victoryPanel.getLayoutX() + board.victoryPanel.getWidth() * 0.05 + board.victoryPawn.getRadius());
        board.victoryPawn.setLayoutY(board.victoryPanel.getLayoutY() + board.victoryPanel.getHeight() / 2);
    }

    private static void resizeVictoryExit (BoardView board) {
        board.victoryExit.setPrefWidth(board.victoryPanel.getWidth() * 0.4);
        board.victoryExit.setPrefHeight(board.victoryPanel.getHeight() * 0.15);
        board.victoryExit.setLayoutY(board.victoryPanel.getLayoutY() + (0.66 * board.victoryPanel.getHeight()));
        board.victoryExit.setLayoutX(board.victoryPanel.getLayoutX() + (board.victoryPanel.getWidth() - (board.victoryPanel.getWidth() * 0.3)) / 2);
    }

    private static void resizeVictoryCrown (BoardView board) {
        board.victoryCrown.setFitWidth(board.victoryPawn.getRadius()*2);
        board.victoryCrown.setLayoutY(board.victoryPawn.getLayoutY() - board.victoryPawn.getRadius()*2.2);
        board.victoryCrown.setLayoutX(board.victoryPawn.getLayoutX() - board.victoryPawn.getRadius());
    }


    private static void resizeVictoryLabel (BoardView board) {
        board.victoryLabel.setLayoutY(board.victoryPanel.getLayoutY() + board.victoryPanel.getHeight() * 0.15);
        board.victoryLabel.setLayoutX(board.victoryPanel.getLayoutX() + board.victoryPanel.getWidth() * 0.25);
        resizeVictoryFont(board.victoryPanel, board.victoryLabel);
    }

    //  Il metodo resizeVictoryFont si occupa di stimare una grandezza del font di victoryLabel tale da non fuoriuscire
    //      da victoryPanel e mantenere dei margini simili
    //  Da un controllo empirico con SceneBuilder, preso un rettangolo di larghezza 400, una label con Font size 20
    //      ed il testo "Hai ottenuto una vittoria doppia!" rimane entro margini accettabili
    //  Il metodo quindi calcola la proporzione tra la larghezza di victoryPanel e 400, e trova il nuovo valore di grandezza
    //      del Font da assegnare a victoryLabel
    private static void resizeVictoryFont(Rectangle victoryPanel, Label victoryLabel) {
        double widthFactor = victoryPanel.getWidth()/400;
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 20 * widthFactor));
    }

    private static void resizeTournamentRibbon(BoardView board) {
        board.tourmanentRibbon.setLayoutX(board.victoryPawn.getLayoutX() + board.victoryPawn.getRadius()*0.10);
        board.tourmanentRibbon.setLayoutY(board.victoryPawn.getLayoutY() - board.victoryPawn.getRadius()*0.20);
        board.tourmanentRibbon.setFitWidth(board.victoryPawn.getRadius());
    }




    //  Metodo per ridimensionare gli elementi del pannello vittoria
    protected static void resizeVictoryPanel (BoardView board)
    {
        resizeVictoryRect(board);
        resizeVictoryPawn(board);
        resizeVictoryLabel(board);
        resizeVictoryExit(board);
        resizeVictoryCrown(board);
        if (board.tourmanentRibbon!=null)
            resizeTournamentRibbon(board);

    }

    private static void resizeStartDialogue (BoardView board) {
        board.startDialogue.setLayoutX(board.window.getWidth()/2-board.startDialogue.getPrefWidth()/2);
        board.startDialogue.setLayoutY(board.window.getHeight()/2-board.startDialogue.getPrefHeight()/2);
    }



    private static void resizePlsPawns(BoardView board) {

        board.plWHTPawn.setRadius(board.window.getHeight() * 0.038);
        board.plBLKPawn.setRadius(board.window.getHeight() * 0.038);
        AnchorPane.setLeftAnchor(board.plWHTPawn, board.window.getWidth() * 0.0125);
        AnchorPane.setRightAnchor(board.plBLKPawn, board.window.getWidth() * 0.0125);
        AnchorPane.setTopAnchor(board.plWHTPawn, board.window.getHeight() * 0.0275);
        AnchorPane.setTopAnchor(board.plBLKPawn, board.window.getHeight() * 0.0275);

    }

    private static void resizePlsRects(BoardView board) {

        resizePlsOutRects(board);
        resizePlsInRects(board);
        resizePlsNames(board);
        resizePlsFont(board);

    }

    private static void resizePlsOutRects (BoardView board) {
        AnchorPane.setTopAnchor(board.plWHTOutRect, board.window.getHeight() * 0.0275);
        AnchorPane.setTopAnchor(board.plBLKOutRect, board.window.getHeight() * 0.0275);
        AnchorPane.setLeftAnchor(board.plWHTOutRect, board.window.getWidth() * 0.025 + board.plWHTPawn.getRadius()*2);
        AnchorPane.setRightAnchor(board.plBLKOutRect, board.window.getWidth() * 0.025 + board.plBLKPawn.getRadius()*2);
        board.plWHTOutRect.setHeight(board.plWHTPawn.getRadius()*2);
        board.plBLKOutRect.setHeight(board.plBLKPawn.getRadius()*2);
        board.plWHTOutRect.setWidth(board.window.getWidth()*0.18);
        board.plBLKOutRect.setWidth(board.window.getWidth()*0.18);
    }

    private static void resizePlsInRects(BoardView board) {
        board.plWHTInRect.setWidth(board.window.getWidth()*0.15);
        board.plWHTInRect.setHeight(board.plWHTPawn.getRadius()*1.5);
        board.plBLKInRect.setWidth(board.window.getWidth()*0.15);
        board.plBLKInRect.setHeight(board.plBLKPawn.getRadius()*1.5);
        AnchorPane.setTopAnchor(board.plBLKInRect,
                AnchorPane.getTopAnchor(board.plBLKOutRect) + board.plBLKOutRect.getHeight()/2 - board.plBLKInRect.getHeight()/2);
        AnchorPane.setTopAnchor(board.plWHTInRect,
                AnchorPane.getTopAnchor(board.plWHTOutRect) + board.plWHTOutRect.getHeight()/2 - board.plWHTInRect.getHeight()/2);
        AnchorPane.setLeftAnchor(board.plWHTInRect,
                AnchorPane.getLeftAnchor(board.plWHTOutRect) + board.plWHTOutRect.getWidth()/2 - board.plWHTInRect.getWidth()/2);
        AnchorPane.setRightAnchor(board.plBLKInRect,
                AnchorPane.getRightAnchor(board.plBLKOutRect) + board.plBLKOutRect.getWidth()/2 - board.plBLKInRect.getWidth()/2);
    }

    private static void resizePlsNames(BoardView board) {
        AnchorPane.setTopAnchor(board.plWHTText, AnchorPane.getTopAnchor(board.plWHTInRect));
        AnchorPane.setTopAnchor(board.plBLKText, AnchorPane.getTopAnchor(board.plBLKInRect));
        AnchorPane.setLeftAnchor(board.plWHTText, AnchorPane.getLeftAnchor(board.plWHTInRect));
        AnchorPane.setRightAnchor(board.plBLKText, AnchorPane.getRightAnchor(board.plBLKInRect));
        board.plWHTText.setPrefWidth(board.plWHTInRect.getWidth());
        board.plBLKText.setPrefWidth(board.plBLKInRect.getWidth());
        board.plWHTText.setPrefHeight(board.plWHTInRect.getHeight());
        board.plBLKText.setPrefHeight(board.plBLKInRect.getHeight());
    }

    private static void resizePlsFont(BoardView board) {
        double widthFactor = board.plWHTInRect.getWidth()/110;
        double fontSize = max(13.5, min(25,(12 * widthFactor)));
        board.plWHTText.setFont(Font.font("calibri", FontWeight.NORMAL, fontSize));
        board.plBLKText.setFont(Font.font("calibri", FontWeight.NORMAL, fontSize));

    }

    protected static void resizeTournamentLabel(BoardView board) {
        AnchorPane.setTopAnchor(board.tournamentWhitePoints, board.window.getHeight() * 0.0275);
        AnchorPane.setTopAnchor(board.tournamentBlackPoints, board.window.getHeight() * 0.0275);
        AnchorPane.setLeftAnchor(board.tournamentWhitePoints, board.window.getWidth() * 0.0125);
        AnchorPane.setRightAnchor(board.tournamentBlackPoints, board.window.getWidth() * 0.0125);
        board.tournamentWhitePoints.setPrefWidth(board.plWHTPawn.getRadius()*2);
        board.tournamentWhitePoints.setPrefHeight(board.plWHTPawn.getRadius()*2);
        board.tournamentBlackPoints.setPrefWidth(board.plBLKPawn.getRadius()*2);
        board.tournamentBlackPoints.setPrefHeight(board.plBLKPawn.getRadius()*2);
        double fontFactor = board.plWHTPawn.getRadius()/25;
        board.tournamentWhitePoints.setFont(Font.font("calibri", FontWeight.BOLD, 20*fontFactor));
        board.tournamentBlackPoints.setFont(Font.font("calibri", FontWeight.BOLD, 20*fontFactor));


    }



    protected static void resizeAll(BoardView board) {

        //TODO Inserire dove necessario controlli su font size
        resizePlsPawns(board);
        resizePlsRects(board);
        resizeOuterRect(board);
        resizeBoardRect(board);
        resizeSeparator(board);
        resizeTimer(board);
        resizeLeftPoints(board);
        resizeRightPoints(board);
        resizePawns(board);
        calcTrayWidths(board);
        if (board.gameStart) {
            resizeDiceTray(board);
            if (board.dtAnimDone)
                resizeDice(board); }
        else
            resizeStartDialogue(board);
        resizeButtons(board);
        resizeExitRegions(board);
        redrawPawns(board);
        resizePauseMenu(board);
        if (logic.isTournamentOngoing())
            resizeTournamentLabel(board);
        if (board.gameEndState) {
            resizeVictoryPanel(board);
        }




    }

/*TODO TOGLIERE
    public static void initializeRedraw(BoardView board) {
        BoardViewRedraw.board = board;
    }

 */


}
