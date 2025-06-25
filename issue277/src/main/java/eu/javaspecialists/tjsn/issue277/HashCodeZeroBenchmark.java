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

package eu.javaspecialists.tjsn.issue277;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.*;

@Fork(value = 3)
@Warmup(iterations = 5, time = 3)
@Measurement(iterations = 10, time = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class HashCodeZeroBenchmark {
    private static final String ZERO_HASH = "ARcZguv";
    @Param({"-1", "0", "1", "2", "3", "4", "5"})
    private int powerOfTen;
    private String s;

    @Setup
    public void setup() {
        int chunks = (int) Math.pow(10, powerOfTen);
        StringBuilder sb = new StringBuilder(
                ZERO_HASH.length() * chunks
        );
        for (int i = 0; i < chunks; i++) {
            sb.append(ZERO_HASH);
        }
        s = sb.toString();
    }

    @Benchmark
    public int hashCodeString() {
        return s.hashCode();
    }
}
