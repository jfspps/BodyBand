package sample.model;

public class bbBandStat {
    private Integer bandStatId;
    private Integer tension;
    private Integer doubledOrNot;
    private String units;

    public Integer getBandStatId() {
        return bandStatId;
    }

    public void setBandStatId(Integer bandStatId) {
        this.bandStatId = bandStatId;
    }

    public Integer getTension() {
        return tension;
    }

    public void setTension(Integer tension) {
        this.tension = tension;
    }

    public Integer getDoubledOrNot() {
        return doubledOrNot;
    }

    public void setDoubledOrNot(Integer doubledOrNot) {
        this.doubledOrNot = doubledOrNot;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
