package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import java.io.IOException;

public class LogIn {

    @FXML
    private AnchorPane window;

    @FXML
    private TitledPane registrati;

    @FXML
    private AnchorPane registrati2;

    @FXML
    private TitledPane GT;

    @FXML
    private AnchorPane GT2;

    @FXML
    private Button salvag;

    @FXML
    private Button uscita;

    @FXML
    private Circle nome1;

    @FXML
    private Circle nome2;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> scrivinomi1;

    @FXML
    private ComboBox<String> scrivinomi2;

    @FXML
    private RadioButton noT;

    @FXML
    private RadioButton media;

    @FXML
    private RadioButton defficile;

    @FXML
    private RadioButton scelta;

    @FXML
    private TextField oPt;

    @FXML
    private CheckBox tournamentCheckBox;

    @FXML
    private Label tournamentLabel;

    @FXML
    private TitledPane tournamentPanel;

    @FXML
    private Spinner<Integer> tournamentSpinner;

    @FXML
    void savePlayer(ActionEvent event) throws IOException{

        if(scelta.isSelected()){
            System.out.println(Integer.parseInt(oPt.getText()));
            defficile.setSelected(false);
            media.setSelected(false);
            noT.setSelected(false);
            oPt.setDisable(false);
            turn_duration = Integer.parseInt(oPt.getText());

        }

            switch (logic.compareNameLists(scrivinomi1.getValue(), scrivinomi2.getValue())){
                case SUCCESS:
                    if (tournamentCheckBox.isSelected())
                        logic.setPlayersForGame(scrivinomi1.getValue(), scrivinomi2.getValue(), tournamentSpinner.getValue().intValue());
                    else
                        logic.setPlayersForGame(scrivinomi1.getValue(), scrivinomi2.getValue());
                    jmb.App.board();
                    break;
                case SAME_NAME_ERROR:
                    errorLabel.setText("ERRORE: I due giocatori hanno lo stesso nome");
                    errorLabel.setVisible(true);
                    break;
                case EMPTY_NAMES_ERROR:
                    errorLabel.setText("ERRORE: Almeno uno dei due nomi è vuoto");
                    errorLabel.setVisible(true);
                    break;
                case NAME1_ALREADY_PRESENT:
                    errorLabel.setText("ERRORE: " + scrivinomi1.getValue().stripTrailing() + "  è già presente nella lista");
                    errorLabel.setVisible(true);
                    break;
                case NAME2_ALREADY_PRESENT:
                    errorLabel.setText("ERRORE: " + scrivinomi2.getValue().stripTrailing() + " è già presente nella lista");
                    errorLabel.setVisible(true);
                    break;
            }
    }

    @FXML
    void vaialMainMenu()  throws IOException {
        uscita.getScene().getWindow();
        jmb.App.MainMenu();
    }

    @FXML
    void easyChance(ActionEvent event) {
        noT.setSelected(true);
        defficile.setSelected(false);
        media.setSelected(false);
        scelta.setSelected(false);
        oPt.setDisable(true);
        turn_duration = 0;
    }

    @FXML
    void medioChance(ActionEvent event) {
        noT.setSelected(false);
        defficile.setSelected(false);
        media.setSelected(true);
        scelta.setSelected(false);
        oPt.setDisable(true);
        turn_duration = 120;
    }

    @FXML
    void hardChance(ActionEvent event) {
        noT.setSelected(false);
        defficile.setSelected(true);
        media.setSelected(false);
        scelta.setSelected(false);
        oPt.setDisable(true);
        turn_duration = 30;
    }

    @FXML
    void chanCe(ActionEvent event) {
        defficile.setSelected(false);
        media.setSelected(false);
        noT.setSelected(false);
        scelta.setSelected(true);
        oPt.setDisable(false);
    }

    @FXML
    void toggleSpinner(ActionEvent event) {
        tournamentLabel.setDisable(!tournamentLabel.isDisable());
        tournamentSpinner.setDisable(!tournamentSpinner.isDisable());
    }

    ToggleGroup group = new ToggleGroup();

    public void initialize() {

        group = new ToggleGroup();
        noT.setToggleGroup(group);
        defficile.setToggleGroup(group);
        media.setSelected(true);
        media.setToggleGroup(group);
        scelta.setToggleGroup(group);

        nome1.setFill(pedIn1);
        nome1.setStroke(pedOut1);
        nome2.setFill(pedIn2);
        nome2.setStroke(pedIn2);

        ObservableList<String> nameList = FXCollections.observableList(logic.getPlayerNameList());
        scrivinomi1.setItems(nameList);
        scrivinomi2.setItems(nameList);

        tournamentSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10));

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

    protected void changeDimensions() {

        double panelX = window.getWidth()/2 - registrati.getWidth()/2;

        registrati.setLayoutX(panelX);
        GT.setLayoutX(panelX);
        tournamentPanel.setLayoutX(panelX);

        registrati.setLayoutY((window.getHeight() - registrati.getHeight() - GT.getHeight() - tournamentPanel.getHeight())/2);
        GT.setLayoutY(registrati.getLayoutY() + registrati.getHeight());
        tournamentPanel.setLayoutY(GT.getLayoutY()+GT.getHeight());


    }

}


