package lab4.ca.uwaterloo.lab0_202_10.lab4;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

public abstract class GameBlockTemplate extends AppCompatImageView {

    public GameBlockTemplate(Context context) {
        super(context);
    }

    abstract void tick();

    abstract void setNewDir(Direction direction);

}
