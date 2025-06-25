package eu.javaspecialists.tjsn.issue162;

import java.util.*;

public class ConcurrentExceptionTest {
    private static volatile boolean running = true;

    public static void main(String[] args)
            throws InterruptedException {
        final Collection shared = new ArrayList();
        Thread reader = new Thread("Reader") {
            public void run() {
                while (running) {
                    // ConcurrentModificationException happens here
                    for (Object o : shared) {
                    }
                }
            }
        };
        reader.start();

        Thread writer = new Thread("Writer") {
            public void run() {
                while (running) {
                    // the thread modifying the collection does
                    // not see any exception
                    shared.add("Hello");
                    shared.remove("Hello");
                }
            }
        };
        writer.start();

        Thread.sleep(2000);

        System.out.println("reader alive? " + reader.isAlive());
        System.out.println("writer alive? " + writer.isAlive());
        running = false;
    }
}
