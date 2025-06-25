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

package eu.javaspecialists.tjsn.issue299;

import java.lang.management.*;
import java.util.*;

public class MonitorsDemo {
    public static void main(String... args) {
        printInfo();

        Object lock1 = new Object();
        synchronized (lock1) {
            printInfo();
        }

        Object lock2 = new Object();
        synchronized (lock1) {
            synchronized (lock2) {
                printInfo();
            }
        }
    }

    private static void printInfo() {
        System.out.println("Monitors locked by current thread:");
        MonitorInfo[] monitors = Monitors.findLockedMonitors();
        if (monitors.length == 0) System.out.println("\tnone");
        else Arrays.stream(monitors)
                .forEach(monitor -> System.out.println("\t" + monitor));
        System.out.println();
    }
}
