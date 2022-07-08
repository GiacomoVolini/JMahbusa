package jmb.view;

import jmb.model.ILogic;

public class View implements IView {

    public static ILogic logic;

    public static BoardView sceneBoard;

    public static LeaderboardView sceneLeaderboard;

    public static LogIn sceneLogIn;

    public  static SettingsView sceneImpostazioni;

    public  static MainMenu sceneMainMenu;

    public static LoadGameView sceneLoadView;
    public static TutorialView sceneTutorial;

    public static Musica sceneMusica = new Musica();

    @Override
    public void openBlackExit(){
        sceneBoard.openBlackExit();
    }

    @Override
    public void openWhiteExit() {
        sceneBoard.openWhiteExit();
    }

    @Override
    public void rollDice() {
        sceneBoard.rollDice();
    }

    @Override
    public void setDiceContrast() {
        DiceView.setDiceContrast(sceneBoard.diceArray);
    }

    @Override
    public void setPawnsForTurn() {
        BoardViewRedraw.redrawPawns(sceneBoard);
    }

    @Override
    public void backBTNSetDisable (boolean disable) {
        sceneBoard.backBTN.setDisable(disable);
    }

    @Override
    public void closeBlackExit () {
        sceneBoard.closeBlackExit();
    }

    @Override
    public void closeWhiteExit() {
        sceneBoard.closeWhiteExit();
    }

    @Override
    public void gameWon(String whitePlayer, String blackPlayer, boolean whiteWon, boolean doubleWin, int tournamentStatus) {
        sceneBoard.gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, tournamentStatus);
    }

    @Override
    public void playmusica() {sceneMusica.Pawn.play();}

}
