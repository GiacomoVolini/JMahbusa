package jmb.view.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import jmb.view.App;
import jmb.view.GameView;

import static jmb.view.ConstantsView.MAIN_MENU;
import static jmb.view.View.*;

public class SaveGamePane {

    @FXML
    private Button closeSaveButton, saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane saveAnchorPane;

    @FXML
    private TitledPane saveDialogue;

    @FXML
    private Text saveLabel;

    @FXML
    private TextField saveTextField;

    private GameView gameView;

    @FXML
    void closeSaveDialogue(ActionEvent event) {
        saveTextField.setText("");
        errorLabel.setVisible(false);
        saveDialogue.setVisible(false);
    }

    @FXML
    void saveGame(ActionEvent event) {
        errorLabel.setVisible(true);
        if (!getLogic().allDiceUsed())
            errorLabel.setText(getLogic().getString("saveErrorCompleteMoves"));
        else if (saveTextField.getText().equals(""))
            errorLabel.setText(getLogic().getString("saveErrorNoName"));
        else if (getLogic().isSaveNamePresent(saveTextField.getText()))
            errorLabel.setText(saveTextField.getText() + " " + getLogic().getString("errorAlreadyPresent"));
        else {
            getLogic().saveGame(saveTextField.getText());
            errorLabel.setVisible(false);
            App.changeRoot(MAIN_MENU);
        }
    }

    public void setVisible(boolean set) {
        saveDialogue.setVisible(true);
        saveDialogue.setViewOrder(-50);
    }

    public void initialize(GameView parent) {
        gameView = parent;
        saveDialogue.setText(getLogic().getString("saveDialogueTitle"));
        saveLabel.setText(getLogic().getString("saveName"));
        errorLabel.setText(getLogic().getString("saveError"));
        saveButton.setText(getLogic().getString("confirm"));
        closeSaveButton.setText(getLogic().getString("cancel"));
        saveTextField.setPromptText(getLogic().getString("savePrompt"));
        if (getLogic().isLanguageRightToLeft(getLogic().getSetting("General", "language", String.class))) {
            saveLabel.setLayoutX(saveTextField.getLayoutX() + 40);
            saveTextField.setLayoutX(saveLabel.getLayoutX() + 50);
        }
    }

    public void changeDimensions() {
        saveDialogue.setLayoutX(gameView.getWindowPane().getWidth() / 2 - saveDialogue.getPrefWidth() / 2);
        saveDialogue.setLayoutY(gameView.getWindowPane().getHeight() / 2 - saveDialogue.getPrefHeight() / 2);
    }

}