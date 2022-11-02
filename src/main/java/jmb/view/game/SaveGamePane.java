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
import static jmb.view.View.logic;

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
        if (!logic.allDiceUsed())
            errorLabel.setText(logic.getString("saveErrorCompleteMoves"));
        else if (saveTextField.getText().equals(""))
            errorLabel.setText(logic.getString("saveErrorNoName"));
        else if (logic.isSaveNamePresent(saveTextField.getText()))
            errorLabel.setText(saveTextField.getText() + " " + logic.getString("errorAlreadyPresent"));
        else {
            logic.saveGame(saveTextField.getText());
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
        saveDialogue.setText(logic.getString("saveDialogueTitle"));
        saveLabel.setText(logic.getString("saveName"));
        errorLabel.setText(logic.getString("saveError"));
        saveButton.setText(logic.getString("confirm"));
        closeSaveButton.setText(logic.getString("cancel"));
        saveTextField.setPromptText(logic.getString("savePrompt"));
        if (logic.isLanguageRightToLeft(logic.getSetting("General", "language", String.class))) {
            saveLabel.setLayoutX(saveTextField.getLayoutX() + 40);
            saveTextField.setLayoutX(saveLabel.getLayoutX() + 50);
        }
    }

    public void changeDimensions() {
        saveDialogue.setLayoutX(gameView.getWindowPane().getWidth() / 2 - saveDialogue.getPrefWidth() / 2);
        saveDialogue.setLayoutY(gameView.getWindowPane().getHeight() / 2 - saveDialogue.getPrefHeight() / 2);
    }

}