package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import jmb.model.SaveGameReader;

import static jmb.App.getStage;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;

public class LoadGameView extends GameBoard {

    //TODO UNA VOLTA FINITA UNA PARTITA PRECEDENTEMENTE SALVATA,
    // CANCELLARE IL SALVATAGGIO, CHIEDERE ALL'UTENTE SE CANCELLARE
    // O NON FARE NULLA?

    //TODO
    //  VALUTARE SE TOGLIERE UNO DEGLI STRATI DI ANCHORPANE VISTE
    //  LE MODIFICHE ALL'FXML

    private final static double HORIZONTAL_RESIZE_FACTOR = 0.45;
    private final static double VERTICAL_RESIZE_FACTOR = 0.55;
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
    private AnchorPane window;
    @FXML
    private AnchorPane saveDetailAnchor;
    @FXML
    private AnchorPane saveListAnchor;
    @FXML
    private Button deleteSaveButton;
    @FXML
    private Label blackPoints;
    @FXML
    private Label whitePoints;
    @FXML
    private Label tournamentLabel;
    @FXML
    private ImageView tournamentCup;
    private String saveName;
    private final double SEPARATOR_RATIO = 0.2625;


    public void initialize() {
        this.boardAnchor = saveDetailAnchor;
        addChildrenToAnchor();

        refreshSaveList();
        savesListView.getSelectionModel().selectedItemProperty().addListener(listener -> renderSelection());
        whitePlayerPawn.setFill(pedIn1);
        whitePlayerPawn.setStroke(pedOut1);
        blackPlayerPawn.setFill(pedIn2);
        blackPlayerPawn.setStroke(pedOut2);
        //Si impedisce all'utente di muovere il divisore dello SplitPane
        window.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  div.setMouseTransparent(true) );


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
        if (savesListView.getSelectionModel().getSelectedItem() == null)
            renderNoSelection();
        else {
            saveName = savesListView.getSelectionModel().getSelectedItem().toString();
            saveDetailTitledPane.setText(saveName);
            String[] saveData = logic.getLoadViewData(saveName);
            if (saveData[TIME].equals("0 secondi"))
                saveData[TIME]= "Nessun Timer";
            if (saveData[TOURNAMENT_POINTS].equals("0")) {
                tournamentCup.setVisible(false);
                tournamentLabel.setVisible(false);
                blackPoints.setVisible(false);
                whitePoints.setVisible(false);
            } else {
                tournamentCup.setVisible(true);
                tournamentLabel.setVisible(true);
                blackPoints.setVisible(true);
                whitePoints.setVisible(true);
                whitePoints.setText(saveData[WHITE_WON_POINTS]);
                blackPoints.setText(saveData[BLACK_WON_POINTS]);
                tournamentLabel.setText(saveData[TOURNAMENT_POINTS]);
            }
            int width = (int) logic.getImageDimensions(saveName)[WIDTH];
            int height = (int) logic.getImageDimensions(saveName)[HEIGHT];
            WritableImage wImg = new WritableImage(width, height);
            wImg.getPixelWriter().setPixels(0, 0, width, height,
                    PixelFormat.getByteBgraInstance(),
                    logic.getImageBytes(saveName), 0, width * 4);
            loadSaveButton.setDisable(false);
            deleteSaveButton.setDisable(false);
            whitePlayerName.setText(saveData[WHITE]);
            blackPlayerName.setText(saveData[BLACK]);
            timerLabel.setText(saveData[TIME]);
            changeDimensions();
        }

    }

    private void renderNoSelection() {
        String noSave = "---";
        loadSaveButton.setDisable(true);
        deleteSaveButton.setDisable(true);
        whitePlayerName.setText(noSave);
        blackPlayerName.setText(noSave);
        timerLabel.setText(noSave);
        saveDetailTitledPane.setText(noSave);
        tournamentCup.setVisible(false);
        blackPoints.setVisible(false);
        whitePoints.setVisible(false);
    }

    private void changeDimensions() {
        //window.setDividerPosition(0, SEPARATOR_RATIO);
        double listWidth = window.getWidth()*SEPARATOR_RATIO;
        double detailWidth = window.getWidth()*(1- SEPARATOR_RATIO);
        saveListAnchor.setPrefWidth(listWidth);
        saveDetailAnchor.setPrefWidth(detailWidth);
        //window.setRightAnchor(saveListAnchor, window.getWidth()*(1-SEPARATOR_RATIO));
        //window.setLeftAnchor(saveDetailAnchor, window.getWidth()*SEPARATOR_RATIO);
        saveDetailView.setRightAnchor(deleteSaveButton, saveDetailView.getRightAnchor(loadSaveButton) + loadSaveButton.getWidth());
        saveDetailView.setLayoutX(listWidth);
        double imageWidth = window.getWidth()*(1-SEPARATOR_RATIO)*0.5;
        double imageHeight = window.getHeight()*0.5;
        double xAnchor = 15;
        double yAnchor = 10;
        saveDetailView.setLeftAnchor(whitePlayerPawn, xAnchor);
        saveDetailView.setRightAnchor(blackPlayerPawn, xAnchor);
        saveDetailView.setTopAnchor(whitePlayerPawn, yAnchor);
        saveDetailView.setTopAnchor(blackPlayerPawn, yAnchor);
        double pawnRadius = window.getHeight() * 0.036;
        whitePlayerPawn.setRadius(pawnRadius);
        blackPlayerPawn.setRadius(pawnRadius);
        whitePlayerName.setPrefHeight(pawnRadius*2);
        blackPlayerName.setPrefHeight(pawnRadius*2);
        saveDetailView.setTopAnchor(whitePlayerName, yAnchor);
        saveDetailView.setTopAnchor(blackPlayerName, yAnchor);
        double labelXAnchor = (xAnchor + pawnRadius) * 2;
        saveDetailView.setLeftAnchor(whitePlayerName, labelXAnchor);
        saveDetailView.setRightAnchor(blackPlayerName, labelXAnchor);
        hourglass.setFitHeight(pawnRadius*3);
        hourglass.setFitWidth(pawnRadius*2);
        timerLabel.setPrefHeight(pawnRadius*3);
        saveDetailView.setBottomAnchor(hourglass, yAnchor);
        saveDetailView.setBottomAnchor(timerLabel, yAnchor);
        saveDetailView.setLeftAnchor(hourglass, xAnchor);
        saveDetailView.setLeftAnchor(timerLabel, labelXAnchor);
        LoadGameViewRedraw.setHResizeFactor(HORIZONTAL_RESIZE_FACTOR);
        LoadGameViewRedraw.setVResizeFactor(VERTICAL_RESIZE_FACTOR);
        LoadGameViewRedraw.redrawAll(this);
        if (tournamentCup.isVisible()) {
            double circleDiameter = whitePlayerPawn.getRadius()*2;
            double cupSize = circleDiameter * 1.5;
            tournamentCup.setFitWidth(cupSize);
            tournamentCup.setFitHeight(cupSize);
            tournamentCup.setLayoutX((detailWidth - tournamentCup.getFitWidth())/2);
            AnchorPane.setTopAnchor(tournamentCup, yAnchor);
            tournamentLabel.setPrefWidth(tournamentCup.getFitWidth());
            tournamentLabel.setPrefHeight(tournamentCup.getFitHeight()*0.7);
            tournamentLabel.setLayoutX(tournamentCup.getLayoutX());
            AnchorPane.setTopAnchor(tournamentLabel, yAnchor);
            whitePoints.setPrefWidth(circleDiameter);
            whitePoints.setPrefHeight(circleDiameter);
            AnchorPane.setLeftAnchor(whitePoints, xAnchor);
            AnchorPane.setTopAnchor(whitePoints, yAnchor);
            blackPoints.setPrefWidth(circleDiameter);
            blackPoints.setPrefHeight(circleDiameter);
            AnchorPane.setRightAnchor(blackPoints, xAnchor);
            AnchorPane.setTopAnchor(blackPoints, yAnchor);
        }
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        jmb.App.MainMenu();
        getStage().setFullScreen(cb);
    }

    @FXML
    void loadGame(ActionEvent event) {
            //TODO VECCHIO logic.getBoard().setUpSavedGame(SaveGameReader.readSaveGame(saveName));
        logic.setUpSavedGame(saveName);
        jmb.App.board();
        getStage().setFullScreen(cb);

    }

    @FXML
    void deleteSave(ActionEvent event) {
        logic.deleteSaveFile(saveDetailTitledPane.getText());
        refreshSaveList();
    }

}