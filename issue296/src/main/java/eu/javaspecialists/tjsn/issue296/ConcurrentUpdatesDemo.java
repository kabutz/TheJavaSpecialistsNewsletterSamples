package eu.javaspecialists.tjsn.issue296;

import java.util.concurrent.*;

public class ConcurrentUpdatesDemo {
    public static void main(String... args) throws InterruptedException {
        var set = new ConcurrentLinkedReducedHashSet<Integer>();
        ExecutorService pool = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 16; i++) {
            pool.submit(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int j = 0; j < 100_000; j++) {
                    int value = random.nextInt(0, 10);
                    set.remove(value);
                    set.add(value);
                }
            });
        }
        pool.shutdown();
        while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("Waiting for pool to shut down");
        }
        System.out.println("set = " + set);
    }
}
