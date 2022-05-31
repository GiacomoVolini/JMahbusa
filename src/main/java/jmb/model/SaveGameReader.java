package jmb.model;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SaveGameReader {

    boolean isWhiteTurn;
    String blackPlayer;
    String whitePlayer;
    long turnDuration;
    int[][] squareMatrix;
    byte[] imageBytes;

    long imageWidth;
    long imageHeight;

    private SaveGameReader(String saveName) {
        try {
            JSONParser parser = new JSONParser();
            Object objSave = parser.parse(new FileReader("./saves/" + saveName + ".json"));
            JSONObject save = (JSONObject) objSave;
            String squareMatrixString = (String) save.get("squareMatrix");
            isWhiteTurn = (boolean) save.get("isWhiteTurn");
            blackPlayer = (String) save.get("blackPlayer");
            whitePlayer = (String) save.get("whitePlayer");
            turnDuration = (long) save.get("turnDuration");
            squareMatrix = parseSquareMatrixString(squareMatrixString);
            imageBytes = Base64.getDecoder().decode((String) save.get("boardImage"));
            imageWidth = (long) save.get("imageWidth");
            imageHeight = (long) save.get("imageHeight");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public static SaveGameReader readSaveGame (String saveName) {
        return new SaveGameReader(saveName);
    }





    /*TODO
        https://www.geeksforgeeks.org/parse-json-java/
        https://www.javaguides.net/2019/07/jsonsimple-tutorial-read-and-write-json-in-java.html
        https://www.tutorialspoint.com/how-can-we-read-a-json-file-in-java

     */

    private int[][] parseSquareMatrixString (String matrixString) {
        String[] stringArray = matrixString.split("\n");
        int[][] intMatrix = new int[16][26];
        for (int row = 0; row <16; row++){
            for (int col = 0; col<26; col++) {
                //intMatrix[row][col] = Character.getNumericValue(stringArray[15-row].charAt(col));
                intMatrix[row][col] = Character.getNumericValue(stringArray[row].charAt(col));
            }
        }
        return intMatrix;
    }

    protected String[] getLoadViewData () {
        String[] out = new String[] {whitePlayer, blackPlayer, String.valueOf(turnDuration)};
        return out;
    }

    protected byte[] getImageBytes() {
        return imageBytes;
    }

    protected static List<String> getSaveList() {
        System.out.println("Sono in SaveGameReader");
        File saveDirectory = new File ("./saves/");
        File[] saveFiles = saveDirectory.listFiles();
        System.out.println("Lista file");
        List<String> outputList = new ArrayList<String>();
        for (File save : saveFiles) {
            System.out.println(save.getName());
            outputList.add(save.getName().replace(".json", ""));
        }
        System.out.println(outputList.toString());
        return outputList;
    }

    protected long[] getImageDimensions() {
        long[] out = new long[] {imageWidth, imageHeight};
        return out;
    }
}
