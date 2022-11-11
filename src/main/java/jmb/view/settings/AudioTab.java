package jmb.view.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import jmb.ConstantsShared;
import jmb.view.SettingsView;

import static jmb.view.ConstantsView.MUSIC_VOLUME;
import static jmb.view.ConstantsView.SFX_VOLUME;
import static jmb.view.View.*;

public class AudioTab {

    @FXML private TitledPane audioTitlePane;
    @FXML private AnchorPane audioAnchorPane;
    @FXML private CheckBox musicCheck, sFXCheck;
    @FXML private Slider sFXSlider, musicSlider;
    @FXML private Text sFXText, musicText;
    private SettingsView settingsView;

    public void setSettingsView(SettingsView sv) {
        settingsView = sv;
    }

    @FXML
    void muteMusic(ActionEvent event) {
        musicSlider.setDisable(musicCheck.isSelected());
        if (musicCheck.isSelected())
            getView().pauseMusic();
        else getView().playMusic(ConstantsShared.Music.MENU);
        settingsView.getApplyButton().setDisable(false);
    }

    @FXML
    void muteSFX(ActionEvent event) {
        sFXSlider.setDisable(sFXCheck.isSelected());
        settingsView.getApplyButton().setDisable(false);
    }

    @FXML
    void playSound(MouseEvent event) {
        getView().playSFX(ConstantsShared.SFX.PAWN_DROP);
    }

    public void loadStrings() {
        audioTitlePane.setText(getLogic().getString("Audio"));
        musicText.setText(getLogic().getString("Music"));
        sFXText.setText(getLogic().getString("SoundEffects"));
        musicCheck.setText(getLogic().getString("MuteMusic"));
        sFXCheck.setText(getLogic().getString("MuteSoundEffects"));
    }

    public void setVisible(boolean set) {
        audioTitlePane.setVisible(set);
        audioAnchorPane.setMouseTransparent(!set);
    }

    public void setDisable(boolean set) {
        audioAnchorPane.setDisable(set);
    }

    public void applySettings() {
        getLogic().setSetting("Audio", "musicVolume",(int) musicSlider.getValue());
        getLogic().setSetting("Audio", "soundFXVolume",(int) sFXSlider.getValue());
        getLogic().setSetting("Audio", "muteMusic",musicCheck.isSelected());
        getLogic().setSetting("Audio", "muteSFX",sFXCheck.isSelected());
    }

    public void loadSettings() {
        boolean muteMusic = getLogic().getSetting("Audio", "muteMusic", boolean.class);
        boolean muteSFX = getLogic().getSetting("Audio", "muteSFX", boolean.class);
        musicCheck.setSelected(muteMusic);
        sFXCheck.setSelected(muteSFX);
        musicSlider.setValue(getLogic().getSetting("Audio", "musicVolume", int.class));
        musicSlider.setDisable(muteMusic);
        sFXSlider.setValue(getLogic().getSetting("Audio", "soundFXVolume", int.class));
        sFXSlider.setDisable(muteSFX);
        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> checkVolumeChanges(newVal, MUSIC_VOLUME));
        sFXSlider.valueProperty().addListener((obs, oldVal, newVal) -> checkVolumeChanges(newVal, SFX_VOLUME));

    }

    private void checkVolumeChanges(Number value, int whichSlider) {
        int savedValue = 0;
        int sliderValue = (int)(value.doubleValue());
        switch (whichSlider) {
            case MUSIC_VOLUME:
                savedValue = getLogic().getSetting("Audio", "musicVolume", int.class);
                getView().setVolume(MUSIC_VOLUME, value.doubleValue()/100);
                break;
            case SFX_VOLUME:
                savedValue = getLogic().getSetting("Audio", "soundFXVolume", int.class);
                getView().setVolume(SFX_VOLUME,value.doubleValue()/100);
                break;
        }
        if (savedValue != sliderValue)
            settingsView.getApplyButton().setDisable(false);
    }

    public void changeDimensions(AnchorPane window) {
        if (getLogic().isLanguageRightToLeft(getLogic().getCurrentLanguage())) {
            AnchorPane.setLeftAnchor(musicSlider, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(musicText, window.getWidth() * 0.4);
            AnchorPane.setLeftAnchor(sFXSlider, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(sFXText, window.getWidth() * 0.4);
            AnchorPane.setLeftAnchor(musicCheck, window.getWidth() * 0.25);
            AnchorPane.setLeftAnchor(sFXCheck, window.getWidth() * 0.25);
        } else {
            AnchorPane.setLeftAnchor(musicText, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(musicSlider, window.getWidth() * 0.4);
            AnchorPane.setLeftAnchor(sFXText, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(sFXSlider, window.getWidth() * 0.4);
            AnchorPane.setLeftAnchor(musicCheck, window.getWidth() * 0.10);
            AnchorPane.setLeftAnchor(sFXCheck, window.getWidth() * 0.10);
        }
        audioAnchorPane.setLayoutX(window.getWidth()/4);
        audioTitlePane.setLayoutX(window.getWidth()/4);
        audioAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        audioTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        audioAnchorPane.setPrefHeight(window.getHeight());
        audioTitlePane.setPrefHeight(window.getHeight());
    }

}
