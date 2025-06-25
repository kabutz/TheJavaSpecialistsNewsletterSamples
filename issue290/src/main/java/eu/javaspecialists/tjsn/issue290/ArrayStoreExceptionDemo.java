package eu.javaspecialists.tjsn.issue290;

import java.util.*;

public class ArrayStoreExceptionDemo {
    public static void main(String... args) {
        List<Integer> numbers = new BadArrayList<>(
                Arrays.asList(1, 2, 3), Integer.class);
        System.out.println(numbers);
        List<Object> objects = new BadArrayList<>(
                numbers, Object.class);
        System.out.println(objects);
        objects.set(0, "One"); // ArrayStoreException
    }
}
