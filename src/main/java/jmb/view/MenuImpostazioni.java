package jmb.view;
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

import static jmb.view.ConstantsView.*;
import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;

import java.io.IOException;

public class MenuImpostazioni  {

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
        private Text Gtext;

        @FXML
        private CheckBox checkSI;

        @FXML
        private Slider SliderGT;

        @FXML
        private CheckBox checkBR;

        @FXML
        private Text Luminosita;

        @FXML
        private Slider SliderL;

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

        //@FXML TODO CANCELLARE
        //private Button RA;

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
        private Button applyButton;

        //@FXML TODO CANCELLARE
        //private Button Reset;

        @FXML
        private AnchorPane comandi;

        @FXML
        private TitledPane Tcomandi;

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
        private TextField confermare;

        @FXML
        private TextField cacellareMo;

        @FXML
        private TextField finitoT;

        @FXML
        private TextField opUscita;

        //@FXML TODO CANCELLARE
        //private Button reComandi;

        @FXML
        private Button Bvuoto1;

        @FXML
        private Button Bvuoto2;

        double Sv, Sve;
        ToggleGroup group = new ToggleGroup();
        private String bindingBefore;

        @FXML
        void goToMainMenu()  throws IOException {
                //TODO controllare se ci sono cambiamenti alle impostazioni senza aver pigiato apply
                //      se sono stati fatti, aprire un dialogo che avverte che così si perderanno i cambiamenti
                //      altrimenti andare al menu
                mainMenuButton.getScene().getWindow();
                App.MainMenu();
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
                        mu = muta;
                        SliderMusi.setValue(0);
                        SliderMusi.setDisable(true);
                        View.sceneMusica.player.pause();
                }else{
                        mu = nonmuta;
                        SliderMusi.setValue(Sv);
                        SliderMusi.setDisable(false);
                        View.sceneMusica.player.play();
                }
                applyButton.setDisable(false);
        }

        @FXML
        void mutaGLIeffetto(ActionEvent event) {
                if (checkMES.isSelected()) {
                        Sve = SliderES.getValue();
                        mue = mutae;
                        SliderES.setValue(0);
                        SliderES.setDisable(true);
                } else {
                        mue = nonmutae;
                        SliderES.setValue(Sve);
                        SliderES.setDisable(false);
                }
                applyButton.setDisable(false);
        }

        @FXML
        void soundProva(MouseEvent event) {
                View.sceneMusica.Pawn.play();
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
        void sinistraAction(ActionEvent event) {
                Ctavolo.setDisable(true);
                Cpunte.setDisable(true);
                Cpunte2.setDisable(true);
                Ccornice.setDisable(true);
                if (logic.getBoardPreset()!=LEFT_PRESET)
                        applyButton.setDisable(false);
        }

        @FXML
        void inMezzo(ActionEvent event) {
                Ctavolo.setDisable(false);
                Cpunte.setDisable(false);
                Cpunte2.setDisable(false);
                Ccornice.setDisable(false);
                if (logic.getBoardPreset()!=CUSTOM_BOARD)
                        applyButton.setDisable(false);
        }

        @FXML
        void destraAction(ActionEvent event) {
                Ctavolo.setDisable(true);
                Cpunte.setDisable(true);
                Cpunte2.setDisable(true);
                Ccornice.setDisable(true);
                if (logic.getBoardPreset()!=RIGHT_PRESET)
                        applyButton.setDisable(false);
        }

        //TODO riempi l'applicazioni

        //Comandi Action
        @FXML
        void selezionata(MouseEvent event) {
                TextField b = (TextField) event.getSource();
                bindingBefore = b.getText();
                b.setText("waiting...");
                applyButton.setDisable(true);
                resetButton.setDisable(true);
                mainMenuButton.setDisable(true);
        }

        @FXML
        void stampa(KeyEvent event) {
                TextField b = (TextField) event.getSource();
                b.setText(event.getCode().toString());
                if (!b.getText().equals(bindingBefore))
                        applyButton.setDisable(false);
                resetButton.setDisable(false);
                mainMenuButton.setDisable(false);
        }

        /*@Override
        public void handle(KeyEvent event) {


                moSinistra.setText(event.getCode().toString());
                moSopra.setText(event.getCode().toString());
                moSotto.setText(event.getCode().toString());
        }*/

       /* @FXML
        void cambiaDestra(MouseEvent event) {

        }

        @FXML
        void cambiaCancellazione(MouseEvent event) {

        }

        @FXML
        void cambiaConferma(MouseEvent event) {

        }

        @FXML
        void cambiaDeSelez(MouseEvent event) {

        }

        @FXML
        void cambiaFinish(MouseEvent event) {

        }

        @FXML
        void cambiaSinistra(MouseEvent event) {

        }

        @FXML
        void cambiaSopra(MouseEvent event) {

        }

        @FXML
        void cambiaSotto(MouseEvent event) {

        }

        @FXML
        void cambiaUscita(MouseEvent event) {

        }*/


        //TODO salva i cambiamenti dei impostazzioni anche se chiudi il gioco
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

        //TODO FORSE SPOSTARE ---
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
        //TODO FORSE SPOSTARE ^^^

        @FXML
        void applySettings(ActionEvent event) {
                logic.setFullScreen(checkSI.isSelected());
                logic.setLockResolution(checkBR.isSelected());
                //logic.setResolutionWidth();TODO AGGIUNGERE COMPONENTE PER INFLUENZARE RISOLUZIONE
                //logic.setResolutionHeight();TODO AGGIUNGERE COMPONENTE PER INFLUENZARE RISOLUZIONE
                logic.setMusicVolume((int)SliderMusi.getValue()*100);
                logic.setSFXVolume((int)SliderES.getValue()*100);
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
                logic.setMoveRight(moDestra.getText());
                logic.setMoveLeft(moSinistra.getText());
                logic.setMoveUp(moSopra.getText());
                logic.setMoveDown(moSotto.getText());
                logic.setSelect(Selezionare.getText());
                logic.setConfirm(confermare.getText());
                logic.setRevertMove(cacellareMo.getText());
                logic.setFinishTurn(finitoT.getText());
                logic.setOpenMenu(opUscita.getText());
                logic.applySettingsChanges();
        }

        @FXML
        void resetToDefaults(ActionEvent event) {
                logic.resetDefaultSettings();
                checkSI.setSelected(logic.getFullScreen());
                checkBR.setSelected(logic.getLockResolution());
                //TODO AGGIUNGERE COMPONENTE PER INFLUENZARE RISOLUZIONE
                //TODO AGGIUNGERE COMPONENTE PER INFLUENZARE RISOLUZIONE
                SliderMusi.setValue(logic.getMusicVolume()/100.0);
                SliderES.setValue(logic.getSFXVolume()/100.0);
                checkMusi.setSelected(logic.getMuteMusic());
                checkMES.setSelected(logic.getMuteSFX());
                switch (logic.getBoardPreset()) {
                        case CUSTOM_BOARD:
                                TM.setSelected(true);
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
                //TODO IMPOSTARE ANCHE I COLORI ALLE PEDINE DEL MENU
                Ccornice.setValue(Color.web(logic.getBoardFrameColor()));
                Ctavolo.setValue(Color.web(logic.getBoardInnerColor()));
                Cpunte.setValue(Color.web(logic.getEvenPointsColor()));
                Cpunte2.setValue(Color.web(logic.getOddPointsColor()));
                //TODO IMPOSTARE ANCHE I COLORI AL MINITABELLONE CENTRALE
                moDestra.setText(logic.getMoveRight());
                moSinistra.setText(logic.getMoveLeft());
                moSopra.setText(logic.getMoveUp());
                moSotto.setText(logic.getMoveDown());
                Selezionare.setText(logic.getSelect());
                confermare.setText(logic.getConfirm());
                cacellareMo.setText(logic.getRevertMove());
                finitoT.setText(logic.getFinishTurn());
                opUscita.setText(logic.getOpenMenu());
        }

        protected void changeDimensions() {
                //TODO DA MODIFICARE
                //bottoni sinistra
                Bvideo.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bvideo.setMaxWidth(133);
                Baudio.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Baudio.setMaxWidth(133);
                Bpersonalizzazione.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bpersonalizzazione.setMaxWidth(133);
                Bcomandi.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bcomandi.setMaxWidth(133);
                Bvuoto1.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bvuoto1.setMaxWidth(133);
                Bvuoto2.setLayoutX(GBG.getWidth()/8 - Bvideo.getWidth()/2);
                Bvuoto2.setMaxWidth(133);
                mainMenuButton.setLayoutX(GBG.getWidth()/8 - mainMenuButton.getWidth()/2);
                mainMenuButton.setMaxWidth(89);
                applyButton.setLayoutX(GBG.getWidth()/8 - applyButton.getWidth()/2);
                applyButton.setMaxWidth(58);

                //bottoni destra
                //Video
                GBG.setLeftAnchor(Gtext,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(Luminosita,GBG.getWidth() * 0.4);
                GBG.setLeftAnchor(SliderGT,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(SliderL,GBG.getWidth() * 0.4);
                GBG.setLeftAnchor(checkSI,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(checkBR,GBG.getWidth() * 0.4);

                //Audio
                GBG.setLeftAnchor(Music,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(SliderMusi,GBG.getWidth() * 0.4);
                GBG.setLeftAnchor(Esonori,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(SliderES,GBG.getWidth() * 0.4);
                GBG.setLeftAnchor(checkMusi,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(checkMES,GBG.getWidth() * 0.10);

                //Personalizzazioni
                GBG.setLeftAnchor(Tint,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(Tcont,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(Tpedgioc1,GBG.getWidth() * 0.20);
                GBG.setLeftAnchor(Inpedina1,GBG.getWidth() * 0.20);
                GBG.setLeftAnchor(Conpedina1,GBG.getWidth() * 0.20);
                GBG.setLeftAnchor(pedina1,GBG.getWidth() * 0.30);
                GBG.setLeftAnchor(Ttav,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(Ancorsini,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(Imsinistra,GBG.getWidth() * 0.10);
                GBG.setLeftAnchor(Ancormed,GBG.getWidth() * 0.30);
                GBG.setLeftAnchor(TM,GBG.getWidth() * 0.30);
                GBG.setLeftAnchor(Ancordes,GBG.getWidth() * 0.50);
                GBG.setLeftAnchor(Imdestra,GBG.getWidth() * 0.50);
                GBG.setLeftAnchor(Ctavolo,GBG.getWidth() * 0.11);
                GBG.setLeftAnchor(Tpunt,GBG.getWidth() * 0.30);
                GBG.setLeftAnchor(Cpunte,GBG.getWidth() * 0.281);
                GBG.setLeftAnchor(Cpunte2,GBG.getLeftAnchor(Cpunte) + Cpunte2.getWidth() + 10);
                GBG.setLeftAnchor(Tcorn,GBG.getWidth() * 0.50);
                GBG.setLeftAnchor(Ccornice,GBG.getWidth() * 0.51);
                GBG.setLeftAnchor(Tpedgioc2,GBG.getWidth() * 0.40);
                GBG.setLeftAnchor(Inpedina2,GBG.getWidth() * 0.40);
                GBG.setLeftAnchor(Conpedina2,GBG.getWidth() * 0.40);
                GBG.setLeftAnchor(pedina2,GBG.getWidth() * 0.50);

                //schermi

                Sbackgraound.setPrefWidth(GBG.getWidth()/4);
                Timpostazioni.setPrefWidth(GBG.getWidth()/4);

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
        }

        public void initialize() {

                group = new ToggleGroup();
                Imsinistra.setToggleGroup(group);
                TM.setToggleGroup(group);
                Imdestra.setToggleGroup(group);
                switch (logic.getBoardPreset()) {
                        case CUSTOM_BOARD:
                                TM.setSelected(true);
                                break;
                        case LEFT_PRESET:
                                Imsinistra.setSelected(true);
                                Ctavolo.setDisable(true);
                                Cpunte.setDisable(true);
                                Cpunte2.setDisable(true);
                                Ccornice.setDisable(true);
                                break;
                        case RIGHT_PRESET:
                                Imdestra.setSelected(true);
                                Ctavolo.setDisable(true);
                                Cpunte.setDisable(true);
                                Cpunte2.setDisable(true);
                                Ccornice.setDisable(true);
                                break;
                }



                //color picker
                Inpedina1.setValue(Color.web(logic.getWhitePawnFill()));
                Conpedina1.setValue(Color.web(logic.getWhitePawnStroke()));
                Inpedina2.setValue(Color.web(logic.getBlackPawnFill()));
                Conpedina2.setValue(Color.web(logic.getBlackPawnStroke()));
                Ctavolo.setValue(Color.web(logic.getBoardInnerColor()));
                Ccornice.setValue(Color.web(logic.getBoardFrameColor()));
                Cpunte.setValue(Color.web(logic.getEvenPointsColor()));
                Cpunte2.setValue(Color.web(logic.getOddPointsColor()));

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
                        SliderMusi.valueProperty().addListener(observable -> {
                                View.sceneMusica.player.setVolume(SliderMusi.getValue()/100);
                                View.sceneMusica.playerp.setVolume(SliderMusi.getValue()/100);
                        });
                        SliderES.valueProperty().addListener(observable -> View.sceneMusica.Pawn.setVolume(SliderES.getValue()/100));

                //TODO CONTROLLARE CHE IL PROGRAMMA ENTRI CORRETTAMENTE IN FULLSCREEN O NO

                //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                GBG.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                GBG.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

                openEditVideo();


        }



}
