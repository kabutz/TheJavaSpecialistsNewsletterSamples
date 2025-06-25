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

package eu.javaspecialists.tjsn.issue252;

import java.time.*;
import java.util.*;
import java.util.function.*;

/**
 * Takes a start date and iterates backwards one year at a time.
 */
public class YearSpliterator implements Spliterator<LocalDate> {
    private LocalDate date;

    public YearSpliterator(LocalDate startDate) {
        this.date = startDate;
    }

    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    public boolean tryAdvance(Consumer<? super LocalDate> action) {
        Objects.requireNonNull(action);
        action.accept(date);
        date = date.minusYears(1);
        return true;
    }

    public Spliterator<LocalDate> trySplit() {
        return null;
    }

    public int characteristics() {
        return DISTINCT | IMMUTABLE | NONNULL | ORDERED | SORTED;
    }

    public Comparator<? super LocalDate> getComparator() {
        return Comparator.reverseOrder();
    }
}
