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

package eu.javaspecialists.tjsn.issue217;

import java.util.concurrent.atomic.*;

public class NumberRangeAtomicRef implements NumberRange {
    private static final class Range {
        private final int lower, upper;

        private Range(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public boolean isInRange(int number) {
            return number >= lower && number <= upper;
        }
    }

    private final AtomicReference<Range> range =
            new AtomicReference<>(new Range(0, 0));

    public boolean setLower(int newLower) {
        while (true) {
            Range current = range.get();
            if (newLower > current.upper) return false;
            Range next = new Range(newLower, current.upper);
            if (range.compareAndSet(current, next)) return true;
        }
    }

    public boolean setUpper(int newUpper) {
        while (true) {
            Range current = range.get();
            if (newUpper < current.lower) return false;
            Range next = new Range(current.lower, newUpper);
            if (range.compareAndSet(current, next)) return true;
        }
    }

    public boolean isInRange(int number) {
        return range.get().isInRange(number);
    }

    public boolean checkCorrectRange() {
        Range range = this.range.get();
        return range.lower <= range.upper;
    }
}