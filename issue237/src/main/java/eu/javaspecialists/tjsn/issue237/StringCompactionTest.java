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

package eu.javaspecialists.tjsn.issue237;

import java.lang.reflect.*;

public class StringCompactionTest {
    private static Field valueField;

    static {
        try {
            valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String... args)
            throws IllegalAccessException {
        showGoryDetails("hello world");
        showGoryDetails("hello w\u00f8rld"); // Scandinavian o
        showGoryDetails("he\u03bb\u03bbo wor\u03bbd"); // Greek l
    }

    private static void showGoryDetails(String s)
            throws IllegalAccessException {
        s = "" + s;
        System.out.printf("Details of String \"%s\"\n", s);
        System.out.printf("Identity Hash of String: 0x%x%n",
                System.identityHashCode(s));
        Object value = valueField.get(s);
        System.out.println("Type of value field: " +
                value.getClass().getSimpleName());
        System.out.println("Length of value field: " +
                Array.getLength(value));
        System.out.printf("Identity Hash of value: 0x%x%n",
                System.identityHashCode(value));
        System.out.println();
    }
}
