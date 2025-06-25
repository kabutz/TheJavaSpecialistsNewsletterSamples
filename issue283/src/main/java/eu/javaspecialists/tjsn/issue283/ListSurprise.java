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

package eu.javaspecialists.tjsn.issue283;

import java.util.*;

public class ListSurprise {
    public static void main(String... args) {
        // Make ListSurprise print 3.14159
        System.setSecurityManager(new SecurityManager());
        List<Integer> numbers = new ArrayList<>();
        Collections.addAll(numbers, 3, 1, 4, 1, 5, 5, 9);
        Iterator<Integer> it = numbers.iterator();
        System.out.print(it.next()); // 3
        System.out.print('.');
        System.out.print(it.next()); // 1
        System.out.print(it.next()); // 4
        System.out.print(it.next()); // 1
        System.out.print(it.next()); // 5
        doSomething(numbers); // should make the next output be 9
        System.out.println(it.next());
        if (!numbers.equals(List.of(3, 1, 4, 1, 5, 9)))
            throw new AssertionError();
    }

    private static void doSomething(List<Integer> list) {
        // how???
    }
}
