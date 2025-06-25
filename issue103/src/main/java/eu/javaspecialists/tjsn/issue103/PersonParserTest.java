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

import java.io.*;

/**
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class PersonParserTest {
    private static final String FILE = "persons.txt";

    public static void main(String... args) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(FILE));
        out.println("Heinz,Kabutz,33");
        out.println("John,Green,33");
        out.println("Anton,de Swardt,33");
        out.println("Zachary,Thalla,32");
        out.close();

        InputParser<Person> personFile = new InputParser<Person>(
                new FileInputStream(FILE), new PersonParser());
        for (Person person : personFile) {
            System.out.println(person);
        }
    }
}
