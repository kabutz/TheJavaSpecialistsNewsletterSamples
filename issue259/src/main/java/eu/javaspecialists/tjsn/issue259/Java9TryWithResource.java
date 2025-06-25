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

package eu.javaspecialists.tjsn.issue259;

import java.io.*;

public class Java9TryWithResource {
    public static void main(String... args) throws IOException {
        printClose(new FileInputStream("Java9TryWithResource.java"));
    }

    private static void printClose(InputStream in)
            throws IOException {
        try (in; // <-- Compiler error prior to Java 9
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(in)
             )) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
