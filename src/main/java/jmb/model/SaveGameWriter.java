package jmb.model;


import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import  org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class SaveGameWriter {

    protected static void writeSaveFile(BoardLogic board, String fileName, WritableImage saveImage) {
        try {

            JSONObject saveContent = new JSONObject();

            saveContent.put("whitePlayer", board.getWhitePlayer());
            saveContent.put("blackPlayer", board.getBlackPlayer());
            saveContent.put("whiteExit", board.getWhiteExit());
            saveContent.put("blackExit", board.getBlackExit());
            saveContent.put("squareMatrix", generateSquareMatrixString(board));
            saveContent.put("isWhiteTurn", !board.isWhiteTurn());
            saveContent.put("boardImage", generateBoardImageString(saveImage));
            saveContent.put("tournamentPoints", board.getTournamentPoints());
            saveContent.put("whitesWonPoints", board.getWhitesWonPoints());
            saveContent.put("blacksWonPoints", board.getBlacksWonPoints());
            int width = (int) saveImage.getWidth();
            int height = (int) saveImage.getHeight();
            saveContent.put("imageHeight", height);
            saveContent.put("imageWidth", width);
            saveContent.put("turnDuration", board.getTurnDuration());
            // saveContent.put("timeRemaining", view.getTimeRemaining()); TODO TOGLIERE, NON SERVE

            Files.createDirectories(Path.of("./saves/"));
            String savePath = "./saves/" + fileName + ".json";

            Files.writeString(Path.of(savePath), saveContent.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected static void deleteSaveFile (String fileName) {
        try {
            String savePath = "./saves/" + fileName + ".json";
            Files.delete(Path.of(savePath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static String generateSquareMatrixString(BoardLogic board) {
        String out = "";
        for (int i = 0; i<16 ; i++) {
            for (int j = 0; j<26 ; j++) {
                if (board.squares[i][j]==null) {
                    out = out.concat("0");
                } else if (board.squares[i][j].getisWhite()) {
                    out= out.concat("1");
                } else out = out.concat("2");
            }
            out = out.concat("\n");
        }
        return out;
    }

    private static String generateBoardImageString(WritableImage saveImage) {
        int width = (int) saveImage.getWidth();
        int height = (int) saveImage.getHeight();
        byte[] pixelBytes = new byte[width * height * 4];
        saveImage.getPixelReader().getPixels(0, 0, width, height,
                PixelFormat.getByteBgraInstance(),
                pixelBytes, 0, width * 4);
        return Base64.getEncoder().encodeToString(pixelBytes);
    }


    //TODO FORSE TOGLIERE METODI SOTTO

    private static String generateDiceValuesString(BoardLogic board) {
        String out = "";
        for (int i = 0; i<4; i++) {
            out = out.concat(String.valueOf(board.getDiceLogic().getDiceValue(i)) + ";");
        }
        return out;
    }

    private static String generateUsedDiceString (BoardLogic board) {
        String out = "";
        for (int i = 0; i<4; i++) {
            out = out.concat(board.getDiceLogic().getUsed(i) + ";");
        }
        return out;
    }
}
