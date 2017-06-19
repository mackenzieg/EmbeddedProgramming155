package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wtw.BuiltDevice;
import com.wtw.Device;
import com.wtw.compression.MeanCompressor;
import com.wtw.detectors.DefaultGestureDetector;
import com.wtw.distance.EuclideanDistance;
import com.wtw.event.EventHandler;
import com.wtw.event.EventListener;
import com.wtw.event.events.PostCompressionEvent;
import com.wtw.event.events.PostTimeWarpEvent;
import com.wtw.event.events.StartTimeWarpEvent;
import com.wtw.filters.DifferenceEquivalenceFilter;
import com.wtw.filters.LowPassFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BuiltDevice builtDevice = new Device()
                .addCompressor(new MeanCompressor(5))
                .addFilter(new LowPassFilter(1))
                .addFilter(new DifferenceEquivalenceFilter(3))
                .setGestureDetector(new DefaultGestureDetector(new EuclideanDistance()))
//                .addTimeWarpCalculator(new SlowTimeWarpCalculator(new AbsoluteDistance()))
                .registerListener(new EventListener() {
                    @EventHandler
                    public void getCompressed(PostCompressionEvent postCompressionEvent) {
                        System.out.println("After");
                        System.out.println(postCompressionEvent.getAfter().toString());
                    }

                    @EventHandler
                    public void postTimeWarp(PostTimeWarpEvent postTimeWarpEvent) {
                        System.out.println(postTimeWarpEvent.getDistance());
                    }

                    @EventHandler
                    public void compare(StartTimeWarpEvent startTimeWarpEvent) {

                    }
                }).build().setStartCompression(true).setStartTimeWarp(true);
    }
}
