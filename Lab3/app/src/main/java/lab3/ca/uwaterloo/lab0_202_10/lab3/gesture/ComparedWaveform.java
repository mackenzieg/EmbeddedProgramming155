package lab3.ca.uwaterloo.lab0_202_10.lab3.gesture;

public class ComparedWaveform {

    private float value;
    private LabelledGesture labelledGesture;

    public ComparedWaveform(float value, LabelledGesture labelledGesture) {
        this.value = value;
        this.labelledGesture = labelledGesture;
    }

    public float getValue() {
        return value;
    }

    public LabelledGesture getLabelledGesture() {
        return labelledGesture;
    }
}
