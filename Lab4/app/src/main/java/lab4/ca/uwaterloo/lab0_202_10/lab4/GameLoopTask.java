package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    private Activity activity;
    private Direction currDirection = Direction.NONE;

    private Random random = new Random();

    private Block[][] blocks = new Block[4][4];

    public GameLoopTask(Activity activity, Context context, RelativeLayout layout) {
        this.activity = activity;

        int numGenerate = this.generateRandom(3, 1);

        Log.d("DEBUG", numGenerate + "");

        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                if (numGenerate != 0 && this.generateRandom(2, 0) == 1) {
                    --numGenerate;
                    TextView numberDisplay = new TextView(context);
                    numberDisplay.setTextColor(Color.RED);
                    layout.addView(numberDisplay);
                    blocks[x][y] = new Block(context, numberDisplay,
                            Locations.getSlotX(x), Locations.getSlotY(y),
                            this.generateRandom(2, 0) == 1 ? 4 : 2);
                    layout.addView(blocks[x][y]);
                    numberDisplay.bringToFront();
                } else {
                    blocks[x][y] = null;
                }
            }
        }
    }

    // Loop that will update the block every tick
    @Override
    public void run() {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        for (int x = 0; x < 4; ++x) {
                            for (int y = 0; y < 4; ++y) {
                                if (blocks[x][y] != null)
                                    blocks[x][y].tick();
                            }
                        }
                    }
                }
        );
    }

    // Set the direction of the block
    public void setDirection(Direction dir) {
        this.currDirection = dir;
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                if (blocks[x][y] != null)
                    blocks[x][y].setNewDir(dir);
            }
        }
    }

    public int generateRandom(int high, int low) {
        return this.random.nextInt(high - low) + low;
    }

}
