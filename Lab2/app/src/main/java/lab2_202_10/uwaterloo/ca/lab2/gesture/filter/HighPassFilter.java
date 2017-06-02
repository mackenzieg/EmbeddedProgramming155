package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public class HighPassFilter extends Filter {

    private double factor;
    private double[] previous;

    public HighPassFilter() {
        this(0.1);
    }

    public HighPassFilter(double factor) {
        super();
        this.factor = factor;
        this.reset();
    }

    @Override
    public double[] filterAlgorithm(double[] vector) {
        double[] newVals = new double[3];
        for (int i = 0; i < 3; ++i) {
            previous[i] = vector[i] * this.factor + this.previous[i] * (1.0 - this.factor);
            newVals[i] = vector[i] - previous[i];
        }
        return newVals;
    }

    @Override
    public void reset() {
        this.previous = new double[] {0.0, 0.0, 0.0};
    }
}
