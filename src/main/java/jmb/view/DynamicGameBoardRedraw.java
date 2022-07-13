package jmb.view;

import javafx.scene.image.ImageView;

import static jmb.ConstantsShared.COL_BLACK;
import static jmb.ConstantsShared.COL_WHITE;
import static jmb.view.ConstantsView.MOVABLE_PAWN_STROKE_WIDTH;
import static jmb.view.ConstantsView.NORMAL_PAWN_STROKE_WIDTH;
import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;

public class DynamicGameBoardRedraw extends GameBoardRedraw{
    protected static double maxDTWidth;
    public static double getMaxDTWidth() {
        return maxDTWidth;
    }
    protected static void calcTrayWidths(DynamicGameBoard board) {
        maxExitWidth = maxDTWidth = board.pawnArrayWHT[0].getRadius() * 6;
    }


    public static void redrawPawns(DynamicGameBoard board) {
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
    public static void resizeDiceTray(DynamicGameBoard board) {
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

    public static void resizeDice(DynamicGameBoard board) {
        /*if (!game.diceArray[UPPER_DICE].isVisible()) {
            for (ImageView gameDice : game.diceArray) {
                gameDice.setVisible(true);
            }
        }*/
        for (ImageView dice : board.diceArray) {
            dice.setFitHeight(board.diceTray.getWidth() * 0.425);
            dice.setFitWidth(board.diceTray.getWidth() * 0.425);
        }
        double diceX = board.diceTray.getLayoutX() + board.diceTray.getWidth() / 2 - board.diceArray[0].getFitWidth() / 2;
        for (ImageView dice : board.diceArray) {
            dice.setLayoutX(diceX);
        }

        System.out.println("UPPER DICE "+ (board.diceTray.getLayoutY() + board.diceTray.getHeight()*0.15));
        System.out.println("UPPER DOUBLE DICE " + (board.diceArray[0].getLayoutY() + board.diceArray[0].getFitHeight()));
        System.out.println("LOWER DOUBLE DICE " + (board.diceArray[1].getLayoutY() - board.diceArray[1].getFitHeight()));
        System.out.println("LOWER DICE " + (board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.85 - board.diceArray[3].getFitHeight()));

        board.diceArray[UPPER_DICE].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.15);
        board.diceArray[UPPER_DOUBLE_DICE].setLayoutY(board.diceArray[UPPER_DICE].getLayoutY() + board.diceArray[UPPER_DICE].getFitHeight());
        board.diceArray[LOWER_DICE].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.85 - board.diceArray[3].getFitHeight());
        board.diceArray[LOWER_DOUBLE_DICE].setLayoutY(board.diceArray[LOWER_DICE].getLayoutY() - board.diceArray[LOWER_DICE].getFitHeight());

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

    protected static void resizeAll(DynamicGameBoard board) {
        resizeOuterRect(board);
        GameBoardRedraw.resizeInnerBoard(board);
        resizeExitRegions(board);
        calcTrayWidths(board);
        if (logic.getGameStart()) {
            resizeDiceTray(board);
            if (board.dtAnimDone)
                resizeDice(board);
        }
        if (board.pawnArrayWHT[0].isVisible())
            redrawPawns(board);
    }
}
