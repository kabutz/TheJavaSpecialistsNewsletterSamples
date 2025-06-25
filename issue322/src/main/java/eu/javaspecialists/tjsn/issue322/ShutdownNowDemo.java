package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class ShutdownNowDemo {
    public static void main(String... args) throws InterruptedException {
        var pool = Executors.newSingleThreadExecutor();
        var future = pool.submit(() -> {Thread.sleep(10000); return "done";});
        pool.shutdown(); // worker threads are left in peace
        Thread.sleep(100);
        System.out.println("isTerminated? " + pool.isTerminated());
        pool.shutdownNow(); // interrupts all the worker threads in pool
        Thread.sleep(100);
        System.out.println("isTerminated? " + pool.isTerminated());

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Future threw an exception: " + e.getCause());
        }
    }
}
