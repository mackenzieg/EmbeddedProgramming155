package lab3.ca.uwaterloo.lab0_202_10.lab3.gesture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Gesture {

    private List<Float> x, y, z;

    public Gesture() {
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
    }

    public void setX(List<Float> x) {
        this.x = x;
    }

    public void setY(List<Float> y) {
        this.y = y;
    }

    public void setZ(List<Float> z) {
        this.z = z;
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

    public void writeToFile(File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println(this.toString());
        printWriter.flush();
        printWriter.close();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name:Unknown-length:");
        stringBuilder.append(x.size());
        stringBuilder.append("-points:");
        for (int i = 0; i < x.size(); ++i) {
            stringBuilder.append(String.format("(%f,%f,%f)", x.get(i), y.get(i), z.get(i)));
        }
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

}
