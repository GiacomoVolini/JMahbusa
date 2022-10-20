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
import static jmb.view.View.logic;
import static jmb.view.View.view;

import java.io.IOException;
public class SettingsView implements GenericGUI{

        /* DOC
              In questa classe c'era un memory leak dovuto alle animazioni delle punte nel tab di personalizzazione
              Le Timeline non venivano mai fermate, e rimanevano quindi in memoria in perpetuo
              Risolto mettendo dei richiami al metodo stop dovunque si tornasse al menu principale
         */

        @FXML
        private AnchorPane window;

        @FXML
        private AnchorPane settingsAnchorPane;

        @FXML
        private TitledPane settingsTitlePane;
        @FXML
        private Button videoButton;
        @FXML
        private Button audioButton;
        @FXML
        private Button customizationButton;
        @FXML
        private Button controlsButton;
        @FXML
        private Button mainMenuButton;
        @FXML
        private AnchorPane videoAnchorPane;
        @FXML
        private TitledPane videoTitlePane;
        @FXML
        private CheckBox fullscreenCheck;
        @FXML
        private CheckBox lockResolutionCheck;
        @FXML
        private Text resolutionText;
        @FXML
        private Text resolutionWidthText;
        @FXML
        private Text resolutionHeightText;
        @FXML
        private TextField resolutionWidthField;
        @FXML
        private TextField resolutionHeightField;
        @FXML
        private AnchorPane audioAnchorPane;
        @FXML
        private TitledPane audioTitlePane;
        @FXML
        private Text musicText;
        @FXML
        private CheckBox musicCheck;
        @FXML
        private Slider musicSlider;
        @FXML
        private CheckBox sFXCheck;
        @FXML
        private Text sFXText;
        @FXML
        private Slider sFXSlider;
        @FXML
        private AnchorPane customizeAnchorPane;
        @FXML
        private TitledPane customizeTitlePane;
        @FXML
        private Button resetButton;
        @FXML
        private ColorPicker whitePawnFillColorPicker;
        @FXML
        private Text whitePawnText;
        @FXML
        private Circle whitePawn;
        @FXML
        private ColorPicker blackPawnFillColorPicker;
        @FXML
        private Text blackPawnText;
        @FXML
        private Circle blackPawn;
        @FXML
        private ColorPicker whitePawnStrokeColorPicker;
        @FXML
        private ColorPicker blackPawnStrokeColorPicker;
        @FXML
        private Text pawnFillText;
        @FXML
        private Text pawnStrokeText;
        @FXML
        private Text tableText;
        @FXML
        private Text pointText;
        @FXML
        private Text frameText;
        @FXML
        private ColorPicker tableColorPicker;
        @FXML
        private ColorPicker evenPointColorPicker;
        @FXML
        private ColorPicker oddPointColorPicker;
        @FXML
        private Polygon leftPresetPoint1;
        @FXML
        private Polygon leftPresetPoint2;
        @FXML
        private Polygon leftPresetPoint3;
        @FXML
        private AnchorPane customPane;
        @FXML
        private Polygon rightPresetPoint1;
        @FXML
        private Polygon rightPresetPoint2;
        @FXML
        private Polygon rightPresetPoint3;
        @FXML
        private AnchorPane leftPresetPane;
        @FXML
        private ColorPicker frameColorPicker;
        @FXML
        private RadioButton rightPresetRadio;
        @FXML
        private RadioButton leftPresetRadio;
        @FXML
        private Rectangle customFrame;
        @FXML
        private Rectangle customBoard;
        @FXML
        private Polygon customPoint1;
        @FXML
        private Polygon customPoint2;
        @FXML
        private Polygon customPoint3;
        @FXML
        private AnchorPane rightPresetPane;
        @FXML
        private RadioButton customRadio;
        @FXML
        protected Button applyButton;
        @FXML
        private AnchorPane controlsAnchorPane;
        @FXML
        private TitledPane controlsTitlePane;
        @FXML
        private Text keyboardText;
        @FXML
        private Text movementText;
        @FXML
        private Text rightText;
        @FXML
        private Text leftText;
        @FXML
        private Text upText;
        @FXML
        private Text downText;
        @FXML
        private Text selectText;
        @FXML
        private Text finishTurnText;
        @FXML
        private Text revertMoveText;
        @FXML
        private Text openMenuText;
        @FXML
        private Text backgroundText;
        @FXML
        private TextField rightTextField;
        @FXML
        private TextField leftTextField;
        @FXML
        private TextField upTextField;
        @FXML
        private TextField downTextField;
        @FXML
        private TextField selectTextField;
        @FXML
        private TextField revertMoveTextField;
        @FXML
        private TextField finishTurnTextField;
        @FXML
        private TextField openMenuTextField;
        @FXML
        private ColorPicker selectedPointColorPicker;
        @FXML
        private ColorPicker backGroundColorPicker;
        @FXML
        private TitledPane warningTitlePane;
        @FXML
        private Text warningText;
        @FXML
        private Button exitAndSaveButton;
        @FXML
        private Button exitNoSaveButton;
        @FXML
        private Button cancelButton;
        double Sv, Sve;
        ToggleGroup group = new ToggleGroup();

