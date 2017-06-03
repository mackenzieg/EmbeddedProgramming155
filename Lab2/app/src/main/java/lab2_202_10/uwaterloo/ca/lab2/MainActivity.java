package lab2_202_10.uwaterloo.ca.lab2;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lab2_202_10.uwaterloo.ca.lab2.gesture.listener.GestureListener;
import lab2_202_10.uwaterloo.ca.lab2.gesture.listener.PostFilterSensorListener;
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

        TextView textView = new TextView(this);

        textView.setTextColor(Color.WHITE);


        final LineGraphView
                anotherLineGraphView = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));

        view.addView(anotherLineGraphView);
        view.addView(textView);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        final List<Filter> filters = new ArrayList<>();
        filters.add(new LowPassFilter());
        filters.add(new HighPassFilter());
        filters.add(new DifferenceEquivalenceFilter());

        final long lastTime = -1;

        GestureListener gestureListener = new GestureListener(anotherLineGraphView);

        PostFilterSensorListener postFilter = new PostFilterSensorListener(filters);
        postFilter.addListener(gestureListener);

        sensorManager.registerListener(postFilter,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }
}
