package jmb.logic.tutorial;

import static jmb.logic.Logic.logic;
import static jmb.logic.Logic.view;
import static jmb.ConstantsShared.*;

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
        logic.setWhiteTurn(whoCalled, false);
        String stageStringMiddlePart;
        if (logic.getBoardMatrix(whoCalled)[0][12]==WHITE)
            stageStringMiddlePart = " 12";
        else stageStringMiddlePart = " 6";
        stringToPass = logic.getString(stageStringFirstPart)+stageStringMiddlePart+logic.getString(stageStringLastPart);
        view.setNextTutorialString(stringToPass, true);
        view.tutorialTextBoxAnimation(0.6,0.5);
        view.allowTextBoxMouseInput(false);
        view.waitForRecall(whoCalled, 1.5);
    }

    public void action() {
        if (internalIndex < 11) {
            boardMoves();
            view.callRedraw(whoCalled);
        } else {
            view.allowTextBoxMouseInput(false);
            logic.nextTutorialStage();
        }
    }

    private void boardMoves() {
        switch (internalIndex) {
            case 0: case 1: case 2: case 3:
                if (logic.getBoardMatrix(whoCalled)[0][12]==WHITE)
                    to = 12;
                else to = 6;
                logic.forceMovePawn(whoCalled, COL_BLACK, to);
                view.playSFX(PAWN_SFX);
                view.waitForRecall(whoCalled, 1.5);
                break;
            case 4:
                for (int i = 0; i<4; i++)
                    logic.forceMovePawn(whoCalled, to, COL_BLACK);
                view.playPawnSFX();
                view.waitForRecall(whoCalled, 1.5);
                break;
            case 5:
                logic.forceMovePawn(whoCalled, COL_WHITE, to);
                view.playSFX(ERROR_SFX);
                view.waitForRecall(whoCalled, 0.5);
                break;
            case 6: case 7: case 8: case 9:
                if (internalIndex%2 == 0)
                    logic.getBoardMatrix(whoCalled)[logic.searchTopOccupiedRow(whoCalled, to)][to]= WRONG_WHITE;
                else logic.getBoardMatrix(whoCalled)[logic.searchTopOccupiedRow(whoCalled, to)][to] = WHITE;
                view.waitForRecall(whoCalled, 0.5);
                break;
            case 10:
                logic.forceMovePawn(whoCalled, to, COL_WHITE);
                view.waitForRecall(whoCalled, 1);
                break;

        }
        internalIndex++;
    }
}
