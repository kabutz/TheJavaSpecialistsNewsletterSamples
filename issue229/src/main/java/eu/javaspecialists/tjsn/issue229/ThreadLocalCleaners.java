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
