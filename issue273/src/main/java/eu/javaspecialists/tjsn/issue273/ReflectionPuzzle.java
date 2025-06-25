package eu.javaspecialists.tjsn.issue273;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionPuzzle {
    public static void main(String... args) {
        Collection<String> names = new ArrayList<>();
        Collections.addAll(names, "Goetz", "Marks", "Rose");

        printSize(names);
        printSize(Arrays.asList("Goetz", "Marks", "Rose"));
        printSize(List.of("Goetz", "Marks", "Rose"));
        printSize(Collections.unmodifiableCollection(names));
    }

    private static void printSize(Collection<?> col) {
        System.out.println("Size of " + col.getClass().getName());
        try {
            Method sizeMethod = col.getClass().getMethod("size");
            System.out.println(sizeMethod.invoke(col));
        } catch (ReflectiveOperationException e) {
            System.err.println(e);
        }
    }
}
