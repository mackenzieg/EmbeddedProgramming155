package lab2_202_10.uwaterloo.ca.lab2.gesture.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lab2_202_10.uwaterloo.ca.lab2.gesture.Gesture;
import lab2_202_10.uwaterloo.ca.lab2.gesture.LabelledGesture;

public class GestureManager {

    private List<LabelledGesture> recognizedGestures;

    private boolean recording;

    public GestureManager(List<LabelledGesture> recognizedGestures, boolean recording) {
        this.recognizedGestures = recognizedGestures;
        this.recording = recording;
    }

    public void addGesture(LabelledGesture labelledGesture) {
        this.recognizedGestures.add(labelledGesture);
    }

    public void newGesture(Gesture gesture) {

        if (recording) {

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

        for (int i = 1; i < comparedTimeWarps.length; ++i) {
            if (smallest > (Float) comparedTimeWarps[i][1]) {
                recognized = (String) comparedTimeWarps[i][0];
            }
        }

        Log.d("DEBUG", recognized);
    }

    public abstract void

}
