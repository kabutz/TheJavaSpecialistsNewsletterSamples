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

package eu.javaspecialists.tjsn.issue149;

import java.util.concurrent.*;

public class ThreadConstructTest {
    private static final int NUMBER_OF_THREADS = 100 * 1000;
    private Semaphore semaphore = new Semaphore(10);
    private final Runnable job = new Runnable() {
        public void run() {
            semaphore.release();
        }
    };

    public void noThreadPool() throws InterruptedException {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            semaphore.acquire();
            new Thread(job).start();
        }
        // wait for all jobs to finish
        semaphore.acquire(10);
        semaphore.release(10);
    }

    public void fixedThreadPool() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(12);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            semaphore.acquire();
            pool.submit(job);
        }
        semaphore.acquire(10);
        semaphore.release(10);
        pool.shutdown();
    }

    public static void main(String... args) throws Exception {
        ThreadConstructTest test = new ThreadConstructTest();

        long time = System.currentTimeMillis();
        test.noThreadPool();
        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        test.fixedThreadPool();
        System.out.println(System.currentTimeMillis() - time);
    }
}
