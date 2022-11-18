package jmb.logic.tutorial;

import static jmb.ConstantsShared.*;
import static jmb.logic.Logic.getLogic;
import static jmb.logic.Logic.getView;

public class GameGoalStage extends ComparableTutorialStage{

    private String stageString = "gameGoal";
    public GameGoalStage() {
        super();
        setStageIndex(2);
    }

    public void start() {
        getLogic().setWhiteTurn(false);
        getView().tutorialPointAnimation(false);
        getLogic().setWhiteExit(true);
        getLogic().setBlackExit(true);
        getView().tutorialExitZoneAnimation(true);
        getView().setNextTutorialString(getLogic().getString(stageString), true);
        getView().tutorialTextBoxAnimation(0.6,0.3);
    }

    public void action() {
        if (internalIndex < 30) {
            if (internalIndex%2==0)
                getLogic().forceMovePawn(COL_WHITE, COL_WHITE_EXIT);
            else getLogic().forceMovePawn(COL_BLACK, COL_BLACK_EXIT);
            internalIndex++;
        }
    }

}
