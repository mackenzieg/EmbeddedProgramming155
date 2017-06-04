package lab2_202_10.uwaterloo.ca.lab2.gesture;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lab2_202_10.uwaterloo.ca.lab2.gesture.AccelerometerPoint;

public class Gesture {

    private List<Float> x, y, z;

    public Gesture() {
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
    }

    public List<Float> getX() {
        return this.x;
    }

    public List<Float> getY() {
        return this.y;
    }

    public List<Float> getZ() {
        return this.z;
    }

    public void addPoint(float[] values) {
        this.x.add(values[0]);
        this.y.add(values[1]);
        this.z.add(values[2]);
    }
}
