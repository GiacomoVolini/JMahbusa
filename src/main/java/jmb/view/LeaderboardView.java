package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import jmb.model.Logic;
import jmb.model.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @FXML
    private ImageView search_icon;

    @FXML
    private TextField cerca;

    @FXML
    private Button uscita;

    //todo le dimenzioni del bottone (searchBTN)
    @FXML
    private Button searchBTN;

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        jmb.App.MainMenu();
    }

    @FXML
    void cercaNomi(ActionEvent event) {
        table.setItems(FXCollections.observableList(searchList()));
    }

    private List<Player> searchList() {
        //todo controlare toLowerCase, se la lettera grande non la vede ma solo se piccola
        return logic.getPlayerList().stream().filter(input ->
        {return input.getName().toLowerCase().contains(cerca.getText().toLowerCase());}).collect(Collectors.toList());
    }
    public void initialize() {
        table.setItems(FXCollections.observableList(logic.getPlayerList()));
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        victories.setCellValueFactory(new PropertyValueFactory<Player,Integer>("wins"));
        defeats.setCellValueFactory(new PropertyValueFactory<Player, Integer>("losses"));
        rate.setCellValueFactory(new PropertyValueFactory<Player, Double>("winRate"));
    }
}