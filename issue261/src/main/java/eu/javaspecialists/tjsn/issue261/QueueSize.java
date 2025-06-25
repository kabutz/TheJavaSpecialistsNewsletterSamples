package eu.javaspecialists.tjsn.issue261;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class QueueSize {
    public static final int RESIDENT_QUEUE_SIZE = 10;
    public static final int ADD_AND_REMOVES = 1_000_000;

    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            // size either 10 or 11
            test(LinkedBlockingQueue::new);

            // size always >= 10
            test(LinkedTransferQueue::new);
            test(ConcurrentLinkedQueue::new);
            System.out.println();
        }
    }

    private static void test(Supplier<Queue<String>> queueType) {
        var queue = queueType.get();
        for (int i = 0; i < RESIDENT_QUEUE_SIZE; i++) {
            queue.add("test" + i);
        }

        var thread = addRemoveThread(queue);

        var maxSize = 0;
        while (thread.isAlive()) {
            maxSize = Math.max(maxSize, queue.size());
        }
        System.out.printf(Locale.US, "%s: maxSize=%d%n",
                queue.getClass().getSimpleName(), maxSize);
    }

    private static Thread addRemoveThread(Queue<String> queue) {
        var thread = new Thread(() -> {
            for (int i = 0; i < ADD_AND_REMOVES; i++) {
                queue.add("test" + (i + RESIDENT_QUEUE_SIZE));
                queue.remove();
            }
        }, "addRemoveThread");
        thread.start();
        return thread;
    }
}
