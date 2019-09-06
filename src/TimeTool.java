import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeTool {

    private static class Instance {
        private static final TimeTool single = new TimeTool();
    }

    public static TimeTool single() {
        return Instance.single;
    }

    private volatile long now;

    private TimeTool() {
        now = System.currentTimeMillis();
        Runnable task = () -> now = System.currentTimeMillis();
        new ScheduledThreadPoolExecutor(1, r -> {
            Thread thread = new Thread("current-time-millis");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(task, 1, 1, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return now;
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
