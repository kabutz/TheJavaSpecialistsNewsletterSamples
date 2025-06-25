package eu.javaspecialists.tjsn.issue246;

import java.util.*;

public class AccessOrderTest {
    public static void main(String... args) {
        test(new LinkedHashMap<>()); //insertion order
        System.out.println();
        test(new LinkedHashMap<>(16, 0.75f, true)); //access order
        System.out.println();
        test(new TreeMap<>()); //sorted order
        System.out.println();
        test(new HashMap<>()); //undefined order
    }

    private static void test(Map<Integer, String> map) {
        System.out.println(map.getClass().getSimpleName());
        map.put(42, "Meaning");
        map.put(7, "Days Per Week");
        map.put(86400, "Seconds");
        map.put(1, "Highlander");

        System.out.println("map = " + map);
        System.out.println("map.get(7) = " + map.get(7));
        System.out.println("map = " + map);
    }
}
