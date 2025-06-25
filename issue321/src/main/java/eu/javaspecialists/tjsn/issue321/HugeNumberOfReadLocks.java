package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class HugeNumberOfReadLocks {
    private final ReadWriteLock rwlock;
    private final int numberOfReaders;

    public HugeNumberOfReadLocks(ReadWriteLock rwlock,
                                 int numberOfReaders) {
        this.rwlock = rwlock;
        this.numberOfReaders = numberOfReaders;
    }

    public void run() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (int i = 0; i < numberOfReaders; i++) {
                scope.fork(() -> {
                    rwlock.readLock().lock();
                    try {
                        Thread.sleep(1000);
                    } finally {
                        rwlock.readLock().unlock();
                    }
                    return true;
                });
            }
            scope.join().throwIfFailed();
            System.out.println("Done");
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        } catch (InterruptedException e) {
            throw new CancellationException("interrupted");
        }
    }
}
