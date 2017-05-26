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

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.AccelerometerEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.EventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.GyroEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.LightSensorEventListener;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners.MagnoEventListener;

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
        final TextView gyroDisplay            = new TextView(getApplicationContext());
        TextView gyroMax                = new TextView(getApplicationContext());
        TextView lightLabel             = new TextView(getApplicationContext());
        TextView lightSensorDisplay     = new TextView(getApplicationContext());
        TextView lightSensorMax         = new TextView(getApplicationContext());

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

        accelerometerLabel.setText("Accelerometer Data");
        magnoLabel.setText("Magnometer Data");
        gyroLabel.setText("Gyroscope Data");
        lightLabel.setText("Light Sensor Data");

        final AccelerometerEventListener accelerometerEventListener = new AccelerometerEventListener(lineGraphView,
                accelerometerDisplay, accelerometerMax);
        final MagnoEventListener magnoEventListener = new MagnoEventListener(magnoDisplay,
                magnoMax);
        final GyroEventListener gyroEventListener = new GyroEventListener(gyroDisplay,
                gyroMax);
        final LightSensorEventListener lightSensorEventListener = new LightSensorEventListener(lightSensorDisplay,
                lightSensorMax);

        Button clearMax = new Button(getApplicationContext());
        Button accelerometerWrite = new Button(getApplicationContext());
        clearMax.setText("Clear Max Values");
        accelerometerWrite.setText("Write Accelerometer Data");

        clearMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometerEventListener.clearHistory();
                accelerometerEventListener.clearMax();
                magnoEventListener.clearMax();
                gyroEventListener.clearMax();
                lightSensorEventListener.clearMax();
            }
        });

        final File file = new File(getExternalFilesDir("accelerometer"), "data.txt");

        accelerometerWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    accelerometerEventListener.writeDataToFile(file);
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
