package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.TextView;

public class Block extends AppCompatImageView {

    private TextView number;

    private int value;

    // Set bounds for block location
    // Coords determined from size of phone, make this eventually compute on startup
    final float LEFT_BOUND = -100;
    final float RIGHT_BOUND = 450;
    final float UPPER_BOUND = -100;
    final float LOWER_BOUND = 450;
    // Current block location
    private float x;
    private float y;
    // Current direction
    private Direction currentDirection = Direction.NONE;
    // Max velocity
    private float velocity = 4f;
    // Max acceleration
    private float acceleration = 1f;
    // Size of image
    private final float IMAGE_SCALE = 0.5f;

    public Block(Context myContext, TextView number, int x, int y, int initialVal){
        super(myContext);
        this.value = initialVal;
        this.number = number;
        Log.d("DEBUG", this.value + "");
        number.setText(this.value + "");
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        this.x = x;
        this.y = y;
        this.setX(x);
        this.setY(y);
        number.setX(x + 50);
        number.setY(y + 50);
        number.bringToFront();
    }

    public void setNewDir(Direction newDir){
        currentDirection = newDir;
        velocity = 8;
    }

    public void tick() {
        // Make sure block is within bounds
        if (currentDirection == Direction.LEFT) {
            if (x > LEFT_BOUND) {
                if (x - velocity < LEFT_BOUND) {
                    // If moving block puts outside bounds set block location
                    x = LEFT_BOUND;
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
                } else {
                    x += velocity;
                }
            }
        }
        else if (currentDirection == Direction.UP) {
            if (y > UPPER_BOUND) {
                if (y - velocity < UPPER_BOUND) {
                    y = UPPER_BOUND;
                } else {
                    y -= velocity;
                }
            }
        }
        else if (currentDirection == Direction.DOWN) {
            if (y < LOWER_BOUND) {
                if (y + velocity > LOWER_BOUND) {
                    y = LOWER_BOUND;
                } else {
                    y += velocity;
                }
            }
        }

        this.setX(x);
        this.setY(y);

        // Add some acceleration for smooth look
        velocity += acceleration;

    }

}
