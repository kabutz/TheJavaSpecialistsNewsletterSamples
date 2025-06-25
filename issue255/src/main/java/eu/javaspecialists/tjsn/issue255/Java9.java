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

package eu.javaspecialists.tjsn.issue255;

import java.util.*;

public class Java9 {
    public static void main(String... args) {
        for (String arg : args) {
            System.out.println(arg);
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.out.println(arg);
        }

        List<String> strings = new ArrayList<>();
        strings.add("hello");
        for (String string : strings) {
        }

        List<String> explicit = new ArrayList<>();

        String name = "Hello";
        name += " World";
        System.out.println("name = " + name);

        int var = 42; // <-- hmmm, wonder what will happen?
    }
}
