package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import jmb.model.SaveGameReader;

import static jmb.App.getStage;
import static jmb.view.ConstantsView.cb;
import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;

public class LoadGameView {

    @FXML
    private Label blackPlayerName;
    @FXML
    private Circle blackPlayerPawn;
    @FXML
    private ImageView hourglass;
    @FXML
    private Label timerLabel;
    @FXML
    private Label whitePlayerName;
    @FXML
    private Circle whitePlayerPawn;
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
    private final double SEPARATOR_RATIO = 0.2625;

    public void initialize() {
        System.out.println("Sto per andare in logic");
        refreshSaveList();
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
                - Andare a recuperare tutti i dati tramite SaveGameReader - FATTO
                - Utilizzare Base64.Decoder per recuperare il ByteArray dell'immagine - FATTO
                - Rigenerare l'immagine da mettere in un'ImageView - FATTO
                - Pescare e visualizzare i dati del salvataggio
                - Renderizzare l'ImageView - FATTO
          */

    }

    protected void refreshSaveList() {
        ObservableList saveList = FXCollections.observableList(logic.getSaveList());
        savesListView.setItems(saveList);
    }

    private void renderSelection() {
        saveName = savesListView.getSelectionModel().getSelectedItem().toString();
        String whitePlayer = logic.getLoadViewData(saveName)[WHITE];
        String blackPlayer = logic.getLoadViewData(saveName)[BLACK];
        String turnDuration = logic.getLoadViewData(saveName)[TIME].concat(" secondi");
        if (turnDuration.equals("0 secondi"))
            turnDuration = "Nessun Timer";
        int width = (int)logic.getImageDimensions(saveName)[WIDTH];
        int height = (int)logic.getImageDimensions(saveName)[HEIGHT];
        WritableImage wImg = new WritableImage(width, height);
        wImg.getPixelWriter().setPixels(0, 0, width, height,
                PixelFormat.getByteBgraInstance(),
                logic.getImageBytes(saveName), 0, width * 4);
        saveImageView.setImage(wImg);
        loadSaveButton.setDisable(false);
        whitePlayerName.setText(whitePlayer);
        blackPlayerName.setText(blackPlayer);
        timerLabel.setText(turnDuration);

    }

    private void changeDimensions() {
        window.setDividerPosition(0, SEPARATOR_RATIO);
        double imageWidth = window.getWidth()*(1-SEPARATOR_RATIO)*0.5;
        saveImageView.setFitWidth(imageWidth);
        double imageHeight = window.getHeight()*0.5;
        saveImageView.setFitHeight(imageHeight);
        saveImageView.setLayoutX(window.getWidth()*(1-SEPARATOR_RATIO)/2 - imageWidth/2);
        saveImageView.setLayoutY((window.getHeight() - imageHeight)/2);
        double xAnchor = 15;
        double yAnchor = 10;
        saveDetailView.setLeftAnchor(whitePlayerPawn, xAnchor);
        saveDetailView.setRightAnchor(blackPlayerPawn, xAnchor);
        saveDetailView.setTopAnchor(whitePlayerPawn, yAnchor);
        saveDetailView.setTopAnchor(blackPlayerPawn, yAnchor);
        double pawnRadius = window.getHeight() * 0.04;
        whitePlayerPawn.setRadius(pawnRadius);
        blackPlayerPawn.setRadius(pawnRadius);
        whitePlayerName.setPrefHeight(pawnRadius*2);
        blackPlayerName.setPrefHeight(pawnRadius*2);
        saveDetailView.setTopAnchor(whitePlayerName, yAnchor);
        saveDetailView.setTopAnchor(blackPlayerName, yAnchor);
        double labelXAnchor = (xAnchor + pawnRadius) * 2 + 5;
        saveDetailView.setLeftAnchor(whitePlayerName, labelXAnchor);
        saveDetailView.setRightAnchor(blackPlayerName, labelXAnchor);
        hourglass.setFitHeight(pawnRadius*3);
        hourglass.setFitWidth(pawnRadius*2);
        timerLabel.setPrefHeight(pawnRadius*3);
        saveDetailView.setBottomAnchor(hourglass, yAnchor);
        saveDetailView.setBottomAnchor(timerLabel, yAnchor);
        saveDetailView.setLeftAnchor(hourglass, xAnchor);
        saveDetailView.setLeftAnchor(timerLabel, labelXAnchor);




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