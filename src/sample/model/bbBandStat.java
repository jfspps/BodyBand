package sample.model;

public class bbBandStat {
    private Integer bandStatId;
    private Integer tension;
    private Boolean doubledOrNot;

    //not implemented by the db since it is assumed users use the same unit of mass but useful for the frontend
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

    public Boolean getDoubledOrNot() {
        return doubledOrNot;
    }

    public void setDoubledOrNot(Boolean doubledOrNot) {
        this.doubledOrNot = doubledOrNot;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
