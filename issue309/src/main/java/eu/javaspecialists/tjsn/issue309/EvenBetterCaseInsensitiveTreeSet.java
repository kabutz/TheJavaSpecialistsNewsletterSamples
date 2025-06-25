package eu.javaspecialists.tjsn.issue309;

import java.util.*;
import java.util.stream.*;

public class EvenBetterCaseInsensitiveTreeSet {
    record CaseInsensitive(String value)
            implements Comparable<CaseInsensitive> {
        public boolean equals(Object obj) {
            return obj instanceof CaseInsensitive that
                    && this.value.equalsIgnoreCase(that.value);
        }

        /**
         * Case insensitive hashCode() on the characters of value, which
         * converts each of the chars to lower case.
         */
        public int hashCode() {
            if (value == null) return 0;
            int h = 0, length = value.length();
            for (int i = 0; i < length; i++) {
                h = 31 * h + Character.toLowerCase(value.charAt(i));
            }
            return h;
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
