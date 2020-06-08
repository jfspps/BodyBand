package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class bbSet {
    private SimpleIntegerProperty setId;
    private SimpleIntegerProperty exerciseId;
    private SimpleStringProperty comments;
    private SimpleStringProperty setDate;
    private SimpleStringProperty repIdSequence;

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

    // repIdSequence is a string of integers, separated by an underscore _ each denoting an repId for the given set

    public String getRepIdSequence() { return this.repIdSequence.get(); }

    public void setRepIdSequence(String repIdSequence) {
        this.repIdSequence.set(repIdSequence);
    }
}
