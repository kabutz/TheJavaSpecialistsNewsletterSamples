package eu.javaspecialists.tjsn.issue140;

import java.util.*;

public class Lists {
    public static <T> List<T> toList(T... arr) {
        List<T> list = new ArrayList<T>();
        for (T t : arr) list.add(t);
        return list;
    }
}
