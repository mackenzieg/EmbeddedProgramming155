package lab2_202_10.uwaterloo.ca.lab2.gesture.listener;

import lab2_202_10.uwaterloo.ca.lab2.LineGraphView;
import lab2_202_10.uwaterloo.ca.lab2.gesture.Gesture;
import lab2_202_10.uwaterloo.ca.lab2.gesture.logic.GestureManager;
import lab2_202_10.uwaterloo.ca.lab2.util.Vector;

public class PostFilterListener implements AccelerometerListener {

    private static final float SLOPE_THRESHOLD = 3.8f;
    // 100 ms+
    private static final long TIME_THRESHOLD = 100000000;

    private GestureManager gestureManager;

    private LineGraphView filtered;

    private LineGraphView lengthGraph;

    private long triggeredTime = Long.MAX_VALUE;

    private boolean triggered = false;

    private Gesture gesture;

    public PostFilterListener(GestureManager gestureManager, LineGraphView filtered, LineGraphView lengthGraph) {
        this.gestureManager = gestureManager;
        this.filtered = filtered;
        this.lengthGraph = lengthGraph;
    }

    @Override
    public void sensorUpdate(float[] values) {
        long currentTime = System.nanoTime();

        if (values == null) {
            if (triggeredTime + TIME_THRESHOLD < currentTime && triggered){
                triggered = false;
                gestureManager.newGesture(this.gesture);
            }
            return;
        }

        float length = Vector.length(values[0], values[1], values[2]);

        if (length > SLOPE_THRESHOLD && !triggered) {
            triggered = true;
            gesture = new Gesture();
        }

        boolean transition = false;

        if (triggeredTime + TIME_THRESHOLD < currentTime && length < SLOPE_THRESHOLD && this.triggered) {
            triggered = false;
            transition = true;
            gestureManager.newGesture(this.gesture);
        }

        if (transition) {
            filtered.purge();
        }

        if (triggered) {
            filtered.addPoint(values);
            gesture.addPoint(values);
            lengthGraph.addPoint(new float[] {length});
        }

        triggeredTime = currentTime;
    }
}
