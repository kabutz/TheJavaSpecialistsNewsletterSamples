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

package eu.javaspecialists.tjsn.issue320;

import java.lang.management.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class YieldALot {
    public static void main(String... args) {
        var timer = Executors.newSingleThreadScheduledExecutor();
        var running = new AtomicBoolean(true);
        timer.schedule(() -> running.set(false),
                1, TimeUnit.SECONDS);

        var tmb = ManagementFactory.getThreadMXBean();
        var cpu = tmb.getCurrentThreadCpuTime();
        var usr = tmb.getCurrentThreadUserTime();

        var counter = 0;
        while (running.get() && ++counter > 0) Thread.yield();

        cpu = tmb.getCurrentThreadCpuTime() - cpu;
        usr = tmb.getCurrentThreadUserTime() - usr;

        System.out.printf("CPU time    = %,d%n", cpu);
        System.out.printf("User time   = %,d%n", usr);
        System.out.printf("System time = %,d%n", cpu - usr);
        System.out.printf("counter     = %,d%n", counter);
        timer.shutdown();
    }
}
