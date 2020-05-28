package sample.model;

import javafx.beans.property.SimpleIntegerProperty;

public class bbRepetition {
    private SimpleIntegerProperty repetitionId;
    private SimpleIntegerProperty reps;
    private SimpleIntegerProperty bandStatId; //id to the corresponding bandStat

    public Integer getRepetitionId() {
        return this.repetitionId.get();
    }

    public void setRepetitionId(Integer repetitionId) {
        this.repetitionId.set(repetitionId);
    }

    public Integer getReps() {
        return this.reps.get();
    }

    public void setReps(Integer reps) {
        this.reps.set(reps);
    }

    public Integer getBandStatId() {
        return this.bandStatId.get();
    }

    public void setBandStatId(Integer bandStatId) {
        this.bandStatId.set(bandStatId);
    }
}
