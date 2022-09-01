package jmb.view;

import javafx.scene.paint.Color;
import jmb.logic.ILogic;

import static jmb.view.ConstantsView.MUSIC_VOLUME;
import static jmb.view.ConstantsView.SFX_VOLUME;

public class View implements IView {

    public static ILogic logic;
    public static IView view;

    public static GenericGUI currentScene;
    public static MusicPlayer musicController;

    @Override
    public void setCurrentScene (GenericGUI scene) {
        currentScene = scene;
    }

    @Override
    public void initializeMusic() {
        musicController = new MusicPlayer();
        musicController.setMusicVolume(logic.getMusicVolume()/100.0);
        musicController.setSFXVolume(logic.getSFXVolume()/100.0);
    }
    public void setVolume (int whichVolume, double value) {
        switch(whichVolume) {
            case MUSIC_VOLUME:
                musicController.setMusicVolume(value);
                break;
            case SFX_VOLUME:
                musicController.setSFXVolume(value);
                break;
        }
    }
    @Override
    public void playMusic(int which) {
        musicController.playMusic(which);
    }
    @Override
    public void playSFX(int which) {
        musicController.playSFX(which);
    }
    @Override
    public void pauseMusic() {
        musicController.pauseMusic();
    }
    @Override
    public void stopMusic() {
        musicController.stopMusic();
    }
    @Override
    public void openBlackExit(){
        ((AnimatedBoard)currentScene).openBlackExit();
    }

    @Override
    public void openWhiteExit() {
        ((AnimatedBoard)currentScene).openWhiteExit();
    }
    @Override
    public void closeBlackExit () {
        ((GameView)currentScene).closeBlackExit();
    }

    @Override
    public void closeWhiteExit() {
        ((GameView)currentScene).closeWhiteExit();
    }
    @Override
    public void openDoubleDice() {
        ((AnimatedBoard)currentScene).openDoubleDice();
    }
    @Override
    public void closeDoubleDice() {
        ((AnimatedBoard)currentScene).closeDoubleDice();
    }

    @Override
    public void rollDice() {
        ((GameView)currentScene).rollDice();
    }

    @Override
    public void setDiceContrast() {
        DiceView.setDiceContrast(((AnimatedBoard)currentScene).getDiceArray());
    }

    @Override
    public void setPawnsForTurn() {
        GameViewRedraw.redrawPawns((GameView)currentScene);
    }

    @Override
    public void backBTNSetDisable (boolean disable) {
        ((GameView)currentScene).backBTN.setDisable(disable);
    }

    @Override
    public void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus) {
        ((GameView)currentScene).gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, tournamentStatus);
    }
    @Override
    public void setNextTutorialString(String text, boolean changeTextBox) {
        ((TutorialView)currentScene).setNextTutorialString(text, changeTextBox);
    }
    @Override
    public void setTutorialOver(){
        ((TutorialView)currentScene).setTutorialOver();
    }
    @Override
    public void setPawnsVisible(boolean set) {
        ((AnimatedBoard)currentScene).setPawnsVisible(set);
    }
    @Override
    public void tutorialTextBoxAnimation(double tbx, double tby) {((TutorialView)currentScene).textBoxAnimation(tbx, tby);}
    @Override
    public void tutorialPointAnimation(boolean set) {
        ((TutorialView)currentScene).pointAnimation(set);
    }
    @Override
    public void tutorialExitZoneAnimation(boolean set) {
        ((TutorialView)currentScene).exitZoneAnimation(set);
    }
    @Override
    public void tutorialDiceAnimation(boolean set, boolean infinite) {
        ((TutorialView)currentScene).diceAnimation(set, infinite, 10);
    }
    @Override
    public void tutorialDiceAnimation(boolean set) { ((TutorialView)currentScene).diceAnimation(set, false, 10);}
    @Override
    public void tutorialDiceAnimation(boolean set, int cycles) {
        ((TutorialView)currentScene).diceAnimation(set, false, cycles);
    }
    @Override
    public void callRedraw() {
        currentScene.changeDimensions();
    }
    @Override
    public void allowTextBoxMouseInput(boolean allow) {
        ((TutorialView)currentScene).allowTextBoxMouseInput(allow);
    }
    @Override
    public void waitForRecall(double seconds) {
        ((TutorialView)currentScene).waitForRecall(seconds);
    }
    @Override
    public void restoreBoardColors() {
        for (int i = 0; i < 26; i++)
            ((AnimatedBoard)currentScene).restoreColorToPoint(i);
    }

    @Override
    public void highlightPointsToOpenExit(int stage) {
        ((TutorialView)currentScene).highlightPointsToOpenExit(stage);
    }
    @Override
    public void colorPoint (int index, String colorFill, String colorStroke) {
        ((AnimatedBoard)currentScene).colorPoint(index, Color.web(colorFill), Color.web(colorStroke));
    }

}
