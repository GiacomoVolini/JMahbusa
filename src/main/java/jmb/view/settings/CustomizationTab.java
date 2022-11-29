package jmb.view.settings;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jmb.view.SettingsView;
import jmb.view.utilities.ColorHandler;
import jmb.view.utilities.TimelineBuilder;

import static jmb.ConstantsShared.*;
import static jmb.view.View.getLogic;

public class CustomizationTab {

    @FXML private TitledPane customizeTitlePane;
    @FXML private AnchorPane customizeAnchorPane, customPane, leftPresetPane, rightPresetPane;
    @FXML private Text backgroundText, blackPawnText, frameText, pawnFillText, pawnStrokeText,
            pointText, tableText, whitePawnText;
    @FXML private Circle blackPawn, whitePawn;
    @FXML private ColorPicker blackPawnFillColorPicker, backGroundColorPicker, blackPawnStrokeColorPicker,
            evenPointColorPicker, frameColorPicker, oddPointColorPicker,
            selectedPointColorPicker, tableColorPicker, whitePawnFillColorPicker,
            whitePawnStrokeColorPicker;
    @FXML private Rectangle customBoard, customFrame, leftPresetBoard, leftPresetFrame;
    @FXML private Polygon customPoint1, customPoint2, customPoint3, leftPresetPoint1,
            leftPresetPoint2, leftPresetPoint3, rightPresetPoint1,
            rightPresetPoint2, rightPresetPoint3;
    @FXML private RadioButton customRadio, leftPresetRadio, rightPresetRadio;
    @FXML private ImageView feltImage;
    private Color selectedPointColor;
    private ToggleGroup group = new ToggleGroup();
    private Timeline selectedPointAnimation;
    private Timeline selectedPointPresetsAnimation;
    private SettingsView settingsView;
    public void setSettingsView(SettingsView sv) {
        settingsView = sv;
    }

    @FXML
    void blackPawnFillChange(ActionEvent event) {
        changeFillColor(blackPawnFillColorPicker.getValue(), blackPawn);
    }

    @FXML
    void blackPawnStrokeChange(ActionEvent event) {
        changeStrokeColor(blackPawnStrokeColorPicker.getValue(), blackPawn);
    }

    @FXML
    void boardColorChange(ActionEvent event) {
        changeBothColors(tableColorPicker.getValue(), customBoard);
    }

    @FXML
    void customPresetAction(ActionEvent event) {
        disableColorPickers(false);
        settingsView.getApplyButton().setDisable(false);
    }

    @FXML
    void evenPointColorChange(ActionEvent event) {
        changeBothColors(evenPointColorPicker.getValue(), customPoint1);
        changeBothColors(evenPointColorPicker.getValue(), customPoint3);
    }

    @FXML
    void frameColorChange(ActionEvent event) {
        changeBothColors(frameColorPicker.getValue(), customFrame);
    }

    @FXML
    void oddPointColorChange(ActionEvent event) {
        changeBothColors(oddPointColorPicker.getValue(), customPoint2);
    }

    @FXML
    void presetAction(ActionEvent event) {
        disableColorPickers(true);
        settingsView.getApplyButton().setDisable(false);
    }

    @FXML
    void selectedBackGroundColorChange(ActionEvent event) {
        if(Color.web(getLogic().getSetting("Customization","backgroundColor",String.class)) != backGroundColorPicker.getValue()) {
            settingsView.getApplyButton().setDisable(false);
            Color color = backGroundColorPicker.getValue();
            settingsView.setBackgroundColors(ColorHandler.buildColorString(color));
        }
    }

    @FXML
    void selectedPointColorChange(ActionEvent event) {
        Color newValue = selectedPointColorPicker.getValue();
        if (newValue!=selectedPointColor) {
            selectedPointColor = newValue;
            regeneratePointAnimation();
            settingsView.getApplyButton().setDisable(false);
        }
    }

    @FXML
    void whitePawnFillChange(ActionEvent event) {
        changeFillColor(whitePawnFillColorPicker.getValue(), whitePawn);
    }

    @FXML
    void whitePawnStrokeChange(ActionEvent event) {
        changeStrokeColor(whitePawnStrokeColorPicker.getValue(), whitePawn);
    }

    private void changeStrokeColor(Color newColor, Shape shape){
        if (!newColor.equals(shape.getStroke())) {
            shape.setStroke(newColor);
            settingsView.getApplyButton().setDisable(false);
        }
    }
    private void changeFillColor(Color newColor, Shape shape){
        if (!newColor.equals(shape.getFill())) {
            shape.setFill(newColor);
            settingsView.getApplyButton().setDisable(false);
        }
    }
    private void changeBothColors(Color newColor, Shape shape) {
        changeStrokeColor(newColor, shape);
        changeFillColor(newColor, shape);
    }

