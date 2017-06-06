package lab2_202_10.uwaterloo.ca.lab2.gesture.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lab2_202_10.uwaterloo.ca.lab2.gesture.Gesture;
import lab2_202_10.uwaterloo.ca.lab2.gesture.LabelledGesture;

public class GestureManager {

    private List<LabelledGesture> recognizedGestures;

    private int recordedGestures = 0;

    public GestureManager(List<LabelledGesture> recognizedGestures) {
        this.recognizedGestures = recognizedGestures;
    }

    public void addLabelledGesture(LabelledGesture labelledGesture) {
        this.recognizedGestures.add(labelledGesture);
    }

    public void removeLastRecordedGesture() {
        recognizedGestures.remove(recordedGestures--);
    }

    public void caughtLabelledGesture(LabelledGesture labelledGesture) {}

    public void newGesture(Gesture gesture) {

        if (this.recordedGestures < recognizedGestures.size()) {
            LabelledGesture labelledGesture = recognizedGestures.get(recordedGestures);
            labelledGesture.setGesture(gesture);
            this.caughtLabelledGesture(labelledGesture);
            ++recordedGestures;
            return;
        }

        Object[][] comparedTimeWarps = new Object[recognizedGestures.size()][2];

        for (int i = 0; i < recognizedGestures.size(); ++i) {
            LabelledGesture compareTo = recognizedGestures.get(i);

            TimeWarp timeWarpX = new TimeWarp(gesture.getX(), compareTo.getX());
            timeWarpX.computeDTW();
            TimeWarp timeWarpY = new TimeWarp(gesture.getY(), compareTo.getZ());
            timeWarpY.computeDTW();
            TimeWarp timeWarpZ = new TimeWarp(gesture.getZ(), compareTo.getZ());
            timeWarpZ.computeDTW();

            comparedTimeWarps[i][0] = compareTo.getLabel();
            comparedTimeWarps[i][1] = timeWarpX.getSum() + timeWarpY.getSum() + timeWarpZ.getSum();
        }

        String recognized = "";
        float smallest = Float.POSITIVE_INFINITY;

        for (int i = 0; i < comparedTimeWarps.length; ++i) {
            Log.d("DEBUG", "" + comparedTimeWarps[i][0]);
            Log.d("DEBUG", "" + comparedTimeWarps[i][1]);
            if ((Float) comparedTimeWarps[i][1] < smallest) {
                smallest = (Float) comparedTimeWarps[i][1];
                recognized = (String) comparedTimeWarps[i][0];
            }
        }

        Log.d("DEBUG", recognized);
    }

}
