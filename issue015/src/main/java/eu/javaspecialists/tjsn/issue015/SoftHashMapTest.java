package eu.javaspecialists.tjsn.issue015;

import java.util.*;

public class SoftHashMapTest {
    private static void print(Map map) {
        System.out.println("One=" + map.get("One"));
        System.out.println("Two=" + map.get("Two"));
        System.out.println("Three=" + map.get("Three"));
        System.out.println("Four=" + map.get("Four"));
        System.out.println("Five=" + map.get("Five"));
    }

    private static void testMap(Map map) {
        System.out.println("Testing " + map.getClass());
        map.put("One", new Integer(1));
        map.put("Two", new Integer(2));
        map.put("Three", new Integer(3));
        map.put("Four", new Integer(4));
        map.put("Five", new Integer(5));
        print(map);
        byte[] block = new byte[10 * 1024 * 1024]; // 10 MB
        print(map);
    }

    public static void main(String[] args) {
        testMap(new HashMap());
        testMap(new SoftHashMap(2));
    }
}
