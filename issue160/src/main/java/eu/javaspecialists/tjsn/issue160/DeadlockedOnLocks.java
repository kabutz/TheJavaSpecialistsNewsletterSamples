package eu.javaspecialists.tjsn.issue160;

import java.util.concurrent.locks.*;

public class DeadlockedOnLocks implements DeadlockingClass {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void method1() throws InterruptedException {
        lock1.lock();
        try {
            Thread.sleep(1000);
            lock2.lock();
            try {
            } finally {
                lock2.unlock();
            }
        } finally {
            lock1.unlock();
        }
    }

    public void method2() throws InterruptedException {
        lock2.lock();
        try {
            Thread.sleep(1000);
            lock1.lock();
            try {
            } finally {
                lock1.unlock();
            }
        } finally {
            lock2.unlock();
        }
    }
}