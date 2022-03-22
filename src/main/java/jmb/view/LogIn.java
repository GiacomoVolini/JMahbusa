package jmb.view;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;
import jmb.model.Player;

import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LogIn {

    @FXML
    private AnchorPane window;

    @FXML
    private TitledPane registrati;

    @FXML
    private Button salvag;

    @FXML
    private Button uscita;

    @FXML
    private Circle nome1;

    @FXML
    private Circle nome2;


    @FXML
    private ComboBox<String> scrivinomi1;

    @FXML
    private ComboBox<String> scrivinomi2;

    @FXML
    void savePlayer(ActionEvent event) throws IOException{

        System.out.println("Hai pigiato il pulsante");

            switch (logic.compareNameLists(scrivinomi1.getValue(), scrivinomi2.getValue())){
                case SUCCESS:
                    logic.addNewPlayersToList(scrivinomi1.getValue(), scrivinomi2.getValue());
                    jmb.App.board();
                    break;
                case SAME_NAME_ERROR:
                    System.out.println("i nomi sono uguali");
                    break;
                case EMPTY_NAMES_ERROR:
                    System.out.println("almeno un nome vuoto");
                    break;
                case NAME1_ALREADY_PRESENT:
                    System.out.println("nome 1 gia presente");
                    break;
                case NAME2_ALREADY_PRESENT:
                    System.out.println("nome 2 gia presente");
                    break;

            }

        }

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        jmb.App.MainMenu();
    }

    public void initialize(){

        nome1.setFill(pedIn1);
        nome1.setStroke(pedOut1);
        nome2.setFill(pedIn2);
        nome2.setStroke(pedIn2);

        ObservableList<String> nameList = FXCollections.observableList(logic.getPlayerNameList());
        scrivinomi1.setItems(nameList);
        scrivinomi2.setItems(nameList);

    }

}
