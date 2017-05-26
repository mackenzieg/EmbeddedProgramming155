package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.content.Context;
import android.hardware.SensorEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import lab1.ca.uwaterloo.lab0_202_10.lab1.LineGraphView;

public class AccelerometerEventListener extends EventListener {

    private LineGraphView lineGraphView;

    public AccelerometerEventListener(LineGraphView output, TextView values, TextView maxView, Context context) {
        super(values, maxView, new String[] {
                "x",
                "y",
                "z"
        }, true);
        this.lineGraphView = output;
        this.lineGraphView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        super.onSensorChanged(event);
    }

    @Override
    public void writeValuesToView(Float[] values) {
        ((TextView) this.getOutputView()).setText(this.getFormattedValues(values));
        this.lineGraphView.addPoint(Arrays.asList(values));
    }

    @Override
    public void writeMaxValuesToView(Float[] values) {
        ((TextView) this.getOutputView()).setText(this.getFormattedValues(values));
    }

    @Override
    public void clearHistory() {
        super.clearHistory();
        this.lineGraphView.purge();
    }
}