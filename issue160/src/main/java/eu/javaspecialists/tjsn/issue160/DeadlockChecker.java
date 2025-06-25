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

package eu.javaspecialists.tjsn.issue160;

import java.lang.management.*;
import java.util.*;

/**
 * Used to check whether there are any known deadlocks by
 * querying the ThreadMXBean.  It looks for threads deadlocked
 * on monitors (synchronized) and on Java 5 locks.  Call check()
 * to get a set of deadlocked threads (might be empty).
 * <p>
 * We can also add threads to the ignore set by calling the
 * ignoreThreads(Thread[]) method.  For example, once we have
 * established that a certain deadlock exists, we might want to
 * ignore that until we have shut down our program and instead
 * concentrate on new deadlocks.
 */
public class DeadlockChecker {
    private static final ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();
    private final Set<Long> threadIdsToIgnore =
            new HashSet<Long>();

    /**
     * Returns set of ThreadInfos that are part of the deadlock;
     * could be empty if there is no deadlock.
     */
    public Collection<ThreadInfo> check() {
        Map<Long, ThreadInfo> map = new HashMap<Long, ThreadInfo>();
        findDeadlockInfo(map, tmb.findMonitorDeadlockedThreads());
        findDeadlockInfo(map, tmb.findDeadlockedThreads());
        return map.values();
    }

    public void ignoreThreads(Thread[] threads) {
        for (Thread thread : threads) {
            threadIdsToIgnore.add(thread.getId());
        }
    }

    private void findDeadlockInfo(Map<Long, ThreadInfo> result,
                                  long[] ids) {
        if (ids != null && ids.length > 0) {
            for (long id : ids) {
                if (!threadIdsToIgnore.contains(id)) {
                    result.put(id, tmb.getThreadInfo(id));
                }
            }
        }
    }
}
