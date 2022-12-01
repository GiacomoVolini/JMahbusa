package jmb.view.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jmb.view.SettingsView;

import static jmb.view.View.getLogic;

public class VideoTab {

    @FXML private TitledPane videoTitlePane;
    @FXML private CheckBox fullscreenCheck, lockResolutionCheck;
    @FXML private TextField resolutionHeightField;
    @FXML private TextField resolutionWidthField;
    @FXML private Text resolutionHeightText, resolutionText, resolutionWidthText;
    @FXML private AnchorPane videoAnchorPane;
    @FXML private ImageView feltImage;
    private SettingsView settingsView;

    public void setSettingsView(SettingsView sv) {
        settingsView = sv;
    }

    @FXML
    void changeResolution(KeyEvent event) {
        if(getLogic().isParsable(resolutionWidthField.getText()) && getLogic().isParsable(resolutionHeightField.getText())) {
            getLogic().setSetting("Video", "resolutionWidth", Integer.parseInt(resolutionWidthField.getText()));
            getLogic().setSetting("Video", "resolutionHeight", Integer.parseInt(resolutionHeightField.getText()));
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
        resolutionText.setText(getLogic().getString("Resolution"));
        resolutionWidthText.setText(getLogic().getString("Length"));
        resolutionHeightText.setText(getLogic().getString("Height"));
        fullscreenCheck.setText(getLogic().getString("Fullscreen"));
        lockResolutionCheck.setText(getLogic().getString("BlockResolution"));
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
        getLogic().setSetting("Video", "fullScreen",fullscreenCheck.isSelected());
        getLogic().setSetting("Video", "lockResolution",lockResolutionCheck.isSelected());
        getLogic().setSetting("Video", "resolutionWidth",Integer.parseInt(resolutionWidthField.getText()));
        stage.setWidth(getLogic().getSetting("Video", "resolutionWidth", int.class));
        getLogic().setSetting("Video", "resolutionHeight",Integer.parseInt(resolutionHeightField.getText()));
        stage.setHeight(getLogic().getSetting("Video", "resolutionHeight", int.class));
    }

    public void resetWindow() {
        fullscreen(null);
        lockResolution(null);
    }

    public void loadSettings() {
        fullscreenCheck.setSelected(getLogic().getSetting("Video", "fullScreen", boolean.class));
        lockResolutionCheck.setSelected(getLogic().getSetting("Video", "lockResolution", boolean.class));
        resolutionWidthField.setText(String.valueOf(getLogic().getSetting("Video", "resolutionWidth", int.class)));
        resolutionHeightField.setText(String.valueOf(getLogic().getSetting("Video", "resolutionHeight", int.class)));
    }

    public void initialize() {
        setResolutionFieldsEditability();
        feltImage.fitHeightProperty().bind(videoAnchorPane.heightProperty());
        feltImage.fitWidthProperty().bind(videoAnchorPane.widthProperty());
        setBackgroundColor(getLogic().getSetting("Customization", "backgroundColor", String.class));
    }

    public void setBackgroundColor(String colorString) {
        videoAnchorPane.setStyle("-fx-background-color: " + colorString);
    }

    public void changeDimensions(AnchorPane window) {
        if (getLogic().isLanguageRightToLeft(getLogic().getCurrentLanguage())) {
            AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.40);
            AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.20);
            AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.20);
            AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.40);
            AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.40);
        } else {
            AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.0925);
            AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.1225);
            AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.1225);
            AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.3);
            AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.3);
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