package lab3.ca.uwaterloo.lab0_202_10.lab3.util;

import java.util.Iterator;
import java.util.List;

public class Array {

    public static float[] mapToFloat(List<Float> sample) {
        float[] array = new float[sample.size()];
        Iterator<Float> iterator = sample.iterator();
        for (int i = 0; i < array.length; ++i) {
            array[i] = iterator.next();
        }
        return array;
    }

}
