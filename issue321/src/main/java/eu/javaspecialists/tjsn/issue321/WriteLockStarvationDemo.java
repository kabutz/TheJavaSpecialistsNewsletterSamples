package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class WriteLockStarvationDemo {
    private final ReadWriteLock rwlock;

    public WriteLockStarvationDemo(ReadWriteLock rwlock) {
        this.rwlock = rwlock;
    }

    public void run() {
        if (checkForWriterStarvation(rwlock) > 1_000) {
            throw new AssertionError("Writer starvation occurred!!!");
        } else {
            System.out.println("No writer starvation");
        }
    }

    private long checkForWriterStarvation(ReadWriteLock rwlock) {
        System.out.println("Checking " + rwlock.getClass());
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            scope.fork(() -> {
                System.out.println("Going to start readers ...");
                for (int i = 0; i < 10; i++) {
                    int reader = i;
                    scope.fork(() -> {
                        rwlock.readLock().lock();
                        try {
                            System.out.println("Reader " + reader +
                                               " is reading ...");
                            Thread.sleep(1000);
                        } finally {
                            rwlock.readLock().unlock();
                        }
                        System.out.println("Reader " + reader + "" +
                                           " is done");
                        return true;
                    });
                    Thread.sleep(500);
                }
                return true;
            });
            Thread.sleep(1800);
            System.out.println("Going to try to write now ...");
            long time = System.nanoTime();
            rwlock.writeLock().lock();
            try {
                time = System.nanoTime() - time;
                time /= 1_000_000; // convert to ms
                System.out.printf(
                        "time to acquire write lock = %dms%n", time);
                System.out.println("Writer is writing ...");
                Thread.sleep(1000);
            } finally {
                rwlock.writeLock().unlock();
            }
            System.out.println("Writer is done");

            scope.join().throwIfFailed(IllegalStateException::new);
            return time;
        } catch(InterruptedException e) {
            throw new CancellationException("interrupted");
        }
    }
}
