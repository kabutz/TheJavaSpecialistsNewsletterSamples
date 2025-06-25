package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class TakeOnBlockingQueue {
    public static void main(String... args) {
        test(new ArrayBlockingQueue<>(10));
        test(new LinkedBlockingQueue<>());
        test(new LinkedTransferQueue<>());
        test(new PriorityBlockingQueue<>());
        test(new SynchronousQueue<>());
    }

    public static void test(BlockingQueue<Integer> queue) {
        System.out.print(queue.getClass().getSimpleName());
        Thread.interrupted(); // clear interrupt
        queue.offer(42); // might not add if no available capacity
        try {
            Thread.currentThread().interrupt(); // self-interrupt
            queue.take(); // shouldn't really go into the WAITING state
            System.out.println(" - no early interrupt on take()");
        } catch (InterruptedException e) {
            System.out.println(" - early interrupt on take()");
        }
    }
}
