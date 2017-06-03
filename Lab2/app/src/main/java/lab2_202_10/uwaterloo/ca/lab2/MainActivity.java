package lab2_202_10.uwaterloo.ca.lab2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.BroadBandpassFilter;
import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.DifferenceEquivalenceFilter;
import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.Filter;
import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.HighPassFilter;
import lab2_202_10.uwaterloo.ca.lab2.gesture.filter.LowPassFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout view = (LinearLayout) findViewById(R.id.main_layout);

        final LineGraphView lineGraphView = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));


        final LineGraphView
                anotherLineGraphView = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));

        view.addView(lineGraphView);
        view.addView(anotherLineGraphView);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        final List<Filter> filters = new ArrayList<>();
        filters.add(new LowPassFilter());
        filters.add(new HighPassFilter());
        filters.add(new DifferenceEquivalenceFilter());

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] newVals = event.values;
                lineGraphView.addPoint(event.values);
                for (Filter filter : filters) {
                    newVals = filter.filter(newVals);
                }
                if (newVals == null) {
                    return;
                }
                anotherLineGraphView.addPoint(newVals);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }
}
