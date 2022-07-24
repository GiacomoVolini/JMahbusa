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
public class SettingsView {

        private final static int MUSIC_SLIDER = 0;
        private final static int SFX_SLIDER = 1;
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
        //TODO RIPRENDERE A RINOMINARE DA QUI
        @FXML
        private Rectangle cornice1;
        @FXML
        private Rectangle tavolo1;
        @FXML
        private Polygon punta11;
        @FXML
        private Polygon punta21;
        @FXML
        private Polygon punta31;
        @FXML
        private AnchorPane Ancormed;
        @FXML
        private Rectangle cornice11;
        @FXML
        private Rectangle tavolo11;
        @FXML
        private Polygon punta111;
        @FXML
        private Polygon punta211;
        @FXML
        private Polygon punta311;
        @FXML
        private AnchorPane Ancorsini;
        @FXML
        private ColorPicker Ccornice;
        @FXML
        private RadioButton Imdestra;
        @FXML
        private RadioButton Imsinistra;
        @FXML
        private Rectangle cornice;
        @FXML
        private Rectangle tavolo;
        @FXML
        private Polygon punta1;
        @FXML
        private Polygon punta2;
        @FXML
        private Polygon punta3;
        @FXML
        private AnchorPane Ancordes;
        @FXML
        private RadioButton TM;
        @FXML
        protected Button applyButton;
        @FXML
        private AnchorPane comandi;
        @FXML
        private TitledPane Tcomandi;
        @FXML
        private Text Tkeyboard;
        @FXML
        private Text Tmuov;
        @FXML
        private Text Tright;
        @FXML
        private Text Tleft;
        @FXML
        private Text Tup;
        @FXML
        private Text Tdown;
        @FXML
        private Text Tdese;
        @FXML
        private Text Tfinish;
        @FXML
        private Text Tcancellation;

        @FXML
        private Text Tmainmenu;

        @FXML
        private TextField moDestra;

        @FXML
        private TextField moSinistra;

        @FXML
        private TextField moSopra;

        @FXML
        private TextField moSotto;

        @FXML
        private TextField Selezionare;

        @FXML
        private TextField cacellareMo;

        @FXML
        private TextField finitoT;

        @FXML
        private TextField opUscita;

        @FXML
        private ColorPicker selectedPointColorPicker;

        @FXML
        private Text TbackGround;

        @FXML
        private ColorPicker selectedBackGroundColorPicker;

        @FXML
        private TitledPane Pavvertimento;

        @FXML
        private Text Tattenzione;

        @FXML
        private Button applyButton2;

        @FXML
        private Button nonsalvaBTN;

        @FXML
        private Button annullareBTN;

