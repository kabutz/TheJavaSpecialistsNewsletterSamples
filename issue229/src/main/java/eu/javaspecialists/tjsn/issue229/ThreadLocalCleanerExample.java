package eu.javaspecialists.tjsn.issue229;

import java.text.*;

public class ThreadLocalCleanerExample {
    private static final ThreadLocal<DateFormat> df =
            new ThreadLocal<DateFormat>() {
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            };

    public static void main(String... args) {
        System.out.println("First ThreadLocalCleaner context");
        try (ThreadLocalCleaner tlc = new ThreadLocalCleaner(
                ThreadLocalChangeListener.PRINTER)) {
            System.out.println(System.identityHashCode(df.get()));
            System.out.println(System.identityHashCode(df.get()));
            System.out.println(System.identityHashCode(df.get()));
        }

        System.out.println("Another ThreadLocalCleaner context");
        try (ThreadLocalCleaner tlc = new ThreadLocalCleaner(
                ThreadLocalChangeListener.PRINTER)) {
            System.out.println(System.identityHashCode(df.get()));
            System.out.println(System.identityHashCode(df.get()));
            System.out.println(System.identityHashCode(df.get()));
        }
    }
}
