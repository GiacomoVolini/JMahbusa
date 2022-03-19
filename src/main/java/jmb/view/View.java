package jmb.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import jmb.App;
import jmb.model.ILogic;

import java.io.IOException;

public class View implements IView {

    public static ILogic logic;

    public static BoardView sceneBoard;


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
        //TODO
        //      Controllare che non siano già visibili (due tiri doppi di fila o più)
        //      Bloccare resizing
        //      Rendere visibili dadi doppi
        //      Iniziare animazione
        //      Sbloccare resizing
        sceneBoard.openDoubleDice();
    }

    @Override
    public void closeDoubleDice() {
        //TODO
        //      Controllare che non siano già invisibili
        //      Bloccare resizing
        //      Iniziare animazione "chiusura"
        //      Rendere invisibili dadi doppi
        //      Sbloccare resizing
        sceneBoard.closeDoubleDice();
    }

}
