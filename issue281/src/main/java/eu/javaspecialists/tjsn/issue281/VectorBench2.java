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

package eu.javaspecialists.tjsn.issue281;

import java.util.*;
import java.util.stream.*;

public class VectorBench2 {
    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test(false);
            test(true);
        }
    }

    private static void test(boolean parallel) {
        Set<List<Integer>> vectors = Collections.newSetFromMap(
                Collections.synchronizedMap(
                        // should not rely on a mutating hashCode()
                        new IdentityHashMap<>()
                )
        );
        IntStream range = IntStream.range(1, 100_000_000);
        if (parallel) range = range.parallel();
        long time = System.nanoTime();
        try {
            ThreadLocal<List<Integer>> lists =
                    ThreadLocal.withInitial(() -> {
                        List<Integer> result = new Vector<>();
                        vectors.add(result); // avoid GC during run
                        for (int i = 0; i < 1024; i++) result.add(i);
                        return result;
                    });
            range.map(i -> lists.get().get(i & 1023)).sum();
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("%s %dms%n",
                    parallel ? "parallel" : "sequential",
                    (time / 1_000_000));
        }
    }
}
