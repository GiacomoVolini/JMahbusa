package jmb.model;


import  org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveGameWriter {

    //TODO AGGIUNGERE LOGICA TORNEO

    //TODO GETRESOURCE NON VA BENE. BISOGNA FAR CREARE LA CARTELLA SAVE SE NON ESISTE E POI
    //  PUNTARE A QUELLA E SALVARE IL FILE

/* TODO
    Nomi Giocatori – BoardLogic – stringhe whitePlayer e blackPlayer
    Dove stanno le pedine – BoardLogic – matrice di PawnLogic squares -> forse convertibile in matrice interi
    Di chi è il turno – BoardLogic – booleano whiteTurn
    Quali dadi hanno usato (e i valori dei dadi) - DIceLogic – vari array interni
    Quanto dura un turno – View – turn_duration
    Quanto tempo manca alla fine del turno – BoardView – stato dell’animazione e durata del tempo - calcolare
    Le mosse che hanno già effettuato nel turno – BoardLogic – Stack turnMoves
     */


    protected static void writeSaveFile(BoardLogic board, String fileName) {
    /*TODO
        board.getBlackPlayer();
        board.squares;
        board.isWhiteTurn();
        board.getDiceLogic().getDiceValues();   // int [4]
        board.getDiceLogic().getUsedArray();    // boolean[4]
        board.getTurnDuration();
        board.getTimeRemaining();
        generateMoveRecord();
     */
        try {
            JSONObject saveContent = new JSONObject();

            saveContent.put("whitePlayer", board.getWhitePlayer());
            saveContent.put("blackPlayer", board.getBlackPlayer());
            saveContent.put("squareMatrix", generateSquareMatrixString(board));
            saveContent.put("isWhiteTurn", board.isWhiteTurn()); //TODO O METTERE NEGAZIONE O FAR FARE NEXT TURN QUANDO SI PIGIA SU SALVA
            //saveContent.put("diceValues", generateDiceValuesString(board)); TODO NON SERVE PIU
            //saveContent.put("usedDice", generateUsedDiceString(board)); TODO NON SERVE PIU
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
