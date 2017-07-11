package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Block extends AppCompatImageView {

    public final static Locations LOCATIONS = new Locations();

    private GameLoopTask gameLoop;

    private TextView number;

    private float endWall;

    private int value;

    private LinearLayout linearLayout;

    private boolean deleted = false;

    private boolean merged = false;

    // Set bounds for block location
    // Coords determined from size of phone, make this eventually compute on startup
    // Current block location
    private int boardX;
    private int boardY;
    private float x;
    private float y;
    // Current direction
    private Direction currentDirection = Direction.NONE;
    // Max velocity
    private float velocity = 4f;
    // Max acceleration
    private float acceleration = 1f;
    // Size of image
    private final float IMAGE_SCALE = 0.47f;

    private final RelativeLayout relativeLayout;

    public Block(Context context, GameLoopTask gameLoop, float x, float y, RelativeLayout relativeLayout, int initialVal) {
        super(context);
        this.value = initialVal;
        this.gameLoop = gameLoop;
        this.number = new TextView(context);
        relativeLayout.addView(number);
        number.setText(this.value + "");
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        this.x = x;
        this.y = y;
        this.setTextLocation(x, y);
        this.relativeLayout = relativeLayout;
        setTextLocation(x, y);
    }

    public void setNewDir(Direction newDir) {
        currentDirection = newDir;
        velocity = 8;
    }

    public void tick() {
        // Make sure block is within bounds
        this.updateColumn();
        this.updateRow();
        this.displayValue(this.value);
        if (currentDirection == Direction.LEFT) {
            if (x > LOCATIONS.LEFT_BOUND) {
                if (x - velocity < LOCATIONS.LEFT_BOUND) {
                    // If moving block puts outside bounds set block location
                    x = LOCATIONS.LEFT_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    // Else move block with velocity
                    x -= velocity;
                }
            }
        } else if (currentDirection == Direction.RIGHT) {
            if (x < LOCATIONS.RIGHT_BOUND) {
                if (x + velocity > LOCATIONS.RIGHT_BOUND) {
                    x = LOCATIONS.RIGHT_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    x += velocity;
                }
            }
        } else if (currentDirection == Direction.UP) {
            if (y > LOCATIONS.UPPER_BOUND) {
                if (y - velocity < LOCATIONS.UPPER_BOUND) {
                    y = LOCATIONS.UPPER_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    y -= velocity;
                }
            }
        } else if (currentDirection == Direction.DOWN) {
            if (y < LOCATIONS.LOWER_BOUND) {
                if (y + velocity > LOCATIONS.LOWER_BOUND) {
                    y = LOCATIONS.LOWER_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    y += velocity;
                }
            }
        }

        this.setX(x);
        this.setY(y);

        setTextLocation(x, y);

        // Add some acceleration for smooth look
        velocity += acceleration;
    }

    public void setTextLocation(float x, float y) {
        this.number.setX(x + LOCATIONS.BLOCK_LENGTH_X * 3 / 2);
        this.number.setY(y + LOCATIONS.BLOCK_LENGTH_Y * 3 / 2);
    }

    public int getValue() {
        return value;
    }

    public void preCalcLocation() {
        if (currentDirection == Direction.NONE) {
            return;
        }

        int column = this.getColumn();
        int row = this.getRow();

        int[] line = new int[4];
        int spacesToMove = 0;

        if (currentDirection == Direction.LEFT) {
            for (int i = 0; i < 4; ++i) {
                if (i <= column) {
                    line[i] = this.gameLoop.getLocationValues()[row][i];
                } else {
                    line[i] = -2;
                }
            }
            spacesToMove = calcMoveDis(line);
            endWall = LOCATIONS.BLOCK_LENGTH_X * (column - spacesToMove) + LOCATIONS.LEFT_BOUND;
        } else if (currentDirection == Direction.RIGHT) {
            for (int i = 3; i >= 0; --i) {
                if (i >= column) {
                    line[3 - i] = this.gameLoop.getLocationValues()[row][i];
                } else {
                    line[3 - i] = -2;
                }
            }
            spacesToMove = calcMoveDis(line);
            endWall = LOCATIONS.BLOCK_LENGTH_X * (column + spacesToMove) + LOCATIONS.LEFT_BOUND;
        } else if (currentDirection == Direction.UP) {
            for (int i = 0; i < 4; ++i) {
                if (i <= column) {
                    line[i] = this.gameLoop.getLocationValues()[i][column];
                } else {
                    line[i] = -2;
                }
            }
            spacesToMove = calcMoveDis(line);
            endWall = LOCATIONS.BLOCK_LENGTH_Y * (row - spacesToMove) + LOCATIONS.UPPER_BOUND;
        } else if (currentDirection == Direction.DOWN) {
            for (int i = 3; i >= 0; --i) {
                if (i >= column) {
                    line[3 - i] = this.gameLoop.getLocationValues()[i][column];
                } else {
                    line[3 - i] = -2;
                }
            }
            spacesToMove = calcMoveDis(line);
            endWall = LOCATIONS.BLOCK_LENGTH_Y * (row + spacesToMove) + LOCATIONS.UPPER_BOUND;
        }
    }

    private int calcMoveDis(int[] line) {
        int empty = 0, merge = 0;

        int blockPosition = 0;

        for (int i = 0; i < 4; ++i) {
            if (line[i] == 0) {
                ++empty;
            } else if (line[i] != -2) {
                ++blockPosition;
            } else {
                --blockPosition;
                break;
            }
        }

        if (blockPosition == 4) {
            blockPosition = 3;
        }

        int previousValue = -1;

        for (int i = 0; i < 4; ++i) {
            if (line[i] == -2) {
                break;
            }

            if (line[i] != 0) {
                if (previousValue == -1) {
                    previousValue = line[i];
                } else {
                    if (line[i] == previousValue) {
                        ++merge;
                        previousValue = -1;
                        if (i == blockPosition) {
                            deleted = true;
                            merged = true;
                        }
                    }
                }
            }
        }
        return (merge + empty);
    }

    public boolean isDoneMoving() {
        return this.currentDirection == Direction.NONE;
    }

    public int getRow() {
        this.updateRow();
        this.updateColumn();
        return this.boardY;
    }

    public int getColumn() {
        this.updateRow();
        this.updateColumn();
        return this.boardX;
    }

    private void updateRow() {
        this.boardY = (int) ((y - LOCATIONS.LEFT_BOUND) / (LOCATIONS.BLOCK_LENGTH_Y));
    }

    private void updateColumn() {
        this.boardX = (int) ((x - LOCATIONS.LEFT_BOUND) / (LOCATIONS.BLOCK_LENGTH_X));
    }

    public void destroy() {
    }

    public boolean samePosition(Block block) {
        if (this.getRow() == block.getRow() && this.getColumn() == block.getColumn()) {
            return true;
        }
        return false;
    }

    public void doubleValue() {
        this.value *= 2;
        this.displayValue(this.value);
    }

    public void displayValue(int value) {
        this.number.setText(String.valueOf(this.value));
    }
}
