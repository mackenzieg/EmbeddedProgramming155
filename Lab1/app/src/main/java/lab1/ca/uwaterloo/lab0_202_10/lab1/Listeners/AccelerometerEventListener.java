package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import lab1.ca.uwaterloo.lab0_202_10.lab1.LineGraphView;
import lab1.ca.uwaterloo.lab0_202_10.lab1.Point3f;

public class AccelerometerEventListener extends EventHistory<Point3f> implements SensorEventListener, EventListener, View.OnClickListener {

    private float xMax = Float.NEGATIVE_INFINITY;
    private float yMax = Float.NEGATIVE_INFINITY;
    private float zMax = Float.NEGATIVE_INFINITY;

    private LineGraphView lineGraphView;

    private PrintWriter printWriter;
    private File file;

    private TextView values;
    private TextView maxView;

    public AccelerometerEventListener(LineGraphView output, TextView values, TextView maxView, Context context) {
        super(100);
        this.lineGraphView = output;
        this.lineGraphView.setVisibility(View.VISIBLE);

        this.file = new File(context.getExternalFilesDir("lab1/accelerometer"),
                "data.csv");
        this.printWriter = null;
        try {
            this.printWriter = new PrintWriter(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.maxView = maxView;
        this.values = values;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.addData(new Point3f(event.values));
        values.setText(String.format("Acceleration (m/s^2)\nx: %f y: %f z: %f", event.values[0], event.values[1], event.values[2]));
        lineGraphView.addPoint(event.values);
        if (event.values[0] > xMax) {
            xMax = event.values[0];
        }
        if (event.values[1] > yMax) {
            yMax = event.values[1];
        }
        if (event.values[2] > zMax) {
            zMax = event.values[2];
        }
        maxView.setText(String.format("Max Acceleration (m/s^2)\nMax x: %f Max y: %f Max z: %f",
                xMax, yMax, zMax));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        printWriter.flush();
        printWriter.close();
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
        Point3f[] points = this.getDataPoints();
        for (Point3f point : points) {
            printWriter.println(String.format("%f,%f,%f", point.x, point.y, point.z));
        }
    }
}