package jmb.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;

import static jmb.ConstantsShared.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;
import static jmb.view.ConstantsView.*;

public class MainMenu {


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
    private Rectangle tutorialPromptBackground;
    @FXML
    private Text tutorialPromptText;
    @FXML
    private Button tutorialPromptYes;
    @FXML
    private Button tutorialPromptNo;
    @FXML
    private ImageView mainLanguageImageView;
    private Path tutorialPath;
    private static Image englishFlag;
    private static Image italianFlag;
    private static final String MAIN_MENU_KEY = "newGame";
    private static final String LOAD_KEY = "loadGame";
    private static final String LEADERBOARD_KEY = "leaderboards";
    private static final String SETTINGS_KEY = "settings";
    private static final String EXIT_KEY = "exitGame";
    private static final String TUTORIAL_NEVER_PLAYED_KEY = "tutorialNeverPlayed";
    private static final String YES_KEY = "yes";
    private static final String NO_KEY = "no";

    //image per le lingue
    static {
        try{
            englishFlag = new Image(MainMenu.class.getResource("eng.jpg").toURI().toString());
            italianFlag = new Image(MainMenu.class.getResource("ita.jpg").toURI().toString());
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeButtonAction() {
        App.getStage().close();
    }

    @FXML
    void newGameAction() {
        if(!Files.exists(tutorialPath)) {
            tutorialPromptText.setVisible(true);
            tutorialPromptBackground.setVisible(true);
            tutorialPromptNo.setVisible(true);
            tutorialPromptYes.setVisible(true);
            loadStrings();
        }else {
            App.changeRoot(LOG_IN);
            View.sceneLogIn.changeDimensions();
        }
    }

    @FXML
    void openLogin() throws IOException {
        Files.createFile(tutorialPath);
        App.changeRoot(LOG_IN);
        View.sceneMusic.gameMusic.pause();
        View.sceneLogIn.changeDimensions();
    }

    @FXML
    void openTutorialFromPrompt() throws IOException {
        Files.createFile(tutorialPath);
        openTutorial();
    }

    @FXML
    void openLeaderBoard()  throws IOException {
        App.changeRoot(LEADERBOARDS);
        View.sceneLeaderboard.table.refresh();
        View.sceneLeaderboard.changeDimensions();
    }

    @FXML
    void openMenuImpostazioni()  throws IOException {
        App.changeRoot(SETTINGS);
        View.sceneSettings.applyButton.setDisable(true);
        View.sceneSettings.changeDimensions();
    }

    @FXML
    void openLoadGame() throws IOException {
        App.changeRoot(LOAD_GAME);
    }

    @FXML
    void openTutorial() throws IOException{
        App.changeRoot(TUTORIAL);
        logic.initializeTutorialLogic();
        view.playMusic(TUTORIAL_MUSIC);
    }

    private void loadStrings() {
        newGameButton.setText(logic.getString(MAIN_MENU_KEY));
        loadGameButton.setText(logic.getString(LOAD_KEY));
        settingsButton.setText(logic.getString(SETTINGS_KEY));
        leaderboardButton.setText(logic.getString(LEADERBOARD_KEY));
        exitButton.setText(logic.getString(EXIT_KEY));
        tutorialPromptText.setText(logic.getString(TUTORIAL_NEVER_PLAYED_KEY));
        tutorialPromptYes.setText(logic.getString(YES_KEY));
        tutorialPromptNo.setText(logic.getString(NO_KEY));
    }

    @FXML
    void englishLanguage(ActionEvent event) {
        mainLanguageImageView.setImage(englishFlag);
        logic.setLanguage("EN");
        logic.applySettingsChanges();
        this.loadStrings();
    }

    @FXML
    void italianLanguage(ActionEvent event) {
        mainLanguageImageView.setImage(italianFlag);
        logic.setLanguage("IT");
        logic.applySettingsChanges();
        this.loadStrings();
    }

    private void setCorrectFlag() {
        switch (logic.getLanguage()) {
            case "IT":
                mainLanguageImageView.setImage(italianFlag);
                break;
            case "EN":
                mainLanguageImageView.setImage(englishFlag);
                break;
        }

    }

    public void initialize() {
        try {
            String settingsDir = logic.getAppDirectory() + "/settings";
            tutorialPath = Path.of (settingsDir.concat("/played.ini"));

            newGameButton.setText(logic.getString(MAIN_MENU_KEY));
            loadGameButton.setText(logic.getString(LOAD_KEY));
            settingsButton.setText(logic.getString(SETTINGS_KEY));
            leaderboardButton.setText(logic.getString(LEADERBOARD_KEY));
            exitButton.setText(logic.getString(EXIT_KEY));

            Image im1 = new Image(this.getClass().getResource("background1.jpg").toURI().toString());
            Image im2 = new Image(this.getClass().getResource("background2.jpg").toURI().toString());
            Image im3 = new Image(this.getClass().getResource("background3.jpg").toURI().toString());
            Image im4 = new Image(this.getClass().getResource("background4.jpg").toURI().toString());
            setCorrectFlag();
            //  BackGround.setImage(new Image("/jmb/view/background3.jpg"));
            background1.setPreserveRatio(false);
            background2.setPreserveRatio(false);

            // musica
            if (!logic.getMuteMusic()) {
                view.playMusic(MENU_MUSIC);
            }

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(background2.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(5),
                            new KeyValue(background2.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(6), e -> {
                        background1.setImage(im3);
                    },
                            new KeyValue(background2.opacityProperty(), 1)
                    ),
                    new KeyFrame(Duration.seconds(11),
                            new KeyValue(background2.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(12), e -> {
                        background2.setImage(im4);
                    },
                            new KeyValue(background2.opacityProperty(), 0)
                    ),
                    new KeyFrame(Duration.seconds(17),
                            new KeyValue(background2.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(18), e -> {
                        background1.setImage(im1);
                    },
                            new KeyValue(background2.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(23),
                            new KeyValue(background2.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(24), e -> {
                        background2.setImage(im2);
                    },
                            new KeyValue(background2.opacityProperty(), 0))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();


            //  LISTENER PER RIDIMENSIONAMENTO ORIZZONTALE DELLA FINESTRA
            window.widthProperty().addListener((obs, oldVal, newVal) -> changeDimensions());


            //LISTENER PER RIDIMENSIONAMENTO VERTICALE DELLA FINESTRA
            window.heightProperty().addListener((obs, oldVal, newVal) -> changeDimensions());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void changeDimensions() {

            //  Ridimensiona il titolo

                gameTitle.setLayoutX(window.getWidth()/2 - gameTitle.getWrappingWidth()/2);
                gameTitleBackground.setLayoutX(window.getWidth()/2 - gameTitleBackground.getWidth()/2);

            //  Ridimensiona il messaggio del tutorial

                tutorialPromptBackground.setLayoutX(window.getWidth()/2 + 153);
                tutorialPromptBackground.setLayoutY(window.getHeight()/2.5);
                tutorialPromptText.setLayoutX(window.getWidth()/2 +151);
                tutorialPromptText.setLayoutY(window.getHeight()/2.5 +15);
                tutorialPromptYes.setLayoutX(window.getWidth()/2 + 178);
                tutorialPromptYes.setLayoutY(window.getHeight()/2.5 + 60);
                tutorialPromptNo.setLayoutX(window.getWidth()/2 + 250);
                tutorialPromptNo.setLayoutY(window.getHeight()/2.5 + 60);

            //  Ridimensiona i Buttoni rispetto alla finestra principale
            //  Larghezza

            newGameButton.setLayoutX(window.getWidth()/2 - newGameButton.getWidth()/2);
            tutorialButton.setLayoutX(window.getWidth()/2 - newGameButton.getWidth()/2);
            loadGameButton.setLayoutX(window.getWidth()/2 - newGameButton.getWidth()/2);
            leaderboardButton.setLayoutX(window.getWidth()/2 - newGameButton.getWidth()/2);
            settingsButton.setLayoutX(window.getWidth()/2 - newGameButton.getWidth()/2);
            exitButton.setLayoutX(window.getWidth()/2 - newGameButton.getWidth()/2);

            // Altezza
            gameTitle.setLayoutY(window.getHeight()/3);
            gameTitleBackground.setLayoutY(window.getHeight()/3 - 71);

            newGameButton.setLayoutY(window.getHeight()/2.5);
            tutorialButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16);
            loadGameButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16);
            leaderboardButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16);
            settingsButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16);
            exitButton.setLayoutY(window.getHeight()/2.5 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16 + window.getHeight()/16);


            background1.setFitWidth(window.getWidth());
            background1.setFitHeight(window.getHeight());
            background2.setFitWidth(window.getWidth());
            background2.setFitHeight(window.getHeight());


    }

}
