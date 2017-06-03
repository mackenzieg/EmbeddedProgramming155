package lab2_202_10.uwaterloo.ca.lab2.gesture;

import android.util.Log;

import java.util.List;

public class TimeWarp {

    private static final float UPPER_BOUND = 0.2f;
    private static final float LOWER_BOUND = 0.2f;

    /**
     * @param axis list of points from axis of accelerometer data
     * @param sample list of points of waveform to compare to
     */
    public static void compareAxis(List<Float> axis, List<Float> sample) {
        float[][] matrix = new float[sample.size()][axis.size()];
        for (int y = 0; y < matrix.length; ++y) {
            String all = "";
            for (int x = 0; x < matrix.length; ++x) {
                float value = (float) Math.pow((sample.get(x) - axis.get(y)), 2);
                if (value < UPPER_BOUND && value > LOWER_BOUND) {
                    value = 0.0f;
                }
                all += value + ",";
                matrix[y][x] = value;
            }
            all.substring(0, all.length() - 1);
            Log.d("DEBUG", all);
        }

    }

}
