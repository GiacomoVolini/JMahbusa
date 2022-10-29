package jmb.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.*;

public class SettingsView implements GenericGUI{
        @FXML private AnchorPane window;

        // Selettore schede impostazioni
        @FXML private TitledPane settingsTitlePane;
        @FXML private AnchorPane settingsAnchorPane;
        @FXML private Button videoButton, audioButton, customizationButton, controlsButton,
                mainMenuButton, resetButton, applyButton;
        private Button[] settingsPaneButtons;
        private enum Tab {VIDEO, AUDIO, CUSTOMIZATION, CONTROLS}
        private boolean wasApplyButtonDisabled = true;

        // Scheda impostazioni "Video"
        @FXML private TitledPane videoTitlePane;
        @FXML private AnchorPane videoAnchorPane;
        @FXML private CheckBox fullscreenCheck, lockResolutionCheck;
        @FXML private Text resolutionText, resolutionWidthText, resolutionHeightText;
        @FXML private TextField resolutionWidthField, resolutionHeightField;

        // Scheda impostazioni "Audio"
        @FXML private AnchorPane audioAnchorPane;
        @FXML private TitledPane audioTitlePane;
        @FXML private Text musicText, sFXText;
        @FXML private CheckBox musicCheck, sFXCheck;
        @FXML private Slider musicSlider, sFXSlider;

        //Scheda impostazioni "Personalizzazione"
        @FXML private AnchorPane customizeAnchorPane;
        @FXML private TitledPane customizeTitlePane;
        @FXML private Text whitePawnText, blackPawnText, pawnFillText, pawnStrokeText,
                tableText, pointText, frameText;
        @FXML private Circle whitePawn, blackPawn;
        @FXML private ColorPicker whitePawnFillColorPicker, blackPawnFillColorPicker, whitePawnStrokeColorPicker,
                blackPawnStrokeColorPicker, tableColorPicker, evenPointColorPicker, oddPointColorPicker,
                frameColorPicker;
        @FXML private AnchorPane customPane, leftPresetPane, rightPresetPane;
        @FXML private Polygon leftPresetPoint1, leftPresetPoint2, leftPresetPoint3,
                rightPresetPoint1, rightPresetPoint2, rightPresetPoint3,
                customPoint1, customPoint2, customPoint3;

