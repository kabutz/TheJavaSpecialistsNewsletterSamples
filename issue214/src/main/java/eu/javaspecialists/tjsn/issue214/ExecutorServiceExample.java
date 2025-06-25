package eu.javaspecialists.tjsn.issue214;

import java.util.*;
import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String... args) throws Exception {
        try (DotPrinter dp = new DotPrinter()) {
            ExecutorService pool = Executors.newCachedThreadPool();
            Collection<Future<Integer>> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                int sleeptime = 5 - i % 5;
                int order = i;
                futures.add(pool.submit(() -> {
                    TimeUnit.SECONDS.sleep(sleeptime);
                    return order;
                }));
            }

            for (Future<Integer> future : futures) {
                System.out.printf("Job %d is done%n", future.get());
            }
            pool.shutdown();
        }
    }
}
