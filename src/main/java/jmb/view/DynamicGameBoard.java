package jmb.view;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jmb.view.utilities.*;
import java.util.Objects;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.*;

public abstract class DynamicGameBoard extends GameBoard implements AnimatedBoard, GenericGUI {

    protected Rectangle diceTray;
    protected ImageView[] diceArray = new ImageView[4];
    protected ImageView feltImage = new ImageView();
    protected Timeline diceRollAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> DiceView.rndRolls(diceArray))
    ); //Necessario impostare il numero di cicli quando si utilizza
    protected boolean selected = false;
    private int col;
    protected int selectedIndex = UNDEFINED;

    public void initialize() {
        feltImage.setImage(new Image(Objects.requireNonNull(this.getClass().getResource("WhiteFelt.png")).toString()));
        feltImage.setBlendMode(BlendMode.MULTIPLY);
        Image[] imgArray = new Image[]{
                new Image(Objects.requireNonNull(this.getClass().getResource("diceImg/Dado1.png")).toString()),
                new Image(Objects.requireNonNull(this.getClass().getResource("diceImg/Dado1INV.png")).toString()),
        };
        diceTray = new Rectangle();
        switch (getLogic().getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD:
                diceTray.setFill(Color.web(getLogic().getSetting("Customization", "boardInnerColor", String.class)));
                break;
            case LEFT_PRESET:
                diceTray.setFill(Color.web(getLogic().getSetting(LEFT, BOARD_INNER)));
                break;
            case RIGHT_PRESET:
                diceTray.setFill(Color.web(getLogic().getSetting(RIGHT, BOARD_INNER)));
                break;
        }
        diceTray.setStroke(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            diceArray[i] = new ImageView(imgArray[i / 2]);
            diceArray[i].setViewOrder(-3 + (i / 2));
            diceArray[i].setVisible(false);
        }
        for (int i = 0; i < 15; i++) {
            pawnArrayWHT[i].setOnMouseDragged(this::dragPawn);
            pawnArrayWHT[i].setOnMousePressed(this::savePosition);
            pawnArrayWHT[i].setOnMouseReleased(this::releasePawn);
            pawnArrayBLK[i].setOnMouseDragged(this::dragPawn);
            pawnArrayBLK[i].setOnMousePressed(this::savePosition);
            pawnArrayBLK[i].setOnMouseReleased(this::releasePawn);
        }
    }

    protected void addChildrenToAnchor() {
        super.addChildrenToAnchor();
        boardAnchor.getChildren().addAll(diceTray, feltImage);
        for (ImageView dice : diceArray) {
            boardAnchor.getChildren().add(dice);
            dice.setVisible(false);
        }
        feltImage.fitHeightProperty().bind(boardAnchor.heightProperty());
        feltImage.fitWidthProperty().bind(boardAnchor.widthProperty());
        feltImage.setViewOrder(50);
        boardAnchor.setStyle("-fx-background-color: " + getLogic().getSetting("Customization", "backgroundColor", String.class));
    }

    private void savePosition (MouseEvent event) {
        int col = searchPawnPlace(event);
        getLogic().createMoveBuffer(col);
        getLogic().isPawnMovable(col, getLogic().searchTopOccupiedRow(col), true);
    }

    private void dragPawn(MouseEvent event) {
        PawnView n = (PawnView) event.getSource();
        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }
    protected void releasePawn(MouseEvent event) {
        int col = this.searchPawnPlace(event);
        if (col != UNDEFINED)
            getLogic().placePawnOnPoint(col);
        getView().restoreBoardColors();
        GameViewRedraw.redrawPawns(this);
    }
    protected int searchPawnPlace(MouseEvent event) {
        //Il metodo cerca a quale zona del tabellone appartiene la pedina
        //Per ridurre il numero di iterazioni del ciclo for si determina in quale quarto del tabellone sia la pedina
        int begin, end, out = UNDEFINED;

        if (event.getSceneX() > separator.getLayoutX()) {
            begin = 6;
            end = 11;
        } else {
            begin = 0;
            end = 5;
            if (blackExitRegion.contains(blackExitRegion.sceneToLocal(event.getSceneX(), event.getSceneY())))
                out = COL_BLACK_EXIT;
            if (whiteExitRegion.contains(whiteExitRegion.sceneToLocal(event.getSceneX(), event.getSceneY())))
                out = COL_WHITE_EXIT;
        }

        //  Ciclo for per controllare se la pedina si trova su una delle sei punte possibili
        for (int i = begin; out == UNDEFINED && i <= end; i++)
            if (event.getSceneY() < (boardRect.getLayoutY() + boardRect.getHeight()/2)) {
                if (regArrayTop[i].contains(regArrayTop[i].sceneToLocal(event.getSceneX(), event.getSceneY())))
                    out = i + 1;
            } else if (regArrayBot[i].contains(regArrayBot[i].sceneToLocal(event.getSceneX(), event.getSceneY())))
                out = 24 - i;
        return out;
    }
    public void handleDoubleDice(boolean open) {
        if (open ^ diceArray[UPPER_DOUBLE_DICE].isVisible() ) {
            letWindowResize(false);
            Timeline timeline = TimelineBuilder.createDoubleDiceAnimation(diceArray, open);
            timeline.setOnFinished(e -> letWindowResize(true));
            timeline.play();
        }
    }
    public void openDoubleDice() {
        handleDoubleDice(true);
    }

    public void closeDoubleDice() {
        handleDoubleDice(false);
    }

    public void handleExitRegion(Rectangle exitRegion, boolean open) {
            letWindowResize(false);
            if (open)
                exitRegion.setVisible(true);
            Timeline timeline = TimelineBuilder.createExitZoneAnimation(exitRegion, outerRect, open);
            timeline.setOnFinished(e -> letWindowResize(true));
            timeline.play();
    }

    public void openBlackExit() {
        handleExitRegion(blackExitRegion, true);
    }
    public void openWhiteExit() {
        handleExitRegion(whiteExitRegion, true);
    }
    public void closeBlackExit() {
        if (blackExitRegion.getWidth()>0)
            handleExitRegion(blackExitRegion, false);
    }
    public void closeWhiteExit() {
        if (whiteExitRegion.getWidth() > 0)
            handleExitRegion(whiteExitRegion, false);
    }

    public void openDiceTray() {
        letWindowResize(false);
        Timeline timeline = TimelineBuilder.createDiceTrayAnimation(this, diceTray, diceArray);
        timeline.setOnFinished(e -> letWindowResize(true));
        timeline.play();
    }

    private void letWindowResize(boolean set) {
        if (blockResizeCondition())
            App.getStage().setResizable(set);
    }

    public void setPawnsVisible (boolean set) {
        for (int i = 0; i < PAWNS_PER_PLAYER; i++){
            pawnArrayWHT[i].setVisible(set);
            pawnArrayBLK[i].setVisible(set);
        }
    }
    protected boolean isMovementKey (String keyPressed) {
        return keyPressed.equals(getLogic().getSetting("Controls", "moveRight", String.class)) ||
                keyPressed.equals(getLogic().getSetting("Controls", "moveLeft", String.class)) ||
                keyPressed.equals(getLogic().getSetting("Controls", "moveUp", String.class))   ||
                keyPressed.equals(getLogic().getSetting("Controls", "moveDown", String.class));
    }

    protected void selectInitialPoint() {
        if (getLogic().getWhichTurn())
            selectedIndex = COL_WHITE;
        else selectedIndex = COL_BLACK;
        colorPoint(selectedIndex, Color.web(getLogic().getSetting("Customization", "selectedPointColor", String.class)));
    }

    protected int moveHorizontally(int selectedIndex, boolean top, boolean right) {
        int out, columnShift, rowShift = 0;
        int exitColumn = COL_BLACK_EXIT;
        boolean exitCheck = getLogic().getBlackExit();
        if (!top) {
            rowShift = 13;
            exitCheck = getLogic().getWhiteExit();
            exitColumn = COL_WHITE_EXIT;
        }
        if (top ^ right)
            columnShift = -1;
        else columnShift = 1;
        out = (selectedIndex + 13 + columnShift) % 13 + rowShift;
        if (!exitCheck && out == exitColumn)
            out = moveHorizontally(out, top, right);
        return out;
    }

    @FXML
    void handleKeyboard(KeyEvent event) {
        String keyPressed = event.getCode().toString();
        if (isMovementKey(keyPressed)) {
            if (selectedIndex==UNDEFINED)
                selectInitialPoint();
            else {
                restoreColorToPoint(selectedIndex);
                if (keyPressed.equals(getLogic().getSetting("Controls", "moveUp", String.class)) ||
                        keyPressed.equals(getLogic().getSetting("Controls", "moveDown", String.class))) {
                    selectedIndex = 25 - selectedIndex;
                    if (selectedIndex == COL_WHITE_EXIT && !getLogic().getWhiteExit())
                        selectedIndex = COL_BLACK;
                    else if (selectedIndex == COL_BLACK_EXIT &&!getLogic().getBlackExit())
                        selectedIndex = COL_WHITE;
                }
                else selectedIndex = moveHorizontally(selectedIndex, selectedIndex < 13,
                        keyPressed.equals(getLogic().getSetting("Controls", "moveRight", String.class)));
                colorPoint(selectedIndex, Color.web(getLogic().getSetting("Customization", "selectedPointColor", String.class)));
            }
        }
        else if (keyPressed.equals(getLogic().getSetting("Controls", "select", String.class)) &&
                selectedIndex!= UNDEFINED && !selected){
            col = findColumn();
            if(getLogic().searchTopOccupiedRow(col)!=UNDEFINED &&
                    (getLogic().getBoardPlaceState(col, getLogic().searchTopOccupiedRow(col))==WHITE) == getLogic().getWhichTurn()) {
                getLogic().selectPawn(col, getLogic().searchTopOccupiedRow(col));
                selected = true;
                getLogic().createMoveBuffer(col);
                DynamicGameBoardRedraw.redrawPawns(this);
            }
        } else if (keyPressed.equals(getLogic().getSetting("Controls", "select", String.class)) && selected){
            int col2 = findColumn();
            getLogic().deselectPawn(col, getLogic().searchTopOccupiedRow(col));
            getLogic().placePawnOnPoint(col2);
            getView().restoreBoardColors();
            colorPoint(selectedIndex, Color.web(getLogic().getSetting("Customization", "selectedPointColor", String.class)));
            DynamicGameBoardRedraw.redrawPawns(this);
            selected = false;
        }
    }

    public void restoreBoardColors() {
        for (int i = 0; i < 26; i++)
            this.restoreColorToPoint(i);
    }

    public void restoreColorToPoint(int restoreIndex) {
        Color color;
        switch (restoreIndex){
            default:
                if (restoreIndex % 2 == 1)
                    color = ColorHandler.getPointColor("evenPointsColor", EVEN_POINTS);
                else color = ColorHandler.getPointColor("oddPointsColor", ODD_POINTS);
                break;
            case COL_WHITE_EXIT:
                color = Color.web(getLogic().getSetting("Customization", "whitePawnFill", String.class));
                break;
            case COL_BLACK_EXIT:
                color = Color.web(getLogic().getSetting("Customization", "blackPawnFill", String.class));
                break;
        }
        if (restoreIndex != UNDEFINED)
            colorPoint(restoreIndex, color);
        if (selected) {
            DynamicGameBoardRedraw.redrawPawns(this);
        }
    }

    private int findColumn(){
        int col = UNDEFINED;

        if (whiteExitRegion.getFill().equals(Paint.valueOf(getLogic().getSetting("Customization", "selectedPointColor", String.class))))
            col = COL_WHITE_EXIT;
        else if (blackExitRegion.getFill().equals(Paint.valueOf(getLogic().getSetting("Customization", "selectedPointColor", String.class))))
            col = COL_BLACK_EXIT;

        for (int i = 0; col == UNDEFINED && i < 12; i++) {
            if (polArrayTop[i].getFill().equals(Paint.valueOf(getLogic().getSetting("Customization", "selectedPointColor", String.class))))
                col = i + 1;
            else if (polArrayBot[i].getFill().equals(Paint.valueOf(getLogic().getSetting("Customization", "selectedPointColor", String.class))))
                col = (24-i);
        }
        return col;
    }

    public ImageView[] getDiceArray() {
        return this.diceArray;
    }

}