        @FXML private RadioButton rightPresetRadio, leftPresetRadio, customRadio;
        @FXML private Rectangle customFrame, customBoard;
        private Color selectedPointColor;
        ToggleGroup group = new ToggleGroup();
        private Timeline selectedPointAnimation;
        private Timeline selectedPointPresetsAnimation = new Timeline(
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

        // Scheda impostazioni "Comandi"
        @FXML private AnchorPane controlsAnchorPane;
        @FXML private TitledPane controlsTitlePane;
        @FXML private Text keyboardText, movementText, rightText, leftText, upText, downText, selectText,
                finishTurnText, revertMoveText, openMenuText, backgroundText;
        @FXML private TextField rightTextField, leftTextField, upTextField, downTextField, selectTextField,
                revertMoveTextField, finishTurnTextField, openMenuTextField;
        @FXML private ColorPicker selectedPointColorPicker, backGroundColorPicker;
        private String bindingBefore;

        // Pannello avviso cambiamenti non salvati
        @FXML private TitledPane warningTitlePane;
        @FXML private Text warningText;
        @FXML private Button exitAndSaveButton, exitNoSaveButton, cancelButton;


        @FXML void goToMainMenu() {
                if(!applyButton.isDisable()){
                        warningTitlePane.setVisible(true);
                        warningTitlePane.setDisable(false);
                        settingsAnchorPane.setDisable(true);
                }else {
                        App.changeRoot(MAIN_MENU);
                        selectedPointAnimation.stop();
                        selectedPointPresetsAnimation.stop();
                }
        }

        @FXML void closeWarning(ActionEvent event) {
                warningTitlePane.setVisible(false);
                warningTitlePane.setDisable(true);
                settingsAnchorPane.setDisable(false);
                videoAnchorPane.setDisable(false);
                audioAnchorPane.setDisable(false);
                controlsAnchorPane.setDisable(false);
                customizeAnchorPane.setDisable(false);
        }

        @FXML void forceMainMenu(ActionEvent event) {
                settingsAnchorPane.setDisable(false);
                App.changeRoot(MAIN_MENU);
                selectedPointAnimation.stop();
                selectedPointPresetsAnimation.stop();
        }


        @FXML void openEditVideo() {
                selectSettingsTab(Tab.VIDEO);
        }

        @FXML void openEditAudio() {
                selectSettingsTab(Tab.AUDIO);
        }

        @FXML void openEditCustomize() {
                selectSettingsTab(Tab.CUSTOMIZATION);
        }

        @FXML void openEditControls(ActionEvent event) {
                selectSettingsTab(Tab.CONTROLS);
        }

        private void selectSettingsTab (Tab tab) {
                videoTitlePane.setVisible(tab.equals(Tab.VIDEO));
                videoAnchorPane.setMouseTransparent(!tab.equals(Tab.VIDEO));
                audioTitlePane.setVisible(tab.equals(Tab.AUDIO));
                audioAnchorPane.setMouseTransparent(!tab.equals(Tab.AUDIO));
                controlsTitlePane.setVisible(tab.equals(Tab.CONTROLS));
                controlsAnchorPane.setMouseTransparent(!tab.equals(Tab.CONTROLS));
                customizeTitlePane.setVisible(tab.equals(Tab.CUSTOMIZATION));
                customizeAnchorPane.setMouseTransparent(!tab.equals(Tab.CUSTOMIZATION));
        }

        //Video
        //schermo intero
        @FXML void fullscreen(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                boolean fullScreen = fullscreenCheck.isSelected();
                stage.setFullScreen(fullScreen);
                applyButton.setDisable(false);
                resolutionWidthField.setDisable(fullScreen);
                resolutionHeightField.setDisable(fullScreen);
        }

        @FXML void lockResolution(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setResizable(!lockResolutionCheck.isSelected());
                applyButton.setDisable(false);
                setResolutionFieldsEditability();
        }

        private void setResolutionFieldsEditability() {
                boolean set = lockResolutionCheck.isSelected() || fullscreenCheck.isSelected();
                resolutionWidthField.setDisable(set);
                resolutionHeightField.setDisable(set);
        }

        //Audio

        @FXML void muteMusic(ActionEvent event) {
                musicSlider.setDisable(musicCheck.isSelected());
                if (musicCheck.isSelected())
                        view.pauseMusic();
                else view.playMusic(Music.MENU);
                applyButton.setDisable(false);
        }

        @FXML void muteSFX(ActionEvent event) {
                sFXSlider.setDisable(sFXCheck.isSelected());
                applyButton.setDisable(false);
        }

        @FXML void playSound(MouseEvent event) {
                view.playSFX(SFX.PAWN_DROP);
        }

        //Personalizzazione
        @FXML void whitePawnFillChange(ActionEvent event) {
                changeFillColor(whitePawnFillColorPicker.getValue(), whitePawn);
        }

        @FXML void whitePawnStrokeChange(ActionEvent event) {
                changeStrokeColor(whitePawnStrokeColorPicker.getValue(), whitePawn);
        }

        @FXML void blackPawnFillChange(ActionEvent event) {
                changeFillColor(blackPawnFillColorPicker.getValue(), blackPawn);
        }

        @FXML void blackPawnStrokeChange(ActionEvent event) {
                changeStrokeColor(blackPawnStrokeColorPicker.getValue(), blackPawn);
        }

        @FXML void frameColorChange(ActionEvent event) {
                changeBothColors(frameColorPicker.getValue(), customFrame);
        }

        @FXML void boardColorChange(ActionEvent event) {
                changeBothColors(tableColorPicker.getValue(), customBoard);
        }

        @FXML void evenPointColorChange(ActionEvent event) {
                changeBothColors(evenPointColorPicker.getValue(), customPoint1);
                changeBothColors(evenPointColorPicker.getValue(), customPoint3);
        }

        @FXML void oddPointColorChange(ActionEvent event) {
                changeBothColors(oddPointColorPicker.getValue(), customPoint2);
        }

        @FXML void selectedPointColorChange(ActionEvent event) {
                Color newValue = selectedPointColorPicker.getValue();
                if (newValue!=selectedPointColor) {
                        selectedPointColor = newValue;
                        regeneratePointAnimation();
                        applyButton.setDisable(false);
                }
        }

        private void changeStrokeColor(Color newColor, Shape shape){
                if (!newColor.equals(shape.getStroke())) {
                        shape.setStroke(newColor);
                        applyButton.setDisable(false);
                }
        }
        private void changeFillColor(Color newColor, Shape shape){
                if (!newColor.equals(shape.getFill())) {
                        shape.setFill(newColor);
                        applyButton.setDisable(false);
                }
        }
        private void changeBothColors(Color newColor, Shape shape) {
                changeStrokeColor(newColor, shape);
                changeFillColor(newColor, shape);
        }

        private void regeneratePointAnimation() {
                if (selectedPointAnimation!=null)
                        selectedPointAnimation.stop();
                selectedPointPresetsAnimation.stop();
                selectedPointAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, e-> {
                                customPoint3.setFill(selectedPointColor);
                                customPoint1.setFill(evenPointColorPicker.getValue());
                        }), new KeyFrame(Duration.seconds(0.5), e-> {
                                customPoint2.setFill(selectedPointColor);
                                customPoint3.setFill(evenPointColorPicker.getValue());
                        }), new KeyFrame(Duration.seconds(1), e-> {
                                customPoint1.setFill(selectedPointColor);
                                customPoint2.setFill(oddPointColorPicker.getValue());
                        }), new KeyFrame(Duration.seconds(1.5))
                );
                selectedPointAnimation.setCycleCount(Animation.INDEFINITE);
                selectedPointAnimation.play();
                selectedPointPresetsAnimation.play();
        }

        @FXML
        void presetAction(ActionEvent event) {
                disableColorPickers(true);
                applyButton.setDisable(false);
        }

        @FXML
        void customPresetAction(ActionEvent event) {
                disableColorPickers(false);
                applyButton.setDisable(false);
        }

        @FXML
        void selectedBackGroundColorChange(ActionEvent event) {
                if(Color.web(logic.getSetting("Customization","backgroundColor",String.class)) != backGroundColorPicker.getValue()) {
                        applyButton.setDisable(false);
                }
        }

        //Comandi Action
        int waitingBinding;
        @FXML
        void selectKeyBind(MouseEvent event) {
                TextField b = (TextField) event.getSource();
                bindingBefore = b.getText();
                b.setText(logic.getString("Waiting"));
                for(int i=0; i<8; i++){
                        if(keyBinds[i].getText().equals(logic.getString("Waiting"))){
                                keyBinds[i].setDisable(false);
                                waitingBinding = i;
                        }else{
                                keyBinds[i].setDisable(true);
                        }
                }
                wasApplyButtonDisabled = applyButton.isDisabled();
                System.out.println(wasApplyButtonDisabled);
                for (Button button : settingsPaneButtons)
                        button.setDisable(true);
        }

        protected TextField[] keyBinds;
        @FXML
        void changeKeyBind(KeyEvent event) {
                TextField b = (TextField) event.getSource();
                for(int i=0; i<8; i++){
                        if(keyBinds[i].getText().equals(event.getCode().toString()) && i != waitingBinding){
                                b.setText(bindingBefore);
                                i=9;
                        }else{
                                b.setText(event.getCode().toString());
                        }
                }
                for(int i=0; i<8; i++){
                        keyBinds[i].setDisable(false);
                }
                for (Button button : settingsPaneButtons)
                        button.setDisable(false);
                if (b.getText().equals(bindingBefore))
                        applyButton.setDisable(wasApplyButtonDisabled);
                System.out.println(wasApplyButtonDisabled);
        }

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

        @FXML
        void changeResolution(KeyEvent event) {
                if(logic.isParsable(resolutionWidthField.getText()) && logic.isParsable(resolutionHeightField.getText())) {
                        logic.setSetting("Video","resolutionWidth",Integer.parseInt(resolutionWidthField.getText()));
                        logic.setSetting("Video","resolutionHeight",Integer.parseInt(resolutionHeightField.getText()));
                        applyButton.setDisable(false);
                }else{
                        applyButton.setDisable(true);
                }
        }


        @FXML
        void applySettingsAndExit(ActionEvent event) {
                applySettings(null);
                selectedPointAnimation.stop();
                selectedPointPresetsAnimation.stop();
                App.changeRoot(MAIN_MENU);
        }

        @FXML
        void applySettings(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                logic.setSetting("Video", "fullScreen",fullscreenCheck.isSelected());
                logic.setSetting("Video", "lockResolution",lockResolutionCheck.isSelected());
                logic.setSetting("Video", "resolutionWidth",Integer.parseInt(resolutionWidthField.getText()));
                stage.setWidth(logic.getSetting("Video", "resolutionWidth", int.class));
                logic.setSetting("Video", "resolutionHeight",Integer.parseInt(resolutionHeightField.getText()));
                stage.setHeight(logic.getSetting("Video", "resolutionHeight", int.class));
                logic.setSetting("Audio", "musicVolume",(int) musicSlider.getValue());
                logic.setSetting("Audio", "soundFXVolume",(int) sFXSlider.getValue());
                logic.setSetting("Audio", "muteMusic",musicCheck.isSelected());
                logic.setSetting("Audio", "muteSFX",sFXCheck.isSelected());
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
                logic.setSetting("Controls", "moveRight",rightTextField.getText());
                logic.setSetting("Controls", "moveLeft",leftTextField.getText());
                logic.setSetting("Controls", "moveUp",upTextField.getText());
                logic.setSetting("Controls", "moveDown",downTextField.getText());
                logic.setSetting("Controls", "select",selectTextField.getText());
                logic.setSetting("Controls", "revertMove",revertMoveTextField.getText());
                logic.setSetting("Controls", "finishTurn",finishTurnTextField.getText());
                logic.setSetting("Controls", "openMenu",openMenuTextField.getText());
                logic.setSetting("Customization", "backgroundColor", colorStringFactory(backGroundColorPicker.getValue()));
                logic.applySettingsChanges();
                applyButton.setDisable(true);
        }

        @FXML
        void resetToDefaults(ActionEvent event) {
                logic.resetDefaultSettings();
                loadSettings();
                fullscreen(null);
                lockResolution(null);
                applyButton.setDisable(true);
                regeneratePointAnimation();
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setWidth(logic.getSetting("Video", "resolutionWidth", int.class));
                stage.setHeight(logic.getSetting("Video", "resolutionHeight", int.class));
                if (logic.getSetting("Audio", "muteMusic", boolean.class))
                        view.pauseMusic();
                else view.playMusic(Music.MENU);
        }

        public void changeDimensions() {
                Stage stage = (Stage) window.getScene().getWindow();
                //bottoni sinistra
                videoButton.setLayoutX(window.getWidth()/8 - videoButton.getWidth()/2);
                videoButton.setLayoutY(window.getHeight()*0.12);
                videoButton.setMaxWidth(133);
                audioButton.setLayoutX(window.getWidth()/8 - videoButton.getWidth()/2);
                audioButton.setLayoutY(window.getHeight()*0.20);
                audioButton.setMaxWidth(133);
                customizationButton.setLayoutX(window.getWidth()/8 - videoButton.getWidth()/2);
                customizationButton.setLayoutY(window.getHeight()*0.28);
                customizationButton.setMaxWidth(133);
                controlsButton.setLayoutX(window.getWidth()/8 - videoButton.getWidth()/2);
                controlsButton.setLayoutY(window.getHeight()*0.36);
                controlsButton.setMaxWidth(133);
                mainMenuButton.setLayoutX(window.getWidth()/8 - mainMenuButton.getWidth()/2);
                mainMenuButton.setLayoutY(window.getHeight()*0.86);
                mainMenuButton.setMaxWidth(133);
                resetButton.setLayoutX(window.getWidth()/8 - mainMenuButton.getWidth()/2);
                resetButton.setLayoutY(window.getHeight()*0.78);
                resetButton.setMaxWidth(133);
                applyButton.setLayoutX(window.getWidth()/8 - applyButton.getWidth()/2);
                applyButton.setLayoutY(window.getHeight()*0.70);
                applyButton.setMaxWidth(133);

                //bottoni destra
                //if (logic.getSetting("General", "language", String.class).equals("AR")) {
                if (logic.isLanguageRightToLeft(logic.getcurrentlanguage())) {
                        //Video
                        AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.40);
                        AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.40);
                        AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.40);

                        //Audio
                        AnchorPane.setLeftAnchor(musicSlider, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(musicText, window.getWidth() * 0.4);
                        AnchorPane.setLeftAnchor(sFXSlider, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(sFXText, window.getWidth() * 0.4);
                        AnchorPane.setLeftAnchor(musicCheck, window.getWidth() * 0.25);
                        AnchorPane.setLeftAnchor(sFXCheck, window.getWidth() * 0.25);

                        //Personalizzazioni
                        AnchorPane.setLeftAnchor(pawnFillText, window.getWidth() * 0.50);
                        AnchorPane.setLeftAnchor(pawnStrokeText, window.getWidth() * 0.50);
                        AnchorPane.setLeftAnchor(whitePawn, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(blackPawn, window.getWidth() * 0.30);
                        AnchorPane.setLeftAnchor(backGroundColorPicker,window.getWidth() * 0.40);
                        AnchorPane.setLeftAnchor(backgroundText,window.getWidth() * 0.50);

                        //Comandi
                        AnchorPane.setLeftAnchor(keyboardText, window.getWidth() * 0.50);
                        AnchorPane.setLeftAnchor(movementText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(rightText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(leftText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(upText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(downText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(rightTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(leftTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(upTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(downTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(selectText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(revertMoveText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(finishTurnText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(openMenuText, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(selectTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(revertMoveTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(finishTurnTextField, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(openMenuTextField, window.getWidth() * 0.20);
                }else {
                        //Video
                        AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.13);
                        AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.13);
                        AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.28);
                        AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.28);

                        //Audio
                        AnchorPane.setLeftAnchor(musicText, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(musicSlider, window.getWidth() * 0.4);
                        AnchorPane.setLeftAnchor(sFXText, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(sFXSlider, window.getWidth() * 0.4);
                        AnchorPane.setLeftAnchor(musicCheck, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(sFXCheck, window.getWidth() * 0.10);

                        //Personalizzazioni
                        AnchorPane.setLeftAnchor(pawnFillText, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(pawnStrokeText, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(whitePawn, window.getWidth() * 0.30);
                        AnchorPane.setLeftAnchor(blackPawn, window.getWidth() * 0.50);
                        AnchorPane.setLeftAnchor(backGroundColorPicker,window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(backgroundText,window.getWidth() * 0.06);

                        //Comandi
                        AnchorPane.setLeftAnchor(keyboardText, window.getWidth() * 0.10);
                        AnchorPane.setLeftAnchor(movementText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(rightText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(leftText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(upText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(downText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(rightTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(leftTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(upTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(downTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(selectText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(revertMoveText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(finishTurnText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(openMenuText, window.getWidth() * 0.20);
                        AnchorPane.setLeftAnchor(selectTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(revertMoveTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(finishTurnTextField, window.getWidth() * 0.45);
                        AnchorPane.setLeftAnchor(openMenuTextField, window.getWidth() * 0.45);
                }

                //in comune
                //video
                AnchorPane.setLeftAnchor(fullscreenCheck, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(lockResolutionCheck, window.getWidth() * 0.4);

                //personalizzazione
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

                //schermi
                settingsAnchorPane.setPrefWidth(window.getWidth()/4);
                settingsTitlePane.setPrefWidth(window.getWidth()/4);
                warningTitlePane.setLayoutX(window.getWidth()/2 - 206);
                warningTitlePane.setLayoutY(window.getHeight()/2 -103);

                videoAnchorPane.setLayoutX(window.getWidth()/4);
                videoTitlePane.setLayoutX(window.getWidth()/4);
                audioAnchorPane.setLayoutX(window.getWidth()/4);
                audioTitlePane.setLayoutX(window.getWidth()/4);
                customizeAnchorPane.setLayoutX(window.getWidth()/4);
                customizeTitlePane.setLayoutX(window.getWidth()/4);
                controlsAnchorPane.setLayoutX(window.getWidth()/4);
                controlsTitlePane.setLayoutX(window.getWidth()/4);

                videoAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                videoTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                audioAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                audioTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                customizeAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                customizeTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                controlsAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                controlsTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);

                settingsAnchorPane.setPrefHeight(window.getWidth());
                settingsTitlePane.setPrefHeight(window.getWidth());
                videoAnchorPane.setPrefHeight(window.getHeight());
                videoTitlePane.setPrefHeight(window.getHeight());
                audioAnchorPane.setPrefHeight(window.getHeight());
                audioTitlePane.setPrefHeight(window.getHeight());
                customizeAnchorPane.setPrefHeight(window.getHeight());
                customizeTitlePane.setPrefHeight(window.getHeight());
                controlsAnchorPane.setPrefHeight(window.getHeight());
                controlsTitlePane.setPrefHeight(window.getHeight());

                //Risoluzione
                resolutionHeightField.setText(String.valueOf((int) stage.getHeight()));
                resolutionWidthField.setText(String.valueOf((int) stage.getWidth()));

                if (((int)stage.getHeight() != logic.getSetting("Video","resolutionHeight",int.class) ||
                        (int)stage.getWidth() != logic.getSetting("Video","resolutionWidth",int.class)) &&
                        (int)stage.getWidth()!= 0 && (int)stage.getHeight() != 0) {
                        applyButton.setDisable(false);
                }

        }
        public void initialize() {
                keyBinds = new TextField[] {this.rightTextField, this.leftTextField, this.upTextField, this.downTextField, this.selectTextField, this.revertMoveTextField, this.finishTurnTextField, this.openMenuTextField};
                settingsPaneButtons = new Button[] {videoButton, audioButton, customizationButton, controlsButton,
                        mainMenuButton, resetButton, applyButton};

                group = new ToggleGroup();
                leftPresetRadio.setToggleGroup(group);
                customRadio.setToggleGroup(group);
                rightPresetRadio.setToggleGroup(group);

                loadStrings();
                loadSettings();

                // LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());
                // LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

                openEditVideo();
                setResolutionFieldsEditability();

                selectedPointPresetsAnimation.setCycleCount(Animation.INDEFINITE);
                regeneratePointAnimation();

        }

        private void loadStrings() {
                // Selettore schede impostazioni
                settingsTitlePane.setText(logic.getString("Settings"));
                videoButton.setText(logic.getString("Video"));
                audioButton.setText(logic.getString("Audio"));
                customizationButton.setText(logic.getString("Customization"));
                controlsButton.setText(logic.getString("Commands"));
                applyButton.setText(logic.getString("Apply"));
                resetButton.setText(logic.getString("Reset"));
                mainMenuButton.setText(logic.getString("MainMenu"));
                // Scheda "Video"
                videoTitlePane.setText(logic.getString("Video"));
                resolutionText.setText(logic.getString("Resolution"));
                resolutionWidthText.setText(logic.getString("Length"));
                resolutionHeightText.setText(logic.getString("Height"));
                fullscreenCheck.setText(logic.getString("Fullscreen"));
                lockResolutionCheck.setText(logic.getString("BlockResolution"));
                // Scheda "Audio"
                audioTitlePane.setText(logic.getString("Audio"));
                musicText.setText(logic.getString("Music"));
                sFXText.setText(logic.getString("SoundEffects"));
                musicCheck.setText(logic.getString("MuteMusic"));
                sFXCheck.setText(logic.getString("MuteSoundEffects"));
                // Scheda "Personalizzazione"
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
                // Scheda "Comandi"
                controlsTitlePane.setText(logic.getString("Commands"));
                keyboardText.setText(logic.getString("Keyboard"));
                movementText.setText(logic.getString("Move"));
                rightText.setText(logic.getString("Right"));
                leftText.setText(logic.getString("Left"));
                upText.setText(logic.getString("Up"));
                downText.setText(logic.getString("Down"));
                selectText.setText(logic.getString("Select"));
                revertMoveText.setText(logic.getString("DeleteMove"));
                finishTurnText.setText(logic.getString("FinishTurn"));
                openMenuText.setText(logic.getString("TMainMenu"));
                // Pannello avviso impostazioni non salvate
                warningTitlePane.setText(logic.getString("Attention"));
                warningText.setText(logic.getString("AttentionText"));
                exitAndSaveButton.setText(logic.getString("Save"));
                exitNoSaveButton.setText(logic.getString("Don'tSave"));
                cancelButton.setText(logic.getString("Cancel"));
        }

        private void loadSettings() {
                // Scheda "Video"
                fullscreenCheck.setSelected(logic.getSetting("Video", "fullScreen", boolean.class));
                lockResolutionCheck.setSelected(logic.getSetting("Video", "lockResolution", boolean.class));
                resolutionWidthField.setText(String.valueOf(logic.getSetting("Video", "resolutionWidth", int.class)));
                resolutionHeightField.setText(String.valueOf(logic.getSetting("Video", "resolutionHeight", int.class)));

                // Scheda "Personalizzazione"
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

                //Scheda "Audio"
                boolean muteMusic = logic.getSetting("Audio", "muteMusic", boolean.class);
                boolean muteSFX = logic.getSetting("Audio", "muteSFX", boolean.class);
                musicCheck.setSelected(muteMusic);
                sFXCheck.setSelected(muteSFX);
                musicSlider.setValue(logic.getSetting("Audio", "musicVolume", int.class));
                musicSlider.setDisable(muteMusic);
                sFXSlider.setValue(logic.getSetting("Audio", "soundFXVolume", int.class));
                sFXSlider.setDisable(muteSFX);
                musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> checkVolumeChanges(newVal, MUSIC_VOLUME));
                sFXSlider.valueProperty().addListener((obs, oldVal, newVal) -> checkVolumeChanges(newVal, SFX_VOLUME));

                //Scheda "Comandi"
                rightTextField.setText(logic.getSetting("Controls", "moveRight", String.class));
                leftTextField.setText(logic.getSetting("Controls", "moveLeft", String.class));
                upTextField.setText(logic.getSetting("Controls", "moveUp", String.class));
                downTextField.setText(logic.getSetting("Controls", "moveDown", String.class));
                selectTextField.setText(logic.getSetting("Controls", "select", String.class));
                revertMoveTextField.setText(logic.getSetting("Controls", "revertMove", String.class));
                finishTurnTextField.setText(logic.getSetting("Controls", "finishTurn", String.class));
                openMenuTextField.setText(logic.getSetting("Controls", "openMenu", String.class));

        }

        private void disableColorPickers(boolean set) {
                tableColorPicker.setDisable(set);
                evenPointColorPicker.setDisable(set);
                oddPointColorPicker.setDisable(set);
                selectedPointColorPicker.setDisable(set);
                frameColorPicker.setDisable(set);
        }

        private void checkVolumeChanges(Number value, int whichSlider) {
                int savedValue = 0;
                int sliderValue = (int)(value.doubleValue());
                switch (whichSlider) {
                        case MUSIC_VOLUME:
                                savedValue = logic.getSetting("Audio", "musicVolume", int.class);
                                view.setVolume(MUSIC_VOLUME, value.doubleValue()/100);
                                break;
                        case SFX_VOLUME:
                                savedValue = logic.getSetting("Audio", "soundFXVolume", int.class);
                                view.setVolume(SFX_VOLUME,value.doubleValue()/100);
                                break;
                }
                if (savedValue != sliderValue)
                        applyButton.setDisable(false);
        }

}
