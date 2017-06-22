package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wtw.BuiltDevice;
import com.wtw.Device;
import com.wtw.compression.MeanCompressor;
import com.wtw.detectors.DefaultGestureDetector;
import com.wtw.distance.AbsoluteDistance;
import com.wtw.distance.EuclideanDistance;
import com.wtw.event.Event;
import com.wtw.event.EventHandler;
import com.wtw.filters.DifferenceEquivalenceFilter;
import com.wtw.filters.LowPassFilter;
import com.wtw.timeseries.TimeSeries;
import com.wtw.timewarp.SlowTimeWarpCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lab3.ca.uwaterloo.lab0_202_10.lab3.events.DetectedGestureEvent;

public class MainActivity extends AppCompatActivity {

    private final List<TimeSeries> recordedGestures = new ArrayList<>();

    private GestureManager gestureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout view = (LinearLayout) findViewById(R.id.main_layout);

        final LineGraphView
                anotherLineGraphView = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));

        final TextView textView = new TextView(this);

        view.addView(textView);
        view.addView(anotherLineGraphView);

        textView.setText("Nothing yet");

        textView.setTextColor(Color.WHITE);

        final BuiltDevice builtDevice = new Device()
                .addCompressor(new MeanCompressor())
                .addFilter(new LowPassFilter(3))
                .addFilter(new DifferenceEquivalenceFilter(3))
                .setGestureDetector(new DefaultGestureDetector(new EuclideanDistance()) {
                    @Override
                    public void newMeasurement(float[] values, long time) {
                        super.newMeasurement(values, time);
                        anotherLineGraphView.addPoint(values);
                    }
                })
                .registerListener(new Event() {
                    @EventHandler
                    public void detectedGesture(DetectedGestureEvent detectedGestureEvent) {
                        textView.setText(detectedGestureEvent.getGesture().getName());
                    }
                })
                .setTimeWarpCalculator(new SlowTimeWarpCalculator(new AbsoluteDistance()))
                .build().setStartCompression(true);

        gestureManager = new GestureManager(builtDevice);


        SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                textView.setText("Here now");
                builtDevice.newMeasurement(event.values, System.nanoTime());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        },  sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST);
    }
}
