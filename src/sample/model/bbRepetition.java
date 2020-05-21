package sample.model;

public class bbRepetition {
    private Integer repetitionId;
    private Integer reps;
    private Integer bandStatId; //id to the corresponding bandStat

    public Integer getRepetitionId() {
        return repetitionId;
    }

    public void setRepetitionId(Integer repetitionId) {
        this.repetitionId = repetitionId;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getBandStatId() {
        return bandStatId;
    }

    public void setBandStatId(Integer bandStatId) {
        this.bandStatId = bandStatId;
    }
}
