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

package eu.javaspecialists.tjsn.issue327;

import module java.base;

public class PrimitiveShuffleCollectorTest {
    private void printRandom(
            int from, int upto, int limit,
            Gatherer<Integer, List<Integer>, Integer> shuffler) {
        var shuffled = IntStream.range(from, upto)
                .boxed()
                .gather(shuffler)
                .limit(limit)
                .toList();
        IO.println(shuffled);
    }

    // Simplified main() method for Java 25
    public void main() {
        // Shuffle integers 0..9, and pick the first 5 elements
        printRandom(0, 10, 5, ShuffleGatherer.of());
        // Unpredictable output - ThreadLocalRandom by default

        // Shuffle integers 0..9, and pick the first 5 elements
        // Use Random(0) to get repeatable results.
        printRandom(0, 10, 5, ShuffleGatherer.of(new Random(0)));
        // [4, 8, 9, 6, 3]

        // Shuffle integers 0..999, and pick the first 3 elements
        printRandom(0, 1000, 3, ShuffleGatherer.of(new Random(0)));
        // [490, 539, 694]

        // Shuffle integers 0..7, with a shuffle window of 3
        printRandom(0, 8, 8, ShuffleGatherer.of(new Random(0), 3));
        // [2, 1, 0, 3, 5, 4, 6, 7]
    }
}