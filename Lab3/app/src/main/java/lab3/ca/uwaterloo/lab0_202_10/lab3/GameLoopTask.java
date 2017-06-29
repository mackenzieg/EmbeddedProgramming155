package lab3.ca.uwaterloo.lab0_202_10.lab3;
import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import java.util.TimerTask;

public class GameLoopTask extends TimerTask{
    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private Direction currDirection = Direction.NONE;

    private GameBlock myGameBlock;

    public GameLoopTask(Activity myAct, Context myCon, RelativeLayout myRelLay){
        myActivity=myAct;
        myContext=myCon;
        myRL=myRelLay;
        createBlock();
    }
    @Override
    public void run(){
        myActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        myGameBlock.move();
                    }
                }
        );
    }
    private void createBlock(){
        GameBlock firstBlock = new GameBlock(myContext, 10, 10);
        this.myGameBlock = firstBlock;
        myRL.addView(firstBlock);
    }
    public void getDir (Direction nextDir) {
        this.currDirection = nextDir;
        myGameBlock.setNewDir(nextDir);
    }
}
