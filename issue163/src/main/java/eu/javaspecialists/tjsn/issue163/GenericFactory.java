package eu.javaspecialists.tjsn.issue163;

import java.util.*;

public class GenericFactory {
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }
    // etc.
}
