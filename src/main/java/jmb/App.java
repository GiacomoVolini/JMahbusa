package jmb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import jmb.model.LeaderboardLogic;
import jmb.model.Logic;
import jmb.view.BoardView;
import jmb.view.BoardViewRedraw;
import jmb.view.View;
import static jmb.view.ConstantsView.*;

import java.io.IOException;

import static jmb.view.View.logic;
import static jmb.model.Logic.view;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    private static Scene sceneMainMenu;
    private static Scene sceneBoard;
    private static Scene sceneSettings;
    private static Scene sceneLeaderBoard;
    private static Scene sceneLogIn;
    private static Scene sceneLoadGame;


    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        //stage.initStyle(StageStyle.UNDECORATED);
        sceneMainMenu = new Scene(loadFXML("view/MainMenu"), 640, 480);
        interfaceInstantiation();
        stage.setScene(sceneMainMenu);
        stage.show();


    }

    public static void MainMenu() {
            stage.setScene(sceneMainMenu);
            stage.show();
    }

    public static void login() throws IOException{
        sceneLogIn = new Scene(loadFXML("view/Login"), 640, 480);
        stage.setScene(sceneLogIn);
        stage.show();
    }

    public static void board() {
        try {
            sceneBoard = new Scene(loadFXML("view/GameBoard"), 640, 480);
            stage.setScene(sceneBoard);
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void edit() throws IOException{
        if (sceneSettings == null) {
            sceneSettings = new Scene(loadFXML("view/MenuImpostazioni"));
        }
        stage.setScene(sceneSettings);
        stage.show();
    }

    public static void leaderBoard() throws IOException{
        if (sceneLeaderBoard == null){
            sceneLeaderBoard = new Scene(loadFXML("view/Leaderboard"));
        }
        stage.setScene(sceneLeaderBoard);
        stage.show();
    }

    public static void loadGame() throws IOException {
        if (sceneLoadGame == null){
            sceneLoadGame = new Scene(loadFXML("view/LoadGameView"));
        }
        stage.setScene(sceneLoadGame);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        sceneMainMenu.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent out = fxmlLoader.load();
        if(fxml == "view/GameBoard"){
            View.sceneBoard = fxmlLoader.getController();
        } else if(fxml == "view/Leaderboard"){
            View.sceneLeaderboard = fxmlLoader.getController();
        } else if(fxml == "view/Login") {
            View.sceneLogIn = fxmlLoader.getController();
        } else if(fxml == "view/MenuImpostazioni") {
            View.sceneImpostazioni = fxmlLoader.getController();
        } else if(fxml == "view/MainMenu"){
            View.sceneMainMenu = fxmlLoader.getController();
        } else if(fxml == "view/LoadGameView"){
            View.sceneLoadView = fxmlLoader.getController();
        }
        return out;
    }

    public static void main(String[] args) {
        launch();
    }



    //La seguente classe è utilizzata per l'implementazione delle interfacce tra i vari package
    private static void interfaceInstantiation() throws IOException{
        View.logic = new Logic();
        Logic.view = new View();
        logic.initializeBoardLogic();
        logic.initializeLeaderboardLogic();
        logic.initializeSettingsLogic();
    }

}
