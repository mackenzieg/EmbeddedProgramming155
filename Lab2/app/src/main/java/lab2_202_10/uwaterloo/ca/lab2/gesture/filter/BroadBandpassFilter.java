package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public class BroadBandpassFilter extends Filter {

    private double upper, lower;

    public BroadBandpassFilter() {
        this(0.5f, 0.5f);
    }

    public BroadBandpassFilter(float upper, float lower) {
        super();
        this.upper = upper;
        this.lower = lower;
    }

    @Override
    public float[] filterAlgorithm(float[] vector) {
        if (vector == null) {
            return null;
        }
        for (int i = 0; i < vector.length; ++i) {
            if (vector[i] > this.upper || vector[i] < this.lower) {
                return vector;
            }
        }
        return null;
    }

    @Override
    public void reset() {

    }
}
