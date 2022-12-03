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

import static jmb.view.View.getLogic;

public class GameViewInitializer {

    public static void setStrings
            (Text backText, Text finishTurnText, Text menuText, Text upText, Text downText, Text rightText,
             Text leftText, Text selectText, Button backBTN, Button menuBTN, Button finishBTN, Text readyText,
             TitledPane startDialogue, Button startBTN, Label plWHTText, Label plBLKText) {
        backText.setText(getLogic().getString("revertMove")+":"+ "\n" + getLogic().getSetting("Controls", "revertMove", String.class));
        finishTurnText.setText(getLogic().getString("finishTurn")+":" +"\n" + getLogic().getSetting("Controls", "finishTurn", String.class));
        menuText.setText(getLogic().getString("menu")+":" +"\n" + getLogic().getSetting("Controls", "openMenu", String.class));
        upText.setText(getLogic().getString("Up")+"\n" + getLogic().getSetting("Controls", "moveUp", String.class));
        downText.setText(getLogic().getString("Down")+"\n" + getLogic().getSetting("Controls", "moveDown", String.class));
        //rightText.setText(getLogic().getString("Right")+"\n" + getLogic().getSetting("Controls", "moveRight", String.class));
        rightText.setText(getLogic().getString("keyboardInfo"));
        leftText.setText(getLogic().getString("Left")+"\n" +getLogic().getSetting("Controls", "moveLeft", String.class));
        selectText.setText(getLogic().getString("Select")+"\n" + getLogic().getSetting("Controls", "select", String.class));
        backBTN.setText(getLogic().getString("revertMove").toUpperCase());
        menuBTN.setText(getLogic().getString("menu").toUpperCase());
        finishBTN.setText(getLogic().getString("finishTurn").toUpperCase());
        startDialogue.setText(getLogic().getString("startTitle"));
        readyText.setText(getLogic().getString("ready"));
        startBTN.setText(getLogic().getString("yes").toUpperCase());
        plWHTText.setText(getLogic().getWhitePlayer().stripTrailing());
        plBLKText.setText(getLogic().getBlackPlayer().stripTrailing());
    }

    public static void setColors (Circle plWHTPawn, Circle plBLKPawn, Label tournamentWhitePoints,
                                  Label tournamentBlackPoints) {
        plWHTPawn.setFill(Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class)));
        plWHTPawn.setStroke(Color.web(getLogic().getSetting("Customization", "whitePawnStroke", String.class)));
        plBLKPawn.setFill(Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class)));
        plBLKPawn.setStroke(Color.web(getLogic().getSetting("Customization", "blackPawnStroke", String.class)));
        tournamentWhitePoints.setTextFill(Color.web(getLogic().getSetting("Customization", "whitePawnStroke", String.class)));
        tournamentBlackPoints.setTextFill(Color.web(getLogic().getSetting("Customization", "blackPawnStroke", String.class)));
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
