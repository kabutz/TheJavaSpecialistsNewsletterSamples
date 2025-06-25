package eu.javaspecialists.tjsn.issue324;

import java.util.*;

public class SequencedArrayListDemo {
    public static void main(String... args) {
        var list = new ArrayList<String>();
        Collections.addAll(list, "one", "two", "three");
        var reversed = list.reversed();
        reversed.addFirst("first");
        reversed.addLast("last");
        System.out.println("list = " + list);
    }
}
