package jmb.model;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SaveGameReader {

    boolean isWhiteTurn;
    String blackPlayer;
    String whitePlayer;
    long turnDuration;
    int[][] squareMatrix;
    private SaveGameReader(String saveName) {
        try {
            JSONParser parser = new JSONParser();
            Object objSave = parser.parse(new FileReader("./saves/" + saveName + ".json"));
            JSONObject save = (JSONObject) objSave;
            String squareMatrixString = (String) save.get("squareMatrix");
            isWhiteTurn = (boolean) save.get("isWhiteTurn");
            blackPlayer = (String) save.get("blackPlayer");
            whitePlayer = (String) save.get("whitePLayer");
            turnDuration = (long) save.get("turnDuration");
            squareMatrix = parseSquareMatrixString(squareMatrixString);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    protected static SaveGameReader readSaveGame (String saveName) {
        return new SaveGameReader(saveName);
    }





    /*TODO
        https://www.geeksforgeeks.org/parse-json-java/
        https://www.javaguides.net/2019/07/jsonsimple-tutorial-read-and-write-json-in-java.html
        https://www.tutorialspoint.com/how-can-we-read-a-json-file-in-java

     */

    private int[][] parseSquareMatrixString (String matrixString) {
        //TODO scrivere
        String[] stringArray = matrixString.split("\n");
        int[][] intMatrix = new int[16][26];
        for (int row = 0; row <16; row++){
            for (int col = 0; col<26; col++) {
                intMatrix[row][col] = Character.getNumericValue(stringArray[15-row].charAt(col));
            }
        }
        return intMatrix;
    }
}
