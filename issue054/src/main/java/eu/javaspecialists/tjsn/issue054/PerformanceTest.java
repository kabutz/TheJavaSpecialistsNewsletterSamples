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

package eu.javaspecialists.tjsn.issue054;

public class PerformanceTest {
    private static final int REPEATS = 10;

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
        }
        System.out.println(
                "JVM version:" + System.getProperty("java.version"));
        try {
            evaluate((Benchmark) Class.forName(args[0]).newInstance());
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
            usage();
        }
    }

    private static void usage() {
        System.err.println(
                "usage: java PerformanceTest BenchmarkClass");
        System.err.println(
                "\tBenchmarkClass is a class implementing Benchmark");
        System.exit(1);
    }

    private static void evaluate(Benchmark benchmark) {
        // do the function once to "warm up" HotSpot compiler
        benchmark.doCalculation();
        long average = 0;
        for (int i = 0; i < REPEATS; i++) {
            long time = -System.currentTimeMillis();
            int iterations = benchmark.doCalculation();
            time += System.currentTimeMillis();
            System.out.print(iterations / time);
            System.out.print("  ");
            System.out.flush();
            average += iterations / time;
        }
        System.out.println();
        System.out.println(
                "Average "
                        + (average / REPEATS)
                        + " iterations per millisecond");
    }
}
