import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Timestamp {

    private static volatile long now;

    static {
        now = System.currentTimeMillis();
        Runnable task = () -> now = System.currentTimeMillis();
        new ScheduledThreadPoolExecutor(1/*, r -> {
                Thread thread = new Thread("cache-time-millis");
                thread.setDaemon(true);
                return thread;
            }*/).scheduleAtFixedRate(task, 0, 1, TimeUnit.MILLISECONDS);
    }

    private Timestamp() {
    }

    /**
     * 获取缓存时间戳
     */
    public static long cacheTimeMillis() {
        return now;
    }

    /**
     * 直接获取系统时间戳
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}