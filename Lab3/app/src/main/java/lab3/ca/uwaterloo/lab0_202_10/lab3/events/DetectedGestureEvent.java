package lab3.ca.uwaterloo.lab0_202_10.lab3.events;

import com.wtw.event.Event;

import lab3.ca.uwaterloo.lab0_202_10.lab3.Gestures;

public class DetectedGestureEvent extends Event {

    private final Gestures gesture;

    public DetectedGestureEvent(Gestures gesture) {
        this.gesture = gesture;
    }

    public Gestures getGesture() {
        return gesture;
    }
}
