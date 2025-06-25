package eu.javaspecialists.tjsn.issue303;

import java.util.*;

public class PutIfAbsentFun {
    public static void main(String... args) {
        Map<Integer, Long> map = HashMap.newHashMap(1); // Java 19
        map.put(-1, null); // start with a null value
        System.out.println("map = " + map); // {-1=null}
        if (!map.containsKey(-1)) map.put(-1, 0L);
        System.out.println("map = " + map); // {-1=null}
        if (map.get(-1) == null) map.put(-1, 0L);
        System.out.println("map = " + map); // {-1=0}
    }
}
