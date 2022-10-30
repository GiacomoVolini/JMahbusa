package jmb.view.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jmb.view.SettingsView;

import static jmb.view.View.logic;

public class VideoTab {

    @FXML private TitledPane videoTitlePane;
    @FXML private CheckBox fullscreenCheck, lockResolutionCheck;
    @FXML private TextField resolutionHeightField;
    @FXML private TextField resolutionWidthField;
    @FXML private Text resolutionHeightText, resolutionText, resolutionWidthText;
    @FXML private AnchorPane videoAnchorPane;
    private SettingsView settingsView;

    public void setSettingsView(SettingsView sv) {
        settingsView = sv;
    }

    @FXML
    void changeResolution(KeyEvent event) {
        if(logic.isParsable(resolutionWidthField.getText()) && logic.isParsable(resolutionHeightField.getText())) {
            logic.setSetting("Video", "resolutionWidth", Integer.parseInt(resolutionWidthField.getText()));
            logic.setSetting("Video", "resolutionHeight", Integer.parseInt(resolutionHeightField.getText()));
            settingsView.getApplyButton().setDisable(false);
        }
    }

    @FXML
    void fullscreen(ActionEvent event) {
        boolean fullScreen = fullscreenCheck.isSelected();
        settingsView.getStage().setFullScreen(fullScreen);
        settingsView.getApplyButton().setDisable(false);
        resolutionWidthField.setDisable(fullScreen);
        resolutionHeightField.setDisable(fullScreen);
    }

    @FXML
    void lockResolution(ActionEvent event) {
        settingsView.getStage().setResizable(!lockResolutionCheck.isSelected());
        settingsView.getApplyButton().setDisable(false);
        setResolutionFieldsEditability();
    }
    private void setResolutionFieldsEditability() {
        boolean set = lockResolutionCheck.isSelected() || fullscreenCheck.isSelected();
        resolutionWidthField.setDisable(set);
        resolutionHeightField.setDisable(set);
    }

    public void loadStrings() {
        resolutionText.setText(logic.getString("Resolution"));
        resolutionWidthText.setText(logic.getString("Length"));
        resolutionHeightText.setText(logic.getString("Height"));
        fullscreenCheck.setText(logic.getString("Fullscreen"));
        lockResolutionCheck.setText(logic.getString("BlockResolution"));
    }

    public void setVisible(boolean set) {
        videoTitlePane.setVisible(set);
        videoAnchorPane.setMouseTransparent(!set);
    }

    public void setDisable (boolean set) {
        videoAnchorPane.setDisable(set);
    }

    public void applySettings() {
        Stage stage = settingsView.getStage();
        logic.setSetting("Video", "fullScreen",fullscreenCheck.isSelected());
        logic.setSetting("Video", "lockResolution",lockResolutionCheck.isSelected());
        logic.setSetting("Video", "resolutionWidth",Integer.parseInt(resolutionWidthField.getText()));
        stage.setWidth(logic.getSetting("Video", "resolutionWidth", int.class));
        logic.setSetting("Video", "resolutionHeight",Integer.parseInt(resolutionHeightField.getText()));
        stage.setHeight(logic.getSetting("Video", "resolutionHeight", int.class));
    }

    public void resetWindow() {
        fullscreen(null);
        lockResolution(null);
    }

    public void loadSettings() {
        fullscreenCheck.setSelected(logic.getSetting("Video", "fullScreen", boolean.class));
        lockResolutionCheck.setSelected(logic.getSetting("Video", "lockResolution", boolean.class));
        resolutionWidthField.setText(String.valueOf(logic.getSetting("Video", "resolutionWidth", int.class)));
        resolutionHeightField.setText(String.valueOf(logic.getSetting("Video", "resolutionHeight", int.class)));
    }

    public void initialize() {
        setResolutionFieldsEditability();
    }

    public void changeDimensions() {
        AnchorPane window = settingsView.getWindowPane();
        if (logic.isLanguageRightToLeft(logic.getCurrentlanguage())) {
            AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.40);
            AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.20);
            AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.20);
            AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.40);
            AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.40);
        } else {
            AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.13);
            AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.13);
            AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.28);
            AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.28);
        }

        AnchorPane.setLeftAnchor(fullscreenCheck, window.getWidth() * 0.10);
        AnchorPane.setLeftAnchor(lockResolutionCheck, window.getWidth() * 0.4);

        videoAnchorPane.setLayoutX(window.getWidth()/4);
        videoTitlePane.setLayoutX(window.getWidth()/4);
        videoAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        videoTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        videoAnchorPane.setPrefHeight(window.getHeight());
        videoTitlePane.setPrefHeight(window.getHeight());

        resolutionHeightField.setText(String.valueOf((int) settingsView.getStage().getHeight()));
        resolutionWidthField.setText(String.valueOf((int) settingsView.getStage().getWidth()));

    }

}