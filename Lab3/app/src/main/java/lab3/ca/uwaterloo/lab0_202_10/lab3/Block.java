package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

public class Block extends View {

    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    private int x = 0;
    private int y = 0;

    public Block(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, 100, 100, paint);
    }
}
