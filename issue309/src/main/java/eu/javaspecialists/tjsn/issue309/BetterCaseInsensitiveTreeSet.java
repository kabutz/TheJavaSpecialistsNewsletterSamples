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

package eu.javaspecialists.tjsn.issue309;

import java.util.*;
import java.util.stream.*;

public class BetterCaseInsensitiveTreeSet {
    record CaseInsensitive(String value)
            implements Comparable<CaseInsensitive>{
        public boolean equals(Object obj) {
            return obj instanceof CaseInsensitive that
                    && this.value.equalsIgnoreCase(that.value);
        }

        public int compareTo(CaseInsensitive that) {
            return String.CASE_INSENSITIVE_ORDER.compare(
                    this.value, that.value
            );
        }
    }
    public static void main(String... args) {
        Set<CaseInsensitive> set1 = Stream.of("a", "b", "c")
                .map(CaseInsensitive::new)
                .collect(Collectors.toCollection(TreeSet::new));
        Set<CaseInsensitive> set2 = Stream.of("A", "B", "C")
                .map(CaseInsensitive::new)
                .collect(Collectors.toSet());
        System.out.println(set1.equals(set2));
        System.out.println(set2.equals(set1));
    }
}
