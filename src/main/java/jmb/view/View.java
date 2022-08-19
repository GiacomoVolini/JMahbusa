package jmb.view;

import javafx.scene.paint.Color;
import jmb.logic.ILogic;
import static jmb.ConstantsShared.*;

public class View implements IView {

    public static ILogic logic;
    public static IView view;

    public static GameView sceneGame;

    public static LeaderboardView sceneLeaderboard;

    public static LogIn sceneLogIn;

    public static SettingsView sceneSettings;

    public static MainMenu sceneMainMenu;

    public static LoadGameView sceneLoadView;
    public static TutorialView sceneTutorial;

    public static MusicPlayer sceneMusic;
    public void setMusicVolume (double value) {
        sceneMusic.setMusicVolume(value);
    }
    public void setSFXVolume (double value) {
        sceneMusic.setSFXVolume(value);
    }

    @Override
    public void initializeMusic() {
        sceneMusic = new MusicPlayer();
        sceneMusic.setMusicVolume(logic.getMusicVolume()/100.0);
        sceneMusic.setSFXVolume(logic.getSFXVolume()/100.0);
    }
    @Override
    public void openBlackExit(int whoCalled){
        try {
            DynamicGameBoard sceneToCall = null;
            switch (whoCalled) {
                case GAME_CALLED:
                    sceneToCall = sceneGame;
                    break;
                case TUTORIAL_CALLED:
                    sceneToCall = sceneTutorial;
                    break;
            }
            sceneToCall.openBlackExit();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public void openWhiteExit(int whoCalled) {
        try {
            DynamicGameBoard sceneToCall = null;
            switch (whoCalled) {
                case GAME_CALLED:
                    sceneToCall = sceneGame;
                    break;
                case TUTORIAL_CALLED:
                    sceneToCall = sceneTutorial;
                    break;
            }
            sceneToCall.openWhiteExit();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public void rollDice() {
        sceneGame.rollDice();
    }

    @Override
    public void setDiceContrast(int whoCalled) {
        DynamicGameBoard sceneToCall = null;
        switch (whoCalled) {
            case GAME_CALLED:
                sceneToCall = sceneGame;
                break;
            case TUTORIAL_CALLED:
                sceneToCall = sceneTutorial;
                break;
        }
        DiceView.setDiceContrast(sceneToCall.diceArray, whoCalled);
    }

    @Override
    public void setPawnsForTurn() {
        GameViewRedraw.redrawPawns(sceneGame);
    }

    @Override
    public void backBTNSetDisable (boolean disable) {
        sceneGame.backBTN.setDisable(disable);
    }

    @Override
    public void closeBlackExit () {
        sceneGame.closeBlackExit();
    }

    @Override
    public void closeWhiteExit() {
        sceneGame.closeWhiteExit();
    }

    @Override
    public void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus) {
        sceneGame.gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, tournamentStatus);
    }

    @Override
    public void playPawnSFX() {playSFX(PAWN_SFX);}
    @Override
    public void setNextTutorialString(String text, boolean changeTextBox) {
        sceneTutorial.setNextTutorialString(text, changeTextBox);
    }
    @Override
    public void setTutorialOver(){
        sceneTutorial.setTutorialOver();
    }
    @Override
    public void setPawnsVisible(boolean set, int whoCalled) {
        switch (whoCalled) {
            case TUTORIAL_CALLED:
                sceneTutorial.setPawnsVisible(set);
                break;
            case GAME_CALLED:
                sceneGame.setPawnsVisible(set);
                break;
        }
    }
    @Override
    public void tutorialTextBoxAnimation(double tbx, double tby) {sceneTutorial.textBoxAnimation(tbx, tby);}
    @Override
    public void tutorialPointAnimation(boolean set) {
        sceneTutorial.pointAnimation(set);
    }
    @Override
    public void tutorialExitZoneAnimation(boolean set) {
        sceneTutorial.exitZoneAnimation(set);
    }
    @Override
    public void tutorialDiceAnimation(boolean set, boolean infinite) {
        sceneTutorial.diceAnimation(set, infinite, 10);
    }
    @Override
    public void tutorialDiceAnimation(boolean set) { sceneTutorial.diceAnimation(set, false, 10);}
    @Override
    public void tutorialDiceAnimation(boolean set, int cycles) {
        sceneTutorial.diceAnimation(set, false, cycles);
    }
    @Override
    public void callRedraw(int whoCalled) {
        switch (whoCalled) {
            case GAME_CALLED:
                GameViewRedraw.resizeAll(sceneGame);
                break;
            case TUTORIAL_CALLED:
                TutorialViewRedraw.resizeAll(sceneTutorial);
                DiceView.setDiceValues(sceneTutorial.diceArray, whoCalled);
                break;
        }
    }
    @Override
    public void allowTextBoxMouseInput(boolean allow) {
        sceneTutorial.allowTextBoxMouseInput(allow);
    }
    @Override
    public void waitForRecall(int whoCalled, double seconds) {
        switch (whoCalled) {
            case TUTORIAL_CALLED:
                sceneTutorial.waitForRecall(seconds);
                break;
            default:
                break;
        }
    }
    @Override
    public void restoreBoardColors(int whoCalled) {
        switch (whoCalled) {
            case GAME_CALLED:
                for (int i = 0; i < 26; i++)
                    sceneGame.restoreColorToPoint(i);
                break;
            case TUTORIAL_CALLED:
                for (int i = 0; i <26; i++)
                    sceneTutorial.restoreColorToPoint(i);
                break;
        }
    }
    @Override
    public void playMusic(int which) {
        sceneMusic.playMusic(which);
    }
    @Override
    public void playSFX(int which) {
        sceneMusic.playSFX(which);
    }
    @Override
    public void pauseMusic() {
        sceneMusic.pauseMusic();
    }
    @Override
    public void stopMusic() {
        sceneMusic.stopMusic();
    }
    @Override
    public void highlightPointsToOpenExit(int stage) {
        sceneTutorial.highlightPointsToOpenExit(stage);
    }
    @Override
    public void openDoubleDice(int whoCalled) {
        switch (whoCalled) {
            case GAME_CALLED:
                sceneGame.openDoubleDice();
                break;
            case TUTORIAL_CALLED:
                sceneTutorial.openDoubleDice();
                break;
        }
    }
    @Override
    public void closeDoubleDice(int whoCalled) {
        switch (whoCalled) {
            case GAME_CALLED:
                sceneGame.closeDoubleDice();
                break;
            case TUTORIAL_CALLED:
                sceneTutorial.closeDoubleDice();
                break;
        }
    }
    @Override
    public void colorPoint (int whoCalled, int index, String colorFill, String colorStroke) {
        switch (whoCalled) {
            case GAME_CALLED:
                sceneGame.colorPoint(index, Color.web(colorFill), Color.web(colorStroke));
                break;
            case TUTORIAL_CALLED:
                sceneTutorial.colorPoint(index, Color.web(colorFill), Color.web(colorStroke));
                break;
        }
    }
}
