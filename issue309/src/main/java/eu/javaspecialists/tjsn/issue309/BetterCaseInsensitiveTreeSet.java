package eu.javaspecialists.tjsn.issue309;

import java.util.*;
import java.util.stream.*;

public class BetterCaseInsensitiveTreeSet {
    record CaseInsensitive(String value)
            implements Comparable<CaseInsensitive>{
        public boolean equals(Object obj) {
            return obj instanceof CaseInsensitive that
                    && this.value.equalsIgnoreCase(that.value);
        }

        public int compareTo(CaseInsensitive that) {
            return String.CASE_INSENSITIVE_ORDER.compare(
                    this.value, that.value
            );
        }
    }
    public static void main(String... args) {
        Set<CaseInsensitive> set1 = Stream.of("a", "b", "c")
                .map(CaseInsensitive::new)
                .collect(Collectors.toCollection(TreeSet::new));
        Set<CaseInsensitive> set2 = Stream.of("A", "B", "C")
                .map(CaseInsensitive::new)
                .collect(Collectors.toSet());
        System.out.println(set1.equals(set2));
        System.out.println(set2.equals(set1));
    }
}
