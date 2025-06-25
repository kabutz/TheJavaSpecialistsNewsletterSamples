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

package eu.javaspecialists.tjsn.issue269;

import java.util.*;
import java.util.stream.*;

public class PersonTesterWithCorrectComparator {
    public static void main(String... args) {
        Person[] people = {
                new Programmer("Aaron", (short) 130),
                new Person("Adolf"),
                new Programmer("Brian", (short) 180),
                new Person("Brian"),
                new Programmer("Cedric", (short) 120),
                new Programmer("Cedric", (short) 120),
                new Programmer("Zoran", (short) 200),
        };

        Comparator<Person> comparator = (p1, p2) -> {
            if (p1 instanceof Programmer) {
                if (p2 instanceof Programmer)
                    return p1.compareTo(p2);
                else
                    return -1;
            }

            if (p2 instanceof Programmer)
                return 1;

            return p1.compareTo(p2);
        };
        CompareTester.test(people, comparator);

        Arrays.sort(people, comparator);
        Stream.of(people).forEach(System.out::println);
    }
}
