package jmb.view;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.getLogic;

public class GameViewRedraw extends DynamicGameBoardRedraw {


    private static double getMaxBtnWidth(AnchorPane window, Rectangle outerRect) {
        double maxBtnWidth = window.getWidth() - (outerRect.getLayoutX() + outerRect.getWidth() + maxDTWidth + (BUTTON_ANCHOR * 2));
        return min(MAX_BTN_WIDTH, maxBtnWidth);

    }

    public static void resizeTimer(GameView board) {
        //  Ridimensiona le barre per il timer
        board.timerOut.setWidth(board.separator.getWidth() / 2);
        board.timerOut.setLayoutX(board.separator.getLayoutX() + (board.separator.getWidth() / 2) - (board.timerOut.getWidth() / 2));
        board.timerOut.setHeight(board.separator.getHeight() - 4);
        board.timerOut.setLayoutY(board.separator.getLayoutY() + (board.separator.getHeight() / 2) - (board.timerOut.getHeight() / 2));

        board.timerIn.setWidth(board.timerOut.getWidth() - 4);
        board.timerIn.setLayoutX(board.timerOut.getLayoutX() + 2);
        board.timerIn.setHeight(board.timerOut.getHeight() - 4);
        board.timerIn.setLayoutY(board.timerOut.getLayoutY() + 2);
    }

    public static void resizeButtons(GameView board) {

        //  Larghezza
        board.backBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.finishBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.menuBTN.setMaxWidth(getMaxBtnWidth(board.window, board.outerRect));
        board.backBTN.setPrefWidth(board.window.getWidth() * 0.15);
        board.finishBTN.setPrefWidth(board.backBTN.getPrefWidth());
        board.menuBTN.setPrefWidth(board.backBTN.getPrefWidth());
        // Altezza
        board.backBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.finishBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.menuBTN.setMaxHeight(MAX_BTN_HEIGHT);
        board.backBTN.setPrefHeight(board.window.getHeight() * 0.2);
        board.finishBTN.setPrefHeight(board.backBTN.getPrefHeight());
        board.menuBTN.setPrefHeight(board.backBTN.getPrefHeight());
        board.backBTN.setLayoutY(board.window.getHeight() * .25 - board.backBTN.getPrefHeight() / 2);
        board.finishBTN.setLayoutY((board.window.getHeight() - board.finishBTN.getPrefHeight()) / 2);
        board.menuBTN.setLayoutY(board.window.getHeight() * .75 - board.menuBTN.getPrefHeight() / 2);
    }

    private static void resizeVictoryRect(GameView board) {
        board.victoryPanel.setWidth(board.window.getWidth() / 2);
        board.victoryPanel.setHeight(board.window.getHeight() / 2.5);
        board.victoryPanel.setLayoutX((board.window.getWidth() - board.victoryPanel.getWidth()) / 2);
        board.victoryPanel.setLayoutY((board.window.getHeight() - board.victoryPanel.getHeight()) / 2);
    }

    private static void resizeVictoryPawn(GameView board) {
        board.victoryPawn.setRadius(board.victoryPanel.getHeight() / 10);
        board.victoryPawn.setLayoutX(board.victoryPanel.getLayoutX() + board.victoryPanel.getWidth() * 0.05 + board.victoryPawn.getRadius());
        board.victoryPawn.setLayoutY(board.victoryPanel.getLayoutY() + board.victoryPanel.getHeight() / 2);
    }

    private static void resizeVictoryExit(GameView board) {
        board.victoryExit.setPrefWidth(board.victoryPanel.getWidth() * 0.5);
        board.victoryExit.setPrefHeight(board.victoryPanel.getHeight() * 0.15);
        board.victoryExit.setLayoutY(board.victoryPanel.getLayoutY() + (0.66 * board.victoryPanel.getHeight()));
        board.victoryExit.setLayoutX(board.victoryPanel.getLayoutX() + (board.victoryPanel.getWidth() - (board.victoryPanel.getWidth() * 0.3)) / 2);
    }

    private static void resizeVictoryCrown(GameView board) {
        board.victoryCrown.setFitWidth(board.victoryPawn.getRadius() * 2);
        board.victoryCrown.setLayoutY(board.victoryPawn.getLayoutY() - board.victoryPawn.getRadius() * 2.2);
        board.victoryCrown.setLayoutX(board.victoryPawn.getLayoutX() - board.victoryPawn.getRadius());
    }


    private static void resizeVictoryLabel(GameView board) {
        board.victoryLabel.setLayoutY(board.victoryPanel.getLayoutY() + board.victoryPanel.getHeight() * 0.15);
        board.victoryLabel.setLayoutX(board.victoryPanel.getLayoutX() + board.victoryPanel.getWidth() * 0.25);
        resizeVictoryFont(board.victoryPanel, board.victoryLabel);
    }

