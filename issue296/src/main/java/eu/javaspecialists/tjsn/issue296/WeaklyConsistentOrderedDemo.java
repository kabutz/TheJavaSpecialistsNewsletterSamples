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

package eu.javaspecialists.tjsn.issue296;

import java.util.*;

public class WeaklyConsistentOrderedDemo {
    public static void main(String... args) {
        //    var set = Collections.synchronizedSet(new LinkedHashSet<String>()); // CME
        //    var set = ConcurrentHashMap.<String>newKeySet(); // works, wrong order
        var set = new ConcurrentLinkedReducedHashSet<String>(); // Perfect (maybe)
        set.add("hello");
        set.add("world");
        Iterator<String> it = set.iterator();
        set.add("Goodbye");
        while (it.hasNext()) {
            String next = it.next();
            System.out.println(next);
        }
    }
}
