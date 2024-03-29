package jmb.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jmb.ConstantsShared;

import java.net.URISyntaxException;
import java.util.Objects;

import static jmb.ConstantsShared.TournamentStatus;
import static jmb.view.ConstantsView.NORMAL_PAWN_STROKE_WIDTH;
import static jmb.view.View.getLogic;

public class VictoryComponentFactory {

    public static Rectangle createVictoryPanel(AnchorPane window) {
        Rectangle victoryPanel = new Rectangle();
        window.getChildren().add(victoryPanel);
        victoryPanel.setFill(Color.web("#e1a751"));
        victoryPanel.setStroke(Color.web("#43300a"));
        victoryPanel.setStrokeWidth(3);
        victoryPanel.setArcHeight(0);
        victoryPanel.setArcWidth(0);
        victoryPanel.setViewOrder(-10);

        return victoryPanel;
    }

    public static Circle createVictoryPawn(boolean whiteWon, AnchorPane window) {
        Circle pawn = new Circle();
        window.getChildren().add(pawn);
        pawn.setViewOrder(-15);
        if (whiteWon) {
            pawn.setFill(Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class)));
            pawn.setStroke(Color.web(getLogic().getSetting("Customization", "whitePawnStroke", String.class)));
        } else {
            pawn.setFill(Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class)));
            pawn.setStroke(Color.web(getLogic().getSetting("Customization", "blackPawnStroke", String.class)));
        }
        pawn.setStrokeWidth(NORMAL_PAWN_STROKE_WIDTH);

        return pawn;
    }

    public static Button createVictoryButton(TournamentStatus status, AnchorPane window) {
        String label;
        boolean tournamentContinues = status.equals(TournamentStatus.TOURNAMENT_CONTINUES);
        if (tournamentContinues)
            label = getLogic().getString("continueTournament");
        else label = getLogic().getString("backToMenu");
        Button victoryExit = new Button(label);
        String style = Objects.requireNonNull(
                VictoryComponentFactory.class.getResource("button style.css")).toExternalForm();
        victoryExit.getStylesheets().add(style);
        window.getChildren().add(victoryExit);
        victoryExit.setViewOrder(-16);
        return victoryExit;
    }


    public static ImageView createCrownImage(boolean doubleWin, AnchorPane window) {
        ImageView crown;
        if (doubleWin) {
            crown = new ImageView(new Image(Objects.requireNonNull(
                    VictoryComponentFactory.class.getResource("victory/crownDouble.png")).toString()));
        } else {
            crown = new ImageView(new Image(Objects.requireNonNull(
                    VictoryComponentFactory.class.getResource("victory/crown.png")).toString()));
        }
        crown.setPreserveRatio(true);
        crown.setViewOrder(-14);
        window.getChildren().add(crown);
        return crown;
    }

    public static Label createVictoryLabel(String winner, boolean doubleWin, TournamentStatus status, AnchorPane window) {
        Label victoryLabel = new Label();
        window.getChildren().add(victoryLabel);
        victoryLabel.setWrapText(true);
        String victoryString = getLogic().getString("congratulations") + " ";
        victoryString = victoryString.concat(winner.stripTrailing());
        if (status.equals(ConstantsShared.TournamentStatus.TOURNAMENT_WON))
            victoryString = victoryString.concat(getLogic().getString("tournamentWon"));
        else if (doubleWin)
            victoryString = victoryString.concat(getLogic().getString("doubleVictory"));
        else
            victoryString = victoryString.concat(getLogic().getString("singleVictory"));
        victoryLabel.setText(victoryString);
        victoryLabel.setViewOrder(-15);
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 16));
        victoryLabel.setTextFill(Color.web("#43300a"));

        return victoryLabel;
    }

    public static ImageView createTournamentRibbon(AnchorPane window) {
        ImageView tournamentRibbon = new ImageView(
                new Image(Objects.requireNonNull(VictoryComponentFactory.class.getResource("victory/tournamentRibbon.png")).toString()));
        tournamentRibbon.setPreserveRatio(true);
        tournamentRibbon.setViewOrder(-16);
        window.getChildren().add(tournamentRibbon);
        return tournamentRibbon;
    }

}
