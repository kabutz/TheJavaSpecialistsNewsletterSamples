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

package eu.javaspecialists.tjsn.issue229;

import java.io.*;
import java.lang.ref.*;
import java.util.AbstractMap.*;
import java.util.*;
import java.util.Map.*;
import java.util.function.*;

import static eu.javaspecialists.tjsn.issue229.ThreadLocalCleaner.*;

public class ThreadLocalCleaners {
    public static Collection<Entry<ThreadLocal<?>, Object>> findAll(
            Thread thread) {
        Collection<Entry<ThreadLocal<?>, Object>> result =
                new ArrayList<>();
        BiConsumer<ThreadLocal<?>, Object> adder =
                (key, value) ->
                        result.add(new SimpleImmutableEntry<>(key, value));
        forEach(thread, adder);
        return result;
    }

    public static void printThreadLocals() {
        printThreadLocals(System.out);
    }

    public static void printThreadLocals(Thread thread) {
        printThreadLocals(thread, System.out);
    }

    public static void printThreadLocals(PrintStream out) {
        printThreadLocals(Thread.currentThread(), out);
    }

    public static void printThreadLocals(Thread thread,
                                         PrintStream out) {
        out.println("Thread " + thread.getName());
        out.println("  ThreadLocals");
        printTable(thread, out);
    }

    private static void printTable(
            Thread thread, PrintStream out) {
        forEach(thread, (key, value) -> {
            out.printf("    {%s,%s", key, value);
            if (value instanceof Reference) {
                out.print("->" + ((Reference<?>) value).get());
            }
            out.println("}");
        });
    }
}
