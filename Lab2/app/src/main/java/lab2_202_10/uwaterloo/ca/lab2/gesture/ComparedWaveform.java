package lab2_202_10.uwaterloo.ca.lab2.gesture;

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
