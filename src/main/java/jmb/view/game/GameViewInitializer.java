package jmb.view.game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static jmb.view.View.logic;

public class GameViewInitializer {

    public static void setStrings
            (Text backText, Text finishTurnText, Text menuText, Text upText, Text downText, Text rightText,
             Text leftText, Text selectText, Button backBTN, Button menuBTN, Button finishBTN, Text readyText,
             TitledPane startDialogue, Button startBTN, Label plWHTText, Label plBLKText) {
        backText.setText(logic.getSetting("Controls", "revertMove", String.class));
        finishTurnText.setText(logic.getSetting("Controls", "finishTurn", String.class));
        menuText.setText(logic.getSetting("Controls", "openMenu", String.class));
        upText.setText(logic.getString("Up")+"\n" + logic.getSetting("Controls", "moveUp", String.class));
        downText.setText(logic.getString("Down")+"\n" + logic.getSetting("Controls", "moveDown", String.class));
        rightText.setText(logic.getString("Right")+"\n" + logic.getSetting("Controls", "moveRight", String.class));
        leftText.setText(logic.getString("Left")+"\n" +logic.getSetting("Controls", "moveLeft", String.class));
        selectText.setText(logic.getString("Select")+"\n" + logic.getSetting("Controls", "select", String.class));
        backBTN.setText(logic.getString("revertMove").toUpperCase());
        menuBTN.setText(logic.getString("menu").toUpperCase());
        finishBTN.setText(logic.getString("finishTurn").toUpperCase());
        startDialogue.setText(logic.getString("startTitle"));
        readyText.setText(logic.getString("ready"));
        startBTN.setText(logic.getString("yes").toUpperCase());
        plWHTText.setText(logic.getWhitePlayer().stripTrailing());
        plBLKText.setText(logic.getBlackPlayer().stripTrailing());
    }

    public static void setColors (Circle plWHTPawn, Circle plBLKPawn, Label tournamentWhitePoints,
                                  Label tournamentBlackPoints) {
        plWHTPawn.setFill(Color.web(logic.getSetting("Customization", "whitePawnFill", String.class)));
        plWHTPawn.setStroke(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
        plBLKPawn.setFill(Color.web(logic.getSetting("Customization", "blackPawnFill", String.class)));
        plBLKPawn.setStroke(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
        tournamentWhitePoints.setTextFill(Color.web(logic.getSetting("Customization", "whitePawnStroke", String.class)));
        tournamentBlackPoints.setTextFill(Color.web(logic.getSetting("Customization", "blackPawnStroke", String.class)));
    }

    public static void setTournamentComponents (Label tournamentWhitePoints, Label tournamentBlackPoints,
                                                Label tournamentPointsToWin) {
        tournamentWhitePoints.setAlignment(Pos.CENTER);
        tournamentBlackPoints.setAlignment(Pos.CENTER);
        tournamentPointsToWin.setAlignment(Pos.CENTER);
        tournamentPointsToWin.setFont(Font.font("calibri", FontWeight.BOLD, 16));
        tournamentWhitePoints.setFont(Font.font("calibri", FontWeight.BOLD, 16));
        tournamentBlackPoints.setFont(Font.font("calibri", FontWeight.BOLD, 16));
    }

    public static void setViewOrders (Rectangle timerIn, Rectangle timerOut, TitledPane startDialogue,
                                      Label tournamentPointsToWin) {
        timerIn.setViewOrder(-2);
        timerOut.setViewOrder(-1.999);
        startDialogue.setViewOrder(-4);
        tournamentPointsToWin.setViewOrder(-10);
    }

    public static void setTournamentComponentsVisibility
            (boolean set, Label tournamentWhitePoints, Label tournamentBlackPoints, Label tournamentPointsToWin,
             ImageView tournamentCup) {
        tournamentWhitePoints.setVisible(set);
        tournamentBlackPoints.setVisible(set);
        tournamentPointsToWin.setVisible(set);
        tournamentCup.setVisible(set);
    }

}
