package eu.javaspecialists.tjsn.issue165;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class LockAcquirer implements Runnable {
    private final AtomicBoolean running;
    private final Lock lock;
    private final CountDownLatch start;
    private final CountDownLatch finish;
    private final boolean readOnly;
    private long numberOfLockAcquisitions = 0;
    private int work = 50;
    private long offset = calculateOffset();

    private long calculateOffset() {
        Random r = new Random(0);
        long result = 0;
        for (int i = 0; i < work; i++) {
            result += r.nextInt();
        }
        return result;
    }

    public LockAcquirer(AtomicBoolean running,
                        Lock lock, CountDownLatch start,
                        CountDownLatch finish, boolean readOnly) {
        this.running = running;
        this.lock = lock;
        this.start = start;
        this.finish = finish;
        this.readOnly = readOnly;
    }

    public void run() {
        try {
            start.countDown();
            start.await();
            while (running.get()) {
                lock.lock();
                try {
                    work();
                } finally {
                    lock.unlock();
                }
            }
            finish.countDown();
        } catch (InterruptedException e) {
            return;
        }
    }

    private void work() {
        Random r = new Random(0);
        for (int i = 0; i < work; i++) {
            numberOfLockAcquisitions += r.nextInt();
        }
        numberOfLockAcquisitions -= offset;
        numberOfLockAcquisitions++;
    }

    public String toString() {
        return String.format("%s%,15d",
                (readOnly ? "RO" : "RW"),
                numberOfLockAcquisitions);
    }


    public long getNumberOfLockAcquisitions() {
        return numberOfLockAcquisitions;
    }

    public boolean isReadOnly() {
        return readOnly;
    }
}
