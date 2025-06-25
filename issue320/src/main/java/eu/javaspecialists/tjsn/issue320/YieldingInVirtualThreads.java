package eu.javaspecialists.tjsn.issue320;

import java.util.concurrent.*;

public class YieldingInVirtualThreads {
    public static void main(String... args)
            throws InterruptedException {
        var threads = new ConcurrentSkipListSet<>();
        Thread.startVirtualThread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                threads.add("" + Thread.currentThread());
                Thread.yield();
            }
        }).join();
        threads.forEach(System.out::println);
    }
}
