package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.locks.*;

public class StampedLockWriterStarvation {
    public static void main(String... args) {
        new WriteLockStarvationDemo(
                new StampedLock().asReadWriteLock()
        ).run();
    }
}
