package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jmb.logic.Logic;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.MAIN_MENU;
import static jmb.view.ConstantsView.PLAY_GAME;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public class LogIn implements GenericGUI {

    @FXML
    private AnchorPane window;
    @FXML
    private TitledPane playerNamesTitlePane;
    @FXML
    private TitledPane gameSettingsTitlePane;
    @FXML
    private Button confirmButton;
    @FXML
    private Button exitButton;
    @FXML
    private Circle whitePlayerPawn;
    @FXML
    private Circle blackPlayerPawn;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<String> whitePlayerNameBox;
    @FXML
    private ComboBox<String> blackPlayerNameBox;
    @FXML
    private RadioButton noTimerRadio;
    @FXML
    private RadioButton mediumTimerRadio;
    @FXML
    private RadioButton hardTimerRadio;
    @FXML
    private RadioButton customTimerRadio;
    @FXML
    private TextField customTimerField;
    @FXML
    private CheckBox tournamentCheckBox;
    @FXML
    private Label tournamentLabel;
    @FXML
    private TitledPane tournamentPanel;
    @FXML
    private AnchorPane GTT;
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

    private static final double ANIMATION_DURATION = 0.35;

    @FXML
    void savePlayer(ActionEvent event) {
        if (!customTimerField.isDisable() && !logic.isParsable(customTimerField.getText())) {
            if(!logic.getSetting("Audio", "muteSFX", boolean.class))
                view.playSFX(SFX.ERROR);
            errorLabel.setText(logic.getString("errorWrongTimerFormat"));
            errorLabel.setVisible(true);
        } else {
            if (!customTimerField.isDisable())
                logic.setTurnDuration(Integer.parseInt(customTimerField.getText()));
            int nameCheckResult = logic.compareNameLists(whitePlayerNameBox.getValue(), blackPlayerNameBox.getValue());
            if (nameCheckResult == SUCCESS) {
                logic.setUpNewBoard();
                if (tournamentCheckBox.isSelected())
                    logic.setPlayersForGame(whitePlayerNameBox.getValue(), blackPlayerNameBox.getValue(), tournamentSpinner.getValue().intValue());
                else
                    logic.setPlayersForGame(whitePlayerNameBox.getValue(), blackPlayerNameBox.getValue());
                App.changeRoot(PLAY_GAME);
            }
            else errorHandler(nameCheckResult);
        }
    }

    private void errorHandler(int errorType) {
        String errorMessage = null;
        switch (errorType) {
            case SAME_NAME_ERROR:
                errorMessage = logic.getString("errorSameName");
                break;
            case EMPTY_NAMES_ERROR:
                errorMessage = logic.getString("errorEmptyName");
                break;
            case NAME1_ALREADY_PRESENT:
                errorMessage = logic.getString("error") + " " + whitePlayerNameBox.getValue().stripTrailing() +
                        " " + logic.getString("errorAlreadyPresent");
                break;
            case NAME2_ALREADY_PRESENT:
                errorMessage = logic.getString("error") + " " + blackPlayerNameBox.getValue().stripTrailing() +
                        " " + logic.getString("errorAlreadyPresent");
                break;
        }
        errorLabel.setText(errorMessage);
        if(!logic.getSetting("Audio", "muteSFX", boolean.class))
            view.playSFX(SFX.ERROR);
        errorLabel.setVisible(true);
    }

    @FXML
    void setCanRevert() {
        logic.setCanRevert(revertCheckBox.isSelected());
    }

    @FXML
    void goToMainMenu() {
        App.changeRoot(MAIN_MENU);
    }

    @FXML
    void easyTimer(ActionEvent event) {
        customTimerField.setDisable(true);
        logic.setTurnDuration(0);
    }

    @FXML
    void mediumTimer(ActionEvent event) {
        customTimerField.setDisable(true);
        logic.setTurnDuration(120);
    }

    @FXML
    void hardTimer(ActionEvent event) {
        customTimerField.setDisable(true);
        logic.setTurnDuration(30);
    }

    @FXML
    void customTimer(ActionEvent event) {
        customTimerField.setDisable(false);
    }

    @FXML
    void toggleSpinner(ActionEvent event) {
        tournamentLabel.setDisable(!tournamentLabel.isDisable());
        tournamentSpinner.setDisable(!tournamentSpinner.isDisable());
    }

    ToggleGroup group = new ToggleGroup();

    public void initialize() {
        double xdispinner = tournamentSpinner.getLayoutX();
        double xditlb = tournamentLabel.getLayoutX();

        group = new ToggleGroup();
        noTimerRadio.setToggleGroup(group);
        hardTimerRadio.setToggleGroup(group);
        mediumTimerRadio.setSelected(true);
        mediumTimerRadio.setToggleGroup(group);
        customTimerRadio.setToggleGroup(group);

        whitePlayerPawn.setFill(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
        whitePlayerPawn.setStroke(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
        blackPlayerPawn.setFill(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
        blackPlayerPawn.setStroke(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));

        ObservableList<String> nameList = FXCollections.observableList(logic.getPlayerNameList());
        whitePlayerNameBox.setItems(nameList);
        blackPlayerNameBox.setItems(nameList);

        tournamentSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10));

        confirmButton.setText(logic.getString("confirm"));
        exitButton.setText(logic.getString("cancel"));
        whitePlayerNameBox.setPromptText(logic.getString("player") + " 1");
        blackPlayerNameBox.setPromptText(logic.getString("player") + " 2");
        playerNamesTitlePane.setText(logic.getString("playerNames"));
        gameSettingsTitlePane.setText(logic.getString("gameSettings"));
        noTimerRadio.setText(logic.getString("none"));
        mediumTimerRadio.setText("2 " + logic.getString("minutes"));
        hardTimerRadio.setText("30 " + logic.getString("seconds"));
        customTimerField.setPromptText(logic.getString("seconds"));
        easyText.setText(logic.getString("easy"));
        mediumText.setText(logic.getString("medium"));
        hardText.setText(logic.getString("hard"));
        customText.setText(logic.getString("Custom"));
        tournamentPanel.setText(logic.getString("tournament"));
        tournamentCheckBox.setText(logic.getString("activate"));
        tournamentLabel.setText(logic.getString("target"));
        revertCheckBox.setText(logic.getString("revertMove"));

        gameSettingsTitlePane.expandedProperty().addListener((obs, oldVal, newVal) -> {
            titledPaneAnimation(newVal);
        });

        //if (logic.getSetting("General", "language", String.class).equals("AR")) {
        if (logic.isLanguageRightToLeft(logic.getSetting("General", "language", String.class))) {
            tournamentLabel.setLayoutX(xdispinner);
            tournamentSpinner.setLayoutX(xditlb);
        }

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

    private void titledPaneAnimation(boolean opens) {
        if (opens)
            lowerTournamentPanel();
        else raiseTournamentPanel();
    }

    private void lowerTournamentPanel() {
        double currentY = tournamentPanel.getLayoutY();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                new KeyValue(tournamentPanel.layoutYProperty(), currentY)),
                new KeyFrame(Duration.seconds(ANIMATION_DURATION),
                        e -> settingsPanelOpened = true,
                        new KeyValue(tournamentPanel.layoutYProperty(), currentY + 106)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void raiseTournamentPanel() {
        double currentY = tournamentPanel.getLayoutY();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                new KeyValue(tournamentPanel.layoutYProperty(), currentY)),
                new KeyFrame(Duration.seconds(ANIMATION_DURATION),
                        e -> settingsPanelOpened = false,
                        new KeyValue(tournamentPanel.layoutYProperty(), currentY - 106)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void changeDimensions() {
        double panelWidth = 383;
        double regHeight = 191;
        double gtMaxHeight = 132;
        double gtMinHeight = 26;
        double tpHeight = 80;

        double panelX = window.getWidth() / 2 - panelWidth / 2;

        playerNamesTitlePane.setLayoutX(panelX);
        gameSettingsTitlePane.setLayoutX(panelX);
        tournamentPanel.setLayoutX(panelX);
        double regY = (window.getHeight() - regHeight - gtMaxHeight - tpHeight) / 2;
        playerNamesTitlePane.setLayoutY(regY);
        gameSettingsTitlePane.setLayoutY(regY + regHeight);
        double gtHeight;
        if (settingsPanelOpened)
            gtHeight = gtMaxHeight;
        else gtHeight = gtMinHeight;
        tournamentPanel.setLayoutY(regY + regHeight + gtHeight);

    }
}


