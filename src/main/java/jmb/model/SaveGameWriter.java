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

    //TODO AGGIUNGERE LOGICA TORNEO



    protected static void writeSaveFile(BoardLogic board, String fileName, WritableImage saveImage) {
        try {

            JSONObject saveContent = new JSONObject();

            saveContent.put("whitePlayer", board.getWhitePlayer());
            saveContent.put("blackPlayer", board.getBlackPlayer());
            saveContent.put("squareMatrix", generateSquareMatrixString(board));
            saveContent.put("isWhiteTurn", board.isWhiteTurn()); //TODO O METTERE NEGAZIONE O FAR FARE NEXT TURN QUANDO SI PIGIA SU SALVA
            saveContent.put("boardImage", generateBoardImageString(saveImage));
            int width = (int) saveImage.getWidth();
            int height = (int) saveImage.getHeight();
            saveContent.put("imageHeight", height);
            saveContent.put("imageWidth", width);

            System.out.println(board.getTurnDuration());
            saveContent.put("turnDuration", board.getTurnDuration());
            // saveContent.put("timeRemaining", view.getTimeRemaining()); TODO TOGLIERE, NON SERVE

            Files.createDirectories(Path.of("./saves/"));
            System.out.println(Path.of("./saves/"));
            String savePath = "./saves/" + fileName + ".json";
            //TODO

            Files.writeString(Path.of(savePath), saveContent.toString());
            //FileWriter file = new FileWriter(Path.of(board.getClass().getResource(savePath).toString()).toString());
            //file.write(String.valueOf(saveContent));
            //file.write(saveContent.toString());
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
        System.out.println(pixelBytes.length);
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
