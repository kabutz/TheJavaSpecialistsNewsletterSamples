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

package eu.javaspecialists.tjsn.issue236;

public class FibonacciTest {
    public static void main(String... args) {
        Fibonacci fib = new Fibonacci();

        for (int i = 0; i < 10; i++) {
            if (!fib.f(i).equals(fib.f_slow(i))) {
                throw new AssertionError("Mismatch at i=" + i);
            }
        }

        for (int n = 10000; n < 50_000_000; n *= 2) {
            timeTest(fib, n);
        }
    }

    private static void timeTest(Fibonacci fib, int n) {
        System.out.printf("fib(%,d)%n", n);
        long time = System.currentTimeMillis();
        System.out.println(fib.f(n).bitLength());
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
