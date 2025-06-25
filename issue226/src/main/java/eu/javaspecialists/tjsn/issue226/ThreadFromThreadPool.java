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

import java.util.concurrent.*;

public class ThreadFromThreadPool {
    public static void main(String... args)
            throws InterruptedException {
        System.setSecurityManager(
                new ThreadWatcher(
                        DemoSupport.createPredicate(),
                        DemoSupport.createConsumer()
                )
        );

        ExecutorService pool = Executors.newFixedThreadPool(10);
        Future<?> future = pool.submit(() ->
                new Thread(DemoSupport.createHelloJob(),
                        "This should print a warning 1")
        );
        try {
            future.get();
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        }
        pool.shutdown();

        System.setSecurityManager(null);
    }
}
