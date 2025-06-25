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

package eu.javaspecialists.tjsn.issue273;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionPuzzle {
    public static void main(String... args) {
        Collection<String> names = new ArrayList<>();
        Collections.addAll(names, "Goetz", "Marks", "Rose");

        printSize(names);
        printSize(Arrays.asList("Goetz", "Marks", "Rose"));
        printSize(List.of("Goetz", "Marks", "Rose"));
        printSize(Collections.unmodifiableCollection(names));
    }

    private static void printSize(Collection<?> col) {
        System.out.println("Size of " + col.getClass().getName());
        try {
            Method sizeMethod = col.getClass().getMethod("size");
            System.out.println(sizeMethod.invoke(col));
        } catch (ReflectiveOperationException e) {
            System.err.println(e);
        }
    }
}
