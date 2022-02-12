package jmb.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import static jmb.view.ConstantsView.*;

import java.io.IOException;

public class MenuImpostazioni<string> {

        @FXML
        private AnchorPane GBG;

        @FXML
        private AnchorPane Sbackgraound;

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
        private ImageView Vbg;

        @FXML
        private CheckBox checkSI;

        @FXML
        private Slider SliderGT;

        @FXML
        private CheckBox checkBR;

        @FXML
        private Slider SliderL;

        @FXML
        private AnchorPane audio;

        @FXML
        private ImageView Abg;

        @FXML
        private CheckBox checkMusi;

        @FXML
        private Slider SliderMusi;

        @FXML
        private CheckBox checkMES;

        @FXML
        private Slider SliderES;

        @FXML
        private AnchorPane personalizzazione;

        @FXML
        private ImageView Ibg;

        @FXML
        private ColorPicker Inpedina1;

        @FXML
        private Circle pedina1;

        @FXML
        private ColorPicker Inpedina2;

        @FXML
        private Circle pedina2;

        @FXML
        private ColorPicker Conpedina1;

        @FXML
        private ColorPicker Conpedina2;

        @FXML
        private ColorPicker Ctavolo;

        @FXML
        private ColorPicker Cpunte;

        @FXML
        private ColorPicker Cpunte2;

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
        private RadioButton TM;

        @FXML
        private Button Apply;

        @FXML
        private Button Reset;

        @FXML
        void Tmainmenu()  throws IOException {
                tornaMM.getScene().getWindow();
                jmb.App.MainMenu();

        }

        @FXML
        void openEditVideo() {
                video.setVisible(true);
                audio.setVisible(false);
                personalizzazione.setVisible(false);

        }

        @FXML
        void openEditAudio() {
                video.setVisible(false);
                audio.setVisible(true);
                personalizzazione.setVisible(false);
        }

        @FXML
        void openEditPersonalizzazione() {
                video.setVisible(false);
                audio.setVisible(false);
                personalizzazione.setVisible(true);

        }


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

                rd = middle;
                Ctavolo.setDisable(false);
                Cpunte.setDisable(false);
                Cpunte2.setDisable(false);
                Ccornice.setDisable(false);
        }

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
                        rd = left;
                        Ctavolo.setDisable(true);
                        Cpunte.setDisable(true);
                        Cpunte2.setDisable(true);
                        Ccornice.setDisable(true);
                        table = Color.web("#ad1111");
                        frame = Color.web("#000000");
                        point = Color.web("#ffde3a");
                        point2 = Color.web("#c0c0c0");
                } else if (Imdestra.isSelected()) {
                        rd = right;
                        Ctavolo.setDisable(true);
                        Cpunte.setDisable(true);
                        Cpunte2.setDisable(true);
                        Ccornice.setDisable(true);
                        table = Color.web("#53960f");
                        frame = Color.web("#090957");
                        point = Color.web("#c21123");
                        point2 = Color.web("#daa505");
                } else {
                        rd = middle;
                        Ctavolo.setDisable(false);
                        Cpunte.setDisable(false);
                        Cpunte2.setDisable(false);
                        Ccornice.setDisable(false);
                }

        }

        private void changeDimensions() {

        }

        ToggleGroup group = new ToggleGroup();

        public void initialize() {

                group = new ToggleGroup();
                Imsinistra.setToggleGroup(group);
                TM.setToggleGroup(group);
                TM.setSelected(true);
                Imdestra.setToggleGroup(group);


                        switch (rd) {
                        case left:
                                Imsinistra.setSelected(true);
                                Ctavolo.setDisable(true);
                                Cpunte.setDisable(true);
                                Cpunte2.setDisable(true);
                                Ccornice.setDisable(true);
                                table = Color.web("#ad1111");
                                frame = Color.web("#000000");
                                point = Color.web("#ffde3a");
                                point2 = Color.web("#c0c0c0");
                                break;
                        case middle:
                                TM.setSelected(true);
                                Ctavolo.setDisable(false);
                                Cpunte.setDisable(false);
                                Cpunte2.setDisable(false);
                                Ccornice.setDisable(false);
                                break;
                        case right:
                                Imdestra.setSelected(true);
                                Ctavolo.setDisable(true);
                                Cpunte.setDisable(true);
                                Cpunte2.setDisable(true);
                                Ccornice.setDisable(true);
                                table = Color.web("#53960f");
                                frame = Color.web("#090957");
                                point = Color.web("#c21123");
                                point2 = Color.web("#daa505");
                                break;
                }

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


                //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                GBG.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                GBG.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        }



}
