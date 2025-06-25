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

// Maven: eu.javaspecialists.books.dynamicproxies:core:2.0.0

import eu.javaspecialists.books.dynamicproxies.*;

import java.util.*;

public class DynamicProxiesDemo {
    public static void main(String... args) {
        Set<String> angrySet = Proxies.castProxy(
                Set.class,
                (p, m, a) -> {
                    throw new UnsupportedOperationException(
                            m.getName() + "() not implemented");
                }
        );

        Set<String> set = Proxies.adapt(
                Set.class, // target interface
                angrySet, // adaptee
                new ConcurrentLinkedReducedHashSet<>() // adapter
        );
        set.add("hello");
        set.add("world");
        Iterator<String> it = set.iterator();
        set.add("Goodbye");
        while (it.hasNext()) {
            String next = it.next();
            System.out.println(next);
        }
        set.clear();
        set.addAll(List.of("one")); // UnsupportedOperationException
    }
}
