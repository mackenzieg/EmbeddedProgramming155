package lab1.ca.uwaterloo.lab0_202_10.lab1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

import java.util.Arrays;

public class AccelerometerEventListener implements SensorEventListener {

    private LineGraphView lineGraphView;

    AccelerometerEventListener(LineGraphView output) {
        this.lineGraphView = output;
        this.lineGraphView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            lineGraphView.addPoint(event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}