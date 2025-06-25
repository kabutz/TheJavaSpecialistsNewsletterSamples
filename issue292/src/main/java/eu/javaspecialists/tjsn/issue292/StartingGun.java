package eu.javaspecialists.tjsn.issue292;

import java.util.concurrent.locks.*;

/**
 * A StartingGun for services, where we can wait for one
 * particular service to start up.
 * <p>
 * Instead of {@link java.util.concurrent.CountDownLatch}, we
 * have a custom concurrency utility called StartingGun, which is
 * like a CountDownLatch with a count of 1 and where the
 * awaitUninterruptibly() method does not throw an exception.
 * Similarly to the CountDownLatch, this uses the {@link
 * AbstractQueuedSynchronizer} for the synchronization.
 *
 * @author Dr Heinz M. Kabutz
 */
public class StartingGun {
    private static final int UNUSED = 0;

    /**
     * Synchronization control For StartingGun.
     * Uses AQS state to represent whether we are ready.
     */
    private final AbstractQueuedSynchronizer sync =
            new AbstractQueuedSynchronizer() {
                private static final int SUCCESS = 1;
                private static final int FAILURE = -1;
                private static final int READY = 1;

                protected int tryAcquireShared(int unused) {
                    return (getState() == READY) ? SUCCESS : FAILURE;
                }

                protected boolean tryReleaseShared(int unused) {
                    setState(READY);
                    return true;
                }
            };

    /**
     * Wait for the starting gun to fire, without propagating the
     * InterruptedException.
     */
    public void awaitUninterruptibly() {
        sync.acquireShared(UNUSED);
    }

    /**
     * Indicate that the service is ready for operation.
     */
    public void ready() {
        sync.releaseShared(UNUSED);
    }
}
