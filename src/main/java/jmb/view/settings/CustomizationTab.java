package jmb.view.settings;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
import javafx.util.Duration;
import jmb.view.SettingsView;

import static jmb.ConstantsShared.*;
import static jmb.ConstantsShared.ODD_POINTS;
import static jmb.view.View.logic;

public class CustomizationTab {

    @FXML
    private TitledPane customizeTitlePane;
    @FXML
    private AnchorPane customizeAnchorPane, customPane, leftPresetPane, rightPresetPane;
    @FXML
    private Text backgroundText, blackPawnText, frameText, pawnFillText, pawnStrokeText,
            pointText, tableText, whitePawnText;
    @FXML
    private Circle blackPawn, whitePawn;
    @FXML
    private ColorPicker blackPawnFillColorPicker, backGroundColorPicker, blackPawnStrokeColorPicker,
            evenPointColorPicker, frameColorPicker, oddPointColorPicker,
            selectedPointColorPicker, tableColorPicker, whitePawnFillColorPicker,
            whitePawnStrokeColorPicker;
    @FXML
    private Rectangle customBoard, customFrame, leftPresetBoard, leftPresetFrame;
    @FXML
    private Polygon customPoint1, customPoint2, customPoint3, leftPresetPoint1,
            leftPresetPoint2, leftPresetPoint3, rightPresetPoint1,
            rightPresetPoint2, rightPresetPoint3;
    @FXML
    private RadioButton customRadio, leftPresetRadio, rightPresetRadio;
    private Color selectedPointColor;
    private ToggleGroup group = new ToggleGroup();
    private Timeline selectedPointAnimation;
    private final Timeline selectedPointPresetsAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e-> {
                leftPresetPoint1.setFill(Color.web(logic.getSetting(LEFT, SELECTED_POINT)));
                rightPresetPoint3.setFill(Color.web(logic.getSetting(RIGHT, SELECTED_POINT)));
                leftPresetPoint3.setFill(Color.web(logic.getSetting(LEFT, EVEN_POINTS)));
                rightPresetPoint1.setFill(Color.web(logic.getSetting(RIGHT, EVEN_POINTS)));
            }), new KeyFrame(Duration.seconds(0.5), e-> {
        leftPresetPoint2.setFill(Color.web(logic.getSetting(LEFT, SELECTED_POINT)));
        rightPresetPoint2.setFill(Color.web(logic.getSetting(RIGHT, SELECTED_POINT)));
        leftPresetPoint1.setFill(Color.web(logic.getSetting(LEFT, EVEN_POINTS)));
        rightPresetPoint3.setFill(Color.web(logic.getSetting(RIGHT, EVEN_POINTS)));
    }), new KeyFrame(Duration.seconds(1), e-> {
        leftPresetPoint3.setFill(Color.web(logic.getSetting(LEFT, SELECTED_POINT)));
        rightPresetPoint1.setFill(Color.web(logic.getSetting(RIGHT, SELECTED_POINT)));
        leftPresetPoint2.setFill(Color.web(logic.getSetting(LEFT, ODD_POINTS)));
        rightPresetPoint2.setFill(Color.web(logic.getSetting(RIGHT, ODD_POINTS)));
    }), new KeyFrame(Duration.seconds(1.5))
    );
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
        selectedPointAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    customPoint3.setFill(selectedPointColor);
                    customPoint1.setFill(evenPointColorPicker.getValue());
                }), new KeyFrame(Duration.seconds(0.5), e -> {
            customPoint2.setFill(selectedPointColor);
            customPoint3.setFill(evenPointColorPicker.getValue());
        }), new KeyFrame(Duration.seconds(1), e -> {
            customPoint1.setFill(selectedPointColor);
            customPoint2.setFill(oddPointColorPicker.getValue());
        }), new KeyFrame(Duration.seconds(1.5))
        );
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

    //TODO SE TROPPE RIGHE QUESTI METODI SONO SPOSTABILI IN CLASSE DI UTILITA'
    private String colorStringFactory(Color color) {
        String out = "#" + componentSubstringFactory(color.getRed()) +
                componentSubstringFactory(color.getGreen()) + componentSubstringFactory(color.getBlue());
        return out;
    }
    private String componentSubstringFactory(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        //Se il valore esadecimale è a una cifra gli aggiunge uno zero in testa e restituisce le due cifre come stringa
        //      altrimenti restituisce il valore
        return in.length() == 1 ? "0" + in : in;
    }

    public void applySettings() {
        if(customRadio.isSelected())
            logic.setSetting("Customization", "boardPreset",CUSTOM_BOARD);
        else if (leftPresetRadio.isSelected())
            logic.setSetting("Customization", "boardPreset",LEFT_PRESET);
        else logic.setSetting("Customization", "boardPreset",RIGHT_PRESET);
        logic.setSetting("Customization", "whitePawnStroke",colorStringFactory(whitePawnStrokeColorPicker.getValue()));
        logic.setSetting("Customization", "whitePawnFill",colorStringFactory(whitePawnFillColorPicker.getValue()));
        logic.setSetting("Customization", "blackPawnStroke",colorStringFactory(blackPawnStrokeColorPicker.getValue()));
        logic.setSetting("Customization", "blackPawnFill",colorStringFactory(blackPawnFillColorPicker.getValue()));
        logic.setSetting("Customization", "boardFrameColor",colorStringFactory(frameColorPicker.getValue()));
        logic.setSetting("Customization", "boardInnerColor",colorStringFactory(tableColorPicker.getValue()));
        logic.setSetting("Customization", "evenPointsColor",colorStringFactory(evenPointColorPicker.getValue()));
        logic.setSetting("Customization", "oddPointsColor",colorStringFactory(oddPointColorPicker.getValue()));
        logic.setSetting("Customization", "selectedPointColor",colorStringFactory(selectedPointColorPicker.getValue()));
        logic.setSetting("Customization", "backgroundColor", colorStringFactory(backGroundColorPicker.getValue()));
    }

    public void changeDimensions() {
        AnchorPane window = settingsView.getWindowPane();
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

        customizeAnchorPane.setLayoutX(window.getWidth()/4);
        customizeTitlePane.setLayoutX(window.getWidth()/4);
        customizeAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        customizeTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        customizeAnchorPane.setPrefHeight(window.getHeight());
        customizeTitlePane.setPrefHeight(window.getHeight());
    }

    public void initialize() {
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
        switch (logic.getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD:
                customRadio.setSelected(true);
                disableColorPickers(false);
                break;
            case LEFT_PRESET:
                leftPresetRadio.setSelected(true);
                disableColorPickers(true);
                break;
            case RIGHT_PRESET:
                rightPresetRadio.setSelected(true);
                disableColorPickers(true);
                break;
        }

        whitePawnFillColorPicker.setValue(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
        whitePawnStrokeColorPicker.setValue(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
        blackPawnFillColorPicker.setValue(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
        blackPawnStrokeColorPicker.setValue(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
        tableColorPicker.setValue(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
        frameColorPicker.setValue(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
        evenPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
        oddPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
        selectedPointColor = Color.web(logic.getSetting("Customization", "selectedPointColor", String.class));
        selectedPointColorPicker.setValue(selectedPointColor);
        backGroundColorPicker.setValue(Color.web(logic.getSetting("Customization", "backgroundColor", String.class)));

        whitePawn.setFill(whitePawnFillColorPicker.getValue());
        whitePawn.setStroke(whitePawnStrokeColorPicker.getValue());
        blackPawn.setFill(blackPawnFillColorPicker.getValue());
        blackPawn.setStroke(blackPawnStrokeColorPicker.getValue());
        customBoard.setFill(tableColorPicker.getValue());
        customBoard.setStroke(tableColorPicker.getValue());
        customFrame.setFill(frameColorPicker.getValue());
        customFrame.setStroke(frameColorPicker.getValue());
        customPoint1.setFill(evenPointColorPicker.getValue());
        customPoint1.setStroke(evenPointColorPicker.getValue());
        customPoint2.setFill(oddPointColorPicker.getValue());
        customPoint2.setStroke(oddPointColorPicker.getValue());
        customPoint3.setFill(evenPointColorPicker.getValue());
        customPoint3.setStroke(evenPointColorPicker.getValue());
    }

}