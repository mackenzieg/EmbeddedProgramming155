package lab2_202_10.uwaterloo.ca.lab2.gesture.filter;

public class LowPassFilter extends Filter {

    private float[] previous;

    private final double TIME_CONSTANT = 0.10;
    private final int COUNT_BEFORE_UPDATE = 1;
    private double alpha = 0.95;
    private double deltaTime = 0.0;

    private double timeStamp = System.nanoTime();
    private double startTime = 0.0;

    private int count = 0;

    public LowPassFilter() {
        super();
        this.reset();
    }

    // Integrate over field time
    @Override
    public float[] filterAlgorithm(float[] vector) {
        // Reset time to current time
        if (startTime == 0.0) {
            startTime = System.nanoTime();
        }

        timeStamp = System.nanoTime();
        deltaTime = 1.0 / (count++ / ((timeStamp - startTime) / 1000000000.0));
        alpha = TIME_CONSTANT / (TIME_CONSTANT + deltaTime);

        // Integrate vector and set to previous
        if (count > COUNT_BEFORE_UPDATE)
        {
            previous[0] = (float)(alpha * previous[0] + (1 - alpha) * vector[0]);
            previous[1] = (float)(alpha * previous[1] + (1 - alpha) * vector[1]);
            previous[2] = (float)(alpha * previous[2] + (1 - alpha) * vector[2]);
        }

        return previous;
    }

    // reseset previous vectors
    @Override
    public void reset() {
        this.previous = new float[]{0.0f, 0.0f, 0.0f};
    }
}