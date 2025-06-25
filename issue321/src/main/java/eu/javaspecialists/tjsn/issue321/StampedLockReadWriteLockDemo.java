package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.locks.*;

public class StampedLockReadWriteLockDemo {
    public static void main(String... args) {
        new HugeNumberOfReadLocks(
                new StampedLock().asReadWriteLock(),
                1_000_000
        ).run();
    }
}
