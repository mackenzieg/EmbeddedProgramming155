package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public class LowPassFilter extends Filter {

    private double factor;
    private double[] previous;

    public LowPassFilter() {
        this(0.1);
    }

    public LowPassFilter(double factor) {
        super();
        this.factor = factor;
        this.reset();
    }

    @Override
    public double[] filterAlgorithm(double[] vector) {
        double[] newVals = new double[3];
        for (int i = 0; i < 3; ++i) {
            previous[i] = vector[i] * this.factor + this.previous[i] * (1.0 - this.factor);
        }
        this.previous = newVals;
        return newVals;
    }

    @Override
    public void reset() {
        this.previous = new double[] {0.0, 0.0, 0.0};
    }
}
