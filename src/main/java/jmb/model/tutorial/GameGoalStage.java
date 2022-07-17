package jmb.model.tutorial;

import static jmb.model.Logic.*;
import static jmb.ConstantsShared.*;

public class GameGoalStage extends ComparableTutorialStage{

    private String stageString = "L'obiettivo del gioco è di spostare tutte le proprie pedine nella propria zona di uscita, o \"portarle fuori\", come mostrato ora.";
    public GameGoalStage() {
        super();
        setStageIndex(2);
    }

    public void start() {
        view.tutorialPointAnimation(false);
        logic.setWhiteExit(whoCalled,true);
        logic.setBlackExit(whoCalled,true);
        view.tutorialExitZoneAnimation(true);
        view.setNextTutorialString(stageString, true);
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
