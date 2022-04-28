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
        BoardViewRedraw.redrawPawns(sceneBoard.pawnArrayWHT, sceneBoard.pawnArrayBLK, sceneBoard.regArrayBot,
                sceneBoard.regArrayTop, sceneBoard.whiteExitRegion, sceneBoard.blackExitRegion);
    }

    @Override
    public void blackWins (boolean doubleWin) {
        //TODO METTERE LE ISTRUZIONI GIUSTE
        //  PLACEHOLDER
        System.out.println("IL NERO VINCE");
        sceneBoard.gameWon(BLACK_WINS, doubleWin);
    }

    @Override
    public void whiteWins (boolean doubleWin) {
        //TODO METTERE LE ISTRUZIONI GIUSTE
        //  PLACEHOLDER
        System.out.println("IL BIANCO VINCE");
        sceneBoard.gameWon(WHITE_WINS, doubleWin);
    }

}
