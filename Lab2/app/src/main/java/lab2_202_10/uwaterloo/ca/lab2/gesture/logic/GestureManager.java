package lab2_202_10.uwaterloo.ca.lab2.gesture.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lab2_202_10.uwaterloo.ca.lab2.gesture.ComparedWaveform;
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

    public void caughtRecognizedGesture(LabelledGesture labelledGesture) {}

    public void caughtReferenceGesture(LabelledGesture labelledGesture) {}

    public void newGesture(Gesture gesture) {

        if (this.recordedGestures < recognizedGestures.size()) {
            LabelledGesture labelledGesture = recognizedGestures.get(recordedGestures);
            labelledGesture.setGesture(gesture);
            this.caughtReferenceGesture(labelledGesture);
            ++recordedGestures;
            return;
        }


        List<ComparedWaveform> comparedWaveforms = new ArrayList<>();

        for (int i = 0; i < recognizedGestures.size(); ++i) {
            LabelledGesture compareTo = recognizedGestures.get(i);

            TimeWarp timeWarpX = new TimeWarp(gesture.getX(), compareTo.getX());
            timeWarpX.computeDTW();
            TimeWarp timeWarpY = new TimeWarp(gesture.getY(), compareTo.getZ());
            timeWarpY.computeDTW();
            TimeWarp timeWarpZ = new TimeWarp(gesture.getZ(), compareTo.getZ());
            timeWarpZ.computeDTW();

            float value = timeWarpX.getSum() + timeWarpY.getSum() + timeWarpZ.getSum();


            comparedWaveforms.add(new ComparedWaveform(value, compareTo));
        }

        LabelledGesture recognized = null;
        float smallest = Float.POSITIVE_INFINITY;

        for (ComparedWaveform waveform : comparedWaveforms) {
            if (waveform.getValue() < smallest) {
                smallest = waveform.getValue();
                recognized = waveform.getLabelledGesture();
            }
        }

        this.caughtRecognizedGesture(recognized);
    }

}
