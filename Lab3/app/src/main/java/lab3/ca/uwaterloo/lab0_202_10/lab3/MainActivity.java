package lab3.ca.uwaterloo.lab0_202_10.lab3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wtw.BuiltDevice;
import com.wtw.Device;
import com.wtw.compression.MeanCompressor;
import com.wtw.detectors.DefaultGestureDetector;
import com.wtw.distance.AbsoluteDistance;
import com.wtw.distance.EuclideanDistance;
import com.wtw.event.EventHandler;
import com.wtw.event.EventListener;
import com.wtw.event.events.PostCompressionEvent;
import com.wtw.event.events.PostTimeWarpEvent;
import com.wtw.event.events.StartTimeWarpEvent;
import com.wtw.filters.DifferenceEquivalenceFilter;
import com.wtw.filters.LowPassFilter;
import com.wtw.timeseries.TimeSeries;
import com.wtw.timewarp.SlowTimeWarpCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private final List<TimeSeries> recordedGestures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AtomicInteger numCaptured = new AtomicInteger(0);

        final BuiltDevice builtDevice = new Device()
                .addCompressor(new MeanCompressor())
                .addFilter(new LowPassFilter(3))
                .addFilter(new DifferenceEquivalenceFilter(3))
                .setGestureDetector(new DefaultGestureDetector(new EuclideanDistance()))
                .addTimeWarpCalculator(new SlowTimeWarpCalculator(new AbsoluteDistance()))
                .build().setStartCompression(true);

        builtDevice.getEventBus().register(new EventListener() {
            @EventHandler
            public void getCompressed(PostCompressionEvent postCompressionEvent) {
                if (numCaptured.getAndIncrement() <= 3) {
                    recordedGestures.add(postCompressionEvent.getAfter());
                } else {
                    if (!builtDevice.getTimeWarpManager().isStarted()) {
                        builtDevice.setStartTimeWarp(true);
                    }
                }
            }

            @EventHandler
            public void postTimeWarp(PostTimeWarpEvent postTimeWarpEvent) {
                System.out.println(postTimeWarpEvent.getDistance());
            }

            @EventHandler
            public void compare(StartTimeWarpEvent startTimeWarpEvent) {
                for (TimeSeries timeSeries : recordedGestures) {
                    startTimeWarpEvent.addComparison(timeSeries);
                }
            }
        });
    }
}
