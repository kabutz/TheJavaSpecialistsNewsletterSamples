/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
