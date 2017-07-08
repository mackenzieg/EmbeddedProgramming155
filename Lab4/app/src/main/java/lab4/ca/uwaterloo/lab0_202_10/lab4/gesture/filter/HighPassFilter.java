package lab4.ca.uwaterloo.lab0_202_10.lab4.gesture.filter;

public class HighPassFilter extends Filter {

    private float factor;
    private float[] previous;

    public HighPassFilter() {
        this(0.1f);
    }

    public HighPassFilter(float factor) {
        super();
        this.factor = factor;
        this.reset();
    }

    @Override
    public float[] filterAlgorithm(float[] vector) {
        if (vector == null) {
            return null;
        }
        float[] newVals = new float[3];
        for (int i = 0; i < 3; ++i) {
            previous[i] = vector[i] * this.factor + this.previous[i] * (1.0f - this.factor);
            newVals[i] = vector[i] - previous[i];
        }
        return newVals;
    }

    @Override
    public void reset() {
        this.previous = new float[]{0.0f, 0.0f, 0.0f};
    }
}
