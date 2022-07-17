package jmb.model.tutorial;

public abstract class ComparableTutorialStage implements TutorialStage, Comparable<ComparableTutorialStage>{

    protected int stageIndex;
    protected int internalIndex = 0;
    @Override
    public int compareTo(ComparableTutorialStage obj) {
    return this.stageIndex - obj.stageIndex;
    }

    protected void setStageIndex(int value) {
        this.stageIndex = value;
    }
}
