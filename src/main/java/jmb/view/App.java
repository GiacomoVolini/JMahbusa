package jmb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jmb.model.Logic;

import java.io.IOException;

import static jmb.view.View.logic;

//TODO CAMBIARE TUTTI I RIFERIMENTI A PATH O NEL JAR COMPILATO NON FUNZIONA NIENTE
//TODO QUANDO SI CAMBIA SCENE L'APPLICAZIONE FA UNO SCATTO IN FULLSCREEN, POI TORNA IN FINESTRA ANCHE SE NON DOVREBBE
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
        interfaceInstantiation();
        sceneMainMenu = new Scene(loadFXML("MainMenu"), logic.getResolutionWidth(), logic.getResolutionHeight());
        setStageOptions();
        stage.setScene(sceneMainMenu);
        stage.show();
    }

    public static void MainMenu() {
            stage.setScene(sceneMainMenu);
            setStageOptions();
            stage.show();
    }

    public static void login() throws IOException{
        sceneLogIn = new Scene(loadFXML("Login"), 640, 480);
        stage.setScene(sceneLogIn);
        setStageOptions();
        stage.show();
    }

    public static void board() {
        try {
            sceneBoard = new Scene(loadFXML("GameBoard"), 640, 480);
            stage.setScene(sceneBoard);
            setStageOptions();
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void edit() throws IOException{
        if (sceneSettings == null) {
            sceneSettings = new Scene(loadFXML("MenuImpostazioni"));
        }
        stage.setScene(sceneSettings);
        setStageOptions();
        stage.show();
    }

    public static void leaderBoard() throws IOException{
        if (sceneLeaderBoard == null){
            sceneLeaderBoard = new Scene(loadFXML("Leaderboard"));
        }
        stage.setScene(sceneLeaderBoard);
        setStageOptions();
        stage.show();
    }

    public static void loadGame() throws IOException {
        if (sceneLoadGame == null){
            sceneLoadGame = new Scene(loadFXML("LoadGameView"));
        }
        stage.setScene(sceneLoadGame);
        setStageOptions();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        sceneMainMenu.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent out = fxmlLoader.load();
        if(fxml == "GameBoard"){
            View.sceneBoard = fxmlLoader.getController();
        } else if(fxml == "Leaderboard"){
            View.sceneLeaderboard = fxmlLoader.getController();
        } else if(fxml == "Login") {
            View.sceneLogIn = fxmlLoader.getController();
        } else if(fxml == "MenuImpostazioni") {
            View.sceneImpostazioni = fxmlLoader.getController();
        } else if(fxml == "MainMenu"){
            View.sceneMainMenu = fxmlLoader.getController();
        } else if(fxml == "LoadGameView"){
            View.sceneLoadView = fxmlLoader.getController();
        }
        return out;
    }



    //La seguente classe è utilizzata per l'implementazione delle interfacce tra i vari package
    private static void interfaceInstantiation() throws IOException{
        View.logic = new Logic();
        Logic.view = new View();
        logic.initializeBoardLogic();
        logic.initializeLeaderboardLogic();
        logic.initializeSettingsLogic();
    }

    private static void setStageOptions() {
        stage.setFullScreen(logic.getFullScreen());
        stage.setResizable(!logic.getLockResolution());
    }

}
