import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeTool {

    private TimeTool() {
    }

    /**
     * 获取缓存时间戳
     */
    public static long cacheTimeMillis() {
        return CacheTime.single.now;
    }

    /**
     * 直接获取系统时间戳
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    private static class CacheTime {

        private static final CacheTime single = new CacheTime();

        private volatile long now;

        private CacheTime() {
            now = System.currentTimeMillis();
            Runnable task = () -> now = System.currentTimeMillis();
            new ScheduledThreadPoolExecutor(1, r -> {
                Thread thread = new Thread("cache-time-millis");
                thread.setDaemon(true);
                return thread;
            }).scheduleAtFixedRate(task, 1, 1, TimeUnit.MILLISECONDS);
        }
    }
}
