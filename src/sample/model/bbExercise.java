package sample.model;

public class bbExercise {
    private Integer exerciseId;
    private String exerciseName;
    private String anchorNeeded;
    private String anchorHeight;
    private String anchorPosition;
    private String exerciseDesc;
    private String videoURL;

    public bbExercise(String exerciseName) {
        this.exerciseName = exerciseName;
        this.anchorNeeded = "";
        this.anchorHeight = "";
        this.anchorPosition = "";
        this.exerciseDesc = "";
        this.videoURL = "";
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getAnchorNeeded() {
        return anchorNeeded;
    }

    public void setAnchorNeeded(String anchorNeeded) {
        this.anchorNeeded = anchorNeeded;
    }

    public String getAnchorHeight() {
        return anchorHeight;
    }

    public void setAnchorHeight(String anchorHeight) {
        this.anchorHeight = anchorHeight;
    }

    public String getAnchorPosition() {
        return anchorPosition;
    }

    public void setAnchorPosition(String anchorPosition) {
        this.anchorPosition = anchorPosition;
    }

    public String getExerciseDesc() {
        return exerciseDesc;
    }

    public void setExerciseDesc(String exerciseDesc) {
        this.exerciseDesc = exerciseDesc;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
