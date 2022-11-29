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

import static jmb.view.ConstantsView.MAIN_MENU;
import static jmb.view.View.*;

public class LeaderboardView implements GenericGUI{

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
        getView().changeRoot(MAIN_MENU);
    }

    @FXML
    void searchNames(ActionEvent event) {
        table.setItems(FXCollections.observableList(searchList()));
    }

    private List<Player> searchList() {
        //Il metodo restituisce la sottolista dei giocatori il cui nome contiene la stringa inserita in searchTextField
        return getLogic().getPlayerList().stream().filter(input ->
                input.getName().toLowerCase().contains(searchTextField.getText().toLowerCase())).collect(Collectors.toList());
    }

    public void initialize() {
        table.setItems(FXCollections.observableList(getLogic().getPlayerList()));
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        victories.setCellValueFactory(new PropertyValueFactory<Player,Integer>("wins"));
        defeats.setCellValueFactory(new PropertyValueFactory<Player, Integer>("losses"));
        rate.setCellValueFactory(new PropertyValueFactory<Player, Double>("winRate"));

        //Languages
        titleLabel.setText(getLogic().getString("leaderboards"));
        name.setText(getLogic().getString("Name"));
        victories.setText(getLogic().getString("Victories"));
        defeats.setText(getLogic().getString("Defeats"));
        rate.setText(getLogic().getString("Winrate"));
        searchTextField.setPromptText(getLogic().getString("Search"));
        exitButton.setText(getLogic().getString("Exit"));

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        //  LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

    public void changeDimensions() {
        double quarterWidth = window.getWidth()/4;

        name.setPrefWidth(quarterWidth);
        victories.setPrefWidth(quarterWidth);
        defeats.setPrefWidth(quarterWidth);
        rate.setPrefWidth(quarterWidth);

        smallAnchor.setPrefWidth(quarterWidth);
        exitButton.setPrefWidth(window.getWidth()/6);

    }
}