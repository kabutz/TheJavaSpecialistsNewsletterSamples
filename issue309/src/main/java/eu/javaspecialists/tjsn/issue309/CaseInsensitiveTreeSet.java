package eu.javaspecialists.tjsn.issue309;

import java.util.*;
import java.util.stream.*;

public class CaseInsensitiveTreeSet {
    public static void main(String... args) {
        Set<String> set1 = Stream.of("a", "b", "c")
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(
                                String.CASE_INSENSITIVE_ORDER)));
        Set<String> set2 = Stream.of("A", "B", "C")
	        .collect(Collectors.toSet());
        System.out.println(set1.equals(set2));
        System.out.println(set2.equals(set1));
    }
}
