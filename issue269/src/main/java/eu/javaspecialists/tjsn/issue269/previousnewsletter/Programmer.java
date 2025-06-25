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

package eu.javaspecialists.tjsn.issue269.previousnewsletter;

public class Programmer extends Person {
    private final short iq;

    public Programmer(String name, short iq) {
        super(name);
        if (iq < 120)
            throw new IllegalArgumentException(
                    "Programmer IQ cannot be less than 120");
        this.iq = iq;
    }

    public int compareTo(Person that) {
        if (that instanceof Programmer) {
            Programmer p = (Programmer) that;
            int result = iq - p.iq;  // works because it's a short
            if (result != 0) return -result; // biggest IQ first
        }
        return super.compareTo(that);
    }

    public String toString() {
        return super.toString() + " (" + iq + ")";
    }
}
