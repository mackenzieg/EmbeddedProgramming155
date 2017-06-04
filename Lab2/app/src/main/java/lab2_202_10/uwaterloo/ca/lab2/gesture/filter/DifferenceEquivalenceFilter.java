package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

/**
 * This filter remove a vector if it doesn't differ enough from the previous
 * retrieved value
 */
public class DifferenceEquivalenceFilter extends Filter {

    private double sensitivity;
    private float[] previous;

    public DifferenceEquivalenceFilter() {
        this(0.2f);
    }

    public DifferenceEquivalenceFilter(double sensitivity) {
        super();
        this.sensitivity = sensitivity;
        this.reset();
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    @Override
    public float[] filterAlgorithm(float[] vector) {
        if (vector == null) {
            return null;
        }
        if (vector[0] < previous[0] - this.sensitivity ||
                vector[0] > previous[0] + this.sensitivity ||
                vector[1] < previous[1] - this.sensitivity ||
                vector[1] > previous[1] + this.sensitivity ||
                vector[2] < previous[2] - this.sensitivity ||
                vector[2] > previous[2] + this.sensitivity) {
            this.previous = vector;
            return vector;
        }
        return null;
    }

    @Override
    public void reset() {
        this.previous = new float[]{0.0f, 0.0f, 0.0f};
    }
}