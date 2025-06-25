package eu.javaspecialists.tjsn.issue296;

import java.util.*;

public class WeaklyConsistentOrderedDemo {
    public static void main(String... args) {
        //    var set = Collections.synchronizedSet(new LinkedHashSet<String>()); // CME
        //    var set = ConcurrentHashMap.<String>newKeySet(); // works, wrong order
        var set = new ConcurrentLinkedReducedHashSet<String>(); // Perfect (maybe)
        set.add("hello");
        set.add("world");
        Iterator<String> it = set.iterator();
        set.add("Goodbye");
        while (it.hasNext()) {
            String next = it.next();
            System.out.println(next);
        }
    }
}
