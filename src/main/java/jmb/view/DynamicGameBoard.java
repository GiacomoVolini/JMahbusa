package jmb.view;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jmb.view.utilities.AnimationBuilder;
import java.util.Objects;

import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public abstract class DynamicGameBoard extends GameBoard implements AnimatedBoard{

    protected Rectangle diceTray;
    protected ImageView[] diceArray = new ImageView[4];
    protected ImageView feltImage = new ImageView();
    protected Timeline diceRollAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> DiceView.rndRolls(diceArray))
    ); //Necessario impostare il numero di cicli quando si utilizza
    protected boolean selected = false;
    private int col;
    protected int selectedIndex = UNDEFINED;

    public DynamicGameBoard() {
        feltImage.setImage(new Image(Objects.requireNonNull(this.getClass().getResource("WhiteFelt.png")).toString()));
        feltImage.setBlendMode(BlendMode.MULTIPLY);
        Image[] imgArray = new Image[]{
                new Image(Objects.requireNonNull(this.getClass().getResource("diceImg/Dado1.png")).toString()),
                new Image(Objects.requireNonNull(this.getClass().getResource("diceImg/Dado1INV.png")).toString()),
        };
        diceTray = new Rectangle();
        switch (logic.getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD:
                diceTray.setFill(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                break;
            case LEFT_PRESET:
                diceTray.setFill(Color.web(logic.getSetting(LEFT, BOARD_INNER)));
                break;
            case RIGHT_PRESET:
                diceTray.setFill(Color.web(logic.getSetting(RIGHT, BOARD_INNER)));
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
        boardAnchor.setStyle("-fx-background-color: " + logic.getSetting("Customization", "backgroundColor", String.class));
    }

    private void savePosition (MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = searchPawnPlace(node);
        logic.createMoveBuffer(col);
        logic.isPawnMovable(col, logic.searchTopOccupiedRow(col), true);
    }

    private void dragPawn(MouseEvent event) {
        PawnView n = (PawnView) event.getSource();
        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }
    protected void releasePawn(MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = this.searchPawnPlace(node);
        if (col != UNDEFINED)
            logic.placePawnOnPoint(col);
        view.restoreBoardColors();
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

        if (left) {
            if (top && blackExitRegion.contains(blackExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = COL_BLACK_EXIT;
            } else if (whiteExitRegion.contains(whiteExitRegion.sceneToLocal(node.getPawnCenter()))) {
                out = COL_WHITE_EXIT;
            }
        }

        //  Ciclo for per controllare se la pedina si trova su una delle sei punte possibili
        if (out == UNDEFINED) {
            found = false;
            for (int i = begin; i <= end && !found; i++)
                if (array[i].contains(array[i].sceneToLocal(node.getPawnCenter()))) {
                    found = true;
                    if (top)
                        out = i + 1;
                    else out = 24 - i;
                }
        }
        return out;
    }
    public void handleDoubleDice(boolean open) {
        if (open ^ diceArray[UPPER_DOUBLE_DICE].isVisible() ) {
            letWindowResize(false);
            Timeline timeline = AnimationBuilder.createDoubleDiceAnimation(diceArray, open);
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
        Timeline timeline = AnimationBuilder.createExitZoneAnimation(exitRegion, outerRect, open);
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
        Timeline timeline = AnimationBuilder.createDiceTrayAnimation(this, diceTray, diceArray);
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
        return keyPressed.equals(logic.getSetting("Controls", "moveRight", String.class)) ||
                keyPressed.equals(logic.getSetting("Controls", "moveLeft", String.class)) ||
                keyPressed.equals(logic.getSetting("Controls", "moveUp", String.class))   ||
                keyPressed.equals(logic.getSetting("Controls", "moveDown", String.class));
    }

    protected void selectInitialPoint() {
        if (logic.getWhichTurn())
            selectedIndex = COL_WHITE;
        else selectedIndex = COL_BLACK;
        colorPoint(selectedIndex, Color.web(logic.getSetting("Customization", "selectedPointColor", String.class)));
    }
    protected int moveHorizontally(int selectedIndex, String keyPressed) {
        int out;
        if (selectedIndex <13) { // La punta selezionata è sopra
            if (keyPressed.equals(logic.getSetting("Controls", "moveRight", String.class))) {
                out = (selectedIndex + 13 + 1) % 13;
                if (!logic.getWhiteExit() && out == COL_BLACK_EXIT)
                    out = 1;
            }
            else {
                out = (selectedIndex+13-1)%13;
                if (!logic.getBlackExit() && out == COL_BLACK_EXIT)
                    out = 12;
            }
        } else { // La punta selezionata è sotto
            if (keyPressed.equals(logic.getSetting("Controls", "moveRight", String.class))) {
                out = ((selectedIndex - 1) % 13) + 13;
                if (!logic.getWhiteExit() && out == COL_WHITE_EXIT)
                    out = 24;
            }
            else {
                out = ((selectedIndex+1)%13)+13;
                if (!logic.getWhiteExit() && out == COL_WHITE_EXIT)
                    out = 13;
            }
        }
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
                if (keyPressed.equals(logic.getSetting("Controls", "moveUp", String.class)) ||
                        keyPressed.equals(logic.getSetting("Controls", "moveDown", String.class))) {
                    selectedIndex = 25 - selectedIndex;
                    if (selectedIndex == COL_WHITE_EXIT && !logic.getWhiteExit())
                        selectedIndex = COL_BLACK;
                    else if (selectedIndex == COL_BLACK_EXIT &&!logic.getBlackExit())
                        selectedIndex = COL_WHITE;
                }
                else selectedIndex = moveHorizontally(selectedIndex, keyPressed);
                colorPoint(selectedIndex, Color.web(logic.getSetting("Customization", "selectedPointColor", String.class)));
            }
        }
        else if (keyPressed.equals(logic.getSetting("Controls", "select", String.class))&&
                selectedIndex!= UNDEFINED && !selected){
            col = findColumn();
            if(logic.searchTopOccupiedRow(col)!=UNDEFINED &&
                    (logic.getBoardPlaceState(col, logic.searchTopOccupiedRow(col))==WHITE)
                            == logic.getWhichTurn()) {
                logic.selectPawn(col, logic.searchTopOccupiedRow(col));
                selected = true;
                logic.createMoveBuffer(col);
                DynamicGameBoardRedraw.redrawPawns(this);
            }
        } else if (keyPressed.equals(logic.getSetting("Controls", "select", String.class)) && selected){
            int col2 = findColumn();
            logic.deselectPawn(col, logic.searchTopOccupiedRow(col));
            logic.placePawnOnPoint(col2);
            view.restoreBoardColors();
            colorPoint(selectedIndex, Color.web(logic.getSetting("Customization", "selectedPointColor", String.class)));
            DynamicGameBoardRedraw.redrawPawns(this);
            selected = false;
        }
    }

    public void restoreColorToPoint(int restoreIndex) {
        Color color;
        switch (restoreIndex){
            default:
                if (restoreIndex % 2 == 1)
                    color = getPointColor("evenPointsColor", EVEN_POINTS);
                else color = getPointColor("oddPointsColor", ODD_POINTS);
                break;
            case COL_WHITE_EXIT:
                color = Color.web(logic.getSetting("Customization", "whitePawnFill", String.class));
                break;
            case COL_BLACK_EXIT:
                color = Color.web(logic.getSetting("Customization", "blackPawnFill", String.class));
                break;
        }
        colorPoint(restoreIndex, color);
        if (selected) {
            DynamicGameBoardRedraw.redrawPawns(this);
        }
    }

    private Color getPointColor (String customColorString, int presetArrayPosition) {
        Color out;
        switch (logic.getSetting("Customization", "boardPreset", int.class)) {
            case CUSTOM_BOARD: default:
                out = Color.web(logic.getSetting("Customization", customColorString, String.class));
                break;
            case LEFT_PRESET:
                out = Color.web(logic.getSetting(LEFT, presetArrayPosition));
                break;
            case RIGHT_PRESET:
                out = Color.web(logic.getSetting(RIGHT, presetArrayPosition));
                break;
        }
        return out;
    }

    private int findColumn(){
        int col = UNDEFINED;

        if (whiteExitRegion.getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class))))
            col = COL_WHITE_EXIT;
        else if (blackExitRegion.getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class))))
            col = COL_BLACK_EXIT;

        for (int i = 0; col == UNDEFINED && i < 12; i++) {
            if (polArrayTop[i].getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class))))
                col = i + 1;
            else if (polArrayBot[i].getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class))))
                col = (24-i);
        }
        return col;
    }

    public ImageView[] getDiceArray() {
        return this.diceArray;
    }

}
