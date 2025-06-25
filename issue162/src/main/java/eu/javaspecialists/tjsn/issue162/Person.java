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

package eu.javaspecialists.tjsn.issue162;

public class Person {
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;

    private final int age;
    private final String name;

    /**
     * Constructs a person with the given age and name.
     *
     * @param age  The age must fit into a range, specified by
     *             MIN_AGE and MAX_AGE
     * @param name The name should not be null, nor an empty
     *             string
     * @throws OutOfRangeException      if the age is out of range.
     *                                  The age may not be less
     *                                  than the constant MIN_AGE
     *                                  and may not be more than
     *                                  the constant MAX_AGE.
     * @throws IllegalArgumentException if the name is null or
     *                                  empty.
     * @see #MIN_AGE, #MAX_AGE
     */
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
        if (this.age < MIN_AGE || this.age > MAX_AGE) {
            throw new OutOfRangeException(this.age, MIN_AGE, MAX_AGE);
        }
        if (this.name == null || this.name.equals("")) {
            throw new IllegalArgumentException(
                    "name parameter should not be null nor empty");
        }
    }

    public String toString() {
        return "Person " + name + " is " + age + " years old";
    }
}
