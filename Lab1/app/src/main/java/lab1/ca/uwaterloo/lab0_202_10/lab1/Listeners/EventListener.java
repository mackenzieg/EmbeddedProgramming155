package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;

public abstract class EventListener extends EventHistory implements SensorEventListener {

    private int valueLength;
    private Float[] values = null;
    private String[] labels = null;
    private View outputView;

    private Float[] maxValues = null;
    private View maxView;

    private EventHistory eventHistory = null;

    EventListener(View outputView, View maxView, String[] labels, int valueLength, boolean recordHistory) {
        this.outputView = outputView;
        this.maxView = maxView;
        this.labels = labels;
        this.values = new Float[valueLength];
        this.maxValues = new Float[valueLength];
        if (recordHistory) {
            eventHistory = new EventHistory();
        }
        this.valueLength = valueLength;

        for (int i = 0; i < valueLength; ++i) {
            maxValues[i] = values[i] = 0.0f;
        }
    }

    public EventHistory getEventHistory() {
        return this.eventHistory;
    }

    public View getOutputView() {
        return outputView;
    }

    public View getMaxView() {
        return maxView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int length = this.valueLength;
        boolean writeMax = false;
        for (int i = 0; i < length; ++i) {
            values[i] = event.values[i];
            if (maxValues[i] < values[i]) {
                writeMax = true;
                maxValues[i] = values[i];
            }
        }

        writeValuesToView(this.values);
        if (writeMax) {
            writeMaxValuesToView(this.maxValues);
        }
    }

    public void clearMax() {
        if (maxValues == null) {
            return;
        }
        for (int i = 0; i < maxValues.length; ++i) {
            maxValues[i] = 0.0f;
        }
    }

    public String getPrefix(int i) {
        if (labels == null) {
            return null;
        }
        return this.labels[i];
    }

    public abstract void writeValuesToView(Float[] values);

    public String getFormattedValues(Float[] values) {
        if (values == null) {
            return null;
        }
        String whole = "";
        for(int i = 0; i < values.length; ++i) {
            whole += this.getPrefix(i) + ": " + values[i] + "\n";
        }
        return whole.substring(0, whole.length() - 1);
    }

    public abstract void writeMaxValuesToView(Float[] values);

    public String getFormattedMaxValues(Float[] values) {
        if (values == null) {
            return null;
        }
        String whole = "";
        for(int i = 0; i < values.length; ++i) {
            whole += "Max " + this.getPrefix(i) + ": " + values[i] + "\n";
        }
        return whole.substring(0, whole.length() - 1);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
