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

import java.net.URISyntaxException;
import java.util.Objects;

import static java.lang.Math.max;
import static jmb.ConstantsShared.*;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;
import static jmb.view.View.view;

public abstract class DynamicGameBoard extends GameBoard implements AnimatedBoard{

    protected Rectangle diceTray;

    protected ImageView[] diceArray = new ImageView[4];
    protected ImageView feltImage = new ImageView();

    protected boolean diceTrayOpen = false;
    protected Timeline diceRollAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> DiceView.rndRolls(diceArray))
    ); //Necessario impostare il numero di cicli quando si utilizza


    public DynamicGameBoard() {
        try {

            feltImage.setImage(new Image (Objects.requireNonNull(this.getClass().getResource("WhiteFelt.png")).toString()));
            feltImage.setBlendMode(BlendMode.MULTIPLY);
            Image[] imgArray = new Image[] {
                    new Image(this.getClass().getResource("diceImg/Dado1.png").toURI().toString()),
                    new Image(this.getClass().getResource("diceImg/Dado1INV.png").toURI().toString()),
            };
            diceTray = new Rectangle();
            switch (logic.getSetting("Customization", "boardPreset", int.class)) {
                case CUSTOM_BOARD:
                    diceTray.setFill(Color.web(logic.getSetting("Customization", "boardInnerColor", String.class)));
                    break;
                case LEFT_PRESET:
                    diceTray.setFill(Color.web(logic.getSetting(LEFT, Preset.BOARD_INNER.ordinal())));
                    break;
                case RIGHT_PRESET:
                    diceTray.setFill(Color.web(logic.getSetting(RIGHT, Preset.BOARD_INNER.ordinal())));
                    break;
            }
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
            logic.placePawnOnPoint(col);
        }
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
    public void openDoubleDice() {
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
    public void closeDoubleDice() {
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

    public void openBlackExit() {
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
    public void openWhiteExit() {
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
                    logic.firstTurn();
                }, new KeyValue(diceTray.widthProperty() , GameViewRedraw.getMaxDTWidth() )
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void setPawnsVisible (boolean set) {
        for (int i = 0; i < PAWNS_PER_PLAYER; i++){
            pawnArrayWHT[i].setVisible(set);
            pawnArrayBLK[i].setVisible(set);
        }
    }

    protected int selectedIndex = UNDEFINED;
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
        if (selectedIndex <13) { // La punta selectKeyBind è sopra
            if (keyPressed.equals(logic.getSetting("Controls", "moveRight", String.class))) {
                out = (selectedIndex + 13 + 1) % 13;
                if (!logic.getWhiteExit())
                    out = max(1, out);
            }
            else {
                out = (selectedIndex+13-1)%13;
                if (!logic.getBlackExit() && out == COL_BLACK_EXIT)
                    out = 12;
            }
        } else { // La punta selectKeyBind è sotto
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
        System.out.println(keyPressed);
        System.out.println(logic.getSetting("Controls", "moveDown", String.class));
        System.out.println(logic.getSetting("Controls", "moveDown", String.class).equals(keyPressed));
        if (isMovementKey(keyPressed)) {
            if (selectedIndex==UNDEFINED)
                selectInitialPoint();
            else {
                restoreColorToPoint(selectedIndex);
                if (keyPressed.equals(logic.getSetting("Controls", "moveUp", String.class)) ||
                        keyPressed.equals(logic.getSetting("Controls", "moveDown", String.class)))
                {
                    System.out.println("ENTRO , PIGI O SU O GIU");
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
            System.out.println(col);
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
    boolean selected = false;
    int col;

    public void restoreColorToPoint(int restoreIndex) {
        Color color = Color.RED;
        switch (restoreIndex){
            default:
                if (restoreIndex % 2 == 1) {
                    switch(logic.getSetting("Customization", "boardPreset", int.class)) {
                        case CUSTOM_BOARD:
                            color = Color.web(logic.getSetting("Customization", "evenPointsColor", String.class));
                            break;
                        case LEFT_PRESET:
                            color = Color.web (logic.getSetting(LEFT, Preset.EVEN_POINTS.ordinal()));
                            break;
                        case RIGHT_PRESET:
                            color = Color.web(logic.getSetting(RIGHT, Preset.EVEN_POINTS.ordinal()));
                            break;
                    }
                    }
                else {
                    switch (logic.getSetting("Customization", "boardPreset", int.class)) {
                        case CUSTOM_BOARD:
                            color = Color.web(logic.getSetting("Customization", "oddPointsColor", String.class));
                            break;
                        case LEFT_PRESET:
                            color = Color.web(logic.getSetting(LEFT, Preset.ODD_POINTS.ordinal()));
                            break;
                        case RIGHT_PRESET:
                            color = Color.web(logic.getSetting(RIGHT, Preset.ODD_POINTS.ordinal()));
                            break;
                    }
                }
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

    private int findColumn(){
        int col = UNDEFINED;
        for (int i = 0; i < 12; i++) {
            if (polArrayTop[i].getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class)))) {
                col = i + 1;
            } else if (polArrayBot[i].getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class)))) {
                col = (24-i);
            }
        }
        if (col == UNDEFINED) {
            if (whiteExitRegion.getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class))))
                col = COL_WHITE_EXIT;
            else if (blackExitRegion.getFill().equals(Paint.valueOf(logic.getSetting("Customization", "selectedPointColor", String.class))))
                col = COL_BLACK_EXIT;
        }
        return col;
    }

    public ImageView[] getDiceArray() {
        return this.diceArray;
    }



}
