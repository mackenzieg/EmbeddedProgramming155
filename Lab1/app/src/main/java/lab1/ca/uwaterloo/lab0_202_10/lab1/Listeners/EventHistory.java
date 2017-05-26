package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

class EventHistory {

    private LinkedList<Float[]> dataPoints = new LinkedList<>();

    private int length;

    EventHistory (int length) {
        this.length = length;
    }

    EventHistory () {
        this.length = 100;
    }

    void addData(Float[] data) {
        dataPoints.add(data);
        if (dataPoints.size() >= this.length) {
            dataPoints.removeLast();
        }
    }

    void clearHistory() {
        this.dataPoints.clear();
    }

    private Float[][] getDataPoints() {
        return (Float[][]) dataPoints.toArray();
    }

    public void writeDataToFile(File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        Float[][] points = getDataPoints();
        for(int y = 0; y < points.length; ++y) {
            String expandedPoints = "";
            for(int x = 0; x < points[y].length; ++x) {
                expandedPoints += points[y][x] + ((x - 1 == points[y].length)
                ? ""
                : ",");
            }
            printWriter.println(expandedPoints);
        }
        printWriter.flush();
        printWriter.close();
    }
}
