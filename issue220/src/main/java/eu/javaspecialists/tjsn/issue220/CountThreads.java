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

package eu.javaspecialists.tjsn.issue220;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class CountThreads {
    private static Map<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String... args) {
        System.out.println(IntStream.range(1, 1_000_000).
                parallel().filter(CountThreads::isPrime).count());
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public static boolean isPrime(int n) {
        map.compute(Thread.currentThread().getName(),
                (k, v) -> v == null ? 1 : v + 1);
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}