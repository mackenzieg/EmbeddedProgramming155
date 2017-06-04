package lab2_202_10.uwaterloo.ca.lab2.gesture.listener;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.Filter;

public class SensorListener implements SensorEventListener {

    private List<Filter> filters;
    private List<AccelerometerListener> listeners = new ArrayList<>();

    public SensorListener(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        for (Filter filter : filters) {
            values = filter.filter(values);
        }
        for (AccelerometerListener sensorEventListener : listeners) {
            sensorEventListener.sensorUpdate(values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void addListener(AccelerometerListener sensorEventListener) {
        this.listeners.add(sensorEventListener);
    }

    ;
}
