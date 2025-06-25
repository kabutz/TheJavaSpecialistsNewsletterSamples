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
import org.openjdk.jmh.infra.*;

import static java.util.concurrent.TimeUnit.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = SECONDS)
@Fork(5)
@State(Scope.Benchmark)
@Threads(8)
public class Microbenchmark0 {
    public static final int TOKENS = Integer.getInteger("TOKENS");
    private final NumberRangeSynchronized nrSync =
            new NumberRangeSynchronized();
    private final NumberRangeAtomic nrAtomic =
            new NumberRangeAtomic();
    private final NumberRangeAtomicWithPark nrAtomicPark =
            new NumberRangeAtomicWithPark();

    @Benchmark
    public void test_0_1_synchronized() {
        nrSync.setUpper(100);
        Blackhole.consumeCPU(TOKENS);
        nrSync.setLower(10);
        Blackhole.consumeCPU(TOKENS);
        nrSync.setLower(50);
        Blackhole.consumeCPU(TOKENS);
        nrSync.setUpper(200);
        Blackhole.consumeCPU(TOKENS);
        nrSync.setLower(30);
        Blackhole.consumeCPU(TOKENS);
    }

    @Benchmark
    public void test_0_2_atomic() {
        nrAtomic.setUpper(100);
        Blackhole.consumeCPU(TOKENS);
        nrAtomic.setLower(10);
        Blackhole.consumeCPU(TOKENS);
        nrAtomic.setLower(50);
        Blackhole.consumeCPU(TOKENS);
        nrAtomic.setUpper(200);
        Blackhole.consumeCPU(TOKENS);
        nrAtomic.setLower(30);
        Blackhole.consumeCPU(TOKENS);
    }

    @Benchmark
    public void test_0_3_atomic_park() {
        nrAtomicPark.setUpper(100);
        Blackhole.consumeCPU(TOKENS);
        nrAtomicPark.setLower(10);
        Blackhole.consumeCPU(TOKENS);
        nrAtomicPark.setLower(50);
        Blackhole.consumeCPU(TOKENS);
        nrAtomicPark.setUpper(200);
        Blackhole.consumeCPU(TOKENS);
        nrAtomicPark.setLower(30);
        Blackhole.consumeCPU(TOKENS);
    }
}