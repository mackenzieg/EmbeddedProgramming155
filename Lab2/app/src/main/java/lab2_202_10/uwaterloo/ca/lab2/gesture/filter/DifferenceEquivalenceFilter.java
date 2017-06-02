package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

/**
 * This filter remove a vector if it doesn't differ enough from the previous
 * retrieved value
 */
public class DifferenceEquivalenceFilter extends Filter {

    private double sensitivity;
    private double[] previous;

    public DifferenceEquivalenceFilter() {
        this(0.2);
    }

    public DifferenceEquivalenceFilter(double sensitivity) {
        super();
        this.sensitivity = sensitivity;
        this.reset();
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    @Override
    public double[] filterAlgorithm(double[] vector) {
        if (vector[0] < previous[0] - this.sensitivity ||
                vector[0] > previous[0] + this.sensitivity ||
                vector[1] < previous[1] - this.sensitivity ||
                vector[1] > previous[1] + this.sensitivity ||
                vector[2] < previous[2] - this.sensitivity ||
                vector[3] > previous[2] + this.sensitivity) {
            this.previous = vector;
            return vector;
        }
        return null;
    }

    @Override
    public void reset() {
        this.previous = new double[] {0.0, 0.0, 0.0};
    }
}