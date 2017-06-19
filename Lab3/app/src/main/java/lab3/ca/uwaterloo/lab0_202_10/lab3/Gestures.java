package lab3.ca.uwaterloo.lab0_202_10.lab3;

public enum Gestures {

    UP("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGHT("Right");

    public String name;

    Gestures(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Gestures[] ALL_GESTURES = values();
}
