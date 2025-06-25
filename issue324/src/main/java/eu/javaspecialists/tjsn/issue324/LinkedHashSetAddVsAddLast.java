package eu.javaspecialists.tjsn.issue324;

import java.util.*;

public class LinkedHashSetAddVsAddLast {
    public static void main(String... args) {
        var set = new LinkedHashSet<String>();
        set.add("A");
        System.out.println(set); // [A]
        set.add("B");
        System.out.println(set); // [A, B]
        set.add("C");
        System.out.println(set); // [A, B, C]
        set.add("A"); // no change to set
        System.out.println(set); // [A, B, C]
        set.addLast("A");
        System.out.println(set); // [B, C, A]
    }
}
