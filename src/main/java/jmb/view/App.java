package jmb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static jmb.view.View.logic;
import static jmb.logic.Logic.view;
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
        stage.setTitle("JMahbusa");
        stage.setScene(scene);
        stage.setWidth(logic.getResolutionWidth());
        stage.setHeight(logic.getResolutionHeight());
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
        view.setCurrentScene(fxmlLoader.getController());
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
