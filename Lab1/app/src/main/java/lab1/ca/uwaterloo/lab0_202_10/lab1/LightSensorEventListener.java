package lab1.ca.uwaterloo.lab0_202_10.lab1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

class LightSensorEventListener implements SensorEventListener {

    private TextView output;

    LightSensorEventListener(TextView output) {
        this.output = output;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            output.setText(event.values[0] + "");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
