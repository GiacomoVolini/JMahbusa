package jmb.logic.tutorial;

import static jmb.logic.Logic.*;
import static jmb.ConstantsShared.*;

public class GameGoalStage extends ComparableTutorialStage{

    private String stageString = "gameGoal";
    public GameGoalStage() {
        super();
        setStageIndex(2);
    }

    public void start() {
        logic.setWhiteTurn(whoCalled, false);
        view.tutorialPointAnimation(false);
        logic.setWhiteExit(whoCalled,true);
        logic.setBlackExit(whoCalled,true);
        view.tutorialExitZoneAnimation(true);
        view.setNextTutorialString(logic.getString(stageString), true);
        view.tutorialTextBoxAnimation(0.6,0.3);
    }

    public void action() {
        if (internalIndex < 30) {
            if (internalIndex%2==0)
                logic.forceMovePawn(whoCalled, COL_WHITE, COL_WHITE_EXIT);
            else logic.forceMovePawn(whoCalled, COL_BLACK, COL_BLACK_EXIT);
            internalIndex++;
        }
    }

}
