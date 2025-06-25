package eu.javaspecialists.tjsn.issue190;

import java.util.concurrent.locks.*;

import static eu.javaspecialists.tjsn.issue190.AutoLock.*;

public class AutoLockInterruptiblyTest {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String... args) throws InterruptedException {
        testLock();
        Thread.currentThread().interrupt();
        try {
            testLock();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    public static void testLock() throws InterruptedException {
        try (AutoLock al = lockInterruptibly(lock)) {
            printLockStatus();
        }
        printLockStatus();
    }

    private static void printLockStatus() {
        System.out.println("We are locked: " + lock.isHeldByCurrentThread());
    }
}
