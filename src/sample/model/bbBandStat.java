package sample.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class bbBandStat {
    private SimpleIntegerProperty bandStatId;

    //Java float less precise with space-saving benefits, compared to double
    private SimpleFloatProperty tension;

    // remains on the DB but is not always used
    private SimpleStringProperty doubledOrNot;
    private SimpleStringProperty units;

    public Integer getBandStatId() {
        return this.bandStatId.get();
    }

    public void setBandStatId(Integer bandStatId) {
        this.bandStatId.set(bandStatId);
    }

    public float getTension() {
        return this.tension.get();
    }

    public void setTension(Float tension) {
        this.tension.set(tension);
    }

    public String getDoubledOrNot() {
        return this.doubledOrNot.get();
    }

    public void setDoubledOrNot(String doubledOrNot) {
        this.doubledOrNot.set(doubledOrNot);
    }

    public String getUnits() { return this.units.get(); }

    public void setUnits(String units) {
        this.units.set(units);
    }
}
