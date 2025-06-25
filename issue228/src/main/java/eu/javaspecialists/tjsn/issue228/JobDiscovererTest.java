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

import java.util.*;
import java.util.concurrent.*;

public class JobDiscovererTest {
    public static void main(String... args) {
        final CountDownLatch latch = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            pool.submit(new Runnable() {
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException consumeAndExit) {
                        System.out.println(Thread.currentThread().getName() +
                                " was interrupted - exiting");
                    }
                }

                public String toString() {
                    return "Runnable: " + finalI;
                }
            });
            pool.submit(new Callable<String>() {
                public String call() throws InterruptedException {
                    latch.await();
                    return "success";
                }

                public String toString() {
                    return "Callable: " + finalI;
                }
            });
        }

        // Note: the Runnables returned from shutdownNow are NOT
        // the same objects as we submitted to the pool!!!
        List<Runnable> tasks = pool.shutdownNow();

        System.out.println("Tasks from ThreadPool");
        System.out.println("=====================");
        for (Runnable task : tasks) {
            System.out.println("Task from ThreadPool " + task);
        }

        System.out.println();
        System.out.println("Using our JobDiscoverer");
        System.out.println("=======================");

        for (Runnable task : tasks) {
            Object realTask = JobDiscoverer.findRealTask(task);
            System.out.println("Real task was actually " + realTask);
        }
    }
}
