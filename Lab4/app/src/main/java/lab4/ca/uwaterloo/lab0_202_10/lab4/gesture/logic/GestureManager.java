package lab4.ca.uwaterloo.lab0_202_10.lab4.gesture.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lab4.ca.uwaterloo.lab0_202_10.lab4.Locations;
import lab4.ca.uwaterloo.lab0_202_10.lab4.gesture.ComparedWaveform;
import lab4.ca.uwaterloo.lab0_202_10.lab4.gesture.Gesture;
import lab4.ca.uwaterloo.lab0_202_10.lab4.gesture.LabelledGesture;

public class GestureManager extends Thread {

    private List<LabelledGesture> recognizedGestures;

    private LinkedList<Gesture> queue = new LinkedList<>();

    private int recordedGestures = 0;

    public GestureManager(List<LabelledGesture> recognizedGestures) {
        this.recognizedGestures = recognizedGestures;
        this.start();
    }

    public void addLabelledGesture(LabelledGesture labelledGesture) {
        this.recognizedGestures.add(labelledGesture);
    }

    @Override
    public void run() {

        while (true) {

            if (this.queue.size() == 0) {
                continue;
            }
            Gesture gesture = this.queue.removeFirst();


            if (this.recordedGestures < recognizedGestures.size()) {
                LabelledGesture labelledGesture = recognizedGestures.get(recordedGestures);
                labelledGesture.setGesture(gesture);
                this.caughtReferenceGesture(labelledGesture);
                ++recordedGestures;
                continue;
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

    public void removeLastRecordedGesture() {
        recognizedGestures.remove(recordedGestures--);
    }

    public synchronized void caughtRecognizedGesture(LabelledGesture labelledGesture) {}

    public synchronized void caughtReferenceGesture(LabelledGesture labelledGesture) {}

    public synchronized void newGesture(Gesture gesture) {

        this.queue.add(gesture);
    }

}
