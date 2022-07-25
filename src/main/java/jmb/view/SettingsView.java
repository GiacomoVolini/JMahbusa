package jmb.view;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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
        private Stage stage;
        @FXML
        private AnchorPane GBG;

        @FXML
        private AnchorPane Sbackgraound;

        @FXML
        private TitledPane Timpostazioni;

        @FXML
        private ImageView bgsinistra;

        @FXML
        private Button Bvideo;

        @FXML
        private Button Baudio;

        @FXML
        private Button Bpersonalizzazione;

        @FXML
        private Button Bcomandi;

        @FXML
        private Button mainMenuButton;

        @FXML
        private AnchorPane video;

        @FXML
        private TitledPane Tvideo;

        @FXML
        private ImageView Vbg;

        @FXML
        private CheckBox checkSI;

        @FXML
        private CheckBox checkBR;

        @FXML
        private Text tResolution;

        @FXML
        private Text tLarghezza;

        @FXML
        private Text tAltezza;

        @FXML
        private TextField fLarghezza;

        @FXML
        private TextField fAltezza;

        @FXML
        private AnchorPane audio;

        @FXML
        private TitledPane Taudio;

        @FXML
        private ImageView Abg;

        @FXML
        private Text Music;

        @FXML
        private CheckBox checkMusi;

        @FXML
        private Slider SliderMusi;

        @FXML
        private CheckBox checkMES;

        @FXML
        private Text Esonori;

        @FXML
        private Slider SliderES;

        @FXML
        private AnchorPane personalizzazione;

        @FXML
        private TitledPane Tpersonalizzazione;

        @FXML
        private ImageView Ibg;

        @FXML
        private Button resetButton;

        @FXML
        private ColorPicker Inpedina1;

        @FXML
        private Text Tpedgioc1;

        @FXML
        private Circle pedina1;

        @FXML
        private ColorPicker Inpedina2;

        @FXML
        private Text Tpedgioc2;

        @FXML
        private Circle pedina2;

        @FXML
        private ColorPicker Conpedina1;

        @FXML
        private ColorPicker Conpedina2;

        @FXML
        private Text Tint;

        @FXML
        private Text Tcont;

        @FXML
        private Text Ttav;

        @FXML
        private Text Tpunt;

        @FXML
        private Text Tcorn;

        @FXML
        private ColorPicker Ctavolo;

        @FXML
        private ColorPicker Cpunte;

        @FXML
        private ColorPicker Cpunte2;

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
                        Sbackgraound.setDisable(true);
                }else {
                        App.changeRoot(MAIN_MENU);
                }
        }

        @FXML
        void chiudiAttenzioni(ActionEvent event) {
                Pavvertimento.setVisible(false);
                Pavvertimento.setDisable(true);
                Sbackgraound.setDisable(false);
        }

        @FXML
        void tornaMainMenu(ActionEvent event) {
                Sbackgraound.setDisable(false);
                App.changeRoot(MAIN_MENU);
        }

        @FXML
        void openEditVideo() {
                Tvideo.setVisible(true);
                video.setMouseTransparent(false);
                Taudio.setVisible(false);
                audio.setMouseTransparent(true);
                Tpersonalizzazione.setVisible(false);
                personalizzazione.setMouseTransparent(true);
                Tcomandi.setVisible(false);
                comandi.setMouseTransparent(true);

        }

        @FXML
        void openEditAudio() {
                Tvideo.setVisible(false);
                video.setMouseTransparent(true);
                Taudio.setVisible(true);
                audio.setMouseTransparent(false);
                Tpersonalizzazione.setVisible(false);
                personalizzazione.setMouseTransparent(true);
                Tcomandi.setVisible(false);
                comandi.setMouseTransparent(true);
        }

        @FXML
        void openEditPersonalizzazione() {
                Tvideo.setVisible(false);
                video.setMouseTransparent(true);
                Taudio.setVisible(false);
                audio.setMouseTransparent(true);
                Tpersonalizzazione.setVisible(true);
                personalizzazione.setMouseTransparent(false);
                Tcomandi.setVisible(false);
                comandi.setMouseTransparent(true);
        }

        @FXML
        void openEditComandi(ActionEvent event) {
                Tvideo.setVisible(false);
                video.setMouseTransparent(true);
                Taudio.setVisible(false);
                audio.setMouseTransparent(true);
                Tpersonalizzazione.setVisible(false);
                personalizzazione.setMouseTransparent(true);
                Tcomandi.setVisible(true);
                comandi.setMouseTransparent(false);
        }

        //Video
        //schermo intero
        @FXML
        void fullscreen(ActionEvent event) {
                Stage stage = (Stage)GBG.getScene().getWindow();
                stage.setFullScreen(checkSI.isSelected());
                applyButton.setDisable(false);
        }

        @FXML
        void blockresolution(ActionEvent event) {
                Stage stage = (Stage)GBG.getScene().getWindow();
                stage.setResizable(!checkBR.isSelected());
                applyButton.setDisable(false);
        }

        //Audio

        @FXML
        void mutaLAmusica(ActionEvent event) {
                if (checkMusi.isSelected()){
                        Sv = SliderMusi.getValue();
                        SliderMusi.setValue(0);
                        SliderMusi.setDisable(true);
                        view.pauseMusic();
                }else{
                        SliderMusi.setValue(Sv);
                        SliderMusi.setDisable(false);
                        view.playMusic(MENU_MUSIC);
                }
                applyButton.setDisable(false);
        }

        @FXML
        void mutaGLIeffetto(ActionEvent event) {
                if (checkMES.isSelected()) {
                        Sve = SliderES.getValue();
                        SliderES.setValue(0);
                        SliderES.setDisable(true);
                } else {
                        SliderES.setValue(Sve);
                        SliderES.setDisable(false);
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
                Color newValue = Inpedina1.getValue();
                if (newValue!=pedina1.getFill()) {
                        pedina1.setFill(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreOUTpedina1(ActionEvent event) {
                Color newValue = Conpedina1.getValue();
                if (newValue!=pedina1.getStroke()) {
                        pedina1.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreINpedina2(ActionEvent event) {
                Color newValue = Inpedina2.getValue();
                if (newValue!=pedina2.getFill()) {
                        pedina2.setFill(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreOUTpedina2(ActionEvent event) {
                Color newValue = Conpedina2.getValue();
                if (newValue!=pedina2.getStroke()) {
                        pedina2.setStroke(newValue);
                        applyButton.setDisable(false);
                }
        }

        @FXML
        void coloreTavolo(ActionEvent event) {
                Color newValue = Ctavolo.getValue();
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
                Color newValue = Cpunte.getValue();
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
                Color newValue = Cpunte2.getValue();
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
                                punta1.setFill(Cpunte.getValue());
                        }), new KeyFrame(Duration.seconds(0.5), e-> {
                                punta2.setFill(selectedPointColor);
                                punta3.setFill(Cpunte.getValue());
                        }), new KeyFrame(Duration.seconds(1), e-> {
                                punta1.setFill(selectedPointColor);
                                punta2.setFill(Cpunte2.getValue());
                        }), new KeyFrame(Duration.seconds(1.5))
                );
                selectedPointAnimation.setCycleCount(Animation.INDEFINITE);
                selectedPointAnimation.play();
                selectedPointPresetsAnimation.play();
        }

        @FXML
        void sinistraAction(ActionEvent event) {
                Ctavolo.setDisable(true);
                Cpunte.setDisable(true);
                Cpunte2.setDisable(true);
                selectedPointColorPicker.setDisable(true);
                Ccornice.setDisable(true);
                if (logic.getBoardPreset()!=LEFT_PRESET)
                        applyButton.setDisable(false);
        }

        @FXML
        void inMezzo(ActionEvent event) {
                Ctavolo.setDisable(false);
                Cpunte.setDisable(false);
                Cpunte2.setDisable(false);
                selectedPointColorPicker.setDisable(false);
                Ccornice.setDisable(false);
                if (logic.getBoardPreset()!=CUSTOM_BOARD)
                        applyButton.setDisable(false);
        }

        @FXML
        void destraAction(ActionEvent event) {
                Ctavolo.setDisable(true);
                Cpunte.setDisable(true);
                Cpunte2.setDisable(true);
                selectedPointColorPicker.setDisable(true);
                Ccornice.setDisable(true);
                if (logic.getBoardPreset()!=RIGHT_PRESET)
                        applyButton.setDisable(false);
        }

        @FXML
        void selectedBackGroundColorChange(ActionEvent event) {
                if(Color.web(logic.getBackgroundColor()) != selectedBackGroundColorPicker.getValue()) {
                        applyButton.setDisable(false);
                }
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
                if(logic.isParsable(fLarghezza.getText()) && logic.isParsable(fAltezza.getText())) {
                        logic.setResolutionWidth(Integer.parseInt(fLarghezza.getText()));
                        logic.setResolutionHeight(Integer.parseInt(fAltezza.getText()));
                        applyButton.setDisable(false);
                }else{
                        applyButton.setDisable(true);
                }
        }


        @FXML
        void applySettingsAndExit(ActionEvent event) {
                applySettings(null);
                applyButton.setDisable(true);
                Sbackgraound.setDisable(false);
                App.changeRoot(MAIN_MENU);
        }

        @FXML
        void applySettings(ActionEvent event) {
                Stage stage = (Stage) GBG.getScene().getWindow();
                logic.setFullScreen(checkSI.isSelected());
                logic.setLockResolution(checkBR.isSelected());
                logic.setResolutionWidth(Integer.parseInt(fLarghezza.getText()));
                stage.setWidth(logic.getResolutionWidth());
                logic.setResolutionHeight(Integer.parseInt(fAltezza.getText()));
                stage.setHeight(logic.getResolutionHeight());
                logic.setMusicVolume((int)SliderMusi.getValue());
                logic.setSFXVolume((int)SliderES.getValue());
                logic.setMuteMusic(checkMusi.isSelected());
                logic.setMuteSFX(checkMES.isSelected());
                if(TM.isSelected())
                        logic.setBoardPreset(CUSTOM_BOARD);
                else if (Imsinistra.isSelected())
                        logic.setBoardPreset(LEFT_PRESET);
                else logic.setBoardPreset(RIGHT_PRESET);
                logic.setWhitePawnStroke(colorStringFactory(Conpedina1.getValue()));
                logic.setWhitePawnFill(colorStringFactory(Inpedina1.getValue()));
                logic.setBlackPawnStroke(colorStringFactory(Conpedina2.getValue()));
                logic.setBlackPawnFill(colorStringFactory(Inpedina2.getValue()));
                logic.setBoardFrameColor(colorStringFactory(Ccornice.getValue()));
                logic.setBoardInnerColor(colorStringFactory(Ctavolo.getValue()));
                logic.setEvenPointsColor(colorStringFactory(Cpunte.getValue()));
                logic.setOddPointsColor(colorStringFactory(Cpunte2.getValue()));
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
                checkSI.setSelected(logic.getFullScreen());
                fullscreen(null);
                blockresolution(null);
                checkBR.setSelected(logic.getLockResolution());
                Stage stage = (Stage) GBG.getScene().getWindow();
                stage.setWidth(logic.getResolutionWidth());
                stage.setHeight(logic.getResolutionHeight());
                fLarghezza.setText(String.valueOf(logic.getResolutionWidth()));
                fAltezza.setText(String.valueOf(logic.getResolutionHeight()));
                SliderMusi.setValue(logic.getMusicVolume());
                view.setMusicVolume(logic.getMusicVolume());
                SliderES.setValue(logic.getSFXVolume());
                view.setSFXVolume(logic.getSFXVolume());
                checkMusi.setSelected(logic.getMuteMusic());
                checkMES.setSelected(logic.getMuteSFX());
                SliderMusi.setDisable(logic.getMuteMusic());
                SliderES.setDisable(logic.getMuteSFX());
                if (logic.getMuteMusic())
                        view.pauseMusic();
                else view.playMusic(MENU_MUSIC);
                switch (logic.getBoardPreset()) {
                        case CUSTOM_BOARD:
                                TM.setSelected(true);
                                Ctavolo.setDisable(false);
                                Cpunte.setDisable(false);
                                Cpunte2.setDisable(false);
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
                Conpedina1.setValue(Color.web(logic.getWhitePawnStroke()));
                Inpedina1.setValue(Color.web(logic.getWhitePawnFill()));
                Conpedina2.setValue(Color.web(logic.getBlackPawnStroke()));
                Inpedina2.setValue(Color.web(logic.getBlackPawnFill()));
                pedina1.setFill(Color.web(logic.getWhitePawnFill()));
                pedina1.setStroke(Color.web(logic.getWhitePawnStroke()));
                pedina2.setFill(Color.web(logic.getBlackPawnFill()));
                pedina2.setStroke(Color.web(logic.getBlackPawnStroke()));
                Ccornice.setValue(Color.web(logic.getBoardFrameColor()));
                Ctavolo.setValue(Color.web(logic.getBoardInnerColor()));
                Cpunte.setValue(Color.web(logic.getEvenPointsColor()));
                Cpunte2.setValue(Color.web(logic.getOddPointsColor()));
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
                Stage stage = (Stage) GBG.getScene().getWindow();
                //bottoni sinistra
                Bvideo.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bvideo.setLayoutY(GBG.getHeight()*0.12);
                Bvideo.setMaxWidth(133);
                Baudio.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Baudio.setLayoutY(GBG.getHeight()*0.20);
                Baudio.setMaxWidth(133);
                Bpersonalizzazione.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bpersonalizzazione.setLayoutY(GBG.getHeight()*0.28);
                Bpersonalizzazione.setMaxWidth(133);
                Bcomandi.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bcomandi.setLayoutY(GBG.getHeight()*0.36);
                Bcomandi.setMaxWidth(133);
                mainMenuButton.setLayoutX(GBG.getWidth()/8 - mainMenuButton.getWidth()/2);
                mainMenuButton.setLayoutY(GBG.getHeight()*0.86);
                mainMenuButton.setMaxWidth(133);
                resetButton.setLayoutX(GBG.getWidth()/8 - mainMenuButton.getWidth()/2);
                resetButton.setLayoutY(GBG.getHeight()*0.78);
                resetButton.setMaxWidth(133);
                applyButton.setLayoutX(GBG.getWidth()/8 - applyButton.getWidth()/2);
                applyButton.setLayoutY(GBG.getHeight()*0.70);
                applyButton.setMaxWidth(133);

                //bottoni destra
                //Video

                AnchorPane.setLeftAnchor(tResolution,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(tAltezza,GBG.getWidth() * 0.13);
                AnchorPane.setLeftAnchor(tLarghezza,GBG.getWidth() * 0.13);
                AnchorPane.setLeftAnchor(fAltezza,GBG.getWidth() * 0.28);
                AnchorPane.setLeftAnchor(fLarghezza,GBG.getWidth() * 0.28);
                AnchorPane.setLeftAnchor(checkSI,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(checkBR,GBG.getWidth() * 0.4);
                fAltezza.setText(String.valueOf((int)stage.getHeight()));
                fLarghezza.setText(String.valueOf((int)stage.getWidth()));

                //Audio
                AnchorPane.setLeftAnchor(Music,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(SliderMusi,GBG.getWidth() * 0.4);
                AnchorPane.setLeftAnchor(Esonori,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(SliderES,GBG.getWidth() * 0.4);
                AnchorPane.setLeftAnchor(checkMusi,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(checkMES,GBG.getWidth() * 0.10);

                //Personalizzazioni
                AnchorPane.setLeftAnchor(Tint,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Tcont,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Tpedgioc1,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Inpedina1,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Conpedina1,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(pedina1,GBG.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(Ttav,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Ancorsini,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Imsinistra,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Ancormed,GBG.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(TM,GBG.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(Ancordes,GBG.getWidth() * 0.50);
                AnchorPane.setLeftAnchor(Imdestra,GBG.getWidth() * 0.50);
                AnchorPane.setLeftAnchor(Ctavolo,GBG.getWidth() * 0.11);
                AnchorPane.setLeftAnchor(Tpunt,GBG.getWidth() * 0.30);
                AnchorPane.setLeftAnchor(Cpunte2,GBG.getWidth() * 0.30 + 7);
                AnchorPane.setLeftAnchor(Cpunte,AnchorPane.getLeftAnchor(Cpunte2) - 56);
                AnchorPane.setLeftAnchor(selectedPointColorPicker, AnchorPane.getLeftAnchor(Cpunte2) + 56);
                AnchorPane.setLeftAnchor(Tcorn,GBG.getWidth() * 0.50);
                AnchorPane.setLeftAnchor(Ccornice,GBG.getWidth() * 0.51);
                AnchorPane.setLeftAnchor(Tpedgioc2,GBG.getWidth() * 0.40);
                AnchorPane.setLeftAnchor(Inpedina2,GBG.getWidth() * 0.40);
                AnchorPane.setLeftAnchor(Conpedina2,GBG.getWidth() * 0.40);
                AnchorPane.setLeftAnchor(pedina2,GBG.getWidth() * 0.50);

                //Comandi
                AnchorPane.setLeftAnchor(Tkeyboard,GBG.getWidth() * 0.10);
                AnchorPane.setLeftAnchor(Tmuov,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tright,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tleft,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tup,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tdown,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(moDestra,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(moSinistra,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(moSopra,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(moSotto,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(Tdese,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tcancellation,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tfinish,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Tmainmenu,GBG.getWidth() * 0.20);
                AnchorPane.setLeftAnchor(Selezionare,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(cacellareMo,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(finitoT,GBG.getWidth() * 0.45);
                AnchorPane.setLeftAnchor(opUscita,GBG.getWidth() * 0.45);

                //schermi
                Sbackgraound.setPrefWidth(GBG.getWidth()/4);
                Timpostazioni.setPrefWidth(GBG.getWidth()/4);
                Pavvertimento.setLayoutX(GBG.getWidth()/2 - 206);
                Pavvertimento.setLayoutY(GBG.getHeight()/2 -103);

                video.setLayoutX(GBG.getWidth()/4);
                Tvideo.setLayoutX(GBG.getWidth()/4);
                audio.setLayoutX(GBG.getWidth()/4);
                Taudio.setLayoutX(GBG.getWidth()/4);
                personalizzazione.setLayoutX(GBG.getWidth()/4);
                Tpersonalizzazione.setLayoutX(GBG.getWidth()/4);
                comandi.setLayoutX(GBG.getWidth()/4);
                Tcomandi.setLayoutX(GBG.getWidth()/4);

                video.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                Tvideo.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                audio.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                Taudio.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                personalizzazione.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                Tpersonalizzazione.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                comandi.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);
                Tcomandi.setPrefWidth(GBG.getWidth()/4 + GBG.getWidth()/4 + GBG.getWidth()/4);

                Sbackgraound.setPrefHeight(GBG.getWidth());
                Timpostazioni.setPrefHeight(GBG.getWidth());
                video.setPrefHeight(GBG.getHeight());
                Tvideo.setPrefHeight(GBG.getHeight());
                audio.setPrefHeight(GBG.getHeight());
                Taudio.setPrefHeight(GBG.getHeight());
                personalizzazione.setPrefHeight(GBG.getHeight());
                Tpersonalizzazione.setPrefHeight(GBG.getHeight());
                comandi.setPrefHeight(GBG.getHeight());
                Tcomandi.setPrefHeight(GBG.getHeight());

                if (((int)stage.getHeight() != logic.getResolutionHeight() ||
                        (int)stage.getWidth() != logic.getResolutionWidth()) &&
                        (int)stage.getWidth()!= 0 && (int)stage.getHeight() != 0) {
                        applyButton.setDisable(false);
                }
        }
        public void initialize() {
                 nomiDiPulsanti = new TextField[] {this.moDestra, this.moSinistra, this.moSopra, this.moSotto, this.Selezionare, this.cacellareMo, this.finitoT, this.opUscita};

                 //languages
                Timpostazioni.setText(logic.getString("Settings"));
                //video lang...
                Bvideo.setText(logic.getString("Video"));
                Tvideo.setText(logic.getString("Video"));
                tResolution.setText(logic.getString("Resolution"));
                tLarghezza.setText(logic.getString("Length"));
                tAltezza.setText(logic.getString("Height"));
                checkSI.setText(logic.getString("Fullscreen"));
                checkBR.setText(logic.getString("BlockResolution"));
                //Audio lang...
                Baudio.setText(logic.getString("Audio"));
                Taudio.setText(logic.getString("Audio"));
                Music.setText(logic.getString("Music"));
                Esonori.setText(logic.getString("SoundEffects"));
                checkMusi.setText(logic.getString("MuteMusic"));
                checkMES.setText(logic.getString("MuteSoundEffects"));
                //Personalizzazione lang...
                Bpersonalizzazione.setText(logic.getString("Customization"));
                Tpersonalizzazione.setText(logic.getString("Customization"));
                Tpedgioc1.setText(logic.getString("Player1Pawns"));
                Tpedgioc2.setText(logic.getString("Player2Pawns"));
                Tint.setText(logic.getString("Internal"));
                Tcont.setText(logic.getString("Outline"));
                Ttav.setText(logic.getString("Table"));
                Tpunt.setText(logic.getString("Points"));
                Tcorn.setText(logic.getString("Frame"));
                Imsinistra.setText(logic.getString("Preset"));
                TM.setText(logic.getString("Custom"));
                Imdestra.setText(logic.getString("Preset"));
                TbackGround.setText(logic.getString("BackGround"));

                //Comandi lang...
                Bcomandi.setText(logic.getString("Commands"));
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
                Tmainmenu.setText(logic.getString("MainMenu"));
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
                checkSI.setSelected(logic.getFullScreen());
                checkBR.setSelected(logic.getLockResolution());


                //Personalizzazione
                group = new ToggleGroup();
                Imsinistra.setToggleGroup(group);
                TM.setToggleGroup(group);
                Imdestra.setToggleGroup(group);
                switch (logic.getBoardPreset()) {
                        case CUSTOM_BOARD:
                                TM.setSelected(true);
                                Ctavolo.setDisable(false);
                                Cpunte.setDisable(false);
                                Cpunte2.setDisable(false);
                                selectedPointColorPicker.setDisable(false);
                                Ccornice.setDisable(false);
                                break;
                        case LEFT_PRESET:
                                Imsinistra.setSelected(true);
                                Ctavolo.setDisable(true);
                                Cpunte.setDisable(true);
                                Cpunte2.setDisable(true);
                                selectedPointColorPicker.setDisable(true);
                                Ccornice.setDisable(true);
                                break;
                        case RIGHT_PRESET:
                                Imdestra.setSelected(true);
                                Ctavolo.setDisable(true);
                                Cpunte.setDisable(true);
                                Cpunte2.setDisable(true);
                                selectedPointColorPicker.setDisable(true);
                                Ccornice.setDisable(true);
                                break;
                }



                //color picker
                Inpedina1.setValue(Color.web(logic.getWhitePawnFill()));
                Conpedina1.setValue(Color.web(logic.getWhitePawnStroke()));
                Inpedina2.setValue(Color.web(logic.getBlackPawnFill()));
                Conpedina2.setValue(Color.web(logic.getBlackPawnStroke()));
                Ctavolo.setValue(Color.web(logic.getBoardInnerColor(true)));
                Ccornice.setValue(Color.web(logic.getBoardFrameColor(true)));
                Cpunte.setValue(Color.web(logic.getEvenPointsColor(true)));
                Cpunte2.setValue(Color.web(logic.getOddPointsColor(true)));
                selectedPointColor = Color.web(logic.getSelectedPointColor(true));
                selectedPointColorPicker.setValue(selectedPointColor);

                //Oggetti
                pedina1.setFill(Inpedina1.getValue());
                pedina1.setStroke(Conpedina1.getValue());

                pedina2.setFill(Inpedina2.getValue());
                pedina2.setStroke(Conpedina2.getValue());

                tavolo.setFill(Ctavolo.getValue());
                tavolo.setStroke(Ctavolo.getValue());

                cornice.setFill(Ccornice.getValue());
                cornice.setStroke(Ccornice.getValue());

                punta1.setFill(Cpunte.getValue());
                punta1.setStroke(Cpunte.getValue());
                punta2.setFill(Cpunte2.getValue());
                punta2.setStroke(Cpunte2.getValue());
                punta3.setFill(Cpunte.getValue());
                punta3.setStroke(Cpunte.getValue());

                //musica
                        checkMusi.setSelected(logic.getMuteMusic());
                        checkMES.setSelected(logic.getMuteSFX());
                        SliderMusi.setValue(logic.getMusicVolume());
                        SliderMusi.setValue(logic.getMusicVolume());
                        SliderES.setValue(logic.getSFXVolume());
                        SliderMusi.valueProperty().addListener((obs, oldVal, newVal) -> checkSliderChanges(newVal, MUSIC_SLIDER));
                        SliderES.valueProperty().addListener((obs, oldVal, newVal) -> checkSliderChanges(newVal, SFX_SLIDER));

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
                GBG.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                GBG.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

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
