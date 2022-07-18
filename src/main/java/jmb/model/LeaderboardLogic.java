package jmb.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.StandardOpenOption.*;
import static jmb.ConstantsShared.*;
import static jmb.ConstantsShared.SUCCESS;
import static jmb.model.Logic.logic;

public class LeaderboardLogic {

    Path path;
    BufferedReader reader;
    ArrayList<Player> arrList = new ArrayList<>();
    String line;

    // Il costruttore crea il BufferedReader per il file csv della classifica,
    //      l'ObservableList per le singole entry e un oggetto String per la gestione di una singola riga del
    //      file csv
    public LeaderboardLogic() throws IOException{
        try {
            String ldbDirectory = logic.getAppDirectory() + "/leaderboard";
            if (!Files.exists(Path.of(ldbDirectory)))
                Files.createDirectory(Path.of(ldbDirectory));
            String ldbPath = ldbDirectory + "/Leaderboards.csv";
            if (!Files.exists(Path.of(ldbPath))) {
                Files.createFile(Path.of(ldbPath));
            }
            reader = Files.newBufferedReader(Path.of(ldbPath));
            this.populateList();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
    }



    private void populateList() throws IOException {
        while ((line = reader.readLine()) != null)
            if (!line.isEmpty() && line.contains(";")) {
                String[] lineValues = line.trim().split(";");
                lineValues[0] = lineValues[0].concat("\u2001");
                arrList.add(new Player(lineValues[0], lineValues[1], lineValues[2]));
            }
    }

    protected void addNewPlayer (String name) {
        if (!name.contains("\u2001")) {
            arrList.add(new Player(name.concat("\u2001")));
        }
    }

    public ArrayList<Player> getList() {
        return arrList;
    }

    public List<String> getNameList() {
        List<String> list = new ArrayList<>();
        Iterator<Player> it = arrList.iterator();
        while(it.hasNext()) {
            list.add(it.next().getName());
        }
        return list;
    }

        public void ldbWriter (Path path) {
            try {
                Files.delete(path);

                Iterator<Player> it = arrList.iterator();
                while(it.hasNext()) {
                    Files.writeString(path, it.next() + System.lineSeparator(), CREATE, WRITE, APPEND);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }


    protected void addStatsToList( String winner, String loser, int points) {

        Player winPlayer = arrList.stream().filter(input -> input.getName().contains(winner)).findFirst().get();
        Player lossPlayer = arrList.stream().filter(input -> input.getName().contains(loser)).findFirst().get();
        winPlayer.addWins(points);
        lossPlayer.addLosses(points);
        winPlayer.setWinRate();
        lossPlayer.setWinRate();
        ldbWriter(path);
    }

    protected int compareNameLists(String newName1, String newName2) {
        List<String> nameList = this.getNameList();
        int output = DECIDING;
        if (newName1 == null || newName2 == null) {
            output = EMPTY_NAMES_ERROR;
        } else if (newName1.equals(newName2)) {
            output = SAME_NAME_ERROR;
        }
        // I nomi della lista hanno in coda il carattere di escape "\u2001"
        // Questo consente di distinguere tra un nome preso dalla lista e lo stesso nome inserito manualmente,
        //      e permette di effettuare un controllo su eventuali duplicati
        if (output == DECIDING) {
            Iterator<String> it = nameList.iterator();
            while (it.hasNext() && output == DECIDING) {
                String temp = it.next();
                if (newName1.equals(temp.stripTrailing())) {
                    output = NAME1_ALREADY_PRESENT;
                } else if (newName2.equals(temp.stripTrailing())) {
                    output = NAME2_ALREADY_PRESENT;
                }
            }
        }
        if (output == DECIDING) {
            output = SUCCESS;
        }
        return output;
    }

}
