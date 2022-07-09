package jmb.view;

import static jmb.ConstantsShared.COL_BLACK;
import static jmb.ConstantsShared.COL_WHITE;
import static jmb.view.ConstantsView.MOVABLE_PAWN_STROKE_WIDTH;
import static jmb.view.ConstantsView.NORMAL_PAWN_STROKE_WIDTH;
import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;

public class DynamicGameBoardRedraw extends GameBoardRedraw{


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

}
