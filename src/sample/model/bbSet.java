package sample.model;

public class bbSet {
    private Integer setId;
    private Integer exerciseId;
    private Integer repetitionId;
    private String comments;
    private String workoutDate;

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getRepetitionId() {
        return repetitionId;
    }

    public void setRepetitionId(Integer repetitionId) {
        this.repetitionId = repetitionId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }
}
