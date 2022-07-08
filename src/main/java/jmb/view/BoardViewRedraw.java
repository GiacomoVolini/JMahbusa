package jmb.view;

import static java.lang.Math.*;
import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BoardViewRedraw extends GameBoardRedraw {


    //  L'altezza massima che il tabellone può occupare rispetto alla finestra

    //Valore di larghezza massima delle regioni di uscita e della zona dei dadi
    private static double maxDTWidth;

    // Metodo getter per maxDTWidth
    public static double getMaxDTWidth() {
        return maxDTWidth;
    }

    protected static void calcTrayWidths(GameBoard board) {
        maxExitWidth = maxDTWidth = board.pawnArrayWHT[0].getRadius() * 6;
    }


    private static double getMaxBtnWidth(AnchorPane window, Rectangle outerRect) {
        double maxBtnWidth = window.getWidth() - (outerRect.getLayoutX() + outerRect.getWidth() + maxDTWidth + (BUTTON_ANCHOR * 2));
        return min(MAX_BTN_WIDTH, maxBtnWidth);

    }

    public static void resizeTimer(BoardView board) {
        //  Ridimensiona le barre per il timer
        board.timerOut.setWidth(board.separator.getWidth() / 2);
        board.timerOut.setLayoutX(board.separator.getLayoutX() + (board.separator.getWidth() / 2) - (board.timerOut.getWidth() / 2));
        board.timerOut.setHeight(board.separator.getHeight() - 4);
        board.timerOut.setLayoutY(board.separator.getLayoutY() + (board.separator.getHeight() / 2) - (board.timerOut.getHeight() / 2));

        board.timerIn.setWidth(board.timerOut.getWidth() - 4);
        board.timerIn.setLayoutX(board.timerOut.getLayoutX() + 2);
        board.timerIn.setHeight(board.timerOut.getHeight() - 4);
        board.timerIn.setLayoutY(board.timerOut.getLayoutY() + 2);
    }


    public static void resizeDice(BoardView board) {
        if (!board.diceArray[0].isVisible()) {
            for (ImageView dice : board.diceArray) {
                dice.setVisible(true);
            }
        }
        for (ImageView dice : board.diceArray) {
            dice.setFitHeight(board.diceTray.getWidth() * 0.425);
            dice.setFitWidth(board.diceTray.getWidth() * 0.425);
        }
        double diceX = board.diceTray.getLayoutX() + board.diceTray.getWidth() / 2 - board.diceArray[0].getFitWidth() / 2;
        for (ImageView dice : board.diceArray) {
            dice.setLayoutX(diceX);
        }
        board.diceArray[0].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.15);
        board.diceArray[2].setLayoutY(board.diceArray[0].getLayoutY() + board.diceArray[0].getFitHeight());
        board.diceArray[1].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.85 - board.diceArray[3].getFitHeight());
        board.diceArray[3].setLayoutY(board.diceArray[1].getLayoutY() - board.diceArray[1].getFitHeight());

    }

    public static void resizeButtons(BoardView board) {

        //  Ridimensiona i Buttoni rispetto alla finestra principale
        //  Larghezza
        board.backBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.finishBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.menuBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.backBTN.setPrefWidth(board.window.getWidth() * 0.15);
        board.finishBTN.setPrefWidth(board.backBTN.getPrefWidth());
        board.menuBTN.setPrefWidth(board.backBTN.getPrefWidth());
        // Altezza
        board.backBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.finishBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.menuBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.backBTN.setPrefHeight(board.window.getHeight() * 0.2);
        board.finishBTN.setPrefHeight(board.backBTN.getPrefHeight());
        board.menuBTN.setPrefHeight(board.backBTN.getPrefHeight());
        board.backBTN.setLayoutY(board.window.getHeight() * .25 - board.backBTN.getPrefHeight() / 2);
        board.finishBTN.setLayoutY((board.window.getHeight() - board.finishBTN.getPrefHeight()) / 2);
        board.menuBTN.setLayoutY(board.window.getHeight() * .75 - board.menuBTN.getPrefHeight() / 2);

    }

    public static void resizeExitRegions(GameBoard board) {
        if (logic.getBlackExit()) {
            board.blackExitRegion.setWidth(maxExitWidth);
            board.blackExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
        if (logic.getWhiteExit()) {
            board.whiteExitRegion.setWidth(maxExitWidth);
            board.whiteExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
    }

    public static void redrawPawns(GameBoard board) {
        redrawPawns(board, logic.getBoardMatrix());
    }

    protected static void redrawPawns (GameBoard board, int[][] matrix) {
        int whitesPlaced = 0;
        int blacksPlaced = 0;
        for (int col = COL_BLACK_EXIT; col <= COL_WHITE_EXIT && (whitesPlaced < PAWNS_PER_PLAYER || blacksPlaced < PAWNS_PER_PLAYER); col++) {
            for (int row = 0; row < 16 && (whitesPlaced < PAWNS_PER_PLAYER || blacksPlaced < PAWNS_PER_PLAYER) && matrix[row][col] != EMPTY; row++) {
                switch (matrix[row][col]) {
                    case WHITE: case SELECTED_WHITE:
                        placePawn(board, board.pawnArrayWHT, whitesPlaced, col, row);
                        highlightMovablePawn(board.pawnArrayWHT, whitesPlaced, col, row);
                        break;
                    case BLACK: case SELECTED_BLACK:
                        placePawn(board, board.pawnArrayBLK, blacksPlaced, col, row);
                        highlightMovablePawn(board.pawnArrayBLK, blacksPlaced, col, row);
                        break;
                }
                switch (matrix[row][col]) {
                    case WHITE:
                        board.pawnArrayWHT[whitesPlaced].setPawnScale(NORMAL_PAWN_SCALE);
                        whitesPlaced++;
                        break;
                    case BLACK:
                        board.pawnArrayBLK[blacksPlaced].setPawnScale(NORMAL_PAWN_SCALE);
                        blacksPlaced++;
                        break;
                    case SELECTED_WHITE:
                        board.pawnArrayWHT[whitesPlaced].setPawnScale(SELECTED_PAWN_SCALE);
                        board.pawnArrayWHT[whitesPlaced].setStrokeWidth(SELECTED_PAWN_STROKE_WIDTH);
                        whitesPlaced++;
                        break;
                    case SELECTED_BLACK:
                        board.pawnArrayBLK[blacksPlaced].setPawnScale(SELECTED_PAWN_SCALE);
                        board.pawnArrayBLK[blacksPlaced].setStrokeWidth(SELECTED_PAWN_STROKE_WIDTH);
                        blacksPlaced++;
                        break;
                }
            }
        }
    }

    private static void highlightMovablePawn (PawnView[] pawnArray, int pawnIndex, int col, int row) {
        if (col <= COL_BLACK && col >= COL_WHITE && logic.isLastOnPoint(col, row) && logic.isPawnMovable(col, row)) {
            pawnArray[pawnIndex].setViewOrder(-1.0);
            pawnArray[pawnIndex].setDisable(false);
            pawnArray[pawnIndex].setStrokeWidth(MOVABLE_PAWN_STROKE_WIDTH);
        } else {
            pawnArray[pawnIndex].setViewOrder(0.0);
            pawnArray[pawnIndex].setDisable(true);
            pawnArray[pawnIndex].setStrokeWidth(NORMAL_PAWN_STROKE_WIDTH);
        }
    }

    public static void resizeDiceTray(BoardView board) {
        System.out.println("Sono dentro resizeDiceTray");
        System.out.println("X " + board.outerRect.getLayoutX());
        System.out.println("Y " + board.outerRect.getLayoutY());
        System.out.println("WDT " + board.outerRect.getWidth());
        board.diceTray.setLayoutX(board.outerRect.getLayoutX() + board.outerRect.getWidth());
        board.diceTray.setLayoutY(board.outerRect.getLayoutY());
        if (board.dtAnimDone) {
            board.diceTray.setWidth(maxDTWidth);
        }
        board.diceTray.setHeight(board.outerRect.getHeight());

    }

    // Metodo per rimposizionamento dinamico della pagina Pausa
    protected static void resizePauseMenu(BoardView board) {
        board.pauseMenu.setLayoutX(board.window.getWidth() / 2 - board.pauseMenu.getWidth() / 2);
        board.pauseMenu.setLayoutY(board.window.getHeight() / 2 - board.pauseMenu.getHeight() / 2);
    }

    private static void resizeVictoryRect(BoardView board) {
        board.victoryPanel.setWidth(board.window.getWidth() / 2);
        board.victoryPanel.setHeight(board.window.getHeight() / 2.5);
        board.victoryPanel.setLayoutX((board.window.getWidth() - board.victoryPanel.getWidth()) / 2);
        board.victoryPanel.setLayoutY((board.window.getHeight() - board.victoryPanel.getHeight()) / 2);
    }

    private static void resizeVictoryPawn(BoardView board) {
        board.victoryPawn.setRadius(board.victoryPanel.getHeight() / 10);
        board.victoryPawn.setLayoutX(board.victoryPanel.getLayoutX() + board.victoryPanel.getWidth() * 0.05 + board.victoryPawn.getRadius());
        board.victoryPawn.setLayoutY(board.victoryPanel.getLayoutY() + board.victoryPanel.getHeight() / 2);
    }

    private static void resizeVictoryExit(BoardView board) {
        board.victoryExit.setPrefWidth(board.victoryPanel.getWidth() * 0.4);
        board.victoryExit.setPrefHeight(board.victoryPanel.getHeight() * 0.15);
        board.victoryExit.setLayoutY(board.victoryPanel.getLayoutY() + (0.66 * board.victoryPanel.getHeight()));
        board.victoryExit.setLayoutX(board.victoryPanel.getLayoutX() + (board.victoryPanel.getWidth() - (board.victoryPanel.getWidth() * 0.3)) / 2);
    }

    private static void resizeVictoryCrown(BoardView board) {
        board.victoryCrown.setFitWidth(board.victoryPawn.getRadius() * 2);
        board.victoryCrown.setLayoutY(board.victoryPawn.getLayoutY() - board.victoryPawn.getRadius() * 2.2);
        board.victoryCrown.setLayoutX(board.victoryPawn.getLayoutX() - board.victoryPawn.getRadius());
    }


    private static void resizeVictoryLabel(BoardView board) {
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
        double widthFactor = victoryPanel.getWidth() / 400;
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 20 * widthFactor));
    }

    private static void resizeTournamentRibbon(BoardView board) {
        board.tourmanentRibbon.setLayoutX(board.victoryPawn.getLayoutX() + board.victoryPawn.getRadius() * 0.10);
        board.tourmanentRibbon.setLayoutY(board.victoryPawn.getLayoutY() - board.victoryPawn.getRadius() * 0.20);
        board.tourmanentRibbon.setFitWidth(board.victoryPawn.getRadius());
    }


    //  Metodo per ridimensionare gli elementi del pannello vittoria
    protected static void resizeVictoryPanel(BoardView board) {
        resizeVictoryRect(board);
        resizeVictoryPawn(board);
        resizeVictoryLabel(board);
        resizeVictoryExit(board);
        resizeVictoryCrown(board);
        if (board.tourmanentRibbon != null)
            resizeTournamentRibbon(board);

    }

    private static void resizeStartDialogue(BoardView board) {
        board.startDialogue.setLayoutX(board.window.getWidth() / 2 - board.startDialogue.getPrefWidth() / 2);
        board.startDialogue.setLayoutY(board.window.getHeight() / 2 - board.startDialogue.getPrefHeight() / 2);
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

    private static void resizePlsOutRects(BoardView board) {
        AnchorPane.setTopAnchor(board.plWHTOutRect, board.window.getHeight() * 0.0275);
        AnchorPane.setTopAnchor(board.plBLKOutRect, board.window.getHeight() * 0.0275);
        AnchorPane.setLeftAnchor(board.plWHTOutRect, board.window.getWidth() * 0.025 + board.plWHTPawn.getRadius() * 2);
        AnchorPane.setRightAnchor(board.plBLKOutRect, board.window.getWidth() * 0.025 + board.plBLKPawn.getRadius() * 2);
        board.plWHTOutRect.setHeight(board.plWHTPawn.getRadius() * 2);
        board.plBLKOutRect.setHeight(board.plBLKPawn.getRadius() * 2);
        board.plWHTOutRect.setWidth(board.window.getWidth() * 0.18);
        board.plBLKOutRect.setWidth(board.window.getWidth() * 0.18);
    }

    private static void resizePlsInRects(BoardView board) {
        board.plWHTInRect.setWidth(board.window.getWidth() * 0.15);
        board.plWHTInRect.setHeight(board.plWHTPawn.getRadius() * 1.5);
        board.plBLKInRect.setWidth(board.window.getWidth() * 0.15);
        board.plBLKInRect.setHeight(board.plBLKPawn.getRadius() * 1.5);
        AnchorPane.setTopAnchor(board.plBLKInRect,
                AnchorPane.getTopAnchor(board.plBLKOutRect) + board.plBLKOutRect.getHeight() / 2 - board.plBLKInRect.getHeight() / 2);
        AnchorPane.setTopAnchor(board.plWHTInRect,
                AnchorPane.getTopAnchor(board.plWHTOutRect) + board.plWHTOutRect.getHeight() / 2 - board.plWHTInRect.getHeight() / 2);
        AnchorPane.setLeftAnchor(board.plWHTInRect,
                AnchorPane.getLeftAnchor(board.plWHTOutRect) + board.plWHTOutRect.getWidth() / 2 - board.plWHTInRect.getWidth() / 2);
        AnchorPane.setRightAnchor(board.plBLKInRect,
                AnchorPane.getRightAnchor(board.plBLKOutRect) + board.plBLKOutRect.getWidth() / 2 - board.plBLKInRect.getWidth() / 2);
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
        double widthFactor = board.plWHTInRect.getWidth() / 110;
        double fontSize = max(13.5, min(25, (12 * widthFactor)));
        board.plWHTText.setFont(Font.font("calibri", FontWeight.NORMAL, fontSize));
        board.plBLKText.setFont(Font.font("calibri", FontWeight.NORMAL, fontSize));

    }

    protected static void resizeTournamentComponents(BoardView board) {
        double yAnchor = board.window.getHeight() * 0.0275;
        AnchorPane.setTopAnchor(board.tournamentWhitePoints, yAnchor);
        AnchorPane.setTopAnchor(board.tournamentBlackPoints, yAnchor);
        AnchorPane.setLeftAnchor(board.tournamentWhitePoints, board.window.getWidth() * 0.0125);
        AnchorPane.setRightAnchor(board.tournamentBlackPoints, board.window.getWidth() * 0.0125);
        AnchorPane.setTopAnchor(board.tournamentCup,yAnchor*0.3);
        AnchorPane.setTopAnchor(board.tournamentPointsToWin, yAnchor*0.3);
        double pawnSize = board.plWHTPawn.getRadius()*2;
        double cupSize = board.plWHTPawn.getRadius()*3;
        board.tournamentCup.setFitWidth(cupSize);
        board.tournamentCup.setFitHeight(cupSize);
        board.tournamentPointsToWin.setPrefWidth(cupSize);
        board.tournamentPointsToWin.setPrefHeight(cupSize*0.7);
        board.tournamentWhitePoints.setPrefWidth(pawnSize);
        board.tournamentWhitePoints.setPrefHeight(pawnSize);
        board.tournamentBlackPoints.setPrefWidth(pawnSize);
        board.tournamentBlackPoints.setPrefHeight(pawnSize);
        double fontFactor = board.plWHTPawn.getRadius() / 25;
        board.tournamentWhitePoints.setFont(Font.font("calibri", FontWeight.BOLD, 20 * fontFactor));
        board.tournamentBlackPoints.setFont(Font.font("calibri", FontWeight.BOLD, 20 * fontFactor));
        board.tournamentPointsToWin.setFont(Font.font("calibri", FontWeight.BOLD, 20 * fontFactor));
        double cupX = (board.window.getWidth() - board.tournamentCup.getFitWidth())/2;
        board.tournamentCup.setLayoutX(cupX);
        board.tournamentPointsToWin.setLayoutX(cupX);

    }

    private static void resizeSaveDialogue(BoardView board) {
        board.saveDialogue.setLayoutX(board.window.getWidth() / 2 - board.saveDialogue.getPrefWidth() / 2);
        board.saveDialogue.setLayoutY(board.window.getHeight() / 2 - board.saveDialogue.getPrefHeight() / 2);
    }


    protected static void resizeAll(BoardView board) {
        resizeOuterRect(board);
        GameBoardRedraw.resizeInnerBoard(board);
        resizeExitRegions(board);
        calcTrayWidths(board);
        resizePlsPawns(board);
        resizePlsRects(board);
        resizeTimer(board);
        if (logic.getGameStart()) {
            resizeDiceTray(board);
            if (board.dtAnimDone)
                resizeDice(board);
        } else
            resizeStartDialogue(board);
        resizeButtons(board);
        redrawPawns(board);
        resizePauseMenu(board);
        resizeSaveDialogue(board);
        if (logic.isTournamentOngoing())
            resizeTournamentComponents(board);
        if (logic.getGameEndState()) {
            resizeVictoryPanel(board);
        }
    }

}