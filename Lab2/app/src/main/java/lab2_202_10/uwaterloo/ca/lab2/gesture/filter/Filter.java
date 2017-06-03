package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public abstract class Filter {

    public float[] filter(float[] vector) {
        if (vector == null) {
            return null;
        }
        return filterAlgorithm(vector);
    }

    abstract public float[] filterAlgorithm(float[] vector);

    abstract public void reset();

}
