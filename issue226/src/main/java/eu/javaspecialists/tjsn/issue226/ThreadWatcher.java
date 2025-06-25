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

package eu.javaspecialists.tjsn.issue226;

import java.security.*;
import java.util.function.*;

public class ThreadWatcher extends SecurityManager {
    private final Predicate<Thread> predicate;
    private final Consumer<Thread> action;

    public ThreadWatcher(Predicate<Thread> predicate,
                         Consumer<Thread> action) {
        this.predicate = predicate;
        this.action = action;
    }

    public void checkPermission(Permission perm) {
        // allow everything
    }

    public void checkPermission(Permission perm, Object context) {
        // allow everything
    }

    public void checkAccess(ThreadGroup g) {
        Thread creatingThread = Thread.currentThread();
        if (predicate.test(creatingThread)) {
            action.accept(creatingThread);
        }
    }
}
