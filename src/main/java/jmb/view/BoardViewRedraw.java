package jmb.view;

import static java.lang.Math.min;
import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BoardViewRedraw {

    private static BoardView board;

    //Valore di larghezza massima delle regioni di uscita e della zona dei dadi
    private static double maxExitWidth;
    private static double maxDTWidth;

    //Variabile per la dimensione del font di victoryLabel
    private static double victoryLabelFontSize = 16;

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


    private static double getBoardSize (AnchorPane window) {
        double usableWidth = window.getWidth()*HORIZONTAL_RESIZE_FACTOR;
        double usableHeight = window.getHeight()*VERTICAL_RESIZE_FACTOR;
        return min(usableHeight, usableWidth);
    }

    private static double getMaxBtnWidth(AnchorPane window, Rectangle outerRect) {
        double maxBtnWidth = window.getWidth() - (outerRect.getLayoutX() + outerRect.getWidth() +maxDTWidth+(BUTTON_ANCHOR*2));
        return min(MAX_BTN_WIDTH, maxBtnWidth);

    }

    public static void resizeOuterRect() {
        //  Ridimensiona il bordo del tavolo da gioco in funzione della finestra principale
        board.outerRect.setWidth(getBoardSize(board.window));
        board.outerRect.setLayoutX((board.window.getWidth()/2)-(board.outerRect.getWidth()*0.6));
        board.outerRect.setHeight(getBoardSize(board.window));
        board.outerRect.setLayoutY((board.window.getHeight()/2)-(board.outerRect.getHeight()/2));
    }

    public static void resizeBoardRect() {
        //  Ridimensiona il rettangolo interno in base alla dimensione di quello esterno
        board.boardRect.setWidth((board.outerRect.getWidth()*0.9));
        board.boardRect.setLayoutX(board.outerRect.getLayoutX()+(board.outerRect.getWidth()/2)-(board.boardRect.getWidth()/2));
        board.boardRect.setHeight((board.outerRect.getHeight()*0.9));
        board.boardRect.setLayoutY(board.outerRect.getLayoutY()+(board.outerRect.getHeight()/2)-(board.boardRect.getHeight()/2));
    }

    public static void resizeSeparator() {
        //  Ridimensiona il separatore tra le due metà dell'area di gioco
        //  in funzione della sua effettiva dimensione
        board.separator.setWidth(board.boardRect.getWidth()/13);
        board.separator.setLayoutX(board.boardRect.getLayoutX() + ((6*(board.boardRect.getWidth()/13))));
        board.separator.setHeight(board.boardRect.getHeight()+2);
        board.separator.setLayoutY(board.boardRect.getLayoutY()-1);
    }

    public static void resizeTimer() {
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

    public static void resizeLeftPoints() {
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

    public static void resizeRightPoints() {
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


    public static void resizeDice () {
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

    public static void resizeButtons() {

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

    public static void resizeExitRegions() {
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

    public static void resizeDiceTray() {
        board.diceTray.setLayoutX(board.outerRect.getLayoutX() + board.outerRect.getWidth());
        board.diceTray.setLayoutY(board.outerRect.getLayoutY());
        if (board.dtAnimDone) {
            board.diceTray.setWidth(maxDTWidth);
        }
        board.diceTray.setHeight(board.outerRect.getHeight());

    }

    //  Metodo per ridimensionamento e riposizionamento Pedine
    public static void redrawPawns() {

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


    private static void resizePawns () {
        for (int i =0; i<board.pawnArrayWHT.length; i++){
            board.pawnArrayBLK[i].setRadius(board.regArraySample[0].getPrefWidth() / 2);
            board.pawnArrayWHT[i].setRadius(board.regArraySample[0].getPrefWidth() / 2);
        }
    }

    // Metodo per rimposizionamento dinamico della pagina Pausa
    private static void resizePauseMenu(){
        board.pauseMenu.setLayoutX(board.window.getWidth()/2-board.pauseMenu.getWidth()/2);
        board.pauseMenu.setLayoutY(board.window.getHeight()/2-board.pauseMenu.getHeight()/2);
    }

    private static void resizeVictoryRect () {
        board.victoryPanel.setWidth(board.window.getWidth() / 2);
        board.victoryPanel.setHeight(board.window.getHeight()/2.5);
        board.victoryPanel.setLayoutX((board.window.getWidth() - board.victoryPanel.getWidth()) / 2);
        board.victoryPanel.setLayoutY((board.window.getHeight() - board.victoryPanel.getHeight()) / 2);
    }

    private static void resizeVictoryPawn () {
        board.victoryPawn.setRadius(board.victoryPanel.getHeight() / 10);
        board.victoryPawn.setLayoutX(board.victoryPanel.getLayoutX() + board.victoryPanel.getWidth() * 0.05 + board.victoryPawn.getRadius());
        board.victoryPawn.setLayoutY(board.victoryPanel.getLayoutY() + board.victoryPanel.getHeight() / 2);
    }

    private static void resizeVictoryExit () {
        board.victoryExit.setPrefWidth(board.victoryPanel.getWidth() * 0.3);
        board.victoryExit.setPrefHeight(board.victoryPanel.getHeight() * 0.15);
        board.victoryExit.setLayoutY(board.victoryPanel.getLayoutY() + (0.66 * board.victoryPanel.getHeight()));
        board.victoryExit.setLayoutX(board.victoryPanel.getLayoutX() + (board.victoryPanel.getWidth() - (board.victoryPanel.getWidth() * 0.3)) / 2);
    }

    private static void resizeVictoryCrown () {
        board.victoryCrown.setFitWidth(board.victoryPawn.getRadius()*2);
        board.victoryCrown.setLayoutY(victoryPawn.getLayoutY() - victoryPawn.getRadius()*2.2);
        victoryCrown.setLayoutX(victoryPawn.getLayoutX() - victoryPawn.getRadius());
    }


    private static void resizeVictoryLabel (Rectangle victoryPanel, Label victoryLabel) {
        victoryLabel.setLayoutY(victoryPanel.getLayoutY() + victoryPanel.getHeight() * 0.15);
        victoryLabel.setLayoutX(victoryPanel.getLayoutX() + victoryPanel.getWidth() * 0.25);
        resizeVictoryFont(victoryPanel, victoryLabel);

        System.out.println(victoryLabel.getWidth());
        System.out.println(victoryLabelFontSize);

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




    //  Metodo per ridimensionare gli elementi del pannello vittoria
    protected static void resizeVictoryPanel (AnchorPane window, Rectangle victoryPanel,
                                            Circle victoryPawn, Button victoryExit, ImageView victoryCrown, Label victoryLabel)
    {
        resizeVictoryRect(window, victoryPanel);
        resizeVictoryPawn(victoryPanel, victoryPawn);
        resizeVictoryLabel(victoryPanel, victoryLabel);
        resizeVictoryExit(victoryPanel, victoryExit);
        resizeVictoryCrown(victoryPawn, victoryCrown);

    }

    private static void resizeStartDialogue (AnchorPane window, TitledPane startDialogue) {
        startDialogue.setLayoutX(window.getWidth()/2-startDialogue.getPrefWidth()/2);
        startDialogue.setLayoutY(window.getHeight()/2-startDialogue.getPrefHeight()/2);
    }



    protected static void callResizeAll(BoardView board) {

        this.board = board;
        resizeAll();

    }

    private static void resizeAll() {

        //TODO Inserire dove necessario controlli su font size
        resizeOuterRect();
        resizeBoardRect();
        resizeSeparator();
        resizeTimer();
        resizeLeftPoints();
        resizeRightPoints();
        resizePawns();
        calcTrayWidth();
        calcDTWidth();
        if (board.gameStart) {
            resizeDiceTray();
            if (board.dtAnimDone)
                resizeDice(); }
        else
            resizeStartDialogue();
        resizeButtons();
        resizeExitRegions();
        redrawPawns();
        resizePauseMenu();
        if (board.gameEndState) {
            resizeVictoryPanel();
        }
        //TODO resizePlsPawns ();
        //TODO resizePlsRects ();

    }



}
