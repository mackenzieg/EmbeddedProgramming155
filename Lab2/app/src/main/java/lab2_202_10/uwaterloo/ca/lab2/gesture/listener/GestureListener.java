package lab2_202_10.uwaterloo.ca.lab2.gesture.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.List;

import lab2_202_10.uwaterloo.ca.lab2.LineGraphView;
import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.Filter;
import lab2_202_10.uwaterloo.ca.lab2.gesture.logic.Gesture;

public class GestureListener implements AccelerometerListener {

    private static final float SLOPE_THRESHOLD = 3.0f;
    private static final long TIME_DELAY_GESTURE_THESHOLD = 200;

    private LineGraphView filtered;

    private long lastTime = -1;

    public GestureListener(LineGraphView filtered) {
        this.filtered = filtered;
    }

    @Override
    public void sensorUpdate(float[] values) {
        filtered.addPoint(values);
    }
}