        private String bindingBefore;
        private Color selectedPointColor;
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

        @FXML
        void goToMainMenu()  throws IOException {
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

        @FXML
        void closeWarning(ActionEvent event) {
                warningTitlePane.setVisible(false);
                warningTitlePane.setDisable(true);
                settingsAnchorPane.setDisable(false);
                videoAnchorPane.setDisable(false);
                audioAnchorPane.setDisable(false);
                controlsAnchorPane.setDisable(false);
                customizeAnchorPane.setDisable(false);
        }

        @FXML
        void forceMainMenu(ActionEvent event) {
                settingsAnchorPane.setDisable(false);
                App.changeRoot(MAIN_MENU);
                selectedPointAnimation.stop();
                selectedPointPresetsAnimation.stop();
        }


        @FXML
        void openEditVideo() {
                videoTitlePane.setVisible(true);
                videoAnchorPane.setMouseTransparent(false);
                audioTitlePane.setVisible(false);
                audioAnchorPane.setMouseTransparent(true);
                customizeTitlePane.setVisible(false);
                customizeAnchorPane.setMouseTransparent(true);
                controlsTitlePane.setVisible(false);
                controlsAnchorPane.setMouseTransparent(true);

        }

        @FXML
        void openEditAudio() {
                videoTitlePane.setVisible(false);
                videoAnchorPane.setMouseTransparent(true);
                audioTitlePane.setVisible(true);
                audioAnchorPane.setMouseTransparent(false);
                customizeTitlePane.setVisible(false);
                customizeAnchorPane.setMouseTransparent(true);
                controlsTitlePane.setVisible(false);
                controlsAnchorPane.setMouseTransparent(true);
        }

        @FXML
        void openEditCustomize() {
                videoTitlePane.setVisible(false);
                videoAnchorPane.setMouseTransparent(true);
                audioTitlePane.setVisible(false);
                audioAnchorPane.setMouseTransparent(true);
                customizeTitlePane.setVisible(true);
                customizeAnchorPane.setMouseTransparent(false);
                controlsTitlePane.setVisible(false);
                controlsAnchorPane.setMouseTransparent(true);
        }

        @FXML
        void openEditControls(ActionEvent event) {
                videoTitlePane.setVisible(false);
                videoAnchorPane.setMouseTransparent(true);
                audioTitlePane.setVisible(false);
                audioAnchorPane.setMouseTransparent(true);
                customizeTitlePane.setVisible(false);
                customizeAnchorPane.setMouseTransparent(true);
                controlsTitlePane.setVisible(true);
                controlsAnchorPane.setMouseTransparent(false);
        }

        //Video
        //schermo intero
        @FXML
        void fullscreen(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                boolean fullScreen = fullscreenCheck.isSelected();
                stage.setFullScreen(fullScreen);
                applyButton.setDisable(false);
                resolutionWidthField.setDisable(fullScreen);
                resolutionHeightField.setDisable(fullScreen);
        }

        @FXML
        void lockResolution(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setResizable(!lockResolutionCheck.isSelected());
                applyButton.setDisable(false);
                if(lockResolutionCheck.isSelected()){
                        resolutionWidthField.setDisable(true);
                        resolutionHeightField.setDisable(true);
                }else{
                        resolutionWidthField.setDisable(false);
                        resolutionHeightField.setDisable(false);
                }
        }

        //Audio

        @FXML
        void muteMusic(ActionEvent event) {
                if (musicCheck.isSelected()){
                        Sv = musicSlider.getValue();
                        musicSlider.setValue(0);
                        musicSlider.setDisable(true);
                        view.pauseMusic();
                }else{
                        musicSlider.setValue(Sv);
                        musicSlider.setDisable(false);
                        view.playMusic(Music.MENU);
                }
                applyButton.setDisable(false);
        }

        @FXML
        void muteSFX(ActionEvent event) {
                if (sFXCheck.isSelected()) {
                        Sve = sFXSlider.getValue();
                        sFXSlider.setValue(0);
                        sFXSlider.setDisable(true);
                } else {
                        sFXSlider.setValue(Sve);
                        sFXSlider.setDisable(false);
                }
                applyButton.setDisable(false);
        }

        @FXML
        void playSound(MouseEvent event) {
                view.playSFX(SFX.PAWN_DROP);
        }

        //Personalizzazione
        @FXML
        void whitePawnFillChange(ActionEvent event) {
                Color newValue = whitePawnFillColorPicker.getValue();
                if (newValue!= whitePawn.getFill()) {
                        whitePawn.setFill(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void whitePawnStrokeChange(ActionEvent event) {
                Color newValue = whitePawnStrokeColorPicker.getValue();
                if (newValue!= whitePawn.getStroke()) {
                        whitePawn.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void blackPawnFillChange(ActionEvent event) {
                Color newValue = blackPawnFillColorPicker.getValue();
                if (newValue!= blackPawn.getFill()) {
                        blackPawn.setFill(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void blackPawnStrokeChange(ActionEvent event) {
                Color newValue = blackPawnStrokeColorPicker.getValue();
                if (newValue!= blackPawn.getStroke()) {
                        blackPawn.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void frameColorChange(ActionEvent event) {
                Color newValue = tableColorPicker.getValue();
                if (newValue!= customBoard.getFill()) {
                        customBoard.setFill(newValue);
                        customBoard.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void boardColorChange(ActionEvent event) {
                Color newValue = frameColorPicker.getValue();
                if (newValue!= customFrame.getFill()) {
                        customFrame.setFill(newValue);
                        customFrame.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void evenPointColorChange(ActionEvent event) {
                Color newValue = evenPointColorPicker.getValue();
                if (newValue!= customPoint1.getFill()) {
                        customPoint1.setFill(newValue);
                        customPoint1.setStroke(newValue);
                        customPoint3.setFill(newValue);
                        customPoint3.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void oddPointColorChange(ActionEvent event) {
                Color newValue = oddPointColorPicker.getValue();
                if (newValue!= customPoint2.getFill()) {
                        customPoint2.setFill(newValue);
                        customPoint2.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void selectedPointColorChange(ActionEvent event) {
                Color newValue = selectedPointColorPicker.getValue();
                if (newValue!=selectedPointColor) {
                        selectedPointColor = newValue;
                        regeneratePointAnimation();
                        applyButton.setDisable(false);
                }
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
        void leftPresetAction(ActionEvent event) {
                tableColorPicker.setDisable(true);
                evenPointColorPicker.setDisable(true);
                oddPointColorPicker.setDisable(true);
                selectedPointColorPicker.setDisable(true);
                frameColorPicker.setDisable(true);
                if (logic.getSetting("Customization", "boardPreset", int.class)!=LEFT_PRESET)
                        applyButton.setDisable(false);
        }

        @FXML
        void customPresetAction(ActionEvent event) {
                tableColorPicker.setDisable(false);
                evenPointColorPicker.setDisable(false);
                oddPointColorPicker.setDisable(false);
                selectedPointColorPicker.setDisable(false);
                frameColorPicker.setDisable(false);
                if (logic.getSetting("Customization", "boardPreset", int.class)!=CUSTOM_BOARD)
                        applyButton.setDisable(false);
        }

        @FXML
        void rightPresetAction(ActionEvent event) {
                tableColorPicker.setDisable(true);
                evenPointColorPicker.setDisable(true);
                oddPointColorPicker.setDisable(true);
                selectedPointColorPicker.setDisable(true);
                frameColorPicker.setDisable(true);
                if (logic.getSetting("Customization", "boardPreset", int.class)!=RIGHT_PRESET)
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
                applyButton.setDisable(true);
                resetButton.setDisable(true);
                mainMenuButton.setDisable(true);
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
                if (!b.getText().equals(bindingBefore))
                        applyButton.setDisable(false);
                resetButton.setDisable(false);
                mainMenuButton.setDisable(false);
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
                applyButton.setDisable(true);
                settingsAnchorPane.setDisable(false);
                App.changeRoot(MAIN_MENU);
                selectedPointAnimation.stop();
                selectedPointPresetsAnimation.stop();
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
                boolean lockResolution = logic.getSetting("Video", "lockResolution", boolean.class);
                boolean fullScreen = logic.getSetting("Video", "fullScreen", boolean.class);
                fullscreenCheck.setSelected(fullScreen);
                fullscreen(null);
                lockResolution(null);
                resolutionWidthField.setDisable(lockResolution || fullScreen);
                resolutionHeightField.setDisable(lockResolution || fullScreen);
                lockResolutionCheck.setSelected(lockResolution);
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setWidth(logic.getSetting("Video", "resolutionWidth", int.class));
                stage.setHeight(logic.getSetting("Video", "resolutionHeight", int.class));
                musicSlider.setValue(logic.getSetting("Audio", "musicVolume", int.class));
                view.setVolume(MUSIC_VOLUME, logic.getSetting("Audio", "musicVolume", int.class));
                sFXSlider.setValue(logic.getSetting("Audio", "soundFXVolume", int.class));
                view.setVolume(SFX_VOLUME, logic.getSetting("Audio", "soundFXVolume", int.class));
                musicCheck.setSelected(logic.getSetting("Audio", "muteMusic", boolean.class));
                sFXCheck.setSelected(logic.getSetting("Audio", "muteSFX", boolean.class));
                musicSlider.setDisable(logic.getSetting("Audio", "muteMusic", boolean.class));
                sFXSlider.setDisable(logic.getSetting("Audio", "muteSFX", boolean.class));
                if (logic.getSetting("Audio", "muteMusic", boolean.class))
                        view.pauseMusic();
                else view.playMusic(Music.MENU);
                switch (logic.getSetting("Customization", "boardPreset", int.class)) {
                        case CUSTOM_BOARD:
                                customRadio.setSelected(true);
                                tableColorPicker.setDisable(false);
                                evenPointColorPicker.setDisable(false);
                                oddPointColorPicker.setDisable(false);
                                selectedPointColorPicker.setDisable(false);
                                frameColorPicker.setDisable(false);
                                break;
                        case LEFT_PRESET:
                                leftPresetRadio.setSelected(true);
                                break;
                        case RIGHT_PRESET:
                                rightPresetRadio.setSelected(true);
                                break;
                }
                whitePawnStrokeColorPicker.setValue(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
                whitePawnFillColorPicker.setValue(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
                blackPawnStrokeColorPicker.setValue(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
                blackPawnFillColorPicker.setValue(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
                whitePawn.setFill(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
                whitePawn.setStroke(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
                blackPawn.setFill(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
                blackPawn.setStroke(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
                frameColorPicker.setValue(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
                tableColorPicker.setValue(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                evenPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                oddPointColorPicker.setValue(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                selectedPointColor = Color.web(logic.getSetting("Customization", "selectedPointColor", String.class));
                selectedPointColorPicker.setValue(selectedPointColor);
                backGroundColorPicker.setValue(Color.web(logic.getSetting("Customization", "backgroundColor", String.class)));
                customFrame.setFill(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
                customFrame.setStroke(Color.web(logic.getSetting("Customization", "boardFrameColor", String.class)));
                customBoard.setFill(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                customBoard.setStroke(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                customPoint1.setFill(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                customPoint1.setStroke(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                customPoint2.setFill(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                customPoint2.setStroke(Color.web(logic.getSetting("Customization", "oddPointsColor", String.class)));
                customPoint3.setFill(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                customPoint3.setStroke(Color.web(logic.getSetting("Customization", "evenPointsColor", String.class)));
                rightTextField.setText(logic.getSetting("Controls", "moveRight", String.class));
                leftTextField.setText(logic.getSetting("Controls", "moveLeft", String.class));
                upTextField.setText(logic.getSetting("Controls", "moveUp", String.class));
                downTextField.setText(logic.getSetting("Controls", "moveDown", String.class));
                selectTextField.setText(logic.getSetting("Controls", "select", String.class));
                revertMoveTextField.setText(logic.getSetting("Controls", "revertMove", String.class));
                finishTurnTextField.setText(logic.getSetting("Controls", "finishTurn", String.class));
                openMenuTextField.setText(logic.getSetting("Controls", "openMenu", String.class));
                resolutionWidthField.setText(String.valueOf(logic.getSetting("Video", "resolutionWidth", int.class)));
                resolutionHeightField.setText(String.valueOf(logic.getSetting("Video", "resolutionHeight", int.class)));
                regeneratePointAnimation();
                applyButton.setDisable(true);
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
                        AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.35);
                        AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.35);

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
                        AnchorPane.setLeftAnchor(backGroundColorPicker,window.getWidth() * 0.45);
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
                        AnchorPane.setLeftAnchor(backGroundColorPicker,window.getWidth() * 0.17);
                        AnchorPane.setLeftAnchor(backgroundText,window.getWidth() * 0.10);

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

                 //languages
                settingsTitlePane.setText(logic.getString("Settings"));
                //videoAnchorPane lang...
                videoButton.setText(logic.getString("Video"));
                videoTitlePane.setText(logic.getString("Video"));
                resolutionText.setText(logic.getString("Resolution"));
                resolutionWidthText.setText(logic.getString("Length"));
                resolutionHeightText.setText(logic.getString("Height"));
                fullscreenCheck.setText(logic.getString("Fullscreen"));
                lockResolutionCheck.setText(logic.getString("BlockResolution"));
                //Audio lang...
                audioButton.setText(logic.getString("Audio"));
                audioTitlePane.setText(logic.getString("Audio"));
                musicText.setText(logic.getString("Music"));
                sFXText.setText(logic.getString("SoundEffects"));
                musicCheck.setText(logic.getString("MuteMusic"));
                sFXCheck.setText(logic.getString("MuteSoundEffects"));
                //Personalizzazione lang...
                customizationButton.setText(logic.getString("Customization"));
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
                //Comandi lang...
                controlsButton.setText(logic.getString("Commands"));
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
                //Attention page lang...
                warningTitlePane.setText(logic.getString("Attention"));
                warningText.setText(logic.getString("AttentionText"));
                exitAndSaveButton.setText(logic.getString("Save"));
                exitNoSaveButton.setText(logic.getString("Don'tSave"));
                cancelButton.setText(logic.getString("Cancel"));
                //BTN Prencipali
                applyButton.setText(logic.getString("Apply"));
                resetButton.setText(logic.getString("Reset"));
                mainMenuButton.setText(logic.getString("MainMenu"));


                 //Video
                fullscreenCheck.setSelected(logic.getSetting("Video", "fullScreen", boolean.class));
                lockResolutionCheck.setSelected(logic.getSetting("Video", "lockResolution", boolean.class));

                if(lockResolutionCheck.isSelected()){
                        resolutionWidthField.setDisable(true);
                        resolutionHeightField.setDisable(true);
                }else{
                        resolutionWidthField.setDisable(false);
                        resolutionHeightField.setDisable(false);
                }

                //Personalizzazione
                group = new ToggleGroup();
                leftPresetRadio.setToggleGroup(group);
                customRadio.setToggleGroup(group);
                rightPresetRadio.setToggleGroup(group);
                switch (logic.getSetting("Customization", "boardPreset", int.class)) {
                        case CUSTOM_BOARD:
                                customRadio.setSelected(true);
                                tableColorPicker.setDisable(false);
                                evenPointColorPicker.setDisable(false);
                                oddPointColorPicker.setDisable(false);
                                selectedPointColorPicker.setDisable(false);
                                frameColorPicker.setDisable(false);
                                break;
                        case LEFT_PRESET:
                                leftPresetRadio.setSelected(true);
                                tableColorPicker.setDisable(true);
                                evenPointColorPicker.setDisable(true);
                                oddPointColorPicker.setDisable(true);
                                selectedPointColorPicker.setDisable(true);
                                frameColorPicker.setDisable(true);
                                break;
                        case RIGHT_PRESET:
                                rightPresetRadio.setSelected(true);
                                tableColorPicker.setDisable(true);
                                evenPointColorPicker.setDisable(true);
                                oddPointColorPicker.setDisable(true);
                                selectedPointColorPicker.setDisable(true);
                                frameColorPicker.setDisable(true);
                                break;
                }



                //color picker
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

                //Oggetti
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

                //musica
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

                //Comandi
                        rightTextField.setText(logic.getSetting("Controls", "moveRight", String.class));
                        leftTextField.setText(logic.getSetting("Controls", "moveLeft", String.class));
                        upTextField.setText(logic.getSetting("Controls", "moveUp", String.class));
                        downTextField.setText(logic.getSetting("Controls", "moveDown", String.class));
                        selectTextField.setText(logic.getSetting("Controls", "select", String.class));
                        revertMoveTextField.setText(logic.getSetting("Controls", "revertMove", String.class));
                        finishTurnTextField.setText(logic.getSetting("Controls", "finishTurn", String.class));
                        openMenuTextField.setText(logic.getSetting("Controls", "openMenu", String.class));

                //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

                openEditVideo();
                selectedPointPresetsAnimation.setCycleCount(Animation.INDEFINITE);
                regeneratePointAnimation();

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
