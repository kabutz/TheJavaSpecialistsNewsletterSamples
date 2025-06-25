package eu.javaspecialists.tjsn.issue303;

import java.util.*;

public class ReplaceRemoveFun {
    public static void main(String... args) {
        Map<Integer, Long> map = HashMap.newHashMap(1);
        System.out.println(map.replace(-1, null)); // null
        map.put(-1, 0L);
        System.out.println(map.replace(-1, null)); // 0

        System.out.println(map.replace(-1, null, null)); // true

        System.out.println(map.remove(-1, null)); // true
        System.out.println(map.remove(-1, null)); // false
    }
}
