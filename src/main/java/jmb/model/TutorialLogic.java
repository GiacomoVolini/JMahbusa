package jmb.model;

import jmb.model.tutorial.ComparableTutorialStage;
import jmb.model.tutorial.TutorialStage;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static jmb.ConstantsShared.*;
import static jmb.model.Logic.view;
import static org.reflections.scanners.Scanners.*;

public class TutorialLogic extends DynamicBoardLogic {


    private static final String TUTORIAL_STRING_0 = "Benvenuto nel tutorial di JMahbusa!\nIl gioco si svolge su questo tabellone, diviso in 24 zone dette \"punte\"";
    private static final String TUTORIAL_STRING_1 = "Ogni giocatore ha 15 pedine, che attraversano il tabellone lungo le 24 punte in direzioni opposte (come da animazione)";
    private static final String TUTORIAL_STRING_2 = "L'obiettivo del gioco è di spostare tutte le proprie pedine nella propria zona di uscita, come mostrato ora.";
    private static final String TUTORIAL_STRING_3 = "All'inizio di ogni turno vengono tirati due dadi. I risultati dei dadi determinano come si potranno muovere le pedine";
    private static final String TUTORIAL_STRING_4 = "Con questi risultati puoi giocare il tuo turno in due modi:\n\u2022Puoi muovere una pedina di 5 punte e una di 6.\n\u2022Puoi muovere una pedina della somma dei due dadi, ovvero 11 punte.\nProva!";
    private static final String TUTORIAL_STRING_5 = "Ora è il turno dell'avversario.";
    private static final String TUTORIAL_STRING_5_CONCAT = "\nHa ottenuto un risultato doppio! Quando ciò capita, è come se per quel turno il giocatore avesse quattro dadi di quel valore a sua disposizione!";
    private static final String TUTORIAL_STRING_6 = "Stringa 6";
    private static final String TUTORIAL_STRING_7 = "Stringa 7";
    private static final int WELCOME_STAGE = 0;
    private static final int PAWN_DIRECTION_STAGE = 1;
    private static final int GAME_GOAL_STAGE = 2;
    private static final int DICE_PRESENTATION_STAGE = 3;
    private static final int TURN_TRIAL_STAGE = 4;
    private static final int FIRST_OPPONENT_TURN_STAGE = 5;
    private static final int POINT_BLOCKING_EXPLANATION_STAGE = 6;

    private ArrayList<ComparableTutorialStage> stageArrayList;
    private Iterator<ComparableTutorialStage> stageArrayIterator;
    private ComparableTutorialStage currentStage;

    private int tutorialStage = UNDEFINED;
    private int index = 0;
    private int stringIndex = 0;

public TutorialLogic() {
        try {
            setWhoCalled(TUTORIAL_CALLED);
            dice = new DiceLogic();
            squares = new int[16][26];
            stageArrayList = stageArrayListConstructor();
            stageArrayIterator = stageArrayList.iterator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void nextTutorialStage() {
        if(stageArrayIterator.hasNext())
            currentStage = stageArrayIterator.next();
        currentStage.start();
    }
    protected void tutorialStageAction() {
        currentStage.action();
    }
    protected void checkTutorialStageAdvancement() {
        switch (tutorialStage) {
            default:
                break;
            case TURN_TRIAL_STAGE: case FIRST_OPPONENT_TURN_STAGE:
                boolean allUsed = true;
                for (boolean used: dice.getUsedArray())
                    if (!used)
                        allUsed = false;
                if (allUsed)
                    nextTutorialStage();
        }
    }

    private ArrayList<ComparableTutorialStage> stageArrayListConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Reflections reflections = new Reflections("jmb.model.tutorial");
            Set<Class<?>> set = reflections.get(SubTypes.of(ComparableTutorialStage.class).asClass());
            ArrayList<ComparableTutorialStage> out = new ArrayList<>();
            Iterator<Class<?>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Class c = iterator.next();
                System.out.println(c);
                out.add((ComparableTutorialStage) c.getConstructor().newInstance());
            }
            Collections.sort(out);
            System.out.println(out);
            return out;
    }

}
