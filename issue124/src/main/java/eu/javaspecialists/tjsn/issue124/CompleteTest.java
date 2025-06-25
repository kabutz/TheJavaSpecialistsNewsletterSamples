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

package eu.javaspecialists.tjsn.issue124;

public class CompleteTest {
    private static final int RUNS = 10;
    private static final long TEST_TIME = 100;

    public static void main(String... args) throws Exception {
        test(1);
        test(10);
        test(100);
        test(1000);
        test(10000);
        test(100000);
    }

    private static void test(int length) {
        PerformanceHarness harness = new PerformanceHarness();
        Average arrayClone = harness.calculatePerf(
                new PerformanceChecker(TEST_TIME,
                        new ArrayCloneTest(length)), RUNS);
        Average arrayNewAndCopy = harness.calculatePerf(
                new PerformanceChecker(TEST_TIME,
                        new ArrayNewAndCopyTest(length)), RUNS);

        System.out.println("Length=" + length);
        System.out.printf("Clone %.0f\t%.0f%n",
                arrayClone.mean(), arrayClone.stddev());
        System.out.printf("Copy  %.0f\t%.0f%n",
                arrayNewAndCopy.mean(), arrayNewAndCopy.stddev());
        System.out.printf("Diff  %.2fx%n",
                arrayNewAndCopy.mean() / arrayClone.mean());
        System.out.println();
    }
}
