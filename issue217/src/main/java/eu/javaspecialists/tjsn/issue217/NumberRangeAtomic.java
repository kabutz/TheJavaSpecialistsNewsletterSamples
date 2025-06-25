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

public class NumberRangeAtomic implements NumberRange {
    private final AtomicLong range = new AtomicLong(0);

    public boolean setLower(int newLower) {
        while (true) {
            long current = range.get();
            int upper = getUpper(current);
            int lower = getLower(current);
            if (newLower > upper) return false;
            long next = combine(newLower, upper);
            if (range.compareAndSet(current, next)) return true;
        }
    }

    public boolean setUpper(int newUpper) {
        while (true) {
            long current = range.get();
            int upper = getUpper(current);
            int lower = getLower(current);
            if (newUpper < lower) return false;
            long next = combine(lower, newUpper);
            if (range.compareAndSet(current, next)) return true;
        }
    }

    public boolean isInRange(int number) {
        long current = range.get();
        int upper = getUpper(current);
        int lower = getLower(current);
        return number >= lower && number <= upper;
    }

    private long combine(long lower, long upper) {
        return upper | (lower << 32);
    }

    private int getLower(long range) {
        return (int) (range >>> 32);
    }

    private int getUpper(long range) {
        return (int) (range);
    }

    public boolean checkCorrectRange() {
        long range = this.range.get();
        int lower = getLower(range);
        int upper = getUpper(range);
        return lower <= upper;
    }
}
