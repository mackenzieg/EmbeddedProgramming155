package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import java.util.LinkedList;

class EventHistory<T> {

    private LinkedList<T> dataPoints = new LinkedList<>();

    private int length;

    EventHistory (int length) {
        this.length = length;
    }

    void addData(T data) {
        dataPoints.add(data);
        if (dataPoints.size() >= this.length) {
            dataPoints.removeLast();
        }
    }

    T[] getDataPoints() {
        return (T[]) dataPoints.toArray();
    }
}
