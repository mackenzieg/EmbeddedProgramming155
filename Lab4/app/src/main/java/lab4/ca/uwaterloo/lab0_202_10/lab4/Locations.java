package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.util.Log;

public class Locations {

    public final float LEFT_BOUND = -105;
    public final float RIGHT_BOUND = 435;
    public final float UPPER_BOUND = -105;
    public final float LOWER_BOUND = 435;

    public final float BLOCK_LENGTH_X = (RIGHT_BOUND - LEFT_BOUND) / 4;
    public final float BLOCK_LENGTH_Y = (LOWER_BOUND - UPPER_BOUND) / 4;

    public float getSlotX(int x) {
        Log.d("DEBUG", this.BLOCK_LENGTH_X + "");
        return BLOCK_LENGTH_X * (x);
    }

    public float getSlotY(int y) {
        return BLOCK_LENGTH_Y * (y);
    }

}
