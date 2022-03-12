package jmb.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class LeaderboardLogic {

    Path path;
    BufferedReader reader;
    ObservableList<Player> obsList = FXCollections.observableArrayList();
    //LinkedList<String[]> leadList; TODO forse cancellare
    String line;

    // Il costruttore crea il BufferedReader per il file csv della classifica,
    //      l'ObservableList per le singole entry e un oggetto String per la gestione di una singola riga del
    //      file csv
    public LeaderboardLogic () throws IOException {
        try {
            URL resource = this.getClass().getResource("LeaderBoards.csv");
            path = Paths.get(resource.toURI());
            reader = Files.newBufferedReader(path);
            //leadList = new LinkedList<String[]>(); TODO forse cancellare
            this.populateList();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch(URISyntaxException use) {
            use.printStackTrace();
        }   finally {
            if (reader!=null) {
                reader.close();
            }
        }
    }

    private void populateList() throws IOException {
        while ((line = reader.readLine()) != null)
            if (!line.isEmpty() && line.contains(";")) {
                //leadList.add(line.trim().split(";")); TODO forse cancellare
                String[] lineValues = line.trim().split(";");
                obsList.add(new Player(lineValues[0], lineValues[1], lineValues[2]));
            }
    }

    /*TODO forse cancellare, è test
    public void testList () {
        System.out.println("Nome\t Vittorie\t Sconfitte\t V\\S");
        for (String[] strArr: obsList) {
            for (String str : strArr) {
                System.out.print(str + "\t");
            }
            System.out.println(Double.parseDouble(strArr[1])/Double.parseDouble(strArr[2]));
        }
    }*/

    public ObservableList<Player> getList() {
        return obsList;
    }

}
