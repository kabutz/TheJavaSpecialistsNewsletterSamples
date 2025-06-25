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

package eu.javaspecialists.tjsn.issue135;

import java.lang.management.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class MultiCoreTester {
    private static final int THREADS =
            Runtime.getRuntime().availableProcessors() * 16;
    private static CountDownLatch ct = new CountDownLatch(THREADS);
    private static AtomicLong total = new AtomicLong();

    public static void main(String... args)
            throws InterruptedException {
        long elapsedTime = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            Thread thread = new Thread() {
                public void run() {
                    total.addAndGet(measureThreadCpuTime());
                    ct.countDown();
                }
            };
            thread.start();
        }
        ct.await();
        elapsedTime = System.nanoTime() - elapsedTime;
        System.out.println("Total elapsed time " + elapsedTime);
        System.out.println("Total thread CPU time " + total.get());
        double factor = total.get();
        factor /= elapsedTime;
        System.out.printf("Factor: %.2f%n", factor);
    }

    private static long measureThreadCpuTime() {
        ThreadMXBean tm = ManagementFactory.getThreadMXBean();
        long cpuTime = tm.getCurrentThreadCpuTime();
        long total = 0;
        for (int i = 0; i < 1000 * 1000 * 1000; i++) {
            // keep ourselves busy for a while ...
            // note: we had to add some "work" into the loop or Java 6
            // optimizes it away.  Thanks to Daniel Einspanjer for
            // pointing that out.
            total += i;
            total *= 10;
        }
        cpuTime = tm.getCurrentThreadCpuTime() - cpuTime;
        System.out.println(total + " ... " + Thread.currentThread() +
                ": cpuTime = " + cpuTime);
        return cpuTime;
    }
}
