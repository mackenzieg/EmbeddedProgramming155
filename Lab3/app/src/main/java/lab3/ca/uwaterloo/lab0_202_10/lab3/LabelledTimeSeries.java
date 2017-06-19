package lab3.ca.uwaterloo.lab0_202_10.lab3;

import com.wtw.timeseries.TimeSeries;

import lab3.ca.uwaterloo.lab0_202_10.lab3.gesture.Gestures;

public class LabelledTimeSeries extends TimeSeries {

    private Gestures type;

    public LabelledTimeSeries(Gestures type) {
        this.type = type;
    }

    public LabelledTimeSeries(TimeSeries other, Gestures type) {
        super(other);
        this.type = type;
    }

    public Gestures getType() {
        return type;
    }

    public void setType(Gestures type) {
        this.type = type;
    }
}
