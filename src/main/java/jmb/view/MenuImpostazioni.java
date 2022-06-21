package jmb.view;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jmb.App;

import static jmb.view.ConstantsView.*;
import static jmb.App.getStage;

import java.io.IOException;

public class MenuImpostazioni  {

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
        private Button tornaMM;

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

        @FXML
        private Button RA;

        @FXML
        private AnchorPane personalizzazione;

        @FXML
        private TitledPane Tpersonalizzazione;

        @FXML
        private ImageView Ibg;

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
        private Button Apply;

        @FXML
        private Button Reset;

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

        @FXML
        private Button reComandi;

        @FXML
        private Button Bvuoto1;

        @FXML
        private Button Bvuoto2;

        double Sv, Sve;

        @FXML
        void Tmainmenu()  throws IOException {
                tornaMM.getScene().getWindow();
                jmb.App.MainMenu();
                getStage().setFullScreen(cb);
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
              /*  if (checkSI.isSelected()){
                        cb = fullscreen;
                        //getStage().setFullScreen(true);
                }else{
                        cb = nofullscreen;
                        //getStage().setFullScreen(false);
                }*/
        }

        @FXML
        void blockresolution(ActionEvent event) {
                if(checkBR.isSelected()) {
                        sr = fermo;
                }else{
                        sr = nonfermo;
                }
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
        }

        @FXML
        void soundProva(MouseEvent event) {
                View.sceneMusica.Pawn.play();
        }

        //Personalizzazione
        @FXML
        void coloreINpedina1(ActionEvent event) {
              pedina1.setFill(Inpedina1.getValue());

        }

        @FXML
        void coloreOUTpedina1(ActionEvent event) {
               pedina1.setStroke(Conpedina1.getValue());

        }

        @FXML
        void coloreINpedina2(ActionEvent event) {
                pedina2.setFill(Inpedina2.getValue());

        }

        @FXML
        void coloreOUTpedina2(ActionEvent event) {
                pedina2.setStroke(Conpedina2.getValue());

        }

        @FXML
        void coloreTavolo(ActionEvent event) {
                tavolo.setFill(Ctavolo.getValue());
                tavolo.setStroke(Ctavolo.getValue());

        }

        @FXML
        void coloreCornice(ActionEvent event) {
                cornice.setFill(Ccornice.getValue());
                cornice.setStroke(Ccornice.getValue());

        }

        @FXML
        void colorePunte(ActionEvent event) {
                punta1.setFill(Cpunte.getValue());
                punta1.setStroke(Cpunte.getValue());
                punta3.setFill(Cpunte.getValue());
                punta3.setStroke(Cpunte.getValue());

        }

        @FXML
        void colorePunte2(ActionEvent event) {
                punta2.setFill(Cpunte2.getValue());
                punta2.setStroke(Cpunte2.getValue());

        }

        @FXML
        void sinistraAction(ActionEvent event) {
                Ctavolo.setDisable(true);
                Cpunte.setDisable(true);
                Cpunte2.setDisable(true);
                Ccornice.setDisable(true);
        }

        @FXML
        void inMezzo(ActionEvent event) {
                Ctavolo.setDisable(false);
                Cpunte.setDisable(false);
                Cpunte2.setDisable(false);
                Ccornice.setDisable(false);
        }

        @FXML
        void destraAction(ActionEvent event) {
                Ctavolo.setDisable(true);
                Cpunte.setDisable(true);
                Cpunte2.setDisable(true);
                Ccornice.setDisable(true);
        }

        //TODO riempi l'applicazioni

        //Comandi Action
        @FXML
        void selezionata(MouseEvent event) {
                TextField b = (TextField) event.getSource();
                b.setText("waiting...");
        }