        double Sv, Sve;
        ToggleGroup group = new ToggleGroup();
        private static final boolean LEFT = true;
        private static final boolean RIGHT = false;
        private String bindingBefore;
        private Color selectedPointColor;
        private Timeline selectedPointAnimation;
        private Timeline selectedPointPresetsAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e-> {
                        punta11.setFill(Color.web(logic.getSelectedPointPreset(LEFT)));
                        punta311.setFill(Color.web(logic.getSelectedPointPreset(RIGHT)));
                        punta31.setFill(Color.web(logic.getEvenPointsLeftPreset()));
                        punta111.setFill(Color.web(logic.getEvenPointsRightPreset()));
                }), new KeyFrame(Duration.seconds(0.5), e-> {
                        punta21.setFill(Color.web(logic.getSelectedPointPreset(LEFT)));
                        punta211.setFill(Color.web(logic.getSelectedPointPreset(RIGHT)));
                        punta11.setFill(Color.web(logic.getEvenPointsLeftPreset()));
                        punta311.setFill(Color.web(logic.getEvenPointsRightPreset()));
                }), new KeyFrame(Duration.seconds(1), e-> {
                        punta31.setFill(Color.web(logic.getSelectedPointPreset(LEFT)));
                        punta111.setFill(Color.web(logic.getSelectedPointPreset(RIGHT)));
                        punta21.setFill(Color.web(logic.getOddPointsLeftPreset()));
                        punta211.setFill(Color.web(logic.getOddPointsRightPreset()));
                }), new KeyFrame(Duration.seconds(1.5))
        );

        @FXML
        void goToMainMenu()  throws IOException {
                if(!applyButton.isDisable()){
                        Pavvertimento.setVisible(true);
                        Pavvertimento.setDisable(false);
                        settingsAnchorPane.setDisable(true);
                }else {
                        App.changeRoot(MAIN_MENU);
                }
        }

        @FXML
        void chiudiAttenzioni(ActionEvent event) {
                Pavvertimento.setVisible(false);
                Pavvertimento.setDisable(true);
                settingsAnchorPane.setDisable(false);
        }

        @FXML
        void tornaMainMenu(ActionEvent event) {
                settingsAnchorPane.setDisable(false);
                App.changeRoot(MAIN_MENU);
        }

        @FXML
        void openEditVideo() {
                videoTitlePane.setVisible(true);
                videoAnchorPane.setMouseTransparent(false);
                audioTitlePane.setVisible(false);
                audioAnchorPane.setMouseTransparent(true);
                customizeTitlePane.setVisible(false);
                customizeAnchorPane.setMouseTransparent(true);
                Tcomandi.setVisible(false);
                comandi.setMouseTransparent(true);

        }

        @FXML
        void openEditAudio() {
                videoTitlePane.setVisible(false);
                videoAnchorPane.setMouseTransparent(true);
                audioTitlePane.setVisible(true);
                audioAnchorPane.setMouseTransparent(false);
                customizeTitlePane.setVisible(false);
                customizeAnchorPane.setMouseTransparent(true);
                Tcomandi.setVisible(false);
                comandi.setMouseTransparent(true);
        }

        @FXML
        void openEditPersonalizzazione() {
                videoTitlePane.setVisible(false);
                videoAnchorPane.setMouseTransparent(true);
                audioTitlePane.setVisible(false);
                audioAnchorPane.setMouseTransparent(true);
                customizeTitlePane.setVisible(true);
                customizeAnchorPane.setMouseTransparent(false);
                Tcomandi.setVisible(false);
                comandi.setMouseTransparent(true);
        }

        @FXML
        void openEditComandi(ActionEvent event) {
                videoTitlePane.setVisible(false);
                videoAnchorPane.setMouseTransparent(true);
                audioTitlePane.setVisible(false);
                audioAnchorPane.setMouseTransparent(true);
                customizeTitlePane.setVisible(false);
                customizeAnchorPane.setMouseTransparent(true);
                Tcomandi.setVisible(true);
                comandi.setMouseTransparent(false);
        }

        //Video
        //schermo intero
        @FXML
        void fullscreen(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setFullScreen(fullscreenCheck.isSelected());
                applyButton.setDisable(false);
        }

        @FXML
        void blockresolution(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setResizable(!lockResolutionCheck.isSelected());
                applyButton.setDisable(false);
        }

        //Audio

        @FXML
        void mutaLAmusica(ActionEvent event) {
                if (musicCheck.isSelected()){
                        Sv = musicSlider.getValue();
                        musicSlider.setValue(0);
                        musicSlider.setDisable(true);
                        view.pauseMusic();
                }else{
                        musicSlider.setValue(Sv);
                        musicSlider.setDisable(false);
                        view.playMusic(MENU_MUSIC);
                }
                applyButton.setDisable(false);
        }

        @FXML
        void mutaGLIeffetto(ActionEvent event) {
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
        void soundProva(MouseEvent event) {
                view.playSFX(PAWN_SFX);
        }

        //Personalizzazione
        @FXML
        void coloreINpedina1(ActionEvent event) {
                Color newValue = whitePawnFillColorPicker.getValue();
                if (newValue!= whitePawn.getFill()) {
                        whitePawn.setFill(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreOUTpedina1(ActionEvent event) {
                Color newValue = whitePawnStrokeColorPicker.getValue();
                if (newValue!= whitePawn.getStroke()) {
                        whitePawn.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreINpedina2(ActionEvent event) {
                Color newValue = blackPawnFillColorPicker.getValue();
                if (newValue!= blackPawn.getFill()) {
                        blackPawn.setFill(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreOUTpedina2(ActionEvent event) {
                Color newValue = blackPawnStrokeColorPicker.getValue();
                if (newValue!= blackPawn.getStroke()) {
                        blackPawn.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreTavolo(ActionEvent event) {
                Color newValue = tableColorPicker.getValue();
                if (newValue!=tavolo.getFill()) {
                        tavolo.setFill(newValue);
                        tavolo.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreCornice(ActionEvent event) {
                Color newValue = Ccornice.getValue();
                if (newValue!= cornice.getFill()) {
                        cornice.setFill(newValue);
                        cornice.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void colorePunte(ActionEvent event) {
                Color newValue = evenPointColorPicker.getValue();
                if (newValue!=punta1.getFill()) {
                        punta1.setFill(newValue);
                        punta1.setStroke(newValue);
                        punta3.setFill(newValue);
                        punta3.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void colorePunte2(ActionEvent event) {
                Color newValue = oddPointColorPicker.getValue();
                if (newValue!=punta2.getFill()) {
                        punta2.setFill(newValue);
                        punta2.setStroke(newValue);
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
                                punta3.setFill(selectedPointColor);
                                punta1.setFill(evenPointColorPicker.getValue());
                        }), new KeyFrame(Duration.seconds(0.5), e-> {
                                punta2.setFill(selectedPointColor);
                                punta3.setFill(evenPointColorPicker.getValue());
                        }), new KeyFrame(Duration.seconds(1), e-> {
                                punta1.setFill(selectedPointColor);
                                punta2.setFill(oddPointColorPicker.getValue());
                        }), new KeyFrame(Duration.seconds(1.5))
                );
                selectedPointAnimation.setCycleCount(Animation.INDEFINITE);
                selectedPointAnimation.play();
                selectedPointPresetsAnimation.play();
        }

        @FXML
        void sinistraAction(ActionEvent event) {
                tableColorPicker.setDisable(true);
                evenPointColorPicker.setDisable(true);
                oddPointColorPicker.setDisable(true);
                selectedPointColorPicker.setDisable(true);
                Ccornice.setDisable(true);
                if (logic.getBoardPreset()!=LEFT_PRESET)
                        applyButton.setDisable(false);
        }

        @FXML
        void inMezzo(ActionEvent event) {
                tableColorPicker.setDisable(false);
                evenPointColorPicker.setDisable(false);
                oddPointColorPicker.setDisable(false);
                selectedPointColorPicker.setDisable(false);
                Ccornice.setDisable(false);
                if (logic.getBoardPreset()!=CUSTOM_BOARD)
                        applyButton.setDisable(false);
        }

        @FXML
        void destraAction(ActionEvent event) {
                tableColorPicker.setDisable(true);
                evenPointColorPicker.setDisable(true);
                oddPointColorPicker.setDisable(true);
                selectedPointColorPicker.setDisable(true);
                Ccornice.setDisable(true);
                if (logic.getBoardPreset()!=RIGHT_PRESET)
                        applyButton.setDisable(false);
        }

        //Comandi Action
        int IdiWaiting;
        @FXML
        void selezionata(MouseEvent event) {
                TextField b = (TextField) event.getSource();
                bindingBefore = b.getText();
                b.setText(logic.getString("Waiting"));
                for(int i=0; i<8; i++){
                        if(nomiDiPulsanti[i].getText().equals(logic.getString("Waiting"))){
                                nomiDiPulsanti[i].setDisable(false);
                                IdiWaiting = i;
                        }else{
                                nomiDiPulsanti[i].setDisable(true);
                        }
                }
                applyButton.setDisable(true);
                resetButton.setDisable(true);
                mainMenuButton.setDisable(true);
        }

        protected TextField[] nomiDiPulsanti;
        @FXML
        void stampa(KeyEvent event) {
                TextField b = (TextField) event.getSource();
                for(int i=0; i<8; i++){
                        if(nomiDiPulsanti[i].getText().equals(event.getCode().toString()) && i != IdiWaiting){
                                b.setText(bindingBefore);
                                i=9;
                        }else{
                                b.setText(event.getCode().toString());
                        }
                }
                for(int i=0; i<8; i++){
                        nomiDiPulsanti[i].setDisable(false);
                }
                if (!b.getText().equals(bindingBefore))
                        applyButton.setDisable(false);
                resetButton.setDisable(false);
                mainMenuButton.setDisable(false);
        }
        /*TODO sostituire riferimenti alle seguenti variabili con riferimenti alla rispettiva variabile di SettingsLogic
                - pedIn1 - whitePawnFill - getWhitePawnFill()
                - pedOut1 - whitePawnStroke -
                - pedIn2 - blackPawnFill
                - pedOut2 - blackPawnStroke
                - table - boardInnerColor
                - frame - boardFrameColor
                - point - evenPointsColor
                - point2 - oddPointsColor
                - cb - fullScreen
                - sr - lockResolution
                - mu - muteMusic
                - mue - muteSFX
                - moveTO_X - moveX
                - select - select
                - confirm - confirm
                - finish_turn - finishTurn
                - main_menu - openMenu
         */

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
        void cambiaRisoluzione(KeyEvent event) {
                if(logic.isParsable(resolutionWidthField.getText()) && logic.isParsable(resolutionHeightField.getText())) {
                        logic.setResolutionWidth(Integer.parseInt(resolutionWidthField.getText()));
                        logic.setResolutionHeight(Integer.parseInt(resolutionHeightField.getText()));
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
        }

        @FXML
        void applySettings(ActionEvent event) {
                Stage stage = (Stage) window.getScene().getWindow();
                logic.setFullScreen(fullscreenCheck.isSelected());
                logic.setLockResolution(lockResolutionCheck.isSelected());
                logic.setResolutionWidth(Integer.parseInt(resolutionWidthField.getText()));
                stage.setWidth(logic.getResolutionWidth());
                logic.setResolutionHeight(Integer.parseInt(resolutionHeightField.getText()));
                stage.setHeight(logic.getResolutionHeight());
                logic.setMusicVolume((int) musicSlider.getValue());
                logic.setSFXVolume((int) sFXSlider.getValue());
                logic.setMuteMusic(musicCheck.isSelected());
                logic.setMuteSFX(sFXCheck.isSelected());
                if(TM.isSelected())
                        logic.setBoardPreset(CUSTOM_BOARD);
                else if (Imsinistra.isSelected())
                        logic.setBoardPreset(LEFT_PRESET);
                else logic.setBoardPreset(RIGHT_PRESET);
                logic.setWhitePawnStroke(colorStringFactory(whitePawnStrokeColorPicker.getValue()));
                logic.setWhitePawnFill(colorStringFactory(whitePawnFillColorPicker.getValue()));
                logic.setBlackPawnStroke(colorStringFactory(blackPawnStrokeColorPicker.getValue()));
                logic.setBlackPawnFill(colorStringFactory(blackPawnFillColorPicker.getValue()));
                logic.setBoardFrameColor(colorStringFactory(Ccornice.getValue()));
                logic.setBoardInnerColor(colorStringFactory(tableColorPicker.getValue()));
                logic.setEvenPointsColor(colorStringFactory(evenPointColorPicker.getValue()));
                logic.setOddPointsColor(colorStringFactory(oddPointColorPicker.getValue()));
                logic.setSelectedPointColor(colorStringFactory(selectedPointColorPicker.getValue()));
                logic.setMoveRight(moDestra.getText());
                logic.setMoveLeft(moSinistra.getText());
                logic.setMoveUp(moSopra.getText());
                logic.setMoveDown(moSotto.getText());
                logic.setSelect(Selezionare.getText());
                logic.setRevertMove(cacellareMo.getText());
                logic.setFinishTurn(finitoT.getText());
                logic.setOpenMenu(opUscita.getText());
                logic.setBackgroundColor(colorStringFactory(selectedBackGroundColorPicker.getValue()));
                logic.applySettingsChanges();
                applyButton.setDisable(true);
        }

        @FXML
        void resetToDefaults(ActionEvent event) {
                logic.resetDefaultSettings();
                fullscreenCheck.setSelected(logic.getFullScreen());
                fullscreen(null);
                blockresolution(null);
                lockResolutionCheck.setSelected(logic.getLockResolution());
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setWidth(logic.getResolutionWidth());
                stage.setHeight(logic.getResolutionHeight());
                resolutionWidthField.setText(String.valueOf(logic.getResolutionWidth()));
                resolutionHeightField.setText(String.valueOf(logic.getResolutionHeight()));
                musicSlider.setValue(logic.getMusicVolume());
                view.setMusicVolume(logic.getMusicVolume());
                sFXSlider.setValue(logic.getSFXVolume());
                view.setSFXVolume(logic.getSFXVolume());
                musicCheck.setSelected(logic.getMuteMusic());
                sFXCheck.setSelected(logic.getMuteSFX());
                musicSlider.setDisable(logic.getMuteMusic());
                sFXSlider.setDisable(logic.getMuteSFX());
                if (logic.getMuteMusic())
                        view.pauseMusic();
                else view.playMusic(MENU_MUSIC);
                switch (logic.getBoardPreset()) {
                        case CUSTOM_BOARD:
                                TM.setSelected(true);
                                tableColorPicker.setDisable(false);
                                evenPointColorPicker.setDisable(false);
                                oddPointColorPicker.setDisable(false);
                                selectedPointColorPicker.setDisable(false);
                                Ccornice.setDisable(false);
                                break;
                        case LEFT_PRESET:
                                Imsinistra.setSelected(true);
                                break;
                        case RIGHT_PRESET:
                                Imdestra.setSelected(true);
                                break;
                }
                whitePawnStrokeColorPicker.setValue(Color.web(logic.getWhitePawnStroke()));
                whitePawnFillColorPicker.setValue(Color.web(logic.getWhitePawnFill()));
                blackPawnStrokeColorPicker.setValue(Color.web(logic.getBlackPawnStroke()));
                blackPawnFillColorPicker.setValue(Color.web(logic.getBlackPawnFill()));
                whitePawn.setFill(Color.web(logic.getWhitePawnFill()));
                whitePawn.setStroke(Color.web(logic.getWhitePawnStroke()));
                blackPawn.setFill(Color.web(logic.getBlackPawnFill()));
                blackPawn.setStroke(Color.web(logic.getBlackPawnStroke()));
                Ccornice.setValue(Color.web(logic.getBoardFrameColor()));
                tableColorPicker.setValue(Color.web(logic.getBoardInnerColor()));
                evenPointColorPicker.setValue(Color.web(logic.getEvenPointsColor()));
                oddPointColorPicker.setValue(Color.web(logic.getOddPointsColor()));
                selectedPointColor = Color.web(logic.getSelectedPointColor());
                selectedPointColorPicker.setValue(selectedPointColor);
                cornice.setFill(Color.web(logic.getBoardFrameColor()));
                cornice.setStroke(Color.web(logic.getBoardFrameColor()));
                tavolo.setFill(Color.web(logic.getBoardInnerColor()));
                tavolo.setStroke(Color.web(logic.getBoardInnerColor()));
                punta1.setFill(Color.web(logic.getEvenPointsColor()));
                punta1.setStroke(Color.web(logic.getEvenPointsColor()));
                punta2.setFill(Color.web(logic.getOddPointsColor()));
                punta2.setStroke(Color.web(logic.getOddPointsColor()));
                punta3.setFill(Color.web(logic.getEvenPointsColor()));
                punta3.setStroke(Color.web(logic.getEvenPointsColor()));
                moDestra.setText(logic.getMoveRight());
                moSinistra.setText(logic.getMoveLeft());
                moSopra.setText(logic.getMoveUp());
                moSotto.setText(logic.getMoveDown());
                Selezionare.setText(logic.getSelect());
                cacellareMo.setText(logic.getRevertMove());
                finitoT.setText(logic.getFinishTurn());
                opUscita.setText(logic.getOpenMenu());
                regeneratePointAnimation();
                applyButton.setDisable(true);
        }

        protected void changeDimensions() {
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
                //Video

                AnchorPane.setLeftAnchor(resolutionText, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(resolutionHeightText, window.getWidth() * 0.13);
                AnchorPane.setLeftAnchor(resolutionWidthText, window.getWidth() * 0.13);
                AnchorPane.setLeftAnchor(resolutionHeightField, window.getWidth() * 0.28);
                AnchorPane.setLeftAnchor(resolutionWidthField, window.getWidth() * 0.28);
                AnchorPane.setLeftAnchor(fullscreenCheck, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(lockResolutionCheck, window.getWidth() * 0.4);
                resolutionHeightField.setText(String.valueOf((int)stage.getHeight()));
                resolutionWidthField.setText(String.valueOf((int)stage.getWidth()));

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
                AnchorPane.setLeftAnchor(whitePawnText, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(whitePawnFillColorPicker, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(whitePawnStrokeColorPicker, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(whitePawn, window.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(tableText, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Ancorsini, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Imsinistra, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Ancormed, window.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(TM, window.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(Ancordes, window.getWidth() * 0.50);
                AnchorPane.setLeftAnchor(Imdestra, window.getWidth() * 0.50);
                AnchorPane.setLeftAnchor(tableColorPicker, window.getWidth() * 0.11);
                AnchorPane.setLeftAnchor(pointText, window.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(oddPointColorPicker, window.getWidth() * 0.30 + 7);
                AnchorPane.setLeftAnchor(evenPointColorPicker,AnchorPane.getLeftAnchor(oddPointColorPicker) - 56);
                AnchorPane.setLeftAnchor(selectedPointColorPicker, AnchorPane.getLeftAnchor(oddPointColorPicker) + 56);
                AnchorPane.setLeftAnchor(frameText, window.getWidth() * 0.50);
                AnchorPane.setLeftAnchor(Ccornice, window.getWidth() * 0.51);
                AnchorPane.setLeftAnchor(blackPawnText, window.getWidth() * 0.40);
                AnchorPane.setLeftAnchor(blackPawnFillColorPicker, window.getWidth() * 0.40);
                AnchorPane.setLeftAnchor(blackPawnStrokeColorPicker, window.getWidth() * 0.40);
                AnchorPane.setLeftAnchor(blackPawn, window.getWidth() * 0.50);

                //Comandi
                AnchorPane.setLeftAnchor(Tkeyboard, window.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Tmuov, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tright, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tleft, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tup, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tdown, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(moDestra, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(moSinistra, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(moSopra, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(moSotto, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(Tdese, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tcancellation, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tfinish, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tmainmenu, window.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Selezionare, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(cacellareMo, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(finitoT, window.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(opUscita, window.getWidth() * 0.45);

                //schermi
                settingsAnchorPane.setPrefWidth(window.getWidth()/4);
                settingsTitlePane.setPrefWidth(window.getWidth()/4);
                Pavvertimento.setLayoutX(window.getWidth()/2 - 206);
                Pavvertimento.setLayoutY(window.getHeight()/2 -103);

                videoAnchorPane.setLayoutX(window.getWidth()/4);
                videoTitlePane.setLayoutX(window.getWidth()/4);
                audioAnchorPane.setLayoutX(window.getWidth()/4);
                audioTitlePane.setLayoutX(window.getWidth()/4);
                customizeAnchorPane.setLayoutX(window.getWidth()/4);
                customizeTitlePane.setLayoutX(window.getWidth()/4);
                comandi.setLayoutX(window.getWidth()/4);
                Tcomandi.setLayoutX(window.getWidth()/4);

                videoAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                videoTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                audioAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                audioTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                customizeAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                customizeTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                comandi.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
                Tcomandi.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);

                settingsAnchorPane.setPrefHeight(window.getWidth());
                settingsTitlePane.setPrefHeight(window.getWidth());
                videoAnchorPane.setPrefHeight(window.getHeight());
                videoTitlePane.setPrefHeight(window.getHeight());
                audioAnchorPane.setPrefHeight(window.getHeight());
                audioTitlePane.setPrefHeight(window.getHeight());
                customizeAnchorPane.setPrefHeight(window.getHeight());
                customizeTitlePane.setPrefHeight(window.getHeight());
                comandi.setPrefHeight(window.getHeight());
                Tcomandi.setPrefHeight(window.getHeight());

                if (((int)stage.getHeight() != logic.getResolutionHeight() ||
                        (int)stage.getWidth() != logic.getResolutionWidth()) &&
                        (int)stage.getWidth()!= 0 && (int)stage.getHeight() != 0) {
                        applyButton.setDisable(false);
                }
        }
        public void initialize() {
                 nomiDiPulsanti = new TextField[] {this.moDestra, this.moSinistra, this.moSopra, this.moSotto, this.Selezionare, this.cacellareMo, this.finitoT, this.opUscita};

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
                musicText.setText(logic.getString("musicText"));
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
                Imsinistra.setText(logic.getString("Preset"));
                TM.setText(logic.getString("Custom"));
                Imdestra.setText(logic.getString("Preset"));
                //Comandi lang...
                controlsButton.setText(logic.getString("Commands"));
                Tcomandi.setText(logic.getString("Commands"));
                Tkeyboard.setText(logic.getString("Keyboard"));
                Tmuov.setText(logic.getString("Move"));
                Tright.setText(logic.getString("Right"));
                Tleft.setText(logic.getString("Left"));
                Tup.setText(logic.getString("Up"));
                Tdown.setText(logic.getString("Down"));
                Tdese.setText(logic.getString("Select"));
                Tcancellation.setText(logic.getString("DeleteMove"));
                Tfinish.setText(logic.getString("FinishTurn"));
                Tmainmenu.setText(logic.getString("TmainMenu"));
                //Attention page lang...
                Pavvertimento.setText(logic.getString("Attention"));
                Tattenzione.setText(logic.getString("AttentionText"));
                applyButton2.setText(logic.getString("Save"));
                nonsalvaBTN.setText(logic.getString("Don'tSave"));
                annullareBTN.setText(logic.getString("Cancel"));
                //BTN Prencipali
                applyButton.setText(logic.getString("Apply"));
                resetButton.setText(logic.getString("Reset"));
                mainMenuButton.setText(logic.getString("MainMenu"));


                 //Video
                fullscreenCheck.setSelected(logic.getFullScreen());
                lockResolutionCheck.setSelected(logic.getLockResolution());


                //Personalizzazione
                group = new ToggleGroup();
                Imsinistra.setToggleGroup(group);
                TM.setToggleGroup(group);
                Imdestra.setToggleGroup(group);
                switch (logic.getBoardPreset()) {
                        case CUSTOM_BOARD:
                                TM.setSelected(true);
                                tableColorPicker.setDisable(false);
                                evenPointColorPicker.setDisable(false);
                                oddPointColorPicker.setDisable(false);
                                selectedPointColorPicker.setDisable(false);
                                Ccornice.setDisable(false);
                                break;
                        case LEFT_PRESET:
                                Imsinistra.setSelected(true);
                                tableColorPicker.setDisable(true);
                                evenPointColorPicker.setDisable(true);
                                oddPointColorPicker.setDisable(true);
                                selectedPointColorPicker.setDisable(true);
                                Ccornice.setDisable(true);
                                break;
                        case RIGHT_PRESET:
                                Imdestra.setSelected(true);
                                tableColorPicker.setDisable(true);
                                evenPointColorPicker.setDisable(true);
                                oddPointColorPicker.setDisable(true);
                                selectedPointColorPicker.setDisable(true);
                                Ccornice.setDisable(true);
                                break;
                }



                //color picker
                whitePawnFillColorPicker.setValue(Color.web(logic.getWhitePawnFill()));
                whitePawnStrokeColorPicker.setValue(Color.web(logic.getWhitePawnStroke()));
                blackPawnFillColorPicker.setValue(Color.web(logic.getBlackPawnFill()));
                blackPawnStrokeColorPicker.setValue(Color.web(logic.getBlackPawnStroke()));
                tableColorPicker.setValue(Color.web(logic.getBoardInnerColor(true)));
                Ccornice.setValue(Color.web(logic.getBoardFrameColor(true)));
                evenPointColorPicker.setValue(Color.web(logic.getEvenPointsColor(true)));
                oddPointColorPicker.setValue(Color.web(logic.getOddPointsColor(true)));
                selectedPointColor = Color.web(logic.getSelectedPointColor(true));
                selectedPointColorPicker.setValue(selectedPointColor);

                //Oggetti
                whitePawn.setFill(whitePawnFillColorPicker.getValue());
                whitePawn.setStroke(whitePawnStrokeColorPicker.getValue());

                blackPawn.setFill(blackPawnFillColorPicker.getValue());
                blackPawn.setStroke(blackPawnStrokeColorPicker.getValue());

                tavolo.setFill(tableColorPicker.getValue());
                tavolo.setStroke(tableColorPicker.getValue());

                cornice.setFill(Ccornice.getValue());
                cornice.setStroke(Ccornice.getValue());

                punta1.setFill(evenPointColorPicker.getValue());
                punta1.setStroke(evenPointColorPicker.getValue());
                punta2.setFill(oddPointColorPicker.getValue());
                punta2.setStroke(oddPointColorPicker.getValue());
                punta3.setFill(evenPointColorPicker.getValue());
                punta3.setStroke(evenPointColorPicker.getValue());

                //musica
                        musicCheck.setSelected(logic.getMuteMusic());
                        sFXCheck.setSelected(logic.getMuteSFX());
                        musicSlider.setValue(logic.getMusicVolume());
                        musicSlider.setValue(logic.getMusicVolume());
                        sFXSlider.setValue(logic.getSFXVolume());
                        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> checkSliderChanges(newVal, MUSIC_SLIDER));
                        sFXSlider.valueProperty().addListener((obs, oldVal, newVal) -> checkSliderChanges(newVal, SFX_SLIDER));

                //Comandi
                        moDestra.setText(logic.getMoveRight());
                        moSinistra.setText(logic.getMoveLeft());
                        moSopra.setText(logic.getMoveUp());
                        moSotto.setText(logic.getMoveDown());
                        Selezionare.setText(logic.getSelect());
                        cacellareMo.setText(logic.getRevertMove());
                        finitoT.setText(logic.getFinishTurn());
                        opUscita.setText(logic.getOpenMenu());

                //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

                openEditVideo();
                selectedPointPresetsAnimation.setCycleCount(Animation.INDEFINITE);
                regeneratePointAnimation();

        }

        private void checkSliderChanges (Number value, int whichSlider) {
                int savedValue = 0;
                int sliderValue = (int)(value.doubleValue());
                switch (whichSlider) {
                        case MUSIC_SLIDER:
                                savedValue = logic.getMusicVolume();
                                view.setMusicVolume(value.doubleValue()/100);
                                break;
                        case SFX_SLIDER:
                                savedValue = logic.getSFXVolume();
                                view.setSFXVolume(value.doubleValue()/100);
                                break;
                }
                if (savedValue != sliderValue)
                        applyButton.setDisable(false);
        }



}
