package core.utils;

import java.util.concurrent.*;

public class TaskExecutor {

    private static final ExecutorService executorService = new ThreadPoolExecutor(
            3,
            10,
            5,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static <T> Future<T> submit(Callable<T> callable) {
        return executorService.submit(callable);
    }

    public static Future<?> submit(Runnable runnable) {
        return executorService.submit(runnable);
    }

}
