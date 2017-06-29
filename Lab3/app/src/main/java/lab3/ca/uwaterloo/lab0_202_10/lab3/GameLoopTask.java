package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    private Activity activity;
    private Direction currDirection = Direction.NONE;

    private Block block;

    public GameLoopTask(Activity activity, Context context, RelativeLayout layout) {
        this.activity = activity;
        // Generate block and add to view
        this.block = new Block(context, 0, 0);
        layout.addView(this.block);
    }

    // Loop that will update the block every tick
    @Override
    public void run() {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        block.tick();
                    }
                }
        );
    }

    // Set the direction of the block
    public void setDirection(Direction dir) {
        this.currDirection = dir;
        Log.d("DEBUG", "Moving direction to: " + dir.getLabel());
        block.setNewDir(dir);
    }
}