    public void stopAnimations() {
        selectedPointAnimation.stop();
        selectedPointPresetsAnimation.stop();
    }

    public void setDisable (boolean set) {
        customizeAnchorPane.setDisable(false);
    }

    public void setVisible (boolean set) {
        customizeTitlePane.setVisible(set);
        customizeAnchorPane.setMouseTransparent(!set);
    }

    private void regeneratePointAnimation() {
        if (selectedPointAnimation != null)
            selectedPointAnimation.stop();
        selectedPointPresetsAnimation.stop();
        selectedPointAnimation = TimelineBuilder.buildCustomAnimation(customPoint1, customPoint2,
                customPoint3, selectedPointColorPicker, evenPointColorPicker, oddPointColorPicker);
        selectedPointAnimation.setCycleCount(Animation.INDEFINITE);
        selectedPointAnimation.play();
        selectedPointPresetsAnimation.play();
    }

    private void disableColorPickers(boolean set) {
        tableColorPicker.setDisable(set);
        evenPointColorPicker.setDisable(set);
        oddPointColorPicker.setDisable(set);
        selectedPointColorPicker.setDisable(set);
        frameColorPicker.setDisable(set);
    }
    public void applySettings() {
        if(customRadio.isSelected())
            getLogic().setSetting("Customization", "boardPreset",CUSTOM_BOARD);
        else if (leftPresetRadio.isSelected())
            getLogic().setSetting("Customization", "boardPreset",LEFT_PRESET);
        else getLogic().setSetting("Customization", "boardPreset",RIGHT_PRESET);
        getLogic().setSetting("Customization", "whitePawnStroke", ColorHandler.buildColorString(whitePawnStrokeColorPicker.getValue()));
        getLogic().setSetting("Customization", "whitePawnFill", ColorHandler.buildColorString(whitePawnFillColorPicker.getValue()));
        getLogic().setSetting("Customization", "blackPawnStroke", ColorHandler.buildColorString(blackPawnStrokeColorPicker.getValue()));
        getLogic().setSetting("Customization", "blackPawnFill", ColorHandler.buildColorString(blackPawnFillColorPicker.getValue()));
        getLogic().setSetting("Customization", "boardFrameColor", ColorHandler.buildColorString(frameColorPicker.getValue()));
        getLogic().setSetting("Customization", "boardInnerColor", ColorHandler.buildColorString(tableColorPicker.getValue()));
        getLogic().setSetting("Customization", "evenPointsColor", ColorHandler.buildColorString(evenPointColorPicker.getValue()));
        getLogic().setSetting("Customization", "oddPointsColor", ColorHandler.buildColorString(oddPointColorPicker.getValue()));
        getLogic().setSetting("Customization", "selectedPointColor", ColorHandler.buildColorString(selectedPointColorPicker.getValue()));
        getLogic().setSetting("Customization", "backgroundColor", ColorHandler.buildColorString(backGroundColorPicker.getValue()));
    }

