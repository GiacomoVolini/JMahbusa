package jmb.model.tutorial;

import static jmb.ConstantsShared.COL_BLACK;
import static jmb.ConstantsShared.WHITE;
import static jmb.model.Logic.logic;
import static jmb.model.Logic.view;

public class FirstOpponentTurnStage extends ComparableTutorialStage{
    private String stageString = "Ora è il turno dell'avversario.";
    private String stageStringAfterAction = "Ha ottenuto un risultato doppio! Quando ciò capita, è come se per quel turno il giocatore avesse quattro dadi di quel valore a sua disposizione!";

    public FirstOpponentTurnStage() {
        super();
        setStageIndex(5);
    }
    public void start() {
        logic.setWhiteTurn(whoCalled,false);
        view.tutorialDiceAnimation(true, 20);
        logic.forceDice(whoCalled, 6);
        view.callRedraw(whoCalled);
        view.setNextTutorialString(stageString, true);
        view.tutorialTextBoxAnimation(0.1,0.4 );
    }

    public void action() {
        if (internalIndex == 0) {
            view.setNextTutorialString(stageStringAfterAction,true);
            view.tutorialTextBoxAnimation(0.15,0.2 );
        }
        if (internalIndex < 5)
            view.waitForRecall(whoCalled, 2.0);
        else {
            view.allowTextBoxMouseInput(true);
            logic.nextTutorialStage();
        }
        firstOpponentMoves();
    }

    private void firstOpponentMoves() {
        switch (internalIndex) {
            default:
                break;
            case 1: case 3:
                logic.movePawn(whoCalled,COL_BLACK, 18);
                break;
            case 2:
                logic.movePawn(whoCalled,18, 12);
                break;
            case 4:
                if (logic.getBoardMatrix(whoCalled)[0][12]==WHITE)
                    logic.movePawn(whoCalled,18, 12);
                else logic.movePawn(whoCalled,12, 6);
                break;
        }
        view.callRedraw(whoCalled);
        internalIndex++;
    }
}
