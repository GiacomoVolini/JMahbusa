package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.*;

public class PointBlockingExplanationStage extends ComparableTutorialStage{

    private int to;
    private String stringToPass;
    private String stageStringFirstPart ="pointBlockingStageFirst";
    private String stageStringLastPart = "pointBlockingStageLast";
    public PointBlockingExplanationStage() {
        super();
        setStageIndex(6);
    }
    public void start() {
        getLogic().setWhiteTurn(false);
        String stageStringMiddlePart;
        if (getLogic().getBoardMatrix()[0][12]==WHITE)
            stageStringMiddlePart = " 12";
        else stageStringMiddlePart = " 6";
        stringToPass = getLogic().getString(stageStringFirstPart)+stageStringMiddlePart+getLogic().getString(stageStringLastPart);
        getView().setNextTutorialString(stringToPass, true);
        getView().tutorialTextBoxAnimation(0.6,0.5);
        getView().allowTextBoxMouseInput(false);
        getView().waitForRecall(1.5);
    }

    public void action() {
        if (internalIndex < 11) {
            boardMoves();
            getView().callRedraw();
        } else {
            getView().allowTextBoxMouseInput(false);
            getLogic().nextTutorialStage();
        }
    }

    private void boardMoves() {
        switch (internalIndex) {
            case 0: case 1: case 2: case 3:
                placePawnToBlock();
                break;
            case 4:
                removeExcessBlockingPawns();
                break;
            case 5:
                makeWrongMove();
                break;
            case 6: case 7: case 8: case 9:
                changeWrongPawnColor();
                break;
            case 10:
                getLogic().forceMovePawn(to, COL_WHITE);
                getView().waitForRecall(1);
                break;
        }
        internalIndex++;
    }

    private void placePawnToBlock() {
        if (getLogic().getBoardMatrix()[0][12]==WHITE)
            to = 12;
        else to = 6;
        getLogic().forceMovePawn(COL_BLACK, to);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.PAWN_DROP);
        getView().waitForRecall(1.5);
    }

    private void removeExcessBlockingPawns() {
        for (int i = 0; i<4; i++)
            getLogic().forceMovePawn(to, COL_BLACK);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.PAWN_DROP);
        getView().waitForRecall(1.5);
    }

    private void makeWrongMove() {
        getLogic().forceMovePawn(COL_WHITE, to);
        if(!getLogic().getSetting("Audio", "muteSFX", boolean.class))
            getView().playSFX(SFX.ERROR);
        getView().waitForRecall(0.5);
    }

    private void changeWrongPawnColor() {
        if (internalIndex%2 == 0)
            getLogic().getBoardMatrix()[getLogic().searchTopOccupiedRow(to)][to]= WRONG_WHITE;
        else getLogic().getBoardMatrix()[getLogic().searchTopOccupiedRow(to)][to] = WHITE;
        getView().waitForRecall(0.5);
    }

}
