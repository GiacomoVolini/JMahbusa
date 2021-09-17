package jmb.model;

import java.io.IOException;
import javafx.fxml.FXML;
import jmb.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
