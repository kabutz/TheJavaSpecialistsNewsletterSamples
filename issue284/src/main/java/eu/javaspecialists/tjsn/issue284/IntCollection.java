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

package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface IntCollection extends PrimitiveIterable.OfInt {
    int size();
    boolean isEmpty();
    boolean contains(int element);
    int[] toArray();
    boolean add(int element);
    boolean remove(int element);
    boolean containsAll(IntCollection c);
    boolean addAll(IntCollection c);
    boolean removeAll(IntCollection c);
    boolean retainAll(IntCollection c);
    void clear();

    default boolean removeIf(IntPredicate filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final PrimitiveIterator.OfInt each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.nextInt())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    default IntStream stream() {
        return StreamSupport.intStream(spliterator(), false);
    }

    default IntStream parallelStream() {
        return StreamSupport.intStream(spliterator(), true);
    }
}
