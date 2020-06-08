package sample.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class bbRepetition {
    private SimpleIntegerProperty repetitionId;
    private SimpleIntegerProperty reps;
    private SimpleFloatProperty tension;

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

    public Float getTension() {
        return this.tension.get();
    }

    public void setTension(Float tension) {
        this.tension.set(tension);
    }
}
