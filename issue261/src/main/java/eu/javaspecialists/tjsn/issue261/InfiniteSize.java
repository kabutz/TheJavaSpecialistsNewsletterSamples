package eu.javaspecialists.tjsn.issue261;

import java.util.concurrent.*;

public class InfiniteSize {
    public static void main(String... args) {
        var queue = new ConcurrentLinkedQueue<String>();
        for (int i = 0; i < 40_000_000; i++) {
            queue.add("test");
        }

        var phaser = new Phaser(900);
        for (int i = 0; i < 1000; i++) { // 1000 threads?  Seriously?
            Thread t = new Thread(() -> {
                phaser.arriveAndAwaitAdvance();
                while (true) {
                    queue.add("test");
                    queue.remove();
                }
            });
            t.setDaemon(true);
            t.start();
        }
        var time = System.nanoTime();
        try {
            System.out.println("Measuring queue size");
            System.out.println(queue.size()); // might never return
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
