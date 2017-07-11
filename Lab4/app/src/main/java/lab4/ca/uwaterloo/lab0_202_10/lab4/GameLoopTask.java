package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.app.Activity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

public class GameLoopTask extends TimerTask {

    private Activity activity;

    private Random random = new Random();

    public LinkedList<Block> blocks = new LinkedList<>();

    public int[][] locationValues = new int[4][4];

    private boolean movementDone = false;

    private RelativeLayout relativeLayout;

    public GameLoopTask(Activity activity, RelativeLayout layout) {
        this.activity = activity;
        this.relativeLayout = layout;
        for (int i = 0; i < this.generateRandom(3, 1); ++i) {
            this.createBlock();
        }
    }

    public LinkedList<Block> getBlocks() {
        return blocks;
    }

    public int[][] getLocationValues() {
        return locationValues;
    }

    public void createBlock() {

        int value = random.nextBoolean() ? 2 : 4;

        int available = 0;
        for (int i = 0; i < 4; ++i) {
            for (int n = 0; n < 4; ++n) {
                if (locationValues[i][n] == 0) {
                    ++available;
                }
            }
        }

        if (available == 0) {
            Log.d("Lose", "Lose");
            return;
        }

        boolean generated = false;

        while (!generated) {
            int x = random.nextInt(3);
            int y = random.nextInt(3);

            Log.d("DEBUG", locationValues[x][y] + "");

            if (locationValues[x][y] == 0) {
                Block block = new Block(this.activity, this, x, y, relativeLayout, value);
                locationValues[x][y] = value;
                this.blocks.add(block);
                generated = true;
            }
        }
    }

    // Loop that will update the block every tick
    @Override
    public void run() {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        movementDone = true;
                        for (int i = 0; i < blocks.size(); ++i) {
                            blocks.get(i).tick();
                            movementDone &= blocks.get(i).isDoneMoving();
                        }

                        if (movementDone) {
                            checkOverlaps();
                            checkLocationValue();

                            Iterator<Block> items = blocks.iterator();
                            while (items.hasNext()) {
                                Block block = items.next();
                                if (block.getValue() == 256) {
                                    Toast toast = Toast.makeText(activity, "Nice Job Kid!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        }
                    }
                }
        );
    }

    private void deleteBlock(Block block) {
        block.destroy();
        for (int i = 0; i < blocks.size(); ++i) {
            if (blocks.get(i).equals(block)) {
                blocks.remove(i);
                return;
            }
        }
    }

    private void checkOverlaps() {
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = i + 1; j < blocks.size(); j++) {
                if (blocks.get(i).samePosition(blocks.get(j))) {
                    blocks.get(i).doubleValue();
                    this.deleteBlock(blocks.get(j));
                }
            }
        }
    }

    private void checkLocationValue() {
        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < 4; x++) {
                locationValues[i][x] = 0;
            }
        }

        for (int i = 0; i < blocks.size(); ++i) {
            int x = blocks.get(i).getRow();
            int y = blocks.get(i).getColumn();
            int value = blocks.get(i).getValue();

            locationValues[x][y] = value;
        }
    }

    // Set the direction of the block
    public void setDirection(Direction dir) {
        if (movementDone) {
            Iterator<Block> items = blocks.iterator();
            while (items.hasNext()) {
                Block block = items.next();
                block.setNewDir(dir);
                block.preCalcLocation();
            }
        }
    }

    public int generateRandom(int high, int low) {
        return this.random.nextInt(high - low) + low;
    }

}