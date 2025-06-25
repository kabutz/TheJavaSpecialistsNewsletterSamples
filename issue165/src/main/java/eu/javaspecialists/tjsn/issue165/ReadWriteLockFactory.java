package eu.javaspecialists.tjsn.issue165;

import java.util.concurrent.locks.*;

public class ReadWriteLockFactory implements LockFactory {
    private final ReadWriteLock rwl;

    public ReadWriteLockFactory(boolean fair) {
        rwl = new ReentrantReadWriteLock(fair);
    }

    public Lock createLock(boolean readOnly) {
        return readOnly ? rwl.readLock() : rwl.writeLock();
    }
}