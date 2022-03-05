package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import static jmb.view.ConstantsView.*;

public class Leaderboard {

    @FXML
    private TableColumn<Leaderboard, String> name;

    @FXML
    private TableColumn<Leaderboard, Integer> victories;

    @FXML
    private TableColumn<Leaderboard, Integer> defeats;

    @FXML
    private TableColumn<Leaderboard, Integer> rate;

    /*ObservableList<Leaderboard> classifica = FXCollections.observableArrayList(
        new Leaderboard( n1,)
    );*/
}
