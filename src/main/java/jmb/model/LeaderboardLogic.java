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

public class LeaderboardLogic {

    Path path;
    BufferedReader reader;
    ArrayList<Player> arrList = new ArrayList<>();
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
                lineValues[0] = lineValues[0].concat("\u2001");
                arrList.add(new Player(lineValues[0], lineValues[1], lineValues[2]));
            }
    }

    protected void addNewPlayer (String name) {
        arrList.add (new Player (name));
    }

    /*TODO forse cancellare, è test
    public void testList () {
        System.out.println("Nome\t Vittorie\t Sconfitte\t V\\S");
        for (String[] strArr: arrList) {
            for (String str : strArr) {
                System.out.print(str + "\t");
            }
            System.out.println(Double.parseDouble(strArr[1])/Double.parseDouble(strArr[2]));
        }
    }*/

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

}
