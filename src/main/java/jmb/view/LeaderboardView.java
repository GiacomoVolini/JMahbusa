package jmb.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import jmb.logic.Player;

import java.util.List;
import java.util.stream.Collectors;

import static jmb.view.View.logic;
import static jmb.view.ConstantsView.*;

public class LeaderboardView {

    @FXML
    private AnchorPane window;

    @FXML
    private Label titleLabel;

    @FXML
    private AnchorPane smallAnchor;

    @FXML
    protected TableView<Player> table;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Integer> victories;

    @FXML
    private TableColumn<Player, Integer> defeats;

    @FXML
    private TableColumn<Player, Double> rate;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button exitButton;

    @FXML
    void goToMainMenu(){
        App.changeRoot(MAIN_MENU);
    }

    @FXML
    void searchNames(ActionEvent event) {
        table.setItems(FXCollections.observableList(searchList()));
    }

    private List<Player> searchList() {
        return logic.getPlayerList().stream().filter(input ->
        {return input.getName().toLowerCase().contains(searchTextField.getText().toLowerCase());}).collect(Collectors.toList());
    }

    protected void changeDimensions() {
        double quarterWidth = window.getWidth()/4;

        name.setPrefWidth(quarterWidth);
        victories.setPrefWidth(quarterWidth);
        defeats.setPrefWidth(quarterWidth);
        rate.setPrefWidth(quarterWidth);

        smallAnchor.setPrefWidth(quarterWidth);

        exitButton.setPrefWidth(window.getWidth()/6);

    }

    public void initialize() {
        table.setItems(FXCollections.observableList(logic.getPlayerList()));
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        victories.setCellValueFactory(new PropertyValueFactory<Player,Integer>("wins"));
        defeats.setCellValueFactory(new PropertyValueFactory<Player, Integer>("losses"));
        rate.setCellValueFactory(new PropertyValueFactory<Player, Double>("winRate"));

        //Languages
        titleLabel.setText(logic.getString("leaderboard"));
        name.setText(logic.getString("Name"));
        victories.setText(logic.getString("Victories"));
        defeats.setText(logic.getString("Defeats"));
        rate.setText(logic.getString("Winrate"));
        searchTextField.setPromptText(logic.getString("Search"));
        exitButton.setText(logic.getString("Exit"));

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }
}