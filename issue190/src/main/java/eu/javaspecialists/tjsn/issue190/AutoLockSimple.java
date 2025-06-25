package eu.javaspecialists.tjsn.issue190;

import java.util.concurrent.locks.*;

public class AutoLockSimple implements AutoCloseable {
    private final Lock lock;

    public AutoLockSimple(Lock lock) {
        this.lock = lock;
        lock.lock();
    }

    public void close() {
        lock.unlock();
    }
}
