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

package eu.javaspecialists.tjsn.issue326;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class DistinctifyGatherer {
    public static <T> Gatherer<T, ?, T> of(
            ToIntFunction<T> hashCode,
            BiPredicate<T, T> equals,
            BinaryOperator<T> merger) {
        class Key {
            private final T t;
            public Key(T t) {this.t = t;}
            public int hashCode() {
                return hashCode.applyAsInt(t);
            }
            public boolean equals(Object obj) {
                return obj instanceof Key that
                        && equals.test(this.t, that.t);
            }
        }
        return Gatherer.<T, Map<Key, Key>, T>ofSequential(
                LinkedHashMap::new,
                (state, element, _) -> {
                    var key = new Key(element);
                    var existing = state.get(key);
                    if (existing != null) {
                        key = new Key(merger.apply(
                                existing.t, key.t));
                    }
                    state.put(key, key);
                    return true;
                },
                (keys, downstream) -> keys.values().stream()
                        .takeWhile(_ -> !downstream.isRejecting())
                        .map(key -> key.t)
                        .forEach(downstream::push)
        );
    }
}
