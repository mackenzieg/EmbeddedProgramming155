package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    private Activity activity;
    private Direction currDirection = Direction.NONE;

    private Block block;

    public GameLoopTask(Activity activity, Context context, RelativeLayout layout) {
        this.activity = activity;
        // Generate block and add to view
        TextView textView = new TextView(context);
        textView.setTextColor(Color.RED);
        this.block = new Block(context, textView, -100, -100, 2);
        layout.addView(this.block);
        layout.addView(textView);
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
