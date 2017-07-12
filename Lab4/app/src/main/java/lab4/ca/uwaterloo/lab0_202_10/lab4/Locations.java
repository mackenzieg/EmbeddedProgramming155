package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.util.Log;

public class Locations {

    public static final float LEFT_BOUND = -105;
    public static final float RIGHT_BOUND = 435;
    public static final float UPPER_BOUND = -105;
    public static final float LOWER_BOUND = 435;

    public static final float BLOCK_LENGTH_X = 180f;
    public static final float BLOCK_LENGTH_Y = 180f;

    public static int getBoardX(float x) {
        return (int) (x / BLOCK_LENGTH_X);
    }

    public static int getBoardY(float y) {
        return (int) (y / BLOCK_LENGTH_Y);
    }

    public static float getSlotY(int y) {
        return UPPER_BOUND + BLOCK_LENGTH_Y * (y);
    }

    public static float getSlotX(int x) {
        return LEFT_BOUND + BLOCK_LENGTH_X * (x);
    }
}
