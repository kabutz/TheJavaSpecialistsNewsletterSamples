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

package eu.javaspecialists.tjsn.issue315;

import java.lang.management.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class TimeMeasurer {
    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test(MathRandomComparison::main);
        }
    }

    private static void test(Runnable task) {
        // find all the thread ids of the parallel stream threads
        var parallelism = ForkJoinPool.getCommonPoolParallelism();
        var phaser = new Phaser(parallelism);
        var threads = IntStream.range(0, parallelism)
                .parallel()
                .mapToObj(i -> {
                    phaser.arriveAndAwaitAdvance();
                    return Thread.currentThread();
                })
                .collect(Collectors.toUnmodifiableSet());

        var startCpuTimes = threads.stream()
                .collect(Collectors.toMap(Function.identity(),
                        TimeMeasurer::getThreadCpuTime));
        var realTime = System.nanoTime();

        task.run();

        realTime = System.nanoTime() - realTime;
        var cpuTimes = startCpuTimes.entrySet().stream()
                .mapToLong(entry -> {
                    long start = entry.getValue();
                    long end = getThreadCpuTime(entry.getKey());
                    return end - start;
                })
                .sum();
        System.out.printf("realTime = %dms, cpuTimes = %dms%n",
                realTime / 1_000_000,
                cpuTimes / 1_000_000);
    }

    private static final ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();

    private static long getThreadCpuTime(Thread thread) {
        return tmb.getThreadCpuTime(thread.threadId());
    }
}
