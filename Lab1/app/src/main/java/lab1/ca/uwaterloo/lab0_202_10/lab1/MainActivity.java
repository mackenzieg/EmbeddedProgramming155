package lab1.ca.uwaterloo.lab0_202_10.lab1;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.AccelerometerEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.GyroEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.LightSensorEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.MagnoEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout view = (LinearLayout) findViewById(R.id.main_layout);
        /*
         * Create graph view
         */
        LineGraphView lineGraphView = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));
        view.addView(lineGraphView);

        /*
         * Create all the views for sensors and labels
         */
        TextView accelerometerLabel     = new TextView(getApplicationContext());
        TextView accelerometerDisplay   = new TextView(getApplicationContext());
        TextView accelerometerMax       = new TextView(getApplicationContext());
        TextView magnoLabel             = new TextView(getApplicationContext());
        TextView magnoDisplay           = new TextView(getApplicationContext());
        TextView magnoMax               = new TextView(getApplicationContext());
        TextView gyroLabel              = new TextView(getApplicationContext());
        TextView gyroDisplay            = new TextView(getApplicationContext());
        TextView gyroMax                = new TextView(getApplicationContext());
        TextView lightLabel             = new TextView(getApplicationContext());
        TextView lightSensorDisplay     = new TextView(getApplicationContext());
        TextView lightSensorMax         = new TextView(getApplicationContext());

        /*
         * Generate array of views for easier iteration
         */
        TextView[] textViews = new TextView [] {
            accelerometerLabel,
            accelerometerDisplay,
            accelerometerMax,
            magnoLabel,
            magnoDisplay,
            magnoMax,
            gyroLabel,
            gyroDisplay,
            gyroMax,
            lightLabel,
            lightSensorDisplay,
            lightSensorMax
        };

        /*
         * Set text for labels
         */
        accelerometerLabel.setText("Accelerometer Data");
        magnoLabel.setText("\nMagnometer Data");
        gyroLabel.setText("\nGyroscope Data");
        lightLabel.setText("\nLight Sensor Data");

        /*
         * Create event listeners for accelerometer, magnometer, gyroscope and light sensor
         */
        final AccelerometerEventListener accelerometerEventListener = new AccelerometerEventListener(lineGraphView,
                accelerometerDisplay, accelerometerMax);
        final MagnoEventListener magnoEventListener = new MagnoEventListener(magnoDisplay,
                magnoMax);
        final GyroEventListener gyroEventListener = new GyroEventListener(gyroDisplay,
                gyroMax);
        final LightSensorEventListener lightSensorEventListener = new LightSensorEventListener(lightSensorDisplay,
                lightSensorMax);

        /*
         * Create button for clear max and write to file
         */
        Button clearMax = new Button(getApplicationContext());
        Button accelerometerWrite = new Button(getApplicationContext());
        clearMax.setText("Clear Max Values And History");
        accelerometerWrite.setText("Write Accelerometer Data");

        /*
         * Add listener to button to clear max and history
         */
        clearMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometerEventListener.clearMax();
                magnoEventListener.clearMax();
                gyroEventListener.clearMax();
                lightSensorEventListener.clearMax();
            }
        });

        /*
         * File to write accelerometer data to
         */
        final File file = new File(getExternalFilesDir("accelerometer") + "/data.csv");

        accelerometerWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    accelerometerEventListener.getEventHistory().writeDataToFile(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        view.addView(clearMax);
        view.addView(accelerometerWrite);

        for (TextView textView : textViews) {
            textView.setTextColor(Color.WHITE);
            view.addView(textView);
        }

        /*
         * Register all the listeners with the SensorManager
         */
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(accelerometerEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(magnoEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(gyroEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(lightSensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);
    }
}
