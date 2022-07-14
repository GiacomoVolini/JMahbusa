package jmb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jmb.model.Logic;

import java.io.IOException;

import static jmb.view.View.logic;

//TODO QUANDO SI CAMBIA SCENE L'APPLICAZIONE FA UNO SCATTO IN FULLSCREEN, POI TORNA IN FINESTRA ANCHE SE NON DOVREBBE
/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;

    private static Scene scene;
    private static Scene sceneMainMenu;
    private static Scene sceneBoard;
    private static Scene sceneSettings;
    private static Scene sceneLeaderBoard;
    private static Scene sceneLogIn;
    private static Scene sceneLoadGame;
    private static Scene sceneTutorial;
    private static final String MAIN_MENU = "MainMenu";
    private static final String LOG_IN = "Login";
    private static final String SETTINGS = "SettingsView";
    private static final String LOAD_GAME = "LoadGameView";
    private static final String LEADERBOARDS = "Leaderboard";
    private static final String PLAY_GAME = "GameBoard";
    private static final String TUTORIAL = "TutorialView";


    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        interfaceInstantiation();
        scene = new Scene (loadFXML(MAIN_MENU), logic.getResolutionWidth(), logic.getResolutionHeight());
        setStageOptions();
        stage.setScene(scene);
        stage.show();
    }

    public static void mainMenu(){
        try {
            scene.setRoot(loadFXML(MAIN_MENU));
            //setStageOptions(); TODO TEST
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //TODO FORSE I METODI SOTTO POSSONO ESSERE PORTATI A UNO SOLO

    //TODO STO PROVANDO A RICREARE OGNI VOLTA LE SCENE, IN MODO DA PASSARE LA GIUSTA RISOLUZIONE DI VOLTA IN VOLTA
    public static void login() throws IOException{
        scene.setRoot(loadFXML(LOG_IN));
        //setStageOptions(); TODO TEST
        stage.show();
    }

    public static void board() {
        try {
            scene.setRoot(loadFXML(PLAY_GAME));
            //setStageOptions(); TODO TEST
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void edit() throws IOException{
        //if (sceneSettings == null) {TODO TEST
            scene.setRoot(loadFXML(SETTINGS));
        //setStageOptions(); TODO TEST
        stage.show();
    }

    public static void leaderBoard() throws IOException{
        //if (sceneLeaderBoard == null){ TODO TEST
            scene.setRoot(loadFXML(LEADERBOARDS));
        //setStageOptions(); TODO TEST
        stage.show();
    }

    public static void loadGame() throws IOException {
        //if (sceneLoadGame == null){TODO TEST
            scene.setRoot(loadFXML(LOAD_GAME));
        //setStageOptions(); TODO TEST
        stage.show();
    }

    public static void tutorial() throws IOException {
        //if (sceneTutorial == null) {TODO TEST
            scene.setRoot(loadFXML(TUTORIAL));
        //setStageOptions(); TODO TEST
        stage.show();
    }

    //TODO FORSE CANCELLARE, MAI USATO
    static void setRoot(String fxml) throws IOException {
        sceneMainMenu.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent out = fxmlLoader.load();
        switch (fxml) {
            case PLAY_GAME:
                View.sceneBoard = fxmlLoader.getController();
                break;
            case LEADERBOARDS:
                View.sceneLeaderboard = fxmlLoader.getController();
                break;
            case LOG_IN:
                View.sceneLogIn = fxmlLoader.getController();
                break;
            case SETTINGS:
                View.sceneImpostazioni = fxmlLoader.getController();
                break;
            case MAIN_MENU:
                View.sceneMainMenu = fxmlLoader.getController();
                break;
            case LOAD_GAME:
                View.sceneLoadView = fxmlLoader.getController();
                break;
            case TUTORIAL:
                View.sceneTutorial = fxmlLoader.getController();
                break;
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

    public static void main(String[] args) {
        launch(args);
    }

}
