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

package eu.javaspecialists.tjsn.issue288;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Spliterator.*;

public class WhatHasWeaklyConsistentIteration {
  public static void main(String... args) {
    List<Collection<?>> collections = List.of(
        new ArrayBlockingQueue<>(10),
        new ArrayDeque<>(),
        new ArrayList<>(),
        Collections.singleton(42),
        Collections.singletonList(42),
        Collections.emptyList(),
        Collections.emptySet(),
        Arrays.asList(1,2,3),
        ConcurrentHashMap.newKeySet(),
        new ConcurrentLinkedDeque<>(),
        new ConcurrentLinkedQueue<>(),
        new ConcurrentSkipListSet<>(),
        new CopyOnWriteArrayList<>(),
        new CopyOnWriteArraySet<>(),
        new DelayQueue<>(),
        new HashSet<>(),
        new LinkedBlockingDeque<>(),
        new LinkedBlockingQueue<>(),
        new LinkedHashSet<>(),
        new LinkedList<>(),
        new LinkedTransferQueue<>(),
        List.of(),
        new PriorityBlockingQueue<>(),
        new PriorityQueue<>(),
        Set.of(),
        new Stack<>(),
        new TreeSet<>(),
        new Vector<>()
    );

    Predicate<Collection<?>> concurrent =
        c -> c.spliterator().hasCharacteristics(CONCURRENT);
    Predicate<Collection<?>> immutable =
        c -> c.spliterator().hasCharacteristics(IMMUTABLE);
    Consumer<Collection<?>> printer =
        c -> System.out.println("\t" + c.getClass().getName());

    System.out.println("Weakly-Consistent (CONCURRENT)");
    collections.stream()
        .filter(concurrent)
        .forEach(printer);
    System.out.println("Snapshot (IMMUTABLE)");
    collections.stream()
        .filter(immutable)
        .forEach(printer);
    System.out.println("Neither");
    collections.stream()
        .filter(concurrent.or(immutable).negate())
        .forEach(printer);
  }
}
