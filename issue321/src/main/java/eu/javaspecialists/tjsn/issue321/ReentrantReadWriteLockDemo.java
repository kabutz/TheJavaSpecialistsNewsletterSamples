package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.locks.*;

public class ReentrantReadWriteLockDemo {
    public static void main(String... args) {
        new HugeNumberOfReadLocks(
                new ReentrantReadWriteLock(),
                1_000_000
        ).run();
    }
}
