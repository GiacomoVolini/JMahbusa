package jmb.view;

import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;
import static jmb.view.ConstantsView.*;

/*DOC
    In questa classe c'era memory leak, oggetti MainMenu rimanevano in memoria anche dopo essere passati a
    un'altra finestra. Risolto smettendo di usare MenuItem e passando a ComboBox
    Alternativamente risolto cambiando il sistema per l'animazione dello sfondo
 */

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
        if(logic.shouldPlayTutorial()) {
            promptPane.setVisible(true);
            loadStrings();
        }else {
            animationToContinue = false;
            openLogin();
        }
    }

    @FXML
    void openLogin() {
        logic.initializeLeaderboardLogic();
        logic.initializeBoardLogic();
        logic.flagTutorialPlayed();
        animationToContinue = false;
        App.changeRoot(LOG_IN);
    }

    @FXML
    void openTutorialFromPrompt() throws IOException {
        logic.flagTutorialPlayed();
        animationToContinue = false;
        openTutorial();
    }

    @FXML
    void openLeaderBoard() {
        animationToContinue = false;
        logic.initializeLeaderboardLogic();
        App.changeRoot(LEADERBOARDS);
    }

    @FXML
    void openMenuImpostazioni(){
        animationToContinue = false;
        App.changeRoot(SETTINGS);
    }

    @FXML
    void openLoadGame(){
        animationToContinue = false;
        App.changeRoot(LOAD_GAME);
    }

    @FXML
    void openTutorial(){
        animationToContinue = false;
        logic.initializeTutorialLogic();
        App.changeRoot(TUTORIAL);
        view.playMusic(TUTORIAL_MUSIC);
    }

    private void loadStrings() {
        newGameButton.setText(logic.getString("newGame"));
        loadGameButton.setText(logic.getString("loadGame"));
        settingsButton.setText(logic.getString("settings"));
        leaderboardButton.setText(logic.getString("leaderboards"));
        exitButton.setText(logic.getString("exitGame"));
        promptLabel.setText(logic.getString("tutorialNeverPlayed"));
        promptYesButton.setText(logic.getString("yes"));
        promptNoButton.setText(logic.getString("no"));
        promptPane.setText(logic.getString("newGame"));
        tutorialButton.setText(logic.getString("Tutorial"));
    }


    private void loadLanguages() {
        String[] supportedLanguages = logic.getSupportedLanguages();
        for (String language: supportedLanguages) {
            languageMenu.getItems().add(new Image(Objects.requireNonNull(
                    this.getClass().getResource("flags/flag_" + language + ".png")).toString()));
        }
        languageMenu.setButtonCell(new ImageListCell());
        languageMenu.setCellFactory(listView -> new ImageListCell());
        languageMenu.getSelectionModel().select(getSelectionIndex(supportedLanguages));
    }

    private int getSelectionIndex(String[] languages) {
        String currentLanguage = logic.getSetting("General", "language", String.class);
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
        logic.setSetting("General", "language",logic.getSupportedLanguages()[((ComboBox)event.getSource()).getSelectionModel().getSelectedIndex()]);
        logic.applySettingsChanges();
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
            if (!logic.getSetting("Audio", "muteMusic", boolean.class)) {
                view.playMusic(MENU_MUSIC);
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

        newGameButton.setLayoutX(window.getWidth()/2 - 50);
        tutorialButton.setLayoutX(window.getWidth()/2 - 50);
        loadGameButton.setLayoutX(window.getWidth()/2 - 50);
        leaderboardButton.setLayoutX(window.getWidth()/2 - 50);
        settingsButton.setLayoutX(window.getWidth()/2 - 50);
        exitButton.setLayoutX(window.getWidth()/2 - 50);

        // Altezza
        gameTitle.setLayoutY(window.getHeight()/3);
        gameTitleBackground.setLayoutY(window.getHeight()/3 - 71);
        languageMenu.setLayoutY(window.getHeight()/3 - 71);

        newGameButton.setLayoutY(window.getHeight()/2.5);
        tutorialButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16);
        loadGameButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16);
        leaderboardButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16);
        settingsButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16);
        exitButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16);

    }

}
