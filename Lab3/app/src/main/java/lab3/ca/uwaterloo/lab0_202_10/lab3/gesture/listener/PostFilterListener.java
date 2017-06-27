package lab3.ca.uwaterloo.lab0_202_10.lab3.gesture.listener;

import lab3.ca.uwaterloo.lab0_202_10.lab3.LineGraphView;
import lab3.ca.uwaterloo.lab0_202_10.lab3.gesture.Gesture;
import lab3.ca.uwaterloo.lab0_202_10.lab3.gesture.logic.GestureManager;
import lab3.ca.uwaterloo.lab0_202_10.lab3.util.Vector;

public class PostFilterListener implements AccelerometerListener {

    private static final float SLOPE_THRESHOLD = 3.8f;
    // 100 ms+
    private static final long TIME_THRESHOLD = 100000000;

    private GestureManager gestureManager;

    private long triggeredTime = Long.MAX_VALUE;

    private boolean triggered = false;

    private Gesture gesture;

    public PostFilterListener(GestureManager gestureManager) {
        this.gestureManager = gestureManager;
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

        if (triggered) {
            gesture.addPoint(values);
        }

        triggeredTime = currentTime;
    }
}
