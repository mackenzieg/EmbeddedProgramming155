package lab3.ca.uwaterloo.lab0_202_10.lab3;

import com.wtw.BuiltDevice;
import com.wtw.event.EventHandler;
import com.wtw.event.EventListener;
import com.wtw.event.events.PostCompressionEvent;
import com.wtw.event.events.PostTimeWarpEvent;
import com.wtw.event.events.StartTimeWarpEvent;
import com.wtw.timeseries.TimeSeries;
import com.wtw.timeseries.TimeSeriesComparison;

import java.util.ArrayList;
import java.util.List;

import lab3.ca.uwaterloo.lab0_202_10.lab3.events.DetectedGestureEvent;


public class GestureManager {

    private final BuiltDevice builtDevice;

    private boolean startedTimeWarp = false;

    private final List<LabelledTimeSeries> gestures = new ArrayList<>();

    public GestureManager(BuiltDevice builtDevice, List<TimeSeries> savedGestures) {
        this(builtDevice);
    }

    public GestureManager(final BuiltDevice builtDevice) {
        this.builtDevice = builtDevice;
        this.builtDevice.getEventBus().register(new EventListener() {
            @EventHandler
            public void getCompressed(PostCompressionEvent postCompressionEvent) {
                if (gestures.size() >= Gestures.ALL_GESTURES.length) {
                    if (!startedTimeWarp) {
                        builtDevice.setStartTimeWarp(true);
                        startedTimeWarp = true;
                    }
                } else {
                    gestures.add(
                            new LabelledTimeSeries(postCompressionEvent.getAfter(), Gestures.ALL_GESTURES[gestures.size()]));
                }
            }

            @EventHandler
            public void compare(StartTimeWarpEvent startTimeWarpEvent) {
                for (LabelledTimeSeries timeSeries : gestures) {
                    startTimeWarpEvent.addComparison(timeSeries);
                }
            }

            @EventHandler
            public void postTimeWarp(PostTimeWarpEvent postTimeWarpEvent) {
                float lowestSum = 100000.0f;
                TimeSeriesComparison lowest = null;
                for (TimeSeriesComparison timeSeriesComparison : postTimeWarpEvent.getTimeWarpComparisonResults().getResults()) {
                    if (lowestSum >= timeSeriesComparison.getDistance()) {
                        lowestSum = timeSeriesComparison.getDistance();
                        lowest = timeSeriesComparison;
                    }
                }

                if (lowest != null && lowest.getReference() instanceof LabelledTimeSeries) {
                    DetectedGestureEvent event = new DetectedGestureEvent(((LabelledTimeSeries) lowest.getReference()).getType());
                    builtDevice.getEventBus().post(event);
                }
            }
        });
    }
}
