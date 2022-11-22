package jmb.view;

import javafx.scene.paint.Color;
import jmb.logic.ILogic;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;

public class View implements IView {

    private static ILogic logic;
    private static View view;

    public GenericGUI currentScene;

    public MusicPlayer musicController;

    public static ILogic getLogic() {
        return logic;
    }

    public static void setLogic(ILogic logic) {
        View.logic = logic;
    }

    public static View getView() {
        return view;
    }

    public static void createView() {
        View.view = new View();
    }

    @Override
    public void setCurrentScene (GenericGUI scene) {
        currentScene = scene;
    }

    @Override
    public void initializeMusic() {
        musicController = new MusicPlayer();
        musicController.setMusicVolume(getLogic().getSetting("Audio", "musicVolume", int.class)/100.0);
        musicController.setSFXVolume(getLogic().getSetting("Audio", "soundFXVolume", int.class)/100.0);
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
    public void playMusic(Music music) {
        musicController.playMusic(music);
    }
    @Override
    public void playSFX(SFX sound) {
        musicController.playSFX(sound);
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
        if(currentScene instanceof AnimatedBoard)
            ((AnimatedBoard)currentScene).openBlackExit();
    }

    @Override
    public void openWhiteExit() {
        if(currentScene instanceof AnimatedBoard)
                ((AnimatedBoard)currentScene).openWhiteExit();
    }
    @Override
    public void closeBlackExit () {
        if (currentScene instanceof GameView)
            ((GameView)currentScene).closeBlackExit();
    }

    @Override
    public void closeWhiteExit() {
        if (currentScene instanceof GameView)
                ((GameView)currentScene).closeWhiteExit();
    }
    @Override
    public void openDoubleDice() {
        if (currentScene instanceof AnimatedBoard)
                ((AnimatedBoard)currentScene).openDoubleDice();
    }
    @Override
    public void closeDoubleDice() {

        if (currentScene instanceof AnimatedBoard)
                ((AnimatedBoard)currentScene).closeDoubleDice();
    }

    @Override
    public void rollDice() {
        if (currentScene instanceof GameView)
            ((GameView)currentScene).rollDice();
    }

    @Override
    public void setDiceContrast() {
        if (currentScene instanceof AnimatedBoard)
            DiceView.setDiceContrast(((AnimatedBoard)currentScene).getDiceArray());
    }

    @Override
    public void setPawnsForTurn() {
        if(currentScene instanceof GameView)
                GameViewRedraw.redrawPawns((GameView)currentScene);
    }

    @Override
    public void backBTNSetDisable (boolean disable) {
        if(currentScene instanceof GameView)
                ((GameView)currentScene).backBTN.setDisable(disable);
    }

    @Override
    public void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, TournamentStatus status) {
        if(currentScene instanceof GameView)
                ((GameView)currentScene).gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, status);
    }
    @Override
    public void setNextTutorialString(String text, boolean changeTextBox) {
        if (currentScene instanceof TutorialView)
                ((TutorialView)currentScene).setNextTutorialString(text, changeTextBox);
    }
    @Override
    public void setTutorialOver(){
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).setTutorialOver();
    }
    @Override
    public void setPawnsVisible(boolean set) {
        if (currentScene instanceof AnimatedBoard)
            ((AnimatedBoard)currentScene).setPawnsVisible(set);
    }
    @Override
    public void tutorialTextBoxAnimation(double tbx, double tby) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).textBoxAnimation(tbx, tby);}
    @Override
    public void tutorialPointAnimation(boolean set) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).pointAnimation(set);
    }
    @Override
    public void tutorialExitZoneAnimation(boolean set) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).exitZoneAnimation(set);
    }
    @Override
    public void tutorialDiceAnimation(boolean set, boolean infinite) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).diceAnimation(set, infinite, 10);
    }
    @Override
    public void tutorialDiceAnimation(boolean set) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).diceAnimation(set, false, 10);}
    @Override
    public void tutorialDiceAnimation(boolean set, int cycles) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).diceAnimation(set, false, cycles);
    }
    @Override
    public void callRedraw() {
        currentScene.changeDimensions();
    }
    @Override
    public void allowTextBoxMouseInput(boolean allow) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).allowTextBoxMouseInput(allow);
    }
    @Override
    public void waitForRecall(double seconds) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).waitForRecall(seconds);
    }
    @Override
    public void restoreBoardColors() {
        if (currentScene instanceof AnimatedBoard)
            ((AnimatedBoard)currentScene).restoreBoardColors();
    }

    @Override
    public void highlightPointsToOpenExit(int stage) {
        if (currentScene instanceof TutorialView)
            ((TutorialView)currentScene).highlightPointsToOpenExit(stage);
    }
    @Override
    public void colorPoint (int index, String colorFill, String colorStroke) {
        if (currentScene instanceof AnimatedBoard)
            ((AnimatedBoard)currentScene).colorPoint(index, Color.web(colorFill), Color.web(colorStroke));
    }

    public void changeRoot(String newRoot) {
        App.changeRoot(newRoot);
    }

}
