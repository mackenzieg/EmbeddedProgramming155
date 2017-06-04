package lab2_202_10.uwaterloo.ca.lab2.gesture;

public class LabelledGesture extends Gesture {

    private String label;

    public LabelledGesture(String label) {
        super();
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
