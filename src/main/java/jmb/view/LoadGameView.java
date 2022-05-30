package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import jmb.model.SaveGameReader;

import java.io.IOException;

import static jmb.App.getStage;
import static jmb.view.ConstantsView.cb;
import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;

public class LoadGameView {

    @FXML
    private Button loadSaveButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private TitledPane saveDetailTitledPane;

    @FXML
    private AnchorPane saveDetailView;

    @FXML
    private AnchorPane savesAnchorPane;

    @FXML
    private ListView<?> savesListView;

    @FXML
    private TitledPane savesTitlePane;

    @FXML
    private SplitPane window;

    @FXML
    private ImageView saveImageView;

    private String saveName;


    public void initialize() {
        System.out.println("Sto per andare in logic");
        ObservableList saveList = FXCollections.observableList(logic.getSaveList());
        System.out.println("Sono tornato in LoadGameView\nOra stampo la lista ricevuta");
        System.out.println(saveList.toString());
        savesListView.setItems(saveList);
        savesListView.getSelectionModel().selectedItemProperty().addListener(listener -> renderSelection());

        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        /* TODO
            Quando si crea il salvataggio bisogna anche generare un'immagine del tabellone
                - Usare snapshot, usando come viewport un rettangolo che vada a coprire solo il tabellone - FATTO
                - Una volta ottenuto lo snapshot (dovrebbe generare una WritableImage), convertirlo in ByteArray - FATTO
                - Convertire il ByteArray in Stringa tramite Base64.Encoder - FATTO
                - Aggiungere la stringa nel salvataggio - FATTO
            Quando si sceglie un salvataggio della lista:
                - Andare a recuperare tutti i dati tramite SaveGameReader
                - Utilizzare Base64.Decoder per recuperare il ByteArray dell'immagine
                - Rigenerare l'immagine da mettere in un'ImageView
                - Pescare e visualizzare i dati del salvataggio
                - Renderizzare l'ImageView
          */

    }

    private void renderSelection() {
        saveName = savesListView.getSelectionModel().getSelectedItem().toString();
        String whitePlayer = logic.getLoadViewData(saveName)[WHITE];
        String blackPlayer = logic.getLoadViewData(saveName)[BLACK];
        // TODO FORSE CANCELLARE Image img = new Image(new ByteArrayInputStream(logic.getImageBytes(saveName)));
        int width = (int)logic.getImageDimensions(saveName)[0];
        int height = (int)logic.getImageDimensions(saveName)[1];
        //TODO SOSTITUIRE NUMERI SOPRA CON COSTANTI APPOSITE
        WritableImage wImg = new WritableImage(width, height);
        wImg.getPixelWriter().setPixels(0, 0, width, height,
                PixelFormat.getByteBgraInstance(),
                logic.getImageBytes(saveName), 0, width * 4);
        saveImageView.setImage(wImg);
        loadSaveButton.setDisable(false);

    }

    private void changeDimensions() {
        window.setDividerPosition(0, 0.2625);
        saveImageView.setFitWidth(window.getWidth()*0.4);
        saveImageView.setFitHeight(window.getHeight()*0.7);
        System.out.println(saveDetailView.getWidth()*0.8);
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        jmb.App.MainMenu();
        getStage().setFullScreen(cb);
    }

    @FXML
    void loadGame(ActionEvent event) {
            logic.getBoard().setUpSavedGame(SaveGameReader.readSaveGame(saveName));
            jmb.App.board();
            getStage().setFullScreen(cb);

    }

}