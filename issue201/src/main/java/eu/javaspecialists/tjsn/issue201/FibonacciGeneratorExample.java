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

package eu.javaspecialists.tjsn.issue201;

import java.util.concurrent.*;

public class FibonacciGeneratorExample {
    private static ForkJoinPool pool = new ForkJoinPool(
            Runtime.getRuntime().availableProcessors() * 4);

    public static void main(String... args)
            throws InterruptedException {
        int[] ns;
        if (args.length != 0) {
            ns = new int[args.length];
            for (int i = 0; i < ns.length; i++) {
                ns[i] = Integer.parseInt(args[i]);
            }
        } else {
            ns = new int[]{
                    1_000_000,
                    10_000_000,
                    100_000_000, // takes a bit long
                    1_000_000_000, // takes a bit long
            };
        }
        test(new FibonacciRecursiveParallelMultiply(), ns);
        // test(new FibonacciRecursiveParallelDijkstraKaratsuba(pool), ns);
    }

    private static void test(Fibonacci fib, int... ns)
            throws InterruptedException {
        for (int n : ns) {
            FibonacciGenerator fibgen = new FibonacciGenerator(fib);
            fibgen.findFib(n);
            System.out.println(pool);
        }
    }
}
