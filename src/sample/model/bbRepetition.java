package sample.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class bbRepetition {
    private SimpleIntegerProperty repetitionId;
    private SimpleIntegerProperty reps;
    private SimpleFloatProperty tension;

    public bbRepetition() {
        this.repetitionId = new SimpleIntegerProperty();
        this.reps = new SimpleIntegerProperty();
        this.tension = new SimpleFloatProperty();
    }

    public int getRepetitionId() {
        return this.repetitionId.get();
    }

    public void setRepetitionId(int repetitionId) {
        this.repetitionId.set(repetitionId);
    }

    public int getReps() {
        return this.reps.get();
    }

    public void setReps(int reps) {
        this.reps.set(reps);
    }

    public Float getTension() {
        return this.tension.get();
    }

    public void setTension(Float tension) {
        this.tension.set(tension);
    }
}
