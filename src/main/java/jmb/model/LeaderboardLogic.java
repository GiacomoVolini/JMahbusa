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

public class LeaderboardLogic {

    Path path;
    BufferedReader reader;
    ArrayList<Player> arrList = new ArrayList<>();
    String line;

    // Il costruttore crea il BufferedReader per il file csv della classifica,
    //      l'ObservableList per le singole entry e un oggetto String per la gestione di una singola riga del
    //      file csv
    public LeaderboardLogic () throws IOException {
        try {
            URL resource = this.getClass().getResource("LeaderBoards.csv");
            path = Paths.get(resource.toURI());
            reader = Files.newBufferedReader(path);
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
                String[] lineValues = line.trim().split(";");
                lineValues[0] = lineValues[0].concat("\u2001");
                arrList.add(new Player(lineValues[0], lineValues[1], lineValues[2]));
            }
    }

    protected void addNewPlayer (String name) {
        arrList.add (new Player (name));
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
                    Player next = it.next();
                    System.out.println("Sto stampando l'elemento " + next.toString());
                    Files.writeString(path, next + System.lineSeparator(), CREATE, WRITE, APPEND);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }


    protected void addStatsToList( String winner, String loser) {

        Player winPlayer = arrList.stream().filter(input -> input.getName().contains(winner)).findFirst().get();
        winPlayer.addWin();
        Player lossPlayer = arrList.stream().filter(input -> input.getName().contains(loser)).findFirst().get();
        lossPlayer.addLoss();
    }

}
