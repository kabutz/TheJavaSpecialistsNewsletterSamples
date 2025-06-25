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

public class IterableTest {
    public static void main(String... args) {
        Vector<String> sv = new Vector<String>();
        sv.addElement("Maximilian");
        sv.addElement("Francis");
        sv.addElement("Kabutz");

        // using the generics makes it look a bit clumsy
        IterableEnumeration<String> ie =
                new IterableEnumeration<String>(sv.elements());
        for (String s : ie) {
            System.out.println(s);
        }

        // Without generics, we cannot automatically cast to String
        IterableEnumeration ie2 =
                new IterableEnumeration(sv.elements());
        for (Object s : ie2) { // here we now have to use Object type
            System.out.println(s);
        }

        // Again, generics makes the code look clumsy
        // here you should load your own driver, if applicable
        // new sun.jdbc.odbc.JdbcOdbcDriver();
        IterableEnumeration<Driver> drivers =
                new IterableEnumeration<Driver>(
                        DriverManager.getDrivers());
        for (Driver driver : drivers) {
            System.out.println("driver = " + driver.getClass());
        }

        // or we could build up the list using Collections.list()
        // and iterate through that - this is ineffient.
        for (Driver driver : Collections.list(
                DriverManager.getDrivers())) {
            System.out.println("driver = " + driver.getClass());
        }
    }
}
