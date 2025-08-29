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

// no more need for individual imports in Java 25 :-)
import module java.base;

public class ShuffleGatherer {
    public static <T> Gatherer<T, List<T>, T> of() {
        return of(ThreadLocalRandom.current());
    }

    public static <T> Gatherer<T, List<T>, T> of(
            int windowSize) {
        return of(ThreadLocalRandom.current(), windowSize);
    }

    public static <T> Gatherer<T, List<T>, T> of(
            RandomGenerator random) {
        return of(random, Integer.MAX_VALUE - 8);
    }

    public static <T> Gatherer<T, List<T>, T> of(
            RandomGenerator random, int windowSize) {
        return Gatherer.ofSequential(
                ArrayList::new,
                (list, element, downstream) -> {
                    list.add(element);
                    if (list.size() == windowSize) {
                        shuffleAndSend(random, list, downstream);
                    }
                    return true;
                },
                (list, downstream) ->
                        shuffleAndSend(random, list, downstream)
        );
    }

    private static <T> void shuffleAndSend(
            RandomGenerator random,
            List<T> list,
            Gatherer.Downstream<? super T> downstream) {
        Collections.shuffle(list, random);
        list.stream()
                .takeWhile(_ -> !downstream.isRejecting())
                .forEach(downstream::push);
        list.clear();
    }
}