package eu.javaspecialists.tjsn.issue226;

import java.util.concurrent.*;

public class ThreadFromThreadPool {
    public static void main(String... args)
            throws InterruptedException {
        System.setSecurityManager(
                new ThreadWatcher(
                        DemoSupport.createPredicate(),
                        DemoSupport.createConsumer()
                )
        );

        ExecutorService pool = Executors.newFixedThreadPool(10);
        Future<?> future = pool.submit(() ->
                new Thread(DemoSupport.createHelloJob(),
                        "This should print a warning 1")
        );
        try {
            future.get();
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        }
        pool.shutdown();

        System.setSecurityManager(null);
    }
}
