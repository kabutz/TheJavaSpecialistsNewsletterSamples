package eu.javaspecialists.tjsn.issue324;

import java.util.*;

public class SequencedLinkedHashSetDemo {
    public static void main(String... args) {
        var set = new LinkedHashSet<String>();
        Collections.addAll(set, "one", "two", "three");
        var reversed = set.reversed();
        reversed.addFirst("first");
        reversed.addLast("last");
        System.out.println("set = " + set);
    }
}
