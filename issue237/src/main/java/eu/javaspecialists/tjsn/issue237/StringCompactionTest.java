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
