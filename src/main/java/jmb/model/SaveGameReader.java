package jmb.model;


import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class SaveGameReader {

    protected static void readSaveGame (String saveName) {

        JSONParser parser = new JSONParser();
        JSONObject save = parser.parse(new FileReader("./saves/" + saveName + ".json"));
        String squareMatrixString = (String) save.get("squareMatrix");
        boolean isWhiteTurn = (boolean) save.get("isWhiteTurn");
        String blackPlayer = (String) save.get("blackPlayer");
        String whitePlayer = (String) save.get("whitePLayer");
        int turnDuration = (int) save.get("turnDuration");

        int[][] squareMatrix = parseSquareMatrixString(squareMatrixString);

    /*TODO
        https://www.geeksforgeeks.org/parse-json-java/
        https://www.javaguides.net/2019/07/jsonsimple-tutorial-read-and-write-json-in-java.html
        https://www.tutorialspoint.com/how-can-we-read-a-json-file-in-java
     */
    }

    private static int[][] parseSquareMatrixString (String matrixString) {
        //TODO scrivere
    }
}
