package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class bbExercise {
    private SimpleIntegerProperty exerciseId;
    private SimpleStringProperty exerciseName;
    private SimpleStringProperty muscleGroup;
    private SimpleStringProperty anchorPosition;
    private SimpleStringProperty exerciseDesc;
    private SimpleStringProperty videoURL;

    public bbExercise() {
        this.exerciseId = new SimpleIntegerProperty();
        this.exerciseName = new SimpleStringProperty();
        this.muscleGroup = new SimpleStringProperty();
        this.anchorPosition = new SimpleStringProperty();
        this.exerciseDesc = new SimpleStringProperty();
        this.videoURL = new SimpleStringProperty();
    }

    //implement data-binding by implementing Simple_Property (this does not affect what exerciseDB sets/requests)

    public int getExerciseId() {
        return this.exerciseId.get();
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId.set(exerciseId);
    }

    public String getExerciseName() {
        return this.exerciseName.get();
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName.set(exerciseName);
    }

    public String getMuscleGroup() {
        return this.muscleGroup.get();
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup.set(muscleGroup);
    }

    public String getAnchorPosition() {
        return this.anchorPosition.get();
    }

    public void setAnchorPosition(String anchorPosition) {
        this.anchorPosition.set(anchorPosition);
    }

    public String getExerciseDesc() {
        return this.exerciseDesc.get();
    }

    public void setExerciseDesc(String exerciseDesc) {
        this.exerciseDesc.set(exerciseDesc);
    }

    public String getVideoURL() {
        return this.videoURL.get();
    }

    public void setVideoURL(String videoURL) {
        this.videoURL.set(videoURL);
    }
}
