package jmb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static jmb.logic.Logic.view;
import static jmb.view.ConstantsView.MAIN_MENU;
import static jmb.view.View.logic;

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
        App.stage = stage;
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        scene = new Scene (loadFXML(MAIN_MENU), logic.getSetting("Video","resolutionWidth",int.class), logic.getSetting("Video","resolutionHeight",int.class));
        setStageOptions();
        stage.setTitle("JMahbusa");
        stage.setScene(scene);
        stage.setWidth(logic.getSetting("Video","resolutionWidth",int.class));
        stage.setHeight(logic.getSetting("Video","resolutionHeight",int.class));
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
        stage.setFullScreen(logic.getSetting("Video", "fullScreen", boolean.class));
        stage.setResizable(!logic.getSetting("Video", "lockResolution", boolean.class));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
