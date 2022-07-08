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
    private static Scene sceneTutorial;
    private static final String MAIN_MENU = "MainMenu";
    private static final String LOG_IN = "Login";
    private static final String SETTINGS = "MenuImpostazioni";
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
        sceneMainMenu = new Scene(loadFXML(MAIN_MENU), logic.getResolutionWidth(), logic.getResolutionHeight());
        setStageOptions();
        stage.setScene(sceneMainMenu);
        stage.show();
    }

    public static void mainMenu(){
        try {
            if (stage.isFullScreen() == logic.getFullScreen())
                sceneMainMenu = new Scene(loadFXML(MAIN_MENU), stage.getScene().getWidth(), stage.getScene().getHeight());
            else
                sceneMainMenu = new Scene(loadFXML(MAIN_MENU), logic.getResolutionWidth(), logic.getResolutionHeight());
            stage.setScene(sceneMainMenu);
            setStageOptions();
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //TODO FORSE I METODI SOTTO POSSONO ESSERE PORTATI A UNO SOLO

    //TODO STO PROVANDO A RICREARE OGNI VOLTA LE SCENE, IN MODO DA PASSARE LA GIUSTA RISOLUZIONE DI VOLTA IN VOLTA
    public static void login() throws IOException{
        sceneLogIn = new Scene(loadFXML(LOG_IN), stage.getScene().getWidth(), stage.getScene().getHeight());
        stage.setScene(sceneLogIn);
        setStageOptions();
        stage.show();
    }

    public static void board() {
        try {
            sceneBoard = new Scene(loadFXML(PLAY_GAME), stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(sceneBoard);
            setStageOptions();
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void edit() throws IOException{
        //if (sceneSettings == null) {TODO TEST
            sceneSettings = new Scene(loadFXML(SETTINGS), stage.getScene().getWidth(), stage.getScene().getHeight());
        //}TODO TEST
        stage.setScene(sceneSettings);
        setStageOptions();
        stage.show();
    }

    public static void leaderBoard() throws IOException{
        //if (sceneLeaderBoard == null){ TODO TEST
            sceneLeaderBoard = new Scene(loadFXML(LEADERBOARDS), stage.getScene().getWidth(), stage.getScene().getHeight());
        //}TODO TEST
        stage.setScene(sceneLeaderBoard);
        setStageOptions();
        stage.show();
    }

    public static void loadGame() throws IOException {
        //if (sceneLoadGame == null){TODO TEST
            sceneLoadGame = new Scene(loadFXML(LOAD_GAME), stage.getScene().getWidth(), stage.getScene().getHeight());
        //}TODO TEST
        stage.setScene(sceneLoadGame);
        setStageOptions();
        stage.show();
    }

    public static void tutorial() throws IOException {
        //if (sceneTutorial == null) {TODO TEST
            sceneTutorial = new Scene(loadFXML(TUTORIAL), stage.getScene().getWidth(), stage.getScene().getHeight());
        //}TODO TEST
        stage.setScene(sceneTutorial);
        setStageOptions();
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
