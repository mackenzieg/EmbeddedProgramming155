package lab2_202_10.uwaterloo.ca.lab2.gesture;

public class LabelledGesture extends Gesture {

    private String label;

    public LabelledGesture(String label) {
        super();
        this.label = label;
    }

    public void setGesture(Gesture gesture) {
        this.setX(gesture.getX());
        this.setY(gesture.getY());
        this.setZ(gesture.getZ());
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        String fullText = super.toString();
        int index = fullText.indexOf("-");
        return "name:-" + fullText.substring(index, fullText.length());
    }

}
