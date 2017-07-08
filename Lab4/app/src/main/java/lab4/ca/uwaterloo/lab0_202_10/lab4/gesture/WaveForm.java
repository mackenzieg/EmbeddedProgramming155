package lab4.ca.uwaterloo.lab0_202_10.lab4.gesture;

import java.util.ArrayList;
import java.util.List;

public class WaveForm {

    private List<Float> x;
    private List<Float> y;
    private List<Float> z;

    public WaveForm() {
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
    }

    public WaveForm(List<Float> x, List<Float> y, List<Float> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void addPoint(float x, float y, float z) {
        this.addPoint(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z));
    }

    public void addPoint(Float x, Float y, Float z) {
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
    }

    public float getX(int index) {
        return this.x.get(index);
    }

    public float getY(int index) {
        return this.x.get(index);
    }

    public float getZ(int index) {
        return this.x.get(index);
    }

    public AccelerometerPoint getPoint(int index) {
        return new AccelerometerPoint(this.getX(index), this.getY(index), this.getZ(index));
    }
}
