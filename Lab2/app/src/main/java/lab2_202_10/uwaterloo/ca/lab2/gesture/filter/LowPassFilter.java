package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public class LowPassFilter extends Filter {

    private float factor;
    private float[] previous;

    public LowPassFilter() {
        this(0.1f);
    }

    public LowPassFilter(float factor) {
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
        }
        newVals = this.previous;
        return newVals;
    }

    @Override
    public void reset() {
        this.previous = new float[]{0.0f, 0.0f, 0.0f};
    }
}
