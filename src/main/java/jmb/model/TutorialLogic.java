package jmb.model;

import static jmb.ConstantsShared.*;
import static jmb.model.Logic.view;

public class TutorialLogic extends DynamicBoardLogic {


    private static final String TUTORIAL_STRING_0 = "Benvenuto nel tutorial di JMahbusa!\nIl gioco si svolge su questo tabellone, diviso in 24 zone dette \"punte\"";
    private static final String TUTORIAL_STRING_1 = "Ogni giocatore ha 15 pedine, che attraversano il tabellone lungo le 24 punte in direzioni opposte (come da animazione)";
    private static final String TUTORIAL_STRING_2 = "L'obiettivo del gioco è di spostare tutte le proprie pedine nella propria zona di uscita, come mostrato ora.";
    private static final String TUTORIAL_STRING_3 = "All'inizio di ogni turno vengono tirati due dadi. I risultati dei dadi determinano come si potranno muovere le pedine";
    private static final String TUTORIAL_STRING_4 = "Con questi risultati puoi giocare il tuo turno in due modi:\n\u2022Puoi muovere una pedina di 5 punte e una di 6.\n\u2022Puoi muovere una pedina della somma dei due dadi, ovvero 11 punte.\nProva!";
    private static final String TUTORIAL_STRING_5 = "Stringa 5";
    private static final String TUTORIAL_STRING_6 = "Stringa 6";
    private static final String TUTORIAL_STRING_7 = "Stringa 7";
    private int tutorialStage = UNDEFINED;
    private int index = 0;
    private int stringIndex = 0;

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
        this.setTutorialStage(++tutorialStage);
    }
    private void sendNextTutorialString() {
        if (stringIndex < tutorialStrings.length)
            view.setNextTutorialString(tutorialStrings[stringIndex++]);
    }
    private void setTutorialStage(int stage) {
        System.out.println("Ho ricevuto chiamata di setTutorialStage");
        switch (stage) {
            default:
                view.setTutorialOver();
                break;
            case 0:
                sendNextTutorialString();
                break;
            case 1:
                this.setUp();
                view.setPawnsVisible(true, whoCalled);
                view.tutorialPointAnimation(true);
                sendNextTutorialString();
                break;
            case 2:
                view.tutorialPointAnimation(false);
                setWhiteExit(true);
                setBlackExit(true);
                view.tutorialExitZoneAnimation(true);
                sendNextTutorialString();
                break;
            case 3:
                setUp();
                view.tutorialExitZoneAnimation(false);
                view.tutorialDiceAnimation(true);
                sendNextTutorialString();
                break;
            case 4:
                view.allowTextBoxMouseInput(false); //TODO NON FUNZIONA
                view.tutorialDiceAnimation(false);
                dice.forceDice(6, 5);
                view.callRedraw(whoCalled);
                sendNextTutorialString();
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
