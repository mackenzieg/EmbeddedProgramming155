package lab1.ca.uwaterloo.lab0_202_10.lab1;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.AccelerometerEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.LightSensorEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout view = (LinearLayout) findViewById(R.id.main_layout);

        LineGraphView lineGraphView = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));
        view.addView(lineGraphView);

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

        accelerometerLabel.setTextColor(Color.WHITE);
        accelerometerDisplay.setTextColor(Color.WHITE);
        accelerometerMax.setTextColor(Color.WHITE);
        lightSensorDisplay.setTextColor(Color.WHITE);
        lightSensorMax.setTextColor(Color.WHITE);

        Button accelerometerWrite = new Button(getApplicationContext());
        accelerometerWrite.setText("Write Accelerometer Data");

        view.addView(accelerometerDisplay);
        view.addView(accelerometerMax);
        view.addView(accelerometerWrite);
        view.addView(lightSensorDisplay);
        view.addView(lightSensorMax);

        LightSensorEventListener lightSensorEventListener = new LightSensorEventListener(lightSensorDisplay,
                lightSensorMax, this);
        AccelerometerEventListener accelerometerEventListener = new AccelerometerEventListener(lineGraphView,
                accelerometerDisplay, accelerometerMax, this);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(lightSensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(accelerometerEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        accelerometerWrite.setOnClickListener(accelerometerEventListener);

        lightSensorEventListener.start();
        accelerometerEventListener.start();

    }
}
