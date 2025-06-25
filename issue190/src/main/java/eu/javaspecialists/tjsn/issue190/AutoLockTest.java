package eu.javaspecialists.tjsn.issue190;

import java.util.concurrent.locks.*;

import static eu.javaspecialists.tjsn.issue190.AutoLock.lock;

public class AutoLockTest {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String... args) {
        // The old way - far more verbose
        lock.lock();
        try {
            printLockStatus();
        } finally {
            lock.unlock();
        }

        // Heinz's new way
        try (AutoLock al = lock(lock)) {
            printLockStatus();
        }
        printLockStatus();
    }

    private static void printLockStatus() {
        System.out.println("We are locked: " + lock.isHeldByCurrentThread());
    }
}
