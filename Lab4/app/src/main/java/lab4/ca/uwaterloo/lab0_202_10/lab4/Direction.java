package lab4.ca.uwaterloo.lab0_202_10.lab4;

public enum Direction {

    UP("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGHT("Right"),
    NONE("None");

    private String label;

    Direction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
