package eu.javaspecialists.tjsn.issue190;

import java.util.concurrent.locks.*;

public class AutoLockSimpleTest {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String... args) {
        try (AutoLockSimple als = new AutoLockSimple(lock)) {
            printLockStatus();
        }
        printLockStatus();
    }

    private static void printLockStatus() {
        System.out.println("We are locked: " + lock.isHeldByCurrentThread());
    }
}
