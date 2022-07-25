package jmb.logic;

import jmb.logic.tutorial.ComparableTutorialStage;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static jmb.ConstantsShared.*;
import static org.reflections.scanners.Scanners.*;

public class TutorialLogic extends DynamicBoardLogic {
    private Iterator<ComparableTutorialStage> stageArrayIterator;
    private ComparableTutorialStage currentStage;
    public TutorialLogic() {
        try {
            setWhoCalled(TUTORIAL_CALLED);
            dice = new DiceLogic();
            squares = new int[16][26];
            ArrayList<ComparableTutorialStage> stageArrayList = stageArrayListConstructor();
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

    private ArrayList<ComparableTutorialStage> stageArrayListConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Reflections reflections = new Reflections("jmb.logic.tutorial");
            Set<Class<?>> set = reflections.get(SubTypes.of(ComparableTutorialStage.class).asClass());
            ArrayList<ComparableTutorialStage> out = new ArrayList<>();
            for (Class c : set) {
                out.add((ComparableTutorialStage) c.getConstructor().newInstance());
            }
            Collections.sort(out);
            return out;
    }

}
