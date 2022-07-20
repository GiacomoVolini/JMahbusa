package jmb.view;

import javafx.scene.effect.ColorAdjust;
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
        redrawPawns(board, logic.getBoardMatrix(whoCalled));
    }

    protected static void redrawPawns (DynamicGameBoard board, int[][] matrix) {
        int whitesPlaced = 0;
        int blacksPlaced = 0;
        for (int col = COL_BLACK_EXIT; col <= COL_WHITE_EXIT && (whitesPlaced < PAWNS_PER_PLAYER || blacksPlaced < PAWNS_PER_PLAYER); col++) {
            for (int row = 0; row < 16 && (whitesPlaced < PAWNS_PER_PLAYER || blacksPlaced < PAWNS_PER_PLAYER) && matrix[row][col] != EMPTY; row++) {
                switch (matrix[row][col]) {
                    case WHITE: case SELECTED_WHITE: case WRONG_WHITE:
                        placePawn(board, board.pawnArrayWHT, whitesPlaced, col, row);
                        highlightMovablePawn(board.pawnArrayWHT, whitesPlaced, col, row);
                        board.pawnArrayWHT[whitesPlaced].setEffect(null);
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
                        logic.isPawnMovable(col, row, true, board.whoCalled);
                        board.pawnArrayWHT[whitesPlaced].setPawnScale(SELECTED_PAWN_SCALE);
                        board.pawnArrayWHT[whitesPlaced].setStrokeWidth(SELECTED_PAWN_STROKE_WIDTH);
                        whitesPlaced++;
                        break;
                    case SELECTED_BLACK:
                        logic.isPawnMovable(col, row, true, board.whoCalled);
                        board.pawnArrayBLK[blacksPlaced].setPawnScale(SELECTED_PAWN_SCALE);
                        board.pawnArrayBLK[blacksPlaced].setStrokeWidth(SELECTED_PAWN_STROKE_WIDTH);
                        blacksPlaced++;
                        break;
                    case WRONG_WHITE:
                        board.pawnArrayWHT[whitesPlaced].setEffect(new ColorAdjust(0.0,0.9,0.0,0.0));
                        whitesPlaced++;
                        break;
                }
            }
        }
    }


    private static void highlightMovablePawn (PawnView[] pawnArray, int pawnIndex, int col, int row) {
        if (col <= COL_BLACK && col >= COL_WHITE && logic.isLastOnPoint(col, row, whoCalled) && logic.isPawnMovable(col, row, false, whoCalled)) {
            pawnArray[pawnIndex].setViewOrder(-2.0);
            pawnArray[pawnIndex].setDisable(false);
            pawnArray[pawnIndex].setStrokeWidth(MOVABLE_PAWN_STROKE_WIDTH);
        } else {
            pawnArray[pawnIndex].setViewOrder(-1.0);
            pawnArray[pawnIndex].setDisable(true);
            pawnArray[pawnIndex].setStrokeWidth(NORMAL_PAWN_STROKE_WIDTH);
        }
    }
    public static void resizeDiceTray(DynamicGameBoard board) {
        board.diceTray.setLayoutX(board.outerRect.getLayoutX() + board.outerRect.getWidth());
        board.diceTray.setLayoutY(board.outerRect.getLayoutY());
        if (board.diceTrayOpen) {
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
        board.diceArray[UPPER_DICE].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.15);
        board.diceArray[UPPER_DOUBLE_DICE].setLayoutY(board.diceArray[UPPER_DICE].getLayoutY() + board.diceArray[UPPER_DICE].getFitHeight());
        board.diceArray[LOWER_DICE].setLayoutY(board.diceTray.getLayoutY() + board.diceTray.getHeight() * 0.85 - board.diceArray[3].getFitHeight());
        board.diceArray[LOWER_DOUBLE_DICE].setLayoutY(board.diceArray[LOWER_DICE].getLayoutY() - board.diceArray[LOWER_DICE].getFitHeight());

    }
    public static void resizeExitRegions(DynamicGameBoard board) {
        if (logic.getBlackExit(whoCalled)) {
            board.blackExitRegion.setWidth(maxExitWidth);
            board.blackExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
        if (logic.getWhiteExit(whoCalled)) {
            board.whiteExitRegion.setWidth(maxExitWidth);
            board.whiteExitRegion.setLayoutX(board.outerRect.getLayoutX() - maxExitWidth);
        }
    }

    protected static void resizeAll(DynamicGameBoard board) {
        resizeOuterRect(board);
        GameBoardRedraw.resizeInnerBoard(board);
        resizeExitRegions(board);
        calcTrayWidths(board);
        if (logic.getGameStart(whoCalled)) {
            resizeDiceTray(board);
            if (board.diceTrayOpen)
                resizeDice(board);
        }
        if (board.pawnArrayWHT[0].isVisible())
            redrawPawns(board);
    }
}
