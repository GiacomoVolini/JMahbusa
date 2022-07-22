package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TitledPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

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
    private Text easyText;
    @FXML
    private Text mediumText;
    @FXML
    private Text hardText;
    @FXML
    private Text customText;
    @FXML
    private CheckBox revertCheckBox;

    private boolean settingsPanelOpened = false;

    private int whoCalled = GAME_CALLED;
    private static final double ANIMATION_DURATION = 0.35;

    @FXML
    void savePlayer(ActionEvent event) {
        if(scelta.isSelected()){
            defficile.setSelected(false);
            media.setSelected(false);
            noT.setSelected(false);
            oPt.setDisable(false);
        }

            switch (logic.compareNameLists(scrivinomi1.getValue(), scrivinomi2.getValue())){
                case SUCCESS:
                    logic.setUpNewBoard(whoCalled);
                    if (tournamentCheckBox.isSelected())
                        logic.setPlayersForGame(scrivinomi1.getValue(), scrivinomi2.getValue(), tournamentSpinner.getValue().intValue());
                    else
                        logic.setPlayersForGame(scrivinomi1.getValue(), scrivinomi2.getValue());
                    App.changeRoot(PLAY_GAME);
                    break;
                case SAME_NAME_ERROR:
                    errorLabel.setText(logic.getString("errorSameName"));
                    errorLabel.setVisible(true);
                    break;
                case EMPTY_NAMES_ERROR:
                    errorLabel.setText(logic.getString("errorEmptyName"));
                    errorLabel.setVisible(true);
                    break;
                case NAME1_ALREADY_PRESENT:
                    errorLabel.setText(logic.getString("error")+" " + scrivinomi1.getValue().stripTrailing() + " " + logic.getString("errorAlreadyPresent"));
                    errorLabel.setVisible(true);
                    break;
                case NAME2_ALREADY_PRESENT:
                    errorLabel.setText(logic.getString("error")+" " + scrivinomi2.getValue().stripTrailing() + " " + logic.getString("errorAlreadyPresent"));
                    errorLabel.setVisible(true);
                    break;
            }
    }
    @FXML
    void setCanRevert() {
        logic.setCanRevert(revertCheckBox.isSelected());
    }

    @FXML
    void vaialMainMenu() {
        uscita.getScene().getWindow();
        App.changeRoot(MAIN_MENU);
    }

    @FXML
    void easyChance(ActionEvent event) {
        noT.setSelected(true);
        defficile.setSelected(false);
        media.setSelected(false);
        scelta.setSelected(false);
        oPt.setDisable(true);
        logic.setTurnDuration(0);
    }

    @FXML
    void medioChance(ActionEvent event) {
        noT.setSelected(false);
        defficile.setSelected(false);
        media.setSelected(true);
        scelta.setSelected(false);
        oPt.setDisable(true);
        logic.setTurnDuration(120);
    }

    @FXML
    void hardChance(ActionEvent event) {
        noT.setSelected(false);
        defficile.setSelected(true);
        media.setSelected(false);
        scelta.setSelected(false);
        oPt.setDisable(true);
        logic.setTurnDuration(30);
    }

    @FXML
    void chanCe(ActionEvent event) {
        defficile.setSelected(false);
        media.setSelected(false);
        noT.setSelected(false);
        scelta.setSelected(true);
        oPt.setDisable(false);
        logic.setTurnDuration(Integer.parseInt(oPt.getText()));
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

        nome1.setFill(Color.web(logic.getWhitePawnFill()));
        nome1.setStroke(Color.web(logic.getWhitePawnStroke()));
        nome2.setFill(Color.web(logic.getBlackPawnFill()));
        nome2.setStroke(Color.web(logic.getBlackPawnStroke()));

        ObservableList<String> nameList = FXCollections.observableList(logic.getPlayerNameList());
        scrivinomi1.setItems(nameList);
        scrivinomi2.setItems(nameList);

        tournamentSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10));

        salvag.setText(logic.getString("confirm"));
        uscita.setText(logic.getString("cancel"));
        scrivinomi1.setPromptText(logic.getString("player") + " 1");
        scrivinomi2.setPromptText(logic.getString("player") + " 2");
        registrati.setText(logic.getString("playerNames"));
        GT.setText(logic.getString("turnTimer"));
        noT.setText(logic.getString("none"));
        media.setText("2 " + logic.getString("minutes"));
        defficile.setText("30 "+ logic.getString("seconds"));
        oPt.setPromptText(logic.getString("seconds"));
        easyText.setText(logic.getString("easy"));
        mediumText.setText(logic.getString("medium"));
        hardText.setText(logic.getString("hard"));
        customText.setText(logic.getString("custom"));
        tournamentPanel.setText(logic.getString("tournament"));
        tournamentCheckBox.setText(logic.getString("activate"));
        tournamentLabel.setText(logic.getString("target"));

        GT.expandedProperty().addListener((obs, oldVal, newVal) -> {
            titledPaneAnimation(newVal);
        });

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

    private void titledPaneAnimation (boolean opens) {
        if (opens)
            lowerTournamentPanel();
        else raiseTournamentPanel();
    }

    private void lowerTournamentPanel() {
        double currentY = tournamentPanel.getLayoutY();
        Timeline timeline = new Timeline (new KeyFrame(Duration.ZERO,
                new KeyValue(tournamentPanel.layoutYProperty(), currentY)),
                new KeyFrame(Duration.seconds(ANIMATION_DURATION),
                        e -> settingsPanelOpened = true,
                        new KeyValue(tournamentPanel.layoutYProperty(), currentY + 106)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void raiseTournamentPanel() {
        double currentY = tournamentPanel.getLayoutY();
        Timeline timeline = new Timeline (new KeyFrame(Duration.ZERO,
                new KeyValue(tournamentPanel.layoutYProperty(), currentY)),
                new KeyFrame(Duration.seconds(ANIMATION_DURATION),
                        e -> settingsPanelOpened = false,
                        new KeyValue(tournamentPanel.layoutYProperty(), currentY - 106)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void changeDimensions() {
        double panelWidth = 383;
        double regHeight = 191;
        double gtMaxHeight = 132;
        double gtMinHeight = 26;
        double tpHeight = 80;


        double panelX = window.getWidth()/2 - panelWidth/2;

        registrati.setLayoutX(panelX);
        GT.setLayoutX(panelX);
        tournamentPanel.setLayoutX(panelX);
        double regY = (window.getHeight() - regHeight - gtMaxHeight - tpHeight)/2;
        registrati.setLayoutY(regY);
        GT.setLayoutY(regY + regHeight);
        double gtHeight;
        if (settingsPanelOpened)
            gtHeight = gtMaxHeight;
        else gtHeight = gtMinHeight;
        tournamentPanel.setLayoutY(regY + regHeight + gtHeight);
    }

}


