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

package eu.javaspecialists.tjsn.issue323;

import java.lang.management.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;

public class DeadlockDemo {
    public static void main(String... args) {
        var lock1 = new Object();
        var lock2 = new Object();
        Thread.startVirtualThread(() -> lockBoth(lock1, lock2));
        Thread.startVirtualThread(() -> lockBoth(lock2, lock1));
        LockSupport.parkNanos(100_000_000L);

        System.out.println("Find all blocked threads");
        Threadables.stream()
                .filter(thread -> thread.getState() == Thread.State.BLOCKED)
                .forEach(thread -> {
                    System.out.println(thread + " " + thread.getState());
                    Stream.of(thread.getStackTrace())
                            .map(element -> "\t" + element)
                            .forEach(System.out::println);
                });

    }

    private static void lockBoth(Object first, Object second) {
        synchronized (first) {
            System.out.println("First locked");
            LockSupport.parkNanos(50_000_000L);
            synchronized (second) {
                System.out.println("Both locked");
            }
        }
    }
}
