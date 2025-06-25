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

package eu.javaspecialists.tjsn.issue103;

/**
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class PersonParser implements InputParser.Parser<Person> {
    public Person convert(String s) {
        String[] values = InputParser.CSV_PARSER.convert(s);
        String firstName = values[0];
        String surname = values[1];
        int age = Integer.parseInt(values[2]);
        return new Person(firstName, surname, age);
    }
}
