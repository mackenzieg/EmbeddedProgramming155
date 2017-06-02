package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public abstract class Filter {

    public double[] filter(double[] vector) {
        if (vector == null) {
            return null;
        }
        return filterAlgorithm(vector);
    }

    abstract public double[] filterAlgorithm(double[] vector);

    abstract public void reset();

}
