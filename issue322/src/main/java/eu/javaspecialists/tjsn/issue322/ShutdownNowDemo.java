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

package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class ShutdownNowDemo {
    public static void main(String... args) throws InterruptedException {
        var pool = Executors.newSingleThreadExecutor();
        var future = pool.submit(() -> {Thread.sleep(10000); return "done";});
        pool.shutdown(); // worker threads are left in peace
        Thread.sleep(100);
        System.out.println("isTerminated? " + pool.isTerminated());
        pool.shutdownNow(); // interrupts all the worker threads in pool
        Thread.sleep(100);
        System.out.println("isTerminated? " + pool.isTerminated());

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Future threw an exception: " + e.getCause());
        }
    }
}
