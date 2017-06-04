package lab2_202_10.uwaterloo.ca.lab2.gesture.logic;


import java.util.List;

import lab2_202_10.uwaterloo.ca.lab2.util.Array;

public class TimeWarp {

    // Wave form measured
    private float[] x;
    // Wave form
    private float[] y;
    // Temp matrix conversion
    private float[][] dtw;
    // Matrix for DTW algorithm
    private float[][] distance;
    private float sum;

    public TimeWarp(List<Float> x, List<Float> y) {
        this(Array.mapToFloat(x), Array.mapToFloat(y));
    }

    public TimeWarp(float[] x, float[] y) {
        this.x = x;
        this.y = y;
        this.distance = new float[x.length][y.length];
        this.dtw = new float[x.length + 1][y.length + 1];

        for (int i = 0; i <= this.x.length; ++i) {
            for (int j = 0; j <= this.y.length; ++j) {
                dtw[i][j] = -1.0f;
            }
        }

        // Compare every point to every other point in matrix
        for (int i = 0; i < this.x.length; ++i) {
            for (int j = 0; j < this.y.length; ++j) {
                distance[i][j] = Math.abs(x[i] - y[j]);
            }
        }

        // Fill in first row
        for (int i = 1; i < x.length; ++i) {
            dtw[i][0] = Float.POSITIVE_INFINITY;
        }

        // Fill in first column
        for (int j = 1; j < y.length; ++j) {
            dtw[0][j] = Float.POSITIVE_INFINITY;
        }

        this.dtw[0][0] = 0.0f;
        this.sum = 0.0f;
    }

    public float getSum() {
        return sum;
    }

    public void computeDTW() {
        this.sum = computeBackward(x.length - 1, y.length - 1);
    }

    private float computeBackward(int i, int j) {
        // If greater than window return distance
        if (!(dtw[i][j] < 0.0f)) {
            return dtw[i][j];
        }

        // Compute path in insertion manner
        if (computeBackward(i - 1, j) <= computeBackward(i, j - 1) &&
                computeBackward(i - 1, j) <= computeBackward(i - 1, j - 1) &&
                computeBackward(i - 1, j) < Float.POSITIVE_INFINITY) {

            dtw[i][j] = distance[i - 1][j - 1] + computeBackward(i - 1, j);
            // Compute path in deletion manner
        } else if (computeBackward(i, j - 1) <= computeBackward(i - 1, j) &&
                computeBackward(i, j - 1) <= computeBackward(i - 1, j - 1) &&
                computeBackward(i, j - 1) < Float.POSITIVE_INFINITY) {

            dtw[i][j] = distance[i - 1][j - 1] + computeBackward(i, j - 1);
            // Compute path in match manner
        } else if (computeBackward(i - 1, j - 1) <= computeBackward(i - 1, j) &&
                computeBackward(i - 1, j - 1) <= computeBackward(i, j - 1) &&
                computeBackward(i - 1, j - 1) < Float.POSITIVE_INFINITY) {

            dtw[i][j] = distance[i - 1][j - 1] + computeBackward(i - 1, j - 1);
        }
        return dtw[i][j];
    }
}
