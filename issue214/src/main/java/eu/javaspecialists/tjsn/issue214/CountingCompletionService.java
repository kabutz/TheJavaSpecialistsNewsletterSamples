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

package eu.javaspecialists.tjsn.issue214;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class CountingCompletionService<V>
        extends ExecutorCompletionService<V> {
    private final AtomicLong submittedTasks = new AtomicLong();
    private final AtomicLong completedTasks = new AtomicLong();

    public CountingCompletionService(Executor executor) {
        super(executor);
    }

    public CountingCompletionService(
            Executor executor, BlockingQueue<Future<V>> queue) {
        super(executor, queue);
    }

    public Future<V> submit(Callable<V> task) {
        Future<V> future = super.submit(task);
        submittedTasks.incrementAndGet();
        return future;
    }

    public Future<V> submit(Runnable task, V result) {
        Future<V> future = super.submit(task, result);
        submittedTasks.incrementAndGet();
        return future;
    }

    public Future<V> take() throws InterruptedException {
        Future<V> future = super.take();
        completedTasks.incrementAndGet();
        return future;
    }

    public Future<V> poll() {
        Future<V> future = super.poll();
        if (future != null) completedTasks.incrementAndGet();
        return future;
    }

    public Future<V> poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        Future<V> future = super.poll(timeout, unit);
        if (future != null) completedTasks.incrementAndGet();
        return future;
    }

    public long getNumberOfCompletedTasks() {
        return completedTasks.get();
    }

    public long getNumberOfSubmittedTasks() {
        return submittedTasks.get();
    }

    public boolean hasUncompletedTasks() {
        return completedTasks.get() < submittedTasks.get();
    }
}
