package jmb.view.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import jmb.view.SettingsView;

import static jmb.view.View.getLogic;

public class ControlsTab {

    @FXML private AnchorPane controlsAnchorPane;

    @FXML private TitledPane controlsTitlePane;

    @FXML private Text downText, finishTurnText, keyboardText, leftText, movementText,
            openMenuText, revertMoveText, rightText, selectText, upText;

    @FXML private TextField downTextField, finishTurnTextField, leftTextField, openMenuTextField,
            revertMoveTextField, rightTextField, selectTextField, upTextField;

    private SettingsView settingsView;
    private TextField[] keyBinds;
    private String bindingBefore;
    private int waitingBinding;
    private boolean wasApplyButtonDisabled = true;

    public void setSettingsView(SettingsView sv) {
        settingsView = sv;
    }

    @FXML
    void selectKeyBind(MouseEvent event) {
        TextField b = (TextField) event.getSource();
        bindingBefore = b.getText();
        b.setText(getLogic().getString("Waiting"));
        for(int i=0; i<8; i++){
            if(keyBinds[i].getText().equals(getLogic().getString("Waiting"))){
                keyBinds[i].setDisable(false);
                waitingBinding = i;
            }else{
                keyBinds[i].setDisable(true);
            }
        }
        wasApplyButtonDisabled = settingsView.getApplyButton().isDisabled();
        settingsView.disableSelectorButtons(true);
    }

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
        settingsView.disableSelectorButtons(false);
        if (b.getText().equals(bindingBefore))
            settingsView.getApplyButton().setDisable(wasApplyButtonDisabled);
    }

    public void loadStrings() {
        controlsTitlePane.setText(getLogic().getString("Commands"));
        keyboardText.setText(getLogic().getString("Keyboard"));
        movementText.setText(getLogic().getString("Move"));
        rightText.setText(getLogic().getString("Right"));
        leftText.setText(getLogic().getString("Left"));
        upText.setText(getLogic().getString("Up"));
        downText.setText(getLogic().getString("Down"));
        selectText.setText(getLogic().getString("Select"));
        revertMoveText.setText(getLogic().getString("DeleteMove"));
        finishTurnText.setText(getLogic().getString("FinishTurn"));
        openMenuText.setText(getLogic().getString("TMainMenu"));
    }

    public void loadSettings() {
        rightTextField.setText(getLogic().getSetting("Controls", "moveRight", String.class));
        leftTextField.setText(getLogic().getSetting("Controls", "moveLeft", String.class));
        upTextField.setText(getLogic().getSetting("Controls", "moveUp", String.class));
        downTextField.setText(getLogic().getSetting("Controls", "moveDown", String.class));
        selectTextField.setText(getLogic().getSetting("Controls", "select", String.class));
        revertMoveTextField.setText(getLogic().getSetting("Controls", "revertMove", String.class));
        finishTurnTextField.setText(getLogic().getSetting("Controls", "finishTurn", String.class));
        openMenuTextField.setText(getLogic().getSetting("Controls", "openMenu", String.class));
    }

    public void applySettings() {
        getLogic().setSetting("Controls", "moveRight",rightTextField.getText());
        getLogic().setSetting("Controls", "moveLeft",leftTextField.getText());
        getLogic().setSetting("Controls", "moveUp",upTextField.getText());
        getLogic().setSetting("Controls", "moveDown",downTextField.getText());
        getLogic().setSetting("Controls", "select",selectTextField.getText());
        getLogic().setSetting("Controls", "revertMove",revertMoveTextField.getText());
        getLogic().setSetting("Controls", "finishTurn",finishTurnTextField.getText());
        getLogic().setSetting("Controls", "openMenu",openMenuTextField.getText());

    }

    public void setDisable(boolean set) {
        controlsAnchorPane.setDisable(set);
    }

    public void setVisible(boolean set) {
        controlsTitlePane.setVisible(set);
        controlsAnchorPane.setMouseTransparent(!set);
    }

    public void initialize() {
        keyBinds = new TextField[] {this.rightTextField, this.leftTextField, this.upTextField, this.downTextField, this.selectTextField, this.revertMoveTextField, this.finishTurnTextField, this.openMenuTextField};

    }

    public void changeDimensions(AnchorPane window) {
        if (getLogic().isLanguageRightToLeft(getLogic().getCurrentLanguage())) {
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
        } else {
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
        controlsAnchorPane.setLayoutX(window.getWidth()/4);
        controlsTitlePane.setLayoutX(window.getWidth()/4);
        controlsAnchorPane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        controlsTitlePane.setPrefWidth(window.getWidth()/4 + window.getWidth()/4 + window.getWidth()/4);
        controlsAnchorPane.setPrefHeight(window.getHeight());
        controlsTitlePane.setPrefHeight(window.getHeight());


    }

}