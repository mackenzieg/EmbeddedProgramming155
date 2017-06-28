package lab3.ca.uwaterloo.lab0_202_10.lab3.gesture;

import lab3.ca.uwaterloo.lab0_202_10.lab3.Direction;

public class LabelledGesture extends Gesture {

    private Direction direction;

    public LabelledGesture(Direction direction) {
        super();
        this.direction = direction;
    }

    public void setGesture(Gesture gesture) {
        this.setX(gesture.getX());
        this.setY(gesture.getY());
        this.setZ(gesture.getZ());
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        String fullText = super.toString();
        int index = fullText.indexOf("-");
        return "name:-" + fullText.substring(index, fullText.length());
    }

}
