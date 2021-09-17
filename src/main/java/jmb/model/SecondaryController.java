package jmb.model;

import java.io.IOException;
import javafx.fxml.FXML;
import jmb.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}