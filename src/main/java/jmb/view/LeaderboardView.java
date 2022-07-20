package jmb.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import jmb.logic.Player;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static jmb.view.View.logic;
import static jmb.view.ConstantsView.*;

public class LeaderboardView {

    @FXML
    private AnchorPane Window;

    @FXML
    private Label lTesto;

    @FXML
    private AnchorPane smallAncor;

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
    private ImageView search_icon;

    @FXML
    private TextField cerca;

    @FXML
    private Button uscita;

    @FXML
    private Button searchBTN;

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        App.changeRoot(MAIN_MENU);
    }

    @FXML
    void cercaNomi(ActionEvent event) {
        table.setItems(FXCollections.observableList(searchList()));
    }

    private List<Player> searchList() {
        return logic.getPlayerList().stream().filter(input ->
        {return input.getName().toLowerCase().contains(cerca.getText().toLowerCase());}).collect(Collectors.toList());
    }

    protected void changeDimensions() {
        double quarterWidth = Window.getWidth()/4;

        name.setPrefWidth(quarterWidth);
        victories.setPrefWidth(quarterWidth);
        defeats.setPrefWidth(quarterWidth);
        rate.setPrefWidth(quarterWidth);

        smallAncor.setPrefWidth(quarterWidth);

        uscita.setPrefWidth(Window.getWidth()/6);




        /*uscita.setLayoutX(table.getLayoutX() + (table.getWidth() * 0.15) + table.getWidth());
        uscita.setLayoutY(Window.getHeight()/2 );
        smallAncor.setLayoutX(table.getLayoutX() + (table.getWidth() * 0.05) + table.getWidth());
        smallAncor.setLayoutY(Window.getHeight()/2 - table.getHeight()/5);*/

    }

    public void initialize() {
        table.setItems(FXCollections.observableList(logic.getPlayerList()));
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        victories.setCellValueFactory(new PropertyValueFactory<Player,Integer>("wins"));
        defeats.setCellValueFactory(new PropertyValueFactory<Player, Integer>("losses"));
        rate.setCellValueFactory(new PropertyValueFactory<Player, Double>("winRate"));

        //Languages
        lTesto.setText(logic.getString("leaderboard"));
        name.setText(logic.getString("Name"));
        victories.setText(logic.getString("Victories"));
        defeats.setText(logic.getString("Defeats"));
        rate.setText(logic.getString("Winrate"));
        cerca.setPromptText(logic.getString("Search"));
        uscita.setText(logic.getString("Exit"));

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        Window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        Window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }
}