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

    public static Musica sceneMusica = new Musica();
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
    public void setDiceContrast() {
        DiceView.setDiceContrast(sceneGame.diceArray);
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
    public void playmusica() {sceneMusica.pawnSFX.play();}

}
