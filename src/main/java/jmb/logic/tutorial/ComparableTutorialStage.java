package jmb.logic.tutorial;

public abstract class ComparableTutorialStage implements Comparable<ComparableTutorialStage>{

    protected int stageIndex;
    protected int internalIndex = 0;
    public int compareTo(ComparableTutorialStage obj) {
    return this.stageIndex - obj.stageIndex;
    }

    protected void setStageIndex(int value) {
        this.stageIndex = value;
    }

    public abstract void start();
    public abstract void action();
}