    public void changeDimensions(AnchorPane window) {
        if (getLogic().isLanguageRightToLeft(getLogic().getCurrentLanguage())) {
            AnchorPane.setLeftAnchor(pawnFillText, window.getWidth() * 0.50);
            AnchorPane.setLeftAnchor(pawnStrokeText, window.getWidth() * 0.50);
            AnchorPane.setLeftAnchor(whitePawn, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(blackPawn, window.getWidth() * 0.30);
            AnchorPane.setLeftAnchor(backGroundColorPicker,window.getWidth() * 0.40);
            AnchorPane.setLeftAnchor(backgroundText,window.getWidth() * 0.50);
        }else {
            AnchorPane.setLeftAnchor(pawnFillText, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(pawnStrokeText, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(whitePawn, window.getWidth() * 0.30);
            AnchorPane.setLeftAnchor(blackPawn, window.getWidth() * 0.50);
            AnchorPane.setLeftAnchor(backGroundColorPicker,window.getWidth() * 0.20);
            AnchorPane.setLeftAnchor(backgroundText,window.getWidth() * 0.06);
        }
        AnchorPane.setLeftAnchor(whitePawnText, window.getWidth() * 0.20);
        AnchorPane.setLeftAnchor(whitePawnFillColorPicker, window.getWidth() * 0.20);
        AnchorPane.setLeftAnchor(whitePawnStrokeColorPicker, window.getWidth() * 0.20);
        AnchorPane.setLeftAnchor(tableText, window.getWidth() * 0.10);
        AnchorPane.setLeftAnchor(leftPresetPane, window.getWidth() * 0.10);
        AnchorPane.setLeftAnchor(leftPresetRadio, window.getWidth() * 0.10);
        AnchorPane.setLeftAnchor(customPane, window.getWidth() * 0.30);
        AnchorPane.setLeftAnchor(customRadio, window.getWidth() * 0.30);
        AnchorPane.setLeftAnchor(rightPresetPane, window.getWidth() * 0.50);
        AnchorPane.setLeftAnchor(rightPresetRadio, window.getWidth() * 0.50);
        AnchorPane.setLeftAnchor(tableColorPicker, window.getWidth() * 0.11);
        AnchorPane.setLeftAnchor(pointText, window.getWidth() * 0.30);
        AnchorPane.setLeftAnchor(oddPointColorPicker, window.getWidth() * 0.30 + 7);
        AnchorPane.setLeftAnchor(evenPointColorPicker, AnchorPane.getLeftAnchor(oddPointColorPicker) - 56);
        AnchorPane.setLeftAnchor(selectedPointColorPicker, AnchorPane.getLeftAnchor(oddPointColorPicker) + 56);
        AnchorPane.setLeftAnchor(frameText, window.getWidth() * 0.50);
        AnchorPane.setLeftAnchor(frameColorPicker, window.getWidth() * 0.51);
        AnchorPane.setLeftAnchor(blackPawnText, window.getWidth() * 0.40);
        AnchorPane.setLeftAnchor(blackPawnFillColorPicker, window.getWidth() * 0.40);
        AnchorPane.setLeftAnchor(blackPawnStrokeColorPicker, window.getWidth() * 0.40);

        customizeTitlePane.setLayoutX(window.getWidth()/4);
        customizeTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        customizeTitlePane.setPrefHeight(window.getHeight());
    }

    public void initialize() {
        selectedPointPresetsAnimation = TimelineBuilder.buildPresetsAnimation
                (leftPresetPoint1, leftPresetPoint2, leftPresetPoint3, rightPresetPoint1,
                        rightPresetPoint2, rightPresetPoint3);
        group = new ToggleGroup();
        leftPresetRadio.setToggleGroup(group);
        customRadio.setToggleGroup(group);
        rightPresetRadio.setToggleGroup(group);
        selectedPointPresetsAnimation.setCycleCount(Animation.INDEFINITE);
        regeneratePointAnimation();
        feltImage.fitHeightProperty().bind(customizeAnchorPane.heightProperty());
        feltImage.fitWidthProperty().bind(customizeAnchorPane.widthProperty());
        setBackgroundColor(getLogic().getSetting("Customization", "backgroundColor", String.class));
    }

    public void setBackgroundColor(String colorString) {
        customizeAnchorPane.setStyle("-fx-background-color: " + colorString);
    }

    public void loadStrings() {
        customizeTitlePane.setText(getLogic().getString("Customization"));
        whitePawnText.setText(getLogic().getString("Player1Pawns"));
        blackPawnText.setText(getLogic().getString("Player2Pawns"));
        pawnFillText.setText(getLogic().getString("Internal"));
        pawnStrokeText.setText(getLogic().getString("Outline"));
        tableText.setText(getLogic().getString("Table"));
        pointText.setText(getLogic().getString("Points"));
        frameText.setText(getLogic().getString("Frame"));
        leftPresetRadio.setText(getLogic().getString("Preset"));
        customRadio.setText(getLogic().getString("Custom"));
        rightPresetRadio.setText(getLogic().getString("Preset"));
        backgroundText.setText(getLogic().getString("BackGround"));
    }

    public void loadSettings() {
        if (getLogic().getSetting("Customization", "boardPreset", int.class).equals(CUSTOM_BOARD)) {
            customRadio.setSelected(true);
            disableColorPickers(false);
        } else {
            disableColorPickers(true);
            if(getLogic().getSetting("Customization", "boardPreset", int.class).equals(LEFT_PRESET))
                leftPresetRadio.setSelected(true);
            else rightPresetRadio.setSelected(true);
        }

        whitePawnFillColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class)));
        whitePawnStrokeColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "whitePawnStroke", String.class)));
        blackPawnFillColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class)));
        blackPawnStrokeColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "blackPawnStroke", String.class)));
        tableColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "boardInnerColor", String.class)));
        frameColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "boardFrameColor", String.class)));
        evenPointColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "evenPointsColor", String.class)));
        oddPointColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "oddPointsColor", String.class)));
        selectedPointColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "selectedPointColor", String.class)));
        backGroundColorPicker.setValue(Color.web(getLogic().getSetting("Customization", "backgroundColor", String.class)));
        selectedPointColor = Color.web(getLogic().getSetting("Customization", "selectedPointColor", String.class));

        whitePawnFillChange(null);
        whitePawnStrokeChange(null);
        blackPawnFillChange(null);
        blackPawnStrokeChange(null);
        boardColorChange(null);
        frameColorChange(null);
        evenPointColorChange(null);
        oddPointColorChange(null);
    }

}