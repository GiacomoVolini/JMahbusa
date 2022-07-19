package jmb.model.tutorial;

import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class TurnTrialStage extends ComparableTutorialStage{
    private String stageString = "Con questi risultati puoi giocare il tuo turno in due modi:\n\u2022Puoi muovere una pedina di 5 punte e una di 6.\n\u2022Puoi muovere una pedina della somma dei due dadi, ovvero 11 punte.\nProva!";

    public TurnTrialStage() {
        super();
        setStageIndex(4);
    }
    public void start() {
        logic.setWhiteTurn(whoCalled, true);
        view.allowTextBoxMouseInput(false);
        view.tutorialDiceAnimation(false);
        logic.forceDice(whoCalled, 5, 6);
        view.callRedraw(whoCalled);
        view.setNextTutorialString(stageString, true);
        view.tutorialTextBoxAnimation(0.4,0.55);
    }
    public void action() {
        boolean allUsed = true;
        for (boolean used: logic.getUsedArray(whoCalled))
            if (!used)
                allUsed = false;
        if (allUsed) {
            logic.nextTutorialStage();
            view.restoreBoardColors(whoCalled);
        }
    }
}
