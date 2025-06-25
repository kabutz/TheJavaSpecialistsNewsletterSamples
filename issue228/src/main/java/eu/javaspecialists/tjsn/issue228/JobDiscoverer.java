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

package eu.javaspecialists.tjsn.issue228;

import java.lang.reflect.*;
import java.util.concurrent.*;

public class JobDiscoverer {
    private static final Field callableInFutureTask;
    private static final Class<? extends Callable> adapterClass;
    private static final Field runnableInAdapter;

    static {
        try {
            callableInFutureTask =
                    FutureTask.class.getDeclaredField("callable");
            callableInFutureTask.setAccessible(true);
            adapterClass = Executors.callable(new Runnable() {
                public void run() {
                }
            }).getClass();
            runnableInAdapter =
                    adapterClass.getDeclaredField("task");
            runnableInAdapter.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Object findRealTask(Runnable task) {
        if (task instanceof FutureTask) {
            try {
                Object callable = callableInFutureTask.get(task);
                if (adapterClass.isInstance(callable)) {
                    return runnableInAdapter.get(callable);
                } else {
                    return callable;
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        throw new ClassCastException("Not a FutureTask");
    }
}
