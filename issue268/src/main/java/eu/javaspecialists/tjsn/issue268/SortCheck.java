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

package eu.javaspecialists.tjsn.issue268;

import java.util.*;

public class SortCheck {
    private static final String[] NAMES = {
            "John", "Paul", "Sarah", "Anton", "Antonia", "Peter", "Heinz",
            "Kiriaki", "Kirk", "Maurice", "Simone", "Vuyisile", "Prakash",
            "Yen", "Michelle", "Rabea", "Steve", "Patrick", "Alvaro", "Petros",
            "Charalambos", "Susie", "Rebekka", "Zoran", "Quinton", "Sean"
    };

    private static final int NUMBER_OF_PERSONS = 10000;

    public static void main(String... args) {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < NUMBER_OF_PERSONS; i++) {
            persons.add(generate());
        }

        while (true) {
            try {
                Collections.sort(persons);
                break;
            } catch (Throwable e) {
                System.err.println("Failed - trying again");
            }
        }
        for (Person person : persons) {
            System.out.println(person);
        }
    }

    private static final Random RANDOM = new Random();

    private static Person generate() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        if (RANDOM.nextBoolean()) {
            return new Person(name);
        } else {
            return new Programmer(name, (short) (RANDOM.nextInt(80) + 120));
        }
    }
}
