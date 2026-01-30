package eu.javaspecialists.tjsn.issue332;

import java.util.concurrent.*;
import java.util.stream.*;

public class CompletableFutureDemo {
    public static void main(String... args) {
        System.setProperty("java.util.concurrent.ForkJoinPool." +
                "common.parallelism", "0");
        var time = System.nanoTime();
        try {
            var numberOfThreads = IntStream.range(0, 1_000_000)
                    .mapToObj(i -> CompletableFuture.supplyAsync(
                            () -> Thread.currentThread()))
                    .toList()
                    .stream()
                    .map(CompletableFuture::join)
                    .distinct()
                    .count();
            System.out.printf("numberOfThreads = %,d%n", numberOfThreads);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
        // Java 24: 1000000
        // Java 25: 2
    }
}
