package eu.javaspecialists.tjsn.issue214;

import java.util.concurrent.*;

public class CompletionServiceExample {
    public static void main(String... args) throws Exception {
        try (DotPrinter dp = new DotPrinter()) {
            ExecutorService pool = Executors.newCachedThreadPool();
            CompletionService<Integer> service =
                    new ExecutorCompletionService<>(pool);
            for (int i = 0; i < 10; i++) {
                int sleeptime = 5 - i % 5;
                int order = i;
                service.submit(() -> { // time to get used to lambdas?
                    TimeUnit.SECONDS.sleep(sleeptime);
                    return order;
                });
            }

            for (int i = 0; i < 10; i++) {
                System.out.printf("Job %d is done%n", service.take().get());
            }
            pool.shutdown();
        }
    }
}
