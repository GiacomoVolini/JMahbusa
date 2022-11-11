package jmb.view.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import jmb.view.App;
import jmb.view.GameView;

import static jmb.view.ConstantsView.MAIN_MENU;
import static jmb.view.View.*;

public class PausePane {

    @FXML
    private Button cancelButton, exitAndSave, exitWithoutSave;

    @FXML
    private TitledPane pauseMenu;

    @FXML
    private Text pauseText;
    private GameView gameView;
    private SaveGamePane saveGamePane;

    @FXML
    void closeExitOption(ActionEvent event) {
        gameView.getTurnTimer().play();
        handleExitOption(false, gameView.getWindowPane());
    }

    @FXML
    void exitAndSave(ActionEvent event) {
        saveGamePane.setVisible(true);
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        App.changeRoot(MAIN_MENU);
    }

    public void openExitOption() {
        handleExitOption(true, exitWithoutSave);
    }

    public void closeExitOption() {
        handleExitOption(false, gameView.getWindowPane());
    }

    private void handleExitOption (boolean open, Node node) {
        pauseMenu.setVisible(open);
        gameView.disableMenuAndFinishButtons(open);
        node.requestFocus();
    }

    public boolean isVisible() {
        return pauseMenu.isVisible();
    }

    public void initialize(GameView parent, SaveGamePane saveGamePane) {
        gameView = parent;
        this.saveGamePane = saveGamePane;
        pauseMenu.setText(getLogic().getString("pause"));
        pauseText.setText(getLogic().getString("pausePrompt"));
        exitWithoutSave.setText(getLogic().getString("exitNoSave"));
        exitAndSave.setText(getLogic().getString("exitAndSave"));
        cancelButton.setText(getLogic().getString("cancel"));
        pauseMenu.setViewOrder(-4);
    }
    
    public void changeDimensions() {
        pauseMenu.setLayoutX(gameView.getWindowPane().getWidth() / 2 - pauseMenu.getWidth() / 2);
        pauseMenu.setLayoutY(gameView.getWindowPane().getHeight() / 2 - pauseMenu.getHeight() / 2);
        if (pauseMenu.isVisible())
            exitWithoutSave.requestFocus();
    }

}