package jmb.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class LeaderboardView {

    @FXML
    private TableColumn<LeaderboardView, String> name;

    @FXML
    private TableColumn<LeaderboardView, Integer> victories;

    @FXML
    private TableColumn<LeaderboardView, Integer> defeats;

    @FXML
    private TableColumn<LeaderboardView, Integer> rate;

    /*ObservableList<Leaderboard> classifica = FXCollections.observableArrayList(
        new Leaderboard( n1,)
    );*/

    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        victories.setCellValueFactory(new PropertyValueFactory<Person, int>("wins"));
    }
}
