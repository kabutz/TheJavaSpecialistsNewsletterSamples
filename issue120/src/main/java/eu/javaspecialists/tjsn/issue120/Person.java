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

package eu.javaspecialists.tjsn.issue120;

public class Person {
    private final String name;
    private final int age;
    private static final int MAXIMUM_AGE = 150;

    /**
     * Person constructor representing a natural
     * person.  Name may not be null.  Age must be
     * non-negative and less than MAXIMUM_AGE.
     *
     * @throws IllegalArgumentException if name is
     *                                  null or if age is out of range.
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        if (this.age < 0 || this.age > MAXIMUM_AGE) {
            throw new IllegalArgumentException(
                    "age out of range: " + this.age +
                            " expected range 0 <= age < " +
                            MAXIMUM_AGE);
        }
        if (this.name == null) {
            throw new IllegalArgumentException(
                    "name is null");
        }
    }
}
