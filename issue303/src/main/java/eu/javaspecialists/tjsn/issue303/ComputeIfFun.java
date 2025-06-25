package eu.javaspecialists.tjsn.issue303;

import java.util.*;

public class ComputeIfFun {
    public static void main(String... args) {
        Map<Integer, Long> map = HashMap.newHashMap(1);
        System.out.println("map = " + map); // {}
        map.computeIfAbsent(-1, key -> {
            System.out.println("computing the value");
            return null; // this will not add an entry into the map
        });
        System.out.println("map = " + map); // {}
        map.computeIfAbsent(-1, key -> {
            System.out.println("computing the value");
            return 0L; // the map now contains -1=0L
        });
        System.out.println("map = " + map); // {-1=0L}
        map.computeIfPresent(-1, (key, value) -> {
            System.out.println("computing the value again");
            return null; // this removes the entry from the map
        });
        System.out.println("map = " + map); // {}
        map.put(-1, null);
        System.out.println("map = " + map); // {-1=null}
        map.compute(-1, (key, value) -> null); // removes the entry
        System.out.println("map = " + map);
    }
}
