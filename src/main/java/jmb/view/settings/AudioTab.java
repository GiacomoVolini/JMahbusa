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
import static jmb.view.View.logic;
import static jmb.view.View.view;

public class AudioTab {
    //TODO FINIRE

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
            view.pauseMusic();
        else view.playMusic(ConstantsShared.Music.MENU);
        settingsView.getApplyButton().setDisable(false);
    }

    @FXML
    void muteSFX(ActionEvent event) {
        sFXSlider.setDisable(sFXCheck.isSelected());
        settingsView.getApplyButton().setDisable(false);
    }

    @FXML
    void playSound(MouseEvent event) {
        view.playSFX(ConstantsShared.SFX.PAWN_DROP);
    }

    public void loadStrings() {
        audioTitlePane.setText(logic.getString("Audio"));
        musicText.setText(logic.getString("Music"));
        sFXText.setText(logic.getString("SoundEffects"));
        musicCheck.setText(logic.getString("MuteMusic"));
        sFXCheck.setText(logic.getString("MuteSoundEffects"));
    }

    public void setVisible(boolean set) {
        audioTitlePane.setVisible(set);
        audioAnchorPane.setMouseTransparent(!set);
    }

    public void setDisable(boolean set) {
        audioAnchorPane.setDisable(set);
    }

    public void applySettings() {
        logic.setSetting("Audio", "musicVolume",(int) musicSlider.getValue());
        logic.setSetting("Audio", "soundFXVolume",(int) sFXSlider.getValue());
        logic.setSetting("Audio", "muteMusic",musicCheck.isSelected());
        logic.setSetting("Audio", "muteSFX",sFXCheck.isSelected());
    }

    public void loadSettings() {
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
            settingsView.getApplyButton().setDisable(false);
    }

    public void changeDimensions(AnchorPane window) {
        if (logic.isLanguageRightToLeft(logic.getCurrentlanguage())) {
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
