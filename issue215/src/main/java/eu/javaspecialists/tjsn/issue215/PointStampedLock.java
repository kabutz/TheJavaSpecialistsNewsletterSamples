package eu.javaspecialists.tjsn.issue215;

import java.util.concurrent.locks.*;

public class PointStampedLock {
    private int x, y;
    private final StampedLock sl = new StampedLock();

    public PointStampedLock(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int deltaX, int deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    public double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        int localX = x, localY = y;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                localX = x;
                localY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.hypot(localX, localY);
    }

    private static final int RETRIES = 5;

    public double distanceFromOriginWithRetries() {
        int localX, localY;
        out:
        {
            // try a few times to do an optimistic read
            for (int i = 0; i < RETRIES; i++) {
                long stamp = sl.tryOptimisticRead();
                localX = x;
                localY = y;
                if (sl.validate(stamp)) {
                    break out;
                }
            }
            // pessimistic read
            long stamp = sl.readLock();
            try {
                localX = x;
                localY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.hypot(localX, localY);
    }

    public boolean moveIfAt(int oldX, int oldY,
                            int newX, int newY) {
        long stamp = sl.readLock();
        try {
            while (x == oldX && y == oldY) {
                long writeStamp = sl.tryConvertToWriteLock(stamp);
                if (writeStamp != 0) {
                    stamp = writeStamp;
                    x = newX;
                    y = newY;
                    return true;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
            return false;
        } finally {
            sl.unlock(stamp); // could be read or write lock
        }
    }
}
