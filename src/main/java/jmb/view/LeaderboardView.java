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
import javafx.scene.layout.AnchorPane;
import jmb.model.Logic;
import jmb.model.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static jmb.App.getStage;
import static jmb.view.ConstantsView.cb;
import static jmb.view.View.logic;

public class LeaderboardView {

    @FXML
    private AnchorPane Window;

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

    //todo le dimensioni del bottone (searchBTN)
    @FXML
    private Button searchBTN;

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        jmb.App.MainMenu();
        getStage().setFullScreen(cb);
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

        table.setLayoutX(Window.getWidth()/2 - table.getWidth()/2);
        table.setLayoutY(Window.getHeight()/2 - table.getHeight()/2);
        uscita.setLayoutX(table.getLayoutX());
        uscita.setLayoutY(table.getLayoutY() - 25);
        smallAncor.setLayoutX(table.getLayoutX() + 376);
        smallAncor.setLayoutY(table.getLayoutY() - 25);


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

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        Window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        Window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }
}