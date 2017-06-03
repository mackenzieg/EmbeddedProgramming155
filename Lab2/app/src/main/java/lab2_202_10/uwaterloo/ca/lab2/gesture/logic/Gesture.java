package lab2_202_10.uwaterloo.ca.lab2.gesture.logic;

import java.util.Vector;

import lab2_202_10.uwaterloo.ca.lab2.gesture.AccelerometerPoint;

public class Gesture {

    private Vector<AccelerometerPoint> data;

    public Gesture() {
        this.data = new Vector<>();
    }

    public Vector<AccelerometerPoint> getData() {
        return this.data;
    }

    public void addPoint(AccelerometerPoint accelerometerPoint) {
        this.data.add(accelerometerPoint);
    }
}
