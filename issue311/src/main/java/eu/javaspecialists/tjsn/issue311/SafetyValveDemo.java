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

package eu.javaspecialists.tjsn.issue311;

import java.util.concurrent.*;
import java.util.function.*;

public class SafetyValveDemo {
    public static <T> T safetyValve(Supplier<T> streamTask) {
        return Thread.currentThread().isVirtual()
                && ForkJoinPool.getCommonPoolParallelism() > 1 ?
                ForkJoinPool.commonPool()
                        .submit(streamTask::get).join() :
                streamTask.get();
    }

    public static void main(String... args) {
        long time = System.nanoTime();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            pool.submit(() -> {
                var thread1 = Thread.currentThread().toString();
                System.out.println(thread1);
                var stats = safetyValve(() ->
                        ThreadLocalRandom.current().ints(100_000_000)
                                .parallel()
                                .sorted()
                                .summaryStatistics());
                var thread2 = Thread.currentThread().toString();
                System.out.println(thread2);
                if (!thread1.equals(thread2))
                    System.out.println("unmounted");
                else
                    System.out.println("maybe not unmounted");
                return stats;
            });
            for (int i = 0; i < 100000; i++) {
                pool.submit(() -> {/* empty task */});
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
