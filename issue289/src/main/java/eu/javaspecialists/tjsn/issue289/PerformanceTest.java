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

package eu.javaspecialists.tjsn.issue289;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class PerformanceTest {
    private static final int SIZE = 10_000_000;
    private static final
    List<Function<List<Stream<Integer>>, Stream<Integer>>> MERGERS =
            List.of(
                    s -> s.stream()
                            .flatMap(Function.identity())
                            .sorted(),
                    s -> s.stream()
                            .flatMap(Function.identity())
                            .parallel()
                            .sorted(),
                    s -> StreamSupport.stream(
                            new MergingSortedSpliterator<>(s), false
                    ));

    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test();
            System.out.println();
        }
    }

    private static void test() {
        MERGERS.forEach(merger -> {
            List<Stream<Integer>> streams = makeStreams();
            long time = System.nanoTime();
            try {
                Stream<Integer> numbers = merger.apply(streams);
                numbers.forEach(i -> {
                });
            } finally {
                time = System.nanoTime() - time;
                System.out.printf("time = %dms%n", (time / 1_000_000));
            }
        });
    }

    private static List<Stream<Integer>> makeStreams() {
        return Stream.generate(() -> generateSorted(SIZE))
                .limit(10).collect(Collectors.toList());
    }

    private static Stream<Integer> generateSorted(int size) {
        return IntStream.range(0, size).boxed();
    }
}
