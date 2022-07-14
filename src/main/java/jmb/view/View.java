package jmb.view;

import jmb.model.ILogic;
import static jmb.ConstantsShared.*;

public class View implements IView {

    public static ILogic logic;

    public static GameView sceneGame;

    public static LeaderboardView sceneLeaderboard;

    public static LogIn sceneLogIn;

    public  static SettingsView sceneImpostazioni;

    public  static MainMenu sceneMainMenu;

    public static LoadGameView sceneLoadView;
    public static TutorialView sceneTutorial;

    public static Musica sceneMusica;
    protected static void setMusicVolume (double value) {
        sceneMusica.setMusicVolume(value);
    }
    protected static void setSFXVolume (double value) {
        sceneMusica.setSFXVolume(value);
    }
    protected static void playMenuMusic() {
        sceneMusica.menuMusic.play();
    }
    protected static void pauseMenuMusic() {
        sceneMusica.menuMusic.pause();
    }

    @Override
    public void initializeMusic() {
        sceneMusica = new Musica();
        sceneMusica.setMusicVolume(logic.getMusicVolume()/100.0);
        sceneMusica.setSFXVolume(logic.getSFXVolume()/100.0);
    }
    @Override
    public void openBlackExit(int whoCalled){
        try {
            GameBoard sceneToCall = null;
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
            GameBoard sceneToCall = null;
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
    public void playPawnSFX() {sceneMusica.pawnSFX.play();}
    @Override
    public void setNextTutorialString(String text) {
        sceneTutorial.setNextTutorialStage(text);
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
    public void tutorialPointAnimation(boolean set) {
        sceneTutorial.tutorialPointAnimation(set);
    }
    @Override
    public void tutorialExitZoneAnimation(boolean set) {
        sceneTutorial.tutorialExitZoneAnimation(set);
    }
    @Override
    public void tutorialDiceAnimation(boolean set) { sceneTutorial.tutorialDiceAnimation(set);}
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
}
