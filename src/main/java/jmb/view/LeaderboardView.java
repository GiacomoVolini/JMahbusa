package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jmb.model.Player;

import static jmb.view.View.logic;

public class LeaderboardView {


    @FXML
    private TableView<Player> table;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Integer> victories;

    @FXML
    private TableColumn<Player, Integer> defeats;

    @FXML
    private TableColumn<Player, Double> rate;

    /*ObservableList<Leaderboard> classifica = FXCollections.observableArrayList(
        new Leaderboard( n1,)
    );*/

    public void initialize() {
        table.setItems(FXCollections.observableList(logic.getPlayerList()));
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        victories.setCellValueFactory(new PropertyValueFactory<Player,Integer>("wins"));
        defeats.setCellValueFactory(new PropertyValueFactory<Player, Integer>("losses"));
        rate.setCellValueFactory(new PropertyValueFactory<Player, Double>("winRate"));
        //victories.setCellValueFactory(new PropertyValueFactory<Person, int>("wins")); TODO forse cancellare
        //name.setCellValueFactory(name -> name.getValue().nameProperty());
    }
}
