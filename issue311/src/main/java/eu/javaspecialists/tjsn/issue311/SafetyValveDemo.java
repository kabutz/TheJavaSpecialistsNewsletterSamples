package eu.javaspecialists.tjsn.issue311;

import java.util.concurrent.*;
import java.util.function.*;

public class SafetyValveDemo {
    public static <T> T safetyValve(Supplier<T> streamTask) {
        return Thread.currentThread().isVirtual()
                && ForkJoinPool.getCommonPoolParallelism() > 1 ?
                ForkJoinPool.commonPool()
                        .submit(streamTask::get).join() :
                streamTask.get();
    }

    public static void main(String... args) {
        long time = System.nanoTime();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            pool.submit(() -> {
                var thread1 = Thread.currentThread().toString();
                System.out.println(thread1);
                var stats = safetyValve(() ->
                        ThreadLocalRandom.current().ints(100_000_000)
                                .parallel()
                                .sorted()
                                .summaryStatistics());
                var thread2 = Thread.currentThread().toString();
                System.out.println(thread2);
                if (!thread1.equals(thread2))
                    System.out.println("unmounted");
                else
                    System.out.println("maybe not unmounted");
                return stats;
            });
            for (int i = 0; i < 100000; i++) {
                pool.submit(() -> {/* empty task */});
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
