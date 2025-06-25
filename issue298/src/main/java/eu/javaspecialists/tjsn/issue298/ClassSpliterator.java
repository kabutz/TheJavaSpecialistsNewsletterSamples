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

package eu.javaspecialists.tjsn.issue298;

import java.util.*;
import java.util.function.*;

public class ClassSpliterator implements Spliterator<Class<?>> {
    private Class<?> nextClass;
    private final Deque<Iterator<Class<?>>> unfinished =
            new ArrayDeque<>();

    public ClassSpliterator(Class<?> root) {
        if (!root.isSealed())
            throw new IllegalArgumentException(root + " not sealed");
        nextClass = root;
        addPermittedSubclasses(root);
    }

    private void addPermittedSubclasses(Class<?> root) {
        Optional.ofNullable(root.getPermittedSubclasses())
                .map(Arrays::asList)
                .map(Iterable::iterator)
                .ifPresent(unfinished::addLast);
    }

    @Override
    public boolean tryAdvance(Consumer<? super Class<?>> action) {
        Objects.requireNonNull(action);
        if (nextClass == null) nextClass = findNextClass();
        if (nextClass == null) return false;
        action.accept(nextClass);
        nextClass = null;
        return true;
    }

    private Class<?> findNextClass() {
        while (!unfinished.isEmpty()) {
            Iterator<Class<?>> iterator = unfinished.peekLast();
            if (iterator.hasNext()) {
                var result = iterator.next();
                addPermittedSubclasses(result);
                return result;
            } else {
                unfinished.removeLast();
            }
        }
        return null;
    }

    @Override
    public Spliterator<Class<?>> trySplit() {
        // never support splitting
        return null;
    }

    @Override
    public long estimateSize() {
        // unknown size
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE | NONNULL;
    }
}
