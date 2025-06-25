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

package eu.javaspecialists.tjsn.issue107;

import java.sql.*;
import java.util.*;

public class IterableTestStatic {
    public static void main(String... args) {
        Vector<String> sv = new Vector<String>();
        sv.addElement("Maximilian");
        sv.addElement("Francis");
        sv.addElement("Kabutz");

        // Use a static "factory method" to reduces generics clutter
        for (String s : IterableEnumeration.make(sv.elements())) {
            System.out.println(s);
        }

        // This also looks slightly more readable
        // new sun.jdbc.odbc.JdbcOdbcDriver();
        for (Driver driver : IterableEnumeration.make(
                DriverManager.getDrivers())) {
            System.out.println("driver = " + driver.getClass());
        }
    }
}