        @FXML
        void stampa(KeyEvent event) {
                TextField b = (TextField) event.getSource();
                b.setText(event.getCode().toString());
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

        @FXML
        void ResetData(ActionEvent event) {
                pedIn1 = Color.web("#ffffff");
                pedIn2 = Color.web("#000000");
                pedOut1 = Color.web("#ffffff");
                pedOut2 = Color.web("#000000");
                table = Color.web("#e1c699");
                frame = Color.web("#432d05");
                point = Color.web("#b27e31");
                point2 = Color.web("#2abc95");

                Inpedina1.setValue(pedIn1);
                Conpedina1.setValue(pedOut1);
                Inpedina2.setValue(pedIn2);
                Conpedina2.setValue(pedOut2);
                Ctavolo.setValue(table);
                Ccornice.setValue(frame);
                Cpunte.setValue(point);
                Cpunte2.setValue(point2);

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

                Imsinistra.setToggleGroup(group);
                TM.setToggleGroup(group);
                TM.setSelected(true);
                Imdestra.setToggleGroup(group);

             //   rd = middle;
                Ctavolo.setDisable(false);
                Cpunte.setDisable(false);
                Cpunte2.setDisable(false);
                Ccornice.setDisable(false);
        }

        @FXML
        void resetVideo(ActionEvent event){

                        cb = nofullscreen;
                        getStage().setFullScreen(false);
                        checkSI.setSelected(false);
                        sr = nonfermo;
                        getStage().setResizable(true);
                        checkBR.setSelected(false);
        }

        @FXML
        void resetAudio(ActionEvent event) {

                checkMusi.setSelected(false);
                Sv = 100;
                checkMES.setSelected(false);
                Sve = 100;
                mutaLAmusica(null);
                mutaGLIeffetto(null);
        }

        //TODO trovare il metodo per i pulsanti della tastieria
        @FXML
        void resetComandi(ActionEvent event) {

        }

        //TODO salva i cambiamenti dei impostazzioni anche se chiudi il gioco
        @FXML
        void SaveData(ActionEvent event) {
                //colori
                pedIn1 = Inpedina1.getValue();
                pedOut1 = Conpedina1.getValue();
                pedIn2 = Inpedina2.getValue();
                pedOut2 = Conpedina2.getValue();
                table = Ctavolo.getValue();
                frame = Ccornice.getValue();
                point = Cpunte.getValue();
                point2 = Cpunte2.getValue();
                //scelte di opzioni fisse

                if (Imsinistra.isSelected()) {
                     //   rd = left;
                        Ctavolo.setDisable(true);
                        Cpunte.setDisable(true);
                        Cpunte2.setDisable(true);
                        Ccornice.setDisable(true);
                        table = Color.web("#ad1111");
                        frame = Color.web("#000000");
                        point = Color.web("#ffde3a");
                        point2 = Color.web("#c0c0c0");
                } else if (Imdestra.isSelected()) {
                     //   rd = right;
                        Ctavolo.setDisable(true);
                        Cpunte.setDisable(true);
                        Cpunte2.setDisable(true);
                        Ccornice.setDisable(true);
                        table = Color.web("#53960f");
                        frame = Color.web("#090957");
                        point = Color.web("#c21123");
                        point2 = Color.web("#daa505");
                } else {
                      //  rd = middle;
                        Ctavolo.setDisable(false);
                        Cpunte.setDisable(false);
                        Cpunte2.setDisable(false);
                        Ccornice.setDisable(false);
                }

                //video
                if (checkSI.isSelected()){
                        cb = fullscreen;
                        getStage().setFullScreen(true);
                }else{
                        cb = nofullscreen;
                        getStage().setFullScreen(false);
                }

                if(checkBR.isSelected()) {
                        sr = fermo;
                        getStage().setResizable(false);
                }else{
                        sr = nonfermo;
                        getStage().setResizable(true);
                }

                //Audio
                if(checkMusi.isSelected()) {
                        mu = muta;
                        View.sceneMusica.player.pause();
                        View.sceneMusica.playerp.stop();
                }else{
                        mu = nonmuta;
                        View.sceneMusica.player.play();
                }

                if(checkMES.isSelected()) {
                        mue = mutae;
                        View.sceneMusica.Pawn.stop();
                }else{
                        mue = nonmutae;
                        View.sceneMusica.Pawn.play();
                }

                //comandi
                moveTO_right = moDestra.getText();
                moveTO_left = moSinistra.getText();
                moveTO_up = moSopra.getText();
                moveTO_down = moSotto.getText();
                select = Selezionare.getText();
                confirm = confermare.getText();
                delete_move = cacellareMo.getText();
                finish_turn = finitoT.getText();
                main_menu = opUscita.getText();

        }


        protected void changeDimensions() {

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
                tornaMM.setLayoutX(GBG.getWidth()/8 - tornaMM.getWidth()/2);
                tornaMM.setMaxWidth(89);
                Apply.setLayoutX(GBG.getWidth()/8 - Apply.getWidth()/2);
                Apply.setMaxWidth(58);

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
                GBG.setLeftAnchor(Reset,GBG.getWidth() * 0.31);

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

        ToggleGroup group = new ToggleGroup();

        public void initialize() {

                group = new ToggleGroup();
                Imsinistra.setToggleGroup(group);
                TM.setToggleGroup(group);
                TM.setSelected(true);
                Imdestra.setToggleGroup(group);

                //color picker
                Inpedina1.setValue(pedIn1);
                Conpedina1.setValue(pedOut1);
                Inpedina2.setValue(pedIn2);
                Conpedina2.setValue(pedOut2);
                Ctavolo.setValue(table);
                Ccornice.setValue(frame);
                Cpunte.setValue(point);
                Cpunte2.setValue(point2);

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
                        checkMusi.setSelected(mu);
                        checkMES.setSelected(mue);
                        SliderMusi.setValue(View.sceneMusica.player.getVolume() * 100);
                        SliderMusi.setValue(View.sceneMusica.playerp.getVolume() * 100);
                        SliderES.setValue(View.sceneMusica.Pawn.getVolume() * 100);
                        SliderMusi.valueProperty().addListener(new InvalidationListener() {
                                @Override
                                public void invalidated(Observable observable) {
                                        View.sceneMusica.player.setVolume(SliderMusi.getValue()/100);
                                        View.sceneMusica.playerp.setVolume(SliderMusi.getValue()/100);
                                }
                        });
                        SliderES.valueProperty().addListener(new InvalidationListener() {
                                @Override
                                public void invalidated(Observable observable) {
                                        View.sceneMusica.Pawn.setVolume(SliderES.getValue()/100);
                                }
                        });

                //schermo intero

                        checkSI.setSelected(cb);
                        getStage().setFullScreen(cb);
                        checkBR.setSelected(sr);
                        getStage().setResizable(sr);


                //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                GBG.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                GBG.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

                openEditVideo();

        }



}
