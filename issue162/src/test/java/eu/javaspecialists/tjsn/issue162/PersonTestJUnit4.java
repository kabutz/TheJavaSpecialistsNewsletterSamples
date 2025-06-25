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

import org.junit.*;

public class PersonTestJUnit4 {
    @Test
    public void correctAges() {
        new Person(36, "Heinz");
        new Person(Person.MIN_AGE, "Heinz");
        new Person(Person.MAX_AGE, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void tooYoung() {
        new Person(Person.MIN_AGE - 1, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void tooOld() {
        new Person(Person.MAX_AGE + 1, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void muchTooOld() {
        new Person(Integer.MAX_VALUE, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void muchTooYoung() {
        new Person(Integer.MIN_VALUE, "Heinz");
    }
}
