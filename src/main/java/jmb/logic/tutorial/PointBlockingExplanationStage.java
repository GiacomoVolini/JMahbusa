package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;

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
        logic.setWhiteTurn(false);
        String stageStringMiddlePart;
        if (logic.getBoardMatrix()[0][12]==WHITE)
            stageStringMiddlePart = " 12";
        else stageStringMiddlePart = " 6";
        stringToPass = logic.getString(stageStringFirstPart)+stageStringMiddlePart+logic.getString(stageStringLastPart);
        view.setNextTutorialString(stringToPass, true);
        view.tutorialTextBoxAnimation(0.6,0.5);
        view.allowTextBoxMouseInput(false);
        view.waitForRecall(1.5);
    }

    public void action() {
        if (internalIndex < 11) {
            boardMoves();
            view.callRedraw();
        } else {
            view.allowTextBoxMouseInput(false);
            logic.nextTutorialStage();
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
                logic.forceMovePawn(to, COL_WHITE);
                view.waitForRecall(1);
                break;
        }
        internalIndex++;
    }

    private void placePawnToBlock() {
        if (logic.getBoardMatrix()[0][12]==WHITE)
            to = 12;
        else to = 6;
        logic.forceMovePawn(COL_BLACK, to);
        if(!logic.getSetting("Audio", "muteSFX", boolean.class))
            view.playSFX(SFX.PAWN_DROP);
        view.waitForRecall(1.5);
    }

    private void removeExcessBlockingPawns() {
        for (int i = 0; i<4; i++)
            logic.forceMovePawn(to, COL_BLACK);
        if(!logic.getSetting("Audio", "muteSFX", boolean.class))
            view.playSFX(SFX.PAWN_DROP);
        view.waitForRecall(1.5);
    }

    private void makeWrongMove() {
        logic.forceMovePawn(COL_WHITE, to);
        if(!logic.getSetting("Audio", "muteSFX", boolean.class))
            view.playSFX(SFX.ERROR);
        view.waitForRecall(0.5);
    }

    private void changeWrongPawnColor() {
        if (internalIndex%2 == 0)
            logic.getBoardMatrix()[logic.searchTopOccupiedRow(to)][to]= WRONG_WHITE;
        else logic.getBoardMatrix()[logic.searchTopOccupiedRow(to)][to] = WHITE;
        view.waitForRecall(0.5);
    }

}
