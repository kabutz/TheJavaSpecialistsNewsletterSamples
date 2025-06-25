package eu.javaspecialists.tjsn.issue323;

import java.lang.management.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;

public class DeadlockDemo {
    public static void main(String... args) {
        var lock1 = new Object();
        var lock2 = new Object();
        Thread.startVirtualThread(() -> lockBoth(lock1, lock2));
        Thread.startVirtualThread(() -> lockBoth(lock2, lock1));
        LockSupport.parkNanos(100_000_000L);

        System.out.println("Find all blocked threads");
        Threadables.stream()
                .filter(thread -> thread.getState() == Thread.State.BLOCKED)
                .forEach(thread -> {
                    System.out.println(thread + " " + thread.getState());
                    Stream.of(thread.getStackTrace())
                            .map(element -> "\t" + element)
                            .forEach(System.out::println);
                });

    }

    private static void lockBoth(Object first, Object second) {
        synchronized (first) {
            System.out.println("First locked");
            LockSupport.parkNanos(50_000_000L);
            synchronized (second) {
                System.out.println("Both locked");
            }
        }
    }
}
