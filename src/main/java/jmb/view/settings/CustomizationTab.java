package jmb.view.settings;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import jmb.view.SettingsView;
import jmb.view.utilities.AnimationBuilder;

import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

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
        if(Color.web(logic.getSetting("Customization","backgroundColor",String.class)) != backGroundColorPicker.getValue()) {
            settingsView.getApplyButton().setDisable(false);
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
        selectedPointAnimation = AnimationBuilder.buildCustomAnimation(customPoint1, customPoint2,
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
            logic.setSetting("Customization", "boardPreset",CUSTOM_BOARD);
        else if (leftPresetRadio.isSelected())
            logic.setSetting("Customization", "boardPreset",LEFT_PRESET);
        else logic.setSetting("Customization", "boardPreset",RIGHT_PRESET);
        logic.setSetting("Customization", "whitePawnStroke",ColorStringFactory.buildString(whitePawnStrokeColorPicker.getValue()));
        logic.setSetting("Customization", "whitePawnFill",ColorStringFactory.buildString(whitePawnFillColorPicker.getValue()));
        logic.setSetting("Customization", "blackPawnStroke",ColorStringFactory.buildString(blackPawnStrokeColorPicker.getValue()));
        logic.setSetting("Customization", "blackPawnFill",ColorStringFactory.buildString(blackPawnFillColorPicker.getValue()));
        logic.setSetting("Customization", "boardFrameColor",ColorStringFactory.buildString(frameColorPicker.getValue()));
        logic.setSetting("Customization", "boardInnerColor",ColorStringFactory.buildString(tableColorPicker.getValue()));
        logic.setSetting("Customization", "evenPointsColor",ColorStringFactory.buildString(evenPointColorPicker.getValue()));
        logic.setSetting("Customization", "oddPointsColor",ColorStringFactory.buildString(oddPointColorPicker.getValue()));
        logic.setSetting("Customization", "selectedPointColor",ColorStringFactory.buildString(selectedPointColorPicker.getValue()));
        logic.setSetting("Customization", "backgroundColor", ColorStringFactory.buildString(backGroundColorPicker.getValue()));
    }

    public void changeDimensions(AnchorPane window) {
        if (logic.isLanguageRightToLeft(logic.getCurrentlanguage())) {
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
        selectedPointPresetsAnimation = AnimationBuilder.buildPresetsAnimation
                (leftPresetPoint1, leftPresetPoint2, leftPresetPoint3, rightPresetPoint1,
                        rightPresetPoint2, rightPresetPoint3);
        group = new ToggleGroup();
        leftPresetRadio.setToggleGroup(group);
        customRadio.setToggleGroup(group);
        rightPresetRadio.setToggleGroup(group);
        selectedPointPresetsAnimation.setCycleCount(Animation.INDEFINITE);
        regeneratePointAnimation();
    }

    public void loadStrings() {
        customizeTitlePane.setText(logic.getString("Customization"));
        whitePawnText.setText(logic.getString("Player1Pawns"));
        blackPawnText.setText(logic.getString("Player2Pawns"));
        pawnFillText.setText(logic.getString("Internal"));
        pawnStrokeText.setText(logic.getString("Outline"));
        tableText.setText(logic.getString("Table"));
        pointText.setText(logic.getString("Points"));
        frameText.setText(logic.getString("Frame"));
        leftPresetRadio.setText(logic.getString("Preset"));
        customRadio.setText(logic.getString("Custom"));
        rightPresetRadio.setText(logic.getString("Preset"));
        backgroundText.setText(logic.getString("BackGround"));
    }

    public void loadSettings() {
        if (logic.getSetting("Customization", "boardPreset", int.class).equals(CUSTOM_BOARD)) {
            customRadio.setSelected(true);
            disableColorPickers(false);
        } else {
            disableColorPickers(true);
            if(logic.getSetting("Customization", "boardPreset", int.class).equals(LEFT_PRESET))
                leftPresetRadio.setSelected(true);
            else rightPresetRadio.setSelected(true);
        }

        whitePawnFillColorPicker.setValue(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
        whitePawnStrokeColorPicker.setValue(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
        blackPawnFillColorPicker.setValue(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
        blackPawnStrokeColorPicker.setValue(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
        tableColorPicker.setValue(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
        frameColorPicker.setValue(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
        evenPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
        oddPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
        selectedPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "selectedPointColor", String.class)));
        backGroundColorPicker.setValue(Color.web(logic.getSetting("Customization", "backgroundColor", String.class)));
        selectedPointColor = Color.web(logic.getSetting("Customization", "selectedPointColor", String.class));

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