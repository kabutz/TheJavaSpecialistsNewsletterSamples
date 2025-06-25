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

package eu.javaspecialists.tjsn.issue271;

import java.lang.management.*;

public class Sleepy {
    public static void main(String... args) throws InterruptedException {
        ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
        long userTime = tmb.getCurrentThreadUserTime();
        long cpuTime = tmb.getCurrentThreadCpuTime();
        long time = System.nanoTime();
        try {
            for (int i = 0; i < 10_000_000; i++) {
//        Thread.yield();
                Thread.sleep(0);
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
        userTime = tmb.getCurrentThreadUserTime() - userTime;
        cpuTime = tmb.getCurrentThreadCpuTime() - cpuTime;
        System.out.printf("cputime = %dms%n", (cpuTime / 1_000_000));
        System.out.printf("usertime = %dms%n", (userTime / 1_000_000));
    }
}
