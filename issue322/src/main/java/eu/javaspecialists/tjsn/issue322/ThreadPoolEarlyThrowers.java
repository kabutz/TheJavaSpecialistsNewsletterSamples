package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class ThreadPoolEarlyThrowers {
    public static void main(String... args) {
        ThreadPoolAwait.test(proxy(Executors.newSingleThreadExecutor()));
        ThreadPoolAwait.test(proxy(Executors.newCachedThreadPool()));
        ThreadPoolAwait.test(proxy(Executors.newWorkStealingPool()));
        ThreadPoolAwait.test(proxy(Executors.newVirtualThreadPerTaskExecutor()));
    }

    private static ExecutorService proxy(ExecutorService pool) {
        return EarlyInterruptedExceptionThrower.proxy(
                ExecutorService.class, pool
        );
    }
}