    //  Il metodo resizeVictoryFont si occupa di stimare una grandezza del font di victoryLabel tale da non fuoriuscire
    //      da victoryPanel e mantenere dei margini simili
    //  Da un controllo empirico con SceneBuilder, preso un rettangolo di larghezza 400, una label con Font size 20
    //      ed il testo "Hai ottenuto una vittoria doppia!" rimane entro margini accettabili
    //  Il metodo quindi calcola la proporzione tra la larghezza di victoryPanel e 400, e trova il nuovo valore di grandezza
    //      del Font da assegnare a victoryLabel
    private static void resizeVictoryFont(Rectangle victoryPanel, Label victoryLabel) {
        double widthFactor = Math.min(victoryPanel.getWidth() / 400, 1.50);
        victoryLabel.setFont(Font.font("calibri", FontWeight.BOLD, 20 * widthFactor));
    }

    private static void resizeTournamentRibbon(GameView board) {
        board.tournamentRibbon.setLayoutX(board.victoryPawn.getLayoutX() + board.victoryPawn.getRadius() * 0.10);
        board.tournamentRibbon.setLayoutY(board.victoryPawn.getLayoutY() - board.victoryPawn.getRadius() * 0.20);
        board.tournamentRibbon.setFitWidth(board.victoryPawn.getRadius());
    }


    //  Metodo per ridimensionare gli elementi del pannello vittoria
    protected static void resizeVictoryPanel(GameView board) {
        resizeVictoryRect(board);
        resizeVictoryPawn(board);
        resizeVictoryLabel(board);
        resizeVictoryExit(board);
        resizeVictoryCrown(board);
        if (board.tournamentRibbon != null)
            resizeTournamentRibbon(board);

    }

    private static void resizeStartDialogue(GameView board) {
        board.startDialogue.setLayoutX(board.window.getWidth() / 2 - board.startDialogue.getPrefWidth() / 2);
        board.startDialogue.setLayoutY(board.window.getHeight() / 2 - board.startDialogue.getPrefHeight() / 2);
    }


    private static void resizePlsPawns(GameView board) {

        board.plWHTPawn.setRadius(board.window.getHeight() * 0.038);
        board.plBLKPawn.setRadius(board.window.getHeight() * 0.038);
        AnchorPane.setLeftAnchor(board.plWHTPawn, board.window.getWidth() * 0.0125);
        AnchorPane.setRightAnchor(board.plBLKPawn, board.window.getWidth() * 0.0125);
        AnchorPane.setTopAnchor(board.plWHTPawn, board.window.getHeight() * 0.0275);
        AnchorPane.setTopAnchor(board.plBLKPawn, board.window.getHeight() * 0.0275);

    }

    private static void resizePlsRects(GameView board) {

        resizePlsOutRects(board);
        resizePlsInRects(board);
        resizePlsNames(board);
        resizePlsFont(board);

    }

    private static void resizePlsOutRects(GameView board) {
        AnchorPane.setTopAnchor(board.plWHTOutRect, board.window.getHeight() * 0.0275);
        AnchorPane.setTopAnchor(board.plBLKOutRect, board.window.getHeight() * 0.0275);
        AnchorPane.setLeftAnchor(board.plWHTOutRect, board.window.getWidth() * 0.025 + board.plWHTPawn.getRadius() * 2);
        AnchorPane.setRightAnchor(board.plBLKOutRect, board.window.getWidth() * 0.025 + board.plBLKPawn.getRadius() * 2);
        board.plWHTOutRect.setHeight(board.plWHTPawn.getRadius() * 2);
        board.plBLKOutRect.setHeight(board.plBLKPawn.getRadius() * 2);
        board.plWHTOutRect.setWidth(board.window.getWidth() * 0.18);
        board.plBLKOutRect.setWidth(board.window.getWidth() * 0.18);
    }

    private static void resizePlsInRects(GameView board) {
        board.plWHTInRect.setWidth(board.window.getWidth() * 0.15);
        board.plWHTInRect.setHeight(board.plWHTPawn.getRadius() * 1.5);
        board.plBLKInRect.setWidth(board.window.getWidth() * 0.15);
        board.plBLKInRect.setHeight(board.plBLKPawn.getRadius() * 1.5);
        AnchorPane.setTopAnchor(board.plBLKInRect,
                AnchorPane.getTopAnchor(board.plBLKOutRect) + board.plBLKOutRect.getHeight() / 2 - board.plBLKInRect.getHeight() / 2);
        AnchorPane.setTopAnchor(board.plWHTInRect,
                AnchorPane.getTopAnchor(board.plWHTOutRect) + board.plWHTOutRect.getHeight() / 2 - board.plWHTInRect.getHeight() / 2);
        AnchorPane.setLeftAnchor(board.plWHTInRect,
                AnchorPane.getLeftAnchor(board.plWHTOutRect) + board.plWHTOutRect.getWidth() / 2 - board.plWHTInRect.getWidth() / 2);
        AnchorPane.setRightAnchor(board.plBLKInRect,
                AnchorPane.getRightAnchor(board.plBLKOutRect) + board.plBLKOutRect.getWidth() / 2 - board.plBLKInRect.getWidth() / 2);
    }

