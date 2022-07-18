package jmb.model.tutorial;

import jmb.model.Logic;

import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class PawnDirectionStage extends ComparableTutorialStage{
    private String stageString = "Ogni giocatore ha 15 pedine, che attraversano il tabellone lungo le 24 punte in direzioni opposte (come da animazione)";

    public PawnDirectionStage() {
        super();
        setStageIndex(1);
    }

    public void start() {
        logic.setUpNewBoard(whoCalled);
        view.setPawnsVisible(true, whoCalled);
        view.callRedraw(whoCalled);
        view.setNextTutorialString(stageString, true);
        view.playPawnSFX();
        view.waitForRecall(whoCalled, 0.5);
        view.tutorialTextBoxAnimation(0.4,0.4);
    }

    public void action() {
        view.tutorialPointAnimation(true);
    }
}
