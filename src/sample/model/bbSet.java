package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class bbSet {
    private SimpleIntegerProperty setId;
    private SimpleIntegerProperty exerciseId;
    private SimpleIntegerProperty repetitionId;
    private SimpleStringProperty comments;
    private SimpleStringProperty setDate;

    public Integer getSetId() {
        return this.setId.get();
    }

    public void setSetId(Integer setId) {
        this.setId.set(setId);
    }

    public Integer getExerciseId() {
        return this.exerciseId.get();
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId.set(exerciseId);
    }

    public Integer getRepetitionId() { return this.repetitionId.get(); }

    public void setRepetitionId(Integer repetitionId) {
        this.repetitionId.set(repetitionId);
    }

    public String getComments() {
        return this.comments.get();
    }

    public void setComments(String comments) {
        this.comments.set(comments);
    }

    public String getSetDate() {
        return this.setDate.get();
    }

    public void setSetDate(String setDate) {
        this.setDate.set(setDate);
    }
}
