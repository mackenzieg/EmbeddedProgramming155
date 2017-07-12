package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static lab4.ca.uwaterloo.lab0_202_10.lab4.Locations.*;

public class Block extends AppCompatImageView {

    private TextView numberDisplay;

    private int value;

    // Set bounds for block location
    // Coords determined from size of phone, make this eventually compute on startup
    // Current block location
    private int x;
    private int y;

    private int boardX;
    private int boardY;

    // Current direction
    private Direction currentDirection = Direction.NONE;
    // Max velocity
    private float velocity = 40f;
    // Max acceleration
    private float acceleration = 0f;
    // Size of image
    private final float IMAGE_SCALE = 0.47f;

    private int bound = LEFT_BOUND;

    public Block(Context context, RelativeLayout relativeLayout, int x, int y, int initialVal) {
        super(context);
        this.value = initialVal;
        this.numberDisplay = new TextView(context);
        numberDisplay.setTextColor(Color.RED);
        numberDisplay.setText(String.valueOf(initialVal));
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        this.setTextLocation(x, y);

        relativeLayout.addView(this);
        relativeLayout.addView(numberDisplay);
        numberDisplay.bringToFront();

        this.boardX = x;
        this.boardY = y;

        this.x = Locations.getSlotX(boardX);
        this.y = Locations.getSlotY(boardY);

        this.updateBoardX();
        this.updateBoardY();

        Log.d("Location:", "(" + this.x + "," + this.y + ")");
        Log.d("Board Coordinations: ", "(" + boardX + "," + boardY + ")");
    }

    public void updateBoardX() {
        this.boardX = Locations.getBoardX(this.x);
    }

    public void updateBoardY() {
        this.boardY = Locations.getBoardY(this.y);
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void setNewDir(Direction newDir){
        currentDirection = newDir;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean isMoving() {
        return this.currentDirection != Direction.NONE;
    }

    public void tick() {

        // Make sure block is within bounds
        if (currentDirection == Direction.LEFT) {
            if (x >= bound) {
                if (x - velocity <= bound) {
                    // If moving block puts outside bounds set block location
                    x = bound;
                    setNewDir(Direction.NONE);
                    this.updateBoardX();
                    this.updateBoardY();
                } else {
                    // Else move block with velocity
                    x -= velocity;
                }
            }
        }
        else if (currentDirection == Direction.RIGHT) {
            if (x <= bound) {
                if (x + velocity >= bound) {
                    x = bound;
                    setNewDir(Direction.NONE);
                    this.updateBoardX();
                    this.updateBoardY();
                } else {
                    x += velocity;
                }
            }
        }
        else if (currentDirection == Direction.UP) {
            if (y >= UPPER_BOUND) {
                if (y - velocity <= bound) {
                    y = bound;
                    setNewDir(Direction.NONE);
                    this.updateBoardX();
                    this.updateBoardY();
                } else {
                    y -= velocity;
                }
            }
        }
        else if (currentDirection == Direction.DOWN) {
            if (y <= bound) {
                if (y + velocity >= bound) {
                    y = bound;
                    setNewDir(Direction.NONE);
                    this.updateBoardX();
                    this.updateBoardY();
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

    public void setTextLocation(int x, int y) {
        this.numberDisplay.setX(x + BLOCK_LENGTH_X);
        this.numberDisplay.setY(y + BLOCK_LENGTH_Y);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.numberDisplay.setText(String.valueOf(value));
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public void destroy() {
        this.numberDisplay.setVisibility(View.GONE);
        this.setVisibility(View.GONE);
    }
}
