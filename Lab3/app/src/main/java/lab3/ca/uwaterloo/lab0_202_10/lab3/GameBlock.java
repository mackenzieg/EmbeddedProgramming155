package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.content.Context;
import android.widget.ImageView;

public class GameBlock extends ImageView {

    final float LEFT_BOUND = -60;
    final float RIGHT_BOUND = 749;
    final float UPPER_BOUND = -60;
    final float LOWER_BOUND = 749;
    private float myXpos;
    private float myYpos;
    private Direction myDir;
    private float velocity = 5;
    private float acceleration = 1;
    private final float IMAGE_SCALE = 1f;

    public GameBlock(Context myContext,int Xpos, int Ypos){
        super(myContext);
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        myXpos = Xpos;
        myYpos = Ypos;
        this.setX(myXpos);
        this.setY(myYpos);
    }

    public void setNewDir(Direction newDir){
        myDir = newDir;
        velocity = 8;
    }

    public void move() {
        boolean iAmMoving = false;
        if (myDir == Direction.LEFT && iAmMoving == false) {
            iAmMoving = true;
            if (myXpos > LEFT_BOUND) {
                if (myXpos - velocity < LEFT_BOUND) {
                    myXpos = LEFT_BOUND;
                    iAmMoving = false;
                } else {
                    myXpos -= velocity;
                }
            }
        }
        else if (myDir == (Direction.RIGHT) && iAmMoving == false) {
            iAmMoving = true;
            if (myXpos < RIGHT_BOUND) {
                if (myXpos + velocity > RIGHT_BOUND) {
                    myXpos = RIGHT_BOUND;
                    iAmMoving = false;
                } else {
                    myXpos += velocity;
                }
            }
        }
        else if (myDir == Direction.UP && iAmMoving == false) {
            iAmMoving = true;
            if (myYpos > UPPER_BOUND) {
                if (myYpos - velocity < UPPER_BOUND) {
                    myYpos = UPPER_BOUND;
                    iAmMoving = false;
                } else {
                    myYpos -= velocity;
                }
            }
        }
        else if (myDir == Direction.DOWN && iAmMoving == false) {
            iAmMoving = true;
            if (myYpos < LOWER_BOUND) {
                if (myYpos + velocity > LOWER_BOUND) {
                    myYpos = LOWER_BOUND;
                    iAmMoving = false;
                } else {
                    myYpos += velocity;
                }
            }
        }

        this.setX(myXpos);
        this.setY(myYpos);

        velocity += acceleration;

    }

}
