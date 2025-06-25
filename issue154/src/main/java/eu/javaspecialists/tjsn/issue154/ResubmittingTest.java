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

package eu.javaspecialists.tjsn.issue154;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class ResubmittingTest {
    public static void main(String... args)
            throws InterruptedException {
        ScheduledExecutorService service2 =
                new ResubmittingScheduledThreadPoolExecutor(
                        5, new MyScheduledExceptionHandler());
        service2.scheduleAtFixedRate(
                new MyRunnable(), 2, 1, TimeUnit.SECONDS);
    }

    private static class MyRunnable implements Runnable {
        public void run() {
            if (Math.random() < 0.3) {
                System.out.println("I have a problem");
                throw new IllegalArgumentException("I have a problem");
            }
            System.out.println("I'm happy");
        }
    }

    /**
     * As an example, we give up after 5 failures.
     */
    private static class MyScheduledExceptionHandler
            implements ScheduledExceptionHandler {
        private AtomicInteger problems = new AtomicInteger();

        public boolean exceptionOccurred(Throwable e) {
            e.printStackTrace();
            if (problems.incrementAndGet() >= 5) {
                System.err.println("We give up!");
                return false;
            }
            System.err.println("Resubmitting task to scheduler");
            return true;
        }
    }
}
