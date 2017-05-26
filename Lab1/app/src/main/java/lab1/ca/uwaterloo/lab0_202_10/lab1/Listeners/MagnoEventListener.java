package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import android.widget.TextView;

public class MagnoEventListener extends EventListener {

    public MagnoEventListener(TextView values, TextView maxView) {
        super(values, maxView, new String[]{
                "x",
                "y",
                "z"
        }, false);
    }

    @Override
    public void writeValuesToView(Float[] values) {
        ((TextView) this.getOutputView()).setText(this.getFormattedValues(values));
    }

    @Override
    public void writeMaxValuesToView(Float[] values) {
        ((TextView) this.getOutputView()).setText(this.getFormattedValues(values));
    }
}