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

package eu.javaspecialists.tjsn.issue188;

import org.jpatterns.gof.*;

/**
 * This special executor guarantees that the call() method of the
 * task parameter is invoked in turns by two threads.  There is
 * probably no practical application for this class, except as a
 * learning experience.
 */
@TemplateMethodPattern.AbstractClass
public abstract class Interlocker {
    @TemplateMethodPattern.PrimitiveOperation
    protected abstract Runnable[] getRunnables(InterlockTask task);

    @TemplateMethodPattern.TemplateMethod
    public final <T> T execute(InterlockTask<T> task)
            throws InterruptedException {
        Runnable[] jobs = getRunnables(task);
        Thread[] threads = new Thread[jobs.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(jobs[i]);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return task.get();
    }
}
