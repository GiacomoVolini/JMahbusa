package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.nio.file.Path;
import java.util.Objects;

import static jmb.ConstantsShared.Music;
import static jmb.ConstantsShared.UNDEFINED;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.*;


public class MainMenu implements GenericGUI{

    @FXML
    private AnchorPane window;
    @FXML
    private ImageView background1;
    @FXML
    private ImageView background2;
    @FXML
    private Rectangle gameTitleBackground;
    @FXML
    private Text gameTitle;
    @FXML
    private Button newGameButton;
    @FXML
    private Button tutorialButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button leaderboardButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;

    @FXML
    private ComboBox<Image> languageMenu;
    @FXML
    private Label promptLabel;

    @FXML
    private Button promptNoButton;

    @FXML
    private TitledPane promptPane;

    @FXML
    private Button promptYesButton;

    private int imageIndex = 2;
    private int startingOpacity = 1;
    private int finalOpacity = 0;
    private boolean background1ToChange = false;
    private boolean animationToContinue = true;

    @FXML
    void closeButtonAction() {
        App.getStage().close();
    }

    @FXML
    void newGameAction() {
        if(getLogic().shouldPlayTutorial()) {
            promptPane.setVisible(true);
            loadStrings();
        }else {
            animationToContinue = false;
            openLogin();
        }
    }

    @FXML
    void openLogin() {
        getLogic().initializeLeaderboardLogic();
        getLogic().initializeGameLogic();
        getLogic().flagTutorialPlayed();
        animationToContinue = false;
        getView().changeRoot(LOG_IN);
    }

    @FXML
    void openLeaderBoard() {
        animationToContinue = false;
        getLogic().initializeLeaderboardLogic();
        getView().changeRoot(LEADERBOARDS);
    }

    @FXML
    void openMenuImpostazioni(){
        animationToContinue = false;
        getView().changeRoot(SETTINGS);
    }

    @FXML
    void openLoadGame(){
        getView().changeRoot(LOAD_GAME);
    }

    @FXML
    void openTutorial(){
        animationToContinue = false;
        getLogic().initializeTutorialLogic();
        getView().changeRoot(TUTORIAL);
        if (!getLogic().getSetting("Audio", "muteMusic", boolean.class))
            getView().playMusic(Music.TUTORIAL);
    }

    private void loadStrings() {
        newGameButton.setText(getLogic().getString("newGame"));
        loadGameButton.setText(getLogic().getString("loadGame"));
        settingsButton.setText(getLogic().getString("settings"));
        leaderboardButton.setText(getLogic().getString("leaderboards"));
        exitButton.setText(getLogic().getString("exitGame"));
        promptLabel.setText(getLogic().getString("tutorialNeverPlayed"));
        promptYesButton.setText(getLogic().getString("yes"));
        promptNoButton.setText(getLogic().getString("no"));
        promptPane.setText(getLogic().getString("newGame"));
        tutorialButton.setText(getLogic().getString("Tutorial"));
    }


    private void loadLanguages() {
        String[] supportedLanguages = getLogic().getSupportedLanguages();
        for (String language: supportedLanguages) {
            languageMenu.getItems().add(
                    new Image(Path.of(getLogic().getAppDirectory(), "languages", "flags", "flag_"+language+".png").toUri().toString()));
        }
        languageMenu.setButtonCell(new ImageListCell());
        languageMenu.setCellFactory(listView -> new ImageListCell());
        languageMenu.getSelectionModel().select(getSelectionIndex(supportedLanguages));
    }

    private int getSelectionIndex(String[] languages) {
        String currentLanguage = getLogic().getCurrentLanguage();
        boolean found = false;
        int out = UNDEFINED;
        for (int i = 0; !found && i < languages.length; i++) {
            if (currentLanguage.equals(languages[i])) {
                out = i;
                found = true;
            }
        }
        return out;
    }

    @FXML
    private void setLanguage(Event event) {
        getLogic().setCurrentLanguage(((ComboBox)event.getSource()).getSelectionModel().getSelectedIndex());
        getLogic().setSetting("General", "language",getLogic().getSupportedLanguages()[((ComboBox)event.getSource()).getSelectionModel().getSelectedIndex()]);
        getLogic().applySettingsChanges();
        loadStrings();
    }

