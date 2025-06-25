package eu.javaspecialists.tjsn.issue303;

import java.util.*;

public class MergeFun {
    public static void main(String... args) {
        Map<Integer, Long> map = HashMap.newHashMap(1);
        System.out.println("map = " + map); // {}
        map.put(-1, null);
        System.out.println("map = " + map); // {-1=null}
        map.merge(-1, 1L, (value1, value2) -> 2L);
        System.out.println("map = " + map); // {-1=1}
        map.merge(-1, 1L, (value1, value2) -> 2L);
        System.out.println("map = " + map); // {-1=2}
        map.merge(-1, 1L, (value1, value2) -> null);
        System.out.println("map = " + map); // {}
    }
}
