package eu.javaspecialists.tjsn.issue160;

import java.util.concurrent.locks.*;

public class DeadlockedOnLockThenMonitor implements DeadlockingClass {
    private final Lock lock1 = new ReentrantLock();
    private final Object lock2 = new Object();

    public void method1() throws InterruptedException {
        lock1.lock();
        try {
            Thread.sleep(1000);
            synchronized (lock2) {
            }
        } finally {
            lock1.unlock();
        }
    }

    public void method2() throws InterruptedException {
        synchronized (lock2) {
            Thread.sleep(1000);
            lock1.lock();
            try {
            } finally {
                lock1.unlock();
            }
        }
    }
}