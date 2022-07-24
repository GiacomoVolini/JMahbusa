package jmb.logic;


import javafx.scene.image.WritableImage;
import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveGameWriter {
    protected static void writeSaveFile(GameLogic board, String fileName) {
        try {
            JSONObject saveContent = new JSONObject();
            saveContent.put("whitePlayer", board.getWhitePlayer());
            saveContent.put("blackPlayer", board.getBlackPlayer());
            saveContent.put("whiteExit", board.getWhiteExit());
            saveContent.put("blackExit", board.getBlackExit());
            saveContent.put("canRevert", board.getCanRevert());
            saveContent.put("squareMatrix", generateSquareMatrixString(board));
            saveContent.put("isWhiteTurn", !board.isWhiteTurn());
            saveContent.put("tournamentPoints", board.getTournamentPoints());
            saveContent.put("whitesWonPoints", board.getWhitesWonPoints());
            saveContent.put("blacksWonPoints", board.getBlacksWonPoints());
            saveContent.put("turnDuration", board.getTurnDuration());
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

    private static String generateSquareMatrixString(GameLogic board) {
        String out = "";
        for (int i = 0; i<16 ; i++) {
            for (int j = 0; j<26 ; j++) {
                out = out.concat(String.valueOf(board.squares[i][j]));
            }
            out = out.concat("\n");
        }
        return out;
    }
}
