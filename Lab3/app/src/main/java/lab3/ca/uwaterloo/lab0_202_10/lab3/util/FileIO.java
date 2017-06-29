/*package lab3.ca.uwaterloo.lab0_202_10.lab3.util;

import android.nfc.FormatException;

import lab3.ca.uwaterloo.lab0_202_10.lab3.gesture.Gesture;
import lab3.ca.uwaterloo.lab0_202_10.lab3.gesture.LabelledGesture;

public class FileIO {

    public static Gesture gestureFromString(String format) throws FormatException {
        Gesture gesture = null;
        String[] keys = format.split("-");
        for (String val : keys) {
            switch (val) {
                case "name":
                    // We can always expect gesture name to be first if it is a LabelledGesture
                    if (gesture == null) {
                        gesture = new LabelledGesture(val.substring(val.indexOf("-"), val.length()));
                    } else {
                        throw new FormatException("Gesture file was wrong format");
                    }
                    break;
                case "length":
                    if (gesture == null) {
                        gesture = new Gesture();
                    }
                    // Don't need length just for statistics and debugging
                    break;
                case "points:":
                    if (gesture == null) {
                        gesture = new Gesture();
                    }
                    float[] values = new float[3];
                    String points = val.substring(0, val.indexOf(":"));
                    String[] individualPoint = points.replaceAll("[()]", "").split(",");
                    for (int i = 0; i < individualPoint.length; ++i) {
                        values[i % 3] = Float.parseFloat(individualPoint[i % 3]);
                        if (i % 3 == 2) {
                            gesture.addPoint(values.clone());
                        }
                    }
                    break;
            }
        }
        return gesture;
    }

}*/
