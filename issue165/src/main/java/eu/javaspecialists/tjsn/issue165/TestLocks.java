package eu.javaspecialists.tjsn.issue165;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class TestLocks {
    private static final int DURATION_SECONDS = 5;
    private static final int TOTAL_LOCKS = 16;
    private static final boolean DETAILED_OUTPUT = false;

    public static void main(String... args)
            throws InterruptedException {
        test(false);
        test(true);
    }

    private static void test(boolean fair)
            throws InterruptedException {
        readWriteLockTest(fair);
        lockTest(fair);
    }

    private static void readWriteLockTest(boolean fair)
            throws InterruptedException {
        System.out.println("ReentrantReadWriteLock test");
        for (int locks = 1; locks <= TOTAL_LOCKS; locks *= 2) {
            for (int reads = 0; reads <= locks; reads++) {
                int writes = locks - reads;
                test(new ReadWriteLockFactory(fair), reads, writes, fair);
            }
        }
    }

    private static void lockTest(boolean fair)
            throws InterruptedException {
        System.out.println("ReentrantLock test");
        for (int locks = 1; locks < TOTAL_LOCKS; locks++) {
            test(new StandardLockFactory(fair), 0, locks, fair);
        }
    }

    public static void test(LockFactory factory, int reads,
                            int writes, boolean fair)
            throws InterruptedException {
        System.out.printf("RO=%2d, RW=%2d, fair=%b    ",
                reads, writes, fair);
        CountDownLatch start = new CountDownLatch(reads + writes);
        CountDownLatch finish = new CountDownLatch(reads + writes);
        AtomicBoolean running = new AtomicBoolean(true);

        ExecutorService pool = Executors.newFixedThreadPool(
                reads + writes);

        Collection<LockAcquirer> acquirers =
                new ArrayList<LockAcquirer>();
        for (int i = 0; i < reads + writes; i++) {
            boolean isReadOnly = i >= writes;
            Lock lock = factory.createLock(isReadOnly);
            LockAcquirer acquirer = new LockAcquirer(running,
                    lock, start, finish, isReadOnly);
            acquirers.add(acquirer);
            pool.submit(acquirer);
        }

        start.await();
        TimeUnit.SECONDS.sleep(DURATION_SECONDS);
        running.set(false);
        finish.await();
        long totalReads = 0;
        long totalWrites = 0;
        if (DETAILED_OUTPUT) System.out.println();
        for (LockAcquirer acquirer : acquirers) {
            long numberOfLockAcquisitions =
                    acquirer.getNumberOfLockAcquisitions();
            if (DETAILED_OUTPUT)
                System.out.printf("##    %,17d%n",
                        numberOfLockAcquisitions);

            if (acquirer.isReadOnly()) {
                totalReads += numberOfLockAcquisitions;
            } else {
                totalWrites += numberOfLockAcquisitions;
            }
        }
        System.out.printf("%,17d%,17d%n", totalReads, totalWrites);
        pool.shutdown();
    }
}
