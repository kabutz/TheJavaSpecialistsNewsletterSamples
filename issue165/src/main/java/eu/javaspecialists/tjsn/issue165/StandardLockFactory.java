package eu.javaspecialists.tjsn.issue165;

import java.util.concurrent.locks.*;

public class StandardLockFactory implements LockFactory {
    private final Lock lock;

    public StandardLockFactory(boolean fair) {
        lock = new ReentrantLock(fair);
    }

    public Lock createLock(boolean readOnly) {
        return lock;
    }
}