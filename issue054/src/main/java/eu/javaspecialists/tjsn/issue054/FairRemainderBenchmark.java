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

import java.util.*;

public class FairRemainderBenchmark implements Benchmark {
    private static final int ITERATIONS = 10 * 1000 * 1000;
    private int memory;

    public int doCalculation() {
        int val = 0;
        Random rand = new Random(0);
        int bucket_size = (int) (rand.nextDouble() * 101) + 1;
        for (int i = 0; i < ITERATIONS; i++) {
            val = i % bucket_size;
        }
        memory = val;
        return ITERATIONS;
    }
}
