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

public class ReversedTreeSet {
    public static void main(String... args) {
        Set<String> set1 = Stream.of("a", "b", "c")
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(
                                Comparator.<String>reverseOrder())));
        Set<String> set2 = Stream.of("a", "c", "b")
                .collect(Collectors.toCollection(
                        TreeSet::new));
        Set<String> set3 = Stream.of("c", "a", "b")
                .collect(Collectors.toSet());
        System.out.println(set1.equals(set2));
        System.out.println(set2.equals(set1));
        System.out.println(set3.equals(set1));
        System.out.println(set1.equals(set3));
        System.out.println(set2.equals(set3));
        System.out.println(set3.equals(set2));
    }
}
