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

package eu.javaspecialists.tjsn.issue296;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class ConcurrentLinkedReducedHashSet<E> implements Iterable<E> {
    /**
     * A tuple holding our value and its insertion order.
     */
    private record InsertionOrder<T>(T value, long order) {
    }

    /**
     * Contains a mapping from our element to its insertionOrder.
     */
    private final Map<E, InsertionOrder<E>> elements =
            new ConcurrentHashMap<>();
    /**
     * The insertion order maintained in a ConcurrentSkipListSet,
     * so that we iterate in the correct order.
     */
    private final Set<InsertionOrder<E>> elementsOrderedByInsertion =
            new ConcurrentSkipListSet<>(
                    Comparator.comparingLong(InsertionOrder::order));
    /**
     * AtomicLong for generating the next insertion order.
     */
    private final AtomicLong nextOrder = new AtomicLong();

    public boolean add(E e) {
        var added = new AtomicBoolean(false);
        elements.computeIfAbsent(
                e, key -> {
                    var holder = new InsertionOrder<>(e,
                            nextOrder.getAndIncrement());
                    elementsOrderedByInsertion.add(holder);
                    added.set(true);
                    return holder;
                }
        );
        return added.get();
    }

    public boolean remove(E e) {
        var removed = new AtomicBoolean(false);
        elements.computeIfPresent(e, (key, holder) -> {
            elementsOrderedByInsertion.remove(holder);
            removed.set(true);
            return null; // will remove the entry
        });
        return removed.get();
    }

    public boolean contains(E e) {
        return elements.containsKey(e);
    }

    public Stream<E> stream() {
        return elementsOrderedByInsertion.stream().map(InsertionOrder::value);
    }

    public void clear() {
        // slow, but ensures we remove all entries in both collections
        stream().forEach(this::remove);
    }

    @Override
    public Iterator<E> iterator() {
        return stream().iterator();
    }

    @Override
    public String toString() {
        return stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
