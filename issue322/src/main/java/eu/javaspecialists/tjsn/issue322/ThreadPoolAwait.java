package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class ThreadPoolAwait {
    public static void main(String... args) {
        test(Executors.newSingleThreadExecutor());
        test(Executors.newCachedThreadPool());
        test(Executors.newWorkStealingPool());
        test(Executors.newVirtualThreadPerTaskExecutor());
    }

    public static void test(ExecutorService pool) {
        System.out.print(pool.getClass().getSimpleName());
        Thread.interrupted(); // clear interrupt
        pool.shutdown();
        try {
            Thread.currentThread().interrupt(); // self-interrupt
            pool.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(" - no early interrupt");
        } catch (InterruptedException e) {
            System.out.println(" - early interrupt");
        }
    }
}
