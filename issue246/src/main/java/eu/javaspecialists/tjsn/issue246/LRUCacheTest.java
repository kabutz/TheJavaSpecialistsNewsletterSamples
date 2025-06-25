package eu.javaspecialists.tjsn.issue246;

import java.util.*;

public class LRUCacheTest {
    public static void main(String... args) {
        Map<Integer, String> cache = new LRUCache<>(5);
        for (int i = 0; i < 10; i++) {
            cache.put(i, "hi");
        }
        // entries 0-4 have already been removed
// entries 5-9 are ordered
        System.out.println("cache = " + cache);

        System.out.println(cache.get(7));
        // entry 7 has moved to the end
        System.out.println("cache = " + cache);

        for (int i = 10; i < 14; i++) {
            cache.put(i, "hi");
        }
        // entries 5,6,8,9 have been removed (eldest entries)
// entry 7 is at the beginning now
        System.out.println("cache = " + cache);

        cache.put(42, "meaning of life");
        // entry 7 is gone too
        System.out.println("cache = " + cache);
    }
}
