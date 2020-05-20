package sample.model;

public class bbSet {
    private Integer setId;
    private bbExercise exercise;
    private bbRepetiton repetitions;
    private String comments;
    private String workoutDate;

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public bbExercise getExercise() {
        return exercise;
    }

    public void setExercise(bbExercise exercise) {
        this.exercise = exercise;
    }

    public bbRepetiton getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(bbRepetiton repetitions) {
        this.repetitions = repetitions;
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
