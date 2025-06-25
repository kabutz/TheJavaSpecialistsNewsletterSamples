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

package eu.javaspecialists.tjsn.issue217;

import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = SECONDS)
@Fork(5)
@State(Scope.Benchmark)
@Threads(8)
public class Microbenchmark1 {
    private final NumberRangeSynchronized nrSync =
            new NumberRangeSynchronized();
    private final NumberRangeAtomic nrAtomic =
            new NumberRangeAtomic();

    @Benchmark
    public void test_1_1_synchronized() {
        nrSync.setUpper(100);
        nrSync.setLower(10);
        nrSync.setLower(50);
        nrSync.setUpper(200);
        nrSync.setLower(30);
    }

    @Benchmark
    public void test_1_2_atomic() {
        nrAtomic.setUpper(100);
        nrAtomic.setLower(10);
        nrAtomic.setLower(50);
        nrAtomic.setUpper(200);
        nrAtomic.setLower(30);
    }
}
