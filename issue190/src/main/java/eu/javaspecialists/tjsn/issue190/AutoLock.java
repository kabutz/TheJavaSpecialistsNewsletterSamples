package eu.javaspecialists.tjsn.issue190;

import java.util.concurrent.locks.*;

public class AutoLock implements AutoCloseable {
    public static AutoLock lock(Lock lock) {
        return new AutoLockNormal(lock);
    }

    public static AutoLock lockInterruptibly(Lock lock) throws InterruptedException {
        return new AutoLockInterruptibly(lock);
    }

    private final Lock lock;

    public void close() {
        lock.unlock();
    }

    private AutoLock(Lock lock) {
        this.lock = lock;
    }

    private static class AutoLockNormal extends AutoLock {
        public AutoLockNormal(Lock lock) {
            super(lock);
            lock.lock();
        }
    }

    private static class AutoLockInterruptibly extends AutoLock {
        public AutoLockInterruptibly(Lock lock) throws InterruptedException {
            super(lock);
            lock.lockInterruptibly();
        }
    }
}
