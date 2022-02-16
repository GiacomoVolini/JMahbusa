package jmb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jmb.model.Logic;
import jmb.view.BoardView;
import jmb.view.View;

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


    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        sceneMainMenu = new Scene(loadFXML("view/MainMenu"), 640, 480);
        interfaceInstantiation();
        stage.setScene(sceneMainMenu);
        stage.show();

    }

    public static void MainMenu() throws IOException {
        stage.setScene(sceneMainMenu);
        stage.show();
    }

    public static void board() throws IOException{
        if (sceneBoard==null) {
            sceneBoard = new Scene(loadFXML("view/GameBoard"), 640, 480);
        }
        stage.setScene(sceneBoard);
        stage.show();
    }

    public static void edit() throws IOException{
        if (sceneSettings == null) {
            sceneSettings = new Scene(loadFXML("view/MenuImpostazioni"));
        }
        stage.setScene(sceneSettings);
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

    }

    private static <T> T getNodeController(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        loader.load();
        return loader.getController();
    }


}
