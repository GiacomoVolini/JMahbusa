package jmb.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static jmb.view.ConstantsView.*;

import java.io.IOException;

public class LogIn {

    @FXML
    private AnchorPane window;

    @FXML
    private TitledPane registrati;

    @FXML
    private Button salvag;

    @FXML
    private Button vaialcalssifica;

    @FXML
    private Button uscita;

    @FXML
    private TextField scrivinomi1;

    @FXML
    private TextField scrivinomi2;

    @FXML
    private Label failed;

    @FXML
    void apricalssifica()  throws IOException {
        if (scrivinomi1.getText().equals("")){
            scrivinomi1.setVisible(false);
            scrivinomi1.setMouseTransparent(true);
            scrivinomi2.setVisible(true);
            scrivinomi2.setMouseTransparent(false);
        }else if (scrivinomi1.getText().equals("") && scrivinomi2.getText().equals(scrivinomi2.getText())){
        vaialcalssifica.getScene().getWindow();
        jmb.App.leaderBoard();
    }}

    @FXML
    void slavagiocatore()  throws IOException {
        if (scrivinomi1.getText().equals(scrivinomi1.getText())) {
            scrivinomi1.setVisible(false);
            scrivinomi1.setMouseTransparent(true);
            scrivinomi2.setVisible(true);
            scrivinomi2.setMouseTransparent(false);
            //  TODO controllare se va bene "salvare in calssifica"
          //  n1 = scrivinomi1.getText();
        }else if(scrivinomi2.getText().equals(scrivinomi2.getText())){
          //  n2 = scrivinomi2.getText();
            salvag.getScene().getWindow();
            jmb.App.board();
        }else if (scrivinomi2.getText().equals(scrivinomi2.getText()) && scrivinomi1.getText().equals("")){
            vaialcalssifica.getScene().getWindow();
            jmb.App.leaderBoard();
        }
    }

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        jmb.App.MainMenu();
    }

}
