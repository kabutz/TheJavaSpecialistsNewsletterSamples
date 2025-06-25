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

public interface PrimitiveIterable<T, T_CONS> extends Iterable<T> {
    void forEach(T_CONS action);

    interface OfInt extends PrimitiveIterable<Integer, IntConsumer> {
        PrimitiveIterator.OfInt iterator();

        default Spliterator.OfInt spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        default void forEach(IntConsumer action) {
            Objects.requireNonNull(action);
            for (PrimitiveIterator.OfInt it = iterator(); it.hasNext(); )
                action.accept(it.nextInt());
        }
    }

    interface OfLong extends PrimitiveIterable<Long, LongConsumer> {
        PrimitiveIterator.OfLong iterator();

        default Spliterator.OfLong spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        default void forEach(LongConsumer action) {
            Objects.requireNonNull(action);
            for (PrimitiveIterator.OfLong it = iterator(); it.hasNext(); )
                action.accept(it.nextLong());
        }
    }

    interface OfDouble extends PrimitiveIterable<Double, DoubleConsumer> {
        PrimitiveIterator.OfDouble iterator();

        default Spliterator.OfDouble spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        default void forEach(DoubleConsumer action) {
            Objects.requireNonNull(action);
            for (PrimitiveIterator.OfDouble it = iterator(); it.hasNext(); )
                action.accept(it.nextDouble());
        }
    }
}
