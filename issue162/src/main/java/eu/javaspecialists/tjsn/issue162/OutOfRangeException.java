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

package eu.javaspecialists.tjsn.issue162;

public class OutOfRangeException
        extends IllegalArgumentException {
    private final long value, min, max;

    public OutOfRangeException(long value, long min, long max) {
        super("Value " + value + " out of range " +
                "[" + min + ".." + max + "]");
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public long getValue() {
        return value;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
