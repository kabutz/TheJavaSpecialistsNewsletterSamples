package eu.javaspecialists.tjsn.issue296;

import java.util.*;

public class WeaklyConsistentOrderedDemoComplex {
    public static void main(String... args) {
        var set = new ConcurrentLinkedReducedHashSet<String>();
        set.add("hello");
        set.add("world");
        set.add("Goodbye");

        Iterator<String> it = set.iterator();
        System.out.println(it.next()); // hello
        System.out.println(it.next()); // world
        set.remove("hello");
        set.add("hello");
        System.out.println(it.next()); // Goodbye
        System.out.println(it.next()); // hello
        System.out.println(it.hasNext()); // false
    }
}
