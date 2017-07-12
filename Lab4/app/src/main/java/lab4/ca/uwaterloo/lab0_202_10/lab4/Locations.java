package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.util.Log;

public class Locations {

    public static final int LEFT_BOUND = -105;
    public static final int RIGHT_BOUND = 435;
    public static final int UPPER_BOUND = -105;
    public static final int LOWER_BOUND = 435;

    public static final int BLOCK_LENGTH_X = 180;
    public static final int BLOCK_LENGTH_Y = 180;

    public static int getBoardX(int x) {
        if (x >= LEFT_BOUND && x < LEFT_BOUND + BLOCK_LENGTH_X) {
            return 0;
        } else if (x >= LEFT_BOUND + BLOCK_LENGTH_X && x < LEFT_BOUND + 2 * BLOCK_LENGTH_X) {
            return 1;
        } else if (x >= LEFT_BOUND + 2 * BLOCK_LENGTH_X && x < LEFT_BOUND + 3 * BLOCK_LENGTH_X) {
            return 2;
        } else if (x >= LEFT_BOUND + 3 * BLOCK_LENGTH_X && x < LEFT_BOUND + 4 * BLOCK_LENGTH_X) {
            return 3;
        }
        return -1;
    }

    public static int getBoardY(int y) {
        if (y >= UPPER_BOUND && y < UPPER_BOUND + BLOCK_LENGTH_X) {
            return 0;
        } else if (y >= UPPER_BOUND + BLOCK_LENGTH_Y && y < UPPER_BOUND + 2 * BLOCK_LENGTH_Y) {
            return 1;
        } else if (y >= UPPER_BOUND + 2 * BLOCK_LENGTH_Y && y < UPPER_BOUND + 3 * BLOCK_LENGTH_Y) {
            return 2;
        } else if (y >= UPPER_BOUND + 3 * BLOCK_LENGTH_Y && y < UPPER_BOUND + 4 * BLOCK_LENGTH_Y) {
            return 3;
        }
        return -1;
    }

    public static int getSlotY(int y) {
        return UPPER_BOUND + BLOCK_LENGTH_Y * (y);
    }

    public static int getSlotX(int x) {
        return LEFT_BOUND + BLOCK_LENGTH_X * (x);
    }
}
