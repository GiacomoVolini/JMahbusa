package jmb.view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class MenuImpostazioni {

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
        private ColorPicker Ccornice;

        @FXML
        private RadioButton Imsenistre;

        @FXML
        private ImageView TFsinistra;

        @FXML
        private RadioButton Imsinistre;

        @FXML
        private ImageView TFdestra;

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
        void Tmainmenu()  throws IOException {
                tornaMM.getScene().getWindow();
                jmb.App.MainMenu();

        }

        private void changeDimensions() {



        }

        public void initialize() {

                //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                GBG.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


                //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                GBG.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        }



}
