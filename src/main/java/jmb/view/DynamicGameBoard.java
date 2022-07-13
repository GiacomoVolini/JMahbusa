package jmb.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URISyntaxException;

import static jmb.ConstantsShared.UNDEFINED;
import static jmb.view.ConstantsView.*;
import static jmb.view.View.logic;

public class DynamicGameBoard extends GameBoard{

    protected Rectangle diceTray;

    protected ImageView[] diceArray = new ImageView[4];

    protected boolean dtAnimDone = false;


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
        logic.createMoveBuffer(this.searchPawnPlace(node));
    }

    //  Metodo per il trascinamento della pedina
    private void drag(MouseEvent event) {

        PawnView n = (PawnView) event.getSource();

        n.setLayoutX(n.getLayoutX() + event.getX());
        n.setLayoutY(n.getLayoutY() + event.getY());
    }
    private void releasePawn(MouseEvent event) {
        PawnView node = (PawnView)event.getSource();
        int col = this.searchPawnPlace(node);
        if (col != UNDEFINED) {
            logic.placePawnOnPoint(col);
        }
        GameViewRedraw.redrawPawns(this);

    }
    private int searchPawnPlace(PawnView node) {
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
        System.out.println("Ho chiamato openDoubleDice");
        System.out.println(diceArray[UPPER_DOUBLE_DICE].isVisible());
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
        System.out.println("Ho chiamato closeDoubleDice");
        System.out.println(diceArray[UPPER_DOUBLE_DICE].isVisible());
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

    protected void diceTrayAnim() {
        if (blockResizeCondition())
            App.getStage().setResizable(false);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.ZERO, new KeyValue(diceTray.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e-> {
                    this.dtAnimDone = true;
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

}