    public void initialize() {

        loadLanguages();
        loadStrings();
        background1.fitHeightProperty().bind(window.heightProperty());
        background1.fitWidthProperty().bind(window.widthProperty());
        background2.fitWidthProperty().bind(window.widthProperty());
        background2.fitHeightProperty().bind(window.heightProperty());

            Image[] backgroundImages = new Image[]{new Image(Objects.requireNonNull(this.getClass().getResource("background1.jpg")).toString()),
                new Image(Objects.requireNonNull(this.getClass().getResource("background2.jpg")).toString()),
                new Image(Objects.requireNonNull(this.getClass().getResource("background3.jpg")).toString()),
                new Image(Objects.requireNonNull(this.getClass().getResource("background4.jpg")).toString())};
            background1.setPreserveRatio(false);
            background2.setPreserveRatio(false);
            background1.setImage(backgroundImages[0]);
            background2.setImage(backgroundImages[1]);

            // musica
            if (!getLogic().getSetting("Audio", "muteMusic", boolean.class)) {
                getView().playMusic(Music.MENU);
            }

            backgroundAnimationTimer(backgroundImages);

            //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
            window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

            //  LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
            window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

    }
    private void backgroundAnimationTimer(Image[] array) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO),
                new KeyFrame(Duration.seconds(5))
        );
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            if (animationToContinue)
                backgroundAnimationCycle(array);
        });
        timeline.play();
    }
    private void backgroundAnimationCycle(Image[] backgroundImages) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(background2.opacityProperty(), startingOpacity)),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(background2.opacityProperty(), finalOpacity))
        );
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            if (animationToContinue) {
                if (background1ToChange) {
                    background1.setImage(backgroundImages[imageIndex++]);
                } else {
                    background2.setImage(backgroundImages[imageIndex++]);
                }
                background1ToChange = !background1ToChange;
                if (imageIndex == backgroundImages.length)
                    imageIndex = 0;
                if (startingOpacity == 0) {
                    startingOpacity = 1;
                    finalOpacity = 0;
                } else {
                    startingOpacity = 0;
                    finalOpacity = 1;
                }
                backgroundAnimationTimer(backgroundImages);
            }
        });
        timeline.play();
    }

    public void changeDimensions() {

        //  Ridimensiona il titolo
        gameTitle.setLayoutX(window.getWidth()/2 - gameTitle.getWrappingWidth()/2);
        gameTitleBackground.setLayoutX(window.getWidth()/2 - gameTitleBackground.getWidth()/2);

        //  Ridimensiona il messaggio del tutorial
        promptPane.setLayoutX(window.getWidth()/2 -180);
        promptPane.setLayoutY(window.getHeight()/2 - 68);

        //  Ridimensiona i Buttoni rispetto alla finestra principale
        //  Larghezza

        double halfButtonWidth = 60;
        newGameButton.setLayoutX(window.getWidth()/2 - halfButtonWidth);
        tutorialButton.setLayoutX(window.getWidth()/2 - halfButtonWidth);
        loadGameButton.setLayoutX(window.getWidth()/2 - halfButtonWidth);
        leaderboardButton.setLayoutX(window.getWidth()/2 - halfButtonWidth);
        settingsButton.setLayoutX(window.getWidth()/2 - halfButtonWidth);
        exitButton.setLayoutX(window.getWidth()/2 - halfButtonWidth);

        // Altezza
        gameTitle.setLayoutY(window.getHeight()/3);
        gameTitleBackground.setLayoutY(window.getHeight()/3 - 71);
        languageMenu.setLayoutY(window.getHeight()/3 - 71);

        double buttonVerticalSpacing = window.getHeight()/16 + 6;

        newGameButton.setLayoutY(window.getHeight()/2.5);
        tutorialButton.setLayoutY(window.getHeight()/2.5 + buttonVerticalSpacing);
        loadGameButton.setLayoutY(window.getHeight()/2.5 + buttonVerticalSpacing*2);
        leaderboardButton.setLayoutY(window.getHeight()/2.5 + buttonVerticalSpacing*3);
        settingsButton.setLayoutY(window.getHeight()/2.5 + buttonVerticalSpacing*4);
        exitButton.setLayoutY(window.getHeight()/2.5 + buttonVerticalSpacing*5);

    }

}
