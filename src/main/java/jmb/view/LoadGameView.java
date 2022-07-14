package jmb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.ConstantsShared.*;

public class LoadGameView extends GameBoard {

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
    protected AnchorPane window;
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
    protected final double SEPARATOR_RATIO = 0.2625;
    protected int[][] saveMatrix;


    public void initialize() {
        this.boardAnchor = saveDetailAnchor;
        addChildrenToAnchor();

        refreshSaveList();
        savesListView.getSelectionModel().selectedItemProperty().addListener(listener -> renderSelection());
        whitePlayerPawn.setFill(Color.web(logic.getWhitePawnFill()));
        whitePlayerPawn.setStroke(Color.web(logic.getWhitePawnStroke()));
        blackPlayerPawn.setFill(Color.web(logic.getBlackPawnFill()));
        blackPlayerPawn.setStroke(Color.web(logic.getBlackPawnStroke()));
        //Si impedisce all'utente di muovere il divisore dello SplitPane
        window.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  div.setMouseTransparent(true) );


        //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
        window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


        //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
        window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }

    protected void refreshSaveList() {
        ObservableList saveList = FXCollections.observableList(logic.getSaveList());
        savesListView.setItems(saveList);
    }

    private void renderSelection() {
        if (savesListView.getSelectionModel().getSelectedItem() == null) {
            renderNoSelection();
            saveMatrix = null;
        }
        else {
            LoadGameViewRedraw.setPawnsVisibility(this, true);
            saveName = savesListView.getSelectionModel().getSelectedItem().toString();
            saveMatrix = logic.getSaveMatrix(saveName);
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
            loadSaveButton.setDisable(false);
            deleteSaveButton.setDisable(false);
            whitePlayerName.setText(saveData[WHITE_NAME]);
            blackPlayerName.setText(saveData[BLACK_NAME]);
            timerLabel.setText(saveData[TIME]);
            changeDimensions();
        }

    }

    protected void renderNoSelection() {
        String noSave = "---";
        LoadGameViewRedraw.setPawnsVisibility(this, false);
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
        //window.setRightAnchor(saveListAnchor, window.getWidth()*(1-SEPARATOR_RATIO)); TODO
        //window.setLeftAnchor(saveDetailAnchor, window.getWidth()*SEPARATOR_RATIO);
        //AnchorPane.setRightAnchor(deleteSaveButton, 41.0);
        saveDetailView.setLayoutX(listWidth);
        double imageWidth = window.getWidth()*(1-SEPARATOR_RATIO)*0.5;
        double imageHeight = window.getHeight()*0.5;
        double xAnchor = 15;
        double yAnchor = 10;
        AnchorPane.setLeftAnchor(whitePlayerPawn, xAnchor);
        AnchorPane.setRightAnchor(blackPlayerPawn, xAnchor);
        AnchorPane.setTopAnchor(whitePlayerPawn, yAnchor);
        AnchorPane.setTopAnchor(blackPlayerPawn, yAnchor);
        double pawnRadius = window.getHeight() * 0.036;
        whitePlayerPawn.setRadius(pawnRadius);
        blackPlayerPawn.setRadius(pawnRadius);
        whitePlayerName.setPrefHeight(pawnRadius*2);
        blackPlayerName.setPrefHeight(pawnRadius*2);
        AnchorPane.setTopAnchor(whitePlayerName, yAnchor);
        AnchorPane.setTopAnchor(blackPlayerName, yAnchor);
        double labelXAnchor = (xAnchor + pawnRadius) * 2;
        AnchorPane.setLeftAnchor(whitePlayerName, labelXAnchor);
        AnchorPane.setRightAnchor(blackPlayerName, labelXAnchor);
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
        App.mainMenu();
    }

    @FXML
    void loadGame(ActionEvent event) {
        logic.setUpSavedGame(saveName);
        App.board();
    }

    @FXML
    void deleteSave(ActionEvent event) {
        logic.deleteSaveFile(saveDetailTitledPane.getText());
        refreshSaveList();
    }

}