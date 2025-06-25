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

package eu.javaspecialists.tjsn.issue225.take1;

import java.io.*;

public final class Person implements Serializable {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        if (this.age < 0)
            throw new IllegalArgumentException("age negative");
        if (this.age > 150)
            throw new IllegalArgumentException("age more than 150");
        if (this.firstName == null || this.firstName.isEmpty())
            throw new IllegalArgumentException("firstName empty");
        if (this.lastName == null || this.lastName.isEmpty())
            throw new IllegalArgumentException("lastName empty");
    }

    public String toString() {
        return firstName + " " + lastName +
                " is " + age + " years old";
    }
}
