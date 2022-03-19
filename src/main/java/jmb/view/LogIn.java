package jmb.view;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import jmb.model.Player;

import static jmb.view.ConstantsView.*;

import java.io.IOException;
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
    private ComboBox<Player> scrivinomi1;

    @FXML
    private ComboBox<Player> scrivinomi2;


    //todo da forse cancellare
     @FXML
    void scegliNomi(ActionEvent event) {
        //scrivinomi1.setItems(View.logic.getPlayerList());
        //scrivinomi1.setItems(View.logic.getPlayerList().filtered(Objects::nonNull));
    }

    @FXML
    void slavagiocatore(ActionEvent event){
        if (scrivinomi1.getValue() == null || scrivinomi2.getValue() == null) {

            //  TODO manda un messaggio

        }else{
            /* TODO aggiugi il nome sulla lista
            ObjectProperty<StringConverter<Player>>
            scrivinomi1.converterProperty();
            View.logic.getPlayerList().add(scrivinomi1.getValue());
            View.logic.getPlayerList().add(scrivinomi2.getValue());*/
        }
            /*if(scrivinomi2.getText().equals(scrivinomi2.getText())){
          //  n2 = scrivinomi2.getText();
            salvag.getScene().getWindow();
            jmb.App.board();
        }else if (scrivinomi2.getText().equals(scrivinomi2.getText()) && scrivinomi1.getText().equals("")){
            vaialcalssifica.getScene().getWindow();
            jmb.App.leaderBoard();
        }*/
    }

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        jmb.App.MainMenu();
    }

    public void initialize(){
        scrivinomi1.setItems(View.logic.getPlayerNameList());
        scrivinomi2.setItems(View.logic.getPlayerNameList());

    }

}
