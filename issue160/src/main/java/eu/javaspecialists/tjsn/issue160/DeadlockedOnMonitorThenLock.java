package eu.javaspecialists.tjsn.issue160;

import java.util.concurrent.locks.*;

public class DeadlockedOnMonitorThenLock implements DeadlockingClass {
    private final Object lock1 = new Object();
    private final Lock lock2 = new ReentrantLock();

    public void method1() throws InterruptedException {
        synchronized (lock1) {
            Thread.sleep(1000);
            lock2.lock();
            try {
            } finally {
                lock2.unlock();
            }
        }
    }

    public void method2() throws InterruptedException {
        lock2.lock();
        try {
            Thread.sleep(1000);
            synchronized (lock1) {
            }
        } finally {
            lock2.unlock();
        }
    }
}