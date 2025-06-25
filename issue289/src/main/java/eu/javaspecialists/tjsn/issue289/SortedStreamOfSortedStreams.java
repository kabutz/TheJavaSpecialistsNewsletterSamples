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
import java.util.concurrent.*;
import java.util.stream.*;

public class SortedStreamOfSortedStreams {
    private static final int SIZE = 5;

    public static void main(String... args) {
        List<Stream<Integer>> streams = List.of(
                generateSortedRandom(SIZE),
                generateSortedRandom(SIZE),
                generateSortedRandom(SIZE),
                generateSortedRandom(SIZE)
        );

        Stream<Integer> numbers = StreamSupport.stream(
                new MergingSortedSpliterator<>(streams), false
        );
        numbers.forEach(System.out::println);
    }

    private static Stream<Integer> generateSortedRandom(int size) {
        return ThreadLocalRandom.current().ints(size, 0, size * 4)
                .parallel()
                .sorted()
                .boxed();
    }
}
