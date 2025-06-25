package eu.javaspecialists.tjsn.issue324;

import java.util.*;

public class SequencedLinkedHashSetMysteryDemo {
    public static void main(String... args) {
        var set = new LinkedHashSet<String>();
        Collections.addAll(set, "one", "two", "three");
        var reversed = set.reversed();
        set.add("set.add()");
        reversed.add("reversed.add()");
        System.out.println("set = " + set);
    }
}
