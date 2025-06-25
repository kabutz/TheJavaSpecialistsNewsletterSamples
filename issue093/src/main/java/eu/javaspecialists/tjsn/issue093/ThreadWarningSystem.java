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

package eu.javaspecialists.tjsn.issue093;

import java.lang.management.*;
import java.util.*;

public class ThreadWarningSystem {
    private final Timer threadCheck = new Timer("Thread Monitor", true);
    private final ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
    private final Collection<Listener> listeners = new ArrayList<Listener>();
    /**
     * The number of milliseconds between checking for deadlocks.
     * It may be expensive to check for deadlocks, and it is not
     * critical to know so quickly.
     */
    private static final int DEADLOCK_CHECK_PERIOD = 500;
    /**
     * The number of milliseconds between checking number of
     * threads.  Since threads can be created very quickly, we need
     * to check this frequently.
     */
    private static final int THREAD_NUMBER_CHECK_PERIOD = 20;
    private static final int MAX_STACK_DEPTH = 30;
    private boolean threadThresholdNotified = false;
    private Set deadlockedThreads = new HashSet();

    /**
     * Monitor only deadlocks.
     */
    public ThreadWarningSystem() {
        threadCheck.schedule(new TimerTask() {
            public void run() {
                long[] ids = mbean.findMonitorDeadlockedThreads();
                if (ids != null && ids.length > 0) {
                    for (Long l : ids) {
                        if (!deadlockedThreads.contains(l)) {
                            deadlockedThreads.add(l);
                            ThreadInfo ti = mbean.getThreadInfo(l, MAX_STACK_DEPTH);
                            fireDeadlockDetected(ti);
                        }
                    }
                }
            }
        }, 10, DEADLOCK_CHECK_PERIOD);
    }

    /**
     * Monitor deadlocks and the number of threads.
     */
    public ThreadWarningSystem(final int threadThreshold) {
        this();
        threadCheck.schedule(new TimerTask() {
            public void run() {
                if (mbean.getThreadCount() > threadThreshold) {
                    if (!threadThresholdNotified) {
                        fireThresholdExceeded();
                        threadThresholdNotified = true;
                    }
                } else {
                    threadThresholdNotified = false;
                }
            }
        }, 10, THREAD_NUMBER_CHECK_PERIOD);
    }

    private void fireDeadlockDetected(ThreadInfo thread) {
        // In general I avoid using synchronized.  The surrounding
        // code should usually be responsible for being threadsafe.
        // However, in this case, the timer could be notifying at
        // the same time as someone is adding a listener, and there
        // is nothing the calling code can do to prevent that from
        // occurring.  Another tip though is this: when I synchronize
        // I use a private field to synchronize on, instead of
        // "this".
        synchronized (listeners) {
            for (Listener l : listeners) {
                l.deadlockDetected(thread);
            }
        }
    }

    private void fireThresholdExceeded() {
        ThreadInfo[] allThreads = mbean.getThreadInfo(mbean.getAllThreadIds());
        synchronized (listeners) {
            for (Listener l : listeners) {
                l.thresholdExceeded(allThreads);
            }
        }
    }

    public boolean addListener(Listener l) {
        synchronized (listeners) {
            return listeners.add(l);
        }
    }

    public boolean removeListener(Listener l) {
        synchronized (listeners) {
            return listeners.remove(l);
        }
    }

    /**
     * This is called whenever a problem with threads is detected.
     * The two events are deadlockDetected() and thresholdExceeded().
     */
    public interface Listener {
        /**
         * @param deadlockedThread The deadlocked thread, with stack
         *                         trace of limited depth.
         */
        void deadlockDetected(ThreadInfo deadlockedThread);

        /**
         * @param allThreads All the threads in the JVM, without
         *                   stack traces.
         */
        void thresholdExceeded(ThreadInfo[] allThreads);
    }
}
