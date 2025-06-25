package eu.javaspecialists.tjsn.issue222;

import java.util.concurrent.*;

public class FindAnyTwin {
    public static void main(String... args) {
        ConcurrentMap<Integer, Object> all =
                new ConcurrentHashMap<>();
        int created = 0;
        Object obj, twin;
        do {
            obj = new Object();
            twin = all.putIfAbsent(obj.hashCode(), obj);
            if ((++created & 0xffffff) == 0) {
                System.out.printf("%,d created%n", created);
            }
        } while (twin == null);
        System.out.println("found twin: " + obj +
                " and " + twin + " but == is " + (obj == twin));
        System.out.println("Size of map is " + all.size());
    }
}
