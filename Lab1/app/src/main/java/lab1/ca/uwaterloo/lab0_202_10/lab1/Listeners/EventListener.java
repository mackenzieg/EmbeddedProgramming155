package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

/**
 * This class consists of useful methods for hardware even listeners such as
 * support for displaying current data points and recording max data points.
 * Also auto inserts prefixes for data points such as (x, y, z).
 */
public abstract class EventListener extends EventHistory implements SensorEventListener {

    private int valueLength;
    private Float[] values = null;
    private String[] labels = null;
    private View outputView;

    private Float[] maxValues = null;
    private View maxView;

    private EventHistory eventHistory = null;

    /**
     * @param outputView    view to output current values of sensor to
     * @param maxView       view to output max values of sensor
     * @param labels        labels for values and max value ex) x, y, z
     * @param valueLength   length of expected sensor values
     * @param recordHistory record data points in history log
     */
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

    /**
     * @return object that handles recording event history
     */
    public EventHistory getEventHistory() {
        return this.eventHistory;
    }

    public View getOutputView() {
        return outputView;
    }

    public View getMaxView() {
        return maxView;
    }

    /**
     * Writes sensor values to current view and checks / writes max values to view
     */
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

    /**
     * Clears max values from data and from view
     */
    public void clearMax() {
        if (maxValues == null) {
            return;
        }
        for (int i = 0; i < maxValues.length; ++i) {
            maxValues[i] = 0.0f;
        }
        writeMaxValuesToView(this.maxValues);
    }

    /**
     * @param i index of prefix
     * @return prefix to value at certain index
     */
    public String getPrefix(int i) {
        if (labels == null || this.labels.length < i) {
            return null;
        }
        return this.labels[i];
    }

    /**
     * @param values from sensor
     * abstract method requires overriding to write values to view
     */
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

    /**
     * @param values from sensor
     * abstract method requires overriding to write max values to view
     */
    public abstract void writeMaxValuesToView(Float[] values);


    /**
     * @param values max values from sensor
     * @return formatted max values with their prefixes
     */
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
