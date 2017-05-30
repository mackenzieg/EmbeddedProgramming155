package lab1.ca.uwaterloo.lab0_202_10.lab1.Listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * This class consists of a event history recorder. Also has the ability to write
 * event history to specified file.
 */
class EventHistory {

    /**
     * LinkedList contains list of data points in history
     */
    private LinkedList<Float[]> dataPoints = new LinkedList<>();

    private int length;

    EventHistory (int length) {
        this.length = length;
    }

    EventHistory () {
        this.length = 100;
    }

    /**
     * @param data point to add to event history que
     * Checks for amount of values and removes if above wanted range
     */
    void addData(Float[] data) {
        dataPoints.add(data);
        if (dataPoints.size() >= this.length) {
            dataPoints.removeLast();
        }
    }

    /**
     * Clears all the data points
     */
    void clearHistory() {
        this.dataPoints.clear();
    }

    /**
     * @return array of array of floats of data points
     */
    private Float[][] getDataPoints() {
        Object[] points = dataPoints.toArray();
        if (points.length == 0) {
            return null;
        }
        Float[][] pointArray = new Float[points.length][((Float[])points[0]).length];
        for (int y = 0; y < points.length; ++y) {
            System.arraycopy(((Float[]) points[y]), 0, pointArray[y], 0, ((Float[]) points[y]).length);
        }
        return pointArray;
    }

    /**
     * @param file output to write data points to
     * @throws FileNotFoundException
     */
    public void writeDataToFile(File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        Float[][] points = getDataPoints();
        if (points == null) {
            return;
        }
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
