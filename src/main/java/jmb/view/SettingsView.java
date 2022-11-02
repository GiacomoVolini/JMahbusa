package jmb.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jmb.view.settings.AudioTab;
import jmb.view.settings.ControlsTab;
import jmb.view.settings.CustomizationTab;
import jmb.view.settings.VideoTab;

import static jmb.ConstantsShared.Music;
import static jmb.view.ConstantsView.MAIN_MENU;
import static jmb.view.View.getLogic;
import static jmb.view.View.getView;

public class SettingsView implements GenericGUI{
        @FXML private AnchorPane window;
        public AnchorPane getWindowPane() {
                return window;
        }
        // Selettore schede impostazioni
        @FXML private TitledPane settingsTitlePane;
        @FXML private AnchorPane settingsAnchorPane;
        @FXML private Button videoButton, audioButton, customizationButton, controlsButton,
                mainMenuButton, resetButton, applyButton;
        private Button[] settingsPaneButtons;
        private enum Tab {VIDEO, AUDIO, CUSTOMIZATION, CONTROLS}


        // Controller delle singole schede
        @FXML private VideoTab videoTabController;
        @FXML private AudioTab audioTabController;
        @FXML private ControlsTab controlsTabController;
        @FXML private CustomizationTab customizationTabController;

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
                        customizationTabController.stopAnimations();
                        App.changeRoot(MAIN_MENU);
                }
        }

        public Button getApplyButton() {
                return applyButton;
        }

        public Stage getStage() {
                return (Stage)window.getScene().getWindow();
        }

        @FXML void closeWarning(ActionEvent event) {
                videoTabController.setDisable(false);
                audioTabController.setDisable(false);
                controlsTabController.setDisable(false);
                customizationTabController.setDisable(false);

                //TODO ROBA DA RIVEDERE
                warningTitlePane.setVisible(false);
                warningTitlePane.setDisable(true);
                settingsAnchorPane.setDisable(false);
        }

        @FXML void forceMainMenu(ActionEvent event) {
                settingsAnchorPane.setDisable(false);
                App.changeRoot(MAIN_MENU);
                customizationTabController.stopAnimations();
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
                videoTabController.setVisible(tab.equals(Tab.VIDEO));
                audioTabController.setVisible(tab.equals(Tab.AUDIO));
                controlsTabController.setVisible(tab.equals(Tab.CONTROLS));
                customizationTabController.setVisible(tab.equals(Tab.CUSTOMIZATION));
        }

        //Comandi Action

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
        void applySettingsAndExit(ActionEvent event) {
                applySettings(null);
                customizationTabController.stopAnimations();
                App.changeRoot(MAIN_MENU);
        }

        @FXML
        void applySettings(ActionEvent event) {
                videoTabController.applySettings();
                audioTabController.applySettings();
                customizationTabController.applySettings();
                controlsTabController.applySettings();

                getLogic().applySettingsChanges();
                applyButton.setDisable(true);
        }

        @FXML
        void resetToDefaults(ActionEvent event) {
                getLogic().resetDefaultSettings();
                videoTabController.resetWindow();
                loadSettings();
                applyButton.setDisable(true);
                Stage stage = (Stage) window.getScene().getWindow();
                stage.setWidth(getLogic().getSetting("Video", "resolutionWidth", int.class));
                stage.setHeight(getLogic().getSetting("Video", "resolutionHeight", int.class));
                if (getLogic().getSetting("Audio", "muteMusic", boolean.class))
                        getView().pauseMusic();
                else getView().playMusic(Music.MENU);
        }

        public void changeDimensions() {
                Stage stage = (Stage) window.getScene().getWindow();
                videoTabController.changeDimensions(window);
                audioTabController.changeDimensions(window);
                controlsTabController.changeDimensions(window);
                customizationTabController.changeDimensions(window);
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

                //schermi
                settingsAnchorPane.setPrefWidth(window.getWidth()/4);
                settingsTitlePane.setPrefWidth(window.getWidth()/4);
                warningTitlePane.setLayoutX(window.getWidth()/2 - 206);
                warningTitlePane.setLayoutY(window.getHeight()/2 -103);
                settingsAnchorPane.setPrefHeight(window.getWidth());
                settingsTitlePane.setPrefHeight(window.getWidth());

                //Risoluzione

                if (((int)stage.getHeight() != getLogic().getSetting("Video","resolutionHeight",int.class) ||
                        (int)stage.getWidth() != getLogic().getSetting("Video","resolutionWidth",int.class)) &&
                        (int)stage.getWidth()!= 0 && (int)stage.getHeight() != 0) {
                        applyButton.setDisable(false);
                }

        }
        public void initialize() {
                videoTabController.setSettingsView(this);
                audioTabController.setSettingsView(this);
                controlsTabController.setSettingsView(this);
                customizationTabController.setSettingsView(this);

                settingsPaneButtons = new Button[] {videoButton, audioButton, customizationButton, controlsButton,
                        mainMenuButton, resetButton, applyButton};

                loadStrings();
                loadSettings();

                // LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
                window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());
                // LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
                window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

                openEditVideo();
                videoTabController.initialize();

        }

        private void loadStrings() {
                // Selettore schede impostazioni
                settingsTitlePane.setText(getLogic().getString("Settings"));
                videoButton.setText(getLogic().getString("Video"));
                audioButton.setText(getLogic().getString("Audio"));
                customizationButton.setText(getLogic().getString("Customization"));
                controlsButton.setText(getLogic().getString("Commands"));
                applyButton.setText(getLogic().getString("Apply"));
                resetButton.setText(getLogic().getString("Reset"));
                mainMenuButton.setText(getLogic().getString("MainMenu"));
                // Pannello avviso impostazioni non salvate
                warningTitlePane.setText(getLogic().getString("Attention"));
                warningText.setText(getLogic().getString("AttentionText"));
                exitAndSaveButton.setText(getLogic().getString("Save"));
                exitNoSaveButton.setText(getLogic().getString("Don'tSave"));
                cancelButton.setText(getLogic().getString("Cancel"));

                videoTabController.loadStrings();
                audioTabController.loadStrings();
                controlsTabController.loadStrings();
                customizationTabController.loadStrings();
        }

        private void loadSettings() {
                videoTabController.loadSettings();
                audioTabController.loadSettings();
                controlsTabController.loadSettings();
                customizationTabController.loadSettings();
        }

        public void disableSelectorButtons(boolean set) {
                for (Button button : settingsPaneButtons)
                        button.setDisable(set);
        }
}
