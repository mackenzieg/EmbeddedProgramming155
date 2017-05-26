package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.widget.TextView;

public class LightSensorEventListener extends EventListener {

    public LightSensorEventListener(TextView values, TextView maxView) {
        super(values, maxView, new String[]{
                "Intensity"
        }, 1, false);
    }

    @Override
    public void writeValuesToView(Float[] values) {
        ((TextView) this.getOutputView()).setText(this.getFormattedValues(values));
    }

    @Override
    public void writeMaxValuesToView(Float[] values) {
        ((TextView) this.getMaxView()).setText(this.getFormattedMaxValues(values));
    }
}
