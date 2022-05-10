package jmb.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import jmb.App;
import jmb.model.ILogic;

import static jmb.ConstantsShared.*;

import java.io.IOException;

public class View implements IView {

    public static ILogic logic;

    public static BoardView sceneBoard;

    public static LeaderboardView sceneLeaderboard;

    public static LogIn sceneLogIn;

    public  static MenuImpostazioni sceneImpostazioni;

    public  static MainMenu sceneMainMenu;

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
    public void openDoubleDice() {
        sceneBoard.openDoubleDice();
    }

    @Override
    public void closeDoubleDice() {
        sceneBoard.closeDoubleDice();
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
        //TODO VECCHIO sceneBoard.gameWon(winner, loser, whiteWon, doubleWin);
        sceneBoard.gameWon(whitePlayer, blackPlayer, whiteWon, doubleWin, tournamentStatus);

    }

    @Override
    public void playmusica() {sceneMusica.Pawn.play();}
}
