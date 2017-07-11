package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.TextView;

import static lab4.ca.uwaterloo.lab0_202_10.lab4.Locations.*;

public class Block extends AppCompatImageView {

    private TextView number;

    private int value;

    // Set bounds for block location
    // Coords determined from size of phone, make this eventually compute on startup
    // Current block location
    private float x;
    private float y;

    private int boardX;
    private int boardY;


    // Current direction
    private Direction currentDirection = Direction.NONE;
    // Max velocity
    private float velocity = 4f;
    // Max acceleration
    private float acceleration = 1f;
    // Size of image
    private final float IMAGE_SCALE = 0.47f;

    private boolean moving = false;

    private float bound = LEFT_BOUND;

    public Block(Context myContext, TextView number, float x, float y, int initialVal){
        super(myContext);
        this.value = initialVal;
        this.number = number;
        number.setText(this.value + "");
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        this.x = x;
        this.y = y;
        this.setTextLocation(x, y);
        setTextLocation(x, y);
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
        velocity = 8;
    }

    public void tick() {

        this.updateBoardX();
        this.updateBoardY();

        Log.d("DEBUG", "(" + this.boardX + "," + this.boardY + ")");

        // Make sure block is within bounds
        if (currentDirection == Direction.LEFT) {
            if (x > LEFT_BOUND) {
                if (x - velocity < LEFT_BOUND) {
                    // If moving block puts outside bounds set block location
                    x = LEFT_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    // Else move block with velocity
                    x -= velocity;
                }
            }
        }
        else if (currentDirection == Direction.RIGHT) {
            if (x < RIGHT_BOUND) {
                if (x + velocity > RIGHT_BOUND) {
                    x = RIGHT_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    x += velocity;
                }
            }
        }
        else if (currentDirection == Direction.UP) {
            if (y > UPPER_BOUND) {
                if (y - velocity < UPPER_BOUND) {
                    y = UPPER_BOUND;
                    currentDirection = Direction.NONE;
                } else {
                    y -= velocity;
                }
            }
        }
        else if (currentDirection == Direction.DOWN) {
            if (y < LOWER_BOUND) {
                if (y + velocity > LOWER_BOUND) {
                    y = LOWER_BOUND;
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
        this.number.setX(x + BLOCK_LENGTH_X * 3 / 2);
        this.number.setY(y + BLOCK_LENGTH_Y * 3 / 2);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.number.setText(value + "");
    }
}
