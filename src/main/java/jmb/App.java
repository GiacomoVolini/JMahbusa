package jmb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jmb.model.ILogic;
import jmb.model.Logic;
import jmb.view.IView;
import jmb.view.View;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    private static Scene scene;
    private static Scene scene1;
    private static Scene scene2;
    private static Scene scene4;


    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        scene = new Scene(loadFXML("view/MainMenu"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void MainMenu() throws IOException {
        scene4 = new Scene(loadFXML("view/MainMenu"));
        stage.setScene(scene4);
        stage.show();
    }

    public static void board() throws IOException{
        scene1 = new Scene(loadFXML("view/GameBoard"));
        stage.setScene(scene1);
        stage.show();
    }

    public static void edit() throws IOException{
        scene2 = new Scene(loadFXML("view/MenuImpostazioni"));
        stage.setScene(scene2);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


    //La seguente classe è utilizzata per l'implementazione delle interfacce tra i vari package
    private static void interfaceInstantiation() {
        View.logic = new Logic();
        Logic.view = new View();
    }

}
