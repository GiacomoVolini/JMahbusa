package jmb.model;

import static jmb.ConstantsShared.*;
import static jmb.model.Logic.view;

public class TutorialLogic extends DynamicBoardLogic {


    private static final String TUTORIAL_STRING_0 = "Benvenuto nel tutorial di JMahbusa!\nIl gioco si svolge su questo tabellone, diviso in 24 zone dette \"punte\"";
    private static final String TUTORIAL_STRING_1 = "Ogni giocatore ha 15 pedine, che attraversano il tabellone lungo le 24 punte in direzioni opposte (come da animazione)";
    private static final String TUTORIAL_STRING_2 = "L'obiettivo del gioco è di spostare tutte le proprie pedine nella propria zona di uscita, come mostrato ora.";
    private static final String TUTORIAL_STRING_3 = "Stringa 3";
    private static final String TUTORIAL_STRING_4 = "Stringa 4";
    private static final String TUTORIAL_STRING_5 = "Stringa 5";
    private static final String TUTORIAL_STRING_6 = "Stringa 6";
    private static final String TUTORIAL_STRING_7 = "Stringa 7";
    private int tutorialStage = UNDEFINED;
    private int index = 0;

    private String[] tutorialStrings = new String[] {TUTORIAL_STRING_0, TUTORIAL_STRING_1, TUTORIAL_STRING_2, TUTORIAL_STRING_3,
                                                        TUTORIAL_STRING_4, TUTORIAL_STRING_5, TUTORIAL_STRING_6, TUTORIAL_STRING_7};
    public TutorialLogic() {
        setWhoCalled(TUTORIAL_CALLED);
        dice = new DiceLogic();
        squares = new int[16][26];
    }
    protected String getNextTutorialString() {
        String out;
        if (tutorialStage < tutorialStrings.length) {
            out = tutorialStrings[tutorialStage];
            tutorialStage++;
        } else out = ARRAY_ENDED;
        return out;
    }
    protected void nextTutorialStage() {
        if (tutorialStage < tutorialStrings.length) {
            tutorialStage++;
            view.setNextTutorialString(tutorialStrings[tutorialStage]);
            this.setTutorialStage(tutorialStage);
        } else {
            view.setTutorialOver();
        }
    }
    private void setTutorialStage(int stage) {
        System.out.println("Ho ricevuto chiamata di setTutorialStage");
        switch (stage) {
            default:
                System.out.println("Faccio niente");
                break;
            case 1:
                this.setUp();
                view.setPawnsVisible(true, whoCalled);
                view.tutorialPointAnimation(true);
                break;
            case 2:
                view.tutorialPointAnimation(false);
                setWhiteExit(true);
                setBlackExit(true);
                view.tutorialExitZoneAnimation(true);
                break;
            case 3:
                setUp();
                view.tutorialExitZoneAnimation(false);
                break;
        }
    }

    protected void tutorialStageAction() {
        switch (tutorialStage) {
            default:
                break;
            case 2:
                if (index < 30) {
                    if (index%2==0)
                        forceMovePawn(COL_WHITE, COL_WHITE_EXIT);
                    else forceMovePawn(COL_BLACK, COL_BLACK_EXIT);
                    index++;
                }
                break;
        }
    }

}
