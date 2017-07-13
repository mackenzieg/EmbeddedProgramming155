package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    private Activity activity;
    private Direction currDirection = Direction.NONE;

    private Random random = new Random();

    // RelativeLayout contains game block and board
    private RelativeLayout relativeLayout;
    // LinkedList contains all blocks in play
    private LinkedList<Block> blocks = new LinkedList<>();
    // Array of block locations and values
    private int[][] locationValue = new int[4][4];
    // Determines if a block should be generated on the next tick
    private boolean generateBlock = false;
    // Constructor
    public GameLoopTask(Activity activity, Context context, RelativeLayout layout) {
        this.activity = activity;
        this.relativeLayout = layout;


        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                locationValue[x][y] = 0;
            }
        }

        for (int i = 0; i < this.generateRandom(4, 1); ++i) {
            this.createBlock();
        }
    }
    // Checks blocks co-ordinates for overlap, if so, multiply by two, then delete one of the blocks
    public void checkOverlaps() {
        // go through blocks and compare it to every other block
        for (int i = 0; i < blocks.size() - 1; ++i) {
            for (int j = i + 1; j < blocks.size(); ++j) {
                Block blockA = blocks.get(i);
                Block blockB = blocks.get(j);

                // Compare the blocks locations with every other
                if (blockA.getBoardX() == blockB.getBoardX() &&
                        blockA.getBoardY() == blockB.getBoardY()) {
                    // Get the value and double it, then destroy the other block
                    int newValue = blockA.getValue() * 2;
                    if (newValue >= 512) {
                        Toast.makeText(activity, "GG You Wun U Gud Boi!", Toast.LENGTH_LONG).show();
                    }
                    blockA.setValue(newValue);
                    this.destroyBlock(blockB);
                }
            }
        }
    }

    // Removes the block from the array and also removes its view
    public void destroyBlock(Block block) {
        for (int i = 0; i < blocks.size(); ++i) {
            if (block.equals(blocks.get(i))) {
                blocks.remove(i);
            }
        }
        block.destroy();
    }

    // Creates a block at a location that doesnt contain any other blocks
    public void createBlock() {
        List<XYLocation> openSpots = new ArrayList<>();
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                if (locationValue[x][y] == 0) {
                    // If the location is free add it to the
                    openSpots.add(new XYLocation(x, y));
                }
            }
        }

        if (openSpots.size() == 0) {
            Toast.makeText(activity, "Here Lost Maybe?", Toast.LENGTH_LONG).show();
            return;
        }

        XYLocation location = openSpots.get(this.generateRandom(openSpots.size(), 0));

        int value = random.nextBoolean() ? 2 : 4;

        Block block = new Block(activity, relativeLayout, location.getX(),
                location.getY(), value);

        this.blocks.add(block);

        this.locationValue[location.getX()][location.getY()] = value;
    }

    // Loop that will update the block every tick
    @Override
    public void run() {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        for(Block block : blocks) {
                            block.tick();
                        }
                        checkDoneMoving();
                    }
                }
        );
    }

    public boolean isDoneMoving() {
        boolean allDone = true;
        for (int i = 0; i < blocks.size(); ++i) {
            if (blocks.get(i).isMoving()) {
                allDone = false;
            }
        }
        return allDone;
    }

    public void checkDoneMoving() {
        if (generateBlock) {
            if (isDoneMoving()) {
                this.checkOverlaps();
                this.createBlock();
                this.generateBlock = false;
            }
        }
    }

    public Block getBlockByLocation(int x, int y) {
        for (int i = 0; i < blocks.size(); ++i) {
            Block block = blocks.get(i);
            block.updateBoardY();
            block.updateBoardX();
            if (block.getBoardX() == x && block.getBoardY() == y) {
                return block;
            }
        }
        return null;
    }

    // Set the direction of the block
    public void setDirection(Direction dir) {
        if (!isDoneMoving()) {
            Log.d("DEBUG", "NOT DONE MOVING");
            return;
        }

        boolean noChanges = true;

        noChanges = false;
        switch (dir) {
            case UP: {
                for (int x = 0; x < 4; ++x) {
                    for (int y = 0; y < 3; ++y) {
                        for (int i = y + 1; i < 4; ++i) {
                            if (locationValue[x][y] != 0 && locationValue[x][y] == locationValue[x][i]) {
                                Block block = this.getBlockByLocation(x, i);
                                block.setBound(Locations.getSlotY(y));
                                block.setNewDir(dir);
                                locationValue[x][y] *= 2;
                                locationValue[x][i] = 0;
                                noChanges = false;
                            } else if (locationValue[x][y] == 0 && locationValue[x][i] != 0) {
                                Block block = this.getBlockByLocation(x, i);
                                block.setBound(Locations.getSlotY(y));
                                block.setNewDir(dir);
                                locationValue[x][y] = locationValue[x][i];
                                locationValue[x][i] = 0;
                                noChanges = false;
                            }
                        }
                    }
                }
                break;
            }
            case DOWN: {
                for (int x = 3; x >= 0; --x) {
                    for (int y = 3; y >= 1; --y) {
                        for (int i = y - 1; i >= 0; --i) {
                            if (locationValue[x][y] != 0 && locationValue[x][y] == locationValue[x][i]) {
                                Block block = this.getBlockByLocation(x, i);
                                block.setBound(Locations.getSlotY(y));
                                block.setNewDir(dir);
                                locationValue[x][y] *= 2;
                                locationValue[x][i] = 0;
                                noChanges = false;
                            } else if (locationValue[x][y] == 0 && locationValue[x][i] != 0) {
                                Block block = this.getBlockByLocation(x, i);
                                block.setBound(Locations.getSlotY(y));
                                block.setNewDir(dir);
                                locationValue[x][y] = locationValue[x][i];
                                locationValue[x][i] = 0;
                                noChanges = false;
                            }
                        }
                    }
                }
                break;
            }
            case LEFT: {
                for (int y = 0; y < 4; ++y) {
                    for (int x = 0; x < 3; ++x) {
                        for (int i = x + 1; i < 4; ++i) {
                            if (locationValue[x][y] != 0 && locationValue[x][y] == locationValue[i][y]) {
                                Block block = this.getBlockByLocation(i, y);
                                block.setBound(Locations.getSlotX(x));
                                block.setNewDir(dir);
                                locationValue[x][y] *= 2;
                                locationValue[i][y] = 0;
                                noChanges = false;
                            } else if (locationValue[x][y] == 0 && locationValue[i][y] != 0) {
                                Block block = this.getBlockByLocation(i, y);
                                block.setBound(Locations.getSlotX(x));
                                block.setNewDir(dir);
                                locationValue[x][y] = locationValue[i][y];
                                locationValue[i][y] = 0;
                                noChanges = false;
                            }
                        }
                    }
                }
                break;
            }
            case RIGHT: {
                for (int y = 3; y >= 0; --y) {
                    for (int x = 3; x >= 1; --x) {
                        for (int i = x - 1; i >= 0; --i) {
                            if (locationValue[x][y] != 0 && locationValue[x][y] == locationValue[i][y]) {
                                Block block = this.getBlockByLocation(i, y);
                                block.setBound(Locations.getSlotX(x));
                                block.setNewDir(dir);
                                locationValue[x][y] *= 2;
                                locationValue[i][y] = 0;
                                noChanges = false;
                            } else if (locationValue[x][y] == 0 && locationValue[i][y] != 0) {
                                Block block = this.getBlockByLocation(i, y);
                                block.setBound(Locations.getSlotX(x));
                                block.setNewDir(dir);
                                locationValue[x][y] = locationValue[i][y];
                                locationValue[i][y] = 0;
                                noChanges = false;
                            }
                        }
                    }
                }
                break;
            }
        }

        if (!noChanges) {
            generateBlock = true;
        }
    }

    public int generateRandom(int high, int low) {
        return this.random.nextInt(high - low) + low;
    }

    public class XYLocation {
        private int x, y;

        public XYLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
