package lab1.ca.uwaterloo.lab0_202_10.lab1;


public class Point3f {

    public float x, y, z;

    public Point3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3f(float[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }
}
