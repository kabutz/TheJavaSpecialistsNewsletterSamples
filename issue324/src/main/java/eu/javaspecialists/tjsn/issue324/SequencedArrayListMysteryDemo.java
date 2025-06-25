package eu.javaspecialists.tjsn.issue324;

import java.util.*;

public class SequencedArrayListMysteryDemo {
    public static void main(String... args) {
        var list = new ArrayList<String>();
        Collections.addAll(list, "one", "two", "three");
        var reversed = list.reversed();
        list.add("list.add()");
        reversed.add("reversed.add()");
        System.out.println("list = " + list);
    }
}
