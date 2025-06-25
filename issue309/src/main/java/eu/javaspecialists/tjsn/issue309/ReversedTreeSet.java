package eu.javaspecialists.tjsn.issue309;

import java.util.*;
import java.util.stream.*;

public class ReversedTreeSet {
    public static void main(String... args) {
        Set<String> set1 = Stream.of("a", "b", "c")
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(
                                Comparator.<String>reverseOrder())));
        Set<String> set2 = Stream.of("a", "c", "b")
                .collect(Collectors.toCollection(
                        TreeSet::new));
        Set<String> set3 = Stream.of("c", "a", "b")
                .collect(Collectors.toSet());
        System.out.println(set1.equals(set2));
        System.out.println(set2.equals(set1));
        System.out.println(set3.equals(set1));
        System.out.println(set1.equals(set3));
        System.out.println(set2.equals(set3));
        System.out.println(set3.equals(set2));
    }
}
