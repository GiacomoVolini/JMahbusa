package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URISyntaxException;

import static java.lang.Math.max;
import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public class DynamicGameBoard extends GameBoard{

    protected Rectangle diceTray;

    protected ImageView[] diceArray = new ImageView[4];

    protected boolean diceTrayOpen = false;
    protected Timeline diceRollAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> DiceView.rndRolls(diceArray))
    ); //Necessario impostare il numero di cicli quando si utilizza


    public DynamicGameBoard() {
        try {
            Image[] imgArray = new Image[] {
                    new Image(this.getClass().getResource("diceImg/Dado1.png").toURI().toString()),
                    new Image(this.getClass().getResource("diceImg/Dado1INV.png").toURI().toString()),
            };
            diceTray = new Rectangle();
            diceTray.setFill(Color.web(logic.getBoardInnerColor()));
            diceTray.setStroke(Color.BLACK);
            for (int i = 0; i < 4; i++) {
                diceArray[i] = new ImageView(imgArray[i/2]);
                diceArray[i].setViewOrder(-3 + (i/2));
                diceArray[i].setVisible(false);
            }
            for (int i = 0; i < 15; i++) {
                pawnArrayWHT[i].setOnMouseDragged(this::drag);
                pawnArrayWHT[i].setOnMousePressed(this::savePosition);
                pawnArrayWHT[i].setOnMouseReleased(this::releasePawn);
                pawnArrayBLK[i].setOnMouseDragged(this::drag);
                pawnArrayBLK[i].setOnMousePressed(this::savePosition);
                pawnArrayBLK[i].setOnMouseReleased(this::releasePawn);
            }
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }
    }

    protected void addChildrenToAnchor() {
        super.addChildrenToAnchor();
        boardAnchor.getChildren().add(diceTray);
        for (ImageView dice : diceArray) {
            boardAnchor.getChildren().add(dice);
            dice.setVisible(false);
        }
    }

    private void savePosition (MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = searchPawnPlace(node);
        logic.createMoveBuffer(col, whoCalled);
        logic.isPawnMovable(col, logic.searchTopOccupiedRow(whoCalled, col), true, whoCalled);
    }

    //  Metodo per il trascinamento della pedina
    private void drag(MouseEvent event) {
        PawnView n = (PawnView) event.getSource();
        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }
    protected void releasePawn(MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = this.searchPawnPlace(node);
        if (col != UNDEFINED) {
            logic.placePawnOnPoint(col, whoCalled);
        }
        view.restoreBoardColors(whoCalled);
        GameViewRedraw.redrawPawns(this);

    }
    protected int searchPawnPlace(PawnView node) {
        //Il metodo cerca a quale zona del tabellone appartiene la pedina

        //Per ridurre il numero di iterazioni del ciclo for si determina in quale quarto del tabellone sia la pedina
        Region[] array;
        boolean top, found, left;
        int begin, end;
        int out = UNDEFINED;
        if (node.getPawnCenterY() > (boardRect.getLayoutY() + boardRect.getHeight()/2)) {
            array = regArrayBot;
            top = false;
        } else {
            array = regArrayTop;
            top = true;
        }

        if (node.getPawnCenterX() > separator.getLayoutX()) {
            begin = 6;
            end = 11;
            left = false;
        } else {
            begin = 0;
            end = 5;
            left = true;
        }


        if (left && top) {
            if (blackExitRegion.contains(blackExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = 0;
            }
        } else if (left && !top) {
            if (whiteExitRegion.contains(whiteExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = 25;
            }
        }

        //  Ciclo for per controllare se la pedina si trova su una delle sei punte possibili
        if (out == UNDEFINED) {
            found = false;
            for (int i = begin; i <= end && !found; i++) {
                if (array[i].contains(array[i].sceneToLocal(node.getPawnCenter()))) {
                    found = true;
                    if (top) {
                        out = i + 1;
                    } else {
                        out = 24 - i;
                    }
                }
            }
        }
        return out;
    }
    protected void openDoubleDice() {
        if (!diceArray[UPPER_DOUBLE_DICE].isVisible()) {
            App.getStage().setResizable(false);
            Timeline timeline = new Timeline (
                    new KeyFrame(Duration.ZERO,
                            e -> {
                                diceArray[UPPER_DOUBLE_DICE].setVisible(true);
                                diceArray[LOWER_DOUBLE_DICE].setVisible(true);
                            },
                            new KeyValue(diceArray[UPPER_DOUBLE_DICE].layoutYProperty(), diceArray[UPPER_DICE].getLayoutY()),
                            new KeyValue(diceArray[LOWER_DOUBLE_DICE].layoutYProperty(), diceArray[LOWER_DICE].getLayoutY())
                    ),
                    new KeyFrame(Duration.seconds(0.5),
                            e -> App.getStage().setResizable(true),
                            new KeyValue(diceArray[UPPER_DOUBLE_DICE].layoutYProperty(), diceArray[UPPER_DICE].getLayoutY() + diceArray[UPPER_DICE].getFitHeight()),
                            new KeyValue(diceArray[LOWER_DOUBLE_DICE].layoutYProperty(), diceArray[LOWER_DICE].getLayoutY() - diceArray[LOWER_DICE].getFitHeight())
                    )
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }
    protected void closeDoubleDice() {
        if (diceArray[UPPER_DOUBLE_DICE].isVisible()) {
            App.getStage().setResizable(false);
            Timeline timeline = new Timeline (
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(diceArray[UPPER_DOUBLE_DICE].layoutYProperty(), diceArray[UPPER_DICE].getLayoutY() + diceArray[UPPER_DICE].getFitHeight()),
                            new KeyValue(diceArray[LOWER_DOUBLE_DICE].layoutYProperty(), diceArray[LOWER_DOUBLE_DICE].getLayoutY() - diceArray[LOWER_DICE].getFitHeight())
                    ),
                    new KeyFrame(Duration.seconds(0.5),
                            e -> {
                                App.getStage().setResizable(true);
                                diceArray[UPPER_DOUBLE_DICE].setVisible(false);
                                diceArray[LOWER_DOUBLE_DICE].setVisible(false);
                            },
                            new KeyValue(diceArray[UPPER_DOUBLE_DICE].layoutYProperty(), diceArray[UPPER_DICE].getLayoutY()),
                            new KeyValue(diceArray[LOWER_DOUBLE_DICE].layoutYProperty(), diceArray[LOWER_DICE].getLayoutY())
                    )
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    protected void openBlackExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        blackExitRegion.setVisible(true);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), 0),
                        new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1),  e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                }, new KeyValue(blackExitRegion.widthProperty() , GameViewRedraw.getMaxExitWidth() ),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    protected void openWhiteExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        whiteExitRegion.setVisible(true);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), 0),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())),
                new KeyFrame(Duration.seconds(1), e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                }, new KeyValue(whiteExitRegion.widthProperty() , GameViewRedraw.getMaxExitWidth() ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    protected void closeBlackExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(blackExitRegion.widthProperty(), GameViewRedraw.getMaxExitWidth()),
                        new KeyValue(blackExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1),  e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                    blackExitRegion.setVisible(false);
                }, new KeyValue(blackExitRegion.widthProperty() ,  0 ),
                        new KeyValue(blackExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void closeWhiteExit() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(whiteExitRegion.widthProperty(), GameViewRedraw.getMaxExitWidth()),
                        new KeyValue(whiteExitRegion.layoutXProperty(), (outerRect.getLayoutX() - GameViewRedraw.getMaxExitWidth()))),
                new KeyFrame(Duration.seconds(1), e-> {
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                    whiteExitRegion.setVisible(false);
                }, new KeyValue(whiteExitRegion.widthProperty() , 0 ),
                        new KeyValue(whiteExitRegion.layoutXProperty(), outerRect.getLayoutX())
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    protected void openDiceTray() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), e-> {
                    this.diceTrayOpen = true;
                    if (blockResizeCondition())
                        App.getStage().setResizable(true);
                    diceArray[UPPER_DICE].setVisible(true);
                    diceArray[LOWER_DICE].setVisible(true);
                    DynamicGameBoardRedraw.resizeDice(this);
                    logic.firstTurn(whoCalled);
                }, new KeyValue(diceTray.widthProperty() , GameViewRedraw.getMaxDTWidth() )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    protected void closeDiceTray() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO,
                        e -> {
                            this.diceTrayOpen = false;
                            diceArray[UPPER_DICE].setVisible(false);
                            diceArray[LOWER_DICE].setVisible(false);
                        },
                        new KeyValue(diceTray.widthProperty(), GameViewRedraw.getMaxDTWidth())),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(diceTray.widthProperty() , 0 )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    protected void setPawnsVisible (boolean set) {
        for (int i = 0; i < PAWNS_PER_PLAYER; i++){
            pawnArrayWHT[i].setVisible(set);
            pawnArrayBLK[i].setVisible(set);
        }
    }

    protected int selectedIndex = UNDEFINED;
    protected boolean isMovementKey (String keyPressed) {
        return keyPressed.equals(logic.getMoveRight()) || keyPressed.equals(logic.getMoveLeft())
                || keyPressed.equals(logic.getMoveUp()) || keyPressed.equals(logic.getMoveDown());
    }

    protected void selectInitialPoint() {
        if (logic.getWhichTurn(whoCalled))
            selectedIndex = COL_WHITE;
        else selectedIndex = COL_BLACK;
        colorPoint(selectedIndex, Color.web(logic.getSelectedPointColor()));
    }
    protected int moveHorizontally(int selectedIndex, String keyPressed) {
        int out;
        if (selectedIndex <13) { // La punta selezionata è sopra
            if (keyPressed.equals(logic.getMoveRight())) {
                out = (selectedIndex + 13 + 1) % 13;
                if (!logic.getWhiteExit(whoCalled))
                    out = max(1, out);
            }
            else {
                out = (selectedIndex+13-1)%13;
                if (!logic.getBlackExit(whoCalled) && out == COL_BLACK_EXIT)
                    out = 12;
            }
        } else { // La punta selezionata è sotto
            if (keyPressed.equals(logic.getMoveRight())) {
                out = ((selectedIndex - 1) % 13) + 13;
                if (!logic.getWhiteExit(whoCalled) && out == COL_WHITE_EXIT)
                    out = 24;
            }
            else {
                out = ((selectedIndex+1)%13)+13;
                if (!logic.getWhiteExit(whoCalled) && out == COL_WHITE_EXIT)
                    out = 13;
            }
        }
        return out;
    }
    @FXML
    void comandaLAtastiera(KeyEvent event) {
        String keyPressed = event.getCode().toString();
        if (isMovementKey(keyPressed)) {
            if (selectedIndex==UNDEFINED)
                selectInitialPoint();
            else {
                restoreColorToPoint(selectedIndex);
                if (keyPressed.equals(logic.getMoveUp())||keyPressed.equals(logic.getMoveDown())) {
                    selectedIndex = 25 - selectedIndex;
                    if (selectedIndex == COL_WHITE_EXIT && !logic.getWhiteExit(whoCalled))
                        selectedIndex = COL_BLACK;
                    else if (selectedIndex == COL_BLACK_EXIT &&!logic.getBlackExit(whoCalled))
                        selectedIndex = COL_WHITE;
                }
                else selectedIndex = moveHorizontally(selectedIndex, keyPressed);
                colorPoint(selectedIndex, Color.web(logic.getSelectedPointColor()));
            }
        }
        else if (keyPressed.equals(logic.getSelect()) && !selected){
            col = trovaColonna();
            if(logic.searchTopOccupiedRow(whoCalled, col)!=UNDEFINED &&
                    (logic.getBoardPlaceState(col, logic.searchTopOccupiedRow(whoCalled, col), whoCalled)==WHITE)
                            == logic.getWhichTurn(whoCalled)) {
                logic.selectPawn(col, logic.searchTopOccupiedRow(whoCalled, col), whoCalled);
                selected = true;
                logic.createMoveBuffer(col, whoCalled);
                DynamicGameBoardRedraw.redrawPawns(this);
            }
        } else if (keyPressed.equals(logic.getSelect()) && selected){
            int col2 = trovaColonna();
            logic.deselectPawn(col, logic.searchTopOccupiedRow(whoCalled, col), whoCalled);
            logic.placePawnOnPoint(col2, whoCalled);
            view.restoreBoardColors(whoCalled);
            DynamicGameBoardRedraw.redrawPawns(this);
            selected = false;
        }
    }
    boolean selected = false;
    int col;

    protected void restoreColorToPoint(int restoreIndex) {
        Color color;
        switch (restoreIndex){
            default:
                if (restoreIndex % 2 == 1) {
                    color = Color.web(logic.getEvenPointsColor());
                }
                else {
                    color = Color.web(logic.getOddPointsColor());
                }
                break;
            case COL_WHITE_EXIT:
                color = Color.web(logic.getWhitePawnFill());
                break;
            case COL_BLACK_EXIT:
                color = Color.web(logic.getBlackPawnFill());
                break;
        }
        colorPoint(restoreIndex, color);
        if (selected) {
            DynamicGameBoardRedraw.redrawPawns(this);
        }
    }

    private int trovaColonna(){
        int col = UNDEFINED;
        for (int i = 0; i < 12; i++) {
            if (polArrayTop[i].getFill().equals(Paint.valueOf(logic.getSelectedPointColor()))) {
                col = i + 1;
            } else if (polArrayBot[i].getFill().equals(Paint.valueOf(logic.getSelectedPointColor()))) {
                col = (24-i);
            }
        }
        if (col == UNDEFINED) {
            if (whiteExitRegion.getFill().equals(Paint.valueOf(logic.getSelectedPointColor())))
                col = COL_WHITE_EXIT;
            else if (blackExitRegion.getFill().equals(Paint.valueOf(logic.getSelectedPointColor())))
                col = COL_BLACK_EXIT;
        }
        return col;
    }



}
