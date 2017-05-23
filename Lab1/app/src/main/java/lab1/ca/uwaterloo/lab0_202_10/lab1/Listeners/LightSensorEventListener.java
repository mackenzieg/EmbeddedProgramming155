package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import lab1.ca.uwaterloo.lab0_202_10.lab1.Point3f;

public class LightSensorEventListener extends EventHistory<Float> implements SensorEventListener, EventListener, View.OnClickListener {

    private float max = Float.NEGATIVE_INFINITY;

    private TextView output;

    private TextView maxView;

    private File file;
    private PrintWriter printWriter;

    public LightSensorEventListener(TextView output, TextView maxView, Context context) {
        super(100);
        this.maxView = maxView;
        this.output = output;

        this.file = new File(context.getExternalFilesDir("lab1/accelerometer"),
                "data.csv");
        this.printWriter = null;
        try {
            this.printWriter = new PrintWriter(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        output.setText(String.format("Light (lux): %f", event.values[0]));
        this.addData(event.values[0]);
        if (event.values[0] > max) {
            max = event.values[0];
            maxView.setText(String.format("Max Light: %f", max));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onClick(View v) {
        this.file.delete();
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Object[] points = this.getDataPoints();
        for (Object point : points) {
            Float po = (Float) point;
            printWriter.println(String.format("%f", po));
        }
    }
}
