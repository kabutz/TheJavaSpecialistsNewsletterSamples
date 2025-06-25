package eu.javaspecialists.tjsn.issue160;

import java.util.concurrent.locks.*;

public class DeadlockedOnLocksInterruptibly
        implements DeadlockingClass {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void method1() throws InterruptedException {
        lock1.lockInterruptibly();
        try {
            Thread.sleep(1000);
            lock2.lockInterruptibly();
            try {
            } finally {
                lock2.unlock();
            }
        } finally {
            lock1.unlock();
        }
    }

    public void method2() throws InterruptedException {
        lock2.lockInterruptibly();
        try {
            Thread.sleep(1000);
            lock1.lockInterruptibly();
            try {
            } finally {
                lock1.unlock();
            }
        } finally {
            lock2.unlock();
        }
    }
}