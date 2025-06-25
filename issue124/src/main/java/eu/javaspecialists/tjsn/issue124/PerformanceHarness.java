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

/**
 * This class calculates the performance of a PerformanceChecker
 * instance, based on the given number of runs.
 *
 * @author Heinz Kabutz
 */
public class PerformanceHarness {
    /**
     * We calculate the average number of times that the check
     * executed, together with the standard deviation.
     *
     * @param check The test that we want to evaluate
     * @param runs  How many times it should be executed
     * @return an average number of times that test could run
     */
    public Average calculatePerf(PerformanceChecker check, int runs) {
        Average avg = new Average();
        // first we warm up the hotspot compiler
        check.start();
        check.start();
        for (int i = 0; i < runs; i++) {
            long count = check.start();
            avg.add(count);
        }
        return avg;
    }
}
