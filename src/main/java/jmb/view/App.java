package jmb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jmb.model.Logic;

import java.io.IOException;

import static jmb.view.View.logic;
import static jmb.model.Logic.view;
import static jmb.view.ConstantsView.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;

    private static Scene scene;


    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        view.initializeMusic();
        this.stage = stage;
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        scene = new Scene (loadFXML(MAIN_MENU), logic.getResolutionWidth(), logic.getResolutionHeight());
        setStageOptions();
        stage.setScene(scene);
        stage.show();
    }

    public static void changeRoot (String newRoot) {
        try {
            scene.setRoot(loadFXML(newRoot));
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent out = fxmlLoader.load();
        switch (fxml) {
            case PLAY_GAME:
                View.sceneGame = fxmlLoader.getController();
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

    private static void setStageOptions() {
        stage.setFullScreen(logic.getFullScreen());
        stage.setResizable(!logic.getLockResolution());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