    private static void resizePlsNames(GameView board) {
        AnchorPane.setTopAnchor(board.plWHTText, AnchorPane.getTopAnchor(board.plWHTInRect));
        AnchorPane.setTopAnchor(board.plBLKText, AnchorPane.getTopAnchor(board.plBLKInRect));
        AnchorPane.setLeftAnchor(board.plWHTText, AnchorPane.getLeftAnchor(board.plWHTInRect));
        AnchorPane.setRightAnchor(board.plBLKText, AnchorPane.getRightAnchor(board.plBLKInRect));
        board.plWHTText.setPrefWidth(board.plWHTInRect.getWidth());
        board.plBLKText.setPrefWidth(board.plBLKInRect.getWidth());
        board.plWHTText.setPrefHeight(board.plWHTInRect.getHeight());
        board.plBLKText.setPrefHeight(board.plBLKInRect.getHeight());
    }

    private static void resizePlsFont(GameView board) {
        double widthFactor = board.plWHTInRect.getWidth() / 110;
        double fontSize = max(13.5, min(25, (12 * widthFactor)));
        board.plWHTText.setFont(Font.font("calibri", FontWeight.NORMAL, fontSize));
        board.plBLKText.setFont(Font.font("calibri", FontWeight.NORMAL, fontSize));

    }

    protected static void resizeTournamentComponents(GameView board) {
        double yAnchor = board.window.getHeight() * 0.0275;
        AnchorPane.setTopAnchor(board.tournamentWhitePoints, yAnchor);
        AnchorPane.setTopAnchor(board.tournamentBlackPoints, yAnchor);
        AnchorPane.setLeftAnchor(board.tournamentWhitePoints, board.window.getWidth() * 0.0125);
        AnchorPane.setRightAnchor(board.tournamentBlackPoints, board.window.getWidth() * 0.0125);
        AnchorPane.setTopAnchor(board.tournamentCup,yAnchor*0.3);
        AnchorPane.setTopAnchor(board.tournamentPointsToWin, yAnchor*0.3);
        double pawnSize = board.plWHTPawn.getRadius()*2;
        double cupSize = board.plWHTPawn.getRadius()*3;
        board.tournamentCup.setFitWidth(cupSize);
        board.tournamentCup.setFitHeight(cupSize);
        board.tournamentPointsToWin.setPrefWidth(cupSize);
        board.tournamentPointsToWin.setPrefHeight(cupSize*0.7);
        board.tournamentWhitePoints.setPrefWidth(pawnSize);
        board.tournamentWhitePoints.setPrefHeight(pawnSize);
        board.tournamentBlackPoints.setPrefWidth(pawnSize);
        board.tournamentBlackPoints.setPrefHeight(pawnSize);
        double fontFactor = board.plWHTPawn.getRadius() / 25;
        board.tournamentWhitePoints.setFont(Font.font("calibri", FontWeight.BOLD, 20 * fontFactor));
        board.tournamentBlackPoints.setFont(Font.font("calibri", FontWeight.BOLD, 20 * fontFactor));
        board.tournamentPointsToWin.setFont(Font.font("calibri", FontWeight.BOLD, 20 * fontFactor));
        double cupX = (board.window.getWidth() - board.tournamentCup.getFitWidth())/2;
        board.tournamentCup.setLayoutX(cupX);
        board.tournamentPointsToWin.setLayoutX(cupX);

    }

    protected static void changeKeyboardInfoPanel(GameView board, boolean panelToClose){
        if (panelToClose) {
            AnchorPane.setLeftAnchor(board.rightText, 10.0);
            AnchorPane.setTopAnchor(board.rightText, 13.0);
            board.keyboardInfo.setPrefWidth(107);
            board.rightText.setText(getLogic().getString("keyboardInfo").concat(" ▶"));
        } else {
            AnchorPane.clearConstraints(board.rightText);
            AnchorPane.setLeftAnchor(board.rightText, 28.0);
            board.rightText.setLayoutY(16.1865234375);
            board.keyboardInfo.setPrefWidth(624);
            board.rightText.setText(getLogic().getString("Right")+"\n" +
                    getLogic().getSetting("Controls", "moveRight", String.class));
        }
        board.leftText.setVisible(!panelToClose);
        board.upText.setVisible(!panelToClose);
        board.downText.setVisible(!panelToClose);
        board.backText.setVisible(!panelToClose);
        board.finishTurnText.setVisible(!panelToClose);
        board.menuText.setVisible(!panelToClose);
        board.selectText.setVisible(!panelToClose);
    }

    protected static void resizeKeyboardInfoPanel(GameView board) {
        AnchorPane.setLeftAnchor(board.keyboardInfo, (board.window.getWidth()/2) - 312);
        board.keyboardInfo.setLayoutY(board.window.getHeight() - 50);
    }

    protected static void resizeAll(GameView board) {
        DynamicGameBoardRedraw.resizeAll(board);
        resizePlsPawns(board);
        resizePlsRects(board);
        resizeTimer(board);
        if (!getLogic().getGameStart())
            resizeStartDialogue(board);
        resizeButtons(board);
        if (getLogic().isTournamentOngoing())
            resizeTournamentComponents(board);
        if (getLogic().getGameEndState()) {
            resizeVictoryPanel(board);
        }
        resizeKeyboardInfoPanel(board);
    }

}