package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

public class Block extends View {

    private final int CANVAS_WIDTH = 4*250;
    private final int CANVAS_HEIGHT = 4*250;

    private final int BLOCK_WIDTH = 250;
    private final int BLOCK_HEIGHT = 250;

    private final int WIDTH = 3*250;
    private final int HEIGHT = 3*250;

    private int x;
    private int y;

    private final int MAX_VELOCITY = 5;

    private Direction direction = Direction.NONE;

    public Block(Context context, int x, int y) {
        super(context);
        this.y = y;
        this.x = x;
    }

    public void update() {
        if (this.direction == Direction.NONE) {
            return;
        }

        switch (this.direction) {
            case UP: {
                if (y == 0) {
                    return;
                }

                this.y -= MAX_VELOCITY;

                break;
            }
            case DOWN: {
                if (y == HEIGHT) {
                    return;
                }

                this.y += MAX_VELOCITY;
                break;
            }
            case LEFT: {
                if (x == WIDTH) {
                    return;
                }

                this.x += MAX_VELOCITY;
                break;
            }
            case RIGHT: {
                if (x == 0) {
                    return;
                }

                this.x -= MAX_VELOCITY;
                break;
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(x, x - BLOCK_WIDTH, x - WIDTH, y, paint);
    }
}
